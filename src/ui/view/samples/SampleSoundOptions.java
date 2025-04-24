/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.view.samples;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ChangeListener;
import lang.LanguageHandler;
import static ui.UIProperties.BOLD_FONT;
import static ui.UIProperties.CHECKBOX_INSETS;
import static ui.UIProperties.DEF_FONT;
import static ui.UIProperties.DEF_INSETS;
import static ui.UIProperties.VALUE_SPINNER_SIZE;

/**
 *
 * @author Edward Jenkins
 */
public class SampleSoundOptions extends JPanel {

    // instance variables
    private int modType;
    private LanguageHandler languageHandler;
    private GridBagLayout soundOptionsLayout;
    private GridBagConstraints soc;
    private Border soundOptionsBorder;
    private JLabel defaultVolumeLabel;
    // all
    private JSpinner defaultVolumeValue;
    private SpinnerModel defVolumeSpinnerModel;
    private JSlider defaultVolumeSlider;
    private JLabel globalVolumeLabel;
    // s3m, it and str
    private JSpinner globalVolumeValue;
    private SpinnerModel globalVolumeSpinnerModel;
    private JSlider globalVolumeSlider;
    private JLabel defaultPanningLabel;
    // xm, it and str
    private JSpinner defaultPanningValue;
    private SpinnerModel panSpinnerModel;
    private JSlider defaultPanningSlider;
    private JLabel usePanningLabel;
    // it and str
    private JCheckBox panning;
    private JLabel useSurroundLabel;
    // str
    private JCheckBox surround;

    // constructor
    public SampleSoundOptions(int modType, LanguageHandler languageHandler) {
        this.modType = modType;
        this.languageHandler = languageHandler;
        init();
    }

    // getters
    public JSpinner getDefaultVolumeValue() {
        return this.defaultVolumeValue;
    }

    public JSlider getDefaultVolumeSlider() {
        return this.defaultVolumeSlider;
    }

    public JSpinner getGlobalVolumeValue() {
        return this.globalVolumeValue;
    }

    public JSlider getGlobalVolumeSlider() {
        return this.globalVolumeSlider;
    }

    public JCheckBox getPanning() {
        return this.panning;
    }

    public JSpinner getDefaultPanningValue() {
        return this.defaultPanningValue;
    }

    public JSlider getDefaultPanningSlider() {
        return this.defaultPanningSlider;
    }

    private void init() {

        // set the layout
        soundOptionsLayout = new GridBagLayout();
        setLayout(soundOptionsLayout);
        soc = new GridBagConstraints();
        soc.anchor = GridBagConstraints.SOUTHWEST;
        //soc.fill = GridBagConstraints.BOTH;
        soc.insets = DEF_INSETS;

        // set the border
        soundOptionsBorder
                = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);

        // set the border title
        soundOptionsBorder
                = BorderFactory.createTitledBorder(soundOptionsBorder,
                        languageHandler
                                .getLanguageText("sound.options"), 0, 0,
                        BOLD_FONT);

        // set options border
        setBorder(soundOptionsBorder);

        // set the default volume label
        defaultVolumeLabel = new JLabel(languageHandler
                .getLanguageText("sound.default_volume"));
        defaultVolumeLabel.setFont(DEF_FONT);
        soc.gridx = 0;
        soc.gridy = 0;
        add(defaultVolumeLabel, soc);

        // set default volume spinner model
        defVolumeSpinnerModel = (modType == 6)
                ? new SpinnerNumberModel(128, 0, 255, 1)
                : new SpinnerNumberModel(64, 0, 64, 1);

        // set the default volume value spinner
        defaultVolumeValue = new JSpinner(defVolumeSpinnerModel);
        defaultVolumeValue.setPreferredSize(VALUE_SPINNER_SIZE);
        soc.gridx = 1;
        soc.gridy = 0;
        add(defaultVolumeValue, soc);

        // set the default volume slider
        defaultVolumeSlider = (modType == 6)
                ? new JSlider(0, 255, 128)
                : new JSlider(0, 64, 64);
        soc.gridx = 2;
        soc.gridy = 0;
        soc.weightx = 1.0;
        soc.gridwidth = GridBagConstraints.REMAINDER;
        soc.fill = GridBagConstraints.HORIZONTAL;
        add(defaultVolumeSlider, soc);

