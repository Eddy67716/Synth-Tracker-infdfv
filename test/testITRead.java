
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
        ITFile reader = new ITFile("src/Hero2.it");

        try {
            // check if read
            boolean isRead = reader.read();

            if (isRead) {
                
                //reader.setFilePath("src/Hero2.it");
                
                //reader.write();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
