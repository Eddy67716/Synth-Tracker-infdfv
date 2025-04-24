/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sound.compression;

/**
 *
 * @author Edward Jenkins
 */
public abstract class SampleCompressSpec {
    
    // constants
    public static final int BLOCK_SIZE = 0x8000;

    // instance variables
    private byte bitRate;
    // a parameter
    private byte aBitWidth;
    // b parameters
    // half the bitrate - 1
    private byte bValue;
    // (-)half the bitrate
    private byte bNegativeValue;
    // is a floating point number
    private boolean floating;
    // is double delta encoded
    private boolean doubleDelta;
    // bounds for compression type
    private long minimumBitValue;
    private long maximumBitValue;
    // the endieness to encode or data with
    private boolean littleEndian;
    // current bit width
    private int currentBitWidth;
    // the top bit of the curent bit width
    private int topBit;

    // constructor
    public SampleCompressSpec(byte bitRate, boolean floating,
            boolean doubleDelta, boolean littleEndian) {
        this.bitRate = bitRate;
        this.littleEndian = littleEndian;
        switch (bitRate) {
            case 4:
                bValue = 1;
                aBitWidth = 2;
                minimumBitValue = -8;
                maximumBitValue = 7;
            case 8:
                bValue = 0b11;
                aBitWidth = 3;
                minimumBitValue = Byte.MIN_VALUE;
                maximumBitValue = Byte.MAX_VALUE;
                break;
            case 16:
                bValue = 0b111;
                aBitWidth = 4;
                minimumBitValue = Short.MIN_VALUE;
                maximumBitValue = Short.MAX_VALUE;
                break;
            case 24:
                bValue = 0b1011;
                aBitWidth = 5;
                minimumBitValue = 0xffffffffff800000L;
                maximumBitValue = 0xffffff;
                break;
            case 32:
                bValue = 0b1111;
                aBitWidth = 5;
                minimumBitValue = Integer.MIN_VALUE;
                maximumBitValue = Integer.MAX_VALUE;
                break;
            case 64:
                bValue = 0b11111;
                aBitWidth = 6;
                minimumBitValue = Long.MIN_VALUE;
                maximumBitValue = Long.MAX_VALUE;
                break;
        }
        bNegativeValue = (byte) (~bValue);
        this.floating = floating;
        this.doubleDelta = doubleDelta;
    }

    // getters
    public byte getBitRate() {
        return bitRate;
    }

    public byte getaBitWidth() {
        return aBitWidth;
    }

    public byte getbValue() {
        return bValue;
    }

    public byte getbNegativeValue() {
        return bNegativeValue;
    }

    public boolean isFloating() {
        return floating;
    }

    public boolean isDoubleDelta() {
        return doubleDelta;
    }

    public long getMinimumBitValue() {
        return minimumBitValue;
    }

    public long getMaximumBitValue() {
        return maximumBitValue;
    }

    public boolean isLittleEndian() {
        return littleEndian;
    }

    public int getCurrentBitWidth() {
        return currentBitWidth;
    }

    public int getTopBit() {
        return topBit;
    }

    // setters
    public void setBitRate(byte bitRate) {
        this.bitRate = bitRate;
        switch (bitRate) {
            case 8:
                bValue = 0b11;
                aBitWidth = 3;
                break;
            case 16:
                bValue = 0b111;
                aBitWidth = 4;
                break;
            case 24:
                bValue = 0b1111;
                aBitWidth = 5;
            case 32:
                bValue = 0b1111;
                aBitWidth = 5;
                break;
            case 64:
                bValue = 0b11111;
                aBitWidth = 6;
                break;
        }
        bNegativeValue = (byte) (~bValue);
    }

    public void setFloating(boolean floating) {
        this.floating = floating;
    }

    public void setDoubleDelta(boolean doubleDelta) {
        this.doubleDelta = doubleDelta;
    }

    public void setCurrentBitWidth(int currentBitWidth) {
        this.currentBitWidth = currentBitWidth;
        topBit = 1 << (currentBitWidth - 1);
    }
    
    // calculate minimum bitrate to store sample
    public byte getMinimumBitRateForSample(long sample) {
        
        byte min = 2, max = this.getBitRate();
        byte i;

        if (sample == 0) {
            return 1;
        } else if (sample > 0) {

            do {
                i = (byte) ((min + max) / 2);
                
                if ((Math.pow(2, i) / 2 - 1 < sample)) {
                    min = (byte) (i + 1);
                    i++;
                } else {
                    max = (byte) (i - 1);
                }
            } while (min <= max);
        } else {

            do {
                i = (byte) ((min + max) / 2);

                if (-(Math.pow(2, i) / 2 - 1) > sample) {
                    min = (byte) (i + 1);
                    i++;
                } else {
                    max = (byte) (i - 1);
                }
            } while (min <= max);
        }

        return i;
    }
    
}