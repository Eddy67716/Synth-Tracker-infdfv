/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.verifiers;

import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;

/**
 *
 * @author Edward Jenkins
 */
public class EffectVerifier extends InputVerifier {
    
    public EffectVerifier() {
        
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
        JFormattedTextField instrumentField = (JFormattedTextField) input;

        if (instrumentField.getText().length() != 1) {
            return false;
        } else {
            char effectChar = instrumentField.getText().charAt(0);
            Character.toUpperCase(effectChar);
            return Character.isAlphabetic(effectChar)
                    || effectChar == '#' || effectChar == '\\';
        }
    }
}
