/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.view;

import ui.controllers.MainController;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.JToolBar.Separator;

/**
 *
 * @author Edward Jenkins
 */
public class MainUI extends JPanel {
    
    // instance variables
    private JToolBar mainToolBar;
    private JButton newFileButton;
    private JButton openFileButton;
    private JButton saveButton;
    private Separator filePlaySeparator;
    private JButton playButton;
    private JButton stopButton;
    private JTabbedPane openedFilesTab;
    
    public MainUI() {
        
        // layout
        this.setLayout(new BorderLayout());
        
        // tool bar 
        mainToolBar = new JToolBar();
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
    public JTabbedPane getOpenedFilesTab() {
        return this.openedFilesTab;
    }
    
    private void init() {
        
        // main tool bar
        mainToolBar.setRollover(true);
        mainToolBar.setFloatable(false);

        // new file
        newFileButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/assets/New.png"))); // NOI18N
        newFileButton.setToolTipText("New file");
        newFileButton.setFocusable(false);
        newFileButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        newFileButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        mainToolBar.add(newFileButton);

        // open file
        openFileButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/assets/Open.png"))); // NOI18N
        openFileButton.setToolTipText("Open file");
        openFileButton.setFocusable(false);
        openFileButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        openFileButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        mainToolBar.add(openFileButton);

        // save file
        saveButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/assets/Filled save icon.png"))); // NOI18N
        saveButton.setToolTipText("Save");
        saveButton.setFocusable(false);
        saveButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        saveButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        mainToolBar.add(saveButton);
        
        // seperator
        mainToolBar.add(filePlaySeparator);
        
        // play button
        playButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/assets/Play Pause.png"))); // NOI18N
        playButton.setToolTipText("Play/Pause module");
        playButton.setFocusable(false);
        playButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        playButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        mainToolBar.add(playButton);
        
        // stop button
        stopButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/assets/Stop.png"))); // NOI18N
        stopButton.setToolTipText("Stop module");
        stopButton.setFocusable(false);
        stopButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        stopButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        mainToolBar.add(stopButton);
        
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
