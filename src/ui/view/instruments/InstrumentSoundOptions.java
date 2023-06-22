/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.view.instruments;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ChangeListener;
import static ui.UIProperties.BOLD_FONT;
import static ui.UIProperties.CHECKBOX_INSETS;
import static ui.UIProperties.DEF_FONT;
import static ui.UIProperties.DEF_INSETS;
import static ui.UIProperties.MEDIUM_FIELD_SIZE;
import static ui.UIProperties.MEDIUM_LABEL_SIZE;
import static ui.UIProperties.NOTE_COMBO_BOX_SIZE;
import static ui.UIProperties.SAMPLE_NOTES;
import static ui.UIProperties.VALUE_SPINNER_SIZE;
import static ui.UIProperties.LARGE_FIELD_SIZE;

/**
 *
 * @author Edward Jenkins
 */
public class InstrumentSoundOptions extends JPanel {

    // instance variables
    private int modType;
    private GridBagLayout soundOptionsLayout;
    private GridBagConstraints soc;
    private Border soundOptionsBorder;
    private JLabel globalVolumeLabel;
    private JSpinner globalVolumeValue;             // it and str
    private SpinnerModel globalVolumeSpinnerModel;
    private JSlider globalVolumeSlider;
    private JLabel defaultPanningLabel;
    private JSpinner defaultPanningValue;           // it and str
    private SpinnerModel panSpinnerModel;
    private JSlider defaultPanningSlider;
    private JLabel usePanningLabel;
    private JCheckBox panning;                      // it and str
    private JLabel useSurroundLabel;
    private JCheckBox surround;                     // str
    private JLabel pitchPanSeparationLabel;
    private JSpinner pitchPanSeparationSpinner;     // it and str
    private SpinnerModel pitchPanSpinnerModel;
    private JLabel pitchPanCentreNoteLabel;
    private ArrayList<String> notes;
    private JComboBox pitchPanCentreNoteComboBox;   // it and str
    private JLabel organPitchPanLabel;
    private JCheckBox organPitchPanCheckBox;        // str

    // constructor
    public InstrumentSoundOptions(int modType) {
        this.modType = modType;
        init();
    }

    // getters
    public JSpinner getGlobalVolumeValue() {
        return globalVolumeValue;
    }

    public JSlider getGlobalVolumeSlider() {
        return globalVolumeSlider;
    }

    public JSpinner getDefaultPanningValue() {
        return defaultPanningValue;
    }

    public JSlider getDefaultPanningSlider() {
        return defaultPanningSlider;
    }

    public JCheckBox getPanning() {
        return panning;
    }

    public JCheckBox getSurround() {
        return surround;
    }

    public JSpinner getPitchPanSeparationSpinner() {
        return pitchPanSeparationSpinner;
    }

    public JComboBox getPitchPanCentreNoteComboBox() {
        return pitchPanCentreNoteComboBox;
    }

    public JCheckBox getOrganPitchPanCheckBox() {
        return organPitchPanCheckBox;
    }
    
    private void init() {

        // set the layout
        soundOptionsLayout = new GridBagLayout();
        setLayout(soundOptionsLayout);
        soc = new GridBagConstraints();
        soc.anchor = GridBagConstraints.SOUTHWEST;
        soc.insets = DEF_INSETS;

        // set the border
        soundOptionsBorder
                = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);

        // set the border title
        soundOptionsBorder
                = BorderFactory.createTitledBorder(soundOptionsBorder,
                        "Sound options", 0, 0, BOLD_FONT);

        // set options border
        setBorder(soundOptionsBorder);

        // set the global volume label
        globalVolumeLabel = new JLabel("Global volume: ");
        globalVolumeLabel.setFont(DEF_FONT);
        soc.gridx = 0;
        soc.gridy = 0;
        add(globalVolumeLabel, soc);

        // set global volume spinner model
        globalVolumeSpinnerModel = new SpinnerNumberModel(128, 0, 128, 1);

        // set the global volume value spinner
        globalVolumeValue = new JSpinner(globalVolumeSpinnerModel);
        globalVolumeValue.setPreferredSize(VALUE_SPINNER_SIZE);
        soc.gridx = 1;
        soc.gridy = 0;
        add(globalVolumeValue, soc);

        // set the global volume slider
        globalVolumeSlider = new JSlider(0, 128, 128);
        soc.gridx = 2;
        soc.gridy = 0;
        soc.weightx = 1.0;
        soc.gridwidth = GridBagConstraints.REMAINDER;
        add(globalVolumeSlider, soc);

        // set the use panning label
        usePanningLabel = new JLabel("Use panning: ");
        usePanningLabel.setFont(DEF_FONT);
        soc.gridx = 0;
        soc.gridy++;
        soc.weightx = 0.0;
        soc.gridwidth = 0;
        add(usePanningLabel, soc);

        // set the use panning checkbox
        panning = new JCheckBox();
        panning.setSelected(false);
        soc.gridx = 2;
        soc.weightx = 1.0;
        soc.gridwidth = GridBagConstraints.REMAINDER;
        soc.insets = CHECKBOX_INSETS;
        add(panning, soc);

        // set the default panning label
        defaultPanningLabel = new JLabel("Default panning: ");
        defaultPanningLabel.setFont(DEF_FONT);
        soc.gridx = 0;
        soc.gridy++;
        soc.gridwidth = 0;
        soc.weightx = 0.0;
        soc.gridwidth = 0;
        soc.insets = DEF_INSETS;
        add(defaultPanningLabel, soc);

        // set the panning spinner model
        panSpinnerModel = new SpinnerNumberModel(32, 0, 64, 1);

