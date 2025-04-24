/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.view.details;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.BorderFactory;
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
import static ui.UIProperties.DEF_FONT;
import static ui.UIProperties.DEF_INSETS;
import static ui.UIProperties.VALUE_SPINNER_SIZE;

/**
 *
 * @author Edward Jenkins
 */
public class ModuleSoundOptions extends JPanel {

    // instance variables
    private int modType;
    private final LanguageHandler languageHandler;
    private GridBagLayout soundOptionsLayout;
    private GridBagConstraints soc;
    private Border soundOptionsBorder;
    // s3m, XM, IT and STR
    private JLabel channelLabel;
    private SpinnerModel channelSpinnerModel;
    private JSpinner channelSpinner;
    // s3m, IT and STR
    private JLabel globalVolumeLabel;
    private JSpinner globalVolumeValue;
    private SpinnerModel globalVolumeSpinnerModel;
    private JSlider globalVolumeSlider;
    // s3m, IT and STR
    private JLabel mixVolumeLabel;
    private JSpinner mixVolumeValue;
    private SpinnerModel mixVolumeSpinnerModel;
    private JSlider mixVolumeSlider;
    // s3m, IT and STR,
    private JLabel panSeparationLabel;
    private JSpinner panSeparationValue;
    private SpinnerModel panSeparationSpinnerModel;
    private JSlider panSeparationSlider;

    public ModuleSoundOptions(int modType, LanguageHandler languageHandler) {
        this.modType = modType;
        this.languageHandler = languageHandler;
        init();
    }
    
    // getters
    public JSpinner getChannelSpinner() {
        return channelSpinner;
    }

    public JSpinner getGlobalVolumeValue() {
        return globalVolumeValue;
    }

    public JSlider getGlobalVolumeSlider() {
        return globalVolumeSlider;
    }

    public JSpinner getMixVolumeValue() {
        return mixVolumeValue;
    }

    public JSlider getMixVolumeSlider() {
        return mixVolumeSlider;
    }

    public JSpinner getPanSeparationValue() {
        return panSeparationValue;
    }

    public JSlider getPanSeparationSlider() {
        return panSeparationSlider;
    }
    

    public void init() {

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
                        languageHandler.getLanguageText("module.sound.options"),
                        0, 0, BOLD_FONT);

        // set options border
        setBorder(soundOptionsBorder);

        // channels
        channelLabel = new JLabel(languageHandler
                .getLanguageText("module.sound.options.channels"));
        channelLabel.setFont(DEF_FONT);
        soc.gridx = 0;
        soc.gridy = 0;
        add(channelLabel, soc);

        // set default volume spinner model
        switch (modType) {
            default:
                // MOD
                channelSpinnerModel = new SpinnerNumberModel(4, 0, 4, 1);
                break;
            case 2:
                // s3m
                channelSpinnerModel = new SpinnerNumberModel(8, 0, 32, 1);
                break;
            case 3:
                // XM
                channelSpinnerModel = new SpinnerNumberModel(16, 0, 32, 1);
                break;
            case 4:
            case 5:
            case 6:
                // IT, MPTM and STR
                channelSpinnerModel = new SpinnerNumberModel(10, 0, 64, 1);
                break;

        }

        // set the channel value spinner
        channelSpinner = new JSpinner(channelSpinnerModel);
        channelSpinner.setPreferredSize(VALUE_SPINNER_SIZE);

        soc.gridx = 1;
        soc.gridy = 0;
        soc.gridwidth = GridBagConstraints.REMAINDER;
        add(channelSpinner, soc);

        // set the global volume label
        globalVolumeLabel = new JLabel(languageHandler
                .getLanguageText("module.sound.options.global_volume"));
        globalVolumeLabel.setFont(DEF_FONT);

        soc.gridx = 0;
        soc.gridy = 1;
        soc.weightx = 0.0;
        soc.gridwidth = 1;
        soc.fill = GridBagConstraints.NONE;
        add(globalVolumeLabel, soc);

        // set global volume spinner model
        globalVolumeSpinnerModel = (modType >= 4)
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
        globalVolumeSlider = (modType >= 4)
                ? new JSlider(0, 128, 128)
                : new JSlider(0, 64, 64);
        soc.gridx = 2;
        soc.gridy = 1;
        soc.weightx = 1.0;
        soc.gridwidth = GridBagConstraints.REMAINDER;
        soc.fill = GridBagConstraints.HORIZONTAL;
        add(globalVolumeSlider, soc);

