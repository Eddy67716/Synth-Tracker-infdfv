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

/**
 *
 * @author Edward Jenkins
 */
public class WaveSampleChunk extends RiffChunk {
    
    // constants
    public static final String S_GROUP_ID = "smpl";
    
    // instance variables
    private long dwManufacturer;
    private long dwProduct;
    private long dwSamplePeriod;
    private long dwMidiUnityNote;
    private long dwMidiPitchFraction;
    private long dwSMPTEFormat;
    private long dwSMPTEOffset;
    private long cSampleLoops;
    private long cbSamplerData;
    private SampleLoop[] sampleLoops;

    public WaveSampleChunk() {
        super(S_GROUP_ID);
    }
    
    public WaveSampleChunk(boolean headerRead) {
        super(headerRead);
    }

    // getters
    public long getDwManufacturer() {
        return dwManufacturer;
    }

    public long getDwProduct() {
        return dwProduct;
    }

    public long getDwSamplePeriod() {
        return dwSamplePeriod;
    }

    public long getDwMidiUnityNote() {
        return dwMidiUnityNote;
    }

    public long getDwMidiPitchFraction() {
        return dwMidiPitchFraction;
    }

    public long getDwSMPTEFormat() {
        return dwSMPTEFormat;
    }

    public long getDwSMPTEOffset() {
        return dwSMPTEOffset;
    }

    public long getcSampleLoops() {
        return cSampleLoops;
    }

    public long getCbSamplerData() {
        return cbSamplerData;
    }

    public SampleLoop[] getSampleLoops() {
        return sampleLoops;
    }

    // setters
    public void setDwManufacturer(long dwManufacturer) {
        this.dwManufacturer = dwManufacturer;
    }

    public void setDwProduct(long dwProduct) {
        this.dwProduct = dwProduct;
    }

    public void setDwSamplePeriod(long dwSamplePeriod) {
        this.dwSamplePeriod = dwSamplePeriod;
    }

    public void setDwMidiUnityNote(long dwMidiUnityNote) {
        this.dwMidiUnityNote = dwMidiUnityNote;
    }

    public void setDwMidiPitchFraction(long dwMidiPitchFraction) {
        this.dwMidiPitchFraction = dwMidiPitchFraction;
    }

    public void setDwSMPTEFormat(long dwSMPTEFormat) {
        this.dwSMPTEFormat = dwSMPTEFormat;
    }

    public void setDwSMPTEOffset(long dwSMPTEOffset) {
        this.dwSMPTEOffset = dwSMPTEOffset;
    }

    public void setcSampleLoops(long cSampleLoops) {
        this.cSampleLoops = cSampleLoops;
    }

    public void setCbSamplerData(long cbSamplerData) {
        this.cbSamplerData = cbSamplerData;
    }

    @Override
    public boolean read(IReadable r) throws IOException, FileNotFoundException, IllegalArgumentException {
        boolean read = super.read(r);
        
        // read manufacturer
        dwManufacturer = r.getUIntAsLong();
        
        // read product
        dwProduct = r.getUIntAsLong();
        
        // read sample period
        dwSamplePeriod = r.getUIntAsLong();
        
        // read MIDI unity note
        dwMidiUnityNote = r.getUIntAsLong();
        
        // read MIDI pitch fraction
        dwMidiPitchFraction = r.getUIntAsLong();
        
        // read SMPTE format
        dwSMPTEFormat = r.getUIntAsLong();
        
        // read SMPTE offset
        dwSMPTEOffset = r.getUIntAsLong();
        
        // read sample loop count
        cSampleLoops = r.getUIntAsLong();
        
        // read sample data
        cbSamplerData = r.getUIntAsLong();
        
        // read sample loops
        sampleLoops = new SampleLoop[(int)cSampleLoops];
        
        for (int i = 0; i < sampleLoops.length; i++) {
            sampleLoops[i] = new SampleLoop();
            sampleLoops[i].read(r);
        }
        
        setFullyRead(read);
        
        return read;
    }

    @Override
    public boolean write(IWritable w) throws IOException {
        boolean written =  super.write(w); 
        
        // write manufacturer
        w.writeInt((int)dwManufacturer);
        
        // write product
        w.writeInt((int)dwProduct);
        
        // write sample period
        w.writeInt((int)dwSamplePeriod);
        
        // write MIDI unity note
        w.writeInt((int)dwMidiUnityNote);
        
        // write MIDI pitch fraction
        w.writeInt((int)dwMidiPitchFraction);
        
        // write SMPTE format
        w.writeInt((int)dwSMPTEFormat);
        
        // write SMPTE offset
        w.writeInt((int)dwSMPTEOffset);
        
        // write sample loop count
        w.writeInt((int)cSampleLoops);
        
        // write sample data
        w.writeInt((int)cbSamplerData);
        
        // write sample loops
        for (SampleLoop loop : sampleLoops) {
            loop.write(w);
        }
        
        return written;
    }
}
