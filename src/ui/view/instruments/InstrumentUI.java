/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.view.instruments;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeListener;
import lang.LanguageHandler;
import module.IInstrument;
import static ui.UIProperties.DEF_INSETS;
import static ui.UIProperties.VALUE_SPINNER_SIZE;

/**
 *
 * @author Edward Jenkins
 */
public class InstrumentUI extends JPanel {
    
    // instance variables
    private int selectedInstrument;
    private int modID;
    private LanguageHandler languageHandler;
    // toolbar for pannel
    private GridBagLayout instrumentToolBarLayout;
    private GridBagConstraints instrumentTBC;
    private JToolBar instrumentToolBar;
    private JButton newInstrumentButton;
    private JButton openInstrumentButton;
    private JButton saveInstrumentButton;
    private JButton deleteButton;
    private JSeparator fileSelectorSeparator;
    private SpinnerNumberModel instrumentSelectorModel;
    private JPanel instrumentSelectPanel;
    private JLabel instrumentSelectLabel;
    private GridBagLayout instrumentSelectLayout;
    private GridBagConstraints instrumentSC;
    private JSpinner instrumentSelectSpinner;
    private JSeparator selectorPlaySeparator;
    private JButton playInstrumentButton;
    private JButton stopInstrumentButton;
    
    // other windows
    private JSplitPane splitPane;
    private InstrumentTools instrumentTools;
    private EnvelopeWindow envelopeWindow;
    
    // constructor
    public InstrumentUI(ArrayList<IInstrument> instruments, int modID,
            LanguageHandler languageHandler) {
        
        this.modID = modID;
        this.languageHandler = languageHandler;
        
        // set layout
        this.setLayout(new BorderLayout());
        
        // set selected sample
        selectedInstrument = 1;
        
        // initialise the UI windows
        this.instrumentTools = new InstrumentTools(modID, languageHandler);
        this.envelopeWindow = new EnvelopeWindow(modID);
        
        // toolbar
        this.instrumentToolBar = new JToolBar();
        instrumentToolBarLayout = new GridBagLayout();
        instrumentTBC = new GridBagConstraints();
        newInstrumentButton = new JButton();
        openInstrumentButton = new JButton();
        saveInstrumentButton = new JButton();
        deleteButton = new JButton();
        fileSelectorSeparator = new JSeparator();
        instrumentSelectorModel 
                = new SpinnerNumberModel(selectedInstrument, 0, 
                        instruments.size() + 1, 1);
        instrumentSelectPanel = new JPanel();
        instrumentSelectLayout = new GridBagLayout();
        instrumentSC = new GridBagConstraints();
        instrumentSelectLabel = new JLabel("Instrument: ");
        instrumentSelectSpinner = new JSpinner(instrumentSelectorModel);
        selectorPlaySeparator = new JSeparator();
        playInstrumentButton = new JButton();
        stopInstrumentButton = new JButton();
        
        init();
    }
    
    // getters
    public InstrumentTools getTools() {
        return instrumentTools;
    }

    public EnvelopeWindow getEnvelopeWindow() {
        return envelopeWindow;
    }

    public int getSelectedInstrument() {
        return selectedInstrument;
    }

    public JSpinner getInstrumentSelectSpinner() {
        return instrumentSelectSpinner;
    }
    
