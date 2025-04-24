/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sound.formats;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Edward Jenkins
 * @version 0.2 © 2024
 */
public class AudioSampleData implements Cloneable {

    // constants
    public static final byte FOUR_BIT_SIGN_SHIFT = 8;
    public static final short EIGHT_BIT_SIGN_SHIFT = 128;
    public static final int SIXTEEN_BIT_SIGN_SHIFT = 32768;

    // instance variables
    private byte bitrate;
    // defined the maximum value of the bit depth
    private double bitAmplitude;
    // the inverse of the above value for more efficient normalising
    private double inverseBitAmplitude;
    private boolean signed;
    private boolean floating;
    private byte channels;
    private int sampleCount;
    private int sampleIndex;
    private int sampleByteIndex;
    // 4 bit data
    private byte nybblePair;
    private boolean nybbleOffset;
    private byte bytesPerSample;
    // channel then index
    private byte[][] sampleData;
    private ByteBuffer buffer;
    private byte[] byteBuffer;
    private byte[] spliceToArray;
    // when streaming to a sample data block
    private List<List<Byte>> streamedData;
    private boolean streaming;

    // all args constructor
    public AudioSampleData(byte bitrate, boolean signed, boolean floating,
            byte channels, int sampleCount, boolean initialiseBlock,
            boolean streaming) {
        this.bitrate = bitrate;
        bitAmplitude = Math.pow(2, bitrate) / 2 - 0.5;
        inverseBitAmplitude = 1.0 / bitAmplitude;
        bytesPerSample = (byte) (bitrate / 8);
        this.signed = signed;
        this.floating = floating;
        this.channels = channels;
        this.sampleCount = sampleCount;
        if (sampleCount > 0 && initialiseBlock && !streaming) {
            initSampleArray();
        } else if (streaming) {
            streamedData = new ArrayList<>();
            for (int i = 0; i < this.channels; i++) {
                streamedData.add(new LinkedList<>());
            }
        }
    }

    // used when the length of data is known
    public AudioSampleData(byte bitrate, boolean signed, boolean floating,
            byte channels, int sampleCount, boolean initialiseBlock) {
        this(bitrate, signed, floating, channels, sampleCount, initialiseBlock,
                false);
    }

    // auto initialise sample block constructor
    public AudioSampleData(byte bitrate, boolean signed, boolean floating,
            byte channels, int sampleCount) {
        this(bitrate, signed, floating, channels, sampleCount, true);
    }

    // signed integer pcm data constructor
    public AudioSampleData(byte bitrate, byte channels, int sampleCount) {
        this(bitrate, true, false, channels, sampleCount);
    }

    // unknown sample count constructor
    public AudioSampleData(byte bitrate, boolean signed, boolean floating,
            byte channels) {
        this(bitrate, signed, floating, channels, 0);
    }

    // streaming constructor
    public AudioSampleData(byte bitrate, boolean signed, boolean floating,
            byte channels, boolean streaming) {
        this(bitrate, signed, floating, channels, 0, false, streaming);
    }

    // getters
    public byte getBitrate() {
        return bitrate;
    }

    public boolean isSigned() {
        return signed;
    }

    public boolean isFloating() {
        return floating;
    }

    public byte getChannels() {
        return channels;
    }

    public int getSampleCount() {
        return sampleCount;
    }

    public byte[][] getSampleData() {
        return sampleData;
    }

    public boolean isStreaming() {
        return streaming;
    }

    // setters
    public void setBitrate(byte bitrate) {
        this.bitrate = bitrate;
        bitAmplitude = Math.pow(2, bitrate) / 2 - 0.5;
        inverseBitAmplitude = 1.0 / bitAmplitude;
    }

    public void setSigned(boolean signed) {
        this.signed = signed;
    }

    public void setFloating(boolean floating) {
        this.floating = floating;
    }

    public void setChannels(byte channels) {
        this.channels = channels;
    }

    public void setSampleCount(int sampleCount) {
        this.sampleCount = sampleCount;
        initSampleArray();
    }

    public void setSampleData(byte[][] sampleData) {
        this.sampleData = sampleData;
        byteBuffer = new byte[bytesPerSample];
        if (bitrate == 24) {
            spliceToArray = new byte[4];
        }
    }

    public void channelInputReset() {
        this.sampleByteIndex = 0;
        this.sampleIndex = 0;
    }

    public void setStreaming(boolean streaming) {
        this.streaming = streaming;
        finaliseData();
    }

