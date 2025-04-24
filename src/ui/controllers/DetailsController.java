/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.controllers;

import java.util.List;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JCheckBox;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.undo.UndoManager;
import module.IChannel;
import module.IModuleHeader;
import module.it.format.EditHistoryEvent;
import module.it.format.ItHeader;
import ui.controllers.undo.UndoableButtonGroupChange;
import ui.controllers.undo.UndoableCheckBoxChange;
import ui.controllers.undo.UndoableSliderChange;
import ui.controllers.undo.UndoableSpinnerChange;
import ui.view.details.ChannelPanel;
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
    private ButtonModel channelSelectionOldButton;
    private ButtonModel slideSelectionOldButton;
    private ButtonModel effectSelectionOldButton;
    private ButtonModel instrumentSelectionOldButton;
    
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
        modOptions.addStereoOptionsAction(e -> stereoOptionChange());
        modOptions.addMonoOptionsAction(e -> monoOptionChange());
        modOptions.addInstrumentOptionsAction(e -> instrumentOptionChange());
        modOptions.addSampleOptionsAction(e -> sampleOptionChange());
        modOptions.addLinearSlideOptionsAction(e -> linearSlideOptionChange());
        modOptions.addAmigaSlideOptionsAction(e -> amigaSlideOptionChange());
        modOptions.addOldEffectOptionsAction(e -> oldEffectsOptionChange());
        modOptions.addNewEffectOptionsAction(e -> newEffectsOptionChange());
        modOptions.addPortamentoLinkAction(e -> portamentoLinkOnChange());
        
        loadHeaderProperties();
        
        ChannelPanel[] channelPanels = detailsUI.getChannelsPanel()
                .getChannelsPanel().getChannelPanels();
        
        // set up the channel controllers array
        channelControllers = new ChannelController[channelPanels.length];
        
        List<IChannel> channels = loadVM.getHeader().getIChannels();
        
        // setup channel controllers
        for (int i = 0; i < channelPanels.length; i++) {
            
            channelControllers[i] = new ChannelController(channelPanels[i],
                loadVM, detailsManager, channels.get(i));
        }
        
    }
    
    // controller methods
    // mod details
    private void songNameOnChange() {

        JTextField songNameField = detailsUI.getModuleTools().getModuleDetails()
                .getSongNameField();

        String sampleNameText = songNameField.getText();

        if (isAlteringModels()) {
            moduleHeader.setSongName(sampleNameText);
        }
    }

    private void artistNameOnChange() {

        JTextField artistNameField = detailsUI.getModuleTools().getModuleDetails()
                .getSongArtistField();
        
        String songArtistText = artistNameField.getText();

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
        
        ButtonGroup channelButtionGroup = detailsUI.getModuleTools()
                .getModuleOptions().getChannelGroup();
        
        JRadioButton stereoRadioButton = detailsUI.getModuleTools()
                .getModuleOptions().getStereoOption();
        
        if (isRecordingUndos()) {
            
            // undo event
            UndoableButtonGroupChange radioButtionChange
                    = new UndoableButtonGroupChange(channelButtionGroup, 
                            channelSelectionOldButton);

            getCurrentUndoManager().addEdit(radioButtionChange);
        }
        
        channelSelectionOldButton = stereoRadioButton.getModel();

        if (isAlteringModels()) {

            // update module global volume value
            moduleHeader.setStereo(stereoRadioButton.isSelected());
        }
    }
    
    public void monoOptionChange() {
        
        ButtonGroup channelButtionGroup = detailsUI.getModuleTools()
                .getModuleOptions().getChannelGroup();
        
        JRadioButton monoRadioButton = detailsUI.getModuleTools()
                .getModuleOptions().getMonoOption();
        
        if (isRecordingUndos()) {
            
            // undo event
            UndoableButtonGroupChange radioButtionChange
                    = new UndoableButtonGroupChange(channelButtionGroup, 
                            channelSelectionOldButton);

            getCurrentUndoManager().addEdit(radioButtionChange);
        }
        
        channelSelectionOldButton = monoRadioButton.getModel();

        if (isAlteringModels()) {

            // update module global volume value
            moduleHeader.setStereo(!monoRadioButton.isSelected());
        }
    }
    
    public void instrumentOptionChange() {
        
        ButtonGroup instrumentButtonGroup = detailsUI.getModuleTools()
                .getModuleOptions().getInstrumentGroup();
        
        JRadioButton instrumentRadioButton = detailsUI.getModuleTools()
                .getModuleOptions().getInstrumentOption();
        
        if (isRecordingUndos()) {
            
            // undo event
            UndoableButtonGroupChange radioButtionChange
                    = new UndoableButtonGroupChange(instrumentButtonGroup, 
                            instrumentSelectionOldButton);

            getCurrentUndoManager().addEdit(radioButtionChange);
        }
        
        instrumentSelectionOldButton = instrumentRadioButton.getModel();

        if (isAlteringModels()) {

            // update module global volume value
            moduleHeader.setUsingInstruments(
                    instrumentRadioButton.isSelected());
        }
    }
    
    public void sampleOptionChange() {
        
        ButtonGroup instrumentButtonGroup = detailsUI.getModuleTools()
                .getModuleOptions().getInstrumentGroup();
        
        JRadioButton sampleRadioButton = detailsUI.getModuleTools()
                .getModuleOptions().getSampleOption();
        
        if (isRecordingUndos()) {
            
            // undo event
            UndoableButtonGroupChange radioButtionChange
                    = new UndoableButtonGroupChange(instrumentButtonGroup, 
                            instrumentSelectionOldButton);

            getCurrentUndoManager().addEdit(radioButtionChange);
        }
        
        instrumentSelectionOldButton = sampleRadioButton.getModel();

        if (isAlteringModels()) {

            // update module global volume value
            moduleHeader.setUsingInstruments(!sampleRadioButton.isSelected());
        }
    }
    
    public void linearSlideOptionChange() {
        
        ButtonGroup slideButtonGroup = detailsUI.getModuleTools()
                .getModuleOptions().getSlideGroup();
        
        JRadioButton linearSlideRadioButton = detailsUI.getModuleTools()
                .getModuleOptions().getLinearSlidesOption();
        
        if (isRecordingUndos()) {
            
            // undo event
            UndoableButtonGroupChange radioButtionChange
                    = new UndoableButtonGroupChange(slideButtonGroup, 
                            slideSelectionOldButton);

            getCurrentUndoManager().addEdit(radioButtionChange);
        }
        
        slideSelectionOldButton = linearSlideRadioButton.getModel();

        if (isAlteringModels()) {

            // update module global volume value
            moduleHeader.setUsingLinearSlides(
                    linearSlideRadioButton.isSelected());
        }
    }
    
    public void amigaSlideOptionChange() {
        
        ButtonGroup slideButtonGroup = detailsUI.getModuleTools()
                .getModuleOptions().getSlideGroup();
        
        JRadioButton amigaSlideRadioButton = detailsUI.getModuleTools()
                .getModuleOptions().getAmigaSlidesOption();
        
        if (isRecordingUndos()) {
            
            // undo event
            UndoableButtonGroupChange radioButtionChange
                    = new UndoableButtonGroupChange(slideButtonGroup, 
                            slideSelectionOldButton);

            getCurrentUndoManager().addEdit(radioButtionChange);
        }
        
        slideSelectionOldButton = amigaSlideRadioButton.getModel();

        if (isAlteringModels()) {

            // update module global volume value
            moduleHeader.setUsingLinearSlides(!amigaSlideRadioButton
                    .isSelected());
        }
    }
    
    public void oldEffectsOptionChange() {
        
        ButtonGroup effectButtonGroup = detailsUI.getModuleTools()
                .getModuleOptions().getEffectGroup();
        
        JRadioButton oldEffectsRadioButton = detailsUI.getModuleTools()
                .getModuleOptions().getOldEffectsOption();
        
        if (isRecordingUndos()) {
            
            // undo event
            UndoableButtonGroupChange radioButtionChange
                    = new UndoableButtonGroupChange(effectButtonGroup, 
                            this.effectSelectionOldButton);

            getCurrentUndoManager().addEdit(radioButtionChange);
        }
        
        effectSelectionOldButton = oldEffectsRadioButton.getModel();

        if (isAlteringModels()) {

            // update module global volume value
            moduleHeader.setUsingOldEffects(oldEffectsRadioButton.isSelected());
        }
    }
    
    public void newEffectsOptionChange() {
        
        ButtonGroup slideButtonGroup = detailsUI.getModuleTools()
                .getModuleOptions().getEffectGroup();
        
        JRadioButton newEffectsRadioButton = detailsUI.getModuleTools()
                .getModuleOptions().getNewEffecstOption();
        
        if (isRecordingUndos()) {
            
            // undo event
            UndoableButtonGroupChange radioButtionChange
                    = new UndoableButtonGroupChange(slideButtonGroup, 
                            effectSelectionOldButton);

            getCurrentUndoManager().addEdit(radioButtionChange);
        }
        
        effectSelectionOldButton = newEffectsRadioButton.getModel();

        if (isAlteringModels()) {

            // update module global volume value
            moduleHeader.setUsingOldEffects(!newEffectsRadioButton.isSelected());
        }
    }
    
    // portamento link
    public void portamentoLinkOnChange() {
        
        JCheckBox portamentoLinkCheckBox = detailsUI.getModuleTools()
                .getModuleOptions().getPortamentoLink();
        
        if (isRecordingUndos()) {
            
            // undo event
            UndoableCheckBoxChange checkBoxChange
                    = new UndoableCheckBoxChange(portamentoLinkCheckBox);

            getCurrentUndoManager().addEdit(checkBoxChange);
        }

        if (isAlteringModels()) {

            // update module global volume value
            moduleHeader.setPortamentoLinked(portamentoLinkCheckBox
                    .isSelected());
        }
    }
    
    public void loadHeaderProperties() {
        
        // set recording undos and alterations to false for a header load
        setRecordingUndos(false);
        setAlteringModels(false);
        
        // set module details listeners
        ModuleDetails modDetails 
                = detailsUI.getModuleTools().getModuleDetails();
        // name
        modDetails.getSongNameField().setText(moduleHeader.getSongName());
        modDetails.getSongNameField().getDocument()
                .addUndoableEditListener(detailsManager);
        
        // artist name
        modDetails.getSongArtistField().setText(moduleHeader.getArtistName());
        modDetails.getSongArtistField().getDocument()
                .addUndoableEditListener(detailsManager);
        
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
        
        // set channels
        channelOldValue = moduleHeader.getChannelCount();
        modSoundOptions.getChannelSpinner().setValue(
                (int)moduleHeader.getChannelCount());
        
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
            channelSelectionOldButton = modOptions.getStereoOption().getModel();
        } else {
            // mono
            modOptions.getMonoOption().setSelected(true);
            channelSelectionOldButton = modOptions.getMonoOption().getModel();
        }
        
        // instruments
        boolean instrumental = moduleHeader.isUsingInstruments();
        
        if (instrumental) {
            // instruments
            modOptions.getInstrumentOption().setSelected(true);
            instrumentSelectionOldButton = modOptions.getInstrumentOption()
                    .getModel();
        } else {
            // samples
            modOptions.getSampleOption().setSelected(true);
            instrumentSelectionOldButton = modOptions.getSampleOption()
                    .getModel();
        }
        
        // slides
        boolean linear = moduleHeader.isUsingLinearSlides();
        
        if (linear) {
            // old
            modOptions.getLinearSlidesOption().setSelected(true);
            slideSelectionOldButton = modOptions.getLinearSlidesOption()
                    .getModel();
        } else {
            // new
            modOptions.getAmigaSlidesOption().setSelected(true);
            slideSelectionOldButton = modOptions.getAmigaSlidesOption()
                    .getModel();
        }
        
        // effects
        boolean old = moduleHeader.isUsingOldEffects();
        
        if (old) {
            // old
            modOptions.getOldEffectsOption().setSelected(true);
            effectSelectionOldButton = modOptions.getOldEffectsOption()
                    .getModel();
        } else {
            // new
            modOptions.getNewEffecstOption().setSelected(true);
            effectSelectionOldButton = modOptions.getNewEffecstOption()
                    .getModel();
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
        
        // add the undo manager
        moduleMessage.getMessageArea().getDocument()
                .addUndoableEditListener(detailsManager);
        
        // edit histories
        if (moduleHeader instanceof ItHeader) {
            
            ItHeader itHeader = (ItHeader) moduleHeader;
            
            JTextArea editHistoryArea = detailsUI.getChannelsPanel()
                .getEditHistoryPanel().getEditHistoryArea();
            
            EditHistoryEvent[] editHistoryEvents 
                    = itHeader.getEditHistoryEvents();
            
            if (editHistoryEvents != null) {
                int i = 0;
                for (EditHistoryEvent editHistoryEvent : editHistoryEvents) {
                    editHistoryArea.append(editHistoryEvent.toString());
                    if (i < editHistoryEvents.length - 1) {
                        editHistoryArea.append("\n");
                    }
                }
            }
        }
        
        // set record undo back to true
        setRecordingUndos(true);
        setAlteringModels(true);
    }
    
    @Override
    public void redo() {
        for (ChannelController controller : channelControllers) {
            controller.setRecordingUndos(false);
        }
        super.redo(); 
        for (ChannelController controller : channelControllers) {
            controller.setRecordingUndos(true);
        }
    }

    @Override
    public void undo() {
        for (ChannelController controller : channelControllers) {
            controller.setRecordingUndos(false);
        }
        super.undo(); 
        for (ChannelController controller : channelControllers) {
            controller.setRecordingUndos(true);
        }
    }
}
