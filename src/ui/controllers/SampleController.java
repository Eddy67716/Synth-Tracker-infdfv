/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.controllers;

import java.awt.Dimension;
import ui.view.samples.SampleTools;
import ui.view.samples.SampleUI;
import ui.view.samples.SampleWindow;
import ui.view.models.EditSampleViewModel;
import ui.view.models.LoadViewModel;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.io.File;
import java.util.List;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.undo.UndoManager;
import lang.LanguageHandler;
import ui.controllers.undo.UndoableCheckBoxChange;
import ui.controllers.undo.UndoableComboBoxChange;
import ui.controllers.undo.UndoableSliderChange;
import ui.controllers.undo.UndoableSpinnerChange;
import ui.view.samples.LoopingTools;
import ui.view.samples.SampleCanvas;
import ui.view.samples.SampleDetails;
import ui.view.samples.SampleSoundOptions;
import ui.view.samples.SamplingTools;
import ui.view.samples.SustainLoopTools;
import ui.view.samples.VibratoOptions;
import module.ISampleSynth;

/**
 *
 * @author Edward Jenkins
 */
public class SampleController extends GenericController {

    // instance variables
    private SampleUI sampleUI;
    private EditSampleViewModel editSampleVM;
    private LoadViewModel loadVM;
    private ISampleSynth selectedSample;
    private UndoManager[] sampleManagers;
    private LanguageHandler languageHandler;
    private String oldSampleName;
    private String oldFileName;
    private int defaultVolSpinnerOldValue;
    private int defaultVolSliderOldValue;
    private int globalVolSpinnerOldValue;
    private int globalVolSliderOldValue;
    private int panSpinnerOldValue;
    private int panSliderOldValue;
    private int loopOldIndex;
    private int loopStartOldValue;
    private int loopEndOldValue;
    private int sustainLoopOldIndex;
    private int susLoopStartOldValue;
    private int susLoopEndOldValue;
    private int oldC5Speed;
    private int oldTransposeIndex;
    private int oldVibratoWaveform;
    private int oldVibratoSpeed;
    private int oldVibratoDepth;
    private int oldVibratoDelay;

