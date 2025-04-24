/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package module;

import file.ISaveableFile;
import java.util.List;

/**
 *
 * @author Edward Jenkins
 */
public interface IModuleFile extends ISaveableFile{
    
    public String getFileName();
    
    public int getModTypeID();
    
    public IModuleHeader getIModuleheader();
    
    public short[] getPatternOrder();
    
    public List<IInstrument> getIInstruments();
    
    public List<ISampleSynth> getISamples();
    
    public List<IPattern> getIPatterns();
    
    public void play();

    public byte getChannelsCount();
    
}
