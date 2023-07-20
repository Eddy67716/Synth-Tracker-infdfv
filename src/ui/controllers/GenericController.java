/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.controllers;

import java.awt.Toolkit;
import javax.swing.undo.UndoManager;

/**
 *
 * @author Edward Jenkins
 */
public abstract class GenericController implements IUndoable {

    // instance variables
    // record undo on the collection
    private boolean recordingUndos;
    // record alterations onto a model
    private boolean alteringModels;
    // current undoManager for controller
    private UndoManager currentUndoManager;

    public GenericController() {
        recordingUndos = true;
        alteringModels = true;
    }

    // getters
    public boolean isRecordingUndos() {
        return recordingUndos;
    }

    public boolean isAlteringModels() {
        return alteringModels;
    }

    public UndoManager getCurrentUndoManager() {
        return currentUndoManager;
    }

    // setters
    public void setRecordingUndos(boolean recordingUndos) {
        this.recordingUndos = recordingUndos;
    }

    public void setAlteringModels(boolean alteringModels) {
        this.alteringModels = alteringModels;
    }

    public void setCurrentUndoManager(UndoManager currentUndoManager) {
        this.currentUndoManager = currentUndoManager;
    }

    // other methods
    public void playErrorSound() {
        Toolkit.getDefaultToolkit().beep();
    }
    
    @Override
    public void undo() {
        if (getCurrentUndoManager().canUndo()) {
            recordingUndos = false;
            currentUndoManager.undo();
            recordingUndos = (true);
        } else {
            playErrorSound();
        }
    }

    @Override
    public void redo() {
        if (getCurrentUndoManager().canRedo()) {
            recordingUndos = false;
            currentUndoManager.redo();
            recordingUndos = (true);
        } else {
            playErrorSound();
        }
    }
}
