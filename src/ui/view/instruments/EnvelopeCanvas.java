/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.view.instruments;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Path2D;
import javax.swing.JPanel;
import module.it.format.NodePoint;

/**
 *
 * @author Edward Jenkins
 */
public class EnvelopeCanvas extends JPanel {

    // instance variabls
    private NodePoint[] envelopeValues;
    private double[] envelopeXPoints;
    private double[] envelopeYPoints;
    private Shape envelopePath;

    public EnvelopeCanvas() {
        setMinimumSize(new Dimension(1000, 20));
        setMaximumSize(new Dimension(3000, 4000));
        setPreferredSize(new Dimension(1000, 100));
        setDoubleBuffered(true);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // draw background black
        g2d.setPaint(Color.BLACK);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        // draw middle line white
        g2d.setPaint(Color.WHITE);
        g2d.drawLine(0, getHeight() / 2, getWidth() - 1, getHeight() / 2);

        // draw sample points in green
        g2d.setPaint(Color.GREEN);

        // update path
        setPaths();

        // draw mono samples
        g2d.draw(envelopePath);

    }

    private void setPaths() {

        Path2D envelopePath = new Path2D.Double();

        double fullHeightMultiplier = getHeight() - 1;

        for (int i = 0; i < envelopeValues.length; i++) {

            if (i == 0) {

                envelopePath.moveTo(envelopeXPoints[i], envelopeYPoints[i]
                        * fullHeightMultiplier);

            } else {

                envelopePath.lineTo(envelopeXPoints[i] * (getWidth() - 1),
                        envelopeYPoints[i] * fullHeightMultiplier);

            }
        }

        this.envelopePath = (Shape) envelopePath;
    }

    private void setEnvelopePoints(int lowest, int highest) {
        
        // deal with negative bounds for pan and pitch envelopes
        if (lowest < 0) {
            highest += Math.abs(lowest);
            lowest -= lowest;
        }

        envelopeXPoints = new double[envelopeValues.length];
        envelopeYPoints = new double[envelopeValues.length];

        if (envelopeValues.length > 0) {
            int lastTick
                    = envelopeValues[envelopeValues.length - 1].getTickNumber();

            for (int i = 0; i < envelopeValues.length; i++) {

                envelopeXPoints[i] = (double) envelopeValues[i].getTickNumber()
                        / lastTick;

                envelopeYPoints[i] = 1 - ((double) envelopeValues[i]
                        .getNodeValue() + Math.abs(lowest)) / (highest 
                        + Math.abs(lowest));
            }
        }
    }

    public void setEnvelopeData(NodePoint[] envelopeData, int lowest,
            int highest) {

        envelopeValues = envelopeData;

        setEnvelopePoints(lowest, highest);

        repaint();
    }
}
