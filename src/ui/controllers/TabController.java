/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.controllers;

import module.IInstrument;
import module.IPattern;
import ui.view.TabUI;
import ui.view.models.LoadViewModel;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.List;
import ui.view.models.ControllerViewModel;
import module.ISampleSynth;

/**
 *
 * @author Edward Jenkins
 */
public class TabController implements IUndoable {

    // instance variables
    private TabUI tabUI;
    private LoadViewModel loadVM;
    private ControllerViewModel tabControllers;
    private String[] sampleNames;
    private String[] instrumentNames;
    private String[] patternNames;

    // constructor
    public TabController(TabUI tabUI, LoadViewModel loadVM, 
            ControllerViewModel tabControllers) {

        // set tabUI and actionListeners
        this.tabUI = tabUI;
        this.loadVM = loadVM;
        this.tabControllers = tabControllers;
        this.tabUI.addPatternToggleActionListener(e -> expandPatterns());
        this.tabUI.addSampleToggleActionListener(e -> expandSamples());
        this.tabUI.addInstrumentToggleActionListener(e -> expandInstruments());
        this.tabUI.addSampleListSelectionListener(e -> navigateToSample());
        this.tabUI.addInstrumentListSelectionListener(
                e -> navigateToInstrument());
        this.tabUI.addPatternListSelectionListener(e -> navigateToPattern());

        // set samples
        List<ISampleSynth> samples = loadVM.getSamples();

        sampleNames = new String[samples.size()];

        for (int i = 0; i < samples.size(); i++) {
            sampleNames[i] = (i + 1) + " " + samples.get(i).getSampleName();
        }

        // set instruments
        List<IInstrument> instruments = loadVM.getInstruments();

        instrumentNames = new String[instruments.size()];

        for (int i = 0; i < instruments.size(); i++) {
            instrumentNames[i] = (i + 1) + " " + instruments.get(i)
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

    // navigates to selected sample in sampleUI
    public void navigateToSample() {

        // get selection index
        int selectedIndex = tabUI.getSampleList().getSelectedIndex();

        // set to sample window (index 2)
        tabUI.getModDataInterface().setSelectedIndex(2);

        // set spinner value to selected index value
        tabUI.getSampleUI().getSampleSelectSpinner()
                .setValue(selectedIndex + 1);
    }

    // navigates to selected instrument in instrumentUI
    public void navigateToInstrument() {

        // get selection index
        int selectedIndex = tabUI.getInstrumentList().getSelectedIndex();

        // set to instrument window (index 3)
        tabUI.getModDataInterface().setSelectedIndex(3);

        // set spinner value to selected index value
        tabUI.getInstrumentUI().getInstrumentSelectSpinner()
                .setValue(selectedIndex + 1);
    }
    
    // navigates to selected pattern in patternUI
    public void navigateToPattern() {
        // get selection index
        int selectedIndex = tabUI.getPatternList().getSelectedIndex();

        // set to instrument window (index 3)
        tabUI.getModDataInterface().setSelectedIndex(1);

        // set pattern controller to selected pattern
        this.tabControllers.getPatternController()
                .viewListPattern(selectedIndex);
    }
    
    @Override
    public void undo() {
        tabControllers.undo(tabUI.getModDataInterface().getSelectedIndex());
    }
    
    @Override
    public void redo() {
        tabControllers.redo(tabUI.getModDataInterface().getSelectedIndex());
    }
}
