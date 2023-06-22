/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 *
 * @author Edward Jenkins
 */
public class SettingsUI extends JFrame {
    
    // constants
    private JPanel settingsPanel;
    private JTabbedPane settingTabs;
    private JPanel audioPanel;
    private JPanel lookPanel;
    
    // instance variabls
    
    // constructor
    public SettingsUI() {
        
        // set up panel
        this.setSize(300, 200);
        init();
        this.setTitle("Synth Tracker settings");
        this.setLocationRelativeTo(null);
    }
    
    private void init() {
        
    }
}
