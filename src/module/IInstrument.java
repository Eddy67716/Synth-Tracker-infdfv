/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package module;

import io.IReadable;
import io.IWritable;
import java.io.IOException;
import module.it.format.ItNodePoint;
import sound.sample.player.IToneAssignable;

/**
 *
 * @author Edward Jenkins
 */
public interface IInstrument extends IInstrumentParseable {
    
    public IEnvelopeNode[] getVolumeEnvelopePoints();
    
    public IEnvelopeNode[] getPanEnvelopePoints();
    
    public IEnvelopeNode[] getPitchEnvelopePoints();

    public void setDosFileName(String dosFileName);

    public void setDuplicateNoteCheck(boolean duplicateNoteCheck);

    public void setNewNoteAction(byte newNoteAction);

    public void setDuplicateCheckType(byte duplicateCheckType);

    public void setDuplicateCheckAction(byte duplicateCheckAction);

    public void setFadeOut(int fadeOut);

    public void setPitchPanSeparation(byte pitchPanSeparation);

    public void setPitchPanCentre(byte pitchPanCentre);

    public void setNormalisedGlobalVolumeValue(double globalVolumeValue);

    public void setNormalisedPanValue(double panValue);
    
    public void setGlobalVolumeValue(int globalVolumeValue);

    public void setPanValue(int panValue);
    
    public void setPanning(boolean panning);

    public void setRandomVolumeVariation(byte randomVolumeVariation);

    public void setRandomPanningVariation(byte randomPanningVariation);

    public void setSampleNumber(short sampleNumber);

    public void setInstrumentName(String instrumentName);

    public void setFiltered(boolean filtered);

    public void setInitialFilterCutoff(short initialFilterCutoff);
    
    public void setUsingResonance(boolean resonance);

    public void setInitialFilterResonance(short initialFilterResonance);

    public void setMidiChannel(short midiChannel);

    public void setMidiProgram(byte midiProgram);

    public void setMidiBank(short midiBank);
    
    public void offsetSamples(short offset);
}
