/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.controllers;

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
import javax.swing.SpinnerNumberModel;
import module.IAudioSample;
import javax.swing.SwingUtilities;
import ui.view.samples.SampleDetails;
import ui.view.samples.SampleSoundOptions;

/**
 *
 * @author Edward Jenkins
 */
public class SampleController {

    // instance variables
    private SampleUI sampleUI;
    private EditSampleViewModel editSampleVM;
    private LoadViewModel loadVM;
    private IAudioSample selectedSample;

    // constructor
    public SampleController(SampleUI sampleUI, LoadViewModel loadVM) {
        this.sampleUI = sampleUI;
        
        // set sample details lisiteners
        SampleDetails sd = this.sampleUI.getTools().getSampleDetails();
        sd.addSampleNameFieldActionListener(e -> sampleNameOnChange());
        sd.addFileNameFieldActionListener(e -> fileNameOnChange());
        
        // set sound options listeners
        SampleSoundOptions so = this.sampleUI.getTools().getSoundOptions();
        so.addDefVolumeValChangeListener(e
                        -> defaultVolumeSpinnerOnChange());
        so.addDefVolumeSliderChangeListener(e
                        -> defaultVolumeSliderOnChange());
        so.addGlobVolumeValChangeListener(e
                        -> globalVolumeSpinnerOnChange());
        so.addGlobVolumeSliderChangeListener(e
                        -> globalVolumeSliderOnChange());
        so.addPaningActionListener(e
                        -> panOnChange());
        so.addDefPanValChangeListener(e
                        -> defaultPanningSpinnerOnChange());
        so.addDefPanSliderChangeListener(e
                        -> defaultPanningSliderOnChange());
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
        this.loadVM = loadVM;
        sampleOnChange();
    }

    // controller methods
    
    // sample details
    private void sampleNameOnChange() {
        
        selectedSample.setSampleName(sampleUI.getTools().getSampleDetails()
                .getSampleNameField().getText());
    }

    private void fileNameOnChange() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    // sound options
    public void defaultVolumeSliderOnChange() {
        int value = sampleUI.getTools().getSoundOptions()
                        .getDefaultVolumeSlider().getValue();
        sampleUI.getTools().getSoundOptions().getDefaultVolumeValue()
                .setValue(value);
        selectedSample.setDefaultVolume((short)value);
    }

    public void defaultVolumeSpinnerOnChange() {
        int value = (int)sampleUI.getTools().getSoundOptions()
                        .getDefaultVolumeValue().getValue();
        sampleUI.getTools().getSoundOptions().getDefaultVolumeSlider()
                .setValue(value);
        selectedSample.setDefaultVolume((short)value);
    }

    public void globalVolumeSliderOnChange() {
        int value = sampleUI.getTools().getSoundOptions()
                        .getGlobalVolumeSlider().getValue();
        sampleUI.getTools().getSoundOptions().getGlobalVolumeValue()
                .setValue(value);
        selectedSample.setGlobalVolume((byte)value);
    }

    public void globalVolumeSpinnerOnChange() {
        int value = (int) sampleUI.getTools().getSoundOptions()
                        .getGlobalVolumeValue().getValue();
        sampleUI.getTools().getSoundOptions().getGlobalVolumeSlider()
                .setValue(value);
        selectedSample.setGlobalVolume((byte)value);
    }

    public void panOnChange() {
        boolean isSelected = sampleUI.getTools().getSoundOptions().getPanning()
                        .isSelected();
        sampleUI.getTools().getSoundOptions().getDefaultPanningSlider()
                .setEnabled(isSelected);
        sampleUI.getTools().getSoundOptions().getDefaultPanningValue()
                .setEnabled(isSelected);
        selectedSample.setPanning(isSelected);
    }

    public void defaultPanningSliderOnChange() {
        int value = sampleUI.getTools().getSoundOptions()
                        .getDefaultPanningSlider().getValue();
        sampleUI.getTools().getSoundOptions().getDefaultPanningValue()
                .setValue(value);
        selectedSample.setPanValue((byte)value);
    }

    public void defaultPanningSpinnerOnChange() {
        int value = (int) sampleUI.getTools().getSoundOptions()
                        .getDefaultPanningValue().getValue();
        sampleUI.getTools().getSoundOptions().getDefaultPanningSlider()
                .setValue(value);
        selectedSample.setPanValue((byte)value);
    }

