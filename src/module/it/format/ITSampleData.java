/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package module.it.format;

import io.IReadable;
import sound.effects.BitLimiter;
import io.Reader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import sound.compression.SampleDecompressor;

/**
 *
 * @author Edward Jenkins
 */
public class ITSampleData {

    // instance variables
    private boolean signed;
    private boolean sixteenBit;
    private long samplePointer;
    private long sampleLength;
    private boolean stereo;
    private boolean compresssed;
    private boolean delta;
    private byte[] l8BitData;
    private short[] l16BitData;
    private byte[] r8BitData;
    private short[] r16BitData;

    // constructor
    public ITSampleData(String fileName, boolean signed, boolean sixteenBit,
            long samplePointer, boolean stereo, long sampleLength,
            boolean compresssed, boolean delta) throws
            FileNotFoundException, IOException {
        this.signed = signed;
        this.sixteenBit = sixteenBit;
        this.stereo = stereo;
        this.sampleLength = sampleLength;
        this.samplePointer = samplePointer;
        this.compresssed = compresssed;
        this.delta = delta;

        // initialise arrays
        if (sixteenBit) {

            // left (mono) channel
            l16BitData = new short[(int) sampleLength];

            // right (stereo)
            if (stereo) {
                r16BitData = new short[(int) sampleLength];
            }
        } else {

            // left (mono) channel
            l8BitData = new byte[(int) sampleLength];

            // right (stereo)
            if (stereo) {
                r8BitData = new byte[(int) sampleLength];
            }
        }
    }

    // constructor
    public ITSampleData(boolean signed, boolean sixteenBit,
            boolean stereo, long sampleLength, boolean compresssed,
            boolean delta, double[] lSampleData, double[] rSampleData) {
        this.signed = signed;
        this.sixteenBit = sixteenBit;
        this.stereo = stereo;
        this.sampleLength = sampleLength;
        this.compresssed = compresssed;
        this.delta = delta;

        if (sixteenBit) {

            l16BitData = new short[(int) sampleLength];

            if (stereo) {
                r16BitData = new short[(int) sampleLength];
            }
        } else {

            l8BitData = new byte[(int) sampleLength];

            if (stereo) {
                r8BitData = new byte[(int) sampleLength];
            }
        }

        BitLimiter bl = new BitLimiter((sixteenBit) ? 16 : 8, false);

        // put data in appropriate array
        for (int i = 0; i < sampleLength; i++) {

            if (sixteenBit) {

                l16BitData[i] = (short) bl.calculateAmpLimit(lSampleData[i]);

                if (stereo) {
                    r16BitData[i] = (short) bl.calculateAmpLimit(rSampleData[i]);
                }
            } else {

                l8BitData[i] = (byte) bl.calculateAmpLimit(lSampleData[i]);

                if (stereo) {
                    r8BitData[i] = (byte) bl.calculateAmpLimit(rSampleData[i]);
                }
            }

        }

    }

