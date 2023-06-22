/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package module;

import io.IReadable;
import java.io.FileNotFoundException;
import java.io.IOException;
import io.ISavableFile;
import io.IWritable;

/**
 *
 * @author Edward Jenkins
 */
public interface ISavableModule extends ISavableFile {
    
    public String getHeaderID();
    
    public boolean write(IWritable w) throws IOException;

    public boolean read(IReadable r) throws IOException, 
            FileNotFoundException, IllegalArgumentException;
}
