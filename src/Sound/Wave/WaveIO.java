/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sound.Wave;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import static Sound.Wave.WaveSpecs.S_GROUP_ID_DATA;
import static Sound.Wave.WaveSpecs.S_GROUP_ID_FACT;
import static Sound.Wave.WaveSpecs.S_GROUP_ID_FORMAT;
import static Sound.Wave.WaveSpecs.S_GROUP_ID_HEADER;
import static Sound.Wave.WaveSpecs.S_RIFF_TYPE;
import io_stuff.*;
import static io_stuff.IOMethods.LITTLE_ENDIAN;
import Module.IAudioSample;

/**
 *
 * @author Edward Jenkins
 */
public class WaveIO extends WaveSpecs implements IAudioSample {

    // constants
    public static final String WAVE_EXTENSION = ".wav";

    // instnace variables
    private String waveName;        // file
    private String testCode;        // used in reading for testing strings

    // all args constructor
    public WaveIO(int channels, long sampleRate, int bitsPerSample,
            double[] lSampleData, double[] rSampleData, String wavOutput,
            boolean isFloat) {
        super(channels, sampleRate, bitsPerSample, lSampleData, rSampleData,
                isFloat);
        this.waveName = wavOutput + WAVE_EXTENSION;
        setFloatingPoint(isFloat);
        if (isFloat) {
            setWFormatTag(3);
            setDWFormatChunkSize(18);
            setCBSize(0);
            setDWFactChunkSize(4);
            setDWSampleLength(getWChannels() * lSampleData.length);
            setDWFileLength(getDWFormatChunkSize() + getDWDataChunkSize()
                    + getDWFactChunkSize() + 34);
        } else {
            setWFormatTag(W_FORMAT_TAG);
            setDWFormatChunkSize(16);
            setDWFileLength(getDWFormatChunkSize() + getDWDataChunkSize() + 20);
        }

    }

    // Six args constructor
    public WaveIO(int channels, long sampleRate, int bitsPerSample,
            double[] lSampleData, String wavOutput, boolean isFloat) {
        this(channels, sampleRate, bitsPerSample, lSampleData, SAMPLE_DATA,
                wavOutput, isFloat);
        this.waveName = wavOutput + ".wav";
    }

    public WaveIO(String wavInput) {
        super();
        this.waveName = wavInput;

    }

