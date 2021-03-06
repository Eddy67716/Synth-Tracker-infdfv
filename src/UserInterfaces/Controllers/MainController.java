/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UserInterfaces.Controllers;

import UserInterfaces.Views.MainMenuBar;
import UserInterfaces.Views.MainUI;
import UserInterfaces.Views.Samples.SampleUI;
import UserInterfaces.Views.TabUI;
import UserInterfaces.viewModels.LoadViewModel;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
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
import Module.IAudioSample;

/**
 *
 * @author Edward Jenkins
 */
public class MainController {
    
    // instance variables
    private MainUI mainUI;
    private MainController mainController;
    private List<LoadViewModel> loadVMs;
    
    // constructor
    public MainController(MainUI mainUI) {
        
        // set up mainUI and actionListeners
        this.mainUI = mainUI;
        this.mainUI.addNewFileActionListener(e -> newFile());
        this.mainUI.addOpenFileActionListener(e -> loadFile());
        this.mainUI.addSaveFileActionListener(e -> saveFile());
        this.mainUI.addPlayActionListener(e -> play());
        this.mainUI.addStopActionListener(e -> stop());
        
        loadVMs = new ArrayList<>();
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
            
            // set up TabUI
            TabUI openedTab = new TabUI();
            TabController controller = new TabController(openedTab, loadVM);
            
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
            
            // set up sampleUI
            ArrayList<IAudioSample> samples 
                    = (ArrayList<IAudioSample>) loadVM.getSamples();
            
            SampleUI sampleUI = new SampleUI(samples, 
                    loadVM.getModuleFile().getModTypeID());
            SampleController sampleController = 
                    new SampleController(sampleUI, loadVM);
            
            openedTab.addSampleInterface(sampleUI);
            
            
        } catch (Exception e) {
            JFrame errorFrame = new JFrame();
            JOptionPane.showMessageDialog(errorFrame, "Error with file! \n" 
                    + e.getMessage(),
                    "Error with file", JOptionPane.ERROR_MESSAGE);
            errorFrame.setLocationRelativeTo(null);
        }
    }
    
    public void saveFile() {
        
    }
    
    public void play() {
        
    }
    
    public void stop() {
        
    }
    
    public void closeTab(String fileName) {
        
        int index = mainUI.getOpenedFilesTab().indexOfTab(fileName);
        
        if (index >= 0) {
            
            // get rid of module tab and associated module file 
            loadVMs.remove(index);
            mainUI.getOpenedFilesTab().remove(index);
            
            // delete unreferenced files
            System.gc();
        }
    }
}
