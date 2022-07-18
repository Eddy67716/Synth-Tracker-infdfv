/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UserInterfaces.Views.Samples;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import static javax.swing.ScrollPaneConstants.*;

/**
 *
 * @author Edward Jenkins
 */
public class SampleWindow extends JPanel{
    
    // instance variables
    private SampleCanvas canvas;
    private JScrollPane pane;
    
    public SampleWindow() {
        
        this.setMinimumSize(new Dimension(20, 0));
        this.setMaximumSize(new Dimension(4000, 3000));
        this.setLayout(new GridLayout());
        
        canvas = new SampleCanvas();
        pane = new JScrollPane(canvas, VERTICAL_SCROLLBAR_NEVER, 
                HORIZONTAL_SCROLLBAR_AS_NEEDED);
        pane.setMinimumSize(new Dimension(20, 0));
        pane.setPreferredSize(new Dimension(300, 200));
        pane.setMaximumSize(new Dimension(4000, 3000));
        add(pane);
    }
    
    public SampleCanvas getCanvas() {
        return this.canvas;
    }
}
