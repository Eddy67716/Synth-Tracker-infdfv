/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sound.formats.wave;

import static io.IOMethods.LITTLE_ENDIAN;
import io.Reader;
import io.Writer;
import java.io.IOException;
import java.io.FileNotFoundException;
import module.IAudioSample;
import sound.formats.SampleData;
import static sound.formats.wave.WaveSpecs.S_GROUP_ID_DATA;
import static sound.formats.wave.WaveSpecs.S_GROUP_ID_FACT;
import static sound.formats.wave.WaveSpecs.S_GROUP_ID_FORMAT;
import static sound.formats.wave.WaveSpecs.S_GROUP_ID_HEADER;
import static sound.formats.wave.WaveSpecs.S_RIFF_TYPE;

/**
 *
 * @author Edward Jenkins
 */
public class WaveFile extends WaveSpecs implements IAudioSample {

    // constants
    public static final String WAVE_EXTENSION = ".wav";

    // instnace variables
    private String waveName;        // file
    private String testCode;        // used in reading for testing strings

    // all args constructor
    public WaveFile(int channels, long sampleRate, int bitsPerSample,
            double[][] sampleData, String wavOutput,
            boolean isFloat) {
        super(channels, sampleRate, bitsPerSample, sampleData,
                isFloat);
        this.waveName = wavOutput + ".wav";
        setFloatingPoint(isFloat);
        if (isFloat) {
            setWFormatTag(3);
            setDWFormatChunkSize(18);
            setCBSize(0);
            setDWFactChunkSize(4);
            setDWSampleLength(getWChannels() * sampleData[0].length);
            setDWFileLength(getDWFormatChunkSize() + getDWDataChunkSize()
                    + getDWFactChunkSize() + 34);
        } else {
            setWFormatTag(W_FORMAT_TAG);
            setDWFormatChunkSize(16);
            setDWFileLength(getDWFormatChunkSize() + getDWDataChunkSize() + 20);
        }

    }

    // no args constructor
    public WaveFile(String wavInput) {
        super();
        this.waveName = wavInput;
    }

    // writeWav method
    @Override
    public boolean write() throws IOException {

        // initialise the Writer
        Writer w = new Writer(waveName, LITTLE_ENDIAN);

        // write the header
        // sGroupID
        w.writeByteString(S_GROUP_ID_HEADER);
        // dwFileLength
        w.writeInt((int) getDWFileLength());
        // sRiffType
        w.writeByteString(S_RIFF_TYPE);

        // write the format
        // sGroupID
        w.writeByteString(S_GROUP_ID_FORMAT);
        // dwChunkSize
        w.writeInt((int) getDWFormatChunkSize());
        // wFormatTag
        w.writeShort((short) getWFormatTag());
        // wChannels
        w.writeShort((short) getWChannels());
        // dwSamplesPerSec
        w.writeInt((int) getDWSamplesPerSec());
        // dwAvgBytesPerSec
        w.writeInt((int) getDWAvgBytesPerSec());
        // wBlockAlign
        w.writeShort((short) getWBlockAlign());
        // wBitsPerSample
        w.writeShort((short) getWBitsPerSample());

        if (isFloatingPoint()) {
            // cbSize
            w.writeShort((short) getCBSize());
            // sGroutID
            w.writeByteString(S_GROUP_ID_FACT);
            // dwFactChunkSize
            w.writeInt((int) getDWFactChunkSize());
            // dwSampleLength
            w.writeInt((int) getDWSampleLength());
        }

        // write the data
        // sGroupID
        w.writeByteString(S_GROUP_ID_DATA);
        // dwChunkSize
        w.writeInt((int) getDWDataChunkSize());

        // write the datastream
        // define point
        double[] points;

        int sampleCount = getSampleData().getSampleCount();

        switch (getWBitsPerSample()) {
            // if 64 bit
            case 64:
                // sample
                for (int i = 0; i < sampleCount; i++) {
                    // get channel samples from data
                    points = getSampleData().outputPoints(i);

                    // output data
                    for (double point : points) {
                        w.writeDouble(point);
                    }
                }
                break;
            // if 32 bit
            case 32:
                if (isFloatingPoint()) {
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
                        points = getSampleData().outputPoints(i);

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
                    points = getSampleData().outputPoints(i);

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
                    points = getSampleData().outputPoints(i);

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
                    points = getSampleData().outputPoints(i);

                    // output data
                    for (double point : points) {
                        w.writeByte((byte) point);
                    }
                }
                break;
            default:
                throw new IOException("Invalid bitrate! ");
        }

        // close file
        w.close();
        return true;
    }

