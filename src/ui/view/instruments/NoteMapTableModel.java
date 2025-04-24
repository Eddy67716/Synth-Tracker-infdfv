/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.view.instruments;

import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Edward Jenkins
 */
public class NoteMapTableModel extends AbstractTableModel {
    
    // instance variabels
    private String[] columnNames;
    private Object[][] data;
    
    // constructor
    public NoteMapTableModel(String[] columnNames, String[] notes, 
            short[][] noteMap) {
        super();
        
        this.columnNames = columnNames;
        
        data = new Object[notes.length][columnNames.length];
        
        for (int i = 0; i < notes.length; i++) {
            
            // note name
            data[i][0] = notes[i];
            
            // sample
            data[i][1] = noteMap[0][i];
            
            // sample note
            data[i][2] = noteMap[1][i];
        }
    }
    
    // 3 args constructor
    public NoteMapTableModel(String[] columnNames, String[] notes, 
            short[] noteMap) {
        
        this.columnNames = columnNames;
        
        data = new Object[columnNames.length][notes.length];
        
        for (int i = 0; i < notes.length; i++) {
            
            // note name
            data[0][i] = notes[i];
            
            // sample
            data[1][i] = noteMap[i];
        }
    }
    
    // 2 arg constructor
    public NoteMapTableModel(String[] columnNames, String[] notes) {
        
        this.columnNames = columnNames;
        
        data = new Object[columnNames.length][notes.length];
        
        System.arraycopy(notes, 0, data[0], 0, notes.length); // note name
    }

    @Override
    public int getRowCount() {
        return data.length;
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return data[rowIndex][columnIndex];
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return (columnIndex > 0);
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        data[rowIndex][columnIndex] = aValue;
        fireTableCellUpdated(rowIndex, columnIndex);
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
    
    public void setNoteMap(short[][] noteMap) {
        
        for (int i = 0; i < data.length; i++) {
            
            // sample
            data[i][1] = noteMap[i][0];
            fireTableCellUpdated(1, i);
            
            // sample note
            data[i][2] = noteMap[i][1];
            fireTableCellUpdated(2, i);
        }
    }
}
