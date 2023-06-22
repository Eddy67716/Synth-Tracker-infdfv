/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package module.xm.format;

/**
 *
 * @author Edward Jenkins
 */
public class XMHeader {
    
    // constants
    public static final String ID_TEXT = "Extended module: ";
    public static final byte ONE_A_BYTE = 0x1a;
    
    // instance variables
    private String modluleName;
    private String trackerName;
    private short versionNumber;
    private long headerSize;
    private short songLength;
    private short restartOrder;
    private short channels;
    private short numberOfPatterns;
    private short numberOfInstruments;
    private boolean usingLinearSlides;
    private short defaultTempo;
    private short defaultBPM;
    private short[] orderTable;
}
