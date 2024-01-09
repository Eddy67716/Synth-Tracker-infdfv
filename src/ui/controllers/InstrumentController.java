/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.controllers;

import java.awt.Toolkit;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.SwingUtilities;
import javax.swing.undo.UndoManager;
import module.IAudioSample;
import module.IInstrument;
import static sound.midi.MidiTables.MIDI_PROGRAM_LIST;
import ui.controllers.undo.UndoableCheckBoxChange;
import ui.controllers.undo.UndoableComboBoxChange;
import ui.controllers.undo.UndoableSliderChange;
import ui.controllers.undo.UndoableSpinnerChange;
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
public class InstrumentController extends GenericController {

    // instance variables
    private InstrumentUI instrumentUI;
    private EditInstrumentViewModel editInstrumentVM;
    private LoadViewModel loadVM;
    private IInstrument selectedInstrument;
    private UndoManager[] instrumentManagers;
    private int fadeoutOldValue;
    private int nnaComboBoxOldValue;
    private int dntComboBoxOldValue;
    private int dnaComboBoxOldValue;
    private int globalVolOldValue;
    private int panOldValue;
    private int pitchPanSeperationOldValue;
    private int pitchPanCentreOldValue;
    private int randomVolumeOldValue;
    private int randomPanOldValue;
    private int randomCutoffOldValue;
    private int randomRezOldValue;
    private int filterCutoffOldValue;
    private int filterRezOldValue;
    private int midiProgramOldValue;
    private int midiChannelOldValue;
    private int midiBankOldValue;

    // constructor
    public InstrumentController(InstrumentUI instrumentUI, LoadViewModel lvm) {
        super();
        this.instrumentUI = instrumentUI;
        this.loadVM = lvm;

        // initialise undo managers
        instrumentManagers = new UndoManager[loadVM.getInstruments().size()];
        for (int i = 0; i < instrumentManagers.length; i++) {
            instrumentManagers[i] = new UndoManager();
        }

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

        instrumentOnChange();
    }

    // sustain option events
    public void fadeOutOnChange() {

        JSpinner fadeOutSpinner = instrumentUI.getTools().getSustainOptions()
                .getFadeOutSpinner();

        // commit edit
        try {

            // makes sure it's a number divided by 32
            if ((int) fadeOutSpinner.getValue() % 32 != 0) {
                int modulus = (int) fadeOutSpinner.getValue() % 32;
                int currentValue = (int) fadeOutSpinner.getValue();
                // round to the closes number
                if (modulus > 16) {
                    fadeOutSpinner.setValue(currentValue - modulus + 32);
                } else {
                    fadeOutSpinner.setValue(currentValue - modulus);
                }
                // return because rest of code has been run in a recursion
                return;
            }

            fadeOutSpinner.commitEdit();

            int storeValue = (int) fadeOutSpinner.getValue() / 32;

            if (isRecordingUndos()) {
                // undo event
                UndoableSpinnerChange spinnerChange
                        = new UndoableSpinnerChange(fadeOutSpinner,
                                fadeoutOldValue);

                // append event to manager
                getCurrentUndoManager().addEdit(spinnerChange);
            }

            if (isAlteringModels()) {

                // update instrument fade value
                selectedInstrument.setFadeOut(storeValue);
            }

            fadeoutOldValue = (int) fadeOutSpinner.getValue();

        } catch (java.text.ParseException e) {

            // play an error sound
            playErrorSound();

            fadeOutSpinner.setValue(fadeoutOldValue);
        }
    }

    public void newNoteActionPerform() {

        JComboBox newNoteActionComboBox = instrumentUI.getTools()
                .getSustainOptions().getNewNoteActionComboBox();

        if (isRecordingUndos()) {
            // undo event
            UndoableComboBoxChange comboBoxChange
                    = new UndoableComboBoxChange(newNoteActionComboBox,
                            nnaComboBoxOldValue);

            // append event to manager
            getCurrentUndoManager().addEdit(comboBoxChange);
        }

        if (isAlteringModels()) {

            // alter instrument
            selectedInstrument.setNewNoteAction((byte) newNoteActionComboBox
                    .getSelectedIndex());
        }

        nnaComboBoxOldValue = newNoteActionComboBox.getSelectedIndex();
    }

