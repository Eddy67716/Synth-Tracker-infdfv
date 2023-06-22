/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sound.formats;

import java.nio.ByteBuffer;

/**
 *
 * @author Edward Jenkins
 * @version 0.1 © 2023
 */
public class SampleData {

    // instance variables
    private byte bitrate;
    private boolean signed;
    private boolean floating;
    private byte channels;
    private int sampleCount;
    private int sampleIndex;
    private byte[][] fourBitSampleData;
    private byte[][] eightBitSampleData;
    private short[][] sixteenBitSampleData;
    private byte[][][] twentyFourBitSampleData;
    private int[][] thirtyTwoBitSampleData;
    private float[][] floatThirtyTwoBitSampleData;
    private long[][] sixtyFourBitSampleData;
    private double[][] floatSixtyFourBitSampleData;

    // all args constructor
    public SampleData(byte bitrate, boolean signed, boolean floating,
            byte channels, int sampleCount) {
        this.bitrate = bitrate;
        this.signed = signed;
        this.floating = floating;
        this.channels = channels;
        this.sampleCount = sampleCount;

        switch (bitrate) {
            case 8:
                eightBitSampleData
                        = new byte[sampleCount][channels];
                break;
            case 16:
                sixteenBitSampleData
                        = new short[sampleCount][channels];
                break;
            case 24:
                twentyFourBitSampleData
                        = new byte[sampleCount][channels][3];
                break;
            case 32:
                if (floating) {
                    floatThirtyTwoBitSampleData
                            = new float[sampleCount][channels];
                } else {
                    thirtyTwoBitSampleData
                            = new int[sampleCount][channels];
                }
                break;
            case 64:
                if (floating) {
                    floatSixtyFourBitSampleData
                            = new double[sampleCount][channels];
                } else {
                    sixtyFourBitSampleData
                            = new long[sampleCount][channels];
                }
                break;
            default:
                break;
        }
        sampleIndex = 0;
    }

    // getters
    public byte getBitrate() {
        return bitrate;
    }

    public boolean isSigned() {
        return signed;
    }

    public boolean isFloating() {
        return floating;
    }

    public byte getChannels() {
        return channels;
    }

    public int getSampleCount() {
        return sampleCount;
    }

    // setters
    public void setBitrate(byte bitrate) {
        this.bitrate = bitrate;
    }

    public void setSigned(boolean signed) {
        this.signed = signed;
    }

    public void setFloating(boolean floating) {
        this.floating = floating;
    }

    public void setChannels(byte channels) {
        this.channels = channels;
    }

    public void setSampleCount(int sampleCount) {
        this.sampleCount = sampleCount;
    }

    public void inputPoints(double[] samplePoints) {
        switch (bitrate) {
            case 8:
                for (int i = 0; i < channels; i++) {
                    eightBitSampleData[sampleIndex][i] = (byte) samplePoints[i];
                }
                break;
            case 16:
                for (int i = 0; i < channels; i++) {
                    sixteenBitSampleData[sampleIndex][i]
                            = (short) samplePoints[i];
                }
                break;
            case 24:
                int twentyFourBitSample;
                for (int i = 0; i < channels; i++) {
                    twentyFourBitSample = (int) samplePoints[i];
                    byte[] sampleBytes = {
                        (byte) ((twentyFourBitSample & 0xff0000) >>> 16),
                        (byte) ((twentyFourBitSample & 0xff00) >>> 8),
                        (byte) (twentyFourBitSample & 0xff)};
                    twentyFourBitSampleData[sampleIndex][i] = sampleBytes;
                }
                break;
            case 32:
                if (floating) {
                    for (int i = 0; i < channels; i++) {
                        floatThirtyTwoBitSampleData[sampleIndex][i]
                                = (float) samplePoints[i];
                    }
                } else {
                    for (int i = 0; i < channels; i++) {
                        thirtyTwoBitSampleData[sampleIndex][i]
                                = (int) samplePoints[i];
                    }
                }
                break;
            case 64:
                if (floating) {
                    floatSixtyFourBitSampleData[sampleIndex]
                            = samplePoints;
                } else {
                    for (int i = 0; i < channels; i++) {
                        sixtyFourBitSampleData[sampleIndex][i]
                                = (long) samplePoints[i];
                    }
                }
            default:
                break;
        }

        sampleIndex++;
    }

