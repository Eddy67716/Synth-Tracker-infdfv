/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sound.formats.riff;

import io.IReadable;
import io.IWritable;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import static sound.formats.riff.RiffChunk.S_GROUP_ID_LENGTH;

/**
 *
 * @author eddy6
 */
public class UnknownList extends RiffList {
    
    // instance variables
    private List<RiffChunk> listChunks;
    
    public UnknownList(String listID, long size) {
        super(S_GROUP_ID, size);
        this.setListIdRead(true);
    }
    
    // getters
    public List<RiffChunk> getListChunks() {
        return listChunks;
    }

    // setter
    public void setListChunks(List<RiffChunk> listChunks) {
        this.listChunks = listChunks;
    }
    
    @Override
    public boolean read(IReadable r) throws IOException, FileNotFoundException, IllegalArgumentException {

        boolean read = super.read(r);

        long fileIndex = r.getFilePosition();

        listChunks = new ArrayList<>();

        while (r.getFilePosition() - fileIndex < this.getChunkSize() - 4) {

            RiffChunk chunk;

            String idString = r.getByteString(S_GROUP_ID_LENGTH);

            chunk = new UnknownRiffChunk(idString);
            chunk.setRiffIdRead(true);

            chunk.read(r);

            listChunks.add(chunk);
        }

        return read;
    }

    @Override
    public boolean write(IWritable w) throws IOException {
        boolean written = super.write(w);

        for (RiffChunk chunk : listChunks) {
            chunk.write(w);
        }

        return written;
    }
}
