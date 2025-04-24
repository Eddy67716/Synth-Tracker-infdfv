/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.view.pattens;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import module.IPattern;
import ui.controllers.CellController;

/**
 *
 * @author Edward Jenkins
 */
public class PatternPanel extends JPanel{
    
    // instance variables
    private JScrollPane patternPane;
    private PatternTable patternTable;
    
    public PatternPanel() {
        
    }
    
    public void init() {
        patternTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        patternPane = new JScrollPane(patternTable, 
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        patternPane.setPreferredSize(new Dimension(300, 200));
        setLayout(new BorderLayout());
        add(patternPane, BorderLayout.CENTER);
    }
    
    // getter
    public PatternTable getPatternTable() {
        return patternTable;
    }
    
    // setter
    public void setPatternTable(PatternTable patternTable) {
        this.patternTable = patternTable;
        repaint();
    }
    
    public void changePattern(String[] channelNames, IPattern pattern) {
        patternTable.changePattern(channelNames, pattern);
    }
}
