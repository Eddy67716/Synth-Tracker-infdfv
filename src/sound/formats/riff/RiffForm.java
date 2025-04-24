/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sound.formats.riff;

import io.IReadable;
import io.IWritable;
import java.io.FileNotFoundException;
import java.io.IOException;
import static sound.formats.riff.RiffChunk.S_GROUP_ID_LENGTH;

/**
 *
 * @author eddy6
 */
public abstract class RiffForm extends RiffChunk {
    
    // instance variable
    private String sRiffType;
    
    public RiffForm(String sGroupID, String sRiffType) {
        super(sGroupID);
        this.sRiffType = sRiffType;
    }
    
    // getter
    public String getsRiffType() {
        return sRiffType;
    }

    @Override
    public boolean read(IReadable r) throws IOException, FileNotFoundException, IllegalArgumentException {
        // read super data
        boolean read = super.read(r);
        
        // chunnk ID
        String checkType = r.getByteString(S_GROUP_ID_LENGTH);
        
        if (!sRiffType.equals(checkType)) {
            throw new IllegalArgumentException("S Riff Type is invalid! ");
        }
        
        return read;
    }

    @Override
    public boolean write(IWritable w) throws IOException {
        // write super data
        boolean written = super.write(w);
        
        // sRiffType
        w.writeByteString(sRiffType);
        
        return written;
    }
}
