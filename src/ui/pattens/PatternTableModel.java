/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.pattens;

import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Edward Jenkins
 */
public class PatternTableModel extends AbstractTableModel {
    
    // instance variables
    private byte modType;
    private String[] columnNames;
    private Object[] tableHeaderData;
    private Object[][] patternData;
    private byte channels;
    private short rows;
    
    public PatternTableModel(byte modType, String[] columnNames, 
            byte[][][] columnData, byte channels, short rows) {
        this.modType = modType;
        this.columnNames = columnNames;
        this.channels = channels;
        this.rows = rows;
    }

    @Override
    public int getRowCount() {
        return rows;
    }

    @Override
    public int getColumnCount() {
        return channels;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return patternData[rowIndex][columnIndex];
    }
    
}