    public void dupNoteTypeActionPerform() {
        JComboBox dupNoteTypeComboBox = instrumentUI.getTools()
                .getSustainOptions().getDupNoteTypeComboBox();

        if (isRecordingUndos()) {
            // undo event
            UndoableComboBoxChange comboBoxChange
                    = new UndoableComboBoxChange(dupNoteTypeComboBox,
                            dntComboBoxOldValue);

            // append event to manager
            getCurrentUndoManager().addEdit(comboBoxChange);
        }

        if (isAlteringModels()) {

            // alter instrument
            selectedInstrument.setDuplicateCheckType(
                    (byte) dupNoteTypeComboBox.getSelectedIndex());
        }

        dntComboBoxOldValue = dupNoteTypeComboBox.getSelectedIndex();
    }

    public void dupNoteActionPerform() {
        JComboBox dupNoteActionComboBox = instrumentUI.getTools()
                .getSustainOptions().getDupNoteTypeComboBox();

        if (isRecordingUndos()) {
            // undo event
            UndoableComboBoxChange comboBoxChange
                    = new UndoableComboBoxChange(dupNoteActionComboBox,
                            dnaComboBoxOldValue);

            // append event to manager
            getCurrentUndoManager().addEdit(comboBoxChange);
        }

        if (isAlteringModels()) {

            // alter instrument
            selectedInstrument.setDuplicateCheckAction(
                    (byte) dupNoteActionComboBox.getSelectedIndex());
        }

        dnaComboBoxOldValue = dupNoteActionComboBox.getSelectedIndex();
    }

    // midi option events
    public void midiChannelOnChange() {

        JSpinner midiChannelSpinner = instrumentUI.getTools()
                .getMidiOptions().getMidiChannelSpinner();

        int value = (int) midiChannelSpinner.getValue();

        if (isRecordingUndos()) {
            // undo event
            UndoableSpinnerChange spinnerChange
                    = new UndoableSpinnerChange(midiChannelSpinner,
                            midiChannelOldValue);

            // append event to manager
            getCurrentUndoManager().addEdit(spinnerChange);
        }

        if (isAlteringModels()) {

            // update instrument global volume value
            selectedInstrument.setMidiChannel((short) value);
        }

        midiChannelOldValue = value;
    }

    public void midiProgramOnChange() {
        JSpinner instrumentSpinner = instrumentUI.getTools()
                .getMidiOptions().getMidiInstrumentSpinner();

        int value = (int) instrumentSpinner.getValue();

        if (isRecordingUndos()) {
            // undo event
            UndoableSpinnerChange spinnerChange
                    = new UndoableSpinnerChange(instrumentSpinner,
                            midiProgramOldValue);

            // append event to manager
            getCurrentUndoManager().addEdit(spinnerChange);
        }

        if (isAlteringModels()) {

            // update instrument global volume value
            selectedInstrument.setMidiProgram((byte) ((int) instrumentSpinner.getValue()));
        }

        midiProgramOldValue = value;

        // update tool tip text to appropriate instrument
        instrumentSpinner.setToolTipText(
                MIDI_PROGRAM_LIST[(int) instrumentSpinner.getValue()]);
    }

    public void midiBankOnChange() {
        JSpinner midiBankSpinner = instrumentUI.getTools()
                .getMidiOptions().getMidiBankSpinner();

        int value = (int) midiBankSpinner.getValue();

        if (isRecordingUndos()) {
            // undo event
            UndoableSpinnerChange spinnerChange
                    = new UndoableSpinnerChange(midiBankSpinner,
                            midiBankOldValue);

            // append event to manager
            getCurrentUndoManager().addEdit(spinnerChange);
        }

        if (isAlteringModels()) {

            // update instrument global volume value
            selectedInstrument.setMidiBank((short) value);
        }

        midiBankOldValue = value;
    }

