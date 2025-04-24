/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package file;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author Edward Jenkins
 */
public interface ISaveableFile extends IByteMeasurable {
    
    public boolean write() throws IOException;
    
    public boolean read() throws IOException, 
            FileNotFoundException, IllegalArgumentException;
}
