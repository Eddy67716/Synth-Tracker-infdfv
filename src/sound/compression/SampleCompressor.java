/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sound.compression;

import io.ByteArrayWriter;
import java.io.IOException;
import static sound.compression.SampleCompressSpec.BLOCK_SIZE;

/**
 *
 * @author Edward Jenkins
 */
public class SampleCompressor extends SampleCompressSpec {

    // instance variables
    // current index of first no delta sample
    private int index;
    // block of deltafied samples
    private long[] deltafiedSamplesBlock;
    // bitWidth block
    private byte[] sampleBitWidthBlock;
    // count of samples in block
    private int blockSampleCount;
    // maximum sample count in block
    private int maxSamplesInBlock;
    // sample writer
    private ByteArrayWriter compressedDataWriter;
    // block writer
    private ByteArrayWriter blockWriter;
    // highBitValue
    private byte highBitValue;
    // used when a value is constant for a long time
    private boolean longConstantValue;
    // stores count when a value is constant for a while
    private int longConstantCount;
    // used when a value is consistantly low for a while
    private boolean constantlyBelowBoundary;
    // below boundary count
    private int belowBoundaryCount;

    // constructor
    public SampleCompressor(byte bitRate, boolean floating,
            boolean doubleDelta, boolean littleEndian) {
        super(bitRate, floating, doubleDelta, littleEndian);
        index = 0;
        maxSamplesInBlock = BLOCK_SIZE / (getBitRate() / 8);
        sampleBitWidthBlock = new byte[maxSamplesInBlock];
        deltafiedSamplesBlock = new long[maxSamplesInBlock];
    }

    // compress
    public byte[] compress(long[] sampleData) throws IOException {

        // the byte array writer
        compressedDataWriter = new ByteArrayWriter(isLittleEndian());

        while (index < sampleData.length) {

            // delta encode a block of samples
            deltaEncode(sampleData, !isDoubleDelta());

            // IT 2.15 (2.14p1) comressed
            if (isDoubleDelta()) {
                deltaEncode(deltafiedSamplesBlock, true);
            }

            // build bitrate array out of dealta sample block
            for (int i = 0; i < blockSampleCount; i++) {
                sampleBitWidthBlock[i]
                        = getMinimumBitRateForSample(deltafiedSamplesBlock[i]);
            }

            // process bit train
            processBitTrain();

            // build a byte array of the compressed data
            byte[] compressedBlock = compressData();

            // write lenght of array
            compressedDataWriter.writeShort((short) compressedBlock.length);

            // write compressed data block
            compressedDataWriter.writeBytes(compressedBlock);

        }

        return compressedDataWriter.extractArray();
    }

    // delta encode
    public void deltaEncode(long[] sampleData, boolean secondDelta) {

        long deltaValue = 0, newDeltaValue;
        int i;
        int indexOffset = (secondDelta) ? 0 : index;

        for (i = 0; i < maxSamplesInBlock; i++) {
            if (i + indexOffset == sampleData.length - 1) {
                i++;
                break;
            }
            newDeltaValue = sampleData[i + indexOffset];
            deltafiedSamplesBlock[i] = newDeltaValue - deltaValue;
            deltaValue = newDeltaValue;
            if ((deltaValue > getMaximumBitValue()
                    || deltaValue < getMinimumBitValue()) && secondDelta) {
                break;
            }

        }
        if (secondDelta) {
            blockSampleCount = i;
            index += i - 1;
        }
    }

