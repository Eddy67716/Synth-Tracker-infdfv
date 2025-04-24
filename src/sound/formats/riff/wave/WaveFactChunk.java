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
 * @author eddy6
 */
public class WaveFactChunk extends RiffChunk {

    // constants
    public static final String S_GROUP_ID = "fact";    // fact

    // instance variables
    private int dwSampleLength;
    
    // all args constructor
    public WaveFactChunk(int chunkSize, int sampleLength) {
        this();
        this.setChunkSize(chunkSize);
        this.dwSampleLength = sampleLength;
    }

    // no args constructor
    public WaveFactChunk() {
        super(S_GROUP_ID);
    }

    // getter
    public int getDwSampleLength() {
        return dwSampleLength;
    }

    // setter
    public void setDwSampleLength(int dwSampleLength) {
        this.dwSampleLength = dwSampleLength;
    }

    @Override
    public boolean write(IWritable w) throws IOException {
        
        // write super data
        boolean written = super.write(w);
        
        // dwSampleLength
        w.writeInt(dwSampleLength);

        return written;
    }

    @Override
    public boolean read(IReadable r) throws IOException, FileNotFoundException, IllegalArgumentException {
        
        // read super data
        boolean read = super.read(r);
        
        return read;
    }
}
