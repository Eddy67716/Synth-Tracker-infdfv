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
    private ByteArrayReader bar;

    // getter
    public long[] getDecompressedData() {
        return decompressedData;
    }

    // constructor
    public SampleDecompressor(byte bitrate, boolean floating,
            boolean doubleDelta, int dataSize) {
        super(bitrate, floating, doubleDelta);
        decompressedData = new long[dataSize];
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
                        throw (new IllegalArgumentException("Bitrate "
                                + currentBitWidth + " is bigger"
                                + " than " + (getBitRate() + 1) + "!"));
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
    public void typeD() {

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
