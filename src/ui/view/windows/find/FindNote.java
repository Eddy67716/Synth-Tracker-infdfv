/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.view.windows.find;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import static ui.UIProperties.BOLD_FONT;
import static ui.UIProperties.DEF_INSETS;

/**
 *
 * @author eddy6
 */
public class FindNote extends JPanel {
    
    // instance variables
    private int modType;
    private Border findNoteBorder;
    private GridBagLayout findNoteLayout;
    private GridBagConstraints fnc;
    private JLabel noteSelectionTypeLabel;
    private JRadioButton singleNoteRadioButton;
    private JRadioButton noteRangeRadioButton;
    private ButtonGroup findNoteGroup;
    private JLabel firstNoteLabel;
    private JComboBox firstNoteComboBox;
    private JLabel secondNoteLabel;
    private JComboBox secondNoteComboBox;
    
    // constructor
    public FindNote(int modType) {
        this.modType = modType;
        init();
    }
    
    // getters
    public JRadioButton getSingleNoteRadioButton() {
        return singleNoteRadioButton;
    }

    public JRadioButton getNoteRangeRadioButton() {
        return noteRangeRadioButton;
    }

    public JComboBox getFirstNoteComboBox() {
        return firstNoteComboBox;
    }

    public JComboBox getSecondNoteComboBox() {
        return secondNoteComboBox;
    }
    
    public void init() {
        // set the layout
        findNoteLayout = new GridBagLayout();
        setLayout(findNoteLayout);
        
        // setup the constrants
        fnc = new GridBagConstraints();
        fnc.anchor = GridBagConstraints.SOUTHWEST;
        //fnc.fill = GridBagConstraints.BOTH;
        fnc.insets = DEF_INSETS;

        // set the border
        findNoteBorder
                = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);

        // set the border title
        findNoteBorder
                = BorderFactory.createTitledBorder(findNoteBorder,
                        "Note: ", 0, 0, BOLD_FONT);

        // set options border
        setBorder(findNoteBorder);
        
        // note selection type label
        noteSelectionTypeLabel = new JLabel("Note selection type: ");
        
        fnc.gridx = 0;
        fnc.gridwidth = GridBagConstraints.REMAINDER;
        fnc.weightx = 1;
        fnc.gridy = 0;
        fnc.gridheight = 1;
        fnc.weighty = 0;
        
        add(noteSelectionTypeLabel, fnc);
        
        // single note radio button
        singleNoteRadioButton = new JRadioButton("Single");
        
        fnc.gridy++;
        fnc.gridwidth = 1;
        
        add(singleNoteRadioButton, fnc);
        
        // note range radio button
        noteRangeRadioButton = new JRadioButton("Range");
        
        fnc.gridx++;
        fnc.gridwidth = GridBagConstraints.REMAINDER;
        
        add(noteRangeRadioButton, fnc);
            
        // button structure
        findNoteGroup = new ButtonGroup();
        findNoteGroup.add(singleNoteRadioButton);
        findNoteGroup.add(noteRangeRadioButton);
        
        // first note label
        firstNoteLabel = new JLabel("First note: ");
        
        fnc.gridx = 0;
        fnc.gridwidth = 1;
        fnc.weightx = 1;
        fnc.gridy++;
        
        add(firstNoteLabel, fnc);
        
        // second note label
        secondNoteLabel = new JLabel("Second note: ");
        
        fnc.gridx = 1;
        fnc.gridwidth = GridBagConstraints.REMAINDER;
        fnc.weightx = 1;
        
        add(secondNoteLabel, fnc);
        
        // first note combo box
        firstNoteComboBox = new JComboBox();
        
        fnc.gridwidth = 1;
        fnc.gridx = 0;
        fnc.gridy++;
        
        add(firstNoteComboBox, fnc);
        
        
        // second note combo box
        secondNoteComboBox = new JComboBox();
        
        fnc.gridwidth = GridBagConstraints.REMAINDER;
        fnc.gridx++;
        
        add(secondNoteComboBox, fnc);
        
        // add trailing empty JPanel
        fnc.gridx = 0;
        fnc.gridy++;
        fnc.weighty = 1.0;
        fnc.gridheight = GridBagConstraints.REMAINDER;
        
        add(new JPanel(), fnc);
        
    }
}
