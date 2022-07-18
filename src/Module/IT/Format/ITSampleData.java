/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Module.IT.Format;

import Sound.Effects.BitLimiter;
import io_stuff.ReadMethods;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author Edward Jenkins
 */
public class ITSampleData {

    // instance variables
    private ReadMethods rm;
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
        rm = new ReadMethods(fileName, true);

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

                l16BitData[i] = (short)bl.calculateAmpLimit(lSampleData[i]);

                if (stereo) {
                    r16BitData[i] = (short)bl.calculateAmpLimit(rSampleData[i]);
                }
            } else {

                l8BitData[i] = (byte)bl.calculateAmpLimit(lSampleData[i]);

                if (stereo) {
                    r8BitData[i] = (byte)bl.calculateAmpLimit(rSampleData[i]);
                }
            }

        }

    }

    // read method
    public boolean read() {

        boolean success = true;

        // set to data offset
        try {
            rm.skipBytes(samplePointer);

            // mono loop
            for (int i = 0; i < sampleLength; i++) {

                // compresssed
                if (compresssed) {

                } // PCM data
                else {

                    // 16-bit
                    if (sixteenBit) {
                        l16BitData[i] = rm.getShort();
                    } // 8-bit
                    else {
                        l8BitData[i] = rm.getByte();
                    }

                }
            }

            // stereo loop
            if (stereo) {
                for (int i = 0; i < sampleLength; i++) {

                    // compresssed
                    if (compresssed) {

                    } // PCM data
                    else {

                        // 16-bit
                        if (sixteenBit) {
                            r16BitData[i] = rm.getShort();
                        } // 8-bit
                        else {
                            r8BitData[i] = rm.getByte();
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
