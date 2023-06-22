/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.pattens;

import javax.swing.JPanel;
import javax.swing.JTable;

/**
 *
 * @author Edward Jenkins
 */
public class OrderView extends JPanel {
    
    // instance variables
    private int modType;
    private short[] orders;
    
    // constructor
    public OrderView(int modType) {
        this.modType = modType;
        init();
    }
    
    // getters
    
    public void init() {
        
    }
}
