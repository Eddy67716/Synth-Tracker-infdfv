/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.controllers;

import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.undo.UndoManager;
import module.IModuleHeader;
import ui.controllers.undo.UndoableRadioButtonChange;
import ui.controllers.undo.UndoableSliderChange;
import ui.controllers.undo.UndoableSpinnerChange;
import ui.view.details.DetailsUI;
import ui.view.details.InitialTiming;
import ui.view.details.ModuleDetails;
import ui.view.details.ModuleMessage;
import ui.view.details.ModuleOptions;
import ui.view.details.ModuleSoundOptions;
import ui.view.models.EditSampleViewModel;
import ui.view.models.LoadViewModel;

/**
 *
 * @author Edward Jenkins
 */
public class DetailsController extends GenericController {
    
    // instance variables
    private DetailsUI detailsUI;
    private EditSampleViewModel editSampleVM;
    private LoadViewModel loadVM;
    private IModuleHeader moduleHeader;
    private ChannelController[] channelControllers;
    private UndoManager detailsManager;
    private int tempoOldValue;
    private int tickSpeedOldValue;
    private int channelOldValue;
    private int globalVolumeSpinnerOldValue;
    private int globalVolumeSliderOldValue;
    private int mixSpinnerOldValue;
    private int mixSliderOldValue;
    private int panSeparationSpinnerOldValue;
    private int panSeparationSliderOldValue;
    
    public DetailsController(DetailsUI detailsUI, LoadViewModel loadVM) {
        super();
        this.detailsUI = detailsUI;
        this.loadVM = loadVM;
        moduleHeader = loadVM.getHeader();
        
        // initialise undoManager
        detailsManager = new UndoManager();
        setCurrentUndoManager(detailsManager);
        
        // set module details listeners
        ModuleDetails modDetails 
                = detailsUI.getModuleTools().getModuleDetails();
        modDetails.addSongNameFieldActionListener(e -> songNameOnChange());
        modDetails.addSongArtistFieldActionListener(e -> artistNameOnChange());
        
        // set module sound options
        ModuleSoundOptions modSoundOptions 
                = detailsUI.getModuleTools().getModuleSoundOptions();
        // channels
        modSoundOptions.addChannelSpinnerChangeListener(
                e -> channelSpinnerOnChange());
        // global volume
        modSoundOptions.addGlobVolumeValChangeListener(
                e -> globalVolumeSpinnerOnChange());
        modSoundOptions.addGlobVolumeSliderChangeListener(
                e -> globalVolumeSliderOnChange());
        // mix volume
        modSoundOptions.addMixVolumeValChangeListener(
                e -> mixVolumeSpinnerOnChange());
        modSoundOptions.addMixVolumeSliderChangeListener(
                e -> mixVolumeSliderOnChange());
        // pan separation
        modSoundOptions.addPanSepValChangeListener(
                e -> panSeparationSpinnerOnChange());
        modSoundOptions.addPanSepSliderChangeListener(
                e -> panSeparationSliderOnChange());
        
        // set initial timings
        InitialTiming initialTiming
                = detailsUI.getModuleTools().getModuleTiming();
        // initial speed
        initialTiming.addTickSpeedSpinnerChangeListener(
                e -> this.tickSpeedSpinnerOnChange());
        // initial tempo
        initialTiming.addBpmSpinnerChangeListener(
                e -> this.tempoSpinnerOnChange());
        
        // set module options
        ModuleOptions modOptions
                = detailsUI.getModuleTools().getModuleOptions();
        modOptions.addStereoOptionsEvent(e -> this.stereoOptionChange());
        modOptions.addMonoOptionsEvent(e -> this.monoOptionChange());
        
        loadHeaderProperties();
    }
    
    // controller methods
    // mod details
    private void songNameOnChange() {

        JTextField songNameField = detailsUI.getModuleTools().getModuleDetails()
                .getSongNameField();

        String sampleNameText = songNameField.getText();

        if (isRecordingUndos()) {
            //sampleNameText.
        }

        if (isAlteringModels()) {
            moduleHeader.setSongName(sampleNameText);
        }
    }

    private void artistNameOnChange() {

        JTextField artistNameField = detailsUI.getModuleTools().getModuleDetails()
                .getSongArtistField();
        
        String songArtistText = artistNameField.getText();
        
        if (isRecordingUndos()) {
            //sampleNameText.
        }

        if (isAlteringModels()) {

            moduleHeader.setAritistName(songArtistText);
        }
    }
    
