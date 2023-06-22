/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.controllers;

import javax.swing.JSpinner;
import javax.swing.SwingUtilities;
import module.IAudioSample;
import module.IInstrument;
import static sound.midi.MidiTables.MIDI_PROGRAM_LIST;
import ui.view.instruments.EnvelopeWindow;
import ui.view.instruments.FilterOptions;
import ui.view.instruments.InstrumentSoundOptions;
import ui.view.models.EditInstrumentViewModel;
import ui.view.models.LoadViewModel;
import ui.view.instruments.InstrumentUI;
import ui.view.instruments.MidiOptions;
import ui.view.instruments.RandomOffsets;
import ui.view.instruments.SustainOptions;

/**
 *
 * @author Edward Jenkins
 */
public class InstrumentController {

    // instance variables
    private InstrumentUI instrumentUI;
    private EditInstrumentViewModel editInstrumentVM;
    private LoadViewModel loadVM;
    private IInstrument selectedInstrument;

    // constructor
    public InstrumentController(InstrumentUI instrumentUI, LoadViewModel lvm) {
        this.instrumentUI = instrumentUI;
        instrumentUI.addInstrumentSelectSpinnerChangeListener(
                e -> instrumentOnChange());

        // set the sustain option actions
        SustainOptions so = instrumentUI.getTools().getSustainOptions();
        so.addFadeOutSpinnerChangeListener(e -> fadeOutOnChange());
        so.addNewNoteActionActionListener(e -> newNoteActionPerform());
        so.addDupNoteTypeActionListener(e -> dupNoteTypeActionPerform());
        so.addDupNoteActionActionListener(e -> dupNoteActionPerform());

        // set the midi actions
        MidiOptions mo = instrumentUI.getTools().getMidiOptions();
        mo.addMidiChannelChangeListener(e -> midiChannelOnChange());
        mo.addMidiInstrumentChangeListener(e -> midiProgramOnChange());
        mo.addMidiBankChangeListener(e -> midiBankOnChange());

        // set the sound actions
        InstrumentSoundOptions iso = instrumentUI.getTools().getSoundOptions();
        iso.addGlobVolumeValChangeListener(e -> globalVolumeSpinnerOnChange());
        iso.addGlobVolumeSliderChangeListener(e -> globalVolumeSliderOnChange());
        iso.addPaningActionListener(e -> panOnChange());
        iso.addDefPanValChangeListener(e -> defaultPanningSpinnerOnChange());
        iso.addDefPanSliderChangeListener(e -> defaultPanningSliderOnChange());
        iso.addPitchPanSpinnerChangeListener(e -> pitchPanOnChange());
        iso.addPitchCentreComboBoxItemListener(e -> pitchPanCentreOnChange());

        // set the filter actions
        FilterOptions fo = instrumentUI.getTools().getFilterOptions();
        fo.addFilterCutoffSpinnerChangeListener(e -> cutoffSpinnerOnChange());
        fo.addFilterCutoffSliderChangeListener(e -> cutoffSliderOnChange());
        fo.addFilterResSpinnerChangeListener(e -> resonanceSpinnerOnChange());
        fo.addFilterResSliderChangeListener(e -> resonanceSliderOnChange());

        // set the random actions
        RandomOffsets ro = instrumentUI.getTools().getRandomOffsets();
        ro.addRandomVolSpinnerChangeListener(e -> randomVolumeSpinnerOnChange());
        ro.addRandomVolSliderChangeListener(e -> randomVolumeSliderOnChange());
        ro.addRandomPanSpinnerChangeListener(e -> randomPanningSpinnerOnChange());
        ro.addRandomPanSliderChangeListener(e -> randomPanningSliderOnChange());

        // set the note map actions
        // set the envelope actions
        EnvelopeWindow ew = instrumentUI.getEnvelopeWindow();
        ew.addEnvelopeTypeComboBoxActionListener(e -> envelopeOnChange());

        this.loadVM = lvm;
        instrumentOnChange();
    }

    // sustain option events
    public void fadeOutOnChange() {
        selectedInstrument.setFadeOut((int) instrumentUI.getTools()
                .getSustainOptions().getFadeOutSpinner().getValue() / 32);
    }

    public void newNoteActionPerform() {
        selectedInstrument.setNewNoteAction((byte) instrumentUI.getTools()
                .getSustainOptions().getNewNoteActionComboBox()
                .getSelectedIndex());
    }