    // calculate minimum bitrate to store sample
    @Override
    public byte getMinimumBitRateForSample(long sample) {

        long unsignedSample = sample;

        // gets the normally minimum bitrate for the sample
        byte sampleBitWidth = super.getMinimumBitRateForSample(sample);

        // making header bits all 1 if first bit is 1
        if (unsignedSample >>> 63 == 1) {

            // bitshift backwards and forwards to set value to unsigned
            unsignedSample <<= (64 - sampleBitWidth);
            unsignedSample >>>= (64 - sampleBitWidth);
        }

        int topBit = 1 << (sampleBitWidth - 1);

        // check if in the 1-6 bit range
        if (sampleBitWidth < 7) {

            // if lowest posible vaue, increment bitRate by 1
            if (unsignedSample == topBit) {
                sampleBitWidth++;
            }
        }

        // check if in the range values in the 7-bitrate range
        if (sampleBitWidth > 6 && sampleBitWidth <= getBitRate()) {

            // if within new bitrate bounds, increment by 1
            while (unsignedSample - topBit >= getbNegativeValue()
                    && unsignedSample - topBit <= getbValue()) {
                sampleBitWidth++;
                topBit = 1 << (sampleBitWidth - 1);
            }
        }

        return sampleBitWidth;
    }

    // figures out the smallest way to compress the data without too many width
    // changes
    private void processBitTrain() {

        // method variables
        int index = 0;
        int processed;

        // make sure all bits are checked
        while (index < blockSampleCount) {

            // algorithm A constant value
            processed = constantBitWidthFor(index);

            if (processed > 10
                    || (blockSampleCount - index <= processed)) {

                index += processed;
            } else {

                int bitOffset = 1;

                // algorithm b bits within an offset
                do {
                    processed = offsetBitWidth(index, bitOffset);
                    bitOffset++;
                    if (longConstantValue || constantlyBelowBoundary) {
                        break;
                    }
                } while (processed < 5
                        && blockSampleCount - (index + processed) > 4);

                setBitsInRange(index, processed, highBitValue);

                index += processed;

                if (longConstantValue) {
                    index += longConstantCount;
                    longConstantCount = 0;
                    longConstantValue = false;
                } else if (constantlyBelowBoundary) {
                    constantlyBelowBoundary = false;
                }
            }
        }
    }

    // algorithm A
    private int constantBitWidthFor(int index) {

        if (index == blockSampleCount) {
            return -1;
        }

        int bitWidth = this.sampleBitWidthBlock[index];
        int constantFor = 0;

        while (index < blockSampleCount
                && sampleBitWidthBlock[index] == bitWidth) {
            index++;
            constantFor++;
        }

        return constantFor;
    }

    // algorithm B
    private int offsetBitWidth(int index, int offset) {

        byte startingBits = sampleBitWidthBlock[index];
        int withinRangeFor = 1;
        int belowLowBoundary = 0;
        int possibleLongConstant;
        byte highestBitValue = (byte) (startingBits + offset);
        highBitValue = startingBits;

        // increment before while loop
        index++;

        int currentSampleBitRate = sampleBitWidthBlock[index];
        while (currentSampleBitRate <= startingBits + offset
                && currentSampleBitRate < this.getBitRate() + 1
                && index < this.blockSampleCount) {
            if (currentSampleBitRate > startingBits
                    && currentSampleBitRate <= highestBitValue) {
                if (withinRangeFor > 6) {
                    break;
                }
                highBitValue = (byte) currentSampleBitRate;
            }
            if (currentSampleBitRate < startingBits - offset) {
                belowLowBoundary++;
            } else {
                belowLowBoundary = 0;
            }
            index++;
            withinRangeFor++;
            possibleLongConstant = constantBitWidthFor(index);
            if (possibleLongConstant > 6) {
                longConstantValue = true;
                longConstantCount = possibleLongConstant;
                break;
            }
            if (belowLowBoundary > 5) {
                withinRangeFor -= belowLowBoundary;
                constantlyBelowBoundary = true;
                break;
            }
            if (index < blockSampleCount) {
                currentSampleBitRate = sampleBitWidthBlock[index];
            }
        }

        return withinRangeFor;
    }

    // finds blocks that stay within two values and set the lower value to the
    // higher one if the total bit size will be smaller
    private void bitWidthAlgorithmC() {

        // method variables
        int i = 0;
        int blockStartIndex = 0;
        int blockRange = 0;
        byte lastBitRate;
        
        while (i < blockSampleCount) {
            lastBitRate = sampleBitWidthBlock[i];
            
            blockRange += constantBitWidthFor(i);
        }
        
    }

