/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.view.pattens;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import module.it.effects.ItNoteRange;
import static module.it.effects.ItEffectValues.decodeEffectByte;
import static module.it.effects.ItEffectValues.decodeVolumeByte;
import music.note.NoteRange;
import static ui.UIProperties.DEF_TABLE_BACKGROUND_COLOUR;

/**
 *
 * @author Edward Jenkins
 */
public class PatternCell extends JPanel implements TableCellRenderer {
    
    // instance variables
    private NoteRange noteRange;
    private int modType;
    private int rows;
    // 1 is note, 2 is sample/instrumnet, 3 is volume, 4 is effect type and 5 is effect value
    private byte[] data;
    private JLabel[] cellSections;

    // constructor
    public PatternCell(int modType) {
        this.modType = modType;
        setBackground(DEF_TABLE_BACKGROUND_COLOUR);
        noteRange = new ItNoteRange();
        switch (modType) {
            case 1:
                cellSections = new JLabel[4];
                break;
            case 6:
                cellSections = new JLabel[8];
                break;
            default:
                cellSections = new JLabel[5];
                break;
        }
        init();
    }

    // getters
    public byte[] getData() {
        return data;
    }

    public JLabel[] getCellSections() {
        return cellSections;
    }

    // setters
    public void setData(byte[] data) {
        this.data = data;
    }

    public void init() {
        this.setLayout(new FlowLayout());
        for (int i = 0; i < cellSections.length; i++) {
            cellSections[i] = new JLabel();
            cellSections[i].setFont(new Font("Courier", Font.PLAIN, 20));
            cellSections[i].setBackground(DEF_TABLE_BACKGROUND_COLOUR);
            add(cellSections[i]);
        }
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
                    cellSections[i].setText(noteRange.getNote(data[i]));
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
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

        data = (byte[]) value;

        updateData();

        return this;
    }
    
}
