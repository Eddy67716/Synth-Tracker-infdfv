/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.view.pattens;

import javax.swing.JPanel;
import javax.swing.JTable;

/**
 *
 * @author Edward Jenkins
 */
public class PatternView extends JPanel {
    
    // instance variables
    private int modType;
    private JTable patternTable;
    
    // constructor
    public PatternView(int modType) {
        this.modType = modType;
        init();
    }
    
    // getters
    
    public void init() {
        
    }
}
