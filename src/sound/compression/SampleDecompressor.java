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
            boolean doubleDelta, int dataSize, boolean littleEndian, 
            boolean allowingTypeD) {
        super(bitrate, floating, doubleDelta, littleEndian);
        this.allowingTypeD = allowingTypeD;
        decompressedData = new long[dataSize];
    }

    // constructor
    public SampleDecompressor(byte bitrate, boolean floating,
            boolean doubleDelta, int dataSize, boolean littleEndian) {
        this(bitrate, floating, doubleDelta, dataSize, littleEndian, false);
    }

    public boolean decompress(IReadable r) throws IOException {

        // method variables
        boolean change;

        for (i = 0; i < decompressedData.length;) {

            // get compressedBlockLength
            int compressedBlockLength = r.getUShortAsInt();
            bar = new ByteArrayReader(r.getBytes(compressedBlockLength), 
                    isLittleEndian());
            // initialsie or reset the delta values
            val1 = val2 = 0;
            setCurrentBitWidth(getBitRate() + 1);
            blockIndex = 0;

            while (bar.isInBounds() && i < decompressedData.length) {

                try {
                    if (getCurrentBitWidth() > getBitRate() + 1) {
                        // invalid bitrate error
                        throw (new IllegalArgumentException("Bitrate "
                                + getCurrentBitWidth() + " is bigger"
                                + " than " + (getBitRate() + 1) + "!"));
                    } else if (getCurrentBitWidth() == 1 && allowingTypeD) {
                        typeD();
                    } else if (getCurrentBitWidth() < 7) {
                        typeA();
                    } else if (getCurrentBitWidth() < getBitRate() + 1) {
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
                = bar.getArbitraryBitValue((byte) getCurrentBitWidth(), true);

        long unsignedValue = value;

        // making header bits all 1 if first bit is 1
        if (unsignedValue >>> 63 == 1) {

            // bitshift backwards and forwards to set value to unsigned
            unsignedValue <<= (64 - getCurrentBitWidth());
            unsignedValue >>>= (64 - getCurrentBitWidth());
        }

        // check if value is new bitrate value
        if (unsignedValue == getTopBit()) {

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
                = bar.getArbitraryBitValue((byte) getCurrentBitWidth(), true);

        long unsignedValue = value;

        // making header bits all 1 if first bit is 1
        if (unsignedValue >>> 63 == 1) {

            // bitshift backwards and forwards to set value to unsigned
            unsignedValue <<= (64 - getCurrentBitWidth());
            unsignedValue >>>= (64 - getCurrentBitWidth());
        }

        // check if value is new bitrate value
        if (unsignedValue - getTopBit() >= getbNegativeValue()
                && unsignedValue - getTopBit() <= getbValue()) {
            
            updateWidth((int)unsignedValue - getTopBit() - getbNegativeValue());
        } else {

            // add value to samples
            decodeToAppendValue(value);
        }
    }

    // bitlengh 1 greater than bitrate
    public void typeC() throws IOException {

        // get value
        long value
                = bar.getArbitraryBitValue((byte) getCurrentBitWidth(), true);

        long unsignedValue = value;

        // making header bits all 1 if first bit is 1
        if (unsignedValue >>> 63 == 1) {

            // bitshift backwards and forwards to set value to unsigned
            unsignedValue <<= (64 - getCurrentBitWidth());
            unsignedValue >>>= (64 - getCurrentBitWidth());
        }

        // check if value is new bitrate value
        if ((unsignedValue & getTopBit()) >= 1) {

            // get new bitwidth
            setCurrentBitWidth((int) ((unsignedValue & 0xff) + 1));
        } else {

            // bitshift to make value signed again
            int bits = getBitRate();
            
            // set value to negative if seconde highest bit is 1
            if (value >>> (bits - 1) == 1 && blockIndex == 0) {
                value <<= (64 - bits);
                value >>= (64 - bits);
            }

            // add value to samples
            decodeToAppendValue(value);
        }
    }

    // constant value
    public void typeD() throws IOException {

        // get leading bit
        if (bar.getArbitraryBitValue((byte) getCurrentBitWidth(), false) == 0) {
            
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
        if (newWidth >= getCurrentBitWidth()) {
            newWidth++;
        }
        setCurrentBitWidth(newWidth);
    }

    // decode delta data and append to file
    public void decodeToAppendValue(long value) {
        //System.out.println(value + " " + getCurrentBitWidth());
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
