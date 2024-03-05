/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.view.details;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import static ui.UIProperties.BOLD_FONT;
import static ui.UIProperties.DEF_FONT;
import static ui.UIProperties.DEF_INSETS;
import static ui.UIProperties.LARGE_FIELD_SIZE;

/**
 *
 * @author Edward Jenkins
 */
public class ModuleDetails extends JPanel {

    // instance variables
    private int modType;
    private GridBagLayout detailsLayout;
    private GridBagConstraints dc;
    private Border detailsBorder;
    // all
    private JLabel songNameLabel;
    private JTextField songNameField;
    // STR
    private JLabel songAuthorLabel;
    private JTextField songAuthorField;
    // s3m, IT and STR
    private JLabel audioChannelsLabel;
    private ButtonGroup channelGroup;
    private JRadioButton stereoOption;
    private JRadioButton monoOption;
    private JLabel innstrumentTypeLabel;
    // XM, IT and STR
    private ButtonGroup instrumentGroup;
    private JRadioButton sampleOption;
    private JRadioButton instrumentOption;
    // STR
    private JRadioButton mappedOption;
    // s3m, XM, IT and STR
    private JLabel slidesLabal;
    private ButtonGroup slideGroup;
    private JRadioButton linearSlidesOption;
    private JRadioButton amigaSlidesOption;
    // IT and STR
    private JLabel effectsLabal;
    private ButtonGroup effectGroup;
    private JRadioButton oldEffectsOption;
    private JRadioButton newEffecstOption;
    // STR options
    private JLabel initialValueResetLabal;
    private JCheckBox initialValueReset;
    private JLabel strPrecisionLabal;
    private JCheckBox strPrecision;
    private JLabel usingTwoEffectsLabal;
    private JCheckBox usingTwoEffects;
    private JLabel millisecondBaseOffsetLabal;
    private JCheckBox millisecondBaseOffset;
    private JLabel constantInstrumentRateLabal;
    private JCheckBox constantInstrumentRate;
    // s3m, XM, IT and STR
    private JLabel channelLabel;
    private JSpinner channelSpinner;
    private JLabel globalVolumeLabel;
    private JTextField globalVolumeField;
    // s3m, IT and STR
    private JLabel mixVolumeLabel;
    private JTextField mixVolumeField;
    // s3m, IT and STR,
    private JLabel panSeparationLabel;
    private JTextField panSeparationField;
    
    

    // constructor
    public ModuleDetails(int modType) {
        this.modType = modType;
        init();
    }

    public void init() {

        // set the layout
        detailsLayout = new GridBagLayout();
        setLayout(detailsLayout);
        dc = new GridBagConstraints();
        dc.anchor = GridBagConstraints.NORTHWEST;
        dc.insets = DEF_INSETS;

        // set the border
        detailsBorder
                = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);

        // set the border title
        detailsBorder
                = BorderFactory.createTitledBorder(detailsBorder,
                        "Module details", 0, 0, BOLD_FONT);

        // set options border
        setBorder(detailsBorder);

        // song name label
        songNameLabel = new JLabel("Song name: ");
        songNameLabel.setFont(DEF_FONT);
        
        dc.gridx = 0;
        dc.gridy = 0;
        add(songNameLabel, dc);

        // song name field
        songNameField = new JTextField();
        songNameField.setToolTipText("Name of song");
        songNameField.setPreferredSize(LARGE_FIELD_SIZE);

        dc.fill = GridBagConstraints.HORIZONTAL;
        dc.gridx = 0;
        dc.gridy = 1;
        add(songNameField, dc);
        
        // song author label
        songAuthorLabel = new JLabel("Song author: ");
        songAuthorLabel.setFont(DEF_FONT);
        
        dc.gridx = 0;
        dc.gridy = 2;
        add(songAuthorLabel, dc);

        // song author field
        songAuthorField = new JTextField();
        songAuthorField.setToolTipText("Writer of song");
        songAuthorField.setPreferredSize(LARGE_FIELD_SIZE);

