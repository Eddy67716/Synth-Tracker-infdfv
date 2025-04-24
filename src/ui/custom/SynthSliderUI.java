/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.custom;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JComponent;
import javax.swing.JSlider;
import javax.swing.UIManager;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.metal.MetalSliderUI;

/**
 *
 * @author Edward Jenkins
 */
public class SynthSliderUI extends MetalSliderUI {

    /**
     * Constructs a {@code SynthSliderUI} instance.
     *
     * @param c a component
     * @return a {@code SynthSliderUI} instance
     */
    public static ComponentUI createUI(JComponent c) {
        return new SynthSliderUI();
    }

    public SynthSliderUI() {
        super();
    }

    @Override
    public void paintTrack(Graphics g) {

        boolean leftToRight = true;

        g.translate(trackRect.x, trackRect.y);

        int trackLeft = 0;
        int trackTop = 0;
        int trackRight;
        int trackBottom;

        // Draw the track
        if (slider.getOrientation() == JSlider.HORIZONTAL) {
            trackBottom = (trackRect.height - 1) - getThumbOverhang();
            trackTop = trackBottom - (getTrackWidth() - 1);
            trackRight = trackRect.width - 1;
        } else {
            if (leftToRight) {
                trackLeft = (trackRect.width - getThumbOverhang())
                        - getTrackWidth();
                trackRight = (trackRect.width - getThumbOverhang()) - 1;
            } else {
                trackLeft = getThumbOverhang();
                trackRight = getThumbOverhang() + getTrackWidth() - 1;
            }
            trackBottom = trackRect.height - 1;
        }

        if (slider.isEnabled()) {
            g.setColor((Color) UIManager.get("Slider.darkShadow"));
            g.drawRect(trackLeft, trackTop,
                    (trackRight - trackLeft) - 1, (trackBottom - trackTop) - 1);

            g.setColor((Color) UIManager.get("Slider.hilight"));
            g.drawLine(trackLeft + 1, trackBottom, trackRight, trackBottom);
            g.drawLine(trackRight, trackTop + 1, trackRight, trackBottom);

            g.setColor((Color) UIManager.get("Slider.shadow"));
            g.drawLine(trackLeft + 1, trackTop + 1, trackRight - 2, trackTop + 1);
            g.drawLine(trackLeft + 1, trackTop + 1, trackLeft + 1, trackBottom - 2);
        } else {
            g.setColor((Color) UIManager.get("Slider.shadow"));
            g.drawRect(trackLeft, trackTop,
                    (trackRight - trackLeft) - 1, (trackBottom - trackTop) - 1);
        }

        // Draw the fill
        int middleOfThumb;
        int fillTop;
        int fillLeft;
        int fillBottom;
        int fillRight;

        if (slider.getOrientation() == JSlider.HORIZONTAL) {
            middleOfThumb = thumbRect.x + (thumbRect.width / 2);
            middleOfThumb -= trackRect.x; // To compensate for the g.translate()
            fillTop = !slider.isEnabled() ? trackTop : trackTop + 1;
            fillBottom = !slider.isEnabled() ? trackBottom - 1 : trackBottom - 2;

            if (!drawInverted()) {
                fillLeft = !slider.isEnabled() ? trackLeft : trackLeft + 1;
                fillRight = middleOfThumb;
            } else {
                fillLeft = middleOfThumb;
                fillRight = !slider.isEnabled() ? trackRight - 1 : trackRight - 2;
            }
        } else {
            middleOfThumb = thumbRect.y + (thumbRect.height / 2);
            middleOfThumb -= trackRect.y; // To compensate for the g.translate()
            fillLeft = !slider.isEnabled() ? trackLeft : trackLeft + 1;
            fillRight = !slider.isEnabled() ? trackRight - 1 : trackRight - 2;

            if (!drawInverted()) {
                fillTop = middleOfThumb;
                fillBottom = !slider.isEnabled() ? trackBottom - 1 : trackBottom - 2;
            } else {
                fillTop = !slider.isEnabled() ? trackTop : trackTop + 1;
                fillBottom = middleOfThumb;
            }
        }

        if (slider.isEnabled()) {
            g.setColor(slider.getBackground());
            g.drawLine(fillLeft, fillTop, fillRight, fillTop);
            g.drawLine(fillLeft, fillTop, fillLeft, fillBottom);

            g.setColor(MetalLookAndFeel.getFocusColor());
            g.fillRect(fillLeft + 1, fillTop + 1,
                    fillRight - fillLeft, fillBottom - fillTop);
        } else {
            g.setColor(MetalLookAndFeel.getFocusColor());
            g.fillRect(fillLeft, fillTop, fillRight - fillLeft, fillBottom - fillTop);
        }

        g.translate(-trackRect.x, -trackRect.y);
    }
}
