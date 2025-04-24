/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sound.formats.riff.wave;

import sound.formats.riff.RiffChunk;
import io.IReadable;
import io.IWritable;
import java.io.FileNotFoundException;
import java.io.IOException;
import sound.formats.AudioSampleData;

/**
 *
 * @author eddy6
 */
public class WaveDataChunk extends RiffChunk {

    // constants
    public static final String S_GROUP_ID = "data";   // DATA

    // instance variable
    private AudioSampleData sampleData;

    // all args constructor
    public WaveDataChunk(WaveFormatChunk waveFormat, double[][] sampleData) {
        this();
        if (sampleData != null && sampleData.length != 0) {
            setChunkSize(waveFormat.getWBlockAlign() * sampleData.length);

            int bitRate = waveFormat.getWBitsPerSample();
            boolean floatingPoint = waveFormat.isFloatingPoint();
            byte channels = (byte) waveFormat.getWChannels();

            this.sampleData = new AudioSampleData(
                    (byte) bitRate, (bitRate != 8 && !floatingPoint),
                    floatingPoint, (byte) channels, ((int) getChunkSize()
                    / (bitRate / 8) / channels)
            );
            for (double[] sampleData1 : sampleData) {
                this.sampleData.inputSamplePoints(sampleData1);
            }
        }
    }

    public WaveDataChunk(WaveFormatChunk waveFormat,
            AudioSampleData sampleData) {
        this();
        setChunkSize(waveFormat.getWBlockAlign() * sampleData.getSampleCount());
        this.sampleData = sampleData;
    }

    // 1 arg constructor
    public WaveDataChunk(WaveFormatChunk waveFormat) {
        this(waveFormat, new double[0][0]);

        int bitrate = waveFormat.getWBitsPerSample();
        int channels = waveFormat.getWChannels();
        boolean floatingPoint = waveFormat.isFloatingPoint();

        // define the object to hold the channel data            
        sampleData = new AudioSampleData((byte) bitrate,
                (bitrate != 8 && !floatingPoint),
                floatingPoint, (byte) channels);
    }

    // no args constructor
    public WaveDataChunk() {
        super(S_GROUP_ID);
    }

    // getter
    public AudioSampleData getSampleData() {
        return sampleData;
    }

    // setter
    public void setSampleData(AudioSampleData sampleData) {
        this.sampleData = sampleData;
    }

    @Override
    public boolean read(IReadable r) throws IOException, FileNotFoundException, IllegalArgumentException {
        boolean read = super.read(r);

        byte channels = sampleData.getChannels();
        boolean floatingPoint = sampleData.isFloating();
        double[] points = new double[channels];

        int sampleCount = ((int) getChunkSize()
                / (sampleData.getBitrate() / 8) / channels);

        // set sample count and also initialise the array
        sampleData.setSampleCount(sampleCount);

        // read sample data
        switch (sampleData.getBitrate()) {
            case 64:
                // sample
                for (int i = 0; i < sampleCount; i++) {
                    // channel
                    for (int j = 0; j < channels; j++) {
                        points[j] = r.getDouble();
                    }

                    // add channel samples to sample data
                    getSampleData().inputSamplePoints(points);
                }
                break;
            // if 32 bit
            case 32:
                if (floatingPoint) {
                    // sample
                    for (int i = 0; i < sampleCount; i++) {
                        // channel
                        for (int j = 0; j < channels; j++) {
                            points[j] = r.getFloat();
                        }

                        // add channel samples to sample data
                        getSampleData().inputSamplePoints(points);
                    }
                } else {
                    // sample
                    for (int i = 0; i < sampleCount; i++) {
                        // channel
                        for (int j = 0; j < channels; j++) {
                            points[j] = r.getInt();
                        }

                        // add channel samples to sample data
                        getSampleData().inputSamplePoints(points);
                    }
                }
                break;
            // if 24 bit
            case 24:
                // sample
                for (int i = 0; i < sampleCount; i++) {
                    // channel
                    for (int j = 0; j < channels; j++) {
                        points[j] = r.get24BitInt();
                    }

                    // add channel samples to sample data
                    getSampleData().inputSamplePoints(points);
                }
                break;
            // if 16 bit:
            case 16:
                // sample
                for (int i = 0; i < sampleCount; i++) {
                    // channel
                    for (int j = 0; j < channels; j++) {
                        points[j] = r.getShort();
                    }

                    // add channel samples to sample data
                    getSampleData().inputSamplePoints(points);
                }
                break;
            // if 8 bit    
            default:
                // sample
                for (int i = 0; i < sampleCount; i++) {
                    // channel
                    for (int j = 0; j < channels; j++) {
                        points[j] = r.getUByteAsShort();
                    }

                    // add channel samples to sample data
                    getSampleData().inputSamplePoints(points);
                }
                break;
        }

        setFullyRead(read);

        return read;
    }

    @Override
    public boolean write(IWritable w) throws IOException {
        boolean written = super.write(w);

        // write the datastream
        // define point
        double[] points;

        int sampleCount = sampleData.getSampleCount();

        switch (sampleData.getBitrate()) {
            // if 64 bit
            case 64:
                // sample
                for (int i = 0; i < sampleCount; i++) {
                    // get channel samples from data
                    points = sampleData.outputPoints(i);

                    // output data
                    for (double point : points) {
                        w.writeDouble(point);
                    }
                }
                break;
            // if 32 bit
            case 32:
                if (sampleData.isFloating()) {
                    // sample
                    for (int i = 0; i < sampleCount; i++) {
                        // get channel samples from data
                        points = getSampleData().outputPoints(i);

                        // output data
                        for (double point : points) {
                            w.writeFloat((float) point);
                        }
                    }
                } else {
                    // sample
                    for (int i = 0; i < sampleCount; i++) {
                        // get channel samples from data
                        points = sampleData.outputPoints(i);

                        // output data
                        for (double point : points) {
                            w.writeInt((int) point);
                        }
                    }
                }
                break;
            // if 24 bit
            case 24:
                // sample
                for (int i = 0; i < sampleCount; i++) {
                    // get channel samples from data
                    points = sampleData.outputPoints(i);

                    // output data
                    for (double point : points) {
                        w.writeIntAsTwentyFourBit((int) point);
                    }
                }
                break;
            // if 16 bit
            case 16:
                // sample
                for (int i = 0; i < sampleCount; i++) {
                    // get channel samples from data
                    points = sampleData.outputPoints(i);

                    // output data
                    for (double point : points) {
                        w.writeShort((short) point);
                    }
                }
                break;
            // if 8 bit
            case 8:
                // sample
                for (int i = 0; i < sampleCount; i++) {
                    // get channel samples from data
                    points = sampleData.outputPoints(i);

                    // output data
                    for (double point : points) {
                        w.writeByte((byte) point);
                    }
                }
                break;
            default:
                throw new IOException("Invalid bitrate! ");
        }

        return written;
    }
}
