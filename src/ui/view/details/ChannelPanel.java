/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.view.details;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ChangeListener;
import lang.LanguageHandler;
import static ui.UIProperties.BOLD_FONT;
import static ui.UIProperties.DEF_FONT;
import static ui.UIProperties.DEF_INSETS;
import static ui.UIProperties.LARGE_FIELD_SIZE;
import static ui.UIProperties.VALUE_SPINNER_SIZE;

/**
 *
 * @author Edward Jenkins
 */
public class ChannelPanel extends JPanel {

    // instance variables
    private int modType;
    private final LanguageHandler languageHandler;
    private int channelNumber;
    private GridBagLayout channelLayout;
    private GridBagConstraints cc;
    private Border channelBorder;
    // IT, XM and STR
    private JLabel channelNameLabel;
    private JTextField channelNameField;
    // IT and STR
    private JLabel channelVolumeLabel;
    private JSpinner channelVolumeValue;
    private SpinnerModel channelVolumeSpinnerModel;
    private JSlider channelVolumeSlider;
    private JLabel channelPanningLabel;
    private JSpinner channelPanningValue;
    private SpinnerModel channelPanningSpinnerModel;
    private JSlider channelPanningSlider;
    private JLabel mutedLabel;
    private JCheckBox muted;
    private JLabel surroundLabel;
    private JCheckBox surround;

    public ChannelPanel(int modType, LanguageHandler languageHandler, 
            int channelNumber) {
        this.modType = modType;
        this.languageHandler = languageHandler;
        this.channelNumber = channelNumber;
        init();
    }

    // getters
    public int getChannelNumber() {
        return channelNumber;
    }

    public JTextField getChannelNameField() {
        return channelNameField;
    }

    public JSpinner getChannelVolumeValue() {
        return channelVolumeValue;
    }

    public JSlider getChannelVolumeSlider() {
        return channelVolumeSlider;
    }

    public JSpinner getChannelPanningValue() {
        return channelPanningValue;
    }

    public JSlider getChannelPanningSlider() {
        return channelPanningSlider;
    }

    public JCheckBox getMuted() {
        return muted;
    }

    public JCheckBox getSurround() {
        return surround;
    }

    // setter
    public void setChannelNumber(int channelNumber) {
        this.channelNumber = channelNumber;
    }

