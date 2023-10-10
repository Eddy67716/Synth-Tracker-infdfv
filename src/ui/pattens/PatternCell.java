/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.pattens;

import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import module.it.decoders.NoteRange;
import static module.it.decoders.effectDecoders.*;

/**
 *
 * @author Edward Jenkins
 */
public class PatternCell extends JPanel {

    // instance variables
    private NoteRange noteRange;
    private int modType;
    private int rows;
    // 1 is note, 2 is sample/instrumnet, 3 is volume, 4 is effect type and 5 is effect value
    private byte[] data;
    private JTextArea[] cellSections;

    // constructor
    public PatternCell(int modType, byte[] data) {
        this.modType = modType;
        this.data = data;
        noteRange = new NoteRange();
        switch (modType) {
            case 1:
                cellSections = new JTextArea[4];
                break;
            case 6:
                cellSections = new JTextArea[8];
                break;
            default:
                cellSections = new JTextArea[5];
                break;
        }
        init();
    }

    // getters
    public byte[] getData() {
        return data;
    }
    
    public void init() {
        this.setLayout(new FlowLayout());
        for (int i = 0; i < cellSections.length; i++) {
            cellSections[i] = new JTextArea();
            cellSections[i].setFont(new Font("Courier", Font.PLAIN, 20));
            cellSections[i].setRows(1);
            switch (i) {
                case 0:
                    cellSections[i].setColumns(3);
                    cellSections[i].setText(noteRange.getNote(data[i]));
                    break;
                case 1:
                    cellSections[i].setColumns(2);
                    cellSections[i].setText(String.format("%02d", data[i]));
                    break;
                case 2:
                    cellSections[i].setColumns(2);
                    cellSections[i].setText(decodeVolumeByte(data[i]));
                    break;
                case 3:
                    cellSections[i].setColumns(1);
                    cellSections[i]
                            .setText(String.valueOf(decodeEffectByte(data[i])));
                    break;
                case 4:
                    cellSections[i].setColumns(2);
                    cellSections[i].setText(String.format("%02X", data[i]));
                    break;
                default:
                    cellSections[i].setColumns(2);
                    cellSections[i].setText(String.valueOf(data[i]));
                    break;
            }
            add(cellSections[i]);
        }
    }
}
