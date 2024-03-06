/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.view.details;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import static ui.UIProperties.BOLD_FONT;
import static ui.UIProperties.DEF_FONT;
import static ui.UIProperties.DEF_INSETS;
import static ui.UIProperties.VALUE_SPINNER_SIZE;

/**
 *
 * @author Edward Jenkins
 */
public class ChannelPanel extends JPanel {
    
    // instance variables
    private int modType;
    private int channelNumber;
    private GridBagLayout channelLayout;
    private GridBagConstraints cc;
    private Border channelBorder;
    // IT, XM and STR
    private JLabel nameFieldLabel;
    private JTextField channelNameField;
    // IT and STR
    private JLabel channelVolumeLabel;
    private JSpinner channelVolumeValue;
    private SpinnerModel channelVolumeSpinnerModel;
    private JSlider channelVolumeSlider;
    private JLabel channelPanningLabel;
    private JSpinner channelPanningValue;           
    private SpinnerModel panSpinnerModel;
    private JSlider channelPanningSlider;
    private JLabel mutedLabel;
    private JCheckBox muted;
    private JLabel surroundLabel;
    private JCheckBox surround; 
    
    public ChannelPanel(int modType, int channelNumber) {
        this.modType = modType;
        this.channelNumber = channelNumber;
        init();
    }
    
    // getters
    public int getChannelNumber() {
        return channelNumber;
    }

    public JSpinner getChannelVolumeValue() {
        return channelVolumeValue;
    }

    public JSlider getChannelVolumeSlider() {
        return channelVolumeSlider;
    }

    public JSpinner getChannelPanningValue() {
        return channelPanningValue;
    }

    public JSlider getChannelPanningSlider() {
        return channelPanningSlider;
    }

    public JCheckBox getMuted() {
        return muted;
    }

    public JCheckBox getSurround() {
        return surround;
    }
    
    // setter
    public void setChannelNumber(int channelNumber) {
        this.channelNumber = channelNumber;
    }
    
    private void init() {
        
        // set the layout
        channelLayout = new GridBagLayout();
        setLayout(channelLayout);
        cc = new GridBagConstraints();
        cc.anchor = GridBagConstraints.SOUTHWEST;
        cc.insets = DEF_INSETS;

        // set the border
        channelBorder
                = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);

        // set the border title
        channelBorder
                = BorderFactory.createTitledBorder(channelBorder,
                        "Initial timings:", 0, 0, BOLD_FONT);

        // set options border
        setBorder(channelBorder);
        
        
        
        // add trailing JPanel
        cc.gridx = 0;
        cc.gridy++;
        cc.weighty = 1.0;
        cc.gridheight = GridBagConstraints.REMAINDER;
        
        add(new JPanel(), cc);
    }
}
