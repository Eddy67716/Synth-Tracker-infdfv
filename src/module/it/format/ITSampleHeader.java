/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package module.it.format;

import io.IOMethods;
import io.IReadable;
import io.IWritable;
import io.Reader;
import io.Writer;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import module.IAudioSample;
import module.ISavableModule;

/**
 *
 * @author Edward Jenkins
 */
public class ITSampleHeader implements IAudioSample, ISavableModule {

    // constants
    public static final String SAMPLE_HEADER = "IMPS";
    public static final byte SAMPLE_HEADER_LENGTH = 80;

    // instance variables
    // name of file
    private String fileName;
    // where to start reading the file
    private long offsetToSample;
    // used to check the header is valid
    private String testHeader;
    // 12 character file name
    private String dosFileName;
    // global volume 0-64
    private byte globalVolume;
    // flags
    private short flags;
    // flag 0
    private boolean sampleAssociatedWithHeader;
    // flag 1
    private boolean sixteenBit;
    // flag 2
    private boolean stereo;
    // flag 3
    private boolean compressed;
    // flag 4
    private boolean looped;
    // flag 5
    private boolean sustainLooped;
    // flag 6
    private boolean pingPongLooped;
    // flag 7
    private boolean pingPongSustainLooped;
    // 0-64
    private byte defaultVolume;
    // actual name of sample
    private String sampleName;
    // flags for conversion
    private short convertFlags;
    // flag 0
    private boolean signed;
    // flag 2
    private boolean delta;
    // default pan for sample files
    private byte defaultPan;
    private byte panValue;
    private boolean panning;
    // length of sample in samples
    private long length;
    // pointer to loop beginning sample
    private long loopBeginning;
    // pointer to loop end
    private long loopEnd;
    // speed of middle C
    private int c5Speed;
    // pointer to sustain loop beginning
    private long sustainLoopBeginning;
    // pointer to sustain loop end
    private long sustainLoopEnd;
    // offset to sample data
    private long samplePointer;
    // speed of vibrato
    private byte vibratoSpeed;
    // depth of vibrato
    private byte vibratoDepth;
    // 0 is sine, 1 is falling sawtooth, 2 is square and 3 is random
    private byte vibratoWaveform;
    // rate that vibrato increases at begining
    private short vibratoRate;
    // sample cache
    private ITSampleCache sampleCache;
    // wraps sample data and normalises it to -1 to 1
    private ITSampleData sampleData;

    // constructor
    public ITSampleHeader(String file, long offsetToSampleHeader) {
        this.fileName = file;
        this.offsetToSample = offsetToSampleHeader;
    }

    // sample file constructor
    public ITSampleHeader(IAudioSample soundSample) {
        File f = new File(soundSample.getSampleName());
        sampleName = f.getName();
        fileName = sampleName.substring(0, 12);
        globalVolume = 64;
        stereo = soundSample.isStereo();
        compressed = false;
        looped = false;
        sustainLooped = false;
        pingPongLooped = false;
        pingPongSustainLooped = false;
        sixteenBit = (soundSample.getBitRate() >= 16);
        defaultVolume = globalVolume;
        signed = true;
        defaultPan = 0;
        length = soundSample.getSampleLength();
        loopBeginning = 0;
        loopEnd = 0;
        c5Speed = soundSample.getC5Speed();
        sustainLoopBeginning = 0;
        sustainLoopEnd = 0;
        vibratoSpeed = 0;
        vibratoDepth = 0;
        vibratoWaveform = 0;

        // set the data
        sampleData = new ITSampleData(signed, sixteenBit, stereo, length,
                compressed, delta, soundSample.getLData(),
                soundSample.getRData());
    }

    // getters
    public String getFileName() {
        return fileName;
    }

    public long getOffsetToSample() {
        return offsetToSample;
    }

    @Override
    public String getDOSFileName() {
        return dosFileName;
    }

    @Override
    public byte getGlobalVolume() {
        return globalVolume;
    }

