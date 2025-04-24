/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sound.formats.riff.wave;

import io.IReadable;
import io.IWritable;
import java.io.FileNotFoundException;
import java.io.IOException;
import file.ISaveableData;

/**
 *
 * @author eddy6
 */
public class SampleLoop implements ISaveableData {
    
    // constants
    public static final byte LENGTH = 24;
    public static final byte LOOP_FORWARDS = 0;
    public static final byte LOOP_PING_PONG = 1;
    public static final byte LOOP_BACKWARDS = 2;
    
    // instance variables
    private long dwIndentifier;
    private long dwType;
    private long dwStart;
    private long dwEnd;
    private long dwFraction;
    private long dwPlayCount;
    
    public SampleLoop() {
        
    }
    
    // getters
    public long getDwIndentifier() {
        return dwIndentifier;
    }

    public long getDwType() {
        return dwType;
    }

    public long getDwStart() {
        return dwStart;
    }

    public long getDwEnd() {
        return dwEnd;
    }

    public long getDwFraction() {
        return dwFraction;
    }

    public long getDwPlayCount() {
        return dwPlayCount;
    }
    
    // setters
    public void setDwIndentifier(long dwIndentifier) {
        this.dwIndentifier = dwIndentifier;
    }

    public void setDwType(long dwType) {
        this.dwType = dwType;
    }

    public void setDwStart(long dwStart) {
        this.dwStart = dwStart;
    }

    public void setDwEnd(long dwEnd) {
        this.dwEnd = dwEnd;
    }

    public void setDwFraction(long dwFraction) {
        this.dwFraction = dwFraction;
    }

    public void setDwPlayCount(long dwPlayCount) {
        this.dwPlayCount = dwPlayCount;
    }

    @Override
    public boolean write(IWritable w) throws IOException {
        
        // write indentifier
        w.writeInt((int)dwIndentifier);
        
        // write type
        w.writeInt((int)dwType);
        
        // write start
        w.writeInt((int)dwStart);
        
        // write end
        w.writeInt((int)dwEnd);
        
        // write fraction
        w.writeInt((int)dwFraction);
        
        // write play count
        w.writeInt((int)dwPlayCount);
        
        return true;
    }

    @Override
    public boolean read(IReadable r) throws IOException, FileNotFoundException, IllegalArgumentException {
       
        // read indentifier
        dwIndentifier = r.getUIntAsLong();
        
        // read type
        dwType = r.getUIntAsLong();
        
        // read start
        dwStart = r.getUIntAsLong();
        
        // read end
        dwEnd = r.getUIntAsLong();
        
        // read fraction
        dwFraction = r.getUIntAsLong();
        
        // read play count
        dwPlayCount = r.getUIntAsLong();
        
        return true;
    }

    @Override
    public int length() {
        return LENGTH;
    }
}