    public double[] outputPoints(int index) {

        double[] outputPoints = new double[channels];

        switch (bitrate) {
            case 8:
                for (int i = 0; i < channels; i++) {
                    outputPoints[i] = eightBitSampleData[index][i];
                }
                break;
            case 16:
                for (int i = 0; i < channels; i++) {
                    outputPoints[i] = sixteenBitSampleData[index][i];
                }
                break;
            case 24:
                int twentyFourBitSample;
                for (int i = 0; i < channels; i++) {
                    
                    // build int from bytes
                    byte[] spliceToArray = {
                        (((twentyFourBitSampleData[index][i][0] >>> 7) 
                            & 1) == 1) ? (byte)0xff : (byte)0, 
                        twentyFourBitSampleData[index][i][0], 
                        twentyFourBitSampleData[index][i][1],
                        twentyFourBitSampleData[index][i][2]
                    };

                    ByteBuffer debuffer = ByteBuffer.wrap(spliceToArray);
                    twentyFourBitSample = debuffer.getInt();
                    outputPoints[i] = twentyFourBitSample;
                }
                break;
            case 32:
                if (floating) {
                    for (int i = 0; i < channels; i++) {
                        outputPoints[i] = floatThirtyTwoBitSampleData[index][i];
                    }
                } else {
                    for (int i = 0; i < channels; i++) {
                        outputPoints[i] = thirtyTwoBitSampleData[index][i];
                    }
                }
                break;
            case 64:
                if (floating) {
                    for (int i = 0; i < channels; i++) {
                        outputPoints[i] = floatSixtyFourBitSampleData[index][i];
                    }
                } else {
                    for (int i = 0; i < channels; i++) {
                        outputPoints[i] = sixtyFourBitSampleData[index][i];
                    }
                }
            default:
                break;
        }

        return outputPoints;
    }

    // get normalised sampleData
    public double[][] getNormalisedSampleData() {

        double[][] normalisedSampleData = new double[sampleCount][channels];
        double bitMultiplier = Math.pow(2, bitrate) / 2 - 0.5;

        switch (bitrate) {
            case 8:
                for (int i = 0; i < eightBitSampleData.length; i++) {
                    for (int j = 0; j < eightBitSampleData[i].length; j++) {
                        normalisedSampleData[i][j]
                                = ((double) eightBitSampleData[i][j]
                                + 0.5) / bitMultiplier;
                    }
                }
                break;
            case 16:
                for (int i = 0; i < sixteenBitSampleData.length; i++) {
                    for (int j = 0; j < sixteenBitSampleData[i].length; j++) {
                        normalisedSampleData[i][j]
                                = ((double) sixteenBitSampleData[i][j]
                                + 0.5) / bitMultiplier;
                    }
                }
                break;
            case 24:
                int twentyFourBitSample;
                for (int i = 0; i < twentyFourBitSampleData.length; i++) {
                    for (int j = 0; j < twentyFourBitSampleData[i].length; j++) {
                        twentyFourBitSample
                                = twentyFourBitSampleData[i][j][2]
                                | twentyFourBitSampleData[i][j][1] << 8
                                | twentyFourBitSampleData[i][j][0] << 16;
                        normalisedSampleData[i][j]
                                = ((double) twentyFourBitSample
                                + 0.5) / bitMultiplier;
                    }
                }
                break;
            case 32:
                if (floating) {
                    for (int i = 0; i < floatThirtyTwoBitSampleData.length;
                            i++) {
                        for (int j = 0; j < floatThirtyTwoBitSampleData[i].length; j++) {
                            normalisedSampleData[i][j]
                                    = floatThirtyTwoBitSampleData[i][j];
                        }
                    }
                } else {
                    for (int i = 0; i < thirtyTwoBitSampleData.length; i++) {
                        for (int j = 0; j < thirtyTwoBitSampleData[i].length;
                                j++) {
                            normalisedSampleData[i][j]
                                    = ((double) thirtyTwoBitSampleData[i][j]
                                    + 0.5) / bitMultiplier;
                        }
                    }
                }
                break;
            case 64:
                if (floating) {
                    normalisedSampleData = floatSixtyFourBitSampleData;
                } else {
                    for (int i = 0; i < sixtyFourBitSampleData.length; i++) {
                        for (int j = 0; j < sixtyFourBitSampleData[i].length;
                                j++) {
                            normalisedSampleData[i][j]
                                    = ((double) sixtyFourBitSampleData[i][j]
                                    + 0.5) / bitMultiplier;
                        }
                    }
                }
            default:
                break;
        }

        return normalisedSampleData;
    }
}