    private void initSampleArray() {

        int sampleLength;

        if (bitrate != 4) {
            sampleLength = sampleCount * bytesPerSample;
            
            // the byte buffer
            int bufferBytes = (bytesPerSample == 3) ? 4 : bytesPerSample;
            buffer = ByteBuffer.allocate(bufferBytes);
        } else {
            sampleLength = sampleCount / 2;
            if ((sampleCount & 1) == 1) {
                sampleLength++;
            }
            nybbleOffset = false;
        }

        this.sampleData = new byte[channels][sampleLength];
        sampleIndex = 0;
        sampleByteIndex = 0;
        byteBuffer = new byte[bytesPerSample];
        if (bitrate == 24) {
            spliceToArray = new byte[4];
        }
    }

    public void finaliseData() {

        int sampleLength = streamedData.get(0).size();

        sampleData = new byte[channels][sampleLength];

        for (int i = 0; i < sampleData.length; i++) {
            for (int j = 0; j < sampleData[i].length; i++) {
                sampleData[i][j] = streamedData.get(i).get(j);
            }
        }

        streamedData = null;
    }

    public void inputSamplePoints(double[] samplePoints) {

        //System.out.println(sampleByteIndex);
        for (int i = 0; i < channels; i++) {
            //System.out.println(samplePoints[i]);
            switch (bitrate) {
                case 4:
                    byte point = (byte) samplePoints[i];
                    if (!signed) {
                        point += FOUR_BIT_SIGN_SHIFT;
                    }
                    point &= 0b1111;
                    if (nybbleOffset) {
                        nybblePair |= point;
                    } else {
                        nybblePair = (byte) (point << 4);
                    }
                    break;
                case 8:
                    if (signed) {
                        buffer.put((byte) samplePoints[i]);
                    } else {
                        buffer.put((byte) (samplePoints[i]
                                + EIGHT_BIT_SIGN_SHIFT));
                    }
                    break;
                case 16:
                    if (signed) {
                        buffer.putShort((short) samplePoints[i]);
                    } else {
                        buffer.putShort((short) (samplePoints[i]
                                + SIXTEEN_BIT_SIGN_SHIFT));
                    }
                    break;
                case 24:
                    buffer.putInt((int) samplePoints[i]);
                    break;
                case 32:
                    if (floating) {
                        buffer.putFloat((float) samplePoints[i]);
                    } else {
                        buffer.putInt((int) samplePoints[i]);
                    }
                    break;
                case 64:
                    if (floating) {
                        buffer.putDouble(samplePoints[i]);
                    } else {
                        buffer.putLong((long) samplePoints[i]);
                    }
                default:
                    break;
            }

            inputBuffer(i);
        }

        incrementSampleIndex();
    }

    public void inputIntegerSamplePoints(long[] samplePoints) {

        //System.out.println(sampleByteIndex);
        for (int i = 0; i < channels; i++) {
            //System.out.println(samplePoints[i]);
            switch (bitrate) {
                case 4:
                    byte point = (byte) samplePoints[i];
                    if (!signed) {
                        point += FOUR_BIT_SIGN_SHIFT;
                    }
                    point &= 0b1111;
                    if (nybbleOffset) {
                        nybblePair |= point;
                    } else {
                        nybblePair = (byte) (point << 4);
                    }
                    break;
                case 8:
                    if (signed) {
                        buffer.put((byte) samplePoints[i]);
                    } else {
                        buffer.put((byte) (samplePoints[i]
                                + EIGHT_BIT_SIGN_SHIFT));
                    }
                    break;
                case 16:
                    if (signed) {
                        buffer.putShort((short) samplePoints[i]);
                    } else {
                        buffer.putShort((short) (samplePoints[i]
                                + SIXTEEN_BIT_SIGN_SHIFT));
                    }
                    break;
                case 24:
                    buffer.putInt((int) samplePoints[i]);
                    break;
                case 32:
                    if (floating) {
                        buffer.putFloat((float) ((double) samplePoints[i]
                                / bitAmplitude));
                    } else {
                        buffer.putInt((int) samplePoints[i]);
                    }
                    break;
                case 64:
                    if (floating) {
                        buffer.putDouble((double) samplePoints[i]
                                / bitAmplitude);
                    } else {
                        buffer.putLong((long) samplePoints[i]);
                    }
                default:
                    break;
            }

            inputBuffer(i);
        }

        incrementSampleIndex();
    }

