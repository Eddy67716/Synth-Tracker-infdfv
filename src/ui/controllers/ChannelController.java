/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.controllers;

import javax.swing.JCheckBox;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.undo.UndoManager;
import module.IChannel;
import ui.controllers.undo.UndoableCheckBoxChange;
import ui.controllers.undo.UndoableSliderChange;
import ui.controllers.undo.UndoableSpinnerChange;
import ui.view.details.ChannelPanel;
import ui.view.models.EditSampleViewModel;
import ui.view.models.LoadViewModel;
import module.ISampleSynth;

/**
 *
 * @author Edward Jenkins
 */
public class ChannelController extends GenericController {
    
    // instance variables
    private ChannelPanel channelPanel;
    private EditSampleViewModel editSampleVM;
    private LoadViewModel loadVM;
    private IChannel channel;
    private UndoManager detailsManager;
    private int channelVolumeSpinnerOldValue;
    private int channelVolumeSliderOldValue;
    private int channelPanSpinnerOldValue;
    private int channelPanSliderOldValue;
    
    // constructor
    public ChannelController(ChannelPanel channelPanel, LoadViewModel loadVM,
            UndoManager detailsManager, IChannel channel) {
        this.channelPanel = channelPanel;
        this.loadVM = loadVM;
        this.detailsManager = detailsManager;
        setCurrentUndoManager(detailsManager);
        this.channel = channel;
        
        // set the listeners
        channelPanel.addChannelNameFieldActionListener(e -> 
                channelNameOnChange());
        channelPanel.addChannelVolumeValChangeListener(e -> 
                channelVolumeSpinnerOnChange());
        channelPanel.addChannelVolumeSliderChangeListener(e -> 
                channelVolumeSliderOnChange());
        channelPanel.addChannelPanValChangeListener(e -> 
                channelPanSpinnerOnChange());
        channelPanel.addChannelPanSliderChangeListener(e -> 
                channelPanSliderOnChange());
        channelPanel.addMuteCheckBoxActionListner(e -> 
                channelMuteOnChange());
        channelPanel.addSurroundCheckBoxActionListner(e -> 
                channelSurroundOnChange());
        
        channelSetup();
    }
    
    public void channelNameOnChange() {
        JTextField songNameField = channelPanel.getChannelNameField();

        String sampleNameText = songNameField.getText();

        if (isAlteringModels()) {
            channel.setChannelName(sampleNameText);
        }
    }
    
    public void channelVolumeSpinnerOnChange() {
        
        JSpinner channelVolumeSpinner = channelPanel.getChannelVolumeValue();

        int value = (int) channelVolumeSpinner.getValue();

        // alter the slider linked to this spinner
        boolean recordUndoState = isRecordingUndos();
        setRecordingUndos(false);
        channelPanel.getChannelVolumeSlider().setValue(value);
        setRecordingUndos(recordUndoState);

        if (isRecordingUndos()) {
            // undo event
            UndoableSpinnerChange spinnerChange
                    = new UndoableSpinnerChange(channelVolumeSpinner,
                            channelVolumeSpinnerOldValue);

            getCurrentUndoManager().addEdit(spinnerChange);
        }
        
        channelVolumeSpinnerOldValue = value;

        if (isAlteringModels()) {

            // update channel volume value
            channel.setChannelVolume((short) value);
        }
    }
    
    public void channelVolumeSliderOnChange() {
        
        JSlider channelVolumeSlider = channelPanel.getChannelVolumeSlider();

        int value = channelVolumeSlider.getValue();

        // alter the spinner linked to this slider
        boolean recordUndoState = isRecordingUndos();
        setRecordingUndos(false);
        channelPanel.getChannelVolumeValue().setValue(value);
        setRecordingUndos(recordUndoState);

        if (!channelVolumeSlider.getValueIsAdjusting()) {

            if (isRecordingUndos()) {
                // undo event
                UndoableSliderChange sliderChange
                        = new UndoableSliderChange(channelVolumeSlider,
                                channelVolumeSliderOldValue);

                getCurrentUndoManager().addEdit(sliderChange);
            }
            
            channelVolumeSliderOldValue = value;

            if (isAlteringModels()) {

                // update channel volume value
                channel.setChannelVolume((short) value);
            }
        }
    }
    