    // sound options events
    public void globalVolumeSpinnerOnChange() {

        JSpinner globalVolumeSpinner = instrumentUI.getTools().getSoundOptions()
                .getGlobalVolumeValue();

        int value = (int) globalVolumeSpinner.getValue();

        // alter the slider linked to this spinner
        boolean recordUndoState = isRecordingUndos();
        setRecordingUndos(false);
        instrumentUI.getTools().getSoundOptions().getGlobalVolumeSlider()
                .setValue(value);
        setRecordingUndos(recordUndoState);

        if (isRecordingUndos()) {
            // undo event
            UndoableSpinnerChange spinnerChange
                    = new UndoableSpinnerChange(globalVolumeSpinner,
                            globalVolOldValue);

            getCurrentUndoManager().addEdit(spinnerChange);

            globalVolOldValue = value;
        }

        if (isAlteringModels()) {

            // update instrument global volume value
            selectedInstrument.setGlobalVolume((byte) value);
        }
    }

    public void globalVolumeSliderOnChange() {

        JSlider globalVolumeSlider = instrumentUI.getTools().getSoundOptions()
                .getGlobalVolumeSlider();

        int value = globalVolumeSlider.getValue();

        // alter the spinner linked to this slider
        boolean recordUndoState = isRecordingUndos();
        setRecordingUndos(false);
        instrumentUI.getTools().getSoundOptions().getGlobalVolumeValue()
                .setValue(value);
        setRecordingUndos(recordUndoState);

        if (!globalVolumeSlider.getValueIsAdjusting()) {

            if (isRecordingUndos()) {
                // undo event
                UndoableSliderChange sliderChange
                        = new UndoableSliderChange(globalVolumeSlider,
                                globalVolOldValue);

                getCurrentUndoManager().addEdit(sliderChange);

                globalVolOldValue = value;
            }

            if (isAlteringModels()) {

                // update instrument global volume value
                selectedInstrument.setGlobalVolume((byte) value);
            }
        }
    }

    public void panOnChange() {

        JCheckBox panning = instrumentUI.getTools().getSoundOptions()
                .getPanning();

        boolean isSelected = panning.isSelected();

        instrumentUI.getTools().getSoundOptions().getDefaultPanningSlider()
                .setEnabled(isSelected);
        instrumentUI.getTools().getSoundOptions().getDefaultPanningValue()
                .setEnabled(isSelected);

        if (isRecordingUndos()) {
            // undo event
            UndoableCheckBoxChange checkBoxChange
                    = new UndoableCheckBoxChange(panning);

            getCurrentUndoManager().addEdit(checkBoxChange);
        }

        if (isAlteringModels()) {

            // update instrument panning value
            selectedInstrument.setPanning(isSelected);
        }
    }

    public void defaultPanningSpinnerOnChange() {

        JSpinner defaultPanningSpinner = instrumentUI.getTools().getSoundOptions()
                .getDefaultPanningValue();

        int value = (int) defaultPanningSpinner.getValue();

        // alter the slider linked to this spinner
        boolean recordUndoState = isRecordingUndos();
        setRecordingUndos(false);
        instrumentUI.getTools().getSoundOptions().getDefaultPanningSlider()
                .setValue(value);
        setRecordingUndos(recordUndoState);

        if (isRecordingUndos()) {
            // undo event
            UndoableSpinnerChange spinnerChange
                    = new UndoableSpinnerChange(defaultPanningSpinner,
                            panOldValue);

            getCurrentUndoManager().addEdit(spinnerChange);

            panOldValue = value;
        }

        if (isAlteringModels()) {

            // update instrument global volume value
            selectedInstrument.setPanValue((byte) value);
        }
    }

    public void defaultPanningSliderOnChange() {
        JSlider defaultPanningSlider = instrumentUI.getTools().getSoundOptions()
                .getDefaultPanningSlider();

        int value = defaultPanningSlider.getValue();

        // alter the spinner linked to this slider
        boolean recordUndoState = isRecordingUndos();
        setRecordingUndos(false);
        instrumentUI.getTools().getSoundOptions().getDefaultPanningValue()
                .setValue(value);
        setRecordingUndos(recordUndoState);

        if (!defaultPanningSlider.getValueIsAdjusting()) {

            if (isRecordingUndos()) {
                // undo event
                UndoableSliderChange sliderChange
                        = new UndoableSliderChange(defaultPanningSlider,
                                panOldValue);

                getCurrentUndoManager().addEdit(sliderChange);

                panOldValue = value;
            }

            if (isAlteringModels()) {

                // update instrument global volume value
                selectedInstrument.setPanValue((byte) value);
            }
        }
    }

