/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.view;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

/**
 *
 * @author Edward Jenkins
 */
public class ProgressionBar extends JFrame{

    // instance variabels
    JPanel loadPanel;
    JLabel label;

    // constructor
    public ProgressionBar(String name) {
        super(name);
        
        loadPanel = new JPanel();
        label = new JLabel(name);
    }
    
    public void init() {
        // add progress bar to load panel
        loadPanel.setDoubleBuffered(true);
        loadPanel.add(label);
        add(loadPanel);

        // add load panel to frame
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setAlwaysOnTop(true);
        setSize(280, 80);
        setVisible(true);
        repaint();
    }
}