    // mod sound options
    public void channelSpinnerOnChange() {
        
        JSpinner channelSpinner = detailsUI.getModuleTools()
                .getModuleSoundOptions().getChannelSpinner();

        int value = (int) channelSpinner.getValue();
        
        if (isRecordingUndos()) {
            
            // undo event
            UndoableSpinnerChange spinnerChange
                    = new UndoableSpinnerChange(channelSpinner,
                            channelOldValue);

            getCurrentUndoManager().addEdit(spinnerChange);

            channelOldValue = value;
        }

        if (isAlteringModels()) {

            // update module global volume value
            moduleHeader.setChannelCount((byte) value);
        }
    }
    
    public void globalVolumeSpinnerOnChange() {

        JSpinner globalVolumeSpinner = detailsUI.getModuleTools()
                .getModuleSoundOptions().getGlobalVolumeValue();

        int value = (int) globalVolumeSpinner.getValue();

        // alter the slider linked to this spinner
        boolean recordUndoState = isRecordingUndos();
        setRecordingUndos(false);
        detailsUI.getModuleTools().getModuleSoundOptions()
                .getGlobalVolumeSlider().setValue(value);
        setRecordingUndos(recordUndoState);

        if (isRecordingUndos()) {
            // undo event
            UndoableSpinnerChange spinnerChange
                    = new UndoableSpinnerChange(globalVolumeSpinner,
                            globalVolumeSpinnerOldValue);

            getCurrentUndoManager().addEdit(spinnerChange);
        }
        
        globalVolumeSpinnerOldValue = value;

        if (isAlteringModels()) {

            // update module global volume value
            moduleHeader.setGlobalVolume((byte) value);
        }
    }

    public void globalVolumeSliderOnChange() {

        JSlider globalVolumeSlider = detailsUI.getModuleTools()
                .getModuleSoundOptions().getGlobalVolumeSlider();

        int value = globalVolumeSlider.getValue();

        // alter the spinner linked to this slider
        boolean recordUndoState = isRecordingUndos();
        setRecordingUndos(false);
        detailsUI.getModuleTools().getModuleSoundOptions()
                .getGlobalVolumeValue().setValue(value);
        setRecordingUndos(recordUndoState);

        if (!globalVolumeSlider.getValueIsAdjusting()) {

            if (isRecordingUndos()) {
                // undo event
                UndoableSliderChange sliderChange
                        = new UndoableSliderChange(globalVolumeSlider,
                                globalVolumeSliderOldValue);

                getCurrentUndoManager().addEdit(sliderChange);
            }
            
            globalVolumeSliderOldValue = value;

            if (isAlteringModels()) {

                // update sample global volume value
                moduleHeader.setGlobalVolume((byte) value);
            }
        }
    }
    
    public void mixVolumeSpinnerOnChange() {

        // get the spinner
        JSpinner mixVolumeSpinner = detailsUI.getModuleTools()
                .getModuleSoundOptions().getMixVolumeValue();

        int value = (int) mixVolumeSpinner.getValue();

        // alter the slider linked to this spinner
        boolean recordUndoState = isRecordingUndos();
        setRecordingUndos(false);
        detailsUI.getModuleTools().getModuleSoundOptions().getMixVolumeSlider()
                .setValue(value);
        setRecordingUndos(recordUndoState);

        if (isRecordingUndos()) {
            // undo event
            UndoableSpinnerChange spinnerChange
                    = new UndoableSpinnerChange(mixVolumeSpinner,
                            mixSpinnerOldValue);

            getCurrentUndoManager().addEdit(spinnerChange);
        }
        
        mixSpinnerOldValue = value;

        if (isAlteringModels()) {

            // update module global volume value
            moduleHeader.setMixVolume((short) value);
        }
    }