    // writeWav method
    @Override
    public boolean write() throws IOException {

        // initialise the WriteMethods
        WriteMethods wm = new WriteMethods(waveName, LITTLE_ENDIAN);

        // write the header
        // sGroupID
        wm.writeByteString(S_GROUP_ID_HEADER);
        // dwFileLength
        wm.writeInt((int) getDWFileLength());
        // sRiffType
        wm.writeByteString(S_RIFF_TYPE);

        // write the format
        // sGroupID
        wm.writeByteString(S_GROUP_ID_FORMAT);
        // dwChunkSize
        wm.writeInt((int) getDWFormatChunkSize());
        // wFormatTag
        wm.writeShort((short) getWFormatTag());
        // wChannels
        wm.writeShort((short) getWChannels());
        // dwSamplesPerSec
        wm.writeInt((int) getDWSamplesPerSec());
        // dwAvgBytesPerSec
        wm.writeInt((int) getDWAvgBytesPerSec());
        // wBlockAlign
        wm.writeShort((short) getWBlockAlign());
        // wBitsPerSample
        wm.writeShort((short) getWBitsPerSample());

        if (isFloatingPoint()) {
            // cbSize
            wm.writeShort((short) getCBSize());
            // sGroutID
            wm.writeByteString(S_GROUP_ID_FACT);
            // dwFactChunkSize
            wm.writeInt((int) getDWFactChunkSize());
            // dwSampleLength
            wm.writeInt((int) getDWSampleLength());
        }

        // write the data
        // sGroupID
        wm.writeByteString(S_GROUP_ID_DATA);
        // dwChunkSize
        wm.writeInt((int) getDWDataChunkSize());

        // write the datastream
        // mono
        if (getWChannels() == 1) {

            // define point
            int point;
            float semiFinePoint;
            double finePoint;

            for (double lData : getLSampleData()) {

                switch (getWBitsPerSample()) {
                    // if 64 bit
                    case 64:
                        // datapoint
                        finePoint = lData;
                        wm.writeDouble(finePoint);
                        break;
                    // if 32 bit
                    case 32:
                        // datapoint
                        if (isFloatingPoint()) {
                            semiFinePoint = (float) lData;
                            wm.writeFloat(semiFinePoint);
                        } else {
                            point = (int) lData;
                            wm.writeInt(point);
                        }
                        break;
                    // if 24 bit
                    case 24:
                        // datapoint
                        point = (int) lData;
                        wm.writeIntAsTwentyFourBit(point);
                        break;
                    // if 16 bit
                    case 16:
                        // datapoint
                        point = (int) lData;
                        wm.writeShort((short) point);
                        break;
                    // if 8 bit
                    case 8:
                        point = (int) lData + 128;
                        wm.writeByte((byte) point);
                        break;
                    default:
                        throw new IOException();
                }
            }
        } // stereo
        else {
            double lFinePoint, rFinePoint;
            float lSemiFinePoint, rSemiFinePoint;
            int lPoint, rPoint;

            for (int i = 0; i < getLSampleData().length; i++) {

                switch (getWBitsPerSample()) {
                    // if 64 bit
                    case 64:
                        // Left datapoint
                        lFinePoint = getLSampleData()[i];
                        wm.writeDouble(lFinePoint);
                        // Right datapoint
                        rFinePoint = getRSampleData()[i];
                        wm.writeDouble(rFinePoint);
                        break;
                    // if 32 bit
                    case 32:
                        if (isFloatingPoint()) {
                            // left datapoint
                            lSemiFinePoint = (float) getLSampleData()[i];
                            wm.writeFloat(lSemiFinePoint);
                            // right datapoint
                            rSemiFinePoint = (float) getRSampleData()[i];
                            wm.writeFloat(rSemiFinePoint);
                        } else {
                            // left datapoint
                            lPoint = (int) getLSampleData()[i];
                            wm.writeInt(lPoint);
                            // right datapoint
                            rPoint = (int) getRSampleData()[i];
                            wm.writeInt(rPoint);
                        }
                        break;
                    // if 24 bit
                    case 24:
                        // Left datapoint
                        lPoint = (int) getLSampleData()[i];
                        wm.writeIntAsTwentyFourBit(lPoint);
                        // Right datapoint
                        rPoint = (int) getRSampleData()[i];
                        wm.writeIntAsTwentyFourBit(rPoint);
                        break;
                    // if 16 bit
                    case 16:
                        // Left datapoint
                        lPoint = (int) getLSampleData()[i];
                        wm.writeShort((short) lPoint);
                        // Left datapoint
                        rPoint = (int) getRSampleData()[i];
                        wm.writeShort((short) rPoint);
                        break;
                    // if 8 bit
                    case 8:
                        // Left datapoint
                        lPoint = (int) getLSampleData()[i] + 128;
                        wm.writeByte((byte) lPoint);
                        rPoint = (int) getRSampleData()[i] + 128;
                        wm.writeByte((byte) rPoint);
                        break;
                    default:
                        throw new IOException();
                }
            }
        }

        // close file
        wm.close();
        return true;
    }

