/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sound.formats.riff;

import sound.formats.riff.RiffChunk;
import io.IReadable;
import io.IWritable;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author eddy6
 */
public class UnknownRiffChunk extends RiffChunk {
    
    // instance variable
    private byte[] data;
    
    public UnknownRiffChunk(String chunkID) {
        super(chunkID);
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    @Override
    public boolean read(IReadable r) throws IOException, FileNotFoundException, IllegalArgumentException {
        boolean read = super.read(r);
        
        data = r.getBytes((int) getChunkSize());
        
        return read;
    }

    @Override
    public boolean write(IWritable w) throws IOException {
        boolean written =  super.write(w); 
        
        w.writeBytes(data);
        
        return written;
    }
}
