/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package module.xm.format;

/**
 *
 * @author Edward Jenkins
 */
public class XMInstrumentHeader {
    
    // instance variables
    private int instrumentSize;
    private String instrumentName;
    private byte instrumentType;
    private short numberOfSamples;
    private int sampleHeaderSize;
    private byte[] sampleKeymapAssignments;
    private short[] volumeEnvelopePoints;
    private short[] panningEnvelopePoints;
    private byte volumePointCount;
    private byte panningPointCount;
    private byte volumeSustainPoint;
    private byte volumeLoopStartPoint;
    private byte volumeLoopEndPoint;
    private byte panningSustainPoint;
    private byte panningLoopStartPoint;
    private byte panningLoopEndPoint;
    private byte volumeFlags;
    private byte panningFlags;
    private byte vibratoWaveform;
    private byte vibratoDelay;
    private byte vibratoDepth;
    private byte vibratoRate;
    private short volumeFadeout;
}