    public short getFlags() {
        return flags;
    }

    public boolean isSampleAssociatedWithHeader() {
        return sampleAssociatedWithHeader;
    }

    public boolean isSixteenBit() {
        return sixteenBit;
    }

    @Override
    public boolean isStereo() {
        return stereo;
    }

    public boolean isCompressed() {
        return compressed;
    }

    @Override
    public boolean isLooped() {
        return looped;
    }

    @Override
    public boolean isSustainLooped() {
        return sustainLooped;
    }

    @Override
    public boolean isPingPongLooped() {
        return pingPongLooped;
    }

    @Override
    public boolean isPingPongSustainLooped() {
        return pingPongSustainLooped;
    }

    @Override
    public short getDefaultVolume() {
        return defaultVolume;
    }

    @Override
    public String getSampleName() {
        return sampleName;
    }

    @Override
    public boolean isSigned() {
        return signed;
    }

    public boolean isDelta() {
        return delta;
    }

    @Override
    public byte getPanValue() {
        return panValue;
    }

    @Override
    public boolean isPanning() {
        return panning;
    }

    public long getLength() {
        return length;
    }

    @Override
    public int getSampleLength() {
        return (int) getLength();
    }

    @Override
    public long getLoopBeginning() {
        return loopBeginning;
    }

    @Override
    public long getLoopEnd() {
        return loopEnd;
    }

    @Override
    public int getC5Speed() {
        return c5Speed;
    }

    @Override
    public long getSustainLoopBeginning() {
        return sustainLoopBeginning;
    }

    @Override
    public long getSustainLoopEnd() {
        return sustainLoopEnd;
    }

    public long getSamplePointer() {
        return samplePointer;
    }

    @Override
    public double getFullVibratoSpeed() {
        return getVibratoSpeed();
    }

    public byte getVibratoSpeed() {
        return vibratoSpeed;
    }

    @Override
    public double getFullVibratoDepth() {
        return getVibratoDepth();
    }

    public byte getVibratoDepth() {
        return vibratoDepth;
    }

    @Override
    public byte getVibratoWaveform() {
        return vibratoWaveform;
    }

    @Override
    public double getVibratoDelay() {
        return getVibratoRate();
    }

    public short getVibratoRate() {
        return vibratoRate;
    }

    public ITSampleCache getSampleCache() {
        return sampleCache;
    }

    public ITSampleData getSampleData() {
        return sampleData;
    }

    // setters
    @Override
    public void setSampleName(String sampleName) {
        this.sampleName = sampleName;
    }

    @Override
    public void setDOSFileName(String dosFileName) {
        this.dosFileName = IOMethods.setStringToNullLength(dosFileName, 12);
    }

    @Override
    public void setC5Speed(int c5Speed) {
        this.c5Speed = c5Speed;
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
        this.stereo = stereo;
    }

    @Override
    public void setDefaultVolume(short defaultVolume) {
        this.defaultVolume = (byte) defaultVolume;
    }

    @Override
    public void setGlobalVolume(byte globalVolume) {
        this.globalVolume = globalVolume;
    }

    @Override
    public void setPanValue(byte panValue) {
        this.panValue = panValue;
    }

    @Override
    public void setPanning(boolean panning) {
        this.panning = panning;
    }

    @Override
    public void setLooped(boolean looped) {
        this.looped = looped;
    }

    @Override
    public void setSustainLooped(boolean sustainLooped) {
        this.sustainLooped = sustainLooped;
    }

    @Override
    public void setPingPongLooped(boolean pingPongLooped) {
        this.pingPongLooped = pingPongLooped;
    }

    @Override
    public void setPingPongSustainLooped(boolean pingPongSustainLooped) {
        this.pingPongSustainLooped = pingPongSustainLooped;
    }

    @Override
    public void setLoopBeginning(long loopBeginning) {
        this.loopBeginning = loopBeginning;
    }

    @Override
    public void setLoopEnd(long loopEnd) {
        this.loopEnd = loopEnd;
    }

