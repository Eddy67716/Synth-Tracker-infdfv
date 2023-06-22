/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.view.samples;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JToolBar;
import static javax.swing.ScrollPaneConstants.*;
import static ui.UIProperties.DEF_INSETS;

/**
 *
 * @author Edward Jenkins
 */
public class SampleWindow extends JPanel{
    
    // instance variables
    private int modID;
    private JToolBar sampleWindowToolBar;
    private GridBagLayout sampleWindowToolBarLayout;
    private GridBagConstraints sampleWindowTBC;
    private JSlider zoomSlider;
    private JComboBox sampleTypeComboBox;
    private SampleCanvas canvas;
    private JScrollPane pane;
    
    public SampleWindow(int modID) {
        
        this.modID = modID;
        setSampleTypeList();
        canvas = new SampleCanvas();
        sampleWindowToolBar = new JToolBar();
        pane = new JScrollPane(canvas, VERTICAL_SCROLLBAR_NEVER, 
                HORIZONTAL_SCROLLBAR_AS_NEEDED);
        sampleWindowToolBarLayout = new GridBagLayout();
        sampleWindowTBC = new GridBagConstraints();
        
        init();
    }
    
    public void init() {
        
        this.setMinimumSize(new Dimension(20, 0));
        this.setMaximumSize(new Dimension(4000, 3000));
        // set layout
        this.setLayout(new BorderLayout());
        
        // toolbar
        //sampleWindowToolBar.setRollover(true);
        sampleWindowToolBar.setFloatable(false);
        sampleWindowToolBar.setLayout(sampleWindowToolBarLayout);
        
        // toolbar constraints
        sampleWindowTBC.anchor = GridBagConstraints.NORTHWEST;
        sampleWindowTBC.gridx = 0;
        sampleWindowTBC.gridy = 0;
        sampleWindowTBC.insets = DEF_INSETS;
        sampleWindowTBC.weightx = 0.0;
        sampleWindowTBC.gridwidth = 1;
        
        // sample type list
        sampleWindowToolBar.add(sampleTypeComboBox, sampleWindowTBC);
        
        sampleWindowTBC.gridx++;
        sampleWindowTBC.weightx = 1.0;
        sampleWindowTBC.gridwidth = 1;
        
        sampleWindowToolBar.add(new JPanel(), sampleWindowTBC);
        
        add(sampleWindowToolBar, BorderLayout.NORTH);
        
        // pane sizing
        pane.setMinimumSize(new Dimension(20, 0));
        pane.setPreferredSize(new Dimension(300, 200));
        pane.setMaximumSize(new Dimension(4000, 3000));
        
        add(pane, BorderLayout.CENTER);
    }
    
    public SampleCanvas getCanvas() {
        return this.canvas;
    }
    
    private void setSampleTypeList() {
        
        sampleTypeComboBox = new JComboBox();
        String[] elements = {"Audio sample", "OPL2 sample", "OPL3 sample", 
            "Chip sample", "Additive synth sample", "Organ sample", "FM sample"};
        
        switch (modID) {
            case 2:
            case 5:
                sampleTypeComboBox.addItem(elements[0]);
                sampleTypeComboBox.addItem(elements[1]);
                break;
            case 6:
                sampleTypeComboBox.addItem(elements[0]);
                sampleTypeComboBox.addItem(elements[2]);
                sampleTypeComboBox.addItem(elements[3]);
                sampleTypeComboBox.addItem(elements[4]);
                sampleTypeComboBox.addItem(elements[5]);
                sampleTypeComboBox.addItem(elements[6]);
                break;
            default:
                sampleTypeComboBox.addItem(elements[0]);
                break;
        }
    }
}
