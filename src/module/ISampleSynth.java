/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package module;

import sound.formats.AudioSampleData;

/**
 *
 * @author eddy6
 */
public interface ISampleSynth extends ISynthesiser, ISampleParseable {
    
    // set the sample rate of the sample
    public void setC5Speed(int c5Speed);
    
    // set the bitrate of the sample
    public void setBitRate(short bitRate);
    
    // set signness of the sample
    public void setSigned(boolean signed);
    
    // set stereo
    public void setStereo(boolean stereo);
    
    // set looped
    public void setLooped(boolean looped);
    
    // set sustaint looped
    public void setSustainLooped(boolean sustainLooped);
    
    // set looped
    public void setPingPongLooped(boolean pingPongLooped);
    
    // set sustaint looped
    public void setPingPongSustainLooped(boolean pingPongSustainLooped);
    
    // set loop beginning
    public void setLoopBeginning(long loopBeginning);
    
    // set loop end
    public void setLoopEnd(long loopEnd);
    
    // set sustain loop beginning
    public void setSustainLoopBeginning(long sustainLoopBeginning);
    
    // set sustain loop end
    public void setSustainLoopEnd(long sustainLoopEnd);
    
    // set audio sample data
    public void setAudioSampleData(AudioSampleData data);
}
