/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.view.windows.replace;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import static ui.UIProperties.BOLD_FONT;
import static ui.UIProperties.DEF_INSETS;

/**
 *
 * @author eddy6
 */
public class ReplaceNote extends JPanel {
    
    // instance variables
    private int modType;
    private Border replaceNoteBorder;
    private GridBagLayout replaceNoteLayout;
    private GridBagConstraints rnc;
    private JLabel replaceNoteTypeLabel;
    private JRadioButton singleNoteRadioButton;
    private JRadioButton transposeRadioButton;
    private ButtonGroup replaceNoteGroup;
    private JLabel singleNoteLabel;
    private JComboBox singleNoteComboBox;
    private JLabel octaveLabel;
    private JSpinner octaveSpinner;
    private JLabel semitoneLabel;
    private JSpinner semitoneSpinner;
    private JLabel transposeDirectionLabel;
    private JRadioButton upRadioButton;
    private JRadioButton downRadioButton;
    private ButtonGroup transposeDirectionGroup;
    
    // constructor
    public ReplaceNote(int modType) {
        this.modType = modType;
        init();
    }
    
    // getters
    public Border getReplaceNoteBorder() {
        return replaceNoteBorder;
    }

    public GridBagLayout getReplaceNoteLayout() {
        return replaceNoteLayout;
    }

    public GridBagConstraints getRnc() {
        return rnc;
    }

    public JRadioButton getSingleNoteRadioButton() {
        return singleNoteRadioButton;
    }

    public JRadioButton getTransposeRadioButton() {
        return transposeRadioButton;
    }

    public JComboBox getSingleNoteComboBox() {
        return singleNoteComboBox;
    }

    public JSpinner getOctaveSpinner() {
        return octaveSpinner;
    }

    public JSpinner getSemitoneSpinner() {
        return semitoneSpinner;
    }

    public JRadioButton getUpRadioButton() {
        return upRadioButton;
    }

    public JRadioButton getDownRadioButton() {
        return downRadioButton;
    }

    public ButtonGroup getTransposeDirectionGroup() {
        return transposeDirectionGroup;
    }
    
    public void init() {
        // set the layout
        replaceNoteLayout = new GridBagLayout();
        setLayout(replaceNoteLayout);
        
        // setup the constrants
        rnc = new GridBagConstraints();
        rnc.anchor = GridBagConstraints.SOUTHWEST;
        //rnc.fill = GridBagConstraints.BOTH;
        rnc.insets = DEF_INSETS;

        // set the border
        replaceNoteBorder
                = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);

        // set the border title
        replaceNoteBorder
                = BorderFactory.createTitledBorder(replaceNoteBorder,
                        "Note: ", 0, 0, BOLD_FONT);

        // set options border
        setBorder(replaceNoteBorder);
        
        // note selection type label
        replaceNoteTypeLabel = new JLabel("Replace note type: ");
        
        rnc.gridx = 0;
        rnc.gridwidth = GridBagConstraints.REMAINDER;
        rnc.weightx = 1;
        rnc.gridy = 0;
        rnc.gridheight = 1;
        rnc.weighty = 0;
        
        add(replaceNoteTypeLabel, rnc);
        
        // single note radio button
        singleNoteRadioButton = new JRadioButton("Single");
        
        rnc.gridy++;
        rnc.gridwidth = 1;
        
        add(singleNoteRadioButton, rnc);
        
        // note range radio button
        transposeRadioButton = new JRadioButton("Transpose");
        
        rnc.gridx++;
        rnc.gridwidth = GridBagConstraints.REMAINDER;
        
        add(transposeRadioButton, rnc);
            
        // button structure
        replaceNoteGroup = new ButtonGroup();
        replaceNoteGroup.add(singleNoteRadioButton);
        replaceNoteGroup.add(transposeRadioButton);
        
        // first note label
        singleNoteLabel = new JLabel("New note: ");
        
        rnc.gridx = 0;
        rnc.gridwidth = GridBagConstraints.REMAINDER;
        rnc.weightx = 1;
        rnc.gridy++;
        
        add(singleNoteLabel, rnc);
        
        // first note combo box
        singleNoteComboBox = new JComboBox();
        
        rnc.gridy++;
        
        add(singleNoteComboBox, rnc);
        
        // octave label
        octaveLabel = new JLabel("Octaves: ");
        
        rnc.gridx = 0;
        rnc.gridwidth = GridBagConstraints.REMAINDER;
        rnc.weightx = 1;
        rnc.gridy++;
        
        add(octaveLabel, rnc);
        
        // octave box
        octaveSpinner = new JSpinner();
        
        rnc.gridy++;
        
        add(octaveSpinner, rnc);
        
        // second note lable
        semitoneLabel = new JLabel("Semitones: ");
        
        rnc.gridx = 0;
        rnc.gridwidth = GridBagConstraints.REMAINDER;
        rnc.weightx = 1;
        rnc.gridy++;
        
        add(semitoneLabel, rnc);
        
        // second note combo box
        semitoneSpinner = new JSpinner();
        
        rnc.gridy++;
        
        add(semitoneSpinner, rnc);
        
        // add trailing empty JPanel
        rnc.gridy++;
        rnc.weighty = 1.0;
        rnc.gridheight = GridBagConstraints.REMAINDER;
        
        add(new JPanel(), rnc);
        
    }
    
}
