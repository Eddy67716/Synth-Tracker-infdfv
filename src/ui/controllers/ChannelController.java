/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.controllers;

import javax.swing.undo.UndoManager;
import module.IAudioSample;
import ui.view.details.ChannelPanel;
import ui.view.models.EditSampleViewModel;
import ui.view.models.LoadViewModel;

/**
 *
 * @author Edward Jenkins
 */
public class ChannelController {
    
    // instance variables
    private ChannelPanel channelPanel;
    private EditSampleViewModel editSampleVM;
    private LoadViewModel loadVM;
    private IAudioSample selectedSample;
    private UndoManager[] sampleManagers;
    private int channelVolumeOldValue;
    private int chnnnelPanOldValue;
}