        if (modType >= 4) {

            // set the mix volume label
            mixVolumeLabel = new JLabel(languageHandler
                .getLanguageText("module.sound.options.mix_volume"));
            mixVolumeLabel.setFont(DEF_FONT);

            soc.gridx = 0;
            soc.gridy++;
            soc.weightx = 0.0;
            soc.gridwidth = 1;
            soc.fill = GridBagConstraints.NONE;
            add(mixVolumeLabel, soc);

            // set mix volume spinner model
            mixVolumeSpinnerModel = (modType >= 4)
                    ? new SpinnerNumberModel(128, 0, 128, 1)
                    : new SpinnerNumberModel(64, 0, 64, 1);

            // set the mix volume value spinner
            mixVolumeValue = new JSpinner(mixVolumeSpinnerModel);
            mixVolumeValue.setPreferredSize(VALUE_SPINNER_SIZE);
            soc.gridx = 1;
            soc.weightx = 0.0;
            add(mixVolumeValue, soc);

            // set the mix volume slider
            mixVolumeSlider = (modType >= 4)
                    ? new JSlider(0, 128, 128)
                    : new JSlider(0, 64, 64);
            soc.gridx = 2;
            soc.weightx = 1.0;
            soc.gridwidth = GridBagConstraints.REMAINDER;
            soc.fill = GridBagConstraints.HORIZONTAL;
            add(mixVolumeSlider, soc);
            
            // set the pan separation label
            panSeparationLabel = new JLabel(languageHandler
                .getLanguageText("module.sound.options.pan_separation"));
            panSeparationLabel.setFont(DEF_FONT);

            soc.gridx = 0;
            soc.gridy++;
            soc.weightx = 0.0;
            soc.gridwidth = 1;
            soc.fill = GridBagConstraints.NONE;
            add(panSeparationLabel, soc);

            // set panned separation spinner model
            panSeparationSpinnerModel = (modType >= 4)
                    ? new SpinnerNumberModel(128, 0, 128, 1)
                    : new SpinnerNumberModel(64, 0, 64, 1);

            // set the mix volume value spinner
            panSeparationValue = new JSpinner(panSeparationSpinnerModel);
            panSeparationValue.setPreferredSize(VALUE_SPINNER_SIZE);
            soc.gridx = 1;
            soc.weightx = 0.0;
            add(panSeparationValue, soc);

            // set the mix volume slider
            panSeparationSlider = (modType >= 4)
                    ? new JSlider(0, 128, 128)
                    : new JSlider(0, 64, 64);
            
            soc.gridx = 2;
            soc.weightx = 1.0;
            soc.gridwidth = GridBagConstraints.REMAINDER;
            soc.fill = GridBagConstraints.HORIZONTAL;
            add(panSeparationSlider, soc);
        }
        
        // add trailing JPanel
        soc.gridx = 0;
        soc.gridy++;
        soc.weighty = 1.0;
        soc.gridheight = GridBagConstraints.REMAINDER;
        
        add(new JPanel(), soc);
    }
    
    // events and listners
    public void addChannelSpinnerChangeListener(ChangeListener changePerformed) {
        channelSpinner.addChangeListener(changePerformed);
    }
    
    public void addGlobVolumeValChangeListener(ChangeListener changePerformed) {
        globalVolumeValue.addChangeListener(changePerformed);
    }

    public void addGlobVolumeSliderChangeListener(ChangeListener changePerformed) {
        globalVolumeSlider.addChangeListener(changePerformed);
    }
    
    public void addMixVolumeValChangeListener(ChangeListener changePerformed) {
        mixVolumeValue.addChangeListener(changePerformed);
    }

    public void addMixVolumeSliderChangeListener(ChangeListener changePerformed) {
        mixVolumeSlider.addChangeListener(changePerformed);
    }
    
    public void addPanSepValChangeListener(ChangeListener changePerformed) {
        panSeparationValue.addChangeListener(changePerformed);
    }

    public void addPanSepSliderChangeListener(ChangeListener changePerformed) {
        panSeparationSlider.addChangeListener(changePerformed);
    }
}