    public void mixVolumeSliderOnChange() {

        JSlider mixVolumeSlider = detailsUI.getModuleTools()
                .getModuleSoundOptions().getMixVolumeSlider();

        int value = mixVolumeSlider.getValue();

        // alter the spinner linked to this slider
        boolean recordUndoState = isRecordingUndos();
        setRecordingUndos(false);
        detailsUI.getModuleTools().getModuleSoundOptions()
                .getMixVolumeValue().setValue(value);
        setRecordingUndos(recordUndoState);

        if (!mixVolumeSlider.getValueIsAdjusting()) {

            if (isRecordingUndos()) {
                // undo event
                UndoableSliderChange sliderChange
                        = new UndoableSliderChange(mixVolumeSlider,
                                mixSliderOldValue);

                getCurrentUndoManager().addEdit(sliderChange);
            }
            
            mixSliderOldValue = value;

            if (isAlteringModels()) {

                // update sample global volume value
                moduleHeader.setMixVolume((short) value);
            }
        }
    }
    
    public void panSeparationSpinnerOnChange() {

        JSpinner panSeparationSpinner = detailsUI.getModuleTools()
                .getModuleSoundOptions().getPanSeparationValue();

        int value = (int) panSeparationSpinner.getValue();

        // alter the slider linked to this spinner
        boolean recordUndoState = isRecordingUndos();
        setRecordingUndos(false);
        detailsUI.getModuleTools().getModuleSoundOptions()
                .getPanSeparationSlider().setValue(value);
        setRecordingUndos(recordUndoState);

        if (isRecordingUndos()) {
            // undo event
            UndoableSpinnerChange spinnerChange
                    = new UndoableSpinnerChange(panSeparationSpinner,
                            panSeparationSpinnerOldValue);

            getCurrentUndoManager().addEdit(spinnerChange);
        }
        
        panSeparationSpinnerOldValue = value;

        if (isAlteringModels()) {

            // update module global volume value
            moduleHeader.setMixVolume((short) value);
        }
    }

    public void panSeparationSliderOnChange() {

        JSlider panSeparationSlider = detailsUI.getModuleTools()
                .getModuleSoundOptions().getPanSeparationSlider();

        int value = panSeparationSlider.getValue();

        // alter the spinner linked to this slider
        boolean recordUndoState = isRecordingUndos();
        setRecordingUndos(false);
        detailsUI.getModuleTools().getModuleSoundOptions()
                .getPanSeparationValue().setValue(value);
        setRecordingUndos(recordUndoState);

        if (!panSeparationSlider.getValueIsAdjusting()) {

            if (isRecordingUndos()) {
                // undo event
                UndoableSliderChange sliderChange
                        = new UndoableSliderChange(panSeparationSlider,
                                panSeparationSliderOldValue);

                getCurrentUndoManager().addEdit(sliderChange);
            }
            
            panSeparationSliderOldValue = value;

            if (isAlteringModels()) {

                // update sample global volume value
                moduleHeader.setMixVolume((short) value);
            }
        }
    }
    
    // initial timings
    public void tickSpeedSpinnerOnChange() {
        
        JSpinner tickSpeedSpinner = detailsUI.getModuleTools()
                .getModuleTiming().getTickSpeedSpinner();

        int value = (int) tickSpeedSpinner.getValue();
        
        if (isRecordingUndos()) {
            
            // undo event
            UndoableSpinnerChange spinnerChange
                    = new UndoableSpinnerChange(tickSpeedSpinner,
                            tickSpeedOldValue);

            getCurrentUndoManager().addEdit(spinnerChange);

            tickSpeedOldValue = value;
        }

        if (isAlteringModels()) {

            // update module global volume value
            moduleHeader.setInitialSpeed((short) value);
        }
    }
    
    // initial timings
    public void tempoSpinnerOnChange() {
        
        JSpinner tempoSpinner = detailsUI.getModuleTools()
                .getModuleTiming().getTempoSpinner();
        
        int value = (int) tempoSpinner.getValue();
        
        if (isRecordingUndos()) {
            
            // undo event
            UndoableSpinnerChange spinnerChange
                    = new UndoableSpinnerChange(tempoSpinner,
                            tempoOldValue);

            getCurrentUndoManager().addEdit(spinnerChange);

            tempoOldValue = value;
        }

        if (isAlteringModels()) {

            // update module global volume value
            moduleHeader.setInitialTempo((short) value);
        }
    }
    
    // module options
    public void stereoOptionChange() {
        
        JRadioButton stereoRadioButtion = detailsUI.getModuleTools()
                .getModuleOptions().getStereoOption();
        
        if (isRecordingUndos()) {
            
            // undo event
            UndoableRadioButtonChange radioButtionChange
                    = new UndoableRadioButtonChange(stereoRadioButtion);

            getCurrentUndoManager().addEdit(radioButtionChange);
        }

        if (isAlteringModels()) {

            // update module global volume value
            moduleHeader.setStereo(stereoRadioButtion.isSelected());
        }
    }
    