    public void inputChannelPoint(byte channel, double samplePoint) {

        // the byte buffer
        int bufferBytes = (bytesPerSample == 3) ? 4 : bytesPerSample;
        buffer = ByteBuffer.allocate(bufferBytes);

        //System.out.println(sampleByteIndex);
        switch (bitrate) {
            case 4:
                byte point = (byte) samplePoint;
                if (!signed) {
                    point += FOUR_BIT_SIGN_SHIFT;
                }
                point &= 0b1111;
                if (nybbleOffset) {
                    nybblePair |= point;
                } else {
                    nybblePair = (byte) (point << 4);
                }
                break;
            case 8:
                if (signed) {
                    buffer.put((byte) samplePoint);
                } else {
                    buffer.put((byte) (samplePoint
                            + EIGHT_BIT_SIGN_SHIFT));
                }
                break;
            case 16:
                if (signed) {
                    buffer.putShort((short) samplePoint);
                } else {
                    buffer.putShort((short) (samplePoint
                            + SIXTEEN_BIT_SIGN_SHIFT));
                }
                break;
            case 24:
                buffer.putInt((int) samplePoint);
                break;
            case 32:
                if (floating) {
                    buffer.putFloat((float) samplePoint);
                } else {
                    buffer.putInt((int) samplePoint);
                }
                break;
            case 64:
                if (floating) {
                    buffer.putDouble(samplePoint);
                } else {
                    buffer.putLong((long) samplePoint);
                }
            default:
                break;
        }

        inputBuffer(channel);

        incrementSampleIndex();
    }

    public void inputIntegerChannelPoint(byte channel, long samplePoint) {

        // the byte buffer
        int bufferBytes = (bytesPerSample == 3) ? 4 : bytesPerSample;
        buffer = ByteBuffer.allocate(bufferBytes);

        //System.out.println(sampleByteIndex);
        //System.out.println(samplePoints[i]);
        switch (bitrate) {
            case 4:
                byte point = (byte) samplePoint;
                if (!signed) {
                    point += FOUR_BIT_SIGN_SHIFT;
                }
                point &= 0b1111;
                if (nybbleOffset) {
                    nybblePair |= point;
                } else {
                    nybblePair = (byte) (point << 4);
                }
                break;
            case 8:
                if (signed) {
                    buffer.put((byte) samplePoint);
                } else {
                    buffer.put((byte) (samplePoint
                            + EIGHT_BIT_SIGN_SHIFT));
                }
                break;
            case 16:
                if (signed) {
                    buffer.putShort((short) samplePoint);
                } else {
                    buffer.putShort((short) (samplePoint
                            + SIXTEEN_BIT_SIGN_SHIFT));
                }
                break;
            case 24:
                buffer.putInt((int) samplePoint);
                break;
            case 32:
                if (floating) {
                    buffer.putFloat((float) ((double) samplePoint
                            / bitAmplitude));
                } else {
                    buffer.putInt((int) samplePoint);
                }
                break;
            case 64:
                if (floating) {
                    buffer.putDouble((double) samplePoint
                            / bitAmplitude);
                } else {
                    buffer.putLong((long) samplePoint);
                }
            default:
                break;
        }

        inputBuffer(channel);

        incrementSampleIndex();
    }

    private void inputBuffer(int channel) {
        if (bitrate == 4) {
            if (nybbleOffset) {
                sampleData[channel][sampleByteIndex] = nybblePair;
                nybblePair = 0;
            }
        } else {
            byte[] bytessForInput = buffer.array();

            buffer.clear();

            int k = (bytesPerSample == 3) ? 1 : 0;
            for (int j = sampleByteIndex; j < sampleByteIndex
                    + bytesPerSample; j++, k++) {
                if (streaming) {
                    streamedData.get(channel).add(bytessForInput[k]);
                } else {
                    sampleData[channel][j] = bytessForInput[k];
                }
            }
        }
    }

    private void incrementSampleIndex() {
        if (bitrate != 4) {
            sampleIndex++;
            sampleByteIndex += bytesPerSample;
        } else {
            if (nybbleOffset) {
                sampleIndex++;
            }
            nybbleOffset = !nybbleOffset;
        }
        if (streaming) {
            sampleCount++;
        }
    }