    public void sampleOnChange() {

        int modType = sampleUI.getTools().getModType();
        
        // loop code
        if ((int) sampleUI.getSampleSelectSpinner().getValue() == 
                loadVM.getSamples().size() + 1) {
            sampleUI.getSampleSelectSpinner().setValue(1);
        } else if ((int) sampleUI.getSampleSelectSpinner().getValue() == 0) {
            sampleUI.getSampleSelectSpinner().setValue(loadVM.getSamples()
                    .size());
        }

        // get sample
        sampleUI.setSelectedSample(
                (int) sampleUI.getSampleSelectSpinner().getValue() - 1);
        selectedSample = loadVM.getSamples()
                .get(sampleUI.getSelectedSample());

        // sample name
        sampleUI.getTools().getSampleDetails()
                .getSampleNameField().setText(selectedSample.getSampleName());

        // DOS file name
        if (modType == 2 || modType >= 4) {
            sampleUI.getTools().getSampleDetails()
                    .getFileNameField().setText(selectedSample.getDOSFileName());
        }

        // format
        String outputString;

        outputString = (selectedSample.isSigned()) ? "Signed " : "Unsigned ";

        outputString += selectedSample.getBitRate() + "-bit";

        sampleUI.getTools().getSampleDetails()
                .getSampleFormatLabel().setText(outputString);

        // channels
        outputString = (selectedSample.isStereo()) ? "Stereo" : "Mono";

        sampleUI.getTools().getSampleDetails()
                .getSampleChannelLabel().setText(outputString);

        // length
        outputString = Integer.toString(selectedSample.getSampleLength());

        sampleUI.getTools().getSampleDetails()
                .getSampleLengthLabel().setText(outputString);

        // default volume
        sampleUI.getTools().getSoundOptions()
                .getDefaultVolumeSlider().setValue(selectedSample.getDefaultVolume());

        defaultVolumeSliderOnChange();

        // global volume
        sampleUI.getTools().getSoundOptions()
                .getGlobalVolumeSlider().setValue(selectedSample.getGlobalVolume());

        globalVolumeSliderOnChange();

        // panning
        sampleUI.getTools().getSoundOptions()
                .getPanning().setSelected(selectedSample.isPanning());

        panOnChange();

        // default panning
        sampleUI.getTools().getSoundOptions()
                .getDefaultPanningSlider().setValue(selectedSample.getPanValue());

        defaultPanningSliderOnChange();

        // loop
        if (selectedSample.isLooped()) {

            sampleUI.getTools().getLoopingTools().getLoopComboBox()
                    .setSelectedIndex(1);
        } else if (selectedSample.isPingPongLooped()) {

            sampleUI.getTools().getLoopingTools().getLoopComboBox()
                    .setSelectedIndex(2);
        } else {

            sampleUI.getTools().getLoopingTools().getLoopComboBox()
                    .setSelectedIndex(0);
        }

        // loop start
        SpinnerNumberModel loopStartModel
                = new SpinnerNumberModel(selectedSample.getLoopBeginning(), 0,
                        selectedSample.getSampleLength(), 1);

        sampleUI.getTools().getLoopingTools().getLoopStartSpinner()
                .setModel(loopStartModel);

        // loop end
        SpinnerNumberModel loopEndModel
                = new SpinnerNumberModel(selectedSample.getLoopEnd(), 0,
                        selectedSample.getSampleLength(), 1);

        sampleUI.getTools().getLoopingTools().getLoopEndSpinner()
                .setModel(loopEndModel);

        // sustainloop
        if (selectedSample.isSustainLooped()) {

            sampleUI.getTools().getSusLoopTools().getSusLoopComboBox()
                    .setSelectedIndex(1);
        } else if (selectedSample.isPingPongLooped()) {

            sampleUI.getTools().getSusLoopTools().getSusLoopComboBox()
                    .setSelectedIndex(2);
        } else {

            sampleUI.getTools().getSusLoopTools().getSusLoopComboBox()
                    .setSelectedIndex(0);
        }

        // sustain loop start
        SpinnerNumberModel susLoopStartModel
                = new SpinnerNumberModel(selectedSample.getSustainLoopBeginning(), 0,
                        selectedSample.getSampleLength(), 1);

        sampleUI.getTools().getSusLoopTools().getSusLoopStartSpinner()
                .setModel(susLoopStartModel);

        // sustain loop end
        SpinnerNumberModel susLoopEndModel
                = new SpinnerNumberModel(selectedSample.getSustainLoopEnd(), 0,
                        selectedSample.getSampleLength(), 1);

        sampleUI.getTools().getSusLoopTools().getSusLoopEndSpinner()
                .setModel(susLoopEndModel);

        // Middle C speed
        sampleUI.getTools().getSamplingTools().getC5SampleRateSpinner()
                .setValue(selectedSample.getC5Speed());
        
        // vibratio speed
        sampleUI.getTools().getVibratoOptions().getVibSpeedSpinner()
                .setValue(selectedSample.getFullVibratoSpeed());
        
        // vibrato depth
        sampleUI.getTools().getVibratoOptions().getVibDepthSpinner()
                .setValue(selectedSample.getFullVibratoDepth());
        
        // vibrato delay
        sampleUI.getTools().getVibratoOptions().getVibDelaySpinner()
                .setValue(selectedSample.getVibratoDelay());
        
        // vibrato waveform
        sampleUI.getTools().getVibratoOptions().getVibWaveformComboBox()
                .setSelectedIndex(selectedSample.getVibratoWaveform());

        // sample data
        SwingUtilities.invokeLater(() -> {
            if (selectedSample.isStereo()) {

                // stereo samples
                sampleUI.getSampleWindow().getCanvas()
                        .setStereoSamples(selectedSample.getLData(), 
                                selectedSample.getRData());
            } else {

                // mono samples
                sampleUI.getSampleWindow().getCanvas()
                        .setMonoSamples(selectedSample.getLData());
            }
        });

    }

    public void loadSample(File file) {

        // lodad sample using loadVM
        loadVM.readSampleFile(file.getPath());
    }
}
