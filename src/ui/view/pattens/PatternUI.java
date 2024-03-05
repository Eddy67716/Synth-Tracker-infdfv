/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.view.pattens;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author Edward Jenkins
 */
public class PatternUI extends JPanel {
    
    // instance variables
    private int modType;
    private JScrollPane orderPane;
    private JScrollPane tablePane;
    private PatternPanel paternPanel;
    private OrderView orderView;
    
    // constructor
    public PatternUI(int modType) {
        this.modType = modType;
        init();
    }
    
    // getters
    
    public void init() {
        
    }
}
