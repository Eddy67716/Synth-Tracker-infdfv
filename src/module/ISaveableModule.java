/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package module;

import io.IReadable;
import java.io.FileNotFoundException;
import java.io.IOException;
import io.IWritable;
import file.ISaveableData;
import file.ISaveableFile;

/**
 *
 * @author Edward Jenkins
 */
public interface ISaveableModule extends ISaveableFile, ISaveableData {
    
    public String getHeaderID();
}