    // read method
    @Override
    public boolean read() throws IOException {

        // set up read methods
        ReadMethods rm = new ReadMethods(waveName, LITTLE_ENDIAN);

        // read the header
        // sGroupID
        testCode = rm.getByteString(4);
        if (!testCode.equals(S_GROUP_ID_HEADER)) {
            throw new IOException("Group ID Header is invalid!");
        }
        System.out.println("Group ID Header: " + testCode);
        // dwFileLength
        setDWFileLength(rm.getUIntAsLong());
        System.out.println("File length: " + getDWFileLength());
        // sRiffType
        testCode = rm.getByteString(4);
        if (!testCode.equals(S_RIFF_TYPE)) {
            throw new IOException("Riff type is invalid!");
        }
        System.out.println("Riff type: " + testCode);
        // read the format
        // sGroupIDFormat
        testCode = rm.getByteString(4);
        if (!testCode.equals(S_GROUP_ID_FORMAT)) {
            throw new IOException("Group ID Format is invalid!");
        }
        System.out.println("Group ID Format: " + testCode);
        // dwChunkSize
        setDWFormatChunkSize(rm.getUIntAsLong());
        System.out.println("Format Chunk size: " + getDWFormatChunkSize());
        // wFormatTag
        setWFormatTag(rm.getUShortAsInt());
        System.out.println("Format Tag: " + getWFormatTag());
        // check if it is float data
        if (getWFormatTag() == 3) {
            setFloatingPoint(true);
        }
        // wChannels
        setWChannels(rm.getUShortAsInt());
        System.out.println("Channels: " + getWChannels());
        // dwSamplesPerSec
        setDWSamplesPerSec(rm.getUIntAsLong());
        System.out.println("Sample rate: " + getDWSamplesPerSec());
        // dwAvgBytesPerSec
        setDWAvgBytesPerSec(rm.getUIntAsLong());
        System.out.println("Average Bytes per Second: " + getDWAvgBytesPerSec());
        // wBlockAlign
        setWBlockAlign(rm.getUShortAsInt());
        System.out.println("Block align: " + getWBlockAlign());
        // wBitsPerSample
        setWBitsPerSample(rm.getUShortAsInt());
        System.out.println("Bits Per Sample: " + getWBitsPerSample());

        // extension chunk
        switch ((int) getDWFormatChunkSize()) {
            case 18:
                // cbSize
                setCBSize(rm.getUShortAsInt());
                System.out.println("Extra Chunk Size: " + getCBSize());
                // sGroupIDFact
                testCode = rm.getByteString(4);
                if (!testCode.equals(S_GROUP_ID_FACT)) {
                    throw new IOException("Group ID Fact is invalid!");
                }
                System.out.println("Group ID Fact: " + testCode);
                break;
            case 40:
                // cbSize
                setCBSize(rm.getUShortAsInt());
                System.out.println("Extra Chunk Size: " + getCBSize());
                // wValidBitsPerSample
                setWValidBitsPerSample(rm.getUShortAsInt());
                System.out.println("Extra Chunk Size: "
                        + getWValidBitsPerSample());
                break;
            default:
                break;
        }

        // read the data
        // sGroupIDData
        testCode = rm.getByteString(4);
        if (!testCode.equals(S_GROUP_ID_DATA)) {
            throw new IOException("Group ID Data is invalid!");
        }
        System.out.println("Group ID Data: " + testCode);
        // dwChunkSize
        setDWDataChunkSize(rm.getUIntAsLong());
        System.out.println("Data Chunk Size: " + getDWDataChunkSize());
        System.out.println("Datastream: ");

        // define the arrays to hold the data
        setRSampleData(new double[(int) getDWDataChunkSize() / 4 - 1]);

        // read the datastream
        // mono
        if (getWChannels() == 1) {

            // define array to hold the channel data            
            double[] lSampleData = new double[(int) getDWDataChunkSize()
                    / (getWBitsPerSample() / 8) - 1];

            int point;
            float semiFinePoint;

            for (int i = 0; i < lSampleData.length; i++) {

                // keep track of array
                switch (getWBitsPerSample()) {
                    // if 32 bit
                    case 32:
                        if (isFloatingPoint()) {
                            semiFinePoint = rm.getFloat();
                            lSampleData[i] = (double) semiFinePoint;
                            break;
                        } else {
                            point = rm.getInt();
                            lSampleData[i] = point;
                        }
                    // if 24 bit
                    case 24:
                        point = rm.get24BitInt();
                        lSampleData[i] = point;
                        break;
                    // if 16 bit:
                    case 16:
                        point = rm.getUShortAsInt();
                        lSampleData[i] = point;
                        break;
                    // if 8 bit    
                    default:
                        point = rm.getUByteAsShort() - 128;
                        lSampleData[i] = point;
                        break;
                }
            }

            setLSampleData(lSampleData);
        } else {

            double[] lSampleData = new double[(int) getDWDataChunkSize()
                    / (getWBitsPerSample() / 8) - 1];
            double[] rSampleData = new double[(int) getDWDataChunkSize()
                    / (getWBitsPerSample() / 8) - 1];

            int lPoint, rPoint;

            for (int i = 0; i < lSampleData.length; i++) {

                switch (getWBitsPerSample()) {
                    case 64:
                        break;
                    case 32:
                        break;
                    case 24:
                        break;
                    case 16:
                        // Left
                        lPoint = rm.getShort();
                        lSampleData[i] = lPoint;

                        // Right
                        rPoint = rm.getShort();
                        rSampleData[i] = rPoint;
                        break;
                    case 8:
                        // Left
                        lPoint = rm.getUByteAsShort() - 128;
                        lSampleData[i] = lPoint;

                        // Right
                        rPoint = rm.getUByteAsShort() - 128;
                        rSampleData[i] = rPoint;
                        break;
                }
            }
            setLSampleData(lSampleData);
            setRSampleData(lSampleData);
        }

        // close file
        rm.close();
        return true;
    }

    @Override
    public double[] getLData() {
        return this.getLSampleData();
    }

    @Override
    public double[] getRData() {
        return this.getRSampleData();
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
}
