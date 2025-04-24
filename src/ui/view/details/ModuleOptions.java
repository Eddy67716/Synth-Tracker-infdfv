/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.view.details;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import lang.LanguageHandler;
import static ui.UIProperties.BOLD_FONT;
import static ui.UIProperties.DEF_FONT;
import static ui.UIProperties.DEF_INSETS;

/**
 *
 * @author Edward Jenkins
 */
public class ModuleOptions extends JPanel {
    
    // instance variables
    private int modType;
    private final LanguageHandler languageHandler;
    private GridBagLayout optionsLayout;
    private GridBagConstraints moc;
    private Border optionsBorder;
    // s3m, IT and STR
    private JLabel audioChannelsLabel;
    private ButtonGroup channelGroup;
    private JRadioButton stereoOption;
    private JRadioButton monoOption;
    private JLabel innstrumentTypeLabel;
    // XM, IT and STR
    private JLabel instrumentTypeLabel;
    private ButtonGroup instrumentGroup;
    private JRadioButton instrumentOption;
    private JRadioButton sampleOption;
    // STR
    private JRadioButton mappedOption;
    // XM, IT and STR
    private JLabel slidesLabal;
    private ButtonGroup slideGroup;
    private JRadioButton linearSlidesOption;
    private JRadioButton amigaSlidesOption;
    // IT and STR
    private JLabel effectsLabal;
    private ButtonGroup effectGroup;
    private JRadioButton oldEffectsOption;
    private JRadioButton newEffecstOption;
    // IT and STR
    private JCheckBox portamentoLink;
    private JCheckBox usingPitchWheel; 
    // STR options
    private JCheckBox initialValueReset;
    private JCheckBox strPrecision;
    private JCheckBox usingTwoEffects;
    private JCheckBox millisecondBaseOffset;
    private JCheckBox constantInstrumentRate;
    
    public ModuleOptions(int modType, LanguageHandler languageHandler) {
        this.modType = modType;
        this.languageHandler = languageHandler;
        init();
    }
    
    // getters
    public ButtonGroup getChannelGroup() {
        return channelGroup;
    }
    
    public JRadioButton getStereoOption() {
        return stereoOption;
    }

    public JRadioButton getMonoOption() {
        return monoOption;
    }

    public ButtonGroup getInstrumentGroup() {
        return instrumentGroup;
    }

    public JRadioButton getInstrumentOption() {
        return instrumentOption;
    }

    public JRadioButton getSampleOption() {
        return sampleOption;
    }

    public JRadioButton getMappedOption() {
        return mappedOption;
    }

    public ButtonGroup getSlideGroup() {
        return slideGroup;
    }

    public JRadioButton getLinearSlidesOption() {
        return linearSlidesOption;
    }

    public JRadioButton getAmigaSlidesOption() {
        return amigaSlidesOption;
    }

    public ButtonGroup getEffectGroup() {
        return effectGroup;
    }

    public JRadioButton getOldEffectsOption() {
        return oldEffectsOption;
    }

    public JRadioButton getNewEffecstOption() {
        return newEffecstOption;
    }

    public JCheckBox getPortamentoLink() {
        return portamentoLink;
    }

    public JCheckBox getInitialValueReset() {
        return initialValueReset;
    }

    public JCheckBox getStrPrecision() {
        return strPrecision;
    }

    public JCheckBox getUsingTwoEffects() {
        return usingTwoEffects;
    }

    public JCheckBox getMillisecondBaseOffset() {
        return millisecondBaseOffset;
    }

    public JCheckBox getConstantInstrumentRate() {
        return constantInstrumentRate;
    }
    
    public void init() {
        
        // set the layout
        optionsLayout = new GridBagLayout();
        setLayout(optionsLayout);
        moc = new GridBagConstraints();
        moc.anchor = GridBagConstraints.SOUTHWEST;
        moc.insets = DEF_INSETS;

        // set the border
        optionsBorder
                = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);

        // set the border title
        optionsBorder
                = BorderFactory.createTitledBorder(optionsBorder,
                        languageHandler
                .getLanguageText("module.options"), 0, 0, BOLD_FONT);

        // set options border
        setBorder(optionsBorder);
        
        // audio label
        audioChannelsLabel = new JLabel(languageHandler
                .getLanguageText("module.options.audio"));
        audioChannelsLabel.setFont(DEF_FONT);

        moc.fill = GridBagConstraints.NONE;
        moc.gridx = 0;
        moc.gridy = 0;
        moc.weighty = 0;
        moc.gridwidth = 2;
        moc.gridheight = 1;
        add(audioChannelsLabel, moc);

        // stereo
        stereoOption = new JRadioButton(languageHandler
                .getLanguageText("sound.channels.stereo"));

        moc.gridx = 0;
        moc.gridy = 1;
        moc.weightx = 0;
        moc.weighty = 0;
        moc.gridwidth = 1;
        add(stereoOption, moc);

        // mono
        monoOption = new JRadioButton(languageHandler
                .getLanguageText("sound.channels.mono"));

        moc.gridx = 1;
        moc.gridy = 1;
        moc.weightx = 0;
        moc.weighty = 0;
        add(monoOption, moc);
        
        // button structure
        channelGroup = new ButtonGroup();
        channelGroup.add(monoOption);
        channelGroup.add(stereoOption);
        
        // slides
        slidesLabal = new JLabel(languageHandler
                .getLanguageText("module.options.slides"));
        slidesLabal.setFont(DEF_FONT);

        moc.gridx = 0;
        moc.gridy = 2;
        moc.weightx = 0;
        add(slidesLabal, moc);

