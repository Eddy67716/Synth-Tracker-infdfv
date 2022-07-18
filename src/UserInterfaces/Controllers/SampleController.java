/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UserInterfaces.Controllers;

import UserInterfaces.Views.Samples.SampleTools;
import UserInterfaces.Views.Samples.SampleUI;
import UserInterfaces.Views.Samples.SampleWindow;
import UserInterfaces.viewModels.EditSampleViewModel;
import UserInterfaces.viewModels.LoadViewModel;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.io.File;
import java.util.List;
import javax.swing.SpinnerNumberModel;
import Module.IAudioSample;
import javax.swing.SwingUtilities;

/**
 *
 * @author Edward Jenkins
 */
public class SampleController {

    // instance variables
    public SampleUI sampleUI;
    public EditSampleViewModel editSampleVM;
    public LoadViewModel loadVM;

    // constructor
    public SampleController(SampleUI sampleUI, LoadViewModel loadVM) {
        this.sampleUI = sampleUI;
        this.sampleUI.getTools()
                .addDefVolumeValChangeListener(e
                        -> defaultVolumeSpinnerOnChange());
        this.sampleUI.getTools()
                .addDefVolumeSliderChangeListener(e
                        -> defaultVolumeSliderOnChange());
        this.sampleUI.getTools()
                .addGlobVolumeValChangeListener(e
                        -> globalVolumeSpinnerOnChange());
        this.sampleUI.getTools()
                .addGlobVolumeSliderChangeListener(e
                        -> globalVolumeSliderOnChange());
        this.sampleUI.getTools()
                .addPaningActionListener(e
                        -> panOnChange());
        this.sampleUI.getTools()
                .addDefPanValChangeListener(e
                        -> defaultPanningSpinnerOnChange());
        this.sampleUI.getTools()
                .addDefPanSliderChangeListener(e
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
    public void defaultVolumeSliderOnChange() {
        sampleUI.getTools().getDefaultVolumeValue()
                .setValue(sampleUI.getTools().getDefaultVolumeSlider()
                        .getValue());
    }

    public void defaultVolumeSpinnerOnChange() {
        sampleUI.getTools().getDefaultVolumeSlider()
                .setValue((int) sampleUI.getTools().getDefaultVolumeValue()
                        .getValue());
    }

    public void globalVolumeSliderOnChange() {
        sampleUI.getTools().getGlobalVolumeValue()
                .setValue(sampleUI.getTools().getGlobalVolumeSlider()
                        .getValue());
    }

    public void globalVolumeSpinnerOnChange() {
        sampleUI.getTools().getGlobalVolumeSlider()
                .setValue((int) sampleUI.getTools().getGlobalVolumeValue()
                        .getValue());
    }

    public void panOnChange() {
        sampleUI.getTools().getDefaultPanningSlider()
                .setEnabled(sampleUI.getTools().getPanning().isSelected());
        sampleUI.getTools().getDefaultPanningValue()
                .setEnabled(sampleUI.getTools().getPanning().isSelected());
    }

    public void defaultPanningSliderOnChange() {
        sampleUI.getTools().getDefaultPanningValue()
                .setValue(sampleUI.getTools().getDefaultPanningSlider()
                        .getValue());
    }

    public void defaultPanningSpinnerOnChange() {
        sampleUI.getTools().getDefaultPanningSlider()
                .setValue((int) sampleUI.getTools().getDefaultPanningValue()
                        .getValue());
    }

    public void sampleOnChange() {

        int modType = sampleUI.getTools().getModType();

        // get sample
        sampleUI.setSelectedSample(
                (int) sampleUI.getSampleSelectSpinner().getValue());
        IAudioSample sample = loadVM.getSamples().get(sampleUI.getSelectedSample());

        // sample name
        sampleUI.getTools()
                .getSampleNameField().setText(sample.getSampleName());

        // DOS file name
        if (modType == 2 || modType >= 4) {
            sampleUI.getTools()
                    .getFileNameField().setText(sample.getDOSFileName());
        }

        // format
        String outputString;

        outputString = (sample.isSigned()) ? "Signed " : "Unsigned ";

        outputString += sample.getBitRate() + "-bit";

        sampleUI.getTools()
                .getSampleFormatLabel().setText(outputString);

        // channels
        outputString = (sample.isStereo()) ? "Stereo" : "Mono";

        sampleUI.getTools()
                .getSampleChannelLabel().setText(outputString);

        // length
        outputString = Integer.toString(sample.getSampleLength());

        sampleUI.getTools()
                .getSampleLengthLabel().setText(outputString);

        // default volume
        sampleUI.getTools()
                .getDefaultVolumeSlider().setValue(sample.getDefaultVolume());

        defaultVolumeSliderOnChange();

        // global volume
        sampleUI.getTools()
                .getGlobalVolumeSlider().setValue(sample.getGlobalVolume());

        globalVolumeSliderOnChange();

        // panning
        sampleUI.getTools()
                .getPanning().setSelected(sample.isPanning());

        panOnChange();

        // default panning
        sampleUI.getTools()
                .getDefaultPanningSlider().setValue(sample.getPanValue());

        defaultPanningSliderOnChange();

        // loop
        if (sample.isLooped()) {

            sampleUI.getTools().getLoopComboBox().setSelectedIndex(1);
        } else if (sample.isPingPongLooped()) {

            sampleUI.getTools().getLoopComboBox().setSelectedIndex(2);
        } else {

            sampleUI.getTools().getLoopComboBox().setSelectedIndex(0);
        }

        // loop start
        SpinnerNumberModel loopStartModel
                = new SpinnerNumberModel(sample.getLoopBeginning(), 0,
                        sample.getSampleLength(), 1);

        sampleUI.getTools().getLoopStartSpinner().setModel(loopStartModel);

        // loop end
        SpinnerNumberModel loopEndModel
                = new SpinnerNumberModel(sample.getLoopEnd(), 0,
                        sample.getSampleLength(), 1);

        sampleUI.getTools().getLoopEndSpinner().setModel(loopEndModel);

        // sustainloop
        if (sample.isSustainLooped()) {

            sampleUI.getTools().getSusLoopComboBox().setSelectedIndex(1);
        } else if (sample.isPingPongLooped()) {

            sampleUI.getTools().getSusLoopComboBox().setSelectedIndex(2);
        } else {

            sampleUI.getTools().getSusLoopComboBox().setSelectedIndex(0);
        }

        // sustain loop start
        SpinnerNumberModel susLoopStartModel
                = new SpinnerNumberModel(sample.getSustainLoopBeginning(), 0,
                        sample.getSampleLength(), 1);

        sampleUI.getTools().getSusLoopStartSpinner()
                .setModel(susLoopStartModel);

        // sustain loop end
        SpinnerNumberModel susLoopEndModel
                = new SpinnerNumberModel(sample.getSustainLoopEnd(), 0,
                        sample.getSampleLength(), 1);

        sampleUI.getTools().getSusLoopEndSpinner()
                .setModel(susLoopEndModel);

        // Middle C speed
        sampleUI.getTools().getC5SampleRateSpinner()
                .setValue(sample.getC5Speed());

        // sample data
        SwingUtilities.invokeLater(() -> {
            if (sample.isStereo()) {

                // stereo samples
                sampleUI.getSampleWindow().getCanvas()
                        .setStereoSamples(sample.getLData(), sample.getRData());
            } else {

                // mono samples
                sampleUI.getSampleWindow().getCanvas()
                        .setMonoSamples(sample.getLData());
            }
        });

    }

    public void loadSample(File file) {

        // lodad sample using loadVM
        loadVM.readSampleFile(file.getPath());
    }
}
