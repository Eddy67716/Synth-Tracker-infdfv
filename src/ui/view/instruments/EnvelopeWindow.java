/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.view.instruments;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JToolBar;
import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;
import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER;
import static ui.UIProperties.DEF_INSETS;

/**
 *
 * @author Edward Jenkins
 */
public class EnvelopeWindow extends JPanel {
    
    // instance variables
    private int modID;
    private JToolBar envelopeWindowToolBar;
    private GridBagLayout envelopeWindowToolBarLayout;
    private GridBagConstraints envelopeWindowTBC;
    private JSlider zoomSlider;
    private JComboBox envelopeTypeComboBox;
    private EnvelopeCanvas canvas;
    private JScrollPane pane;
    
    public EnvelopeWindow(int modID) {
        
        this.modID = modID;
        setEnvelopeTypeList();
        canvas = new EnvelopeCanvas();
        envelopeWindowToolBar = new JToolBar();
        pane = new JScrollPane(canvas, VERTICAL_SCROLLBAR_NEVER, 
                HORIZONTAL_SCROLLBAR_AS_NEEDED);
        envelopeWindowToolBarLayout = new GridBagLayout();
        envelopeWindowTBC = new GridBagConstraints();
        
        init();
    }
    
    // getters
    public JComboBox getEnvelopeTypeComboBox() {
        return envelopeTypeComboBox;
    }
    
    public void init() {
        
        this.setMinimumSize(new Dimension(20, 0));
        this.setMaximumSize(new Dimension(4000, 3000));
        // set layout
        this.setLayout(new BorderLayout());
        
        // toolbar
        //envelopeWindowToolBar.setRollover(true);
        envelopeWindowToolBar.setFloatable(false);
        envelopeWindowToolBar.setLayout(envelopeWindowToolBarLayout);
        
        // toolbar constraints
        envelopeWindowTBC.anchor = GridBagConstraints.NORTHWEST;
        envelopeWindowTBC.gridx = 0;
        envelopeWindowTBC.gridy = 0;
        envelopeWindowTBC.insets = DEF_INSETS;
        envelopeWindowTBC.weightx = 0.0;
        envelopeWindowTBC.gridwidth = 1;
        
        // envelope type list
        envelopeWindowToolBar.add(envelopeTypeComboBox, envelopeWindowTBC);
        
        envelopeWindowTBC.gridx++;
        envelopeWindowTBC.weightx = 1.0;
        envelopeWindowTBC.gridwidth = 1;
        
        envelopeWindowToolBar.add(new JPanel(), envelopeWindowTBC);
        
        add(envelopeWindowToolBar, BorderLayout.NORTH);
        
        // pane sizing
        pane.setMinimumSize(new Dimension(20, 0));
        pane.setPreferredSize(new Dimension(300, 200));
        pane.setMaximumSize(new Dimension(4000, 3000));
        
        add(pane, BorderLayout.CENTER);
    }
    
    public EnvelopeCanvas getCanvas() {
        return this.canvas;
    }
    
    private void setEnvelopeTypeList() {
        
        envelopeTypeComboBox = new JComboBox();
        String[] elements = {"Volume ", "Pan ", "Pitch-filter", 
            "Low Pass Filter", "Pitch-HPF", "Resonance"};
        
        switch (modID) {
            case 3:
                envelopeTypeComboBox.addItem(elements[0]);
                envelopeTypeComboBox.addItem(elements[1]);
                break;
            case 4:
                envelopeTypeComboBox.addItem(elements[0]);
                envelopeTypeComboBox.addItem(elements[1]);
                envelopeTypeComboBox.addItem(elements[2]);
                break;
            case 5:
                envelopeTypeComboBox.addItem(elements[0]);
                envelopeTypeComboBox.addItem(elements[1]);
                envelopeTypeComboBox.addItem(elements[3]);
                envelopeTypeComboBox.addItem(elements[4]);
                envelopeTypeComboBox.addItem(elements[5]);
                break;
            default:
                envelopeTypeComboBox.addItem(elements[0]);
                break;
        }
    }
    
    // event listeners
    public void addEnvelopeTypeComboBoxActionListener(
            ActionListener actionPerformed) {
        envelopeTypeComboBox.addActionListener(actionPerformed);
    }
}