    public void pitchPanOnChange() {

        JSpinner pitchPanSpinner = instrumentUI.getTools().getSoundOptions()
                .getPitchPanSeparationSpinner();

        int value = (int) pitchPanSpinner.getValue();

        if (isRecordingUndos()) {
            // undo event
            UndoableSpinnerChange spinnerChange
                    = new UndoableSpinnerChange(pitchPanSpinner,
                            pitchPanSeperationOldValue);

            getCurrentUndoManager().addEdit(spinnerChange);

            pitchPanSeperationOldValue = value;
        }

        if (isAlteringModels()) {

            // update instrument global volume value
            selectedInstrument.setPitchPanSeparation((byte) value);
        }

        selectedInstrument.setPitchPanSeparation((byte) value);
    }

    public void pitchPanCentreOnChange() {

        JComboBox pitchPanCentreComboBox = instrumentUI.getTools()
                .getSoundOptions().getPitchPanCentreNoteComboBox();

        if (isRecordingUndos()) {
            // undo event
            UndoableComboBoxChange comboBoxChange
                    = new UndoableComboBoxChange(pitchPanCentreComboBox,
                            pitchPanCentreOldValue);

            // append event to manager
            getCurrentUndoManager().addEdit(comboBoxChange);

            // update old value to current one
            dnaComboBoxOldValue = pitchPanCentreComboBox.getSelectedIndex();
        }

        if (isAlteringModels()) {

            // alter instrument
            selectedInstrument.setPitchPanCentre((byte) pitchPanCentreComboBox
                    .getSelectedIndex());
        }
    }

    // filter events
    public void cutoffSpinnerOnChange() {

        JSpinner cutoffSpinner = instrumentUI.getTools().getFilterOptions()
                .getFilterCutoffSpinner();

        int value = (int) cutoffSpinner.getValue();

        // alter the slider linked to this spinner
        boolean recordUndoState = isRecordingUndos();
        setRecordingUndos(false);
        instrumentUI.getTools().getFilterOptions().getFilterCutoffSlider()
                .setValue(value);
        setRecordingUndos(recordUndoState);

        if (isRecordingUndos()) {
            // undo event
            UndoableSpinnerChange spinnerChange
                    = new UndoableSpinnerChange(cutoffSpinner,
                            filterCutoffOldValue);

            getCurrentUndoManager().addEdit(spinnerChange);

            filterCutoffOldValue = value;
        }

        if (isAlteringModels()) {

            // update instrument global volume value
            selectedInstrument.setInitialFilterCutoff((short) value);
        }
    }

    public void cutoffSliderOnChange() {

        JSlider cutoffSlider = instrumentUI.getTools()
                .getFilterOptions().getFilterCutoffSlider();

        int value = cutoffSlider.getValue();

        // alter the spinner linked to this slider
        boolean recordUndoState = isRecordingUndos();
        setRecordingUndos(false);
        instrumentUI.getTools().getFilterOptions().getFilterCutoffSpinner()
                .setValue(value);
        setRecordingUndos(recordUndoState);

        if (!cutoffSlider.getValueIsAdjusting()) {

            if (isRecordingUndos()) {
                // undo event
                UndoableSliderChange sliderChange
                        = new UndoableSliderChange(cutoffSlider,
                                filterCutoffOldValue);

                getCurrentUndoManager().addEdit(sliderChange);

                filterCutoffOldValue = value;
            }

            if (isAlteringModels()) {

                // update instrument initial filter cutoff value
                selectedInstrument.setInitialFilterCutoff((short) value);
            }
        }
    }

