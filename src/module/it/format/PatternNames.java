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
public class PatternNames {
    
    // instance variables
    private String[] patternNames;
    
    // constructor
    public PatternNames(String[] patternNames) {
        this.patternNames = patternNames;
    }
    
    // getters
    public String[] getPatternNameStuff() {
        return this.patternNames;
    }
    
    // get certain pattern name
    public String getPatternNameAtChannel(int patternIndex) {
        
        for (int i = 0; i < patternNames.length; i++) {
             
            // return when index is same as channelIndex
            if( i == patternIndex) {
                return patternNames[i];
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
        for (int i = 0; i < patternNames.length; i++) {
            
            aStringBuilder.append("\nPattern ");
            aStringBuilder.append(i + 1);
            aStringBuilder.append(": ");
            aStringBuilder.append(patternNames[i]);
        }
        
        return aStringBuilder.toString();
    }
}
