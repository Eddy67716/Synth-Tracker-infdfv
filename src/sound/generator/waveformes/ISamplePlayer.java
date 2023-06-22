/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sound.generator.waveformes;

/**
 *
 * @author Edward Jenkins
 */
public interface ISamplePlayer {
    
    public double getFrequency();
    
    public double getPoint();
    
    public double getTotalVolume();

    public void setSampleRate(int sampleRate);
    
    public void setAmplitude(double amplitude);
    
    public void setFrequencyAtZero(double frequency);
    
    public void setOffset(double offset);  
    
    public void setPanning(double panning);
    
    public void setSurround(boolean surround);
    
    public void addToFrequency(double offset);
    
    public void subtractFromFrequency(double offset);
    
    public Object clone() throws CloneNotSupportedException;
    
    public double generateWaveformPoint();
    
    public double[] generateStereoWaveformPoints();
    
    public void panbrelloShift(double panbrelloValue);
    
    public void noteOff();
    
    public boolean isFinished();
    
    public void setFinished(boolean finished);
}
