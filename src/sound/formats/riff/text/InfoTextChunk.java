/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sound.formats.riff.text;

import io.IReadable;
import io.IWritable;
import java.io.FileNotFoundException;
import java.io.IOException;
import sound.formats.riff.RiffChunk;
import static io.IOMethods.setStringToLength;

/**
 *
 * @author eddy6
 */
public class InfoTextChunk extends RiffChunk {
    
    // instance variable
    private String text;
    
    public InfoTextChunk(String chunkID) {
        super(chunkID);
    }

    // getter
    public String getText() {
        return text;
    }

    // setter
    public void setText(String text) {
        this.text = text;
        
        setChunkSize(text.length());
    }

    @Override
    public boolean read(IReadable r) throws IOException, FileNotFoundException, IllegalArgumentException {
        boolean read = super.read(r);
        
        // read the text
        text = r.getByteString((int)getChunkSize());
        
        if ((getChunkSize() & 0b1) == 0b1) {
            r.getByte();
        }
        
        return read;
    }

    @Override
    public boolean write(IWritable w) throws IOException {
        boolean written = super.write(w);
        
        // alter size of chunk
        setChunkSize(text.length());
        
        // write the text
        w.writeByteString(text);
        
        return written;
    }
}
