/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Module;

import java.util.List;

/**
 *
 * @author Edward Jenkins
 */
public interface IModuleFile {
    
    public boolean read() throws Exception;
    
    public boolean write() throws Exception;
    
    public String getFileName();
    
    public int getModTypeID();
    
    public List<IInstrument> getIInstruments();
    
    public List<IAudioSample> getISamples();
    
    public List<IPattern> getIPatterns();
    
    public void play();

    public byte getChannelsCount();
    
}
