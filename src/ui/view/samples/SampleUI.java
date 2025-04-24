/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.view.samples;

import static ui.UIProperties.VALUE_SPINNER_SIZE;
import static ui.UIProperties.DEF_INSETS;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeListener;
import lang.LanguageHandler;
import module.ISampleSynth;

/**
 *
 * @author Edward Jenkins
 */
public class SampleUI extends JPanel {

    // instance variables
    private int selectedSample;
    private int modID;
    private final LanguageHandler languageHandler;
    // toolbar for pannel
    private GridBagLayout sampleToolBarLayout;
    private GridBagConstraints sampleTBC;
    private JToolBar sampleToolBar;
    private JButton newSampleButton;
    private JButton openSampleButton;
    private JButton saveSampleButton;
    private JButton deleteButton;
    private JSeparator fileSelectorSeparator;
    private SpinnerNumberModel sampleSelectorModel;
    private JPanel sampleSelectPanel;
    private JLabel sampleSelectLabel;
    private GridBagLayout sampleSelectLayout;
    private GridBagConstraints sampleSC;
    private JSpinner sampleSelectSpinner;
    private JSeparator selectorPlaySeparator;
    private JButton playSampleButton;
    private JButton stopSampleButton;
    private JButton resampleButton;
    private JSeparator playTypeSeparator;
    private JComboBox sampleTypeComboBox;
    
    // other windows
    private JSplitPane splitPane;
    private SampleTools sampleTools;
    private SampleWindow sampleWindow;

    public SampleUI(ArrayList<ISampleSynth> samples, int modID, 
            LanguageHandler languageHandler) {
        
        this.modID = modID;
        this.languageHandler = languageHandler;
        
        // set layout
        this.setLayout(new BorderLayout());
        
        // set selected sample
        selectedSample = 1;
        
        // initialise the UI windows
        this.sampleTools = new SampleTools(modID, languageHandler);
        this.sampleWindow = new SampleWindow(modID);
        
        // toolbar
        this.sampleToolBar = new JToolBar();
        sampleToolBarLayout = new GridBagLayout();
        sampleTBC = new GridBagConstraints();
        newSampleButton = new JButton();
        openSampleButton = new JButton();
        saveSampleButton = new JButton();
        deleteButton = new JButton();
        fileSelectorSeparator = new JSeparator();
        sampleSelectorModel 
                = new SpinnerNumberModel(selectedSample, 0, samples.size() + 1,
                        1);
        sampleSelectPanel = new JPanel();
        sampleSelectLayout = new GridBagLayout();
        sampleSC = new GridBagConstraints();
        sampleSelectLabel = new JLabel(languageHandler
                .getLanguageText("synth"));
        sampleSelectSpinner = new JSpinner(sampleSelectorModel);
        selectorPlaySeparator = new JSeparator();
        playSampleButton = new JButton();
        stopSampleButton = new JButton();
        playTypeSeparator = new JSeparator();
        
        init();
    }
    
    // getters
    public LanguageHandler getLanguageHandler() {
        return languageHandler;
    }
    
    public SampleTools getTools() {
        return this.sampleTools;
    }
    
    public SampleWindow getSampleWindow() {
        return this.sampleWindow;
    }

    public int getSelectedSample() {
        return selectedSample;
    }

    public JSpinner getSampleSelectSpinner() {
        return sampleSelectSpinner;
    }

    // setters
    public void setSelectedSample(int selectedSample) {
        this.selectedSample = selectedSample;
    }    
    