    public void monoOptionChange() {
        
        JRadioButton monoRadioButtion = detailsUI.getModuleTools()
                .getModuleOptions().getMonoOption();
        
        if (isRecordingUndos()) {
            
            // undo event
            UndoableRadioButtonChange radioButtionChange
                    = new UndoableRadioButtonChange(monoRadioButtion);

            getCurrentUndoManager().addEdit(radioButtionChange);
        }

        if (isAlteringModels()) {

            // update module global volume value
            moduleHeader.setStereo(!monoRadioButtion.isSelected());
        }
    }
    
    public void loadHeaderProperties() {
        
        // set recording undos and alterations to false for a header load
        setRecordingUndos(false);
        setAlteringModels(false);
        
        // set module details listeners
        ModuleDetails modDetails 
                = detailsUI.getModuleTools().getModuleDetails();
        modDetails.getSongNameField().setText(moduleHeader.getSongName());
        modDetails.getSongArtistField().setText(moduleHeader.getArtistName());
        
        // created version
        String createdVerision 
                = Integer.toHexString(moduleHeader.getCreatedWithTracker());
        
        modDetails.getCreatedVersionLabel().setText(createdVerision);
        
        modDetails.getCreatedVersionLabel().setToolTipText(
                moduleHeader.getPossibleCreatedSoftware());
        
        // compatible version
        String compatibleVersion
                = Integer.toHexString(moduleHeader.getCompatibleWithTracker());
        
        modDetails.getCompatibleVersionLabel().setText(compatibleVersion);
        
        modDetails.getCompatibleVersionLabel().setToolTipText(
                moduleHeader.getLowestCompatibleSoftware());
        
        // set module sound options
        ModuleSoundOptions modSoundOptions 
                = detailsUI.getModuleTools().getModuleSoundOptions();
        
        // global volume
        globalVolumeSpinnerOldValue = moduleHeader.getGlobalVolume();
        modSoundOptions.getGlobalVolumeValue()
                .setValue((int)moduleHeader.getGlobalVolume());
        globalVolumeSliderOldValue = moduleHeader.getGlobalVolume();
        modSoundOptions.getGlobalVolumeSlider()
                .setValue(moduleHeader.getGlobalVolume());
        
        // mix
        mixSpinnerOldValue = moduleHeader.getMixVolume();
        modSoundOptions.getMixVolumeValue()
                .setValue((int)moduleHeader.getMixVolume());
        mixSliderOldValue = moduleHeader.getMixVolume();
        modSoundOptions.getMixVolumeSlider()
                .setValue(moduleHeader.getMixVolume());
        
        // pan separation
        panSeparationSpinnerOldValue = moduleHeader.getPanSeparation();
        modSoundOptions.getPanSeparationValue()
                .setValue((int)moduleHeader.getPanSeparation());
        panSeparationSliderOldValue = moduleHeader.getPanSeparation();
        modSoundOptions.getPanSeparationSlider()
                .setValue(moduleHeader.getPanSeparation());
        
        // options
        ModuleOptions modOptions 
                = detailsUI.getModuleTools().getModuleOptions();
        
        // channels
        boolean stereo = moduleHeader.isStereo();
        
        if (stereo) {
            // stereo
            modOptions.getStereoOption().setSelected(true);
        } else {
            // mono
            modOptions.getMonoOption().setSelected(true);
        }
        
        // timing
        InitialTiming initialTiming
                = detailsUI.getModuleTools().getModuleTiming();
        
        // intials speed
        tickSpeedOldValue = moduleHeader.getInitialSpeed();
        initialTiming.getTickSpeedSpinner()
                .setValue((int)moduleHeader.getInitialSpeed());
        
        // initial tempo
        tempoOldValue = moduleHeader.getInitialTempo();
        initialTiming.getTempoSpinner()
                .setValue((int)moduleHeader.getInitialTempo());
        
        // message
        ModuleMessage moduleMessage 
                = detailsUI.getModuleTools().getModuleMessage();
        
        moduleMessage.getMessageArea().setText(moduleHeader.getMessage());
        
        // set record undo back to true
        setRecordingUndos(true);
        setAlteringModels(true);
    }
}
