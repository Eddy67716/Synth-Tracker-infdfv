/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.controllers;

import java.awt.Component;
import java.util.EventObject;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.CellEditorListener;
import javax.swing.table.TableCellEditor;
import javax.swing.undo.UndoManager;
import static module.it.effects.ItEffectValues.encodeEffectByte;
import static module.it.effects.ItEffectValues.encodeVolumeByte;
import static music.note.NoteFrequencyConstants.DEF_NOTE_RANGE;
import ui.view.pattens.PatternCellEditor;

/**
 *
 * @author Edward Jenkins
 */
public class CellController extends GenericController
        implements TableCellEditor {

    // instance variables
    private UndoManager undoManager;
    private PatternCellEditor patternCell;
    private CellEditorListener cellEditListener;
    private boolean volumeUsed;
    private boolean combinedVolume;
    private byte volumeIndex;
    private byte volumeValueIndex;
    private byte effect1Index;
    private byte effect1ValueIndex;
    private byte[] data;
    private byte[] tempData;

    public CellController(PatternCellEditor patternCell) {
        this.patternCell = patternCell;
        // setup the indexes based on the mod format
        switch (patternCell.getModType()) {
            case 1:
                volumeUsed = false;
                effect1Index = 2;
                break;
            case 2:
            case 3:
            case 4:
            case 5:
                volumeUsed = true;
                combinedVolume = true;
                volumeIndex = 2;
                effect1Index = 3;
                break;
            case 6:
                volumeUsed = true;
                combinedVolume = false;
                volumeIndex = 2;
                volumeValueIndex = 3;
                effect1Index = 4;
                break;
        }
        effect1ValueIndex = (byte) (effect1Index + 1);
        // event listners
        // note
        patternCell.addCellSectionActionListener(0, e -> noteOnChange());
        // instrument/sample
        patternCell.addCellSectionActionListener(1, e -> instrumentOnChange());
        if (patternCell.getModType() > 1) {
            // volume
            patternCell.addCellSectionActionListener(volumeIndex,
                    e -> volumeOnChange());
            if (patternCell.getModType() == 6) {
                // volume value
            }
        }
        // effect
        patternCell.addCellSectionActionListener(effect1Index,
                e -> effectOneOnChange());
        // effect value
        patternCell.addCellSectionActionListener(effect1Index + 1,
                e -> effectValueOneOnChange());
        if (patternCell.getModType() == 6) {
            // effect 2
            // effect 2 value
        }
    }

    public PatternCellEditor getPatternCell() {
        return patternCell;
    }

    public void noteOnChange() {

        JTextField noteField = patternCell.getCellSections()[0];

        noteField.validate();

        tempData[0] = (byte) (patternCell.getNoteRange()
                .getWritableNoteIndex(noteField.getText()) + 1);
    }

    public void instrumentOnChange() {
        JTextField instrumentField = patternCell.getCellSections()[1];

        instrumentField.validate();

        tempData[1] = (byte) Integer.parseInt(instrumentField.getText());
    }

    public void volumeOnChange() {
        JTextField volumeField = patternCell.getCellSections()[volumeIndex];

        volumeField.validate();

        tempData[volumeIndex] = (byte) encodeVolumeByte(volumeField.getText());
    }

    public void volumeValueOnChange() {

    }

    public void effectOneOnChange() {
        JTextField effectOneField = patternCell.getCellSections()[effect1Index];

        effectOneField.validate();

        tempData[effect1Index] = (byte) encodeEffectByte(
                effectOneField.getText().charAt(0));
    }

    public void effectValueOneOnChange() {
        JTextField effectOneField = patternCell.getCellSections()
                [effect1Index + 1];

        effectOneField.validate();

        tempData[effect1Index + 1] = (byte) (int)Integer
                .decode(effectOneField.getText());
    }

    public void effectTwoOnChange() {

    }

    public void effectValueTwoOnChange() {

    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        byte[] dataValue = (byte[]) value;

        data = dataValue;

        if (tempData == null || tempData.length != data.length) {
            tempData = new byte[data.length];
        }

        System.arraycopy(data, 0, tempData, 0, data.length);

        patternCell.setData(data);

        return patternCell;
    }

    @Override
    public Object getCellEditorValue() {
        return data;
    }

    @Override
    public boolean isCellEditable(EventObject anEvent) {
        return true;
    }

    @Override
    public boolean shouldSelectCell(EventObject anEvent) {
        return true;
    }

    @Override
    public boolean stopCellEditing() {
        System.arraycopy(tempData, 0, data, 0, data.length);

        return true;
    }

    @Override
    public void cancelCellEditing() {
        System.arraycopy(data, 0, tempData, 0, data.length);
    }

    @Override
    public void addCellEditorListener(CellEditorListener l) {
        cellEditListener = l;
    }

    @Override
    public void removeCellEditorListener(CellEditorListener l) {
        if (cellEditListener == l) {
            cellEditListener = null;
        }
    }
}
