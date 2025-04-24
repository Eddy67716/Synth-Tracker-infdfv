/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package music.note;

/**
 *
 * @author Edward Jenkins
 */
public class SolfegeNoteRange extends NoteRange {
    
    // constants
    public static final String[] NOTES = {"Do_", "Do#", "Re_",
        "Re#", "Me_", "Fa_", "Fa#", "So_", "So#", "La_", "La#", "Si_"};
    public static final String[] ALT_NOTES = {"Si#", "Reb", "Do*",
        "Meb", "Fab", "Me#", "Sob", "Fa*", "Lab", "SiB", "Sib", "Dob"};
    public static final String[] QUARTER_TONE_NOTES = {"Dom", "Dos", "Rem",
        "Res", "Mem", "Fam", "Fas", "Som", "Sos", "Lam", "Las", "Sim"};

    public SolfegeNoteRange(double tuningFrequency) {
        super(NOTES, ALT_NOTES, QUARTER_TONE_NOTES, tuningFrequency);
    }
    
    public SolfegeNoteRange() {
        super(NOTES, ALT_NOTES, QUARTER_TONE_NOTES);
    }
}
