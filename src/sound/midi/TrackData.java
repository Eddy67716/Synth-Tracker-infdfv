/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sound.midi;

import module.IInstrument;

/**
 *
 * @author eddy6
 */
public class TrackData {
    
    // instance variables
    private String trackName;
    private byte port;
    private byte channel;
    private byte program;
    private short bank;
    private short initialPan;
    
    // constructor
    public TrackData(IInstrument instrument, byte port, byte channel) {
        this.port = port;
        this.channel = channel;
        this.trackName = instrument.getInstrumentName();
        bank = instrument.getMidiBank();
        program = (byte)(instrument.getMidiProgram() - 1);
        if (program < 0) {
            program = 0;
        }
    }
    
    // getters
    public String getTrackName() {
        return trackName;
    }

    public byte getPort() {
        return port;
    }

    public byte getChannel() {
        return channel;
    }

    public byte getProgram() {
        return program;
    }

    public short getBank() {
        return bank;
    }

    public short getInitialPan() {
        return initialPan;
    }
}
