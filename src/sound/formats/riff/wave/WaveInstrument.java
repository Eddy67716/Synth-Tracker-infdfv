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
public class WaveInstrument extends RiffChunk {
    
    // constants
    public static final String S_GROUP_ID = "inst";   // DATA
    
    // instance variabels
    private byte bUnshiftedNote;
    private byte chFineTune;
    private byte chGain;
    private byte bLowNote;
    private byte bHighNote;
    private byte bLowVelocity;
    private byte bHighVelocity;

    public WaveInstrument() {
        super(S_GROUP_ID);
    }
    
    public WaveInstrument(boolean headerRead) {
        super(headerRead);
    }
    
    // getters
    public byte getbUnshiftedNote() {
        return bUnshiftedNote;
    }

    public byte getChFineTune() {
        return chFineTune;
    }

    public byte getChGain() {
        return chGain;
    }

    public byte getbLowNote() {
        return bLowNote;
    }

    public byte getbHighNote() {
        return bHighNote;
    }

    public byte getbLowVelocity() {
        return bLowVelocity;
    }

    public byte getbHighVelocity() {
        return bHighVelocity;
    }
    
    // setters
    public void setbUnshiftedNote(byte bUnshiftedNote) {
        this.bUnshiftedNote = bUnshiftedNote;
    }

    public void setChFineTune(byte chFineTune) {
        this.chFineTune = chFineTune;
    }

    public void setChGain(byte chGain) {
        this.chGain = chGain;
    }

    public void setbLowNote(byte bLowNote) {
        this.bLowNote = bLowNote;
    }

    public void setbHighNote(byte bHighNote) {
        this.bHighNote = bHighNote;
    }

    public void setbLowVelocity(byte bLowVelocity) {
        this.bLowVelocity = bLowVelocity;
    }

    public void setbHighVelocity(byte bHighVelocity) {
        this.bHighVelocity = bHighVelocity;
    }

    @Override
    public boolean read(IReadable r) throws IOException, FileNotFoundException, IllegalArgumentException {
        boolean read = super.read(r);
        
        // read unshifted note
        bUnshiftedNote = r.getByte();
        
        // read fine tune
        chFineTune = r.getByte();
        
        // read gain
        chGain = r.getByte();
        
        // read low note
        bLowNote = r.getByte();
        
        // read hight note
        bHighNote = r.getByte();
        
        // read low velocity
        bLowVelocity = r.getByte();
        
        // read high velocity
        bHighVelocity = r.getByte();
        
        setFullyRead(read);
        
        return read;
    }

    @Override
    public boolean write(IWritable w) throws IOException {
        boolean written =  super.write(w); 
        
        // write unshifted note
        w.writeByte(bUnshiftedNote);
        
        // write fine tune
        w.writeByte(chFineTune);
        
        // write gain
        w.writeByte(chGain);
        
        // write low note
        w.writeByte(bLowNote);
        
        // write hight note
        w.writeByte(bHighNote);
        
        // write low velocity
        w.writeByte(bLowVelocity);
        
        return written;
    }
}