    public void channelPanSpinnerOnChange() {
        
        JSpinner channelPanSpinner = channelPanel.getChannelPanningValue();

        int value = (int) channelPanSpinner.getValue();

        // alter the slider linked to this spinner
        boolean recordUndoState = isRecordingUndos();
        setRecordingUndos(false);
        channelPanel.getChannelPanningSlider().setValue(value);
        setRecordingUndos(recordUndoState);

        if (isRecordingUndos()) {
            // undo event
            UndoableSpinnerChange spinnerChange
                    = new UndoableSpinnerChange(channelPanSpinner,
                            channelPanSpinnerOldValue);

            getCurrentUndoManager().addEdit(spinnerChange);
        }
        
        channelPanSpinnerOldValue = value;

        if (isAlteringModels()) {

            // update channel pan value
            channel.setChannelPan((short) value);
        }
    }
    
    public void channelPanSliderOnChange() {
        
        JSlider channelPanSlider = channelPanel.getChannelPanningSlider();

        int value = channelPanSlider.getValue();

        // alter the spinner linked to this slider
        boolean recordUndoState = isRecordingUndos();
        setRecordingUndos(false);
        channelPanel.getChannelPanningValue().setValue(value);
        setRecordingUndos(recordUndoState);

        if (!channelPanSlider.getValueIsAdjusting()) {

            if (isRecordingUndos()) {
                // undo event
                UndoableSliderChange sliderChange
                        = new UndoableSliderChange(channelPanSlider,
                                channelPanSliderOldValue);

                getCurrentUndoManager().addEdit(sliderChange);
            }
            
            channelPanSliderOldValue = value;

            if (isAlteringModels()) {

                // update channel pan value
                channel.setChannelPan((short) value);
            }
        }
    }
    
    public void channelMuteOnChange() {
        
        JCheckBox channelMuteCheckBox = channelPanel.getMuted();
        
        if (isRecordingUndos()) {
            
            // undo event
            UndoableCheckBoxChange checkBoxChange
                    = new UndoableCheckBoxChange(channelMuteCheckBox);

            getCurrentUndoManager().addEdit(checkBoxChange);
        }

        if (isAlteringModels()) {

            // update module global volume value
            channel.setMuted(channelMuteCheckBox.isSelected());
        }
    }
    
    public void channelSurroundOnChange() {
        
        JCheckBox channelSurroundCheckBox = channelPanel.getSurround();
        
        if (isRecordingUndos()) {
            
            // undo event
            UndoableCheckBoxChange checkBoxChange
                    = new UndoableCheckBoxChange(channelSurroundCheckBox);

            getCurrentUndoManager().addEdit(checkBoxChange);
        }

        if (isAlteringModels()) {

            // update module global volume value
            channel.setSurroundChannel(channelSurroundCheckBox.isSelected());
        }
    }
    
    private void channelSetup() {
        
        // set recording undos and alterations to false for a header load
        setRecordingUndos(false);
        setAlteringModels(false);
        
        // name
        channelPanel.getChannelNameField().setText(channel.getChannelName());
        channelPanel.getChannelNameField().getDocument()
                .addUndoableEditListener(detailsManager);
        
        // volume
        channelVolumeSpinnerOldValue = channel.getChannelVolume();
        channelPanel.getChannelVolumeValue()
                .setValue((int)channel.getChannelVolume());
        channelVolumeSliderOldValue = channel.getChannelVolume();
        channelPanel.getChannelVolumeSlider()
                .setValue((int)channel.getChannelVolume());
        
        // pan
        channelPanSpinnerOldValue = channel.getChannelPan();
        channelPanel.getChannelPanningValue()
                .setValue((int)channel.getChannelPan());
        channelPanSliderOldValue = channel.getChannelPan();
        channelPanel.getChannelPanningSlider()
                .setValue((int)channel.getChannelPan());
        
        // muted
        channelPanel.getMuted().setSelected(channel.isMuted());
        
        // surround
        channelPanel.getSurround().setSelected(channel.isSurroundChannel());
        
        // set record undo back to true
        setRecordingUndos(true);
        setAlteringModels(true);
    }
}
