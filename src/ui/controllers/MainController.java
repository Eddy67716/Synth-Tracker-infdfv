/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.controllers;

import ui.view.MainMenuBar;
import ui.view.MainUI;
import ui.view.samples.SampleUI;
import ui.view.TabUI;
import ui.view.models.LoadViewModel;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import lang.LanguageHandler;
import module.IInstrument;
import module.IPattern;
import ui.view.SettingsUI;
import ui.view.details.DetailsUI;
import ui.view.instruments.InstrumentUI;
import ui.view.models.ControllerViewModel;
import ui.view.pattens.PatternUI;
import module.ISampleSynth;

/**
 *
 * @author Edward Jenkins
 */
public class MainController implements IUndoable {

    // instance variables
    private LanguageHandler languageHandler;
    private MainUI mainUI;
    private List<TabController> tabControllers;
    private List<LoadViewModel> loadVMs;

    // constructor
    public MainController(MainUI mainUI) {

        // set up mainUI and actionListeners
        this.mainUI = mainUI;
        languageHandler = mainUI.getLanguageHandler();
        this.mainUI.addNewFileActionListener(e -> newFile());
        this.mainUI.addOpenFileActionListener(e -> loadFile());
        this.mainUI.addSaveFileActionListener(e -> saveFile());
        this.mainUI.addPlayActionListener(e -> play());
        this.mainUI.addStopActionListener(e -> stop());

        loadVMs = new ArrayList<>();
        tabControllers = new ArrayList<>();
    }

    // getters
    public MainUI getMainUI() {
        return this.mainUI;
    }

    // setters
    public void setMainUI(MainUI mainUI) {
        this.mainUI = mainUI;
    }

    public void newFile() {

    }

    public void loadFile() {

        LoadViewModel loadVM = new LoadViewModel();

        try {

            // read file
            String fileName = loadVM.readModuleFile();
            JPanel tabPanel;
            GridBagConstraints tabConstraints;
            JLabel titleLabel;
            JButton closeButton;
            int index;

            // when no file is selected
            if (fileName.equals("")) {
                return;
            }

            // check for no dulicate
            for (int i = 0; i < mainUI.getOpenedFilesTab().getTabCount(); i++) {
                if (fileName.equals(mainUI.getOpenedFilesTab().getTitleAt(i))) {
                    mainUI.getOpenedFilesTab().setSelectedIndex(i);
                    return;
                }
            }

            // add valid file to tab pane
            loadVMs.add(loadVM);

            // set the tab controller view model
            ControllerViewModel tabControllerViewModel
                    = new ControllerViewModel();

            // set up TabUI
            TabUI openedTab = new TabUI(languageHandler);
            TabController controller = new TabController(openedTab, loadVM,
                    tabControllerViewModel);

            mainUI.getOpenedFilesTab().add(fileName, openedTab);

            // set up tab with close button
            index = mainUI.getOpenedFilesTab().indexOfTab(fileName);
            tabPanel = new JPanel(new GridBagLayout());
            tabPanel.setOpaque(false);
            titleLabel = new JLabel(fileName);
            closeButton = new JButton("X");

            tabConstraints = new GridBagConstraints();

            tabConstraints.insets = new Insets(0, 5, 0, 5);
            tabConstraints.gridx = 0;
            tabConstraints.gridy = 0;
            tabConstraints.weightx = 1;

            tabPanel.add(titleLabel, tabConstraints);

            tabConstraints.gridx++;
            tabConstraints.weightx = 0;
            tabPanel.add(closeButton, tabConstraints);

            controller.setLists();

            mainUI.getOpenedFilesTab().setTabComponentAt(index, tabPanel);

            closeButton.addActionListener(e -> this.closeTab(fileName));

            // set up detailsUI
            setDetailsUI(openedTab, loadVM, tabControllerViewModel);

            // set up sampleUI
            setSampleUI(openedTab, loadVM, tabControllerViewModel);

            // set up instrumentUI
            setInstrumentUI(openedTab, loadVM, tabControllerViewModel);
            
            // set up patternsUI
            setPatternUI(openedTab, loadVM, tabControllerViewModel);
            
            // set up playChartUI
            // add value tab controller to list
            
            tabControllers.add(controller);

        } catch (Exception e) {
            JFrame errorFrame = new JFrame();
            JOptionPane.showMessageDialog(errorFrame, "Error with file! \n"
                    + e.getMessage(),
                    "Error with file", JOptionPane.ERROR_MESSAGE);
            errorFrame.setLocationRelativeTo(null);
        }
    }

