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

/**
 *
 * @author eddy6
 */
public abstract class RiffList extends RiffChunk {

    // constants
    public static final String S_GROUP_ID = "LIST";

    // instance variables
    private String listID;
    private boolean listIdRead;

    public RiffList() {
        super(S_GROUP_ID);
    }

    public RiffList(String listID, long size) {
        super(S_GROUP_ID, size);
        listIdRead = true;
    }

    public RiffList(String listID) {
        this();
        this.listID = listID;
    }

    // getters
    public String getListID() {
        return listID;
    }

    // setter
    public void setListIdRead(boolean listIdRead) {
        this.listIdRead = listIdRead;
    }

    @Override
    public boolean read(IReadable r) throws IOException, FileNotFoundException, IllegalArgumentException {

        boolean read = validateList(r);

        return read;
    }

    @Override
    public boolean write(IWritable w) throws IOException {
        boolean written = super.write(w);
        
        // write list id
        w.writeByteString(listID);

        return written;
    }

    protected boolean validateList(IReadable r) throws IOException,
            IllegalArgumentException {

        boolean valid = super.read(r);

        if (!listIdRead) {
            if (listID != null) {
                // list ID
                String checkID = r.getByteString(S_GROUP_ID_LENGTH);

                if (!listID.equals(checkID)) {
                    throw new IllegalArgumentException("List ID is invalid! ");
                }
            } else {
                listID = r.getByteString(S_GROUP_ID_LENGTH);
            }
        }

        return valid;
    }
}
