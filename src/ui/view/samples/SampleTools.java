/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.view.samples;

import ui.controllers.SampleController;
import ui.UIProperties;
import static ui.UIProperties.*;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JPanel;
import lang.LanguageHandler;
import module.ISampleSynth;

/**
 *
 * @author Edward Jenkins
 */
public class SampleTools extends JPanel {

    // instance variables
    private int modType;
    private LanguageHandler languageHandler;
    // this panel
    private GridBagLayout toolsLayout;
    private GridBagConstraints tc;
    // basics
    private ISampleSynth sample;
    // Sample details (File name, bitrate, channels, etc.)
    private SampleDetails sampleDetails;
    // sound options (volume, panning, etc.)
    private SampleSoundOptions soundOptions;
    // sampling tools (defining note frequency and C5 frequency)
    private SamplingTools samplingTools;
    // looping tools
    // all
    private LoopingTools loopingTools;              
    // sustain loop tools
    // it and str
    private SustainLoopTools susLoopTools;          
    // vibrato options
    // xm, it and str
    private VibratoOptions vibratoOptions;          
    // detuning options
    // str
    private JPanel detuningOptins;                 
    // adsr options
    // str
    private JPanel ADSROptions;                     
    // rotaty tremolo optoins
    // str
    private JPanel RotaryTremoloOptions;            

    // consturctor
    public SampleTools(int modType, LanguageHandler languageHandler) {

        
        this.modType = modType;
        this.languageHandler = languageHandler;

        init();
    }

    // getters
    public SampleDetails getSampleDetails() {
        return sampleDetails;
    }

    public SampleSoundOptions getSoundOptions() {
        return soundOptions;
    }

    public SamplingTools getSamplingTools() {
        return samplingTools;
    }

    public VibratoOptions getVibratoOptions() {
        return vibratoOptions;
    }

    public LoopingTools getLoopingTools() {
        return loopingTools;
    }

    public SustainLoopTools getSusLoopTools() {
        return susLoopTools;
    }

    public int getModType() {
        return modType;
    }
    
    public void init() {
        
        // set layout
        toolsLayout = new GridBagLayout();
        tc = new GridBagConstraints();
        tc.anchor = GridBagConstraints.SOUTHWEST;
        tc.fill = GridBagConstraints.VERTICAL;
        tc.insets = DEF_INSETS;
        this.setLayout(toolsLayout);
        
        // add sample details to tools;
        sampleDetails = new SampleDetails(modType, languageHandler);
        tc.gridx = 0;
        tc.gridy = 0;
        tc.weighty = 1.0;
        tc.gridheight = GridBagConstraints.REMAINDER;
        this.add(sampleDetails, tc);
        
        // add sound options to tools
        soundOptions = new SampleSoundOptions(modType, languageHandler);
        tc.gridx = 1;
        tc.gridy = 0;
        tc.gridheight = GridBagConstraints.REMAINDER;
        this.add(soundOptions, tc);
        
        // add loop tools to tools
        loopingTools = new LoopingTools(modType, languageHandler);
        tc.gridx = 2;
        tc.gridy = 0;
        tc.gridwidth = 1;
        tc.gridheight = 1;
        this.add(loopingTools, tc);
        
        // add sustain loop tools to tools
        susLoopTools = new SustainLoopTools(modType, languageHandler);
        tc.gridx = 3;
        tc.gridy = 0;
        tc.gridwidth = 1;
        tc.gridheight = 1;
        this.add(susLoopTools, tc);
        
        // add sampling tools to tools
        samplingTools = new SamplingTools(modType, languageHandler);
        tc.gridx = 2;
        tc.gridy = 1;
        tc.gridwidth = 2;
        tc.gridheight = GridBagConstraints.REMAINDER;
        this.add(samplingTools, tc);
        
        // add bottom trailing JPanel
        tc.gridx = 4;
        tc.gridy = 1;
        tc.weightx = 1.0;
        tc.gridwidth = GridBagConstraints.REMAINDER;
        tc.gridheight = GridBagConstraints.REMAINDER;
        this.add(new JPanel(), tc);
        
        // add vibrato options to tools
        vibratoOptions = new VibratoOptions(modType, languageHandler);
        tc.gridx = 4;
        tc.gridy = 0;
        tc.weightx = 0;
        tc.gridwidth = GridBagConstraints.REMAINDER;
        tc.gridheight = 1;
        this.add(vibratoOptions, tc);
        
        // add trailing JPanel
        tc.gridx = 5;
        tc.gridy = 0;
        tc.weightx = 1.0;
        tc.gridwidth = GridBagConstraints.REMAINDER;
        tc.gridheight = 1;
        this.add(new JPanel(), tc);
    }
}
