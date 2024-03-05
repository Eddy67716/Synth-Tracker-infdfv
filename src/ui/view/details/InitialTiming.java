/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.view.details;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import static ui.UIProperties.BOLD_FONT;
import static ui.UIProperties.DEF_INSETS;

/**
 *
 * @author Edward Jenkins
 */
public class InitialTiming extends JPanel {
    
    // instance variables
    private int modType;
    private GridBagLayout timingLayout;
    private GridBagConstraints tc;
    private Border timingBorder;
    // all
    private JLabel tickSpeedLabel;
    private SpinnerModel tickSpeedSpinnerModel;
    private JSpinner tickSpeedSpinner;
    private JLabel tempoLabel;
    private SpinnerModel tempoSpinnerModel;
    private JSpinner tempoSpinner;
    
    public InitialTiming(int modType) {
        this.modType = modType;
        init();
    }
    
    private void init() {
        
        // set the layout
        timingLayout = new GridBagLayout();
        setLayout(timingLayout);
        tc = new GridBagConstraints();
        tc.anchor = GridBagConstraints.NORTHWEST;
        tc.insets = DEF_INSETS;

        // set the border
        timingBorder
                = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);

        // set the border title
        timingBorder
                = BorderFactory.createTitledBorder(timingBorder,
                        "Initial timings:", 0, 0, BOLD_FONT);

        // set options border
        setBorder(timingBorder);
    }
}