    private void init() {

        // set the layout
        channelLayout = new GridBagLayout();
        setLayout(channelLayout);
        cc = new GridBagConstraints();
        cc.anchor = GridBagConstraints.SOUTHWEST;
        cc.insets = DEF_INSETS;

        // set the border
        channelBorder
                = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
        
        String channelTitle = languageHandler
                            .getLanguageText("module.channel")
                .replaceFirst("%1", Integer.toString(channelNumber));

        // set the border title
        channelBorder
                = BorderFactory.createTitledBorder(channelBorder,
                        channelTitle, 0, 0, BOLD_FONT);

        // set options border
        setBorder(channelBorder);

        // channel name label
        channelNameLabel = new JLabel(languageHandler
                            .getLanguageText("module.channel.name"));
        channelNameLabel.setFont(DEF_FONT);

        cc.gridx = 0;
        cc.gridy = 0;
        cc.weightx = 1;
        cc.gridwidth = GridBagConstraints.REMAINDER;
        add(channelNameLabel, cc);

        // channel name field
        channelNameField = new JTextField();
        channelNameField.setToolTipText(languageHandler
                            .getLanguageText("module.channel.name.desc"));
        channelNameField.setPreferredSize(LARGE_FIELD_SIZE);

        cc.gridx = 0;
        cc.gridy++;
        add(channelNameField, cc);

        if (modType >= 4) {

            // channel volume spinner model
            channelVolumeSpinnerModel = (modType == 6)
                    ? new SpinnerNumberModel(128, 0, 128, 1)
                    : new SpinnerNumberModel(64, 0, 64, 1);

            // channel volume label
            channelVolumeLabel = new JLabel(languageHandler
                            .getLanguageText("module.channel.volume"));

            cc.gridx = 0;
            cc.gridy++;
            cc.weightx = 1.0;
            cc.gridwidth = 1;
            add(channelVolumeLabel, cc);

            // channel volume spinner
            channelVolumeValue = new JSpinner(channelVolumeSpinnerModel);
            channelVolumeValue.setPreferredSize(VALUE_SPINNER_SIZE);

            cc.gridy++;
            add(channelVolumeValue, cc);

            // channel volume slider
            channelVolumeSlider = (modType == 6)
                    ? new JSlider(0, 128, 128)
                    : new JSlider(0, 64, 64);
            channelVolumeSlider.setOrientation(JSlider.VERTICAL);

            cc.gridy++;
            cc.anchor = GridBagConstraints.CENTER;
            add(channelVolumeSlider, cc);

            // channel pan spinner model
            channelPanningSpinnerModel = (modType == 6)
                    ? new SpinnerNumberModel(0, -128, 127, 1)
                    : new SpinnerNumberModel(64, 0, 64, 1);

            // channel pan label
            channelPanningLabel = new JLabel(languageHandler
                            .getLanguageText("module.channel.panning"));

            cc.gridx = 1;
            cc.gridy = 2;
            cc.weightx = 1.0;
            cc.gridwidth = GridBagConstraints.REMAINDER;
            cc.anchor = GridBagConstraints.SOUTHWEST;

            add(channelPanningLabel, cc);

            // channel pan spinner
            channelPanningValue = new JSpinner(channelPanningSpinnerModel);
            channelPanningValue.setPreferredSize(VALUE_SPINNER_SIZE);

            cc.gridy++;
            add(channelPanningValue, cc);

            // channel pan slider
            channelPanningSlider = (modType == 6)
                    ? new JSlider(-128, 127, 0)
                    : new JSlider(0, 64, 32);
            channelPanningSlider.setOrientation(JSlider.VERTICAL);

            cc.gridy++;
            cc.anchor = GridBagConstraints.CENTER;
            add(channelPanningSlider, cc);
        }

        // add mute check box
        muted = new JCheckBox(languageHandler
                            .getLanguageText("module.channel.mute"));

        cc.gridx = 0;
        cc.gridy++;
        cc.anchor = GridBagConstraints.SOUTHWEST;
        add(muted, cc);
        
        // add surround check box
        surround = new JCheckBox(languageHandler
                            .getLanguageText("module.channel.surround"));

        cc.gridx = 1;
        cc.weightx = 1.0;
        cc.gridwidth = GridBagConstraints.REMAINDER;
        cc.anchor = GridBagConstraints.SOUTHWEST;
        add(surround, cc);

        // add trailing JPanel
        cc.gridx = 0;
        cc.gridy++;
        cc.weightx = 1.0;
        cc.weighty = 1.0;
        cc.gridwidth = GridBagConstraints.REMAINDER;
        cc.gridheight = GridBagConstraints.REMAINDER;

        add(new JPanel(), cc);
    }
    
    // events and listeners
    public void addChannelNameFieldActionListener(ActionListener actionPerformed) {
        channelNameField.addActionListener(actionPerformed);
    }
    
    public void addChannelVolumeValChangeListener(ChangeListener changePerformed) {
        channelVolumeValue.addChangeListener(changePerformed);
    }

    public void addChannelVolumeSliderChangeListener(ChangeListener changePerformed) {
        channelVolumeSlider.addChangeListener(changePerformed);
    }

    public void addChannelPanValChangeListener(ChangeListener changePerformed) {
        channelPanningValue.addChangeListener(changePerformed);
    }

    public void addChannelPanSliderChangeListener(ChangeListener changePerformed) {
        channelPanningSlider.addChangeListener(changePerformed);
    }
    
    public void addSurroundCheckBoxActionListner(ActionListener actionPerformed) {
        surround.addActionListener(actionPerformed);
    }
    
    public void addMuteCheckBoxActionListner(ActionListener actionPerformed) {
        muted.addActionListener(actionPerformed);
    }
}