    public void resonanceSpinnerOnChange() {

        JSpinner rezSpinner = instrumentUI.getTools().getFilterOptions()
                .getFilterResonanceSpinner();

        int value = (int) rezSpinner.getValue();

        // alter the slider linked to this spinner
        boolean recordUndoState = isRecordingUndos();
        setRecordingUndos(false);
        instrumentUI.getTools().getFilterOptions().getFilterResonanceSlider()
                .setValue(value);
        setRecordingUndos(recordUndoState);

        if (isRecordingUndos()) {
            // undo event
            UndoableSpinnerChange spinnerChange
                    = new UndoableSpinnerChange(rezSpinner,
                            filterRezOldValue);

            getCurrentUndoManager().addEdit(spinnerChange);

            filterRezOldValue = value;
        }

        if (isAlteringModels()) {

            // update instrument initial filter resonance value
            selectedInstrument.setInitialFilterResonance((short) value);
        }
    }

    public void resonanceSliderOnChange() {

        JSlider rezSlider = instrumentUI.getTools().getFilterOptions()
                .getFilterResonanceSlider();

        int value = rezSlider.getValue();

        // alter the spinner linked to this slider
        boolean recordUndoState = isRecordingUndos();
        setRecordingUndos(false);
        instrumentUI.getTools().getFilterOptions().getFilterResonanceSpinner()
                .setValue(value);
        setRecordingUndos(recordUndoState);

        if (!rezSlider.getValueIsAdjusting()) {

            if (isRecordingUndos()) {
                // undo event
                UndoableSliderChange sliderChange
                        = new UndoableSliderChange(rezSlider,
                                filterRezOldValue);

                getCurrentUndoManager().addEdit(sliderChange);

                filterRezOldValue = value;
            }

            if (isAlteringModels()) {

                // update instrument initial filter resonance value
                selectedInstrument.setInitialFilterResonance((short) value);
            }
        }
    }

    // random variation events
    public void randomVolumeSpinnerOnChange() {

        JSpinner randomVolSpinner = instrumentUI.getTools().getRandomOffsets()
                .getRandomVolumeSpinner();

        int value = (int) randomVolSpinner.getValue();

        // alter the slider linked to this spinner
        boolean recordUndoState = isRecordingUndos();
        setRecordingUndos(false);
        instrumentUI.getTools().getRandomOffsets().getRandomVolumeSlider()
                .setValue(value);
        setRecordingUndos(recordUndoState);

        if (isRecordingUndos()) {
            // undo event
            UndoableSpinnerChange spinnerChange
                    = new UndoableSpinnerChange(randomVolSpinner,
                            randomVolumeOldValue);

            getCurrentUndoManager().addEdit(spinnerChange);

            randomVolumeOldValue = value;
        }

        if (isAlteringModels()) {

            // update instrument random volume value
            selectedInstrument.setRandomVolumeVariation((byte) value);
        }
    }

    public void randomVolumeSliderOnChange() {

        JSlider randomVolSlider = instrumentUI.getTools().getRandomOffsets()
                .getRandomVolumeSlider();

        int value = randomVolSlider.getValue();

        // alter the spinner linked to this slider
        boolean recordUndoState = isRecordingUndos();
        setRecordingUndos(false);
        instrumentUI.getTools().getRandomOffsets().getRandomVolumeSpinner()
                .setValue(value);
        setRecordingUndos(recordUndoState);

        if (!randomVolSlider.getValueIsAdjusting()) {

            if (isRecordingUndos()) {
                // undo event
                UndoableSliderChange sliderChange
                        = new UndoableSliderChange(randomVolSlider,
                                randomVolumeOldValue);

                getCurrentUndoManager().addEdit(sliderChange);

                randomVolumeOldValue = value;
            }

            if (isAlteringModels()) {

                // update instrument random volume value
                selectedInstrument.setRandomVolumeVariation((byte) value);
            }
        }
    }