    @Override
    public void setSustainLoopBeginning(long sustainLoopBeginning) {
        this.sustainLoopBeginning = sustainLoopBeginning;
    }

    @Override
    public void setSustainLoopEnd(long sustainLoopEnd) {
        this.sustainLoopEnd = sustainLoopEnd;
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
        this.vibratoSpeed = (byte)vibratoSpeed;
    }

    @Override
    public void setFullVibratoDepth(double vibratoDepth) {
        this.vibratoDepth = (byte)vibratoDepth;
    }

    @Override
    public void setVibratoWaveform(byte vibratoWaveform) {
        this.vibratoWaveform = vibratoWaveform;
    }

    @Override
    public void setVibratoDelay(double vibratoDelay) {
        this.vibratoRate = (short)vibratoDelay;
    }

    // read method
    @Override
    public boolean read() throws IOException, FileNotFoundException,
            IllegalArgumentException {

        // read methods
        Reader reader = new Reader(fileName, true);

        // set input stream to read at offset
        reader.skipBytes(offsetToSample);

        boolean success = read(reader);

        return success;
    }
    
    @Override
    public boolean read(IReadable r) throws IOException, FileNotFoundException, IllegalArgumentException {
        // success boolean
        boolean success = true;

        // sample header
        testHeader = r.getByteString(4);

        if (!testHeader.equals(SAMPLE_HEADER)) {
            throw new IllegalArgumentException("Header is invalid! ");
        }

        // dos filename
        dosFileName = r.getByteString(12);

        // skip one byte
        r.getByte();

        // global volume
        globalVolume = r.getByte();

        // flags
        flags = r.getByte();

        // sample associated with header
        sampleAssociatedWithHeader = (flags & 1) == 1;

        // 8/16 bit
        sixteenBit = (flags & 2) == 2;

        // stereo or mono
        stereo = (flags & 4) == 4;

        // compresssion
        compressed = (flags & 8) == 8;

        // loop
        looped = (flags & 16) == 16;

        // sustain loop
        sustainLooped = (flags & 32) == 32;

        // ping poing loop
        pingPongLooped = (flags & 64) == 64;

        // sustain ping pong loop
        pingPongSustainLooped = (flags & 128) == 128;

        // default volume
        defaultVolume = r.getByte();

        // sample name
        sampleName = r.getByteString(26);

        // convert
        convertFlags = r.getUByteAsShort();

        // signed
        signed = (convertFlags & 1) == 1;

        // delta values
        delta = (convertFlags & 4) == 4;

        // default pan
        defaultPan = r.getByte();

        panValue = (byte) (defaultPan & 127);

        panning = (defaultPan & 128) == 128;

        // sample length
        length = r.getUIntAsLong();

        // loop begining point
        loopBeginning = r.getUIntAsLong();

        // loop end point
        loopEnd = r.getUIntAsLong();

        // middle c speed
        c5Speed = r.getInt();

        // sustain loop begining point
        sustainLoopBeginning = r.getUIntAsLong();

        // sustain loop end point
        sustainLoopEnd = r.getUIntAsLong();

        // sample pointer
        samplePointer = r.getUIntAsLong();

        // vibrato speed
        vibratoSpeed = r.getByte();

        // vibrato depth
        vibratoDepth = r.getByte();

        // vibrato rate
        vibratoRate = r.getUByteAsShort();

        // vibrato vibratoWaveform
        vibratoWaveform = r.getByte();

        // sample cache
        sampleCache = new ITSampleCache(r);

        boolean cacheDataRead = sampleCache.read();
        if (!cacheDataRead) {
            throw new IOException("Sample cache data failed to be read. ");
        }

        // sample data
        sampleData = new ITSampleData(fileName, signed, sixteenBit,
                samplePointer, stereo, length, compressed, delta);

        boolean dataRead = sampleData.read(r);

        // check that sample read
        if (!dataRead) {
            throw new IOException("Sample Data failed to be read. ");
        }
        
        return dataRead;
    }

