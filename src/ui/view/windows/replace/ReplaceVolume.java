/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.view.windows.replace;

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
public class ReplaceVolume extends JPanel {
    
    // instance variables
    private int modType;
    private Border replaceVolumeBorder;
    private GridBagLayout replaceVolumeLayout;
    private GridBagConstraints rvc;
    private JLabel volumeEffectLabel;
    private JComboBox volumeEffectsList;
    private JLabel modificationTypeLabel;
    private JComboBox modificationList;
    private JLabel volumeValueLabel;
    private JSpinner volumeValueSpinner;
    
    public ReplaceVolume(int modType) {
        this.modType = modType;
        init();
    }
    
    // getters
    
    public void init() {
        
        // set the layout
        replaceVolumeLayout = new GridBagLayout();
        setLayout(replaceVolumeLayout);
        
        // setup the constrants
        rvc = new GridBagConstraints();
        rvc.anchor = GridBagConstraints.SOUTHWEST;
        //fnc.fill = GridBagConstraints.BOTH;
        rvc.insets = DEF_INSETS;

        // set the border
        replaceVolumeBorder
                = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);

        // set the border title
        replaceVolumeBorder
                = BorderFactory.createTitledBorder(replaceVolumeBorder,
                        "Volume: ", 0, 0, BOLD_FONT);

        // set options border
        setBorder(replaceVolumeBorder);
        
        // volume effect label
        volumeEffectLabel = new JLabel("Volume effect: ");
        
        rvc.gridx = 0;
        rvc.gridwidth = GridBagConstraints.REMAINDER;
        rvc.weightx = 1;
        rvc.gridy = 0;
        rvc.gridheight = 1;
        rvc.weighty = 0;
        
        add(volumeEffectLabel, rvc);
        
        // volume effect combo box
        volumeEffectsList = new JComboBox();
        
        rvc.gridwidth = 1;
        rvc.gridx = 0;
        rvc.gridy++;
        
        add(volumeEffectsList, rvc);
        
        // volume modification label
        modificationTypeLabel = new JLabel("Modification type: ");
        
        rvc.gridy++;
        rvc.gridheight = 1;
        rvc.weighty = 0;
        
        add(modificationTypeLabel, rvc);
        
        // volume value spinner
        modificationList = new JComboBox();
        
        rvc.gridwidth = 1;
        rvc.gridy++;
        
        add(modificationList, rvc);
        
        // volume value label
        volumeValueLabel = new JLabel("Volume value: ");
        
        rvc.gridy++;
        rvc.gridheight = 1;
        rvc.weighty = 0;
        
        add(volumeValueLabel, rvc);
        
        // volume value spinner
        volumeValueSpinner = new JSpinner();
        
        rvc.gridwidth = 1;
        rvc.gridy++;
        
        add(volumeValueSpinner, rvc);
        
        // add trailing empty JPanel
        rvc.gridx = 0;
        rvc.gridy++;
        rvc.weighty = 1.0;
        rvc.gridheight = GridBagConstraints.REMAINDER;
        
        add(new JPanel(), rvc);
    }
}
