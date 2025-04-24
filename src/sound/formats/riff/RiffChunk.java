/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sound.formats.riff;

import io.IReadable;
import io.IWritable;
import java.io.FileNotFoundException;
import java.io.IOException;
import file.ISaveableData;

/**
 *
 * @author eddy6
 */
public abstract class RiffChunk implements ISaveableData {

    // constants
    public static final byte S_GROUP_ID_LENGTH = (byte) 4;

    private String sGroupID;
    private long chunkSize;
    private boolean riffIdRead;
    private boolean partiallyRead;
    private boolean fullyRead;

    // constructor
    public RiffChunk(String chunkID) {
        this.sGroupID = chunkID;
    }

    // constructor for a header detected chunk
    public RiffChunk(boolean headerRead) {
        this.riffIdRead = headerRead;
    }

    // constructor for parsing in a partial read list chunk
    public RiffChunk(String chunkID, long chunkSize) {
        this.sGroupID = chunkID;
        this.chunkSize = chunkSize;
        partiallyRead = true;
    }

    // getter
    public String getsGroupID() {
        return sGroupID;
    }

    public long getChunkSize() {
        return chunkSize;
    }

    public boolean isRiffIdRead() {
        return riffIdRead;
    }

    public boolean isPartiallyRead() {
        return partiallyRead;
    }

    public boolean isFullyRead() {
        return fullyRead;
    }

    // setter
    public void setsGroupID(String sGroupID) {
        this.sGroupID = sGroupID;
    }

    public void setChunkSize(long chunkSize) {
        this.chunkSize = chunkSize;
    }

    public void setRiffIdRead(boolean headerRead) {
        this.riffIdRead = headerRead;
    }

    public void setFullyRead(boolean fullyRead) {
        this.fullyRead = fullyRead;
        riffIdRead = false;
    }

    @Override
    public boolean write(IWritable w) throws IOException {

        // chunk ID
        w.writeByteString(sGroupID);

        // chunk size
        w.writeInt((int) chunkSize);

        return true;
    }

    @Override
    public boolean read(IReadable r) throws IOException, FileNotFoundException,
            IllegalArgumentException {

        if (!partiallyRead) {
            if (!riffIdRead) {
                // chunnk ID
                String checkID = r.getByteString(S_GROUP_ID_LENGTH);

                if (!sGroupID.equals(checkID)) {
                    throw new IllegalArgumentException("S Group ID is invalid! ");
                }
            }

            // chunk size
            chunkSize = r.getUIntAsLong();
        }

        return true;
    }

    @Override
    public int length() {
        return (int) (chunkSize + S_GROUP_ID_LENGTH);
    }
}