        // set the default panning value spinner
        defaultPanningValue = new JSpinner(panSpinnerModel);
        defaultPanningValue.setPreferredSize(VALUE_SPINNER_SIZE);
        defaultPanningValue.setEnabled(panning.isSelected());
        soc.gridx = 1;
        soc.insets = DEF_INSETS;
        add(defaultPanningValue, soc);

        // set the default panning slider
        defaultPanningSlider = new JSlider(0, 64, 32);
        defaultPanningSlider.setEnabled(panning.isSelected());
        soc.gridx = 2;
        soc.weightx = 1.0;
        soc.gridwidth = GridBagConstraints.REMAINDER;
        add(defaultPanningSlider, soc);

        if (modType == 6) {
            // set the use surround label
            useSurroundLabel = new JLabel("Use surround: ");
            useSurroundLabel.setFont(DEF_FONT);
            soc.gridx = 0;
            soc.gridy++;
            soc.gridwidth = 0;
            soc.weightx = 0.0;
            add(useSurroundLabel, soc);

            // set the use surround checkbox
            surround = new JCheckBox();
            surround.setSelected(false);
            soc.gridx = 2;
            soc.weightx = 1.0;
            soc.gridwidth = GridBagConstraints.REMAINDER;
            soc.insets = CHECKBOX_INSETS;
            add(surround, soc);
        }

        // pitch pan separation label
        pitchPanSeparationLabel = new JLabel("Pitch pan separation: ");
        pitchPanSeparationLabel.setFont(DEF_FONT);
        pitchPanSeparationLabel.setPreferredSize(MEDIUM_LABEL_SIZE);
        soc.gridx = 0;
        soc.gridy++;
        soc.gridwidth = 2;
        soc.weightx = 0.0;
        soc.insets = DEF_INSETS;
        add(pitchPanSeparationLabel, soc);

        // set the pitch pan separation spinner
        pitchPanSpinnerModel = new SpinnerNumberModel(0, 0, 32, 1);
        pitchPanSeparationSpinner = new JSpinner(pitchPanSpinnerModel);
        soc.gridx = 0;
        soc.gridy++;
        soc.weightx = 1.0;
        soc.gridwidth = GridBagConstraints.REMAINDER;
        add(pitchPanSeparationSpinner, soc);

        // pitch pan centre note label
        pitchPanCentreNoteLabel = new JLabel("Pitch pan centre note: ");
        pitchPanCentreNoteLabel.setFont(DEF_FONT);
        pitchPanCentreNoteLabel.setPreferredSize(LARGE_FIELD_SIZE);
        soc.gridx = 2;
        soc.gridy--;
        soc.gridwidth = 2;
        soc.weightx = 0.0;
        soc.insets = DEF_INSETS;
        add(pitchPanCentreNoteLabel, soc);

        // end note
        String noteEnd = (modType == 6) ? "C_10" : "B_9";

        // make list for combo box
        notes = new ArrayList<String>();

        for (int i = 0; i <= 9; i++) {  // i is octave

            // iterate through all 12 notes per octave
            for (String note : SAMPLE_NOTES) {

                // add note to notes list
                notes.add(note + i);

                if (notes.get(notes.size() - 1).equals(noteEnd)) {
                    break;
                }
            }
        }

        // set the pitch pan centre note combo box
        pitchPanCentreNoteComboBox = new JComboBox(notes.toArray());
        pitchPanCentreNoteComboBox.setSelectedIndex(60);
        soc.gridx = 2;
        soc.gridy++;
        soc.gridwidth = 2;
        soc.weightx = 0.0;
        soc.insets = DEF_INSETS;
        add(pitchPanCentreNoteComboBox, soc);

        if (modType == 6) {
            // set the use surround label
            this.organPitchPanLabel = new JLabel("Organ pitch pan: ");
            organPitchPanLabel.setFont(DEF_FONT);
            soc.gridx = 0;
            soc.gridy++;
            soc.gridwidth = 0;
            soc.weightx = 0.0;
            add(organPitchPanLabel, soc);

            // set the use surround checkbox
            this.organPitchPanCheckBox = new JCheckBox();
            organPitchPanCheckBox.setSelected(false);
            soc.gridx = 2;
            soc.weightx = 1.0;
            soc.gridwidth = GridBagConstraints.REMAINDER;
            soc.insets = CHECKBOX_INSETS;
            add(organPitchPanCheckBox, soc);
        }

        // add pannel to bottom column
        soc.gridx = 0;
        soc.gridy = 6;
        soc.weightx = 2;
        soc.weighty = 1;
        soc.gridwidth = GridBagConstraints.REMAINDER;
        soc.gridheight = GridBagConstraints.REMAINDER;
        add(new JPanel(), soc);
    }

    // events
    public void addGlobVolumeValChangeListener(ChangeListener changePerformed) {
        globalVolumeValue.addChangeListener(changePerformed);
    }

    public void addGlobVolumeSliderChangeListener(ChangeListener changePerformed) {
        globalVolumeSlider.addChangeListener(changePerformed);
    }

    public void addPaningActionListener(ActionListener actionPerformed) {
        panning.addActionListener(actionPerformed);
    }

    public void addDefPanValChangeListener(ChangeListener changePerformed) {
        defaultPanningValue.addChangeListener(changePerformed);
    }

    public void addDefPanSliderChangeListener(ChangeListener changePerformed) {
        defaultPanningSlider.addChangeListener(changePerformed);
    }

    public void addSurroundActionListener(ActionListener actionPerformed) {
        surround.addActionListener(actionPerformed);
    }

    public void addPitchPanSpinnerChangeListener(ChangeListener changePerformed) {
        pitchPanSeparationSpinner.addChangeListener(changePerformed);
    }

    public void addPitchCentreComboBoxItemListener(ItemListener itemChange) {
        pitchPanCentreNoteComboBox.addItemListener(itemChange);
    }
}
