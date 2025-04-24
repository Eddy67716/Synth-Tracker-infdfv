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
public class FindVolume extends JPanel {
    
    // instance variables
    private int modType;
    private Border findVolumeBorder;
    private GridBagLayout findVolumeLayout;
    private GridBagConstraints fvc;
    private JLabel volumeEffectLabel;
    private JComboBox volumeEffectsList;
    private JLabel volumeValueLabel;
    private JSpinner volumeValueSpinner;
    
    public FindVolume(int modType) {
        this.modType = modType;
        init();
    }
    
    // getters
    public JComboBox getVolumeEffectsList() {
        return volumeEffectsList;
    }

    public JSpinner getVolumeValueSpinner() {
        return volumeValueSpinner;
    }
    
    public void init() {
        
        // set the layout
        findVolumeLayout = new GridBagLayout();
        setLayout(findVolumeLayout);
        
        // setup the constrants
        fvc = new GridBagConstraints();
        fvc.anchor = GridBagConstraints.SOUTHWEST;
        //fnc.fill = GridBagConstraints.BOTH;
        fvc.insets = DEF_INSETS;

        // set the border
        findVolumeBorder
                = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);

        // set the border title
        findVolumeBorder
                = BorderFactory.createTitledBorder(findVolumeBorder,
                        "Volume: ", 0, 0, BOLD_FONT);

        // set options border
        setBorder(findVolumeBorder);
        
        // volume effect label
        volumeEffectLabel = new JLabel("Volume effect: ");
        
        fvc.gridx = 0;
        fvc.gridwidth = GridBagConstraints.REMAINDER;
        fvc.weightx = 1;
        fvc.gridy = 0;
        fvc.gridheight = 1;
        fvc.weighty = 0;
        
        add(volumeEffectLabel, fvc);
        
        // volume effect combo box
        volumeEffectsList = new JComboBox();
        
        fvc.gridwidth = 1;
        fvc.gridx = 0;
        fvc.gridy++;
        
        add(volumeEffectsList, fvc);
        
        // volume value label
        volumeValueLabel = new JLabel("Volume effect: ");
        
        fvc.gridy++;
        fvc.gridheight = 1;
        fvc.weighty = 0;
        
        add(volumeValueLabel, fvc);
        
        // volume value spinner
        volumeValueSpinner = new JSpinner();
        
        fvc.gridwidth = 1;
        fvc.gridy++;
        
        add(volumeValueSpinner, fvc);
        
        // add trailing empty JPanel
        fvc.gridx = 0;
        fvc.gridy++;
        fvc.weighty = 1.0;
        fvc.gridheight = GridBagConstraints.REMAINDER;
        
        add(new JPanel(), fvc);
    }
}
