/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package module.xm.format;

import java.util.List;

/**
 *
 * @author Edward Jenkins
 */
public class XMFile {
    
    // constants
    public static final int XM_MOD_TYPE_ID = 3;
    
    // instance variables
    private String file;
    private XMHeader header;
    private List<XMInstrumentHeader> instruments;     // instruments
    private List<XMSampleHeader> sampleHeaders; // sample headers
    private List<XMPattern> patterns;           // pattenrs
    
}
