/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.view.windows.find;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import static ui.UIProperties.BOLD_FONT;
import static ui.UIProperties.DEF_INSETS;

/**
 *
 * @author eddy6
 */
public class FindEffect extends JPanel {
    
    // instance variables
    private int modType;
    private Border findEffectBorder;
    private GridBagLayout findEffectLayout;
    private GridBagConstraints fec;
    private JLabel effectLabel;
    private JComboBox effectsList;
    private JLabel effectValueLabel;
    private JSpinner effectValueSpinner;
    
    public FindEffect(int modType) {
        this.modType = modType;
        init();
    }
    
    // getters
    
    public void init() {
        
        // set the layout
        findEffectLayout = new GridBagLayout();
        setLayout(findEffectLayout);
        
        // setup the constrants
        fec = new GridBagConstraints();
        fec.anchor = GridBagConstraints.SOUTHWEST;
        //fnc.fill = GridBagConstraints.BOTH;
        fec.insets = DEF_INSETS;

        // set the border
        findEffectBorder
                = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);

        // set the border title
        findEffectBorder
                = BorderFactory.createTitledBorder(findEffectBorder,
                        "Effect: ", 0, 0, BOLD_FONT);

        // set options border
        setBorder(findEffectBorder);
        
        // volume effect label
        effectLabel = new JLabel("Effect: ");
        
        fec.gridx = 0;
        fec.gridwidth = GridBagConstraints.REMAINDER;
        fec.weightx = 1;
        fec.gridy = 0;
        fec.gridheight = 1;
        fec.weighty = 0;
        
        add(effectLabel, fec);
        
        // volume effect combo box
        effectsList = new JComboBox();
        
        fec.gridwidth = 1;
        fec.gridx = 0;
        fec.gridy++;
        
        add(effectsList, fec);
        
        // volume value label
        effectValueLabel = new JLabel("Effect: ");
        
        fec.gridy++;
        fec.gridheight = 1;
        fec.weighty = 0;
        
        add(effectValueLabel, fec);
        
        // volume value spinner
        effectValueSpinner = new JSpinner();
        
        fec.gridwidth = 1;
        fec.gridy++;
        
        add(effectValueSpinner, fec);
        
        // add trailing empty JPanel
        fec.gridx = 0;
        fec.gridy++;
        fec.weighty = 1.0;
        fec.gridheight = GridBagConstraints.REMAINDER;
        
        add(new JPanel(), fec);
    }
}
