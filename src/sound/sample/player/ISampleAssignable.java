/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package sound.sample.player;

import sound.generator.waveformes.ISamplePlayer;

/**
 *
 * @author Edward Jenkins
 */
public interface ISampleAssignable {
    
    // assign a sample by note
    public ISamplePlayer assignByNote(int note, int volume) 
            throws CloneNotSupportedException ;
    
    // assign a sample by frequency
    public ISamplePlayer assignByFrequency(double frequency, int volume) 
            throws CloneNotSupportedException ;
    
    // updata any cache data
    public void  updateCache();
}
