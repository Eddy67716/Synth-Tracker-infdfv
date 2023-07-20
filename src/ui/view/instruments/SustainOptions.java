/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.view.instruments;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ChangeListener;
import javax.swing.event.UndoableEditListener;
import static ui.UIProperties.BOLD_FONT;
import static ui.UIProperties.C5_SPINNER_SIZE;
import static ui.UIProperties.DEF_FONT;
import static ui.UIProperties.DEF_INSETS;
import static ui.UIProperties.LARGE_FIELD_SIZE;
import static ui.UIProperties.MEDIUM_FIELD_SIZE;
import static ui.UIProperties.VALUE_SPINNER_SIZE;

/**
 *
 * @author Edward Jenkins
 */
public class SustainOptions extends JPanel {

    // cosntants
    public static final String[] NEW_NOTE_ACTIONS = {
        "Note cut", "Continue", "Note off", "Note fade"
    };

    public static final String[] DUPLICATE_CHECK_TYPE = {
        "Off", "Note", "Sample", "Instrument"
    };

    public static final String[] DUPLICATE_CHECK_ACTION = {
        NEW_NOTE_ACTIONS[0], NEW_NOTE_ACTIONS[2], NEW_NOTE_ACTIONS[3]
    };

    // instance variables
    private int modType;
    private GridBagLayout sustainOptionsLayout;
    private GridBagConstraints soc;
    private Border sustainOptionsBorder;
    private JLabel fadeOutLabel;
    private JSpinner fadeOutSpinner;                // it and str
    private SpinnerModel fadeOutSpinnerModel;
    private JLabel newNoteActionLabel;
    private JComboBox newNoteActionComboBox;        // it and str
    private JLabel dupNoteTypeLabel;
    private JComboBox dupNoteTypeComboBox;          // it and str
    private JLabel dupNoteActionLabel;
    private JComboBox dupNoteActionComboBox;        // it and str

    // constructor
    public SustainOptions(int modType) {
        this.modType = modType;
        init();
    }

    // getters
    public JSpinner getFadeOutSpinner() {
        return fadeOutSpinner;
    }

    public JComboBox getNewNoteActionComboBox() {
        return newNoteActionComboBox;
    }

    public JComboBox getDupNoteTypeComboBox() {
        return dupNoteTypeComboBox;
    }

    public JComboBox getDupNoteActionComboBox() {
        return dupNoteActionComboBox;
    }
    
    private void init() {

        // set the layout
        sustainOptionsLayout = new GridBagLayout();
        setLayout(sustainOptionsLayout);
        soc = new GridBagConstraints();
        soc.anchor = GridBagConstraints.SOUTHWEST;
        soc.insets = DEF_INSETS;

        // set the border
        sustainOptionsBorder
                = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);

        // set the border title
        sustainOptionsBorder
                = BorderFactory.createTitledBorder(sustainOptionsBorder,
                        "Sustain options", 0, 0, BOLD_FONT);

        // set options border
        setBorder(sustainOptionsBorder);

        // set the fade out label
        fadeOutLabel = new JLabel("Fade out: ");
        fadeOutLabel.setFont(DEF_FONT);
        fadeOutLabel.setPreferredSize(LARGE_FIELD_SIZE);
        soc.gridx = 0;
        soc.gridy = 0;
        add(fadeOutLabel, soc);

        // set fade out spinner model
        fadeOutSpinnerModel = (modType == 2)
                ? new SpinnerNumberModel(128, 0, 32767, 1)
                : new SpinnerNumberModel(256, 0, 8192, 32);

        // set the fade out value spinner
        fadeOutSpinner = new JSpinner(fadeOutSpinnerModel);
        fadeOutSpinner.setPreferredSize(C5_SPINNER_SIZE);
        soc.gridx = 1;
        soc.gridy = 0;
        soc.weightx = 1.0;
        soc.gridwidth = GridBagConstraints.REMAINDER;
        add(fadeOutSpinner, soc);

        if (modType >= 4) {

            // set the new note action label
            newNoteActionLabel = new JLabel("New note action: ");
            newNoteActionLabel.setFont(DEF_FONT);
            newNoteActionLabel.setPreferredSize(LARGE_FIELD_SIZE);
            soc.gridx = 0;
            soc.gridy++;
            soc.weightx = 0;
            add(newNoteActionLabel, soc);

            // set the new note action combo box
            newNoteActionComboBox = new JComboBox(NEW_NOTE_ACTIONS);
            soc.gridx = 2;
            soc.weightx = 1.0;
            soc.gridwidth = GridBagConstraints.REMAINDER;
            add(newNoteActionComboBox, soc);
            
            // set the duplicate note type label
            dupNoteTypeLabel = new JLabel("Duplicate note type: ");
            dupNoteTypeLabel.setFont(DEF_FONT);
            dupNoteTypeLabel.setPreferredSize(LARGE_FIELD_SIZE);
            soc.gridx = 0;
            soc.gridy++;
            soc.weightx = 0;
            add(dupNoteTypeLabel, soc);

            // set the new duplicate note type combo box
            dupNoteTypeComboBox = new JComboBox(DUPLICATE_CHECK_TYPE);
            soc.gridx = 1;
            soc.weightx = 1.0;
            soc.gridwidth = GridBagConstraints.REMAINDER;
            add(dupNoteTypeComboBox, soc);
            
            // set the duplicate note action label
            dupNoteActionLabel = new JLabel("Duplicate note action: ");
            dupNoteActionLabel.setFont(DEF_FONT);
            dupNoteActionLabel.setPreferredSize(LARGE_FIELD_SIZE);
            soc.gridx = 0;
            soc.gridy++;
            soc.weightx = 0;
            add(dupNoteActionLabel, soc);

            // set the duplicate note action combo box
            dupNoteActionComboBox = new JComboBox(DUPLICATE_CHECK_ACTION);
            soc.gridx = 1;
            soc.weightx = 1.0;
            soc.gridwidth = GridBagConstraints.REMAINDER;
            add(dupNoteActionComboBox, soc);

        }

        // add pannel to bottom column
        soc.gridx = 0;
        soc.gridy++;
        soc.weightx = 2;
        soc.weighty = 1;
        soc.gridwidth = GridBagConstraints.REMAINDER;
        soc.gridheight = GridBagConstraints.REMAINDER;
        add(new JPanel(), soc);
    }
    
    // listeners
    public void addFadeOutSpinnerChangeListener(ChangeListener changePerformed) {
        fadeOutSpinner.addChangeListener(changePerformed);
    }
    
    public void addNewNoteActionActionListener(ActionListener actionPerformed) {
        newNoteActionComboBox.addActionListener(actionPerformed);
    }
    
    public void addDupNoteTypeActionListener(ActionListener actionPerformed) {
        dupNoteTypeComboBox.addActionListener(actionPerformed);
    }
    
    public void addDupNoteActionActionListener(ActionListener actionPerformed) {
        dupNoteActionComboBox.addActionListener(actionPerformed);
    }
}
