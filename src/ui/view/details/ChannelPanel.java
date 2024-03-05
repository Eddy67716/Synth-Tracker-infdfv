/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.view.details;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;

/**
 *
 * @author Edward Jenkins
 */
public class ChannelPanel {
    
    // instance variables
    private int modType;
    private JLabel nameFieldLabel;
    private JTextField channelNameField;  
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
}
