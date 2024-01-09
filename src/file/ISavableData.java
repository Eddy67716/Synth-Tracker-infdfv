/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package file;

import io.IReadable;
import io.IWritable;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author Edward Jenkins
 */
public interface ISavableData extends IByteMeasurable {
    
    public boolean write(IWritable w) throws IOException;

    public boolean read(IReadable r) throws IOException, 
            FileNotFoundException, IllegalArgumentException;
}
