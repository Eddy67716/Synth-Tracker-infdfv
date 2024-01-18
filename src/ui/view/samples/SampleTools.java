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
import module.IAudioSample;

/**
 *
 * @author Edward Jenkins
 */
public class SampleTools extends JPanel {

    // instance variables
    private int modType;
    // this panel
    private GridBagLayout toolsLayout;
    private GridBagConstraints tc;
    // basics
    private IAudioSample sample;
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
    public SampleTools(int modType) {

        // set layout
        toolsLayout = new GridBagLayout();
        tc = new GridBagConstraints();
        tc.anchor = GridBagConstraints.NORTHWEST;
        tc.fill = GridBagConstraints.VERTICAL;
        this.setLayout(toolsLayout);
        this.modType = modType;

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
        
        // set the panels
        
        // add sample details to tools;
        sampleDetails = new SampleDetails(modType);
        tc.gridx = 0;
        tc.gridy = 0;
        tc.weighty = 1;
        tc.gridheight = 2;
        this.add(sampleDetails, tc);
        
        // add sound options to tools
        soundOptions = new SampleSoundOptions(modType);
        tc.gridx = 1;
        tc.gridy = 0;
        tc.weighty = 1;
        tc.gridheight = 2;
        this.add(soundOptions, tc);
        
        // add loop tools to tools
        loopingTools = new LoopingTools(modType);
        tc.gridx = 2;
        tc.gridy = 0;
        tc.weightx = 0.0;
        tc.weighty = 0.0;
        tc.gridwidth = 1;
        tc.gridheight = 1;
        this.add(loopingTools, tc);
        
        // add sustain loop tools to tools
        susLoopTools = new SustainLoopTools(modType);
        tc.gridx = 3;
        tc.gridy = 0;
        tc.weightx = 0.0;
        tc.weighty = 0.0;
        tc.gridwidth = 1;
        tc.gridheight = 1;
        this.add(susLoopTools, tc);
        
        // add sampling tools to tools
        samplingTools = new SamplingTools(modType);
        tc.gridx = 2;
        tc.gridy = 1;
        tc.weightx = 0.0;
        tc.weighty = 0.0;
        tc.gridwidth = 2;
        tc.gridheight = 1;
        this.add(samplingTools, tc);
        
        // add vibrato options to tools
        vibratoOptions = new VibratoOptions(modType);
        tc.gridx = 4;
        tc.gridy = 0;
        tc.weightx = 1.0;
        tc.weighty = 1.0;
        tc.gridwidth = 1;
        tc.gridheight = 1;
        this.add(vibratoOptions, tc);
    }
}
