/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.view.windows.replace;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JPanel;

/**
 *
 * @author eddy6
 */
public class ReplacePanel extends JPanel {
    // instance variables
    private int modType;
    private GridBagConstraints fpc;
    // note
    private ReplaceNote ReplaceNote;
    // instrument
    private ReplaceInstrument ReplaceInstrument;
    // volume
    private ReplaceVolume ReplaceVolume;
    // effect 1
    private ReplaceEffect replaceEffect1;
    // effect 2
    private ReplaceEffect ReplaceEffect2;
    
    // constructor
    public ReplacePanel(byte modType) {
        this.modType = modType;
        init();
    }
    
    // getters
    public ReplaceNote getReplaceNote() {
        return ReplaceNote;
    }

    public ReplaceInstrument getReplaceInstrument() {
        return ReplaceInstrument;
    }

    public ReplaceVolume getReplaceVolume() {
        return ReplaceVolume;
    }

    public ReplaceEffect getReplaceEffect1() {
        return replaceEffect1;
    }

    public ReplaceEffect getReplaceEffect2() {
        return ReplaceEffect2;
    }
    
    public void init() {
        this.setLayout(new GridBagLayout());
        fpc = new GridBagConstraints();
        fpc.anchor = GridBagConstraints.NORTHWEST;
        
        // find note
        ReplaceNote = new ReplaceNote(this.modType);
        
        fpc.weightx = 1;
        fpc.weighty = 0;
        fpc.gridwidth = GridBagConstraints.REMAINDER;
        fpc.gridheight = 1;
        fpc.gridx = 0;
        fpc.gridy = 0;
        
        add(ReplaceNote, fpc);
        
        // find instrument
        ReplaceInstrument = new ReplaceInstrument(modType);
        
        fpc.gridy++;
        
        add(ReplaceInstrument, fpc);
        
        // find volume effect
        ReplaceVolume = new ReplaceVolume(modType);
        
        fpc.gridy++;
        
        add(ReplaceVolume, fpc);
        
        // find effect 1
        replaceEffect1 = new ReplaceEffect(modType);
        
        fpc.gridy++;
        fpc.weighty = 1;
        fpc.gridheight = GridBagConstraints.REMAINDER;
        
        add(replaceEffect1, fpc);
    }
}
