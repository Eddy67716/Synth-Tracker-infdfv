/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.view.pattens;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import module.IPattern;
import ui.controllers.CellController;

/**
 *
 * @author Edward Jenkins
 */
public class PatternTable extends JTable {
    
    // instance variables
    private int modID;
    private PatternCellEditor patternCellEditor;

    // constructor
    public PatternTable(int modType, String[] channelNames, 
            IPattern pattern, short instrumentCount, 
            CellController cellController) {
        super(new PatternTableModel(channelNames, pattern.getUnpackedData(), 
                pattern.getRows(), pattern.getGlobalChannelCount()));
        this.patternCellEditor = cellController.getPatternCell();
        for (int i = 0; i < getColumnCount(); i++) {
            TableColumn column = getColumnModel().getColumn(i);
            if (i == 0) {
                column.setMinWidth(30);
                column.setPreferredWidth(30);
                column.setMaxWidth(30);
            } else {
                column.setMinWidth(190);
                column.setPreferredWidth(190);
                column.setMaxWidth(190);
            }
        }
        setRowHeight(40);
        setDefaultRenderer(byte[].class, patternCellEditor);
        setDefaultEditor(byte[].class, cellController);
    }
    
    // getters
    public int getModID() {
        return modID;
    }

    public PatternCellEditor getPatternCellEditor() {
        return patternCellEditor;
    }
    
    public void changePattern(String[] channelNames, 
            IPattern pattern) {
        
        this.setModel(new PatternTableModel(channelNames, pattern.getUnpackedData(), 
                pattern.getRows(), pattern.getNumberOfChannels()));
        for (int i = 0; i < getColumnCount(); i++) {
            TableColumn column = getColumnModel().getColumn(i);
            if (i == 0) {
                column.setMinWidth(30);
                column.setPreferredWidth(30);
                column.setMaxWidth(30);
            } else {
                column.setMinWidth(190);
                column.setPreferredWidth(190);
                column.setMaxWidth(190);
            }
        }
        setRowHeight(40);
        this.repaint();
    }
}