    public void dupNoteTypeActionPerform() {
        selectedInstrument.setDuplicateCheckType((byte) instrumentUI.getTools()
                .getSustainOptions().getDupNoteTypeComboBox()
                .getSelectedIndex());
    }

    public void dupNoteActionPerform() {
        selectedInstrument.setDuplicateCheckAction((byte) instrumentUI.getTools()
                .getSustainOptions().getDupNoteActionComboBox()
                .getSelectedIndex());
    }

    // midi opption events
    public void midiChannelOnChange() {
        selectedInstrument.setMidiChannel((short) ((int) instrumentUI.getTools()
                .getMidiOptions().getMidiChannelSpinner().getValue()));
    }

    public void midiProgramOnChange() {
        JSpinner instrumentSpinner = instrumentUI.getTools()
                .getMidiOptions().getMidiInstrumentSpinner();

        selectedInstrument.setMidiProgram((short) ((int) instrumentSpinner.getValue()));

        // update tool tip text to appropriate instrument
        instrumentSpinner.setToolTipText(
                MIDI_PROGRAM_LIST[(int) instrumentSpinner.getValue()]);
    }

    public void midiBankOnChange() {
        selectedInstrument.setMidiBank((short) ((int) instrumentUI.getTools()
                .getMidiOptions().getMidiBankSpinner().getValue()));
    }

    // sound options events
    public void globalVolumeSpinnerOnChange() {
        int value = (int) instrumentUI.getTools().getSoundOptions()
                .getGlobalVolumeValue().getValue();
        instrumentUI.getTools().getSoundOptions().getGlobalVolumeSlider()
                .setValue(value);
        selectedInstrument.setGlobalVolume((byte) value);
    }

    public void globalVolumeSliderOnChange() {
        int value = instrumentUI.getTools().getSoundOptions()
                .getGlobalVolumeSlider().getValue();
        instrumentUI.getTools().getSoundOptions().getGlobalVolumeValue()
                .setValue(value);
        selectedInstrument.setGlobalVolume((byte) value);
    }

    public void panOnChange() {
        boolean isSelected = instrumentUI.getTools().getSoundOptions().getPanning()
                .isSelected();
        instrumentUI.getTools().getSoundOptions().getDefaultPanningSlider()
                .setEnabled(isSelected);
        instrumentUI.getTools().getSoundOptions().getDefaultPanningValue()
                .setEnabled(isSelected);
        selectedInstrument.setPanning(isSelected);
    }

    public void defaultPanningSpinnerOnChange() {
        int value = (int) instrumentUI.getTools().getSoundOptions()
                .getDefaultPanningValue().getValue();
        instrumentUI.getTools().getSoundOptions().getDefaultPanningSlider()
                .setValue(value);
        selectedInstrument.setPanValue((byte) value);
    }

    public void defaultPanningSliderOnChange() {
        int value = instrumentUI.getTools().getSoundOptions()
                .getDefaultPanningSlider().getValue();
        instrumentUI.getTools().getSoundOptions().getDefaultPanningValue()
                .setValue(value);
        selectedInstrument.setPanValue((byte) value);
    }

    public void pitchPanOnChange() {
        int value = (int) instrumentUI.getTools().getSoundOptions()
                .getPitchPanSeparationSpinner().getValue();
        selectedInstrument.setPitchPanSeparation((byte) value);
    }

    public void pitchPanCentreOnChange() {
        byte value = (byte) instrumentUI.getTools().getSoundOptions()
                .getPitchPanCentreNoteComboBox().getSelectedIndex();
        selectedInstrument.setPitchPanCentre(value);
    }

    // filter events
    public void cutoffSpinnerOnChange() {
        int value = (int) instrumentUI.getTools().getFilterOptions()
                .getFilterCutoffSpinner().getValue();
        instrumentUI.getTools().getFilterOptions().getFilterCutoffSlider()
                .setValue(value);
        selectedInstrument.setInitialFilterCutoff((short) value);
    }

    public void cutoffSliderOnChange() {
        int value = instrumentUI.getTools().getFilterOptions()
                .getFilterCutoffSlider().getValue();
        instrumentUI.getTools().getFilterOptions().getFilterCutoffSpinner()
                .setValue(value);
        selectedInstrument.setInitialFilterCutoff((short) value);
    }