    // setters
    public void setSelectedInstrument(int selectedInstrument) {
        this.selectedInstrument = selectedInstrument;
    }
    
    
    private void init() {
        
        // toolbar
        instrumentToolBar.setRollover(true);
        instrumentToolBar.setFloatable(false);
        instrumentToolBar.setLayout(instrumentToolBarLayout);
        
        // instrumentTBC
        instrumentTBC.anchor = GridBagConstraints.NORTHWEST;
        instrumentTBC.gridx = 0;
        instrumentTBC.gridy = 0;
        instrumentTBC.insets = DEF_INSETS;
        instrumentTBC.weightx = 0.0;
        instrumentTBC.gridwidth = 1;
        
        // new instrument
        newInstrumentButton.setIcon(new javax.swing.ImageIcon(getClass()
                .getResource("/ui/assets/New.png")));
        newInstrumentButton.setToolTipText(languageHandler
                .getLanguageText("instrument.options.new"));
        newInstrumentButton.setFocusable(false);
        newInstrumentButton
                .setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        newInstrumentButton
                .setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        instrumentToolBar.add(newInstrumentButton, instrumentTBC);
        
        instrumentTBC.gridx++;
        instrumentTBC.weightx = 0.0;
        instrumentTBC.gridwidth = 1;
        
        // open instrument
        openInstrumentButton.setIcon(new javax.swing.ImageIcon(getClass()
                .getResource("/ui/assets/Open.png")));
        openInstrumentButton.setToolTipText(languageHandler
                .getLanguageText("instrument.options.open"));
        openInstrumentButton.setFocusable(false);
        openInstrumentButton
                .setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        openInstrumentButton
                .setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        instrumentToolBar.add(openInstrumentButton, instrumentTBC);
        
        instrumentTBC.gridx++;
        instrumentTBC.weightx = 0.0;
        instrumentTBC.gridwidth = 1;
        
        // save instrument
        saveInstrumentButton.setIcon(new javax.swing.ImageIcon(getClass()
                .getResource("/ui/assets/Filled save icon.png")));
        saveInstrumentButton.setToolTipText(languageHandler
                .getLanguageText("instrument.options.save"));
        saveInstrumentButton.setFocusable(false);
        saveInstrumentButton
                .setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        saveInstrumentButton
                .setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        instrumentToolBar.add(saveInstrumentButton, instrumentTBC);
        
        instrumentTBC.gridx++;
        instrumentTBC.weightx = 0.0;
        instrumentTBC.gridwidth = 1;
        
        // delete sample
        deleteButton.setIcon(new javax.swing.ImageIcon(getClass()
                .getResource("/ui/assets/Delete.png")));
        deleteButton.setToolTipText(languageHandler
                .getLanguageText("instrument.options.delete"));
        deleteButton.setFocusable(false);
        deleteButton
                .setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        deleteButton
                .setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        instrumentToolBar.add(deleteButton, instrumentTBC);
        
        instrumentTBC.gridx++;
        instrumentTBC.weightx = 0.0;
        instrumentTBC.gridwidth = 1;
        instrumentTBC.anchor = GridBagConstraints.CENTER;
        
        // separator
        instrumentToolBar.add(fileSelectorSeparator, instrumentTBC);
        
        instrumentTBC.gridx++;
        instrumentTBC.weightx = 0.0;
        instrumentTBC.gridwidth = 1;
        instrumentTBC.anchor = GridBagConstraints.NORTHWEST;
        
        // instrumentSelectPanel
        instrumentSelectPanel.setLayout(instrumentSelectLayout);
                
        instrumentSC.anchor = GridBagConstraints.NORTHWEST;
        instrumentSC.gridx = 0;
        instrumentSC.gridy = 0;
        instrumentSC.insets = DEF_INSETS;
        instrumentSC.weightx = 1.0;
        instrumentSC.gridwidth = GridBagConstraints.REMAINDER;
        
        instrumentSelectPanel.add(instrumentSelectLabel, instrumentSC);
        
        instrumentSC.gridy = 1;
        
        instrumentSelectSpinner.setPreferredSize(VALUE_SPINNER_SIZE);
        
        instrumentSelectPanel.add(instrumentSelectSpinner, instrumentSC);
        
        instrumentToolBar.add(instrumentSelectPanel, instrumentTBC);
        
        instrumentTBC.gridx++;
        instrumentTBC.weightx = 0.0;
        instrumentTBC.gridwidth = 1;
        
        // separator
        instrumentToolBar.add(selectorPlaySeparator, instrumentTBC);
        
        instrumentTBC.gridx++;
        instrumentTBC.weightx = 0.0;
        instrumentTBC.gridwidth = 1;
        
        // play
        playInstrumentButton.setIcon(new javax.swing.ImageIcon(getClass()
                .getResource("/ui/assets/Play Pause.png")));
        playInstrumentButton.setToolTipText(languageHandler
                .getLanguageText("instrument.options.play"));
        playInstrumentButton.setFocusable(false);
        playInstrumentButton
                .setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        playInstrumentButton
                .setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        instrumentToolBar.add(playInstrumentButton, instrumentTBC);
        
        instrumentTBC.gridx++;
        instrumentTBC.weightx = 1.0;
        instrumentTBC.gridwidth = 1;
        
        instrumentToolBar.add(new JPanel(), instrumentTBC);
        
        // add toolbar
        this.add(instrumentToolBar, BorderLayout.NORTH);
        
        // initialise the split pane
        splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, instrumentTools, 
                envelopeWindow);
        
        this.add(splitPane, BorderLayout.CENTER);
    }
    
    public void addInstrumentSelectSpinnerChangeListener(ChangeListener 
            changePerformed) {
        this.instrumentSelectSpinner.addChangeListener(changePerformed);
    }
}
