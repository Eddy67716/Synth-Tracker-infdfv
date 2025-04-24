/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.view.pattens;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JToolBar;
import javax.swing.SpinnerNumberModel;
import lang.LanguageHandler;
import module.IPattern;
import static ui.UIProperties.DEF_INSETS;
import static ui.UIProperties.VALUE_SPINNER_SIZE;

/**
 *
 * @author Edward Jenkins
 */
public class PatternUI extends JPanel {
    
    // instance variables
    private LanguageHandler languageHandler;
    private int modType;
    private List<IPattern> patterns;
    private short[] orders;
    private short selectedOrder;
    private IPattern selectedPattern;
    // toolbar for pannel
    private GridBagLayout patternToolBarLayout;
    private GridBagConstraints patternTBC;
    private JToolBar patternToolBar;
    private JButton newPatternButton;
    private JButton deleteButton;
    private JSeparator patternPlaySeparator;
    private JButton playPatternButton;
    private JButton playPatternRow;
    private JButton stopPatternButton;
    private JSeparator playOptionsSeparator;
    private JButton patternOptions;
    // other windows
    private JScrollPane orderPane;
    private OrderView orderView;
    private PatternPanel paternPanel;
    
    // constructor
    public PatternUI(List<IPattern> patterns, short[] orders, int modType,
            LanguageHandler languageHandler) {
        this.languageHandler = languageHandler;
        this.patterns = patterns;
        this.orders = orders;
        this.modType = modType;
        init();
    }
    
    // getters
    public int getModType() {
        return modType;
    }

    public short getSelectedOrder() {
        return selectedOrder;
    }

    public IPattern getSelectedPattern() {
        return selectedPattern;
    }

    public JToolBar getPatternToolBar() {
        return patternToolBar;
    }

    public JButton getNewPatternButton() {
        return newPatternButton;
    }

    public JButton getDeleteButton() {
        return deleteButton;
    }

    public JButton getPlayPatternButton() {
        return playPatternButton;
    }

    public JButton getPlayPatternRow() {
        return playPatternRow;
    }

    public JButton getStopPatternButton() {
        return stopPatternButton;
    }

    public JButton getPatternOptions() {
        return patternOptions;
    }

    public PatternPanel getPaternPanel() {
        return paternPanel;
    }
    
    // setter
    public void setSelectedPattern(IPattern pattern) {
        this.selectedPattern = pattern;
    }
    
    public void init() {
        
        // set layout
        setLayout(new BorderLayout());
        
        // toolbar layout setup
        patternToolBarLayout = new GridBagLayout();
        patternTBC = new GridBagConstraints();
        
        // toolbar
        patternToolBar = new JToolBar();
        patternToolBar.setRollover(true);
        patternToolBar.setFloatable(false);
        patternToolBar.setLayout(patternToolBarLayout);
        
        // patternTBC
        patternTBC.anchor = GridBagConstraints.NORTHWEST;
        patternTBC.gridx = 0;
        patternTBC.gridy = 0;
        patternTBC.insets = DEF_INSETS;
        patternTBC.weightx = 0.0;
        patternTBC.gridwidth = 1;
        
        // new pattern
        newPatternButton = new JButton();
        newPatternButton.setIcon(new javax.swing.ImageIcon(getClass()
                .getResource("/ui/assets/New.png")));
        newPatternButton.setToolTipText("New pattern");
        newPatternButton.setFocusable(false);
        newPatternButton
                .setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        newPatternButton
                .setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        patternToolBar.add(newPatternButton, patternTBC);
        
        patternTBC.gridx++;
        patternTBC.weightx = 0.0;
        patternTBC.gridwidth = 1;
        
        // delete pattern
        deleteButton = new JButton();
        deleteButton.setIcon(new javax.swing.ImageIcon(getClass()
                .getResource("/ui/assets/Delete.png")));
        deleteButton.setToolTipText("Delete pattern");
        deleteButton.setFocusable(false);
        deleteButton
                .setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        deleteButton
                .setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        patternToolBar.add(deleteButton, patternTBC);
        
        patternTBC.gridx++;
        patternTBC.weightx = 0.0;
        patternTBC.gridwidth = 1;
        //patternTBC.anchor = GridBagConstraints.CENTER;
        
        // separator
        patternPlaySeparator = new JSeparator();
        patternToolBar.add(patternPlaySeparator, patternTBC);
        
        patternTBC.gridx++;
        patternTBC.weightx = 0.0;
        patternTBC.gridwidth = 1;
        //patternTBC.anchor = GridBagConstraints.NORTHWEST;
        
        // play
        playPatternButton = new JButton();
        playPatternButton.setIcon(new javax.swing.ImageIcon(getClass()
                .getResource("/ui/assets/Play Pause.png")));
        playPatternButton.setToolTipText("Play/Pause pattern");
        playPatternButton.setFocusable(false);
        playPatternButton
                .setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        playPatternButton
                .setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        patternToolBar.add(playPatternButton, patternTBC);
        
        patternTBC.gridx++;
        patternTBC.weightx = 0.0;
        patternTBC.gridwidth = 1;
        
        // play patternRow
        playPatternRow = new JButton();
        playPatternRow.setIcon(new javax.swing.ImageIcon(getClass()
                .getResource("/ui/assets/Play Row.png")));
        playPatternRow.setToolTipText("Play pattern row");
        playPatternRow.setFocusable(false);
        playPatternRow
                .setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        playPatternRow
                .setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        patternToolBar.add(playPatternRow, patternTBC);
        
        patternTBC.gridx++;
        patternTBC.weightx = 0.0;
        patternTBC.gridwidth = 1;
        
        // stop
        stopPatternButton = new JButton();
        stopPatternButton.setIcon(new javax.swing.ImageIcon(getClass()
                .getResource("/ui/assets/Stop.png")));
        stopPatternButton.setToolTipText("Stop pattern");
        stopPatternButton.setFocusable(false);
        stopPatternButton
                .setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        stopPatternButton
                .setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        patternToolBar.add(stopPatternButton, patternTBC);
        
        patternTBC.gridx++;
        patternTBC.weightx = 0.0;
        patternTBC.gridwidth = 1;
        
        // separator
        playOptionsSeparator = new JSeparator();
        patternToolBar.add(playOptionsSeparator, patternTBC);
        
        // options
        
        patternTBC.gridx++;
        patternTBC.weightx = 1.0;
        patternTBC.gridwidth = GridBagConstraints.REMAINDER;
        
        patternToolBar.add(new JPanel(), patternTBC);
        
        // add toolbar
        add(patternToolBar, BorderLayout.NORTH);
        
        // order view
        orderView = new OrderView(modType, orders);
        
        // scroll pane with pattern orders
        orderPane = new JScrollPane(orderView, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        orderPane.setMinimumSize(new Dimension(100, 20));
        
        add(orderPane, BorderLayout.WEST);
        
        // pattern view
        paternPanel = new PatternPanel();
        add(paternPanel, BorderLayout.CENTER);
        
    }
}
