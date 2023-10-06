/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package module.it.format;

import io.IReadable;
import io.IWritable;
import sound.effects.BitLimiter;
import io.Reader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import sound.compression.SampleCompressor;
import sound.compression.SampleDecompressor;

/**
 *
 * @author Edward Jenkins
 */
public class ITSampleData {

    // constants
    public static final boolean ENDIENESS = true;

    // instance variables
    private boolean signed;
    private boolean sixteenBit;
    private long samplePointer;
    private long sampleLength;
    private boolean stereo;
    private boolean compressed;
    private boolean it15Compressed;
    private byte[] lCompressedData;
    private byte[] l8BitData;
    private short[] l16BitData;
    private byte[] rCompressedData;
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
        this.compressed = compresssed;
        this.it15Compressed = delta;

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
        this.compressed = compresssed;
        this.it15Compressed = delta;

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
            if (compressed) {

                SampleDecompressor sd
                        = new SampleDecompressor(
                                (byte) ((sixteenBit) ? 16 : 8), false, it15Compressed,
                                (int) sampleLength, true);

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
                if (compressed) {

                    SampleDecompressor sd
                            = new SampleDecompressor(
                                    (byte) ((sixteenBit) ? 16 : 8), false, it15Compressed,
                                    (int) sampleLength, true);

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

    // write
    public boolean write(IWritable writer) throws IOException {

        boolean success = true;

        if (compressed) {

            // write left/mono data
            writer.writeBytes(lCompressedData);

            // write right data if stereo
            if (stereo) {
                writer.writeBytes(rCompressedData);
            }
        } else {

            // write left/mono samples
            for (int i = 0; i < sampleLength; i++) {

                // 16-bit
                if (sixteenBit) {
                    writer.writeShort(l16BitData[i]);
                } // 8-bit
                else {
                    writer.writeByte(l8BitData[i]);
                }
            }

            if (stereo) {

                // write r samples
                for (int i = 0; i < sampleLength; i++) {

                    // 16-bit
                    if (sixteenBit) {
                        writer.writeShort(r16BitData[i]);
                    } // 8-bit
                    else {
                        writer.writeByte(r8BitData[i]);
                    }
                }
            }
        }

        return success;
    }

    // length
    public int length() {
        int length = 0;

        if (compressed) {

            length = lCompressedData.length;

            if (stereo) {
                length += rCompressedData.length;
            }

        } else {
            if (sixteenBit) {

                length = l16BitData.length * 2;
            } else {

                length = l8BitData.length;
            }

            if (stereo) {
                length *= 2;
            }
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

    public boolean compressData(boolean it15Compression) throws IOException {
        this.compressed = true;
        this.it15Compressed = it15Compression;
        return compressData();
    }

    public boolean compressData() throws IOException {

        compress(0);

        if (stereo) {
            compress(1);
        }

        return true;
    }

    private boolean compress(int channel) throws IOException {
        long[] sampleData = new long[(int) sampleLength];

        byte bitrate = (sixteenBit) ? (byte) 16 : (byte) 8;

        SampleCompressor compressor = new SampleCompressor(bitrate, false,
                it15Compressed, ENDIENESS);

        switch (channel) {
            default:
                switch (bitrate) {
                    default:
                        for (int i = 0; i < l8BitData.length; i++) {
                            sampleData[i] = l8BitData[i];
                        }
                        break;
                    case 16:
                        for (int i = 0; i < l16BitData.length; i++) {
                            sampleData[i] = l16BitData[i];
                        }
                        break;
                }
                lCompressedData = compressor.compress(sampleData);
                break;
            case 1:
                switch (bitrate) {
                    default:
                        for (int i = 0; i < r8BitData.length; i++) {
                            sampleData[i] = r8BitData[i];
                        }
                        break;
                    case 16:
                        for (int i = 0; i < r16BitData.length; i++) {
                            sampleData[i] = r16BitData[i];
                        }
                        break;
                }
                rCompressedData = compressor.compress(sampleData);
                break;
        }

        return true;
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
