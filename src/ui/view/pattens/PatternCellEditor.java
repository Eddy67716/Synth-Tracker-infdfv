/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.view.pattens;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;
import java.util.EventObject;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.event.CaretListener;
import javax.swing.event.CellEditorListener;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import module.it.effects.ItNoteRange;
import static module.it.effects.ItEffectValues.decodeEffectByte;
import static module.it.effects.ItEffectValues.decodeVolumeByte;
import static music.note.NoteFrequencyConstants.DEF_NOTE_RANGE;
import music.note.NoteRange;
import static ui.UIProperties.DEF_BACKGROUND_COLOUR;
import static ui.UIProperties.DEF_EXTREMELY_WASHED_COLOUR;
import static ui.UIProperties.DEF_HEAVILY_WASHED_COLOUR;
import static ui.UIProperties.DEF_TABLE_BACKGROUND_COLOUR;
import static ui.UIProperties.NO_COLOUR;
import ui.verifiers.EffectValueVerifier;
import ui.verifiers.EffectVerifier;
import ui.verifiers.InstrumentVerifier;
import ui.verifiers.NoteVerifier;
import ui.verifiers.TotalVolumeVerifier;

/**
 *
 * @author Edward Jenkins
 */
public class PatternCellEditor extends JPanel implements TableCellRenderer {

    // instance variables
    private FlowLayout flowLayout;
    private NoteRange noteRange;
    // verifiers
    private NoteVerifier noteVerifier;
    private short instrumentCount;
    private int modType;
    // 1 is note, 2 is sample/instrumnet, 3 is volume, 4 is effect type and 5 is effect value
    private byte[] data;
    private JTextField[] cellSections;
    // hilight values
    private short[] hilightValues;

    // constructor
    public PatternCellEditor(int modType, short instrumentCount) {
        this.modType = modType;
        this.instrumentCount = instrumentCount;
        setBackground(DEF_TABLE_BACKGROUND_COLOUR);
        noteRange = new ItNoteRange();
        switch (modType) {
            case 1:
                noteVerifier = new NoteVerifier(noteRange, 0, 120);
                cellSections = new JTextField[4];
                break;
            case 6:
                noteVerifier = new NoteVerifier(noteRange, 0, 121);
                cellSections = new JTextField[8];
                break;
            default:
                cellSections = new JTextField[5];
                break;
        }
        init();
    }

    // getters
    public int getModType() {
        return modType;
    }

    public NoteRange getNoteRange() {
        return noteRange;
    }

    public byte[] getData() {
        return data;
    }

    public JTextField[] getCellSections() {
        return cellSections;
    }

    // setters
    public void setData(byte[] data) {
        this.data = data;
        updateData();
    }

    public void setHilightValues(short[] hilightValues) {
        this.hilightValues = hilightValues;
    }

    public void init() {
        flowLayout = new FlowLayout(FlowLayout.CENTER, 0, 0);
        this.setLayout(flowLayout);
        switch (modType) {
            case 1:
                initModData();
                break;
            case 2:
                initS3mData();
                break;
            case 3:
                initXmData();
                break;
            case 4:
                initItData();
                break;
            case 5:
                initMptmData();
                break;
            case 6:
                initStrData();
                break;
        }
    }

    private void initModData() {

    }

    private void initS3mData() {

    }

    private void initXmData() {

    }

    private void initItData() {
        for (int i = 0; i < cellSections.length; i++) {
            switch (i) {
                case 0:
                    cellSections[i] = new JFormattedTextField();
                    cellSections[i].setInputVerifier(noteVerifier);
                    initCell(cellSections[i]);
                    cellSections[i].setColumns(3);
                    break;
                case 1:
                    InstrumentVerifier iv
                            = new InstrumentVerifier(instrumentCount);
                    cellSections[i] = new JTextField();
                    cellSections[i].setInputVerifier(iv);
                    initCell(cellSections[i]);
                    cellSections[i].setColumns(2);
                    break;
                case 2:
                    TotalVolumeVerifier tvv = new TotalVolumeVerifier();
                    cellSections[i] = new JTextField();
                    cellSections[i].setInputVerifier(tvv);
                    initCell(cellSections[i]);
                    cellSections[i].setColumns(3);
                    break;
                case 3:
                    EffectVerifier ev = new EffectVerifier();
                    cellSections[i] = new JTextField();
                    cellSections[i].setInputVerifier(ev);
                    initCell(cellSections[i]);
                    cellSections[i].setColumns(1);
                    break;
                case 4:
                    EffectValueVerifier evv = new EffectValueVerifier();
                    cellSections[i] = new JTextField();
                    cellSections[i].setInputVerifier(evv);
                    initCell(cellSections[i]);
                    cellSections[i].setColumns(2);
                    break;
                default:
                    cellSections[i] = new JTextField();
                    initCell(cellSections[i]);
                    cellSections[i].setColumns(2);
                    break;
            }
            add(cellSections[i]);
        }
    }

    private void initMptmData() {

    }

    private void initStrData() {

    }

    private void initCell(JTextField field) {
        field.setFont(new Font("Courier", Font.PLAIN, 20));
        field.setBackground(DEF_TABLE_BACKGROUND_COLOUR);
        field.setBorder(javax.swing.BorderFactory
                .createEmptyBorder());
    }

    private void updateData() {
        switch (modType) {
            case 1:
                updateModData();
                break;
            case 2:
                updateS3mData();
                break;
            case 3:
                updateXmData();
                break;
            case 4:
                updateItData();
                break;
            case 5:
                updateMptmData();
                break;
            case 6:
                updateStrData();
                break;
        }
    }

    private void updateModData() {

    }

    private void updateS3mData() {

    }

    private void updateXmData() {

    }

    private void updateItData() {
        for (int i = 0; i < cellSections.length; i++) {
            switch (i) {
                case 0:
                    byte noteData = data[i];
                    if (noteData > 0 && noteData < 121) {
                        noteData--;
                    }
                    cellSections[i].setText(noteRange.getNote(noteData));
                    break;
                case 1:
                    cellSections[i].setText(String.format("%02d", data[i]));
                    break;
                case 2:
                    cellSections[i].setText(decodeVolumeByte(data[i]));
                    break;
                case 3:
                    cellSections[i]
                            .setText(String.valueOf(decodeEffectByte(data[i])));
                    break;
                case 4:
                    cellSections[i].setText(String.format("%02X", data[i]));
                    break;
                default:
                    cellSections[i].setText(String.valueOf(data[i]));
                    break;
            }
        }
    }

    private void updateMptmData() {

    }

    private void updateStrData() {

    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {

        data = (byte[]) value;

        if (row % hilightValues[1] == 0) {
            reColourCell(DEF_HEAVILY_WASHED_COLOUR);
        } else if (row % hilightValues[0] == 0) {
            reColourCell(DEF_EXTREMELY_WASHED_COLOUR);
        } else {
            reColourCell(DEF_TABLE_BACKGROUND_COLOUR);
        }

        updateData();

        return this;
    }

    private void reColourCell(Color colour) {
        setBackground(colour);
        for (JTextField cellSection : cellSections) {
            cellSection.setBackground(colour);
        }
    }

    // events and listeners
    public void addCellSectionCaretListener(int index,
            CaretListener caretPerfomed) {
        this.cellSections[index].addCaretListener(caretPerfomed);
    }

    public void addCellSectionFocusListener(int index,
            FocusListener focusEvent) {
        this.cellSections[index].addFocusListener(focusEvent);
    }

    public void addCellSectionActionListener(int index,
            ActionListener actionPerformed) {
        this.cellSections[index].addActionListener(actionPerformed);
    }
}
