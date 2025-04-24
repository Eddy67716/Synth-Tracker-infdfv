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
public class WaveOMPTExtra extends RiffChunk {

    // constants
    public static final String S_GROUP_ID = "xtra";
    public static final byte PAN_FLAG = 0b10;

    // instance variables
    private int sampleFlags;
    private boolean panned;
    private short panning;
    private short defaultVolume;
    private short globalVolume;
    private byte vibratoWave;
    private short vibratoSweep;
    private short vibratoDepth;
    private short vibratoRate;

    public WaveOMPTExtra() {
        super(S_GROUP_ID);
    }
    
    public WaveOMPTExtra(boolean headerRead) {
        super(headerRead);
    }

    // getters
    public int getSampleFlags() {
        return sampleFlags;
    }

    public boolean isPanned() {
        return panned;
    }

    public short getPanning() {
        return panning;
    }

    public short getDefaultVolume() {
        return defaultVolume;
    }

    public short getGlobalVolume() {
        return globalVolume;
    }

    public byte getVibratoWave() {
        return vibratoWave;
    }

    public short getVibratoSweep() {
        return vibratoSweep;
    }

    public short getVibratoDepth() {
        return vibratoDepth;
    }

    public short getVibratoRate() {
        return vibratoRate;
    }

    // setters
    public void setSampleFlags(int sampleFlags) {
        this.sampleFlags = sampleFlags;
    }

    public void setPanned(boolean panned) {
        this.panned = panned;
    }

    public void setPanning(short panning) {
        this.panning = panning;
    }

    public void setDefaultVolume(short defaultVolume) {
        this.defaultVolume = defaultVolume;
    }

    public void setGlobalVolume(short globalVolume) {
        this.globalVolume = globalVolume;
    }

    public void setVibratoWave(byte vibratoWave) {
        this.vibratoWave = vibratoWave;
    }

    public void setVibratoSweep(short vibratoSweep) {
        this.vibratoSweep = vibratoSweep;
    }

    public void setVibratoDepth(short vibratoDepth) {
        this.vibratoDepth = vibratoDepth;
    }

    public void setVibratoRate(short vibratoRate) {
        this.vibratoRate = vibratoRate;
    }

    @Override
    public boolean read(IReadable r) throws IOException, FileNotFoundException, 
            IllegalArgumentException {
        boolean read = super.read(r);

        // read sample flags
        sampleFlags = r.getInt();
        
        // panned flag
        panned = (sampleFlags & PAN_FLAG) == PAN_FLAG;

        // read panning
        panning = r.getShort();

        // read defaut volume
        defaultVolume = r.getShort();

        // read global volume
        globalVolume = r.getShort();

        // read reserved
        r.skipBytes(2);

        // read vibrato type
        vibratoWave = r.getByte();

        // read vibrato sweep/delay
        vibratoSweep = r.getUByteAsShort();

        // read vibrato depth
        vibratoDepth = r.getUByteAsShort();

        // read vibrato rate
        vibratoRate = r.getUByteAsShort();
        
        setFullyRead(read);

        return read;
    }

    @Override
    public boolean write(IWritable w) throws IOException {

        boolean written = super.write(w);

        // write sample flags
        w.writeInt(sampleFlags);
        
        // panned flag
        sampleFlags |= (panned) ? PAN_FLAG : 0;

        // write panning
        w.writeShort(panning);

        // write defaut volume
        w.writeShort(defaultVolume);

        // write global volume
        w.writeShort(globalVolume);

        // write reserved
        w.skipBytes(2);

        // write vibrato type
        w.writeByte(vibratoWave);

        // write vibrato sweep/delay
        w.writeByte((byte)vibratoSweep);

        // write vibrato depth
        w.writeByte((byte)vibratoDepth);

        // write vibrato rate
        w.writeByte((byte)vibratoRate);

        return written;
    }

}