    // read method
    public boolean read(IReadable reader) {

        boolean success = true;

        // set to data offset
        try {
            reader.setFilePosition(samplePointer);

            // mono loop
            // compresssed
            if (compresssed) {

                SampleDecompressor sd
                        = new SampleDecompressor(
                                (byte) ((sixteenBit) ? 16 : 8), false, delta,
                                (int) sampleLength);

                boolean compressed = sd.decompress(reader);

                long[] decompressedData = sd.getDecompressedData();

                for (int i = 0; i < sampleLength; i++) {

                    // 16-bit
                    if (sixteenBit) {
                        l16BitData[i] = (short) decompressedData[i];
                    } // 8-bit
                    else {
                        l8BitData[i] = (byte) decompressedData[i];
                    }

                }

            } // PCM data
            else {
                for (int i = 0; i < sampleLength; i++) {

                    // 16-bit
                    if (sixteenBit) {
                        l16BitData[i] = reader.getShort();
                    } // 8-bit
                    else {
                        l8BitData[i] = reader.getByte();
                    }

                }
            }

            // stereo loop
            if (stereo) {

                // compresssed
                if (compresssed) {

                    SampleDecompressor sd
                            = new SampleDecompressor(
                                    (byte) ((sixteenBit) ? 16 : 8), false, delta,
                                    (int) sampleLength);

                    boolean compressed = sd.decompress(reader);

                    long[] decompressedData = sd.getDecompressedData();

                    for (int i = 0; i < sampleLength; i++) {

                        // 16-bit
                        if (sixteenBit) {
                            r16BitData[i] = (short) decompressedData[i];
                        } // 8-bit
                        else {
                            r8BitData[i] = (byte) decompressedData[i];
                        }

                    }

                } // PCM data
                else {
                    for (int i = 0; i < sampleLength; i++) {

                        // 16-bit
                        if (sixteenBit) {
                            r16BitData[i] = reader.getShort();
                        } // 8-bit
                        else {
                            r8BitData[i] = reader.getByte();
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.out.println(e);
            success = false;
        }

        return success;
    }

    // length
    public int length() {
        int length = 0;

        if (sixteenBit) {

            length = l16BitData.length * 2;
        } else {

            length = l8BitData.length;
        }

        if (stereo) {
            length *= 2;
        }

        return length;
    }

    // get left sample data
    public double getLSampleDataPoint(int index) {

        // methods variables
        int signedSample;
        double normalisedSample;
        int bitRate = (sixteenBit) ? 16 : 8;
        double bitMultiplier = Math.pow(2, bitRate) / 2 - 0.5;

        // 16-bit
        if (sixteenBit) {

            // convert to signed fron unsiged
            if (!signed) {
                signedSample = 0xffff & l16BitData[index];
                signedSample -= 32768;
            } else {
                signedSample = (int) l16BitData[index];
            }
        } // 8-bit
        else {

            // convert to signed fron unsiged
            if (!signed) {
                signedSample = 0xff & l8BitData[index];
                signedSample -= 128;
            } else {
                signedSample = (int) l8BitData[index];
            }
        }

        // normalised
        normalisedSample = ((double) signedSample + 0.5) / bitMultiplier;

        return normalisedSample;
    }

    // get right sample data
    public double getRSampleDataPoint(int index) {

        // methods variables
        int signedSample;
        double normalisedSample;
        int bitRate = (sixteenBit) ? 16 : 8;
        double bitMultiplier = Math.pow(2, bitRate) / 2 - 0.5;

        // 16-bit
        if (sixteenBit) {

            // convert to signed fron unsiged
            if (!signed) {
                signedSample = 0xffff & r16BitData[index];
                signedSample -= 32768;
            } else {
                signedSample = (int) r16BitData[index];
            }
        } // 8-bit
        else {

            // convert to signed fron unsiged
            if (!signed) {
                signedSample = 0xff & r8BitData[index];
                signedSample -= 128;
            } else {
                signedSample = (int) r8BitData[index];
            }
        }

        // normalised
        normalisedSample = ((double) signedSample + 0.5) / bitMultiplier;

        return normalisedSample;
    }

    public double[] getLData() {

        double[] lSampleData = new double[(int) sampleLength];

        for (int i = 0; i < lSampleData.length; i++) {
            lSampleData[i] = getLSampleDataPoint(i);
        }

        return lSampleData;
    }

    public double[] getRData() {

        double[] rSampleData = new double[(int) sampleLength];

        for (int i = 0; i < rSampleData.length; i++) {
            rSampleData[i] = getRSampleDataPoint(i);
        }

        return rSampleData;
    }

    @Override
    public String toString() {

        // string builder
        StringBuilder sb = new StringBuilder();

        sb.append("Sample Data[");

        // normalised data
        for (int i = 0; i < sampleLength; i++) {
            sb.append("\nSample ");
            sb.append(i);
            sb.append(" :\t");
            sb.append(getLSampleDataPoint(i));
            sb.append(" :\t");
            if (stereo) {
                sb.append(getRSampleDataPoint(i));
            }
        }
        sb.append("]");

        return sb.toString();
    }
}
