/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.view.samples;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Path2D;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Edward Jenkins
 */
public class SampleCanvas extends JPanel {

    // instance variables
    private boolean stereo;
    // mono or left
    private double[] leftValues;
    private double[] leftXPoints;
    private double[] leftYPoints;
    private Shape leftSoundPath;
    // stereo right
    private double[] rightValues;
    private double[] rightXPoints;
    private double[] rightYPoints;
    private Shape rightSoundPath;

    public SampleCanvas() {
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

        if (stereo) {

            // draw left channel line white
            g2d.setPaint(Color.WHITE);
            g2d.drawLine(0, (int) Math.round(getHeight() * 0.25), getWidth() - 1,
                    (int) Math.round(getHeight() * 0.25));

            // draw right channel line white
            g2d.setPaint(Color.WHITE);
            g2d.drawLine(0, (int) Math.round(getHeight() * 0.75), getWidth() - 1,
                    (int) Math.round(getHeight() * 0.75));

            // draw sample points in green
            g2d.setPaint(Color.GREEN);

            // update path
            setPaths();

            // draw stereo samples
            g2d.draw(leftSoundPath);
            g2d.draw(rightSoundPath);

        } else {

            // draw sample points in green
            g2d.setPaint(Color.GREEN);

            // update path
            setPaths();

            // draw mono samples
            g2d.draw(leftSoundPath);
        }
    }

