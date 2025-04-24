/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.view.details;

import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;
import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import static ui.UIProperties.BOLD_FONT;

/**
 *
 * @author Edward Jenkins
 */
public class EditHistoryPanel extends JPanel{
    
    // instance variables
    private int modType;
    private Border messageBorder;
    private JScrollPane editHistoryPane;
    private JTextArea editHistoryArea;
    
    public EditHistoryPanel(int modType) {
        this.modType = modType;
        init();
    }

    public JTextArea getEditHistoryArea() {
        return editHistoryArea;
    }
    
    private void init() {
        
        setLayout(new BorderLayout());
        
        // set the border
        messageBorder
                = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);

        // set the border title
        messageBorder
                = BorderFactory.createTitledBorder(messageBorder,
                        "Past edits", 0, 0, BOLD_FONT);

        // set options border
        setBorder(messageBorder);
        
        editHistoryArea = new JTextArea();
        editHistoryArea.setEditable(false);
        
        editHistoryPane = new JScrollPane(editHistoryArea, 
                VERTICAL_SCROLLBAR_AS_NEEDED, HORIZONTAL_SCROLLBAR_AS_NEEDED);
        
        add(editHistoryPane, BorderLayout.CENTER);
    }
    
}
