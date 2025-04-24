/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package music.note;

/**
 *
 * @author Edward Jenkins
 */
public class GermanicNoteRange extends NoteRange{
    
    public static final String[] NOTES = {"C_", "C#", "D_",
        "D#", "E_", "F_", "F#", "G_", "G#", "A_", "B_", "H_"};
    public static final String[] ALT_NOTES = {"H#", "Db", "C*",
        "Eb", "Fb", "E#", "Gb", "F*", "Ab", "Bb", "Hb", "Cb"};
    public static final String[] QUARTER_TONE_NOTES = {"Ch", "Cs", "Dh",
        "Ds", "Eh", "Fh", "Fs", "Gh", "Gs", "Ah", "Bh", "Hh"};
    
    public GermanicNoteRange(double tuningFrequency) {
        super(NOTES, ALT_NOTES, QUARTER_TONE_NOTES, tuningFrequency);
    }
    
    public GermanicNoteRange() {
        super(NOTES, ALT_NOTES, QUARTER_TONE_NOTES);
    }
    
}
