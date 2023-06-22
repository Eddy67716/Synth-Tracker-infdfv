/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package module.it.format;

/**
 *
 * @author Edward Jenkins
 */
public class ChannelNames {
    
    // instance variables
    private String[] channelNames;
    
    // constructor
    public ChannelNames(String[] channelNames) {
        this.channelNames = channelNames;
    }
    
    // getChannelNames
    public String[] getChannelNames() {
        return this.channelNames;
    }
    
    public String getChannelNameAtChannel(int channelIndex) {
        
        for (int i = 0; i < channelNames.length; i++) {
             
            // return when index is same as channelIndex
            if( i == channelIndex) {
                return channelNames[i];
            }
        }
        return "empty";
    }
    
    // To Stirng method
    @Override
    public String toString() {
        
        // string builder
        StringBuilder aStringBuilder = new StringBuilder();
        
        // loop through strings
        for (int i = 0; i < channelNames.length; i++) {
            
            aStringBuilder.append("\nChannel ");
            aStringBuilder.append(i + 1);
            aStringBuilder.append(": ");
            aStringBuilder.append(channelNames[i]);
        }
        
        return aStringBuilder.toString();
    }
    
}
