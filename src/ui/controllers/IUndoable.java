/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.controllers;

/**
 *
 * @author Edward Jenkins
 */
public interface IUndoable {
    
    // process an undo option
    public void undo();
    
    // process a redo option
    public void redo();
}