    public double[] outputPoints(int index) {

        // method variables
        double[] outputPoints = new double[channels];
        ByteBuffer debuffer;

        // channel loop
        for (int i = 0; i < channels; i++) {

            if (bitrate != 4) {
                sampleByteIndex = index * bytesPerSample;

                // get bytes for buffer
                for (int j = 0; j < bytesPerSample; j++, sampleByteIndex++) {
                    byteBuffer[j] = sampleData[i][sampleByteIndex];
                }
            }

            switch (bitrate) {
                case 4:
                    byte point;
                    int arrayIndex = index >>> 1;
                    boolean nybbleOffset = (index & 0b1) == 1;
                    if (nybbleOffset) {
                        point = sampleData[i][arrayIndex];
                    } else {
                        point = (byte) (sampleData[i][arrayIndex] >> 4);
                    }
                    if (signed && (point & 0b1000) == 0b1000) {
                        point |= 0b11110000;
                    }
                    outputPoints[i] = point;
                    break;
                case 8:
                    debuffer = ByteBuffer.wrap(byteBuffer);
                    outputPoints[i] = debuffer.get();
                    break;
                case 16:
                    debuffer = ByteBuffer.wrap(byteBuffer);
                    outputPoints[i] = debuffer.getShort();
                    break;
                case 24:
                    int twentyFourBitSample;

                    // build int from bytes
                    spliceToArray[0] = (((byteBuffer[0] >>> 7) & 1) == 1)
                            ? (byte) 0xff : (byte) 0;

                    System.arraycopy(byteBuffer, 0, spliceToArray, 1,
                            bytesPerSample);

                    debuffer = ByteBuffer.wrap(spliceToArray);
                    twentyFourBitSample = debuffer.getInt();
                    outputPoints[i] = twentyFourBitSample;
                    break;
                case 32:
                    debuffer = ByteBuffer.wrap(byteBuffer);
                    if (floating) {
                        outputPoints[i] = debuffer.getFloat();
                    } else {
                        outputPoints[i] = debuffer.getInt();
                    }
                    break;
                case 64:
                    debuffer = ByteBuffer.wrap(byteBuffer);
                    if (floating) {
                        outputPoints[i] = debuffer.getDouble();
                    } else {
                        outputPoints[i] = debuffer.getLong();
                    }
                default:
                    break;
            }
        }

        return outputPoints;
    }

    public long[] outputIntegerPoints(int index) {

        // method variables
        long[] outputPoints = new long[channels];
        ByteBuffer debuffer;

        // channel loop
        for (int i = 0; i < channels; i++) {

            if (bitrate != 4) {
                sampleByteIndex = index * bytesPerSample;

                // get bytes for buffer
                for (int j = 0; j < bytesPerSample; j++, sampleByteIndex++) {
                    byteBuffer[j] = sampleData[i][sampleByteIndex];
                }
            }

            switch (bitrate) {
                case 4:
                    byte point;
                    int arrayIndex = index >>> 1;
                    boolean nybbleOffset = (index & 0b1) == 1;
                    if (nybbleOffset) {
                        point = sampleData[i][arrayIndex];
                    } else {
                        point = (byte) (sampleData[i][arrayIndex] >> 4);
                    }
                    if (signed && (point & 0b1000) == 0b1000) {
                        point |= 0b11110000;
                    }
                    outputPoints[i] = point;
                    break;
                case 8:
                    debuffer = ByteBuffer.wrap(byteBuffer);
                    outputPoints[i] = debuffer.get();
                    break;
                case 16:
                    debuffer = ByteBuffer.wrap(byteBuffer);
                    outputPoints[i] = debuffer.getShort();
                    break;
                case 24:
                    int twentyFourBitSample;

                    // build int from bytes
                    spliceToArray[0] = (((byteBuffer[0] >>> 7) & 1) == 1)
                            ? (byte) 0xff : (byte) 0;

                    System.arraycopy(byteBuffer, 0, spliceToArray, 1,
                            bytesPerSample);

                    debuffer = ByteBuffer.wrap(spliceToArray);
                    twentyFourBitSample = debuffer.getInt();
                    outputPoints[i] = twentyFourBitSample;
                    break;
                case 32:
                    debuffer = ByteBuffer.wrap(byteBuffer);
                    if (floating) {
                        outputPoints[i] = (int) (debuffer.getFloat()
                                * this.bitAmplitude - 0.5);
                    } else {
                        outputPoints[i] = debuffer.getInt();
                    }
                    break;
                case 64:
                    debuffer = ByteBuffer.wrap(byteBuffer);
                    if (floating) {
                        outputPoints[i] = (long) (debuffer.getDouble()
                                * this.bitAmplitude - 0.5);
                    } else {
                        outputPoints[i] = debuffer.getLong();
                    }
                default:
                    break;
            }
        }

        return outputPoints;
    }

