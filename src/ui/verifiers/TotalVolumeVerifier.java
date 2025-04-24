/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.verifiers;

import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JTextField;
import static module.it.effects.ItEffectValues.encodeVolumeByte;

/**
 *
 * @author Edward Jenkins
 */
public class TotalVolumeVerifier extends InputVerifier {
    
    public TotalVolumeVerifier() {
        
    }

    @Override
    public boolean shouldYieldFocus(JComponent source, JComponent target) {
        if (source instanceof JTextField) {

            return verify(source);

        } else {
            return true;
        }
    }

    @Override
    public boolean verify(JComponent input) {
        if (input instanceof JTextField) {
            JTextField inputField = (JTextField) input;

            try {
                int index
                        = encodeVolumeByte(inputField.getText());
                
                return index < 256;
            } catch (IllegalArgumentException e) {
                return false;
            }

        } else {
            return false;
        }
    }
}
