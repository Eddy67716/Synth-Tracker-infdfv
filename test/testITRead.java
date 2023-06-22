
import module.IModuleFile;
import module.IPattern;
import module.it.format.ITHeader;
import module.it.format.ITInstrument;
import module.it.format.ITPattern;
import module.it.format.ITFile;
import module.it.format.ITSampleHeader;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;
import module.IAudioSample;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Edward Jenkins
 */
public class testITRead {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        // create ITReader
        ITFile reader = new ITFile("src/Hero.it");

        try {
            // check if read
            boolean isRead = reader.read();

            if (isRead) {

                /*// header
                ITHeader header = reader.getHeader();

                // to string
                System.out.print(header.toString());
                
                
                // instruments
                ITInstrument[] instruments = reader.getInstruments();

                for (ITInstrument instrument : instruments) {

                    //System.out.println(instrument.toString());
                }

                // samples
                List<ITSampleHeader> samples = reader.getSamples();

                for (ITSampleHeader sample : samples) {

                    System.out.println(sample.toString());
                }*/
                
                
                // patterns
                List<IPattern> patterns = reader.getIPatterns();

                for (IPattern pattern : patterns) {
                    
                    ITPattern itPattern = (ITPattern)pattern;
                    
                    System.out.print(itPattern);

                    byte[] packedData = itPattern.pack();
                    
                    itPattern.unpack();
                    
                    System.out.print(itPattern);
                }
                
                System.out.println("Total Number of channels: " + reader.getChannelsCount());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