    // read method
    @Override
    public boolean read() throws IOException, IllegalArgumentException {

        // set up read methods
        Reader r = new Reader(waveName, LITTLE_ENDIAN);

        // read the header
        // sGroupID
        testCode = r.getByteString(4);
        if (!testCode.equals(S_GROUP_ID_HEADER)) {
            throw new IllegalArgumentException("Group ID Header is invalid!");
        }
        System.out.println("Group ID Header: " + testCode);
        // dwFileLength
        setDWFileLength(r.getUIntAsLong());
        System.out.println("File length: " + getDWFileLength());
        // sRiffType
        testCode = r.getByteString(4);
        if (!testCode.equals(S_RIFF_TYPE)) {
            throw new IOException("Riff type is invalid!");
        }
        System.out.println("Riff type: " + testCode);
        // read the format
        // sGroupIDFormat
        testCode = r.getByteString(4);
        if (!testCode.equals(S_GROUP_ID_FORMAT)) {
            throw new IOException("Group ID Format is invalid!");
        }
        System.out.println("Group ID Format: " + testCode);
        // dwChunkSize
        setDWFormatChunkSize(r.getUIntAsLong());
        System.out.println("Format Chunk size: " + getDWFormatChunkSize());
        // wFormatTag
        setWFormatTag(r.getUShortAsInt());
        System.out.println("Format Tag: " + getWFormatTag());
        // check if it is float data
        if (getWFormatTag() == 3) {
            setFloatingPoint(true);
        }
        // wChannels
        setWChannels(r.getUShortAsInt());
        System.out.println("Channels: " + getWChannels());
        // dwSamplesPerSec
        setDWSamplesPerSec(r.getUIntAsLong());
        System.out.println("Sample rate: " + getDWSamplesPerSec());
        // dwAvgBytesPerSec
        setDWAvgBytesPerSec(r.getUIntAsLong());
        System.out.println("Average Bytes per Second: " + getDWAvgBytesPerSec());
        // wBlockAlign
        setWBlockAlign(r.getUShortAsInt());
        System.out.println("Block align: " + getWBlockAlign());
        // wBitsPerSample
        setWBitsPerSample(r.getUShortAsInt());
        System.out.println("Bits Per Sample: " + getWBitsPerSample());

        // extension chunk
        switch ((int) getDWFormatChunkSize()) {
            case 18:
                // cbSize
                setCBSize(r.getUShortAsInt());
                System.out.println("Extra Chunk Size: " + getCBSize());
                // sGroupIDFact
                testCode = r.getByteString(4);
                if (!testCode.equals(S_GROUP_ID_FACT)) {
                    throw new IOException("Group ID Fact is invalid!");
                }
                System.out.println("Group ID Fact: " + testCode);
                break;
            case 40:
                // cbSize
                setCBSize(r.getUShortAsInt());
                System.out.println("Extra Chunk Size: " + getCBSize());
                // wValidBitsPerSample
                setWValidBitsPerSample(r.getUShortAsInt());
                System.out.println("Extra Chunk Size: "
                        + getWValidBitsPerSample());
                break;
            default:
                break;
        }

        // read the data
        // sGroupIDData
        testCode = r.getByteString(4);
        if (!testCode.equals(S_GROUP_ID_DATA)) {
            throw new IOException("Group ID Data is invalid!");
        }
        System.out.println("Group ID Data: " + testCode);
        // dwChunkSize
        setDWDataChunkSize(r.getUIntAsLong());
        System.out.println("Data Chunk Size: " + getDWDataChunkSize());
        System.out.println("Datastream: ");

        // get the sample count
        int sampleCount = ((int) getDWDataChunkSize() / (getWBitsPerSample()
                / 8) / getWChannels());

        // read the datastream
        // define the object to hold the channel data            
        setSampleData(new SampleData((byte) getWBitsPerSample(),
                (getWBitsPerSample() != 8 && !isFloatingPoint()),
                isFloatingPoint(), (byte) getWChannels(), sampleCount)
        );

        double[] points = new double[getWChannels()];

        // read sample data
        switch (getWBitsPerSample()) {
            case 64:
                // sample
                for (int i = 0; i < sampleCount; i++) {
                    // channel
                    for (int j = 0; j < getWChannels(); j++) {
                        points[j] = r.getDouble();
                    }

                    // add channel samples to sample data
                    getSampleData().inputPoints(points);
                }
                break;
            // if 32 bit
            case 32:
                if (isFloatingPoint()) {
                    // sample
                    for (int i = 0; i < sampleCount; i++) {
                        // channel
                        for (int j = 0; j < getWChannels(); j++) {
                            points[j] = r.getFloat();
                        }

                        // add channel samples to sample data
                        getSampleData().inputPoints(points);
                    }
                } else {
                    // sample
                    for (int i = 0; i < sampleCount; i++) {
                        // channel
                        for (int j = 0; j < getWChannels(); j++) {
                            points[j] = r.getInt();
                        }

                        // add channel samples to sample data
                        getSampleData().inputPoints(points);
                    }
                }
                break;
            // if 24 bit
            case 24:
                // sample
                for (int i = 0; i < sampleCount; i++) {
                    // channel
                    for (int j = 0; j < getWChannels(); j++) {
                        points[j] = r.get24BitInt();
                    }

                    // add channel samples to sample data
                    getSampleData().inputPoints(points);
                }
                break;
            // if 16 bit:
            case 16:
                // sample
                for (int i = 0; i < sampleCount; i++) {
                    // channel
                    for (int j = 0; j < getWChannels(); j++) {
                        points[j] = r.getShort();
                    }

                    // add channel samples to sample data
                    getSampleData().inputPoints(points);
                }
                break;
            // if 8 bit    
            default:
                // sample
                for (int i = 0; i < sampleCount; i++) {
                    // channel
                    for (int j = 0; j < getWChannels(); j++) {
                        points[j] = r.getUByteAsShort();
                    }

                    // add channel samples to sample data
                    getSampleData().inputPoints(points);
                }
                break;
        }

        // close file
        r.close();
        return true;
    }