        // linear
        linearSlidesOption = new JRadioButton(languageHandler
                .getLanguageText("module.options.slides.linear"));
        linearSlidesOption.setToolTipText(languageHandler
                .getLanguageText("module.options.slides.linear.desc"));

        moc.gridx = 0;
        moc.gridy = 3;
        moc.weightx = 0;
        moc.weighty = 0;
        moc.gridwidth = 1;
        add(linearSlidesOption, moc);

        // amiga
        amigaSlidesOption = new JRadioButton(languageHandler
                .getLanguageText("module.options.slides.amiga"));
        amigaSlidesOption.setToolTipText(languageHandler
                .getLanguageText("module.options.slides.amiga.desc"));

        moc.gridx = 1;
        moc.gridy = 3;
        moc.weightx = 0;
        moc.gridwidth = GridBagConstraints.REMAINDER;
        add(amigaSlidesOption, moc);
        
        // button structure
        slideGroup = new ButtonGroup();
        slideGroup.add(linearSlidesOption);
        slideGroup.add(amigaSlidesOption);
        
        // effects
        effectsLabal = new JLabel(languageHandler
                .getLanguageText("module.options.effects"));
        effectsLabal.setFont(DEF_FONT);

        moc.gridx = 2;
        moc.gridy = 0;
        moc.weightx = 1.0;
        moc.gridwidth = GridBagConstraints.REMAINDER;
        add(effectsLabal, moc);

        // old
        oldEffectsOption = new JRadioButton(languageHandler
                .getLanguageText("module.options.effects.old"));
        oldEffectsOption.setToolTipText(languageHandler
                .getLanguageText("module.options.effects.old.desc"));

        moc.gridx = 2;
        moc.gridy = 1;
        moc.weightx = 0;
        moc.gridwidth = 1;
        add(oldEffectsOption, moc);

        // new
        newEffecstOption = new JRadioButton(languageHandler
                .getLanguageText("module.options.effects.new"));
        newEffecstOption.setToolTipText(languageHandler
                .getLanguageText("module.options.effects.new.desc"));

        moc.gridx = 3;
        moc.gridy = 1;
        moc.weightx = 1.0;
        moc.gridwidth = GridBagConstraints.REMAINDER;
        add(newEffecstOption, moc);
        
        // button structure
        effectGroup = new ButtonGroup();
        effectGroup.add(newEffecstOption);
        effectGroup.add(oldEffectsOption);
        
        // instrument type 
        innstrumentTypeLabel = new JLabel(languageHandler
                .getLanguageText("module.options.instrument_type"));
        innstrumentTypeLabel.setFont(DEF_FONT);

        moc.gridx = 2;
        moc.gridy = 2;
        moc.weightx = 1.0;
        moc.gridwidth = GridBagConstraints.REMAINDER;
        add(innstrumentTypeLabel, moc);

        // synths (samples)
        sampleOption = new JRadioButton(languageHandler
                .getLanguageText("module.options.instrument_type.synth"));
        sampleOption.setToolTipText(languageHandler
                .getLanguageText("module.options.instrument_type.synth.desc"));

        moc.gridx = 2;
        moc.gridy = 3;
        moc.weightx = 0;
        moc.gridwidth = 1;
        add(sampleOption, moc);

        // instruments
        instrumentOption = new JRadioButton(languageHandler
                .getLanguageText("module.options.instrument_type.inst"));
        instrumentOption.setToolTipText(languageHandler
                .getLanguageText("module.options.instrument_type.inst.desc"));

        moc.gridx = 3;
        moc.gridy = 3;
        moc.weightx = 1.0;
        moc.gridwidth = GridBagConstraints.REMAINDER;
        add(instrumentOption, moc);
        
        // button structure
        instrumentGroup = new ButtonGroup();
        instrumentGroup.add(sampleOption);
        instrumentGroup.add(instrumentOption);
        
        // EF to G link
        portamentoLink = new JCheckBox(languageHandler
                .getLanguageText("module.optoins.memory_e_f_link"));
        
        moc.gridx = 0;
        moc.gridy = 4;
        moc.weightx = 1.0;
        moc.gridwidth = GridBagConstraints.REMAINDER;
        add(portamentoLink, moc);
        
        // add trailing JPanel
        moc.gridx = 0;
        moc.gridy++;
        moc.weighty = 1.0;
        moc.gridheight = GridBagConstraints.REMAINDER;

        add(new JPanel(), moc);
    }
    
    // events and listeners
    public void addStereoOptionsAction(ActionListener actionPerformed) {
        this.stereoOption.addActionListener(actionPerformed);
    }
    
    public void addMonoOptionsAction(ActionListener actionPerformed) {
        this.monoOption.addActionListener(actionPerformed);
    }
    
    public void addInstrumentOptionsAction(ActionListener actionPerformed) {
        this.instrumentOption.addActionListener(actionPerformed);
    }
    
    public void addSampleOptionsAction(ActionListener actionPerformed) {
        this.sampleOption.addActionListener(actionPerformed);
    }
    
    public void addLinearSlideOptionsAction(ActionListener actionPerformed) {
        this.linearSlidesOption.addActionListener(actionPerformed);
    }
    
    public void addAmigaSlideOptionsAction(ActionListener actionPerformed) {
        this.amigaSlidesOption.addActionListener(actionPerformed);
    }
    
    public void addOldEffectOptionsAction(ActionListener actionPerformed) {
        this.oldEffectsOption.addActionListener(actionPerformed);
    }
    
    public void addNewEffectOptionsAction(ActionListener actionPerformed) {
        this.newEffecstOption.addActionListener(actionPerformed);
    }
    
    public void addPortamentoLinkAction(ActionListener actionPerformed) {
        this.portamentoLink.addActionListener(actionPerformed);
    }
}