    public void randomPanningSpinnerOnChange() {

        JSpinner randomPanSpinner = instrumentUI.getTools().getRandomOffsets()
                .getRandomPanningSpinner();

        int value = (int) randomPanSpinner.getValue();

        // alter the slider linked to this spinner
        boolean recordUndoState = isRecordingUndos();
        setRecordingUndos(false);
        instrumentUI.getTools().getRandomOffsets().getRandomPanningSlider()
                .setValue(value);
        setRecordingUndos(recordUndoState);

        if (isRecordingUndos()) {
            // undo event
            UndoableSpinnerChange spinnerChange
                    = new UndoableSpinnerChange(randomPanSpinner,
                            randomPanOldValue);

            getCurrentUndoManager().addEdit(spinnerChange);

            randomPanOldValue = value;
        }

        if (isAlteringModels()) {

            // update instrument random panning value
            selectedInstrument.setRandomPanningVariation((byte) value);
        }
    }

    public void randomPanningSliderOnChange() {
        JSlider randomPanSlider = instrumentUI.getTools().getRandomOffsets()
                .getRandomPanningSlider();

        int value = randomPanSlider.getValue();

        // alter the spinner linked to this slider
        boolean recordUndoState = isRecordingUndos();
        setRecordingUndos(false);
        instrumentUI.getTools().getRandomOffsets().getRandomPanningSpinner()
                .setValue(value);
        setRecordingUndos(recordUndoState);

        if (!randomPanSlider.getValueIsAdjusting()) {

            if (isRecordingUndos()) {
                // undo event
                UndoableSliderChange sliderChange
                        = new UndoableSliderChange(randomPanSlider,
                                randomPanOldValue);

                getCurrentUndoManager().addEdit(sliderChange);

                randomPanOldValue = value;
            }

            if (isAlteringModels()) {

                // update instrument random pan value
                selectedInstrument.setRandomPanningVariation((byte) value);
            }
        }
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

        // set recording undos and alterations to false for an instrument change
        setRecordingUndos(false);
        setAlteringModels(false);

        if ((int) instrumentUI.getInstrumentSelectSpinner().getValue()
                == loadVM.getInstruments().size() + 1) {
            instrumentUI.getInstrumentSelectSpinner().setValue(1);
        } else if ((int) instrumentUI.getInstrumentSelectSpinner()
                .getValue() == 0) {
            instrumentUI.getInstrumentSelectSpinner()
                    .setValue(loadVM.getInstruments().size());
        }

        // get selected instrument value
        int value = (int) instrumentUI.getInstrumentSelectSpinner().getValue()
                - 1;

        // get instrument
        instrumentUI.setSelectedInstrument(value);
        selectedInstrument = loadVM.getInstruments().get(instrumentUI
                .getSelectedInstrument());

        // set current undoManager
        setCurrentUndoManager(instrumentManagers[value]);

        // instrument name
        instrumentUI.getTools().getInstrumentDetails().getInstrumentNameField()
                .setText(selectedInstrument.getInstrumentName());

        // instrument DOS file name
        if (modType >= 4) {
            instrumentUI.getTools().getInstrumentDetails()
                    .getFileNameField().setText(selectedInstrument.getDosFileName());
        }

        // set old value (needed in case of undo)
        fadeoutOldValue = selectedInstrument.getFadeOut() * 32;

        // fade out
        instrumentUI.getTools().getSustainOptions().getFadeOutSpinner()
                .setValue(fadeoutOldValue);

        // set old value for combo box
        nnaComboBoxOldValue = selectedInstrument.getNewNoteAction();

        // NNA
        instrumentUI.getTools().getSustainOptions().getNewNoteActionComboBox()
                .setSelectedIndex(nnaComboBoxOldValue);

        // set old value for combo box
        dntComboBoxOldValue = selectedInstrument.getNewNoteAction();

        // duplicate note type
        instrumentUI.getTools().getSustainOptions().getDupNoteTypeComboBox()
                .setSelectedIndex(dntComboBoxOldValue);

        // set old value for combo box
        dnaComboBoxOldValue = selectedInstrument.getNewNoteAction();

        // duplicate note action
        instrumentUI.getTools().getSustainOptions().getDupNoteActionComboBox()
                .setSelectedIndex(dnaComboBoxOldValue);

        // set old value (needed in case of undo)
        midiChannelOldValue = selectedInstrument.getMidiChannel();

        // MIDI channel
        instrumentUI.getTools().getMidiOptions().getMidiChannelSpinner()
                .setValue(midiChannelOldValue);

        // MIDI program
        JSpinner instrumentSpinner
                = instrumentUI.getTools().getMidiOptions()
                        .getMidiInstrumentSpinner();

        if (selectedInstrument.getMidiProgram() < 129) {
            instrumentSpinner.setValue((int) selectedInstrument.getMidiProgram());
        } else {
            instrumentSpinner.setValue(128);
        }

        // set old value (needed in case of undo)
        midiProgramOldValue = (int) instrumentSpinner.getValue();

        // update tool tip text to appropriate instrument
        instrumentSpinner.setToolTipText(
                MIDI_PROGRAM_LIST[(int) instrumentSpinner.getValue()]);

        // MIDI bank
        // set old value (needed in case of undo)
        midiBankOldValue = selectedInstrument.getMidiBank();

        instrumentUI.getTools().getMidiOptions().getMidiBankSpinner()
                .setValue(midiBankOldValue);

        // global volume
        // set old value (needed in case of undo)
        globalVolOldValue = selectedInstrument.getGlobalVolume();

        instrumentUI.getTools().getSoundOptions().getGlobalVolumeSlider()
                .setValue(globalVolOldValue);

        instrumentUI.getTools().getSoundOptions().getGlobalVolumeValue()
                .setValue(globalVolOldValue);

        // default paning
        instrumentUI.getTools().getSoundOptions().getPanning()
                .setSelected(selectedInstrument.isPanning());

        panOnChange();

        // default pan value
        // set old value (needed in case of undo)
        panOldValue = selectedInstrument.getPanValue();

        instrumentUI.getTools().getSoundOptions().getDefaultPanningSlider()
                .setValue(panOldValue);

        instrumentUI.getTools().getSoundOptions().getDefaultPanningValue()
                .setValue(panOldValue);

        // set old value (needed in case of undo)
        pitchPanSeperationOldValue = selectedInstrument.getPitchPanSeparation();

        // pitch pan separation
        instrumentUI.getTools().getSoundOptions().getPitchPanSeparationSpinner()
                .setValue(pitchPanSeperationOldValue);

        // pitch pan centre
        // set old value (needed in case of undo)
        pitchPanCentreOldValue = selectedInstrument.getPitchPanCentre();

        instrumentUI.getTools().getSoundOptions()
                .getPitchPanCentreNoteComboBox()
                .setSelectedIndex(pitchPanCentreOldValue);

        // filter cutoff
        // set old value (needed in case of undo)
        filterCutoffOldValue = selectedInstrument.getInitialFilterCutoff();

        instrumentUI.getTools().getFilterOptions().getFilterCutoffSpinner()
                .setValue(filterCutoffOldValue);

        instrumentUI.getTools().getFilterOptions().getFilterCutoffSlider()
                .setValue(filterCutoffOldValue);

        // filter resoance
        // set old value (needed in case of undo)
        filterRezOldValue = selectedInstrument.getInitialFilterResonance();

        instrumentUI.getTools().getFilterOptions().getFilterResonanceSpinner()
                .setValue(filterRezOldValue);

        instrumentUI.getTools().getFilterOptions().getFilterResonanceSlider()
                .setValue(filterRezOldValue);

        // random volume
        // set old value (needed in case of undo)
        randomVolumeOldValue = selectedInstrument.getRandomVolumeVariation();

        instrumentUI.getTools().getRandomOffsets().getRandomVolumeSpinner()
                .setValue(randomVolumeOldValue);

        instrumentUI.getTools().getRandomOffsets().getRandomVolumeSlider()
                .setValue(randomVolumeOldValue);

        // random panning
        // set old value (needed in case of undo)
        randomPanOldValue = selectedInstrument.getRandomPanningVariation();

        instrumentUI.getTools().getRandomOffsets().getRandomPanningSpinner()
                .setValue(randomPanOldValue);

        instrumentUI.getTools().getRandomOffsets().getRandomPanningSlider()
                .setValue(randomPanOldValue);

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

        // set record undo back to true
        setRecordingUndos(true);
        setAlteringModels(true);
    }
}
