/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UserInterfaces.Controllers;

import Module.IInstrument;
import Module.IPattern;
import UserInterfaces.Views.TabUI;
import UserInterfaces.viewModels.LoadViewModel;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.List;
import Module.IAudioSample;

/**
 *
 * @author Edward Jenkins
 */
public class TabController {
    
    // instance variables
    private TabUI tabUI;
    private LoadViewModel loadVM;
    private String[] sampleNames;
    private String[] instrumentNames;
    private String[] patternNames;
    
    // constructor
    public TabController(TabUI tabUI, LoadViewModel loadVM) {
        
        // set tabUI and actionListeners
        this.tabUI = tabUI;
        this.loadVM = loadVM;
        this.tabUI.addPatternToggleActionListener(e -> expandPatterns());
        this.tabUI.addSampleToggleActionListener(e -> expandSamples());
        this.tabUI.addInstrumentToggleActionListener(e -> expandInstruments());
        
        // set samples
        List<IAudioSample> samples = loadVM.getSamples();
        
        sampleNames = new String[samples.size()];
        
        for (int i = 0; i < samples.size(); i++) {
            sampleNames[i] = i + " " + samples.get(i).getSampleName();
        }
        
        // set instruments
        List<IInstrument> instruments = loadVM.getInstruments();
        
        instrumentNames = new String[instruments.size()];
        
        for (int i = 0; i < instruments.size(); i++) {
            instrumentNames[i] = i + " " + instruments.get(i)
                    .getInstrumentName();
        }
        
        // set patterns
        List<IPattern> patterns = loadVM.getPatterns();
        
        patternNames = new String[patterns.size()];
        
        for (int i = 0; i < patterns.size(); i++) {
            patternNames[i] = i + " " + patterns.get(i).getName();
        }
    }
    
    // methods
    public void expandInstruments() {        
        tabUI.getInstrumentList()
                .setVisible(!tabUI.getInstrumentList().isVisible());
    }                                                      

    public void expandSamples() {                                                   
        tabUI.getSampleList()
                .setVisible(!tabUI.getSampleList().isVisible());
    }                                                  

    public void expandPatterns() {                                                    
        tabUI.getPatternList()
                .setVisible(!tabUI.getPatternList().isVisible());
    }
    
    public void setLists() {
        tabUI.getSampleList().setListData(sampleNames);
        tabUI.getSampleList().setPreferredSize(
                new Dimension(tabUI.getSampleList().getWidth(), 
                        sampleNames.length * 18));
        tabUI.getInstrumentList().setListData(instrumentNames);
        tabUI.getInstrumentList().setPreferredSize(
                new Dimension(tabUI.getInstrumentList().getWidth(), 
                        instrumentNames.length * 18));
        tabUI.getPatternList().setListData(patternNames);
        tabUI.getPatternList().setPreferredSize(
                new Dimension(tabUI.getPatternList().getWidth(), 
                        patternNames.length * 18));
    }
}
