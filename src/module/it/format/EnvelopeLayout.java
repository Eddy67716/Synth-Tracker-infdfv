package module.it.format;

import io.IReadable;
import io.IWritable;
import io.Reader;
import java.io.DataInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author Edward Jenkins
 * (c) 2021
 */
class EnvelopeLayout {
    
    // constants
    public static final byte ENVELOPE_LENGTH = 81;
    public static final byte NODE_LENGTH = 3;
    
    // instance variables
    // flags for envelope
    private short flags;
    // use envelope
    private boolean envelopeOn;
    // use loop
    private boolean loopOn;
    // use sustain loop
    private boolean sustainLoopOn;
    // carries envelope onto new note (MPT only)
    private boolean envelopeCarryOn;
    // use filter    
    private boolean pitchSetAsFilter;
    // nodes
    private short nodePointCount;
    // position of loop start
    private short loopBeginning; 
    // position of loop end
    private short loopEnd;
    // position of sustain loop start
    private short sustainLoopBeginning;
    // position of sustain loop end
    private short sustainLoopEnd;
    // node points
    private ItNodePoint[] nodePoints;     
    
    // constructor
    public EnvelopeLayout() {
        
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

    public short getNodePointCount() {
        return nodePointCount;
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

    public ItNodePoint[] getNodePoints() {
        return nodePoints;
    }
    
    // read method
    public boolean read(IReadable reader) 
            throws IOException, FileNotFoundException {
        
        // flags
        flags = reader.getUByteAsShort();
        
        // is envelope on
        envelopeOn = (flags & 1) == 1;
        
        // is envelope on
        loopOn = (flags & 2) == 2;
        
        // is sustain loop on
        sustainLoopOn = (flags & 4) == 4;
        
        // is carry envelope on (used in Open MPT files)
        envelopeCarryOn = (flags & 8) == 8;
        
        // use filter envelope
        pitchSetAsFilter = (flags & 128) == 128;
        
        // number of loop points
        nodePointCount = reader.getUByteAsShort();
        
        // loop begining
        loopBeginning = reader.getUByteAsShort();
        
        // loop end
        loopEnd = reader.getUByteAsShort();
        
        // sustain loop beginning
        sustainLoopBeginning = reader.getUByteAsShort();
        
        // sustain loop end
        sustainLoopEnd = reader.getUByteAsShort();
        
        // node points
        nodePoints = new ItNodePoint[nodePointCount];
        
        byte nodeValue;
        short tickNumber;
        int i;
        
        // loop to obtain node points
        for (i = 0; i < nodePointCount; i++) {
            
            // node value
            nodeValue = reader.getByte();
            
            // tick number
            tickNumber = reader.getShort();
            
            // set node instance i
            nodePoints[i] = new ItNodePoint(nodeValue, tickNumber);
        }
        
        // skip the end
        int emptyNodes = 25 - i;
        
        reader.skipBytes(emptyNodes * NODE_LENGTH);
        
        // skip last trailing byte
        reader.getByte();
        
        return true;
    }
    
    public boolean write(IWritable writer) 
            throws IOException, FileNotFoundException {
        
        // flags
        flags = 0;
        
        // is envelope on
        flags |= (envelopeOn) ? 0b1 : 0;
        
        // is envelope on
        flags |= (loopOn) ? 0b10 : 0;
        
        // is sustain loop on
        flags |= (sustainLoopOn) ? 0b100 : 0;
        
        // is carry envelope on (used in Open MPT files)
        flags |= (envelopeCarryOn) ? 0b1000 : 0;
        
        // use filter envelope
        flags |= (pitchSetAsFilter) ? 0b10000000 : 0;
        
        // write the flags
        writer.writeByte((byte)flags);
        
        // number of loop points
        writer.writeByte((byte)nodePointCount);
        
        // loop begining
        writer.writeByte((byte)loopBeginning);
        
        // loop end
        writer.writeByte((byte)loopEnd);
        
        // sustain loop beginning
        writer.writeByte((byte)sustainLoopBeginning);
        
        // sustain loop end
        writer.writeByte((byte)sustainLoopEnd);
        
        int i = 0;
        
        // loop to write node points
        for (ItNodePoint nodePoint : nodePoints) {
            
            // node value
            writer.writeByte((byte)nodePoint.getNodePoint());
            
            // tick number
            writer.writeShort(nodePoint.getTickIndex());
            
            i++;
        }
        
        // zero fill the end
        int emptyNodes = 25 - i;
        
        writer.skipBytes(emptyNodes * NODE_LENGTH);
        
        // skip last trailing byte
        writer.skipBytes(1);
        
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
        sb.append(nodePointCount);
        
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
