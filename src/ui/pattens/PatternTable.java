/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.pattens;

import javax.swing.JPanel;
import javax.swing.JTable;

/**
 *
 * @author Edward Jenkins
 */
public class PatternTable extends JTable {

    // instance variables
    private byte modType;
    private String[] channelNames;
    private byte[][][] patternData;
    private short rows;
    private byte channels;

    // constructor
    public PatternTable(byte[][][] patternData, short rows, byte channels,
            byte modType, String[] channelNames) {
        this.modType = modType;
        this.channelNames = channelNames;
        this.patternData = patternData;
        this.rows = rows;
        this.channels = channels;
        init();
    }

    private void init() {
        this.dataModel = new PatternTableModel(modType, channelNames,
                patternData, channels, rows);
    }

}
