/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package module.it.format;

import module.IChannel;

/**
 *
 * @author Edward Jenkins
 */
public class ItChannel implements IChannel {
    
    // constants
    public static final byte SURROUND_VALUE = (byte) 100;
    
    // instance variables
    private String channelName;
    private short channelVolume;
    private short channelPan;
    private boolean muted;
    private boolean surroundChannel;
    
    // constructor
    public ItChannel(String channelName, byte volumeValue, short panValue) {
        this.channelName = channelName;
        this.channelVolume = volumeValue;
        if (panValue < 0) {
            muted = true;
        } else if (panValue == SURROUND_VALUE) {
            surroundChannel = true;
        } else {
            channelPan = panValue;
        }
    }
    
    // getters
    @Override
    public String getChannelName() {
        return channelName;
    }

    @Override
    public short getChannelVolume() {
        return channelVolume;
    }

    @Override
    public short getChannelPan() {
        return channelPan;
    }

    @Override
    public boolean isMuted() {
        return muted;
    }

    @Override
    public boolean isSurroundChannel() {
        return surroundChannel;
    }
    
    // setters
    @Override
    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    @Override
    public void setChannelVolume(short channelVolume) {
        this.channelVolume = channelVolume;
    }

    @Override
    public void setChannelPan(short channelPan) {
        this.channelPan = channelPan;
    }

    @Override
    public void setMuted(boolean muted) {
        this.muted = muted;
    }

    @Override
    public void setSurroundChannel(boolean surroundChannel) {
        this.surroundChannel = surroundChannel;
    }
}
