/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package module;

/**
 *
 * @author Edward Jenkins
 */
public interface IChannel {
    
    // getters
    public String getChannelName();

    public short getChannelVolume();

    public short getChannelPan();

    public boolean isMuted();

    public boolean isSurroundChannel();
    
    // setters
    public void setChannelName(String channelName);
    
    public void setChannelVolume(short channelVolume);

    public void setChannelPan(short channelPan);

    public void setMuted(boolean muted);

    public void setSurroundChannel(boolean surroundChannel);
    
}