    // constructor
    public SampleController(SampleUI sampleUI, LoadViewModel loadVM) {
        super();
        this.sampleUI = sampleUI;
        this.languageHandler = sampleUI.getLanguageHandler();
        this.loadVM = loadVM;

        // initialise undo managers
        sampleManagers = new UndoManager[loadVM.getSamples().size()];
        for (int i = 0; i < sampleManagers.length; i++) {
            sampleManagers[i] = new UndoManager();
        }

        // set sample details lisiteners
        SampleDetails sd = this.sampleUI.getTools().getSampleDetails();
        sd.addSampleNameFieldActionListener(e -> sampleNameOnChange());
        sd.addFileNameFieldActionListener(e -> fileNameOnChange());

        // set sound options listeners
        SampleSoundOptions so = this.sampleUI.getTools().getSoundOptions();
        so.addDefVolumeValChangeListener(e -> defaultVolumeSpinnerOnChange());
        so.addDefVolumeSliderChangeListener(e -> defaultVolumeSliderOnChange());
        so.addGlobVolumeValChangeListener(e -> globalVolumeSpinnerOnChange());
        so.addGlobVolumeSliderChangeListener(e -> globalVolumeSliderOnChange());
        so.addPaningActionListener(e -> panOnChange());
        so.addDefPanValChangeListener(e -> defaultPanningSpinnerOnChange());
        so.addDefPanSliderChangeListener(e -> defaultPanningSliderOnChange());

        // set loop otions listeners
        LoopingTools lt = sampleUI.getTools().getLoopingTools();
        lt.addLoopComboBoxActionListener(e -> loopComboBoxOnChange());
        lt.addLoopStartSpinnerChangeListener(e -> loopStartSpinnerOnChange());
        lt.addLoopEndSpinnerChangeListener(e -> loopEndSpinnerOnChange());

        // set sustain loop otions listeners
        SustainLoopTools slt = sampleUI.getTools().getSusLoopTools();
        slt.addSusLoopComboBoxActionListener(e -> susLoopComboBoxOnChange());
        slt.addSusLoopStartSpinnerChangeListener(e
                -> susLoopStartSpinnerOnChange());
        slt.addSusLoopEndSpinnerChangeListener(e
                -> susLoopEndSpinnerOnChange());

        // set vibrato options listeners
        VibratoOptions vo = sampleUI.getTools().getVibratoOptions();
        vo.addVibSpeedSpinnerChangeListener(e -> vibratoSpeedOnChange());
        vo.addVibDepthSpinnerChangeListener(e -> vibratoDepthOnChange());
        vo.addVibDelaySpinnerChangeListener(e -> vibratoDelayOnChange());
        vo.addVibWaveformComboBoxActionListenr(e -> vibratoWaveformOnChange());

        // set sample options listeners
        SamplingTools st = sampleUI.getTools().getSamplingTools();
        st.addC5SampleRateSpinnerChangeEvent(
                e -> c5SampleRateSpinnerOnChange());
        st.addSampleTransposeComboBox(e -> sampleTransposeComboBoxOnChange());
        
        // set sample canvas slider options
        SampleWindow sw = sampleUI.getSampleWindow();
        sw.addZoomSliderChangeEvent(e -> zoomSliderOnChange());

        this.sampleUI.addSampleSelectSpinnerChangeListener(
                e -> sampleOnChange());
        // set drop target
        this.sampleUI.setDropTarget(new DropTarget() {

            @Override
            public synchronized void drop(DropTargetDropEvent evt) {
                try {
                    evt.acceptDrop(DnDConstants.ACTION_COPY);
                    List<File> droppedFiles = (List<File>) evt.getTransferable()
                            .getTransferData(DataFlavor.javaFileListFlavor);
                    for (File file : droppedFiles) {
                        loadSample(file);
                    }
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        });
        sampleOnChange();
    }

    // controller methods
    // sample details
    private void sampleNameOnChange() {

        JTextField sampleNameField = sampleUI.getTools().getSampleDetails()
                .getSampleNameField();

        String sampleNameText = sampleNameField.getText();

        if (isRecordingUndos()) {
            //sampleNameText.
        }

        if (isAlteringModels()) {
            selectedSample.setSampleName(sampleNameText);
        }
    }

    private void fileNameOnChange() {

        String sampleNameText = sampleUI.getTools().getSampleDetails()
                .getFileNameField().getText();

        if (isAlteringModels()) {

            selectedSample.setDosFileName(sampleNameText.substring(0, 12));
        }
    }

    // sound options
    public void defaultVolumeSpinnerOnChange() {

        JSpinner defaultVolumeSpinner = sampleUI.getTools().getSoundOptions()
                .getDefaultVolumeValue();

        int value = (int) defaultVolumeSpinner.getValue();

        // alter the slider linked to this spinner
        boolean recordUndoState = isRecordingUndos();
        setRecordingUndos(false);
        sampleUI.getTools().getSoundOptions().getDefaultVolumeSlider()
                .setValue(value);
        setRecordingUndos(recordUndoState);

        if (isRecordingUndos()) {
            // undo event
            UndoableSpinnerChange spinnerChange
                    = new UndoableSpinnerChange(defaultVolumeSpinner,
                            defaultVolSpinnerOldValue);

            getCurrentUndoManager().addEdit(spinnerChange);
        }
        
        defaultVolSpinnerOldValue = value;

        if (isAlteringModels()) {

            // update sample default volume value
            selectedSample.setNormalisedDefaultVolume((byte) value);
        }
    }

    public void defaultVolumeSliderOnChange() {

        JSlider defaultVolumeSlider = sampleUI.getTools().getSoundOptions()
                .getDefaultVolumeSlider();

        int value = defaultVolumeSlider.getValue();

        // alter the spinner linked to this slider
        boolean recordUndoState = isRecordingUndos();
        setRecordingUndos(false);
        sampleUI.getTools().getSoundOptions().getDefaultVolumeValue()
                .setValue(value);
        setRecordingUndos(recordUndoState);

        if (!defaultVolumeSlider.getValueIsAdjusting()) {

            if (isRecordingUndos()) {
                // undo event
                UndoableSliderChange sliderChange
                        = new UndoableSliderChange(defaultVolumeSlider,
                                defaultVolSliderOldValue);

                getCurrentUndoManager().addEdit(sliderChange);
            }
            
            defaultVolSliderOldValue = value;

            if (isAlteringModels()) {

                // update sample default volume value
                selectedSample.setNormalisedDefaultVolume((byte) value);
            }
        }
    }

    public void globalVolumeSpinnerOnChange() {

        JSpinner globalVolumeSpinner = sampleUI.getTools().getSoundOptions()
                .getGlobalVolumeValue();

        int value = (int) globalVolumeSpinner.getValue();

        // alter the slider linked to this spinner
        boolean recordUndoState = isRecordingUndos();
        setRecordingUndos(false);
        sampleUI.getTools().getSoundOptions().getGlobalVolumeSlider()
                .setValue(value);
        setRecordingUndos(recordUndoState);

        if (isRecordingUndos()) {
            // undo event
            UndoableSpinnerChange spinnerChange
                    = new UndoableSpinnerChange(globalVolumeSpinner,
                            globalVolSpinnerOldValue);

            getCurrentUndoManager().addEdit(spinnerChange);
        }
        
        globalVolSpinnerOldValue = value;

        if (isAlteringModels()) {

            // update sample global volume value
            selectedSample.setNormalisedGlobalVolume((byte) value);
        }
    }

    public void globalVolumeSliderOnChange() {

        JSlider globalVolumeSlider = sampleUI.getTools().getSoundOptions()
                .getGlobalVolumeSlider();

        int value = globalVolumeSlider.getValue();

        // alter the spinner linked to this slider
        boolean recordUndoState = isRecordingUndos();
        setRecordingUndos(false);
        sampleUI.getTools().getSoundOptions().getGlobalVolumeValue()
                .setValue(value);
        setRecordingUndos(recordUndoState);

        if (!globalVolumeSlider.getValueIsAdjusting()) {

            if (isRecordingUndos()) {
                // undo event
                UndoableSliderChange sliderChange
                        = new UndoableSliderChange(globalVolumeSlider,
                                globalVolSliderOldValue);

                getCurrentUndoManager().addEdit(sliderChange);
            }
            
            globalVolSliderOldValue = value;

            if (isAlteringModels()) {

                // update sample global volume value
                selectedSample.setNormalisedGlobalVolume((byte) value);
            }
        }
    }

    public void panOnChange() {

        JCheckBox panning = sampleUI.getTools().getSoundOptions()
                .getPanning();

        boolean isSelected = panning.isSelected();

        sampleUI.getTools().getSoundOptions().getDefaultPanningSlider()
                .setEnabled(isSelected);
        sampleUI.getTools().getSoundOptions().getDefaultPanningValue()
                .setEnabled(isSelected);

        if (isRecordingUndos()) {
            // undo event
            UndoableCheckBoxChange checkBoxChange
                    = new UndoableCheckBoxChange(panning);

            getCurrentUndoManager().addEdit(checkBoxChange);
        }

        if (isAlteringModels()) {

            // update sample panning
            selectedSample.setPanning(isSelected);
        }
    }

    public void defaultPanningSpinnerOnChange() {
        JSpinner defaultPanningSpinner = sampleUI.getTools().getSoundOptions()
                .getDefaultPanningValue();

        int value = (int) defaultPanningSpinner.getValue();

        // alter the slider linked to this spinner
        boolean recordUndoState = isRecordingUndos();
        setRecordingUndos(false);
        sampleUI.getTools().getSoundOptions().getDefaultPanningSlider()
                .setValue(value);
        setRecordingUndos(recordUndoState);

        if (isRecordingUndos()) {
            // undo event
            UndoableSpinnerChange spinnerChange
                    = new UndoableSpinnerChange(defaultPanningSpinner,
                            panSpinnerOldValue);

            getCurrentUndoManager().addEdit(spinnerChange);
        }
        
        panSpinnerOldValue = value;

        if (isAlteringModels()) {

            // update sample pan value
            selectedSample.setNormalisedPanValue((byte) value);
        }
    }

    public void defaultPanningSliderOnChange() {
        JSlider defaultPanningSlider = sampleUI.getTools().getSoundOptions()
                .getDefaultPanningSlider();

        int value = defaultPanningSlider.getValue();

        // alter the spinner linked to this slider
        boolean recordUndoState = isRecordingUndos();
        setRecordingUndos(false);
        sampleUI.getTools().getSoundOptions().getDefaultPanningValue()
                .setValue(value);
        setRecordingUndos(recordUndoState);

        if (!defaultPanningSlider.getValueIsAdjusting()) {

            if (isRecordingUndos()) {
                // undo event
                UndoableSliderChange sliderChange
                        = new UndoableSliderChange(defaultPanningSlider,
                                panSliderOldValue);

                getCurrentUndoManager().addEdit(sliderChange);
            }
            
            panSliderOldValue = value;

            if (isAlteringModels()) {

                // update sample pan value
                selectedSample.setPanValue((byte) value);
            }
        }
    }

    // loop tools
    public void loopComboBoxOnChange() {

        JComboBox loopComboBox = sampleUI.getTools()
                .getLoopingTools().getLoopComboBox();

        int index = loopComboBox.getSelectedIndex();

        if (isRecordingUndos()) {
            // undo event
            UndoableComboBoxChange comboBoxChange
                    = new UndoableComboBoxChange(loopComboBox, loopOldIndex);

            // append event to manager
            getCurrentUndoManager().addEdit(comboBoxChange);

            loopOldIndex = index;
        }

        if (isAlteringModels()) {

            // alter sample
            switch (index) {
                case 1:
                    selectedSample.setLooped(true);
                    selectedSample.setPingPongLooped(false);
                    break;
                case 2:
                    selectedSample.setLooped(false);
                    selectedSample.setPingPongLooped(true);
                default:
                    selectedSample.setLooped(false);
                    selectedSample.setPingPongLooped(false);
                    break;
            }
        }
    }

    public void loopStartSpinnerOnChange() {

        JSpinner loopStartSpinner = sampleUI.getTools().getLoopingTools()
                .getLoopStartSpinner();

        int value = (int) loopStartSpinner.getValue();

        if (isRecordingUndos()) {
            // undo event
            UndoableSpinnerChange spinnerChange
                    = new UndoableSpinnerChange(loopStartSpinner,
                            loopStartOldValue);

            getCurrentUndoManager().addEdit(spinnerChange);

            loopStartOldValue = value;
        }

        if (isAlteringModels()) {

            // update sample pan value
            selectedSample.setLoopBeginning(value);
        }
    }

    public void loopEndSpinnerOnChange() {

        JSpinner loopEndSpinner = sampleUI.getTools().getLoopingTools()
                .getLoopEndSpinner();

        int value = (int) loopEndSpinner.getValue();

        if (isRecordingUndos()) {
            // undo event
            UndoableSpinnerChange spinnerChange
                    = new UndoableSpinnerChange(loopEndSpinner,
                            loopEndOldValue);

            getCurrentUndoManager().addEdit(spinnerChange);

            loopEndOldValue = value;
        }

        if (isAlteringModels()) {

            // update sample pan value
            selectedSample.setLoopEnd(value);
        }
    }

    // sustain loop tools
    public void susLoopComboBoxOnChange() {

        JComboBox susLoopComboBox = sampleUI.getTools()
                .getSusLoopTools().getSusLoopComboBox();

        int index = susLoopComboBox.getSelectedIndex();

        if (isRecordingUndos()) {
            // undo event
            UndoableComboBoxChange comboBoxChange
                    = new UndoableComboBoxChange(susLoopComboBox,
                            sustainLoopOldIndex);

            // append event to manager
            getCurrentUndoManager().addEdit(comboBoxChange);

            sustainLoopOldIndex = index;
        }

        if (isAlteringModels()) {

            // alter sample
            switch (index) {
                case 1:
                    selectedSample.setSustainLooped(true);
                    selectedSample.setPingPongSustainLooped(false);
                    break;
                case 2:
                    selectedSample.setSustainLooped(false);
                    selectedSample.setPingPongSustainLooped(true);
                default:
                    selectedSample.setSustainLooped(false);
                    selectedSample.setPingPongSustainLooped(false);
                    break;
            }
        }
    }

    public void susLoopStartSpinnerOnChange() {

        JSpinner susLoopStartSpinner = sampleUI.getTools().getSusLoopTools()
                .getSusLoopStartSpinner();

        int value = (int) susLoopStartSpinner.getValue();

        if (isRecordingUndos()) {
            // undo event
            UndoableSpinnerChange spinnerChange
                    = new UndoableSpinnerChange(susLoopStartSpinner,
                            susLoopStartOldValue);

            getCurrentUndoManager().addEdit(spinnerChange);

            susLoopStartOldValue = value;
        }

        if (isAlteringModels()) {

            // update sample pan value
            selectedSample.setSustainLoopBeginning(value);
        }
    }

    public void susLoopEndSpinnerOnChange() {

        JSpinner susLoopEndSpinner = sampleUI.getTools().getSusLoopTools()
                .getSusLoopEndSpinner();

        int value = (int) susLoopEndSpinner.getValue();

        if (isRecordingUndos()) {
            // undo event
            UndoableSpinnerChange spinnerChange
                    = new UndoableSpinnerChange(susLoopEndSpinner,
                            susLoopEndOldValue);

            getCurrentUndoManager().addEdit(spinnerChange);

            susLoopEndOldValue = value;
        }

        if (isAlteringModels()) {

            // update sample pan value
            selectedSample.setSustainLoopEnd(value);
        }
    }

    // vibrato options
    public void vibratoSpeedOnChange() {

        JSpinner vibSpeedSpinner = sampleUI.getTools().getVibratoOptions()
                .getVibSpeedSpinner();

        int value = (int) vibSpeedSpinner.getValue();

        if (isRecordingUndos()) {
            // undo event
            UndoableSpinnerChange spinnerChange
                    = new UndoableSpinnerChange(vibSpeedSpinner,
                            oldVibratoSpeed);

            getCurrentUndoManager().addEdit(spinnerChange);

            oldVibratoSpeed = value;
        }

        if (isAlteringModels()) {

            // update sample pan value
            selectedSample.setVibratoSpeed(value);
        }
    }

    public void vibratoDepthOnChange() {

        JSpinner vibDepthSpinner = sampleUI.getTools().getVibratoOptions()
                .getVibSpeedSpinner();

        int value = (int) vibDepthSpinner.getValue();

        if (isRecordingUndos()) {
            // undo event
            UndoableSpinnerChange spinnerChange
                    = new UndoableSpinnerChange(vibDepthSpinner,
                            oldVibratoDepth);

            getCurrentUndoManager().addEdit(spinnerChange);

            oldVibratoDepth = value;
        }

        if (isAlteringModels()) {

            // update sample pan value
            selectedSample.setFullVibratoDepth(value);
        }
    }

    public void vibratoDelayOnChange() {

        JSpinner vibDelaySpinner = sampleUI.getTools().getVibratoOptions()
                .getVibSpeedSpinner();

        int value = (int) vibDelaySpinner.getValue();

        if (isRecordingUndos()) {
            // undo event
            UndoableSpinnerChange spinnerChange
                    = new UndoableSpinnerChange(vibDelaySpinner,
                            oldVibratoDelay);

            getCurrentUndoManager().addEdit(spinnerChange);

            oldVibratoDelay = value;
        }

        if (isAlteringModels()) {

            // update sample pan value
            selectedSample.setVibratoDelay(value);
        }
    }

    public void vibratoWaveformOnChange() {

        JComboBox vibWaveformComboBox = sampleUI.getTools()
                .getVibratoOptions().getVibWaveformComboBox();

        int index = vibWaveformComboBox.getSelectedIndex();

        if (isRecordingUndos()) {
            // undo event
            UndoableComboBoxChange comboBoxChange
                    = new UndoableComboBoxChange(vibWaveformComboBox,
                            oldVibratoWaveform);

            // append event to manager
            getCurrentUndoManager().addEdit(comboBoxChange);

            oldVibratoWaveform = index;
        }

        if (isAlteringModels()) {

            selectedSample.setVibratoWaveform((byte)index);
        }
    }

    // sampling tools
    public void c5SampleRateSpinnerOnChange() {

        JSpinner c5SampleRateSpinner = sampleUI.getTools().getSamplingTools()
                .getC5SampleRateSpinner();

        int value = (int) c5SampleRateSpinner.getValue();

        if (isRecordingUndos()) {
            // undo event
            UndoableSpinnerChange spinnerChange
                    = new UndoableSpinnerChange(c5SampleRateSpinner,
                            oldC5Speed);

            getCurrentUndoManager().addEdit(spinnerChange);

            oldC5Speed = value;
        }

        if (isAlteringModels()) {

            // update sample pan value
            selectedSample.setC5Speed(value);
        }
    }

    public void sampleTransposeComboBoxOnChange() {

        JComboBox sampleTransposeComboBox = sampleUI.getTools()
                .getSamplingTools().getSampleTransposeComboBox();

        int index = sampleTransposeComboBox.getSelectedIndex();

        if (isRecordingUndos()) {
            // undo event
            UndoableComboBoxChange comboBoxChange
                    = new UndoableComboBoxChange(sampleTransposeComboBox,
                            oldTransposeIndex);

            // append event to manager
            getCurrentUndoManager().addEdit(comboBoxChange);

            oldTransposeIndex = index;
        }

        if (isAlteringModels()) {

            //TODO
        }
    }
    
    public void zoomSliderOnChange() {
        SampleCanvas canvas = sampleUI.getSampleWindow().getCanvas();
        
        int zoomValue = sampleUI.getSampleWindow().getZoomSlider().getValue();
        
        double widthRatio = 1d / selectedSample.getSampleLength();
        
        double currentWidthRatio = zoomValue * widthRatio;
        
        int newWidth = (int)Math.round(1800 / currentWidthRatio);
        
        canvas.setSize(new Dimension(newWidth, canvas.getHeight()));
        canvas.setPreferredSize(new Dimension(newWidth, canvas.getHeight()));
        
        canvas.repaint();
    }

    public void sampleOnChange() {

        int modType = sampleUI.getTools().getModType();

        // set recording undos and alterations to false for a sample change
        setRecordingUndos(false);
        setAlteringModels(false);

        // loop code
        if ((int) sampleUI.getSampleSelectSpinner().getValue()
                == loadVM.getSamples().size() + 1) {
            sampleUI.getSampleSelectSpinner().setValue(1);
        } else if ((int) sampleUI.getSampleSelectSpinner().getValue() == 0) {
            sampleUI.getSampleSelectSpinner().setValue(loadVM.getSamples()
                    .size());
        }

        // get selected sample value
        int value = (int) sampleUI.getSampleSelectSpinner().getValue()
                - 1;

        // get sample
        sampleUI.setSelectedSample(value);
        selectedSample = loadVM.getSamples()
                .get(sampleUI.getSelectedSample());

        // set current undoManager
        setCurrentUndoManager(sampleManagers[value]);

        // sample name
        sampleUI.getTools().getSampleDetails()
                .getSampleNameField().setText(selectedSample.getSampleName());

        // DOS file name
        if (modType == 2 || modType >= 4) {
            sampleUI.getTools().getSampleDetails()
                    .getFileNameField().setText(selectedSample.getDosFileName());
        }

        // format
        String outputString;

        if (selectedSample.isCompressed()) {
            outputString = languageHandler
                            .getLanguageText("sample.details.format.compressed");
        } else {
            outputString = (selectedSample.isSigned()) 
                    ? languageHandler
                            .getLanguageText("sample.details.format.signed")
                    : languageHandler
                            .getLanguageText("sample.details.format.unsigned");
        }
        
        String bitrate = languageHandler
                            .getLanguageText("sample.details.format.bit")
                .replaceFirst("%1", Short
                        .toString(selectedSample.getBitRate()));

        outputString += bitrate;

        sampleUI.getTools().getSampleDetails()
                .getSampleFormatLabel().setText(outputString);

        // channels
        outputString = (selectedSample.isStereo()) 
                ? languageHandler
                            .getLanguageText("sound.channels.stereo") 
                : languageHandler
                            .getLanguageText("sound.channels.mono");

        sampleUI.getTools().getSampleDetails()
                .getSampleChannelLabel().setText(outputString);

        // length
        outputString = Integer.toString(selectedSample.getSampleLength());

        sampleUI.getTools().getSampleDetails()
                .getSampleLengthLabel().setText(outputString);

        // default volume
        // set old value
        defaultVolSpinnerOldValue = selectedSample.getDefaultVolume();
        defaultVolSliderOldValue = selectedSample.getDefaultVolume();

        sampleUI.getTools().getSoundOptions()
                .getDefaultVolumeSlider().setValue(defaultVolSpinnerOldValue);

        defaultVolumeSliderOnChange();

        // global volume
        // set old value
        globalVolSpinnerOldValue = selectedSample.getGlobalVolume();
        globalVolSliderOldValue = selectedSample.getGlobalVolume();

        sampleUI.getTools().getSoundOptions()
                .getGlobalVolumeSlider().setValue(globalVolSpinnerOldValue);

        globalVolumeSliderOnChange();

        // panning
        sampleUI.getTools().getSoundOptions()
                .getPanning().setSelected(selectedSample.isPanning());

        panOnChange();

        // default panning
        // set old value
        panSpinnerOldValue = selectedSample.getPanValue();
        panSliderOldValue = selectedSample.getPanValue();

        sampleUI.getTools().getSoundOptions()
                .getDefaultPanningSlider().setValue(panSpinnerOldValue);

        defaultPanningSliderOnChange();

        // loop
        loopOldIndex = 0;

        if (selectedSample.isLooped()) {

            loopOldIndex = 1;
        } else if (selectedSample.isPingPongLooped()) {

            loopOldIndex = 2;
        }

        sampleUI.getTools().getLoopingTools().getLoopComboBox()
                .setSelectedIndex(loopOldIndex);

        // loop start
        // set old value
        loopStartOldValue = (int) selectedSample.getLoopBeginning();

        SpinnerNumberModel loopStartModel
                = new SpinnerNumberModel(loopStartOldValue, 0,
                        selectedSample.getSampleLength(), 1);

        sampleUI.getTools().getLoopingTools().getLoopStartSpinner()
                .setModel(loopStartModel);

        // loop end
        // set old value
        loopEndOldValue = (int) selectedSample.getLoopEnd();

        SpinnerNumberModel loopEndModel
                = new SpinnerNumberModel(loopEndOldValue, 0,
                        selectedSample.getSampleLength(), 1);

        sampleUI.getTools().getLoopingTools().getLoopEndSpinner()
                .setModel(loopEndModel);

        // sustain loop
        sustainLoopOldIndex = 0;

        if (selectedSample.isSustainLooped()) {

            sustainLoopOldIndex = 1;
        } else if (selectedSample.isPingPongLooped()) {

            sustainLoopOldIndex = 2;
        }

        sampleUI.getTools().getSusLoopTools().getSusLoopComboBox()
                .setSelectedIndex(sustainLoopOldIndex);

        // sustain loop start
        susLoopStartOldValue = (int) selectedSample
                .getSustainLoopBeginning();

        SpinnerNumberModel susLoopStartModel
                = new SpinnerNumberModel(susLoopStartOldValue, 0,
                        selectedSample.getSampleLength(), 1);

        sampleUI.getTools().getSusLoopTools().getSusLoopStartSpinner()
                .setModel(susLoopStartModel);

        // sustain loop end
        susLoopEndOldValue = (int) selectedSample
                .getSustainLoopEnd();

        SpinnerNumberModel susLoopEndModel
                = new SpinnerNumberModel(susLoopEndOldValue, 0,
                        selectedSample.getSampleLength(), 1);

        sampleUI.getTools().getSusLoopTools().getSusLoopEndSpinner()
                .setModel(susLoopEndModel);

        // Middle C speed
        // set old value
        oldC5Speed = selectedSample.getC5Speed();

        sampleUI.getTools().getSamplingTools().getC5SampleRateSpinner()
                .setValue(oldC5Speed);
        
        oldTransposeIndex = sampleUI.getTools().getSamplingTools()
                .getSampleTransposeComboBox().getSelectedIndex();

        // vibrato speed
        // set old value
        oldVibratoSpeed = (int) selectedSample.getFullVibratoSpeed();

        sampleUI.getTools().getVibratoOptions().getVibSpeedSpinner()
                .setValue(oldVibratoSpeed);

        // vibrato depth
        // set old value
        oldVibratoDepth = (int) selectedSample.getFullVibratoDepth();

        sampleUI.getTools().getVibratoOptions().getVibDepthSpinner()
                .setValue(oldVibratoDepth);

        // vibrato delay
        // set old value
        oldVibratoDelay = (int) selectedSample.getVibratoDelay();

        sampleUI.getTools().getVibratoOptions().getVibDelaySpinner()
                .setValue(oldVibratoDelay);

        // vibrato waveform
        // set old value
        oldVibratoWaveform = selectedSample.getVibratoWaveform();

        sampleUI.getTools().getVibratoOptions().getVibWaveformComboBox()
                .setSelectedIndex(oldVibratoWaveform);
        
        // zoom slider value
        JSlider zoomSlider = sampleUI.getSampleWindow().getZoomSlider();
        zoomSlider.setMaximum(selectedSample.getSampleLength());
        zoomSlider.setMinimum(20);
        zoomSlider.setValue(selectedSample.getSampleLength());

        // sample data
        SwingUtilities.invokeLater(() -> {
            sampleUI.getSampleWindow().getCanvas()
                    .setSampleData(selectedSample.getAudioSampleData());
        });

        // set record undo back to true
        setRecordingUndos(true);
        setAlteringModels(true);
    }

    public void loadSample(File file) {

        // lodad sample using loadVM
        loadVM.readSampleFile(file.getPath());
    }
}