    public void resonanceSpinnerOnChange() {
        int value = (int) instrumentUI.getTools().getFilterOptions()
                .getFilterResonanceSpinner().getValue();
        instrumentUI.getTools().getFilterOptions().getFilterResonanceSlider()
                .setValue(value);
        selectedInstrument.setInitialFilterResonance((short) value);
    }

    public void resonanceSliderOnChange() {
        int value = instrumentUI.getTools().getFilterOptions()
                .getFilterResonanceSlider().getValue();
        instrumentUI.getTools().getFilterOptions().getFilterResonanceSpinner()
                .setValue(value);
        selectedInstrument.setInitialFilterResonance((short) value);
    }

    // random variation events
    public void randomVolumeSpinnerOnChange() {
        int value = (int) instrumentUI.getTools().getRandomOffsets()
                .getRandomVolumeSpinner().getValue();
        instrumentUI.getTools().getRandomOffsets().getRandomVolumeSlider()
                .setValue(value);
        selectedInstrument.setRandomVolumeVariation((byte) value);
    }

    public void randomVolumeSliderOnChange() {
        int value = instrumentUI.getTools().getRandomOffsets()
                .getRandomVolumeSlider().getValue();
        instrumentUI.getTools().getRandomOffsets().getRandomVolumeSpinner()
                .setValue(value);
        selectedInstrument.setRandomVolumeVariation((byte) value);
    }

    public void randomPanningSpinnerOnChange() {
        int value = (int) instrumentUI.getTools().getRandomOffsets()
                .getRandomPanningSpinner().getValue();
        instrumentUI.getTools().getRandomOffsets().getRandomPanningSlider()
                .setValue(value);
        selectedInstrument.setRandomPanningVariation((byte) value);
    }

    public void randomPanningSliderOnChange() {
        int value = instrumentUI.getTools().getRandomOffsets()
                .getRandomPanningSlider().getValue();
        instrumentUI.getTools().getRandomOffsets().getRandomPanningSpinner()
                .setValue(value);
        selectedInstrument.setRandomPanningVariation((byte) value);
    }

    public void envelopeOnChange() {
        byte envelopeType = (byte) instrumentUI.getEnvelopeWindow()
                .getEnvelopeTypeComboBox().getSelectedIndex();

        switch (envelopeType) {
            case 0:
                SwingUtilities.invokeLater(() -> {
                    instrumentUI.getEnvelopeWindow().getCanvas()
                            .setEnvelopeData(selectedInstrument
                                    .getVolumeEnvelopePoints(), 0, 64);
                });
                break;
            case 1:
                SwingUtilities.invokeLater(() -> {
                    instrumentUI.getEnvelopeWindow().getCanvas()
                            .setEnvelopeData(selectedInstrument
                                    .getPanEnvelopePoints(), -32, 32);
                });
                break;
            case 2:
                SwingUtilities.invokeLater(() -> {
                    instrumentUI.getEnvelopeWindow().getCanvas()
                            .setEnvelopeData(selectedInstrument
                                    .getPitchEnvelopePoints(), -32, 32);
                });
                break;
            default:
        }
    }