    private void setBitsInRange(int index, int range, byte bitRate) {

        bitRate = checkBits(index, range);

        if (bitRate * range < getBitCount(index, range)) {

            // set all bit value to highest value
            for (int i = index; i < index + range; i++) {
                sampleBitWidthBlock[i] = bitRate;
            }
        }
    }

    private byte checkBits(int index, int range) {
        byte highestBitRate = 0;

        // find highest bitrate and return it
        for (int i = index; i < index + range; i++) {
            if (sampleBitWidthBlock[i] > highestBitRate) {
                System.out.println("Higher bitrate found. ");
                highestBitRate = sampleBitWidthBlock[i];
            }
        }

        return highestBitRate;
    }

    // find out how many bits it would take to store the bitwidts within
    // index to index + range
    private int getBitCount(int index, int range) {
        int bitCount = 0;

        byte lastBitRate = sampleBitWidthBlock[0];

        for (int i = index; i < index + range; i++) {
            byte bits = sampleBitWidthBlock[i];
            if (bits != lastBitRate) {
                bitCount += lastBitRate;
                if (lastBitRate < 6) {
                    bitCount += this.getaBitWidth();
                }
            }
            bitCount += bits;
            lastBitRate = bits;
        }

        return bitCount;
    }

    // compress block
    private byte[] compressData() throws IOException {

        byte[] compressedData;

        // the byte array writer
        blockWriter = new ByteArrayWriter(isLittleEndian());

        setCurrentBitWidth(getBitRate() + 1);

        // write all of the block data to baw
        for (int i = 0; i < blockSampleCount; i++) {

            int bitWidth = sampleBitWidthBlock[i];

            // alter bitwidth if different
            if (bitWidth != getCurrentBitWidth()) {
                setNewBitWidth(bitWidth);
            }

            blockWriter.writeArbitraryBitValue(deltafiedSamplesBlock[i],
                    (byte) getCurrentBitWidth());

        }

        compressedData = blockWriter.extractArray();

        return compressedData;
    }

    private void setNewBitWidth(int bitWidth) throws IOException {

        if (getCurrentBitWidth() > getBitRate() + 1) {
            // invalid bitrate error
            throw (new IllegalArgumentException("Bitrate "
                    + getCurrentBitWidth() + " is bigger"
                    + " than " + (getBitRate() + 1) + "!"));
        } else if (getCurrentBitWidth() < 7) {
            typeA(bitWidth);
        } else if (getCurrentBitWidth() < getBitRate() + 1) {
            typeB(bitWidth);
        } else {
            typeC(bitWidth);
        }
    }

    private void typeA(int bitWidth) throws IOException {

        // update value
        int writeValue = updateABBitWidth(bitWidth);

        // write special value
        blockWriter.writeArbitraryBitValue(getTopBit(),
                (byte) getCurrentBitWidth());

        // update bit width
        setCurrentBitWidth(bitWidth);

        // write new bitwidth value
        blockWriter.writeArbitraryBitValue(writeValue,
                getaBitWidth());
    }

    private void typeB(int bitWidth) throws IOException {

        // get the current bitwidth
        int oldBitRate = getCurrentBitWidth();

        // update value
        int writeValue = updateABBitWidth(bitWidth) + getbNegativeValue()
                + getTopBit();

        // update bit width
        setCurrentBitWidth(bitWidth);

        // write new bitwidth value
        blockWriter.writeArbitraryBitValue(writeValue, (byte) oldBitRate);
    }

    private void typeC(int bitWidth) throws IOException {

        // get the current bitwidth
        int oldBitRate = getCurrentBitWidth();

        // update value
        int writeValue = updateCBitWidth(bitWidth) | getTopBit();

        // update bit width
        setCurrentBitWidth(bitWidth);

        // write new bitwidth value
        blockWriter.writeArbitraryBitValue(writeValue, (byte) oldBitRate);
    }

    private int updateABBitWidth(int bitRate) {
        if (bitRate > getCurrentBitWidth()) {
            bitRate--;
        }
        bitRate--;

        return bitRate;
    }

    private int updateCBitWidth(int bitRate) {
        bitRate--;
        return bitRate;
    }
}