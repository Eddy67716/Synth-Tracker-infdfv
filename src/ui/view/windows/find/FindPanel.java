/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.view.windows.find;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import static ui.UIProperties.DEF_INSETS;

/**
 *
 * @author eddy6
 */
public class FindPanel extends JPanel {
    
    // instance variables
    private int modType;
    private GridBagConstraints fpc;
    // note
    private FindNote findNote;
    // instrument
    private FindInstrument findInstrument;
    // volume
    private FindVolume findVolume;
    // effect 1
    private FindEffect findEffect1;
    // effect 2
    private FindEffect findEffect2;
    
    // constructor
    public FindPanel(byte modType) {
        this.modType = modType;
        init();
    }
    
    // getters
    public FindNote getFindNote() {
        return findNote;
    }

    public FindInstrument getFindInstrument() {
        return findInstrument;
    }

    public FindVolume getFindVolume() {
        return findVolume;
    }

    public FindEffect getFindEffect1() {
        return findEffect1;
    }

    public FindEffect getFindEffect2() {
        return findEffect2;
    }
    
    public void init() {
        this.setLayout(new GridBagLayout());
        fpc = new GridBagConstraints();
        fpc.anchor = GridBagConstraints.NORTHWEST;
        
        // find note
        findNote = new FindNote(this.modType);
        
        fpc.weightx = 1;
        fpc.weighty = 0;
        fpc.gridwidth = GridBagConstraints.REMAINDER;
        fpc.gridheight = 1;
        fpc.gridx = 0;
        fpc.gridy = 0;
        
        add(findNote, fpc);
        
        // find instrument
        findInstrument = new FindInstrument(modType);
        
        fpc.gridy++;
        
        add(findInstrument, fpc);
        
        // find volume effect
        findVolume = new FindVolume(modType);
        
        fpc.gridy++;
        
        add(findVolume, fpc);
        
        // find effect 1
        findEffect1 = new FindEffect(modType);
        
        fpc.gridy++;
        fpc.weighty = 1;
        fpc.gridheight = GridBagConstraints.REMAINDER;
        
        add(findEffect1, fpc);
    }
}