    private void setDetailsUI(TabUI openedTab, LoadViewModel loadVM,
            ControllerViewModel tabControllerViewModel) {

        // setup detailsUI
        DetailsUI detailsUI = new DetailsUI(loadVM.getHeader(), 
                loadVM.getModuleFile().getModTypeID(), languageHandler);
        
        // details controller
        DetailsController detailsController = new DetailsController(detailsUI,
            loadVM);
        
        // add details controller to view model
        tabControllerViewModel.setDetailsController(detailsController);

        // add the detailUI
        openedTab.addDetailsInterface(detailsUI);
    }

    private void setSampleUI(TabUI openedTab, LoadViewModel loadVM,
            ControllerViewModel tabControllerViewModel) {

        ArrayList<ISampleSynth> samples
                = (ArrayList<ISampleSynth>) loadVM.getSamples();

        // sampleUI
        SampleUI sampleUI = new SampleUI(samples,
                loadVM.getModuleFile().getModTypeID(), languageHandler);

        // sampleController
        SampleController sampleController
                = new SampleController(sampleUI, loadVM);

        // add sample controller to view model
        tabControllerViewModel.setSampleController(sampleController);

        // add the sampleUI
        openedTab.addSampleInterface(sampleUI);
    }

    private void setInstrumentUI(TabUI openedTab, LoadViewModel loadVM,
            ControllerViewModel tabControllerViewModel) {

        ArrayList<IInstrument> instruments
                = (ArrayList<IInstrument>) loadVM.getInstruments();

        // instrumentUI
        InstrumentUI instrumentUI = new InstrumentUI(instruments,
                loadVM.getModuleFile().getModTypeID(), languageHandler);

        // add the instrumentUI
        openedTab.addInstrumentInterface(instrumentUI);

        // instrumentController
        InstrumentController instrumentController
                = new InstrumentController(instrumentUI, loadVM);

        // add instrument controller to view model
        tabControllerViewModel
                .setInstrumentController(instrumentController);
    }

    private void setPatternUI(TabUI openedTab, LoadViewModel loadVM,
            ControllerViewModel tabControllerViewModel) {
        
        ArrayList<IPattern> patterns
                = (ArrayList<IPattern>) loadVM.getPatterns();
        
        short[] orders = loadVM.getPatternOrder();

        // patternUI
        PatternUI patternUI = new PatternUI(patterns, orders,
                loadVM.getModuleFile().getModTypeID(), languageHandler);

        // add the patternUI
        openedTab.addPatternInterface(patternUI);

        // instrumentController
        PatternController patternColtroller
                = new PatternController(patternUI, loadVM);

        // add instrument controller to view model
        tabControllerViewModel
                .setPatternController(patternColtroller);
    }

    public void saveFile() {

    }

    public void play() {

    }

    public void stop() {

    }

    @Override
    public void undo() {
        if (!tabControllers.isEmpty()) {
            tabControllers.get(mainUI.getOpenedFilesTab().getSelectedIndex())
                .undo();
        } else {
            playErrorSound();
        }
    }

    @Override
    public void redo() {
        if (!tabControllers.isEmpty()) {
            tabControllers.get(mainUI.getOpenedFilesTab().getSelectedIndex())
                .redo();
        } else {
            playErrorSound();
        }
    }
    
    public void playErrorSound() {
        Toolkit.getDefaultToolkit().beep();
    }

    public void closeTab(String fileName) {

        int index = mainUI.getOpenedFilesTab().indexOfTab(fileName);

        if (index >= 0) {

            // get rid of module tab, controller and associated module file 
            loadVMs.remove(index);
            tabControllers.remove(index);
            mainUI.getOpenedFilesTab().remove(index);

            // delete unreferenced files
            System.gc();
        }
    }

    public void openSettings() {

        // run the frame
        SwingUtilities.invokeLater(() -> {
            new SettingsUI().setVisible(true);
        });
    }
}