        if (modType >= 4) {

            // set the global volume label
            globalVolumeLabel = new JLabel(languageHandler
                .getLanguageText("sound.global_volume"));
            globalVolumeLabel.setFont(DEF_FONT);
            soc.gridx = 0;
            soc.gridy = 1;
            soc.weightx = 0.0;
            soc.gridwidth = 0;
            soc.fill = GridBagConstraints.NONE;
            add(globalVolumeLabel, soc);

            // set global volume spinner model
            globalVolumeSpinnerModel = (modType == 6)
                    ? new SpinnerNumberModel(128, 0, 128, 1)
                    : new SpinnerNumberModel(64, 0, 64, 1);

            // set the global volume value spinner
            globalVolumeValue = new JSpinner(globalVolumeSpinnerModel);
            globalVolumeValue.setPreferredSize(VALUE_SPINNER_SIZE);
            soc.gridx = 1;
            soc.gridy = 1;
            soc.weightx = 0.0;
            add(globalVolumeValue, soc);

            // set the global volume slider
            globalVolumeSlider = (modType == 6)
                    ? new JSlider(0, 128, 128)
                    : new JSlider(0, 64, 64);
            soc.gridx = 2;
            soc.gridy = 1;
            soc.weightx = 1.0;
            soc.gridwidth = GridBagConstraints.REMAINDER;
            soc.fill = GridBagConstraints.HORIZONTAL;
            add(globalVolumeSlider, soc);

            // set the use panning label
            usePanningLabel = new JLabel(languageHandler
                .getLanguageText("sound.use_panning"));
            usePanningLabel.setFont(DEF_FONT);
            soc.gridx = 0;
            soc.gridy++;
            soc.weightx = 0.0;
            soc.gridwidth = 0;
            soc.fill = GridBagConstraints.NONE;
            add(usePanningLabel, soc);

            // set the use panning checkbox
            panning = new JCheckBox();
            panning.setSelected(false);
            soc.gridx = 2;
            soc.weightx = 1.0;
            soc.gridwidth = GridBagConstraints.REMAINDER;
            soc.insets = CHECKBOX_INSETS;
            add(panning, soc);
        }

        if (modType >= 3) {

            // set the default panning label
            defaultPanningLabel = new JLabel(languageHandler
                .getLanguageText("sound.default_panning"));
            defaultPanningLabel.setFont(DEF_FONT);
            soc.gridx = 0;
            soc.gridy++;
            soc.gridwidth = 1;
            soc.weightx = 0.0;
            soc.gridwidth = 0;
            soc.fill = GridBagConstraints.NONE;
            soc.insets = DEF_INSETS;
            add(defaultPanningLabel, soc);

            // set the panning spinner model
            switch (modType) {
                case 6:     // STR
                    panSpinnerModel = new SpinnerNumberModel(0, -128, 127, 1);
                    break;
                case 3:     // XM
                    panSpinnerModel = new SpinnerNumberModel(128, 0, 255, 1);
                    break;
                default:    // IT and MPTM
                    panSpinnerModel = new SpinnerNumberModel(32, 0, 64, 1);
                    break;
            }

            // set the default panning value spinner
            defaultPanningValue = new JSpinner(panSpinnerModel);
            defaultPanningValue.setPreferredSize(VALUE_SPINNER_SIZE);
            if (modType >= 4) {
                defaultPanningValue.setEnabled(panning.isSelected());
            }
            soc.gridx = 1;
            add(defaultPanningValue, soc);

            // set the default panning slider
            switch (modType) {
                case 6:     // STR
                    defaultPanningSlider = new JSlider(-128, 127, 0);
                    break;
                case 3:     // XM
                    defaultPanningSlider = new JSlider(0, 255, 128);
                    break;
                default:    // IT and MPTM
                    defaultPanningSlider = new JSlider(0, 64, 32);
                    break;
            }
            if (modType >= 4) {
                defaultPanningSlider.setEnabled(panning.isSelected());
            }
            soc.gridx = 2;
            soc.weightx = 1.0;
            soc.fill = GridBagConstraints.HORIZONTAL;
            soc.gridwidth = GridBagConstraints.REMAINDER;
            add(defaultPanningSlider, soc);
        }

        if (modType == 6) {
            // set the use surround label
            useSurroundLabel = new JLabel(languageHandler
                .getLanguageText("sound.use_surround"));
            useSurroundLabel.setFont(DEF_FONT);
            soc.gridx = 0;
            soc.gridy++;
            soc.gridwidth = 0;
            soc.weightx = 0.0;
            soc.fill = GridBagConstraints.NONE;
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

        // add pannel to bottom column
        soc.gridx = 0;
        soc.gridy++;
        soc.weightx = 2;
        soc.weighty = 1;
        soc.gridwidth = GridBagConstraints.REMAINDER;
        soc.gridheight = GridBagConstraints.REMAINDER;
        add(new JPanel(), soc);
    }

    // events and listeners
    public void addDefVolumeValChangeListener(ChangeListener changePerformed) {
        this.defaultVolumeValue.addChangeListener(changePerformed);
    }

    public void addDefVolumeSliderChangeListener(ChangeListener changePerformed) {
        this.defaultVolumeSlider.addChangeListener(changePerformed);
    }

    public void addGlobVolumeValChangeListener(ChangeListener changePerformed) {
        this.globalVolumeValue.addChangeListener(changePerformed);
    }

    public void addGlobVolumeSliderChangeListener(ChangeListener changePerformed) {
        this.globalVolumeSlider.addChangeListener(changePerformed);
    }

    public void addPaningActionListener(ActionListener actionPerformed) {
        this.panning.addActionListener(actionPerformed);
    }

    public void addDefPanValChangeListener(ChangeListener changePerformed) {
        this.defaultPanningValue.addChangeListener(changePerformed);
    }

    public void addDefPanSliderChangeListener(ChangeListener changePerformed) {
        this.defaultPanningSlider.addChangeListener(changePerformed);
    }

    public void addSurrondActionListener(ActionListener actionPerformed) {
        this.surround.addActionListener(actionPerformed);
    }
}
