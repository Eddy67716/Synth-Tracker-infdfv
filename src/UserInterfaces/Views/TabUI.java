/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UserInterfaces.Views;

import Module.IInstrument;
import Module.IPattern;
import static UserInterfaces.UIProperties.BOLD_FONT;
import static UserInterfaces.UIProperties.DEF_INSETS;
import static UserInterfaces.UIProperties.SINGLE_COLUMN_INSETS;
import UserInterfaces.Controllers.TabController;
import UserInterfaces.Views.Samples.SampleUI;
import UserInterfaces.viewModels.LoadViewModel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JToggleButton;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import Module.IAudioSample;

/**
 *
 * @author Edward Jenkins
 */
public class TabUI extends JPanel {

    // instance variables
    private final JSplitPane contentsDataSplitPane;
    private final JPanel modDataPane;
    private final JTabbedPane modDataInterface;
    private final JPanel detailsPanel;
    private final JPanel patternPanel;
    private final JPanel samplePanel;
    private final JPanel instrumentPanel;
    private final JPanel playChartPanel;
    private final JPanel fileContentsScrollPanel;
    private final JScrollPane fileContentsPane;
    private final JPanel fileContentsPanel;
    private final JToggleButton patternToggleButton;
    private final JList<String> patternList;
    private final JToggleButton sampleToggleButton;
    private final JList<String> sampleList;
    private final JToggleButton instrumentToggleButton;
    private final JList<String> instrumentList;

    // constructor
    public TabUI() {

        // components
        contentsDataSplitPane = new JSplitPane();
        modDataPane = new JPanel();
        modDataInterface = new JTabbedPane();
        instrumentPanel = new JPanel();
        detailsPanel = new JPanel();
        patternPanel= new JPanel();
        samplePanel = new JPanel();
        playChartPanel = new JPanel();
        fileContentsScrollPanel = new JPanel();
        fileContentsPane = new JScrollPane();
        fileContentsPanel = new JPanel();
        patternToggleButton = new JToggleButton();
        patternList = new JList<>();
        sampleToggleButton = new JToggleButton();
        sampleList = new JList<>();
        instrumentToggleButton = new JToggleButton();
        instrumentList = new JList<>();

        init();
    }

    // getters
    public JList<String> getPatternList() {
        return patternList;
    }

    public JList<String> getSampleList() {
        return sampleList;
    }

    public JList<String> getInstrumentList() {
        return instrumentList;
    }
    
