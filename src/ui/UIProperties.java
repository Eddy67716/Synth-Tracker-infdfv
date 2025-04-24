/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalSliderUI;
import ui.custom.SynthSliderUI;

/**
 *
 * @author Edward Jenkins
 */
public class UIProperties {
    
    public static final Dimension VALUE_SPINNER_SIZE = new Dimension(45, 20);
    public static final Dimension C5_SPINNER_SIZE = new Dimension(80, 20);
    public static final Dimension DETAILS_FIELD_SIZE = new Dimension(198, 20);
    public static final Dimension LARGE_FIELD_SIZE = new Dimension(150, 20);
    public static final Dimension MEDIUM_TALL_FIELD_SIZE 
            = new Dimension(120, 30);
    public static final Dimension MEDIUM_FIELD_SIZE = new Dimension(120, 20);
    public static final Dimension SMALLER_FIELD_SIZE = new Dimension(90, 20);
    public static final Dimension SMALL_LABEL_SIZE = new Dimension(100, 22);
    public static final Dimension MEDIUM_LABEL_SIZE = new Dimension(120, 22);
    public static final Dimension NOTE_COMBO_BOX_SIZE = new Dimension(80, 25);
    public static final Insets DEF_INSETS = new Insets(5, 5, 0, 0);
    public static final Insets SINGLE_COLUMN_INSETS = new Insets(5, 5, 0, 5);
    public static final Insets CHECKBOX_INSETS = new Insets(5, 8, 0, 0);
    public static final Font DEF_FONT = new Font("Default font", 0, 12);
    public static final Font BOLD_FONT = new Font("Bold font", 1, 12);
    public static final Font ITALIC_FONT = new Font("Italic font", 2, 12);
    public static final String[] SAMPLE_NOTES = {"C_", "C#", "D_",
        "D#", "E_", "F_", "F#", "G_", "G#", "A_", "A#", "B_"};
    public static final Color DEF_BACKGROUND_COLOUR = new Color(238, 238, 238);
    public static final Color DEF_TABLE_BACKGROUND_COLOUR = Color.WHITE;
    public static final Color DEF_THUMB_COLOUR = new Color(128, 128, 128);
    public static final Color DEF_FORGROUND_COLOUR = Color.WHITE;
    public static final Color DEF_HIGHLIGHT_COLOUR = new Color(255, 0, 0);
    public static final Color DEF_WASHED_COLOUR
            = new Color((DEF_HIGHLIGHT_COLOUR.getRed() + 0xff) / 2, 
            (DEF_HIGHLIGHT_COLOUR.getGreen()+ 0xff) / 2,
            (DEF_HIGHLIGHT_COLOUR.getBlue()+ 0xff) / 2);
    public static final Color DEF_HEAVILY_WASHED_COLOUR 
            = new Color((DEF_WASHED_COLOUR.getRed() + 0xff) / 2, 
            (DEF_WASHED_COLOUR.getGreen()+ 0xff) / 2,
            (DEF_WASHED_COLOUR.getBlue()+ 0xff) / 2);
    public static final Color DEF_EXTREMELY_WASHED_COLOUR 
            = new Color((DEF_HEAVILY_WASHED_COLOUR.getRed() + 0xff) / 2, 
            (DEF_HEAVILY_WASHED_COLOUR.getGreen()+ 0xff) / 2,
            (DEF_HEAVILY_WASHED_COLOUR.getBlue()+ 0xff) / 2);
    public static final Color DEF_SHADOW_COLOUR 
            = DEF_WASHED_COLOUR.darker();
    public static final Color DEF_DARK_SHADOW_COLOUR 
            = DEF_SHADOW_COLOUR.darker();
    public static final Color DEF_WASHED_SHADOW_COLOUR
            = new Color((DEF_SHADOW_COLOUR.getRed() + 0xff) / 2, 
            (DEF_SHADOW_COLOUR.getGreen()+ 0xff) / 2,
            (DEF_SHADOW_COLOUR.getBlue()+ 0xff) / 2);
    public static final Color DEF_TEXT_COLOUR = new Color(0, 0, 0);
    public static final Color DEF_OSCILOSCOPE_COLOUR = Color.GREEN;
    public static final Color DEF_TABLE_BACKGROND_COLOUR = Color.WHITE;
    public static final Color LOW_VOLUME_COLOUR = Color.GREEN;
    public static final Color MID_VOLUME_COLOUR = Color.ORANGE;
    public static final Color HIGH_VOLUME_COLOUR = Color.RED;
    public static final Color TOOL_TIP_COLOUR = new Color(255, 233, 152);
    public static final Color NO_COLOUR = new Color(0, 0, 0, 0);
    