    // write method
    @Override
    public boolean write() throws IOException, FileNotFoundException {
        return false;
    }
    
    @Override
    public boolean write(IWritable wm) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    // to string
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        // sample header
        sb.append("Sample[Header code: ");
        sb.append(testHeader);

        // dos filename
        sb.append("\nDos file name: ");
        sb.append(dosFileName);

        // global volume
        sb.append("\nGlobal vomume: ");
        sb.append(globalVolume);

        // sample associated with header
        sb.append("\nSampla associated with header? ");
        sb.append(sampleAssociatedWithHeader);

        // 8/16 bit
        sb.append("\nSixteen bit? ");
        sb.append(sixteenBit);

        // stereo or mono
        sb.append("\nSterio? ");
        sb.append(stereo);

        // compresssion
        sb.append("\nIs compressed? ");
        sb.append(compressed);

        // loop
        sb.append("\nIs looped? ");
        sb.append(looped);

        // sustain loop
        sb.append("\nIs sustain looped? ");
        sb.append(sustainLooped);

        // ping poing loop
        sb.append("\nIs ping-pong looped? ");
        sb.append(pingPongLooped);

        // sustain ping pong loop
        sb.append("\nIs ping-poing sustain Looped? ");
        sb.append(pingPongSustainLooped);

        // default volume
        sb.append("\nDefault volume: ");
        sb.append(defaultVolume);

        // sample name
        sb.append("\nSample name: ");
        sb.append(sampleName);

        // signed
        sb.append("\nIs signed? ");
        sb.append(signed);

        // delta values
        sb.append("\nIs delta? ");
        sb.append(delta);

        // default pan
        sb.append("\nDefault pan: ");
        sb.append(defaultPan);

        // sample length
        sb.append("\nSample length: ");
        sb.append(length);

        // loop begining point
        sb.append("\nLoop beginning: ");
        sb.append(loopBeginning);

        // loop end point
        sb.append("\nLoop end: ");
        sb.append(loopEnd);

        // middle c speed
        sb.append("\nC5 speed: ");
        sb.append(c5Speed);

        // sustain loop begining point
        sb.append("\nSustian loop beginning: ");
        sb.append(sustainLoopBeginning);

        // sustain loop end point
        sb.append("\nSustain loop end: ");
        sb.append(sustainLoopEnd);

        // sample pointer
        sb.append("\nSample data location: ");
        sb.append(samplePointer);

        // vibrato speed
        sb.append("\nVibrato speed: ");
        sb.append(vibratoSpeed);

        // vibrato depth
        sb.append("\nVibrato depth: ");
        sb.append(vibratoDepth);

        // vibrato rate
        sb.append("\nVibrato rate: ");
        sb.append(vibratoRate);

        // vibrato vibratoWaveform
        sb.append("\nVibrato waveform: ");
        sb.append(vibratoWaveform);

        // data
        /*sb.append("\n");
        sb.append(sampleData.toString());*/
        sb.append("]");
        return sb.toString();
    }

    public int length() {
        return SAMPLE_HEADER_LENGTH;
    }

    @Override
    public short getBitRate() {
        if (sixteenBit) {
            return 16;
        } else {
            return 8;
        }
    }

    @Override
    public double[] getLData() {
        return sampleData.getLData();
    }

    @Override
    public double[] getRData() {
        return sampleData.getRData();
    }

    public double incrementSampleIndex(double index) {

        // loop back
        if (index > loopEnd) {
            index = loopBeginning;
        } else {
            index++;
        }

        return index;
    }

    public double updateSampleIndex(double downSampleIndex, double amount) {

        // loop back
        if (downSampleIndex + amount > loopEnd) {
            downSampleIndex = loopBeginning + (downSampleIndex + amount - loopEnd);
        } else {
            downSampleIndex += amount;
        }

        return downSampleIndex;
    }

    @Override
    public String getHeaderID() {
        return SAMPLE_HEADER;
    }
}