    private void setPaths() {

        Path2D leftSoundPath = new Path2D.Double();
        Path2D rightSoundPath = new Path2D.Double();

        double halfHeightMultiplier = (getHeight() - 2) / 2,
                fullHeightMultiplier = getHeight() - 1,
                rightSampleOffset = getHeight() / 2 + 1;

        if (leftValues.length <= getWidth()) {

            for (int i = 0; i < leftValues.length; i++) {

                if (i == 0) {
                    if (stereo) {
                        leftSoundPath.moveTo(leftXPoints[i], leftYPoints[i]
                                * halfHeightMultiplier);

                        rightSoundPath.moveTo(rightXPoints[i], rightYPoints[i]
                                * halfHeightMultiplier
                                + rightSampleOffset);
                    } else {
                        leftSoundPath.moveTo(leftXPoints[i], leftYPoints[i]
                                * fullHeightMultiplier);
                    }
                } else {
                    if (stereo) {
                        leftSoundPath.lineTo(leftXPoints[i] * (getWidth() - 1),
                                leftYPoints[i] * halfHeightMultiplier);

                        rightSoundPath.lineTo(rightXPoints[i] * (getWidth() - 1),
                                rightYPoints[i] * halfHeightMultiplier
                                + rightSampleOffset);
                    } else {
                        leftSoundPath.lineTo(leftXPoints[i] * (getWidth() - 1),
                                leftYPoints[i] * fullHeightMultiplier);
                    }
                }
            }

        } else if (leftValues.length / getWidth() < 32) {

            double samplesPerPixel = (double) leftValues.length / getWidth();

            double samplesIndex = 0, rightXPoint = 0, rightYPoint = 0;

            for (int i = 0; i < getWidth(); i++) {

                // get average value of floor and celing sampels of sampels index
                double leftXPoint = leftXPoints[(int) Math.round(samplesIndex)];

                double leftYPoint = leftYPoints[(int) Math.round(samplesIndex)];

                if (stereo) {
                    rightXPoint = leftXPoints[(int) Math.round(samplesIndex)];

                    rightYPoint = leftYPoints[(int) Math.round(samplesIndex)];
                }

                if (i == 0) {
                    if (stereo) {
                        leftSoundPath.moveTo(leftXPoint, leftYPoint
                                * halfHeightMultiplier);

                        rightSoundPath.moveTo(rightXPoint, rightYPoint
                                * halfHeightMultiplier
                                + rightSampleOffset);
                    } else {
                        leftSoundPath.moveTo(leftXPoint, leftYPoint
                                * fullHeightMultiplier);
                    }
                } else {
                    if (stereo) {
                        leftSoundPath.lineTo(i, leftYPoint
                                * halfHeightMultiplier);

                        rightSoundPath.lineTo(i, rightYPoint
                                * halfHeightMultiplier
                                + rightSampleOffset);
                    } else {
                        leftSoundPath.lineTo(i, leftYPoint
                                * fullHeightMultiplier);
                    }
                }

                samplesIndex += samplesPerPixel;
            }
        } else {
            double samplesPerPixel = (double) leftValues.length / getWidth();
            double halfOfSamples = samplesPerPixel * 0.5;

            double samplesIndex = 0, rightXPoint = 0, rightYPoint = 0;
            int highestLIndex = 0, lowestLIndex = 0,
                    highestRIndex = 0, lowestRIndex = 0;

            for (int i = 0; i < getWidth(); i++) {

                double highestLPoint, lowestLPoint,
                        highestRPoint = 0, lowestRPoint = 0;

                int midIndex = (int) Math.round(samplesIndex 
                        + halfOfSamples);

                highestLPoint = lowestLPoint = leftValues[midIndex];

                if (stereo) {
                    highestRPoint = lowestRPoint = rightValues[midIndex];
                }

                // get highest and lowest pixels within samples per pixel
                for (int j = 0; j < Math.round(samplesPerPixel); j++) {

                    int sampleIndex = (int) Math.round(j + samplesIndex);

                    if (leftValues[sampleIndex] > highestLPoint) {
                        highestLPoint = leftValues[sampleIndex];
                        highestLIndex = sampleIndex;
                    } else if (leftValues[sampleIndex] < lowestLPoint) {
                        lowestLPoint = leftValues[sampleIndex];
                        lowestLIndex = sampleIndex;
                    }

                    if (stereo) {
                        if (rightValues[sampleIndex] > highestRPoint) {
                            highestRPoint = rightValues[sampleIndex];
                            highestRIndex = sampleIndex;
                        } else if (rightValues[sampleIndex] < lowestRPoint) {
                            lowestRPoint = rightValues[sampleIndex];
                            lowestRIndex = sampleIndex;
                        }
                    }
                }

                // get average value of floor and celing sampels of sampels index
                double highestLXPoint = leftXPoints[highestLIndex];
                double lowestLXPoint = leftXPoints[lowestLIndex];
                double highestLYPoint = leftYPoints[highestLIndex];
                double lowestLYPoint = leftYPoints[lowestLIndex];

                double highestRXPoint = 0, lowestRXPoint = 0, highestRYPoint = 0,
                        lowestRYPoint = 0;

                if (stereo) {
                    highestRXPoint = rightXPoints[highestRIndex];
                    lowestRXPoint = rightXPoints[lowestRIndex];
                    highestRYPoint = rightYPoints[highestRIndex];
                    lowestRYPoint = rightYPoints[lowestRIndex];
                }

                // initialise path placement
                if (i == 0) {
                    if (stereo) {

                        // left highest lowest and middle
                        leftSoundPath.moveTo(highestLXPoint, highestLYPoint
                                * halfHeightMultiplier);

                        leftSoundPath.lineTo(lowestLXPoint, lowestLYPoint
                                * halfHeightMultiplier);

                        // righ highest lowest and middlet 
                        rightSoundPath.moveTo(highestRXPoint, highestRYPoint
                                * halfHeightMultiplier + rightSampleOffset);

                        rightSoundPath.lineTo(lowestRXPoint, lowestRYPoint
                                * halfHeightMultiplier + rightSampleOffset);
                    } else {

                        // left highest lowest and middle
                        leftSoundPath.moveTo(highestLXPoint, highestLYPoint
                                * fullHeightMultiplier);

                        leftSoundPath.lineTo(lowestLXPoint, lowestLYPoint
                                * fullHeightMultiplier);
                    }
                } else {
                    // continue path writing
                    if (stereo) {

                        // left highest and lowest
                        leftSoundPath.moveTo(i,
                                highestLYPoint * halfHeightMultiplier);

                        leftSoundPath.lineTo(i,
                                lowestLYPoint * halfHeightMultiplier);

                        // right highest and lowest
                        rightSoundPath.moveTo(i, highestRYPoint
                                * halfHeightMultiplier + rightSampleOffset);

                        rightSoundPath.lineTo(i, lowestRYPoint
                                * halfHeightMultiplier + rightSampleOffset);
                    } else {

                        // left highest and lowest
                        leftSoundPath.lineTo(i,
                                highestLYPoint * fullHeightMultiplier);

                        leftSoundPath.lineTo(i,
                                lowestLYPoint * fullHeightMultiplier);
                    }

                    samplesIndex += samplesPerPixel;
                }
            }
        }
        this.leftSoundPath = (Shape) leftSoundPath;
        this.rightSoundPath = (Shape) rightSoundPath;
    }

    private void setLeftPoints() {

        leftXPoints = new double[leftValues.length];
        leftYPoints = new double[leftValues.length];

        for (int i = 0; i < leftValues.length; i++) {

            if (i == 0) {
                leftXPoints[i] = 0;
            } else {
                leftXPoints[i] = (double) i / (leftValues.length - 1);
            }

            leftYPoints[i] = Math.abs((leftValues[i] * 0.5 - 0.5));
        }
    }

    private void setRightPoints() {

        rightXPoints = new double[rightValues.length];
        rightYPoints = new double[rightValues.length];

        for (int i = 0; i < rightValues.length; i++) {

            if (i == 0) {
                rightXPoints[i] = 0;
            } else {
                rightXPoints[i] = (double) i / (rightValues.length - 1);
            }

            rightYPoints[i] = Math.abs((rightValues[i] * 0.5 - 0.5));
        }
    }

    public void setMonoSamples(double[] lData) {

        leftValues = lData;

        stereo = false;

        setLeftPoints();

        repaint();
    }

    public void setStereoSamples(double[] lData, double[] rData) {

        leftValues = lData;
        rightValues = rData;

        stereo = true;

        setLeftPoints();
        setRightPoints();

        repaint();
    }
}