    private void init() {

        contentsDataSplitPane.setDividerLocation(-4);
        
        BorderLayout detailsInterfaceLayout = new BorderLayout();
        detailsPanel.setLayout(detailsInterfaceLayout);

        modDataInterface.addTab("Details", detailsPanel);

        BorderLayout patternInterfaceLayout = new BorderLayout();
        patternPanel.setLayout(patternInterfaceLayout);

        modDataInterface.addTab("Patterns", patternPanel);

        BorderLayout sampleInterfaceLayout = new BorderLayout();
        samplePanel.setLayout(sampleInterfaceLayout);

        modDataInterface.addTab("Samples", samplePanel);

        BorderLayout instrumentInterfaceLayout = new BorderLayout();
        instrumentPanel.setLayout(instrumentInterfaceLayout);

        modDataInterface.addTab("Instruments", instrumentPanel);

        BorderLayout playChartInterfaceLayout = new BorderLayout();
        playChartPanel.setLayout(playChartInterfaceLayout);

        modDataInterface.addTab("Play chart", playChartPanel);

        BorderLayout modDataPaneLayout = new BorderLayout();
        modDataPane.setLayout(modDataPaneLayout);
        modDataPane.add(modDataInterface);

        contentsDataSplitPane.setRightComponent(modDataPane);

        patternToggleButton.setText("Patterns");
        patternToggleButton.setMaximumSize(new java.awt.Dimension(1000, 23));
        patternToggleButton.setMinimumSize(new java.awt.Dimension(140, 23));
        patternToggleButton.setPreferredSize(new java.awt.Dimension(140, 23));

        patternList.setMaximumSize(new java.awt.Dimension(1000, 80));
        patternList.setMinimumSize(new java.awt.Dimension(140, 80));
        patternList.setPreferredSize(new java.awt.Dimension(140, 80));

        sampleToggleButton.setText("Samples");
        sampleToggleButton.setMaximumSize(new java.awt.Dimension(1000, 23));
        sampleToggleButton.setMinimumSize(new java.awt.Dimension(140, 23));
        sampleToggleButton.setPreferredSize(new java.awt.Dimension(140, 23));

        sampleList.setMaximumSize(new java.awt.Dimension(1000, 80));
        sampleList.setMinimumSize(new java.awt.Dimension(140, 80));
        sampleList.setPreferredSize(new java.awt.Dimension(140, 80));
        sampleList.setVisibleRowCount(256);

        instrumentToggleButton.setText("Instrument");
        instrumentToggleButton.setMaximumSize(new java.awt.Dimension(1000, 23));
        instrumentToggleButton.setMinimumSize(new java.awt.Dimension(140, 23));
        instrumentToggleButton.setPreferredSize(new java.awt.Dimension(140, 23));

        instrumentList.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        instrumentList.setMaximumSize(new java.awt.Dimension(1000, 80));
        instrumentList.setMinimumSize(new java.awt.Dimension(140, 80));
        instrumentList.setPreferredSize(new java.awt.Dimension(140, 80));

        GridBagLayout fileContentsPanelLayout = new GridBagLayout();
        
        fileContentsPanel.setLayout(fileContentsPanelLayout);
        
        GridBagConstraints fileContentsGBC = new GridBagConstraints();
        
        fileContentsGBC.gridy = 0;
        fileContentsGBC.weightx = 1;
        fileContentsGBC.insets = new Insets(10, 10, 0, 10);
        fileContentsGBC.anchor = GridBagConstraints.NORTHWEST;
        fileContentsGBC.fill = GridBagConstraints.HORIZONTAL;
        fileContentsGBC.gridwidth = GridBagConstraints.REMAINDER;
        fileContentsPanel.add(patternToggleButton, fileContentsGBC);

        fileContentsGBC.gridy++;
        fileContentsGBC.weightx = 1;
        fileContentsGBC.gridwidth = GridBagConstraints.REMAINDER;
        patternList.setVisible(false);
        fileContentsPanel.add(patternList, fileContentsGBC);

        fileContentsGBC.gridy++;
        fileContentsGBC.weightx = 1;
        fileContentsGBC.gridwidth = GridBagConstraints.REMAINDER;
        fileContentsPanel.add(sampleToggleButton, fileContentsGBC);

        fileContentsGBC.gridy++;
        fileContentsGBC.weightx = 1;
        fileContentsGBC.gridwidth = GridBagConstraints.REMAINDER;
        sampleList.setVisible(false);
        fileContentsPanel.add(sampleList, fileContentsGBC);

        fileContentsGBC.gridy++;
        fileContentsGBC.weightx = 1;
        fileContentsGBC.gridwidth = GridBagConstraints.REMAINDER;
        fileContentsPanel.add(instrumentToggleButton, fileContentsGBC);

        fileContentsGBC.gridy++;
        fileContentsGBC.weightx = 1;
        fileContentsGBC.gridwidth = GridBagConstraints.REMAINDER;
        instrumentList.setVisible(false);
        fileContentsPanel.add(instrumentList, fileContentsGBC);
        
        // add trailing JPanel
        fileContentsGBC.gridy++;
        fileContentsGBC.weightx = 1;
        fileContentsGBC.gridwidth = GridBagConstraints.REMAINDER;
        fileContentsGBC.weighty = 1;
        fileContentsPanel.add(new JPanel(), fileContentsGBC);

        // set fileContentsPane
        fileContentsPane.setViewportView(fileContentsPanel);

        // set scroll panel
        BorderLayout fileContentsScrollPanelLayout = new BorderLayout();
        fileContentsScrollPanel.setLayout(fileContentsScrollPanelLayout);
        fileContentsScrollPanel.add(fileContentsPane, BorderLayout.CENTER);

        // add scroll panel to split plane
        contentsDataSplitPane.setLeftComponent(fileContentsScrollPanel);
        
        // set list data
        

        // set main panel (this)
        BorderLayout mainPanelLayout = new BorderLayout();
        this.setLayout(mainPanelLayout);
        this.add(contentsDataSplitPane, BorderLayout.CENTER);
    }
    
    // action listeners
    public void addPatternToggleActionListener(ActionListener actionPerformed) {
        patternToggleButton.addActionListener(actionPerformed);
    }
    
    public void addSampleToggleActionListener(ActionListener actionPerformed) {
        sampleToggleButton.addActionListener(actionPerformed);
    }
    
    public void addInstrumentToggleActionListener(ActionListener actionPerformed) {
        instrumentToggleButton.addActionListener(actionPerformed);
    }
    
    public void addSampleInterface(SampleUI sampleUI) {
        samplePanel.add(sampleUI);
    }
}
