/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.controllers;

import java.util.List;
import javax.swing.undo.UndoManager;
import module.IChannel;
import module.IPattern;
import ui.view.models.EditPatternViewModel;
import ui.view.models.LoadViewModel;
import ui.view.pattens.PatternCellEditor;
import ui.view.pattens.PatternTable;
import ui.view.pattens.PatternUI;

/**
 *
 * @author Edward Jenkins
 */
public class PatternController extends GenericController {
    
    // instance variables
    private PatternUI patternUI;
    private EditPatternViewModel editSampleVM;
    private LoadViewModel loadVM;
    private String[] columnHeader;
    private IPattern selectedPattern;
    private PatternCellEditor patternCellEditor;
    private CellController cellController;
    private UndoManager[] patternManagers;
    
    public PatternController(PatternUI patternUI, LoadViewModel loadVM) {
        this.patternUI = patternUI;
        this.loadVM = loadVM;
        selectedPattern = loadVM.getPatterns().get(0);
        patternCellEditor = new PatternCellEditor(loadVM.getModID(), 
                (short) loadVM.getInstruments().size());
        patternCellEditor.setHilightValues(loadVM.getHeader().getPatternHilight());
        cellController = new CellController(patternCellEditor);
        List<IChannel> channels = loadVM.getHeader().getIChannels();
        String[] channelNames = new String[channels.size() + 1];
        for (int i = 0; i < channelNames.length; i++) {
            if (i == 0) {
                channelNames[i] = "#" + 0;
            } else {
                channelNames[i] = channels.get(i - 1).getChannelName();
            }
        }
        columnHeader = channelNames;
        PatternTable pt = new PatternTable(loadVM.getModID(), channelNames, 
            selectedPattern, (short) loadVM.getInstruments().size(), 
                cellController);
        patternUI.getPaternPanel().setPatternTable(pt);
        patternUI.getPaternPanel().init();
    }
    
    public void orderOnChange() {
        
    }
    
    public void patternOnChange() {
        patternUI.getPaternPanel().getPatternTable()
                .changePattern(columnHeader, selectedPattern);
    }
    
    public void viewListPattern(int index) {
        this.selectedPattern = this.loadVM.getPatterns().get(index);
        columnHeader[0] = "#" + index;
        patternOnChange();
    }
}
