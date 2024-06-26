/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package module;

import io.IReadable;
import file.ISavableData;
import java.io.FileNotFoundException;
import java.io.IOException;
import file.ISavableFile;
import io.IWritable;

/**
 *
 * @author Edward Jenkins
 */
public interface ISavableModule extends ISavableFile, ISavableData {
    
    public String getHeaderID();
}
