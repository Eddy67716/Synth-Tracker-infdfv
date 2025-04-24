/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.view.windows;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import ui.view.windows.find.FindPanel;

/**
 *
 * @author eddy6
 */
public class FindReplacePanel extends JPanel {
    
    // instance variables
    private JTabbedPane tabPane;
    private FindPanel findPanel;
    private JPanel replacePanel;
    private JLabel replaceNoteLabel;
    private GridBagConstraints rc;
    
    public FindReplacePanel() {
        init();
    }
    
    private void init() {
        this.setLayout(new BorderLayout());
        
    }
}