    public void instrumentOnChange() {

        int modType = instrumentUI.getTools().getModType();

        if ((int) instrumentUI.getInstrumentSelectSpinner().getValue()
                == loadVM.getInstruments().size() + 1) {
            instrumentUI.getInstrumentSelectSpinner().setValue(1);
        } else if ((int) instrumentUI.getInstrumentSelectSpinner()
                .getValue() == 0) {
            instrumentUI.getInstrumentSelectSpinner()
                    .setValue(loadVM.getInstruments().size());
        }

        // get instrument
        instrumentUI.setSelectedInstrument(
                (int) instrumentUI.getInstrumentSelectSpinner().getValue() - 1);
        selectedInstrument = loadVM.getInstruments().get(instrumentUI
                .getSelectedInstrument());

        // instrument name
        instrumentUI.getTools().getInstrumentDetails().getInstrumentNameField()
                .setText(selectedInstrument.getInstrumentName());

        // instrument DOS file name
        if (modType >= 4) {
            instrumentUI.getTools().getInstrumentDetails()
                    .getFileNameField().setText(selectedInstrument.getDosFileName());
        }

        // fade out
        instrumentUI.getTools().getSustainOptions().getFadeOutSpinner()
                .setValue(selectedInstrument.getFadeOut() * 32);

        // NNA
        instrumentUI.getTools().getSustainOptions().getNewNoteActionComboBox()
                .setSelectedIndex(selectedInstrument.getNewNoteAction());

        // duplicate note type
        instrumentUI.getTools().getSustainOptions().getDupNoteTypeComboBox()
                .setSelectedIndex(selectedInstrument.getDuplicateCheckType());

        // duplicate note action
        instrumentUI.getTools().getSustainOptions().getDupNoteActionComboBox()
                .setSelectedIndex(selectedInstrument.getDuplicateCheckAction());

        // MIDI channel
        instrumentUI.getTools().getMidiOptions().getMidiChannelSpinner()
                .setValue((int) selectedInstrument.getMidiChannel());

        // MIDI program
        JSpinner instrumentSpinner
                = instrumentUI.getTools().getMidiOptions()
                        .getMidiInstrumentSpinner();

        if (selectedInstrument.getMidiProgram() < 129) {
            instrumentSpinner.setValue((int) selectedInstrument.getMidiProgram());
        } else {
            instrumentSpinner.setValue(128);
        }

        // update tool tip text to appropriate instrument
        instrumentSpinner.setToolTipText(
                MIDI_PROGRAM_LIST[(int) instrumentSpinner.getValue()]);

        // MIDI bank
        instrumentUI.getTools().getMidiOptions().getMidiBankSpinner()
                .setValue((int) selectedInstrument.getMidiBank());

        // global volume
        instrumentUI.getTools().getSoundOptions().getGlobalVolumeSlider()
                .setValue(selectedInstrument.getGlobalVolume());

        instrumentUI.getTools().getSoundOptions().getGlobalVolumeValue()
                .setValue((int) selectedInstrument.getGlobalVolume());

        // default paning
        instrumentUI.getTools().getSoundOptions().getPanning()
                .setSelected(selectedInstrument.isPanning());

        panOnChange();

        // default pan value
        instrumentUI.getTools().getSoundOptions().getDefaultPanningSlider()
                .setValue(selectedInstrument.getPanValue());

        instrumentUI.getTools().getSoundOptions().getDefaultPanningValue()
                .setValue((int) selectedInstrument.getPanValue());

        // pitch pan separation
        instrumentUI.getTools().getSoundOptions().getPitchPanSeparationSpinner()
                .setValue((int) selectedInstrument.getPitchPanSeparation());

        // pitch pan centre
        instrumentUI.getTools().getSoundOptions()
                .getPitchPanCentreNoteComboBox().setSelectedIndex(
                        selectedInstrument.getPitchPanCentre()
                );

        // filter cutoff
        instrumentUI.getTools().getFilterOptions().getFilterCutoffSpinner()
                .setValue((int) selectedInstrument.getInitialFilterCutoff());

        instrumentUI.getTools().getFilterOptions().getFilterCutoffSlider()
                .setValue((int) selectedInstrument.getInitialFilterCutoff());

        // filter resoance
        instrumentUI.getTools().getFilterOptions().getFilterResonanceSpinner()
                .setValue((int) selectedInstrument.getInitialFilterResonance());

        instrumentUI.getTools().getFilterOptions().getFilterResonanceSlider()
                .setValue((int) selectedInstrument.getInitialFilterResonance());

        // random volume
        instrumentUI.getTools().getRandomOffsets().getRandomVolumeSpinner()
                .setValue((int) selectedInstrument.getRandomVolumeVariation());

        instrumentUI.getTools().getRandomOffsets().getRandomVolumeSlider()
                .setValue((int) selectedInstrument.getRandomVolumeVariation());

        // random panning
        instrumentUI.getTools().getRandomOffsets().getRandomPanningSpinner()
                .setValue((int) selectedInstrument.getRandomPanningVariation());

        instrumentUI.getTools().getRandomOffsets().getRandomPanningSlider()
                .setValue((int) selectedInstrument.getRandomPanningVariation());

        // note map
        instrumentUI.getTools().getNoteMapView()
                .setNoteMap(selectedInstrument.getNoteSampleKeyboardTable());

        instrumentUI.getTools().getNoteMapView().repaint();

        // combo box
        instrumentUI.getEnvelopeWindow()
                .getEnvelopeTypeComboBox().setSelectedIndex(0);
        
        // envelope data
        SwingUtilities.invokeLater(() -> {
            instrumentUI.getEnvelopeWindow().getCanvas()
                    .setEnvelopeData(selectedInstrument
                            .getVolumeEnvelopePoints(), 0, 64);
        });
    }
}
