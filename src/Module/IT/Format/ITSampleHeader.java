/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Module.IT.Format;

import Sound.Wave.WaveIO;
import io_stuff.ReadMethods;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import Module.IAudioSample;

/**
 *
 * @author Edward Jenkins
 */
public class ITSampleHeader implements IAudioSample{
    
    // constants
    public static final String SAMPLE_HEADER = "IMPS";
    
    // instance variables
    private ReadMethods rm;                         // read methods for sample
    private String fileName;                        // name of file
    private long offsetToSample;                    // where to start reading the file
    private String testHeader;                      // used to check the header is valid
    private String dosFileName;                     // 12 character file name
    private byte globalVolume;                      // global volume 0-64
    private short flags;                            // flags
    private boolean sampleAssociatedWithHeader;     // flag 0
    private boolean sixteenBit;                     // flag 1
    private boolean stereo;                         // flag 2
    private boolean compressed;                     // flag 3
    private boolean looped;                         // flag 4
    private boolean sustainLooped;                  // flag 5
    private boolean pingPongLooped;                 // flag 6
    private boolean pingPongSustainLooped;          // flag 7
    private byte defaultVolume;                     // 0-64
    private String sampleName;                      // actual name of sample
    private short convertFlags;                      // flags for conversion
    private boolean signed;                         // flag 0
    private boolean delta;                          // flag 2
    private byte defaultPan;                        // default pan for sample files
    private byte panValue;
    private boolean panning;
    private long length;                            // length of sample in samples
    private long loopBeginning;                     // pointer to loop beginning
    private long loopEnd;                           // pointer to loop end
    private int c5Speed;                            // speed of middle C
    private long sustainLoopBeginning;              // pointer to sustain loop beginning
    private long sustainLoopEnd;                    // pointer to sustain loop end
    private long samplePointer;                     // offset to sample data
    private byte vibratoSpeed;                      // speed of vibrato
    private byte vibratoDepth;                      // depth of vibrato
    private byte waveform;                          // 0 is sine, 1 is falling 
                                                    // sawtooth, 2 is square and 3 is random
    private byte vibratoRate;                       // rate that vibrato increases at begining
    private ITSampleData sampleData;                // wraps sample data and normalises it ot -1 to 1
    
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
        waveform = 0;
        
        // set the data
        sampleData = new ITSampleData(signed, sixteenBit, stereo, length, 
                compressed, delta, soundSample.getLData(), 
                soundSample.getRData());
    }
    
    // getters
    public String getFileName() {
        return fileName;
    }

    public ReadMethods getReadMethods() {
        return rm;
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

    public byte getVibratoSpeed() {
        return vibratoSpeed;
    }

    public byte getVibratoDepth() {
        return vibratoDepth;
    }

    public byte getWaveform() {
        return waveform;
    }

    public byte getVibratoRate() {
        return vibratoRate;
    }

    public ITSampleData getSampleData() {
        return sampleData;
    }
    
    // read method
    @Override
    public boolean read() throws IOException, FileNotFoundException, 
            IllegalArgumentException {
        
        // read methods
        rm = new ReadMethods(fileName, true);

        // set input stream to read at offset
        rm.skipBytes(offsetToSample);

        // success boolean
        boolean success = true;
        
        // sample header
        testHeader = rm.getByteString(4);
        
        if (!testHeader.equals(SAMPLE_HEADER)) {
            throw new IllegalArgumentException("Header is invalid! ");
        }
        
        // dos filename
        dosFileName = rm.getByteString(12);
        
        // skip one byte
        rm.getByte();
        
        // global volume
        globalVolume = rm.getByte();
        
        // flags
        flags = rm.getByte();
        
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
        defaultVolume = rm.getByte();
        
        // sample name
        sampleName = rm.getByteString(26);
        
        // convert
        convertFlags = rm.getUByteAsShort();
        
        // signed
        signed = (convertFlags & 1) == 1;
        
        // delta values
        delta = (convertFlags & 4) == 4;
        
        // default pan
        defaultPan = rm.getByte();
        
        panValue = (byte) (defaultPan & 127);
        
        panning = (defaultPan & 128) == 128;
        
        // sample length
        length = rm.getUIntAsLong();
        
        // loop begining point
        loopBeginning = rm.getUIntAsLong();
        
        // loop end point
        loopEnd = rm.getUIntAsLong();
        
        // middle c speed
        c5Speed = rm.getInt();
        
        // sustain loop begining point
        sustainLoopBeginning = rm.getUIntAsLong();
        
        // sustain loop end point
        sustainLoopEnd = rm.getUIntAsLong();
        
        // sample pointer
        samplePointer = rm.getUIntAsLong();
        
        // vibrato speed
        vibratoSpeed = rm.getByte();
        
        // vibrato depth
        vibratoDepth = rm.getByte();
        
        // vibrato rate
        vibratoRate = rm.getByte();
        
        // vibrato waveform
        waveform = rm.getByte();
        
        // sample data
        sampleData = new ITSampleData(fileName, signed, sixteenBit, 
                samplePointer, stereo, length, compressed, delta);
        
        boolean dataRead = sampleData.read();
        
        // check that sample read
        if(!dataRead) {
            throw new IOException("Sample Data failed to be read. ");
        }
        
        return success;
    }
    
    // write method
    @Override
    public boolean write() throws IOException, FileNotFoundException {
        return false;
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
        
        // vibrato waveform
        sb.append("\nVibrato waveform: ");
        sb.append(waveform);
        
        // data
        /*sb.append("\n");
        sb.append(sampleData.toString());*/
        
        sb.append("]");
        return sb.toString();
    }

    @Override
    public short getBitRate() {
        if(sixteenBit) {
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
}
