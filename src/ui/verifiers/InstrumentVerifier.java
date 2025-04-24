/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.verifiers;

import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JTextField;

/**
 *
 * @author Edward Jenkins
 */
public class InstrumentVerifier extends InputVerifier {
    
    // instance variables
    private short maxInstrument;
    
    public InstrumentVerifier(short maxInstrument) {
        this.maxInstrument = maxInstrument;
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
        try {
            
            JTextField instrumentField = (JTextField)input;
            
            int instrument = Integer.parseInt(instrumentField.getText());
            
            return instrument <= maxInstrument && instrument > 0;
            
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
