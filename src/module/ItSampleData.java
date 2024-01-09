/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package module;

import io.IReadable;
import io.IWritable;
import java.io.IOException;
import module.it.format.ItSampleHeader;

/**
 *
 * @author Edward Jenkins
 */
public abstract class ItSampleData {
    
    public ItSampleData() {
        
    }
    
    public abstract boolean write(IWritable w) throws IOException;
    
    public abstract boolean read(IReadable r, ItSampleHeader sample) throws IOException,
            IllegalArgumentException;
    
    public abstract int length();
}
