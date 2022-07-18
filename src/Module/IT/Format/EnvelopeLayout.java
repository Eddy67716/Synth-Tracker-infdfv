package Module.IT.Format;

import io_stuff.ReadMethods;
import java.io.DataInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author Edward Jenkins
 * (c) 2021
 */
class EnvelopeLayout {
    
    // instance variables
    byte[] convertedBytes;              // used in obtaining littleEndian data
    ReadMethods rm;                     // used in converting littleEndian data
    private short flags;                // flags for envelope
    private boolean envelopeOn;         // use envelope
    private boolean loopOn;             // use loop
    private boolean sustainLoopOn;      // use sustain loop
    private boolean envelopeCarryOn;      // carries envelope onto new note (MPT only)
    private boolean pitchSetAsFilter;   // use filter
    private short numberOfNodePoints;   // nodes
    private short loopBeginning;        // position of loop start
    private short loopEnd;              // position of 
    private short sustainLoopBeginning; // position of sustain loop start
    private short sustainLoopEnd;       // position of sustain loop end
    private NodePoint[] nodePoints;     // node points
    
    // constructor
    public EnvelopeLayout(ReadMethods rm) {
        this.rm = rm;
    }
    
    // getters
    public ReadMethods getRM() {
        return rm;
    }

    public boolean isEnvelopeOn() {
        return envelopeOn;
    }

    public boolean isSustainLoopOn() {
        return sustainLoopOn;
    }

    public boolean isLoopOn() {
        return loopOn;
    }
    
    public boolean isEnvelopeCarryOn() {
        return envelopeCarryOn;
    }

    public boolean isPitchSetAsFilter() {
        return pitchSetAsFilter;
    }

    public short getNumberOfNodePoints() {
        return numberOfNodePoints;
    }

    public short getLoopBeginning() {
        return loopBeginning;
    }

    public short getLoopEnd() {
        return loopEnd;
    }

    public short getSustainLoopBeginning() {
        return sustainLoopBeginning;
    }

    public short getSustainLoopEnd() {
        return sustainLoopEnd;
    }

    public NodePoint[] getNodePoints() {
        return nodePoints;
    }
    
    // read method
    public boolean readEnvelope() throws IOException, FileNotFoundException {
        
        // flags
        flags = rm.getUByteAsShort();
        
        // is envelope on
        envelopeOn = (flags & 1) == 1;
        
        // is envelope on
        loopOn = (flags & 2) == 2;
        
        // is carry envelope on (used in Open MPT files)
        envelopeCarryOn = (flags & 8) == 8;
        
        // is sustain loop on
        sustainLoopOn = (flags & 4) == 4;
        
        // use filter envelope
        pitchSetAsFilter = (flags & 128) == 128;
        
        // number of loop points
        numberOfNodePoints = rm.getUByteAsShort();
        
        // loop begining
        loopBeginning = rm.getUByteAsShort();
        
        // loop end
        loopEnd = rm.getUByteAsShort();
        
        // sustain loop beginning
        sustainLoopBeginning = rm.getUByteAsShort();
        
        // sustain loop end
        sustainLoopEnd = rm.getUByteAsShort();
        
        // node points
        nodePoints = new NodePoint[25];
        
        byte nodeValue;
        short tickNumber;
        
        // loop to obtain node points
        for (int i = 0; i < 25; i++) {
            
            // node value
            nodeValue = rm.getByte();
            
            // tick number
            tickNumber = rm.getShort();
            
            // set node instance i
            nodePoints[i] = new NodePoint(nodeValue, tickNumber);
        }
        
        // skip one byte
        rm.getByte();
        
        return true;
    }
    
    // to string
    @Override
    public String toString() {
        
        // string builder
        StringBuilder sb = new StringBuilder();
        
        // envelope on
        sb.append("\nEnvelope[Envelope on: ");
        sb.append(envelopeOn);
        
        // loop on
        sb.append("\nLoop on: ");
        sb.append(loopOn);
        
        // sustain loop on
        sb.append("\nSustain Loop on: ");
        sb.append(sustainLoopOn);
        
        // carry envelope on
        sb.append("\nEnvelope carry on: ");
        sb.append(envelopeCarryOn);
        
        // use filter envelope
        sb.append("\nFilter envelope on: ");
        sb.append(pitchSetAsFilter);
        
        // number of loop points
        sb.append("\nNode point count: ");
        sb.append(numberOfNodePoints);
        
        // loop beginning
        sb.append("\nLoop begining: ");
        sb.append(loopBeginning);
        
        // loop end
        sb.append("\nLoop end: ");
        sb.append(loopEnd);
        
        // sustain loop beginning
        sb.append("\nSustain loop begining: ");
        sb.append(sustainLoopBeginning);
        
        // sustain loop end
        sb.append("\nSustain loop end: ");
        sb.append(sustainLoopEnd);
        
        // node points
        sb.append("\nNode points: ");
        
        // loop to obtain node points
        for (int i = 0; i < nodePoints.length; i++) {
            
            // node point
            sb.append("\nNode point ");
            sb.append(i);
            sb.append(": ");
            sb.append(nodePoints[i].toString());
            
        }
        
        sb.append("]");
        
        return sb.toString();
    }
    
}
