/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.controllers.undo;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

/**
 *
 * @author Edward Jenkins
 */
public class UndoableCellChange extends AbstractUndoableEdit {
    
    // instance variables
    private byte[] cellData;
    private byte cellIndex;
    private byte cellNewValue;
    private byte cellOldValue;
    
    
    @Override
    public void redo() throws CannotRedoException {
        super.redo();
        cellData[cellIndex] = cellNewValue;
    }

    @Override
    public void undo() throws CannotUndoException {
        super.undo();
        cellData[cellIndex] = cellOldValue;
    }
}
