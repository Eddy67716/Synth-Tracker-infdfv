/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.view;

import ui.controllers.MainController;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.JToolBar.Separator;
import javax.swing.UIManager;
import lang.LanguageHandler;
import static ui.UIProperties.DEF_BACKGROUND_COLOUR;
import static ui.UIProperties.DEF_INSETS;

/**
 *
 * @author Edward Jenkins
 */
public class MainUI extends JPanel {
    
    // instance variables
    private LanguageHandler languageHandler;
    private JToolBar mainToolBar;
    private GridBagLayout mainToolBarLayout;
    private GridBagConstraints mainTBC;
    private JButton newFileButton;
    private JButton openFileButton;
    private JButton saveButton;
    private Separator filePlaySeparator;
    private JButton playButton;
    private JButton stopButton;
    private JTabbedPane openedFilesTab;
    
    public MainUI(LanguageHandler languageHandler) {
        
        // language handler
        this.languageHandler = languageHandler;
        
        // layout
        this.setLayout(new BorderLayout());
        
        // tool bar 
        mainToolBar = new JToolBar();
        mainTBC = new GridBagConstraints();
        newFileButton = new JButton();
        openFileButton = new JButton();
        saveButton = new JButton();
        filePlaySeparator = new Separator();
        playButton = new JButton();
        stopButton = new JButton();
        
        // opened files
        openedFilesTab = new JTabbedPane();
        
        init();
    }
    
    // getters
    public LanguageHandler getLanguageHandler() {
        return languageHandler;
    }
    
    public JTabbedPane getOpenedFilesTab() {
        return this.openedFilesTab;
    }
    
    private void init() {
        
        // main tool bar
        mainToolBar.setRollover(true);
        mainToolBar.setFloatable(false);
        mainToolBarLayout = new GridBagLayout();
        mainToolBar.setLayout(mainToolBarLayout);
        
        // sampleTBC
        mainTBC.anchor = GridBagConstraints.NORTHWEST;
        mainTBC.gridx = 0;
        mainTBC.gridy = 0;
        mainTBC.insets = DEF_INSETS;
        mainTBC.weightx = 0.0;
        mainTBC.gridwidth = 1;

        // new file
        newFileButton.setIcon(new javax.swing.ImageIcon(getClass()
                .getResource("/ui/assets/New.png")));
        newFileButton.setToolTipText(languageHandler.getLanguageText("file.new"));
        newFileButton.setFocusable(false);
        newFileButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        newFileButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        mainToolBar.add(newFileButton, mainTBC);
        
        mainTBC.gridx++;
        mainTBC.weightx = 0.0;
        mainTBC.gridwidth = 1;

        // open file
        openFileButton.setIcon(new javax.swing.ImageIcon(getClass()
                .getResource("/ui/assets/Open.png")));
        openFileButton.setToolTipText(languageHandler.getLanguageText("file.open"));
        openFileButton.setFocusable(false);
        openFileButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        openFileButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        mainToolBar.add(openFileButton, mainTBC);
        
        mainTBC.gridx++;
        mainTBC.weightx = 0.0;
        mainTBC.gridwidth = 1;

        // save file
        saveButton.setIcon(new javax.swing.ImageIcon(getClass()
                .getResource("/ui/assets/Filled save icon.png")));
        saveButton.setToolTipText(languageHandler.getLanguageText("file.save"));
        saveButton.setFocusable(false);
        saveButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        saveButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        mainToolBar.add(saveButton, mainTBC);
        
        mainTBC.gridx++;
        mainTBC.weightx = 0.0;
        mainTBC.gridwidth = 1;
        
        // seperator
        mainToolBar.add(filePlaySeparator, mainTBC);
        
        mainTBC.gridx++;
        mainTBC.weightx = 0.0;
        mainTBC.gridwidth = 1;
        
        // play button
        playButton.setIcon(new javax.swing.ImageIcon(getClass()
                .getResource("/ui/assets/Play Pause.png")));
        playButton.setToolTipText(languageHandler.getLanguageText("main.toolbar.play_pause"));
        playButton.setFocusable(false);
        playButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        playButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        mainToolBar.add(playButton, mainTBC);
        
        mainTBC.gridx++;
        mainTBC.weightx = 0.0;
        mainTBC.gridwidth = 1;
        
        // stop button
        stopButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/assets/Stop.png"))); // NOI18N
        stopButton.setToolTipText(languageHandler.getLanguageText("main.toolbar.stop"));
        stopButton.setFocusable(false);
        stopButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        stopButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        mainToolBar.add(stopButton, mainTBC);
        
        mainTBC.gridx++;
        mainTBC.weightx = 1.0;
        mainTBC.gridwidth = GridBagConstraints.REMAINDER;
        
        mainToolBar.add(new JPanel(), mainTBC);
        
        // add tool bar
        this.add(mainToolBar, BorderLayout.NORTH);
        
        // add tabbedPane
        this.add(openedFilesTab, BorderLayout.CENTER);
    }
    
    // action listeners
    public void addNewFileActionListener(ActionListener actionPerformed) {
        newFileButton.addActionListener(actionPerformed);
    }
    
    public void addOpenFileActionListener(ActionListener actionPerformed) {
        openFileButton.addActionListener(actionPerformed);
    }
    
    public void addSaveFileActionListener(ActionListener actionPerformed) {
        saveButton.addActionListener(actionPerformed);
    }
    
    public void addPlayActionListener(ActionListener actionPerformed) {
        playButton.addActionListener(actionPerformed);
    }
    
    public void addStopActionListener(ActionListener actionPerformed) {
        stopButton.addActionListener(actionPerformed);
    }
}
