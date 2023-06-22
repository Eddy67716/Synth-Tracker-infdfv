/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package module.s3m.format;

/**
 *
 * @author Edward Jenkins
 */
public class S3MHeader {
    
    // constants
    public static final byte SIG_1 = 0x1A;
    public static final byte TYPE = 0x10;
    public static final byte ZERO = 0;
    public static final String SIG_2 = "SCRM";
    
    // instance variables
    private String title;
    private int orderCount;
    private int instrumentCount;
    private int patternCount;
    private boolean usingST2Vibrato;
    private boolean usingST2Tempo;
    private boolean usingAmigaSlides;
    private boolean usingZeroVolOtpimising;
    private boolean usingAmigaNoteLimit;
    private boolean usingSoundBlasterFX;
    private boolean usingST3VolumeSlides;
    private boolean validPaternSpecial;
    private byte trackerID;
    private short trackerVersion;
    private byte sampleType;
    private short globalVolume;
    private int initialSpeed;
    private int initialTempo;
    private boolean stereo;
    private byte volume;
}