        dc.fill = GridBagConstraints.HORIZONTAL;
        dc.gridx = 0;
        dc.gridy = 3;
        dc.weighty = 1.0;
        dc.gridheight = GridBagConstraints.REMAINDER;
        add(songAuthorField, dc);

        // audio label
        audioChannelsLabel = new JLabel("Audio: ");
        audioChannelsLabel.setFont(DEF_FONT);

        dc.fill = GridBagConstraints.NONE;
        dc.gridx = 1;
        dc.gridy = 0;
        dc.weighty = 0;
        dc.gridwidth = 2;
        dc.gridheight = 1;
        add(audioChannelsLabel, dc);

        // stereo
        stereoOption = new JRadioButton("Stereo");

        dc.gridx = 1;
        dc.gridy = 1;
        dc.weightx = 0;
        dc.weighty = 0;
        dc.gridwidth = 1;
        add(stereoOption, dc);

        // mono
        monoOption = new JRadioButton("Mono");

        dc.gridx = 2;
        dc.gridy = 1;
        dc.weightx = 0;
        dc.weighty = 0;
        add(monoOption, dc);
        
        // button structure
        channelGroup = new ButtonGroup();
        channelGroup.add(monoOption);
        channelGroup.add(stereoOption);
        
        // slides
        slidesLabal = new JLabel("Slides: ");
        slidesLabal.setFont(DEF_FONT);

        dc.gridx = 1;
        dc.gridy = 2;
        dc.weightx = 0;
        add(slidesLabal, dc);

        // linear
        linearSlidesOption = new JRadioButton("Linear");
        linearSlidesOption.setToolTipText("Slides are tone linar");

        dc.gridx = 1;
        dc.gridy = 3;
        dc.weightx = 0;
        dc.weighty = 0;
        dc.gridwidth = 1;
        dc.gridheight = GridBagConstraints.REMAINDER;
        add(linearSlidesOption, dc);

        // amiga
        amigaSlidesOption = new JRadioButton("Amiga");
        amigaSlidesOption.setToolTipText("Slides are tone logarithmic");

        dc.gridx = 2;
        dc.gridy = 3;
        dc.weightx = 0;
        dc.weighty = 0;
        dc.gridwidth = GridBagConstraints.REMAINDER;
        dc.gridheight = GridBagConstraints.REMAINDER;
        add(amigaSlidesOption, dc);
        
        // button structure
        slideGroup = new ButtonGroup();
        slideGroup.add(linearSlidesOption);
        slideGroup.add(amigaSlidesOption);
        
        // effects
        effectsLabal = new JLabel("Effects: ");
        effectsLabal.setFont(DEF_FONT);

        dc.gridx = 3;
        dc.gridy = 0;
        dc.weightx = 1.0;
        dc.gridwidth = GridBagConstraints.REMAINDER;
        add(effectsLabal, dc);

        // old
        oldEffectsOption = new JRadioButton("Old");
        oldEffectsOption.setToolTipText("Process every non-row tick.");

        dc.gridx = 3;
        dc.gridy = 1;
        dc.weightx = 0;
        dc.weighty = 1.0;
        dc.gridwidth = 1;
        dc.gridheight = GridBagConstraints.REMAINDER;
        add(oldEffectsOption, dc);

        // new
        newEffecstOption = new JRadioButton("New");
        newEffecstOption.setToolTipText("Process every single tick.");

        dc.gridx = 4;
        dc.gridy = 1;
        dc.weightx = 1.0;
        dc.weighty = 1.0;
        dc.gridwidth = GridBagConstraints.REMAINDER;
        dc.gridheight = GridBagConstraints.REMAINDER;
        add(newEffecstOption, dc);
        
        // button structure
        effectGroup = new ButtonGroup();
        effectGroup.add(newEffecstOption);
        effectGroup.add(oldEffectsOption);
    }
}
