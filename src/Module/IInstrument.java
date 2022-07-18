/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Module;

/**
 *
 * @author Edward Jenkins
 */
public interface IInstrument {
    
    public boolean read() throws Exception;
    
    public boolean write() throws Exception;
    
    public String getInstrumentName();
    
}
