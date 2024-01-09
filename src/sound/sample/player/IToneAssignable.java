/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package sound.sample.player;

import io.Reader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import module.ISavableModule;
import module.it.format.ItSampleHeader;
import sound.generator.waveformes.ISamplePlayer;

/**
 *
 * @author Edward Jenkins
 */
public interface IToneAssignable extends ISavableModule {
    
    // assign a sample by note
    public ISamplePlayer assignByNote(int note, int volume) 
            throws CloneNotSupportedException ;
    
    // assign a sample by frequency
    public ISamplePlayer assignByFrequency(double frequency, int volume) 
            throws CloneNotSupportedException ;
    
    // updata any cache data
    public void  updateCache();
    
    public static IToneAssignable load(File file) throws IOException, 
            FileNotFoundException, IllegalArgumentException {

        IToneAssignable returnToneAssignable;

        String headerID = new Reader(file.getAbsolutePath()).getByteString(4);

        switch (headerID) {
            /*case StDrumInstrument.HEADER_ID:
                returnToneAssignable = new StDrumInstrument(file);
                break;
            case StInstrument.HEADER_ID:
                returnToneAssignable = new StInstrument(file);
                break;
            case StSoundSample.HEADER_ID:
                returnToneAssignable = new StSoundSample(file);
                break;
            case ChipSampleSpec.HEADER_ID:
                returnToneAssignable = new ChipSampleSpec(file);
                break;
            case AdditiveSynthSpec.HEADER_ID:
                returnToneAssignable = new AdditiveSynthSpec(file);
                break;
            case OrganSampleSpec.HEADER_ID:
                returnToneAssignable = new OrganSampleSpec(file);
                break;*/
            case ItSampleHeader.SAMPLE_HEADER :
                returnToneAssignable = new ItSampleHeader(file);
                break;
            default:
                throw new IllegalArgumentException("Header ID is invalid");
        }

        returnToneAssignable.read();

        return returnToneAssignable;
    }
    
    public byte getNewNoteAction();
    
    public byte getDuplicateCheckType();
    
    public byte getDuplicateCheckAction();
}