    public static void setUIProperties() {
        
        // set default UI colours
        // backgrounds
        UIManager.put("MenuBar.background", DEF_BACKGROUND_COLOUR);
        UIManager.put("Panel.background", DEF_BACKGROUND_COLOUR);
        UIManager.put("ToolBar.background", DEF_BACKGROUND_COLOUR);
        UIManager.put("Label.background", DEF_BACKGROUND_COLOUR);
        UIManager.put("Panel.background", DEF_BACKGROUND_COLOUR);
        UIManager.put("Slider.background", DEF_BACKGROUND_COLOUR);
        UIManager.put("RadioButton.background", DEF_BACKGROUND_COLOUR);
        UIManager.put("CheckBox.background", DEF_BACKGROUND_COLOUR);
        UIManager.put("ComboBox.background", DEF_BACKGROUND_COLOUR);
        UIManager.put("TabbedPane.background", DEF_BACKGROUND_COLOUR);
        UIManager.put("ToolTip.background", TOOL_TIP_COLOUR);
        UIManager.put("OptionPane.background", DEF_BACKGROUND_COLOUR);
        UIManager.put("Button.background", DEF_HEAVILY_WASHED_COLOUR);
        UIManager.put("ToggleButton.background", DEF_HEAVILY_WASHED_COLOUR);
        UIManager.put("ScrollBar.background", DEF_BACKGROUND_COLOUR);
        
        // foregrounds
        UIManager.put("MenuBar.foreground", DEF_TEXT_COLOUR);
        UIManager.put("Panel.foreground", DEF_TEXT_COLOUR);
        UIManager.put("ToolBar.foreground", DEF_TEXT_COLOUR);
        UIManager.put("Label.foreground", DEF_TEXT_COLOUR);
        UIManager.put("TextField.foreground", DEF_TEXT_COLOUR);
        UIManager.put("Panel.foreground", DEF_TEXT_COLOUR);
        UIManager.put("Slider.foreground", DEF_HEAVILY_WASHED_COLOUR);
        UIManager.put("RadioButton.foreground", DEF_TEXT_COLOUR);
        UIManager.put("CheckBox.foreground", DEF_TEXT_COLOUR);
        UIManager.put("ComboBox.foreground", DEF_TEXT_COLOUR);
        UIManager.put("TabbedPane.foreground", DEF_TEXT_COLOUR);
        UIManager.put("ToolTip.foreground", DEF_TEXT_COLOUR);
        UIManager.put("OptionPane.foreground", DEF_TEXT_COLOUR);
        UIManager.put("Button.foreground", DEF_TEXT_COLOUR);
        UIManager.put("ToggleButton.foreground", DEF_TEXT_COLOUR);
        UIManager.put("ScrollBar.foreground", DEF_TEXT_COLOUR);
        
        // texts
        
        
        // focus
        UIManager.put("ToolBar.highlight", DEF_HEAVILY_WASHED_COLOUR);
        UIManager.put("Slider.hilight", DEF_HEAVILY_WASHED_COLOUR);
        UIManager.put("Slider.focus", DEF_HEAVILY_WASHED_COLOUR);
        UIManager.put("Slider.tickColor", DEF_HEAVILY_WASHED_COLOUR);
        UIManager.put("Button.hilight", DEF_HEAVILY_WASHED_COLOUR);
        UIManager.put("TabbedPane.focus", DEF_WASHED_COLOUR);
        
        // shadow
        UIManager.put("ToolBar.shadow", DEF_SHADOW_COLOUR);
        UIManager.put("ToolBar.darkShadow", DEF_DARK_SHADOW_COLOUR);
        UIManager.put("Slider.shadow", DEF_SHADOW_COLOUR);
        UIManager.put("Slider.darkShadow", DEF_DARK_SHADOW_COLOUR);
        UIManager.put("RadioButton.shadow", DEF_DARK_SHADOW_COLOUR);
        UIManager.put("RadioButton.darkShadow", DEF_DARK_SHADOW_COLOUR);
        UIManager.put("Button.shadow", DEF_SHADOW_COLOUR);
        UIManager.put("Button.darkShadow", DEF_SHADOW_COLOUR);
        UIManager.put("TabbedPane.shadow", DEF_SHADOW_COLOUR);
        UIManager.put("TabbedPane.darkShadow", DEF_DARK_SHADOW_COLOUR);
        UIManager.put("ToggleButton.shadow", DEF_DARK_SHADOW_COLOUR);
        UIManager.put("ToggleButton.darkShadow", DEF_DARK_SHADOW_COLOUR);
        
        // selected
        UIManager.put("MenuBar.selectionBackground", DEF_HEAVILY_WASHED_COLOUR);
        UIManager.put("MenuItem.selectionBackground", DEF_HEAVILY_WASHED_COLOUR);
        UIManager.put("RadioButton.select", DEF_WASHED_COLOUR);
        UIManager.put("CheckBox.select", DEF_WASHED_COLOUR);
        UIManager.put("ComboBox.selectionBackground", DEF_HEAVILY_WASHED_COLOUR);
        UIManager.put("List.selectionBackground", DEF_HEAVILY_WASHED_COLOUR);
        UIManager.put("Button.select", DEF_WASHED_COLOUR);
        UIManager.put("ToggleButton.select", DEF_WASHED_COLOUR);
        UIManager.put("TabbedPane.selected", DEF_HEAVILY_WASHED_COLOUR);
        UIManager.put("Slider.trackHighlight", DEF_WASHED_COLOUR);
        UIManager.put("Slider.thumbHighlight", DEF_HEAVILY_WASHED_COLOUR);
        UIManager.put("ScrollBar.trackHighlight", DEF_BACKGROUND_COLOUR);
        UIManager.put("ScrollBar.thumbHighlight", DEF_BACKGROUND_COLOUR);
        
        // borders
        UIManager.put("TabbedPane.tabRunOverlay", DEF_HEAVILY_WASHED_COLOUR);
        
        // other
        UIManager.put("Slider.altTrackColor", DEF_WASHED_COLOUR);
        UIManager.put("Slider.track", DEF_WASHED_COLOUR);
        UIManager.put("Slider.thumb", DEF_THUMB_COLOUR);
        UIManager.put("ScrollBar.track", DEF_BACKGROUND_COLOUR);
        UIManager.put("ScrollBar.thumb", DEF_THUMB_COLOUR);
        
        // set component UI
        UIManager.put("SliderUI", "ui.custom.SynthSliderUI");
    }
}