    private void init() {
        
        // toolbar
        sampleToolBar.setRollover(true);
        sampleToolBar.setFloatable(false);
        sampleToolBar.setLayout(sampleToolBarLayout);
        
        // sampleTBC
        sampleTBC.anchor = GridBagConstraints.NORTHWEST;
        sampleTBC.gridx = 0;
        sampleTBC.gridy = 0;
        sampleTBC.insets = DEF_INSETS;
        sampleTBC.weightx = 0.0;
        sampleTBC.gridwidth = 1;
        
        // new sample
        newSampleButton.setIcon(new javax.swing.ImageIcon(getClass()
                .getResource("/ui/assets/New.png")));
        newSampleButton.setToolTipText(languageHandler
                .getLanguageText("synth.options.new"));
        newSampleButton.setFocusable(false);
        newSampleButton
                .setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        newSampleButton
                .setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        sampleToolBar.add(newSampleButton, sampleTBC);
        
        sampleTBC.gridx++;
        sampleTBC.weightx = 0.0;
        sampleTBC.gridwidth = 1;
        
        // open sample
        openSampleButton.setIcon(new javax.swing.ImageIcon(getClass()
                .getResource("/ui/assets/Open.png")));
        openSampleButton.setToolTipText(languageHandler
                .getLanguageText("synth.options.open"));
        openSampleButton.setFocusable(false);
        openSampleButton
                .setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        openSampleButton
                .setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        sampleToolBar.add(openSampleButton, sampleTBC);
        
        sampleTBC.gridx++;
        sampleTBC.weightx = 0.0;
        sampleTBC.gridwidth = 1;
        
        // save sample
        saveSampleButton.setIcon(new javax.swing.ImageIcon(getClass()
                .getResource("/ui/assets/Filled save icon.png")));
        saveSampleButton.setToolTipText(languageHandler
                .getLanguageText("synth.options.save"));
        saveSampleButton.setFocusable(false);
        saveSampleButton
                .setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        saveSampleButton
                .setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        sampleToolBar.add(saveSampleButton, sampleTBC);
        
        sampleTBC.gridx++;
        sampleTBC.weightx = 0.0;
        sampleTBC.gridwidth = 1;
        
        // delete sample
        deleteButton.setIcon(new javax.swing.ImageIcon(getClass()
                .getResource("/ui/assets/Delete.png")));
        deleteButton.setToolTipText(languageHandler
                .getLanguageText("synth.options.delete"));
        deleteButton.setFocusable(false);
        deleteButton
                .setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        deleteButton
                .setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        sampleToolBar.add(deleteButton, sampleTBC);
        
        sampleTBC.gridx++;
        sampleTBC.weightx = 0.0;
        sampleTBC.gridwidth = 1;
        sampleTBC.anchor = GridBagConstraints.CENTER;
        
        // separator
        sampleToolBar.add(fileSelectorSeparator, sampleTBC);
        
        sampleTBC.gridx++;
        sampleTBC.weightx = 0.0;
        sampleTBC.gridwidth = 1;
        sampleTBC.anchor = GridBagConstraints.NORTHWEST;
        
        // sampleSelectPanel
        sampleSelectPanel.setLayout(sampleSelectLayout);
                
        sampleSC.anchor = GridBagConstraints.NORTHWEST;
        sampleSC.gridx = 0;
        sampleSC.gridy = 0;
        sampleSC.insets = DEF_INSETS;
        sampleSC.weightx = 1.0;
        sampleSC.gridwidth = GridBagConstraints.REMAINDER;
        
        sampleSelectPanel.add(sampleSelectLabel, sampleSC);
        
        sampleSC.gridy = 1;
        
        sampleSelectSpinner.setPreferredSize(VALUE_SPINNER_SIZE);
        
        sampleSelectPanel.add(sampleSelectSpinner, sampleSC);
        
        sampleToolBar.add(sampleSelectPanel, sampleTBC);
        
        sampleTBC.gridx++;
        sampleTBC.weightx = 0.0;
        sampleTBC.gridwidth = 1;
        
        // separator
        setSampleTypeList();
        sampleToolBar.add(selectorPlaySeparator, sampleTBC);
        
        sampleTBC.gridx++;
        sampleTBC.weightx = 0.0;
        sampleTBC.gridwidth = 1;
        
        // play
        playSampleButton.setIcon(new javax.swing.ImageIcon(getClass()
                .getResource("/ui/assets/Play Pause.png")));
        playSampleButton.setToolTipText(languageHandler
                .getLanguageText("synth.options.play"));
        playSampleButton.setFocusable(false);
        playSampleButton
                .setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        playSampleButton
                .setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        sampleToolBar.add(playSampleButton, sampleTBC);
        
        sampleTBC.gridx++;
        sampleTBC.weightx = 0.0;
        sampleTBC.gridwidth = 1;
        
        // separator
        sampleToolBar.add(selectorPlaySeparator, sampleTBC);
        
        sampleTBC.gridx++;
        sampleTBC.weightx = 0.0;
        
        // sample type combo box
        sampleToolBar.add(sampleTypeComboBox, sampleTBC);
        
        sampleTBC.gridx++;
        sampleTBC.weightx = 1;
        sampleTBC.gridwidth = 1;
        
        sampleToolBar.add(new JPanel(), sampleTBC);
        
        // add toolbar
        this.add(sampleToolBar, BorderLayout.NORTH);
        
        // initialise the split pane
        splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, sampleTools, 
                sampleWindow);
        
        this.add(splitPane, BorderLayout.CENTER);
    }
    
    private void setSampleTypeList() {
        
        sampleTypeComboBox = new JComboBox();
        String[] elements = {
            languageHandler.getLanguageText("synth.type.sampled"),
            languageHandler.getLanguageText("synth.type.opl2"),
            languageHandler.getLanguageText("synth.type.opl3"),
            languageHandler.getLanguageText("synth.type.chip"),
            languageHandler.getLanguageText("synth.type.additive"),
            languageHandler.getLanguageText("synth.type.organ"),
            languageHandler.getLanguageText("synth.type.fm"),
        };
        
        switch (modID) {
            case 2:
            case 5:
                sampleTypeComboBox.addItem(elements[0]);
                sampleTypeComboBox.addItem(elements[1]);
                break;
            case 6:
                sampleTypeComboBox.addItem(elements[0]);
                sampleTypeComboBox.addItem(elements[2]);
                sampleTypeComboBox.addItem(elements[3]);
                sampleTypeComboBox.addItem(elements[4]);
                sampleTypeComboBox.addItem(elements[5]);
                sampleTypeComboBox.addItem(elements[6]);
                break;
            default:
                sampleTypeComboBox.addItem(elements[0]);
                break;
        }
    }

    public void addSampleSelectSpinnerChangeListener(ChangeListener changePerformed) {
        this.sampleSelectSpinner.addChangeListener(changePerformed);
    }
    
    public void addSampleTypeComboBoxChangeEvent(ActionListener actionPerformed) {
        sampleTypeComboBox.addActionListener(actionPerformed);
    }
}