    public double outputChannelPoint(int index, byte channel) {

        // method variables
        double outputPoint = 0;
        ByteBuffer debuffer;

        if (bitrate != 4) {
            sampleByteIndex = index * bytesPerSample;

            // get bytes for buffer
            for (int j = 0; j < bytesPerSample; j++, sampleByteIndex++) {
                byteBuffer[j] = sampleData[channel][sampleByteIndex];
            }
        }

        switch (bitrate) {
            case 4:
                byte point;
                int arrayIndex = index >>> 1;
                boolean nybbleOffset = (index & 0b1) == 1;
                if (nybbleOffset) {
                    point = sampleData[channel][arrayIndex];
                } else {
                    point = (byte) (sampleData[channel][arrayIndex] >> 4);
                }
                if (signed && (point & 0b1000) == 0b1000) {
                    point |= 0b11110000;
                }
                outputPoint = point;
                break;
            case 8:
                debuffer = ByteBuffer.wrap(byteBuffer);
                outputPoint = debuffer.get();
                break;
            case 16:
                debuffer = ByteBuffer.wrap(byteBuffer);
                outputPoint = debuffer.getShort();
                break;
            case 24:
                int twentyFourBitSample;

                // build int from bytes
                spliceToArray[0] = (((byteBuffer[0] >>> 7) & 1) == 1)
                        ? (byte) 0xff : (byte) 0;

                System.arraycopy(byteBuffer, 0, spliceToArray, 1,
                        bytesPerSample);

                debuffer = ByteBuffer.wrap(spliceToArray);
                twentyFourBitSample = debuffer.getInt();
                outputPoint = twentyFourBitSample;
                break;
            case 32:
                debuffer = ByteBuffer.wrap(byteBuffer);
                if (floating) {
                    outputPoint = debuffer.getFloat();
                } else {
                    outputPoint = debuffer.getInt();
                }
                break;
            case 64:
                debuffer = ByteBuffer.wrap(byteBuffer);
                if (floating) {
                    outputPoint = debuffer.getDouble();
                } else {
                    outputPoint = debuffer.getLong();
                }
            default:
                break;
        }

        return outputPoint;
    }

    public double outputNormalisedChannelPoint(int index, byte channel) {
        double point = outputChannelPoint(index, channel);

        if (!floating) {
            point *= inverseBitAmplitude;
        }

        return point;
    }

    public long outputChannelIntegerPoint(int index, byte channel) {

        // method variables
        long outputPoint = 0;
        ByteBuffer debuffer;

        if (bitrate != 4) {
            sampleByteIndex = index * bytesPerSample;

            // get bytes for buffer
            for (int j = 0; j < bytesPerSample; j++, sampleByteIndex++) {
                byteBuffer[j] = sampleData[channel][sampleByteIndex];
            }
        }

        switch (bitrate) {
            case 4:
                byte point;
                int arrayIndex = index >>> 1;
                boolean nybbleOffset = (index & 0b1) == 1;
                if (nybbleOffset) {
                    point = sampleData[channel][arrayIndex];
                } else {
                    point = (byte) (sampleData[channel][arrayIndex] >> 4);
                }
                if (signed && (point & 0b1000) == 0b1000) {
                    point |= 0b11110000;
                }
                outputPoint = point;
                break;
            case 8:
                debuffer = ByteBuffer.wrap(byteBuffer);
                outputPoint = debuffer.get();
                break;
            case 16:
                debuffer = ByteBuffer.wrap(byteBuffer);
                outputPoint = debuffer.getShort();
                break;
            case 24:
                int twentyFourBitSample;

                // build int from bytes
                spliceToArray[0] = (((byteBuffer[0] >>> 7) & 1) == 1)
                        ? (byte) 0xff : (byte) 0;

                System.arraycopy(byteBuffer, 0, spliceToArray, 1,
                        bytesPerSample);

                debuffer = ByteBuffer.wrap(spliceToArray);
                twentyFourBitSample = debuffer.getInt();
                outputPoint = twentyFourBitSample;
                break;
            case 32:
                debuffer = ByteBuffer.wrap(byteBuffer);
                if (floating) {
                    outputPoint = (int) (debuffer.getFloat()
                            * this.bitAmplitude - 0.5);
                } else {
                    outputPoint = debuffer.getInt();
                }
                break;
            case 64:
                debuffer = ByteBuffer.wrap(byteBuffer);
                if (floating) {
                    outputPoint = (long) (debuffer.getDouble()
                            * this.bitAmplitude - 0.5);
                } else {
                    outputPoint = debuffer.getLong();
                }
            default:
                break;
        }

        return outputPoint;
    }

