/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.view.pattens;

import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Edward Jenkins
 */
public class PatternTableModel extends AbstractTableModel {
    
    // instance variables
    private String[] columnNames;
    private Object[] tableHeaderData;
    private Object[][] tableData;
    private byte[][][] columnData;
    private int columns;
    private int rows;
    
    public PatternTableModel(String[] columnNames, 
            byte[][][] columnData, short rows, byte channels) {
        this.columnNames = columnNames;
        this.columnData = columnData;
        this.columns = channels + 1;
        this.rows = rows;
        init();
    }
    
    private void init() {
        tableData = new Object[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (j == 0) {
                    tableData[i][j] = i;
                } else {
                    tableData[i][j] = columnData[i][j - 1];
                }
            }
        }
    }
    
    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public int getRowCount() {
        return rows;
    }

    @Override
    public int getColumnCount() {
        return columns;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return tableData[rowIndex][columnIndex];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return getValueAt(0, columnIndex).getClass();
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        
        if (rowIndex > 0) {
            
            byte[] data = (byte[]) aValue;
            
            columnData[rowIndex][columnIndex] = data;
            fireTableCellUpdated(rowIndex, columnIndex);
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        
        boolean editable = false;
        
        if (columnIndex > 0) {
            editable = true;
        }
        
        return editable;
    }
}
