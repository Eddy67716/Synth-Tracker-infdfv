/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sound.compression;

import io.ByteArrayReader;
import io.IReadable;
import java.io.IOException;

/**
 *
 * @author Edward Jenkins
 */
public class SampleDecompressor extends SampleCompressSpec {

    // instance variables
    private long[] decompressedData;
    private long val1;
    private long val2;
    private int currentBitWidth;
    private int topBit;
    private int i;
    private int blockIndex;
    private boolean allowingTypeD;
    private ByteArrayReader bar;

    // getter
    public long[] getDecompressedData() {
        return decompressedData;
    }
    
    // all args constructor
    public SampleDecompressor(byte bitrate, boolean floating,
            boolean doubleDelta, int dataSize, boolean allowingTypeD) {
        super(bitrate, floating, doubleDelta);
        this.allowingTypeD = allowingTypeD;
        decompressedData = new long[dataSize];
    }

    // constructor
    public SampleDecompressor(byte bitrate, boolean floating,
            boolean doubleDelta, int dataSize) {
        this(bitrate, floating, doubleDelta, dataSize, false);
    }

    public boolean decompress(IReadable r) throws IOException {

        // method variables
        boolean change;

        for (i = 0; i < decompressedData.length;) {

            // get compressedBlockLength
            int compressedBlockLength = r.getUShortAsInt();
            bar = new ByteArrayReader(r.getBytes(compressedBlockLength), true);
            // initialsie or reset the delta values
            val1 = val2 = 0;
            currentBitWidth = getBitRate() + 1;
            topBit = 1 << (currentBitWidth - 1);
            blockIndex = 0;

            while (bar.isInBounds() && i < decompressedData.length) {

                try {
                    if (currentBitWidth > getBitRate() + 1) {
                        // invalid bitrate error
                        throw (new IllegalArgumentException("Bitrate "
                                + currentBitWidth + " is bigger"
                                + " than " + (getBitRate() + 1) + "!"));
                    } else if (currentBitWidth == 1 && allowingTypeD) {
                        typeD();
                    } else if (currentBitWidth < 7) {
                        typeA();
                    } else if (currentBitWidth < getBitRate() + 1) {
                        typeB();
                    } else {
                        typeC();
                    }
                } catch (Exception e) {
                    System.out.println(e + " " + e.getMessage());
                    i = decompressedData.length;
                }
            }
        }

        return true;
    }

    // bitlength 1-6
    public void typeA() throws IOException {

        // get value
        long value
                = bar.getArbitraryBitValue((byte) currentBitWidth, true);

        long unsignedValue = value;

        // making header bits all 1 if first bit is 1
        if (unsignedValue >>> 63 == 1) {

            // bitshift backwards and forwards to set value to unsigned
            unsignedValue <<= (64 - currentBitWidth);
            unsignedValue >>>= (64 - currentBitWidth);
        }

        // check if value is new bitrate value
        if (unsignedValue == topBit) {

            // get new bitwidth
            byte bits = getaBitWidth();

            updateWidth((int) bar.getArbitraryBitValue(bits, false));
        } else {

            // add value to samples
            decodeToAppendValue(value);
        }
    }

    // bitlength 7-sample bitrate
    public void typeB() throws IOException {

        // get value
        long value
                = bar.getArbitraryBitValue((byte) currentBitWidth, true);

        long unsignedValue = value;

        // making header bits all 1 if first bit is 1
        if (unsignedValue >>> 63 == 1) {

            // bitshift backwards and forwards to set value to unsigned
            unsignedValue <<= (64 - currentBitWidth);
            unsignedValue >>>= (64 - currentBitWidth);
        }

        // check if value is new bitrate value
        if (unsignedValue - topBit >= getbNegativeValue()
                && unsignedValue - topBit <= getbValue()) {
            
            updateWidth((int)unsignedValue - topBit - getbNegativeValue());
        } else {

            // add value to samples
            decodeToAppendValue(value);
        }
    }

    // bitlengh 1 greater than bitrate
    public void typeC() throws IOException {

        // get value
        long value
                = bar.getArbitraryBitValue((byte) currentBitWidth, true);

        long unsignedValue = value;

        // making header bits all 1 if first bit is 1
        if (unsignedValue >>> 63 == 1) {

            // bitshift backwards and forwards to set value to unsigned
            unsignedValue <<= (64 - currentBitWidth);
            unsignedValue >>>= (64 - currentBitWidth);
        }

        // check if value is new bitrate value
        if ((unsignedValue & topBit) >= 1) {

            // get new bitwidth
            currentBitWidth = (int) ((unsignedValue & 0xff) + 1);
            topBit = 1 << (currentBitWidth - 1);
        } else {

            // add value to samples
            decodeToAppendValue(value);
        }
    }

    // constant value
    public void typeD() throws IOException {

        // get leading bit
        if (bar.getArbitraryBitValue((byte) currentBitWidth, false) == 0) {
            
            // use type A if bit is zero
            typeA();
        } else {
            
            // use type D if 1;
            
            // how many bits the length hold (1-16)
            byte bitsForConstantWidth 
                    = (byte)(bar.getArbitraryBitValue((byte)4, false) + 1);
            
            // how long value is held for
            long holdLength 
                    = bar.getArbitraryBitValue(bitsForConstantWidth, false);
            
            // add all constant values to collection for decoding
            for (int holdIndex = 0; holdIndex < holdLength; holdIndex++) {
                decodeToAppendValue(0);
            }
        }
    }

    // update width
    public void updateWidth(int newWidth) {
        newWidth++;
        if (newWidth >= currentBitWidth) {
            newWidth++;
        }
        currentBitWidth = newWidth;
        topBit = 1 << (currentBitWidth - 1);
    }

    // decode delta data and append to file
    public void decodeToAppendValue(long value) {
        //System.out.println(value);
        val1 += value;
        val2 += val1;
        decompressedData[i] = (isDoubleDelta()) ? val2 : val1;
        i++;
        if (i == decompressedData.length - 1) {
            i++;
        }
        blockIndex += (getBitRate() / 8);
    }
}