    @Override
    public double[] getLData() {
        double[] lData = new double[getSampleLength()];
        
        double[][] normalisedSampleData 
                = getSampleData().getNormalisedSampleData();
        
        for (int i = 0; i < lData.length; i++) {
            lData[i] = normalisedSampleData[i][0];
        }
        
        return lData;
    }

    @Override
    public double[] getRData() {
        double[] rData = new double[getSampleLength()];
        
        double[][] normalisedSampleData 
                = getSampleData().getNormalisedSampleData();
        
        for (int i = 0; i < rData.length; i++) {
            rData[i] = normalisedSampleData[i][1];
        }
        
        return rData;
    }

    @Override
    public String getSampleName() {

        return waveName.substring(0, waveName.length()
                - WAVE_EXTENSION.length());
    }

    @Override
    public int getC5Speed() {
        return (int) this.getDWSamplesPerSec();
    }

    @Override
    public short getBitRate() {
        return (short) this.getWBitsPerSample();
    }

    @Override
    public int getSampleLength() {
        return (int) getDWSampleLength();
    }

    @Override
    public boolean isSigned() {
        return (getWBitsPerSample() > 8);
    }

    @Override
    public boolean isStereo() {
        return (getWChannels() >= 2);
    }

    @Override
    public String getDOSFileName() {
        return getSampleName().substring(0, 12);
    }

    @Override
    public short getDefaultVolume() {
        return 64;
    }

    @Override
    public byte getGlobalVolume() {
        return 64;
    }

    @Override
    public byte getPanValue() {
        return 32;
    }

    @Override
    public boolean isPanning() {
        return false;
    }

    @Override
    public boolean isLooped() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean isSustainLooped() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean isPingPongLooped() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean isPingPongSustainLooped() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public long getLoopBeginning() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public long getLoopEnd() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public long getSustainLoopBeginning() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public long getSustainLoopEnd() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public double getFullVibratoSpeed() {
        return 0;
    }

    @Override
    public double getFullVibratoDepth() {
        return 0;
    }

    @Override
    public byte getVibratoWaveform() {
        return 0;
    }

    @Override
    public double getVibratoDelay() {
        return 0;
    }

    @Override
    public void setSampleName(String sampleName) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void setDOSFileName(String dosFileName) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void setC5Speed(int c5Speed) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void setBitRate(short bitRate) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void setSigned(boolean signed) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void setStereo(boolean stereo) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void setDefaultVolume(short defaultVolume) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void setGlobalVolume(byte globalVolume) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void setPanValue(byte panValue) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void setPanning(boolean panning) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void setLooped(boolean looped) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void setSustainLooped(boolean sustainLooped) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void setPingPongLooped(boolean pingPongLooped) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void setPingPongSustainLooped(boolean pingPongSustainLooped) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void setLoopBeginning(long loopBeginning) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void setLoopEnd(long loopEnd) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void setSustainLoopBeginning(long sustainLoopBeginning) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void setSustainLoopEnd(long sustainLoopEnd) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void setLData(double[] lData) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void setRData(double[] rData) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void setVibratoSpeed(double vibratoSpeed) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void setFullVibratoDepth(double vibratoDepth) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void setVibratoWaveform(byte vibratoWaveform) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void setVibratoDelay(double vibratoDelay) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int length() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
