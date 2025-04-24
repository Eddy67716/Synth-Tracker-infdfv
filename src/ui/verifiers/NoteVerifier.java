/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.verifiers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import music.note.NoteRange;

/**
 *
 * @author Edward Jenkins
 */
public class NoteVerifier extends InputVerifier {
    
    private int minimumNote;
    private int maximumNote;
    private NoteRange noteRange;
    
    public NoteVerifier(NoteRange noteRange, int minimumNote, int maximumNote) {
        super();
        this.noteRange = noteRange;
        this.minimumNote = minimumNote;
        this.maximumNote = maximumNote;
    }

    @Override
    public boolean shouldYieldFocus(JComponent source, JComponent target) {
        if (source instanceof JFormattedTextField) {
            
            return verify(source);
            
        } else {
            return true;
        }
    }

    @Override
    public boolean verify(JComponent input) {
        if (input instanceof JFormattedTextField) {
            JFormattedTextField inputField = (JFormattedTextField)input;
            
            try {
                int index = noteRange.getWritableNoteIndex(inputField.getText());
            
                return index <= maximumNote && index >= minimumNote;
            } catch (NullPointerException e) {
                return false;
            }
            
        } else {
            return false;
        }
    }
    
}
