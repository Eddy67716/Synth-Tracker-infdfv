/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sound.midi;

import java.util.List;
import module.IInstrument;
import module.IModuleFile;

/**
 *
 * @author eddy6
 */
public class InstrumentTrackDataCache {

    private TrackData[] trackData;

    public InstrumentTrackDataCache(IModuleFile moduleFile,
            boolean multiPortFile) {
        List<IInstrument> instrumentList = moduleFile.getIInstruments();
        trackData = new TrackData[instrumentList.size()];
        if (multiPortFile) {
            multiPortMap(instrumentList);
        } else {
            singlePortMap(moduleFile);
        }
    }
    
    // getter
    public TrackData[] getTrackData() {
        return trackData;
    }
    
    private void singlePortMap(IModuleFile moduleFile) {
        
    }

    private void multiPortMap(List<IInstrument> instrumentList) {
        byte[] channelPorts = new byte[16];
        byte currentChannel = 0;
        byte currentPort = 0;
        IInstrument currentInstrument;
        byte trackChannel = 0;
        byte trackPort = 0;

        // map the ports and channels
        for (int i = 0; i < trackData.length; i++) {
            currentInstrument = instrumentList.get(i);
            if (currentInstrument.getMidiChannel() - 1 >= 0
                    && currentInstrument.getMidiChannel() < 16) {
                trackChannel = (byte) (currentInstrument.getMidiChannel() - 1);
                if (trackChannel != 9) {
                    trackPort = channelPorts[trackChannel];
                    channelPorts[trackChannel]++;
                }
            } else {
                trackChannel = currentChannel;
                trackPort = currentPort;
                // check for holes in port count and fill
                if (trackChannel
                        != 9 && channelPorts[trackChannel] > trackPort) {
                    while (channelPorts[trackChannel] > trackPort) {
                        currentChannel++;
                        if (currentChannel == 9) {
                            currentChannel++;
                        }
                        if (currentChannel == 16) {
                            currentChannel = 0;
                            currentPort++;
                        }
                        trackChannel = currentChannel;
                        trackPort = currentPort;
                    }
                }
                // increment to next channel
                currentChannel++;
                // increment again if a drum channel
                if (currentChannel == 9) {
                    currentChannel++;
                }
                // increment port and reset channel to 0 is at last channel
                if (currentChannel == 16) {
                    currentChannel = 0;
                    currentPort++;
                }
            }

            trackData[i] = new TrackData(currentInstrument, trackPort,
                    trackChannel);
        }
    }
}
