/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package music.note;

/**
 *
 * @author Edward Jenkins
 */
public class NoteFrequencyConstants {
    
    public static final int SAMPLE_RATE = 44100;
    
    // tuning frequency
    public static final double TUNING_FREQUENCY = 440;
    
    // the semitone tuning ratio
    public static final double TUNING_RATIO = Math.pow(2.0, 1.0 / 12.0);
    
    // the cent tuning ratio
    public static final double CENT_TUNING_RATIO = Math.pow(2, 1.0 / 1200);
    
    // log tuning ratio
    public static final double LOG_TUNING_RATIO = 1.0 / Math.log(TUNING_RATIO);
    
    // the note range that stores all note frequencies
    public static final NoteRange DEF_NOTE_RANGE = new EngDutNoteRange();
    
    // the Middle C frequency
    public static final double MIDDLE_C_FREQUENCY 
            = DEF_NOTE_RANGE.getFrequency("C_4");
    
    // the sample detuneAmount
    public static final double SAMPLE_DETUNE_AMOUNT 
            = (double)SAMPLE_RATE / MIDDLE_C_FREQUENCY;
}
