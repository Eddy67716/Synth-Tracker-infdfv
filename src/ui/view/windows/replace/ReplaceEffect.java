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
public class ReplaceEffect extends JPanel {
    
    // instance variables
    private int modType;
    private Border replaceEffectBorder;
    private GridBagLayout replaceEffectLayout;
    private GridBagConstraints rec;
    private JLabel effectLabel;
    private JComboBox effectsList;
    private JLabel modificationTypeLabel;
    private JComboBox modificationList;
    private JLabel effectValueLabel;
    private JSpinner effectValueSpinner;
    
    public ReplaceEffect(int modType) {
        this.modType = modType;
        init();
    }
    
    // getters
    
    public void init() {
        
        // set the layout
        replaceEffectLayout = new GridBagLayout();
        setLayout(replaceEffectLayout);
        
        // setup the constrants
        rec = new GridBagConstraints();
        rec.anchor = GridBagConstraints.SOUTHWEST;
        //fnc.fill = GridBagConstraints.BOTH;
        rec.insets = DEF_INSETS;

        // set the border
        replaceEffectBorder
                = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);

        // set the border title
        replaceEffectBorder
                = BorderFactory.createTitledBorder(replaceEffectBorder,
                        "Effect: ", 0, 0, BOLD_FONT);

        // set options border
        setBorder(replaceEffectBorder);
        
        // effect label
        effectLabel = new JLabel("Effect: ");
        
        rec.gridx = 0;
        rec.gridwidth = GridBagConstraints.REMAINDER;
        rec.weightx = 1;
        rec.gridy = 0;
        rec.gridheight = 1;
        rec.weighty = 0;
        
        add(effectLabel, rec);
        
        // effect combo box
        effectsList = new JComboBox();
        
        rec.gridwidth = 1;
        rec.gridx = 0;
        rec.gridy++;
        
        add(effectsList, rec);
        
        // effect modification label
        modificationTypeLabel = new JLabel("Modification type: ");
        
        rec.gridy++;
        rec.gridheight = 1;
        rec.weighty = 0;
        
        add(modificationTypeLabel, rec);
        
        // effect value spinner
        modificationList = new JComboBox();
        
        rec.gridwidth = 1;
        rec.gridy++;
        
        add(modificationList, rec);
        
        // effect value label
        effectValueLabel = new JLabel("Effect value: ");
        
        rec.gridy++;
        rec.gridheight = 1;
        rec.weighty = 0;
        
        add(effectValueLabel, rec);
        
        // effect value spinner
        effectValueSpinner = new JSpinner();
        
        rec.gridwidth = 1;
        rec.gridy++;
        
        add(effectValueSpinner, rec);
        
        // add trailing empty JPanel
        rec.gridx = 0;
        rec.gridy++;
        rec.weighty = 1.0;
        rec.gridheight = GridBagConstraints.REMAINDER;
        
        add(new JPanel(), rec);
    }
}