    // get normalised samples at current index between 1 and -1
    public double[] outputNormalisedPoints(int index) {

        // method variables
        double[] outputPoints = new double[channels];
        ByteBuffer debuffer;

        // channel loop
        for (int i = 0; i < channels; i++) {

            if (bitrate != 4) {
                sampleByteIndex = index * bytesPerSample;

                // get bytes for buffer
                for (int j = 0; j < bytesPerSample; j++, sampleByteIndex++) {
                    byteBuffer[j] = sampleData[i][sampleByteIndex];
                }
            }

            switch (bitrate) {
                case 4:
                    byte point;
                    int arrayIndex = index >>> 1;
                    boolean nybbleOffset = (index & 0b1) == 1;
                    if (nybbleOffset) {
                        point = sampleData[i][arrayIndex];
                    } else {
                        point = (byte) (sampleData[i][arrayIndex] >> 4);
                    }
                    if (signed && (point & 0b1000) == 0b1000) {
                        point |= 0b11110000;
                    }
                    outputPoints[i] = point;
                    break;
                case 8:
                    debuffer = ByteBuffer.wrap(byteBuffer);
                    outputPoints[i] = debuffer.get();
                    break;
                case 16:
                    debuffer = ByteBuffer.wrap(byteBuffer);
                    outputPoints[i] = debuffer.getShort();
                    break;
                case 24:
                    int twentyFourBitSample;

                    // build int from bytes
                    spliceToArray[0] = (((byteBuffer[0] >>> 7) & 1) == 1)
                            ? (byte) 0xff : (byte) 0;

                    System.arraycopy(byteBuffer, 0, spliceToArray, 1,
                            bytesPerSample);

                    debuffer = ByteBuffer.wrap(spliceToArray);
                    twentyFourBitSample = debuffer.getInt();
                    outputPoints[i] = twentyFourBitSample;
                    break;
                case 32:
                    debuffer = ByteBuffer.wrap(byteBuffer);
                    if (floating) {
                        outputPoints[i] = debuffer.getFloat();
                    } else {
                        outputPoints[i] = debuffer.getInt();
                    }
                    break;
                case 64:
                    debuffer = ByteBuffer.wrap(byteBuffer);
                    if (floating) {
                        outputPoints[i] = debuffer.getDouble();
                    } else {
                        outputPoints[i] = debuffer.getLong();
                    }
                default:
                    break;
            }
        }

        for (int i = 0; i < outputPoints.length; i++) {
            outputPoints[i] *= inverseBitAmplitude;
        }

        return outputPoints;
    }

    // get normalised sampleData
    public double[][] getNormalisedSampleData() {

        double[][] sampleData = new double[sampleCount][channels];

        for (int i = 0; i < sampleCount; i++) {
            sampleData[i] = this.outputNormalisedPoints(i);
        }

        return sampleData;
    }

    public void getNormalisedSampleData(double[][] sampleData) {

        for (int i = 0; i < sampleCount; i++) {
            sampleData[i] = this.outputNormalisedPoints(i);
        }
    }

    public double[][] getUnnormalisedSampleData() {

        double[][] sampleData = new double[sampleCount][channels];

        for (int i = 0; i < sampleCount; i++) {
            sampleData[i] = this.outputPoints(i);
        }

        return sampleData;
    }

    public void getUnnormalisedSampleData(double[][] sampleData) {

        for (int i = 0; i < sampleCount; i++) {
            sampleData[i] = this.outputPoints(i);
        }
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Object clonedObject = super.clone();

        AudioSampleData cloneData = (AudioSampleData) clonedObject;

        byte[][] newSampleData;
        byte[] newByteBuffer;
        byte[] newSpliceToArray;

        newSampleData = new byte[sampleData.length][sampleData[0].length];

        for (int i = 0; i < newSampleData.length; i++) {

            System.arraycopy(sampleData[i], 0, newSampleData[i], 0,
                    sampleData[i].length);
        }

        cloneData.sampleData = newSampleData;

        newByteBuffer = new byte[bytesPerSample];

        cloneData.byteBuffer = newByteBuffer;

        if (bitrate == 24) {
            newSpliceToArray = new byte[4];
            cloneData.spliceToArray = newSpliceToArray;
        }

        return clonedObject;
    }
}
