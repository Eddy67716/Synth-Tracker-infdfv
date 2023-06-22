/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.view.instruments;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import static javax.swing.ScrollPaneConstants.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import static ui.UIProperties.BOLD_FONT;
import static ui.UIProperties.DEF_INSETS;
import static ui.UIProperties.SAMPLE_NOTES;

/**
 *
 * @author Edward Jenkins
 */
public class NoteMapView extends JPanel {

    // constants
    public static final byte NOTE_COUNT = 120;
    public static final byte STR_NOTE_COUNT = 121;
    public static final byte XM_NOTE_COUNT = 96;
    public static final String[] COLUMNS = {
        "Note", "Sample", "Sample note"
    };

    // instance variables
    private int modType;
    private GridBagLayout noteMapLayout;
    private GridBagConstraints nmc;
    private Border noteMapBorder;
    private JLabel noteMapLabel;
    private JScrollPane mapScroll;
    private JTable noteMapTable;

    // constructor
    public NoteMapView(int modType) {
        this.modType = modType;
        init();
    }

    // setters
    public void setNoteMap(short[][] noteSampleKeyboardTable) {
        NoteMapTableModel tableToUpdate 
                = (NoteMapTableModel)noteMapTable.getModel();
        
        tableToUpdate.setNoteMap(noteSampleKeyboardTable);
    }
    
    public void init() {

        // set the border
        noteMapBorder
                = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);

        // set the border title
        noteMapBorder
                = BorderFactory.createTitledBorder(noteMapBorder,
                        "Note map", 0, 0, BOLD_FONT);
        
        // set options border
        setBorder(noteMapBorder);
        
        this.setLayout(new BorderLayout());

        // set up table
        int rows;
        int octave;
        int columns;

        switch (modType) {
            case 6:
                rows = STR_NOTE_COUNT;
                octave = 0;
                columns = 3;
                break;
            case 5:
            case 4:
                rows = NOTE_COUNT;
                octave = 0;
                columns = 3;
                break;
            case 3:
                rows = XM_NOTE_COUNT;
                octave = 1;
                columns = 2;
                break;
            default:
                rows = 1;
                columns = 2;
                octave = 0;
        }

        String[] noteNames = new String[rows];

        // set up notes
        for (int i = 0; i < rows;) {

            // iterate through all 12 notes per octave
            for (String note : SAMPLE_NOTES) {

                // add note to note colum
                noteNames[i] = note + octave;
                i++;
            }
            octave++;
        }

        // set table
        noteMapTable = new JTable(new NoteMapTableModel(COLUMNS,
                noteNames, new short[2][rows]));
        
        // set table parameters
        noteMapTable.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
        noteMapTable.getColumnModel().getColumn(0).setMaxWidth(30);
        noteMapTable.getColumnModel().getColumn(1).setMaxWidth(50);
        noteMapTable.getColumnModel().getColumn(2).setMaxWidth(60);
        
        noteMapTable.setPreferredScrollableViewportSize(noteMapTable.getPreferredSize());

        mapScroll = new JScrollPane(noteMapTable, VERTICAL_SCROLLBAR_AS_NEEDED,
                HORIZONTAL_SCROLLBAR_AS_NEEDED);

        this.setPreferredSize(new Dimension(170, 200));
        
        add(mapScroll);
    }
}
