
import module.IModuleFile;
import module.IPattern;
import module.it.format.ItHeader;
import module.it.format.ItInstrument;
import module.it.format.ItPattern;
import module.it.format.ItFile;
import module.it.format.ItSampleHeader;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;

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
        ItFile reader = new ItFile("src/Hero.it");
        
        try {
            // check if read
            boolean isRead = reader.read();
            
            System.out.println(reader.getPatterns());


            if (isRead) {
                
                //reader.setFilePath("src/Hero 2.it");
                
                //reader.write();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
