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
import javax.swing.SpinnerNumberModel;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ChangeListener;
import static ui.UIProperties.BOLD_FONT;
import static ui.UIProperties.DEF_FONT;
import static ui.UIProperties.DEF_INSETS;
import static ui.UIProperties.VALUE_SPINNER_SIZE;

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
    
    // getters
    public JSpinner getTickSpeedSpinner() {
        return tickSpeedSpinner;
    }

    public JSpinner getTempoSpinner() {
        return tempoSpinner;
    }
    
    private void init() {
        
        // set the layout
        timingLayout = new GridBagLayout();
        setLayout(timingLayout);
        tc = new GridBagConstraints();
        tc.anchor = GridBagConstraints.SOUTHWEST;
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
        
        // tick speed
        
        // tick speed label
        tickSpeedLabel = new JLabel("Tick speed");
        tickSpeedLabel.setFont(DEF_FONT);
        
        // grid bag setting
        tc.gridx = 0;
        tc.gridy = 0;
        add(tickSpeedLabel, tc);
        
        // tick speed spinner model
        tickSpeedSpinnerModel = new SpinnerNumberModel(6, 1, 128, 1);
        
        // tick speed spinner
        tickSpeedSpinner = new JSpinner(tickSpeedSpinnerModel);
        tickSpeedSpinner.setPreferredSize(VALUE_SPINNER_SIZE);
        
        // grid bag setting
        tc.gridx = 1;
        tc.gridy = 0;
        add(tickSpeedSpinner, tc);
        
        // tempo
        
        // tempo lable
        tempoLabel = new JLabel("Tempo");
        tempoLabel.setFont(DEF_FONT);
        
        // grid bag setting
        tc.gridx = 2;
        tc.gridy = 0;
        add(tempoLabel, tc);
        
        // tempo spinner model
        tempoSpinnerModel = new SpinnerNumberModel(120, 0, 255, 1);
        
        // tempo spinner
        tempoSpinner = new JSpinner(tempoSpinnerModel);
        tempoSpinner.setPreferredSize(VALUE_SPINNER_SIZE);
        
        // grid bag setting
        tc.gridx = 3;
        tc.gridy = 0;
        tc.weightx = 1.0;
        tc.gridwidth = GridBagConstraints.REMAINDER;
        add(tempoSpinner, tc);
        
        // add trailing JPanel
        tc.gridx = 0;
        tc.gridy = 1;
        tc.weighty = 1.0;
        tc.gridheight = GridBagConstraints.REMAINDER;
        
        add(new JPanel(), tc);
    }
    
    public void addTickSpeedSpinnerChangeListener(ChangeListener changePerformed) {
        this.tickSpeedSpinner.addChangeListener(changePerformed);
    }
    
    public void addBpmSpinnerChangeListener(ChangeListener changePerformed) {
        this.tempoSpinner.addChangeListener(changePerformed);
    }
}
