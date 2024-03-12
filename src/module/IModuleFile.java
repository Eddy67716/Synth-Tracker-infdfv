/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package module;

import io.ISavableFile;
import java.util.List;

/**
 *
 * @author Edward Jenkins
 */
public interface IModuleFile extends ISavableFile{
    
    public String getFileName();
    
    public int getModTypeID();
    
    public IModuleHeader getIModuleheader();
    
    public List<IInstrument> getIInstruments();
    
    public List<IAudioSample> getISamples();
    
    public List<IPattern> getIPatterns();
    
    public void play();

    public byte getChannelsCount();
    
}
