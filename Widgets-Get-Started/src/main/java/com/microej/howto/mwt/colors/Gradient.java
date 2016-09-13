/*
 * Java
 *
 * Copyright 2016 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.microej.howto.mwt.colors;


import ej.color.ColorHelper;
import ej.microui.MicroUI;
import ej.microui.display.Colors;
import ej.microui.display.Display;
import ej.microui.display.Displayable;
import ej.microui.display.GraphicsContext;
import ej.microui.util.EventHandler;

/**
 * This class shows how to create and draw a gradient
 */
public class Gradient extends Displayable {

	public Gradient(Display display) {
		super(display);
	}
	
	public void paint(GraphicsContext g) {

		// draw gradient on areas smaller than the number of colors in
		// the gradient
		{
			int[] greenToWhiteGradient = getGradient(g, Colors.GREEN, Colors.WHITE);
			drawGradientLeftToRight(g, greenToWhiteGradient, 0, 0, 16, getDisplay().getHeight() / 2);

			int[] whiteToRedGradient = getGradient(g, Colors.WHITE, Colors.RED);
			drawGradientLeftToRight(g, whiteToRedGradient, 16, 0, 48, getDisplay().getHeight() / 2);
		}

		// draw gradient on areas with more columns than the number of
		// colors in the gradient
		{
			int[] redToWhiteGradient = getGradient(g, Colors.RED, Colors.WHITE);
			drawGradientLeftToRight(g, redToWhiteGradient, 48, 0, getDisplay().getWidth() / 2,
					getDisplay().getHeight() / 2);

			int[] whiteToBlueGradient = getGradient(g, Colors.WHITE, Colors.BLUE);
			drawGradientLeftToRight(g, whiteToBlueGradient, getDisplay().getWidth() / 2, 0, getDisplay().getWidth(),
					getDisplay().getHeight() / 2);
		}

		// draw gradient
		// * from right to left
		// * from top to bottom
		{
			int[] whiteToBlackGradient = getGradient(g, Colors.WHITE, Colors.BLACK);
			drawGradientRightToLeft(g, whiteToBlackGradient, 0, getDisplay().getHeight() / 2, getDisplay().getWidth(),
					getDisplay().getHeight() / 4 * 3);
			drawGradientTopToBottom(g, whiteToBlackGradient, 0, getDisplay().getHeight() / 4 * 3, getDisplay().getWidth(),
					getDisplay().getHeight());
		}

	}

	@Override
	public EventHandler getController() {
		// No event handling is required for this sample
		return null;
	}

	public static void drawGradientTopToBottom(GraphicsContext g, int[] gradient, int xStart, int yStart, int xEnd,
			int yEnd) {
		drawGradient(g, gradient, xStart, yStart, xEnd, yEnd, false, true);
	}

	public static void drawGradientBottomToTop(GraphicsContext g, int[] gradient, int xStart, int yStart, int xEnd,
			int yEnd) {
		drawGradient(g, gradient, xStart, yStart, xEnd, yEnd, false, false);
	}

	public static void drawGradientLeftToRight(GraphicsContext g, int[] gradient, int xStart, int yStart, int xEnd,
			int yEnd) {
		drawGradient(g, gradient, xStart, yStart, xEnd, yEnd, true, true);
	}

	public static void drawGradientRightToLeft(GraphicsContext g, int[] gradient, int xStart, int yStart, int xEnd,
			int yEnd) {
		drawGradient(g, gradient, xStart, yStart, xEnd, yEnd, true, false);
	}

	/**
	 * 
	 * Gets a color index within the bounds of a gradient's index range from an estimated color index
	 * 
	 * @param gradient the gradient to pick colors from
	 * @param estimatedColorIndex the floating point value close to the desired color index
	 * @return
	 */
	private static int normalizeColorIndex(int[] gradient, float estimatedColorIndex)
	{
		final int roundedIndex = Math.round(estimatedColorIndex);
		final int actualColorIndex;
		if ( roundedIndex < 0 )
		{
			//if accumulated inaccuracy makes it so that roundedIndex is lower than 0, we might end up getting an ArrayOutOfBoundsException 
			actualColorIndex = 0;
		}
		else
		{
			//if accumulated inaccuracy makes it so that roundedIndex is greater than gradient.length, we might end up getting an ArrayOutOfBoundsException 
			actualColorIndex = Math.min(gradient.length, roundedIndex);
		}
		return actualColorIndex;
	}

	/**
	 * Fills the rectangle specified by (xStart,yStart,yEnd,yEnd) with stripes
	 * of homogeneous width or height using colors from the gradient for each
	 * stripe. <br/>
	 * Stripes are either horizontal or vertical depending on the horizontal
	 * parameter. <br/>
	 * Gradient colors are used either from start to finish or from finish to
	 * start depending on the startToFinish parameter. <br/>
	 * If the width of the rectangle is greater than the number of colors in the
	 * gradient then the number of stripes is the number of colors in the
	 * gradient. <br/>
	 * If the side of the rectangle matching the horizontal parameter is smaller
	 * than the number of colors in the gradient then the number of stripes is
	 * set to the size of this side.
	 *
	 * @param g
	 * @param gradient
	 * @param xStart
	 * @param yStart
	 * @param xEnd
	 * @param yEnd
	 * @param horizontal
	 * @param startToFinish
	 */
	private static void drawGradient(GraphicsContext g, int[] gradient, int xStart, int yStart, int xEnd, int yEnd,
			boolean horizontal, boolean startToFinish) {
		final int xWidth = xEnd - xStart;
		final int yHeight = yEnd - yStart;

		int nbSteps = 0;

		boolean gradientIsBiggerThanDirection = false;
		if (horizontal)
		{
			if (xWidth > gradient.length) {
				nbSteps = gradient.length;
			} else {
				gradientIsBiggerThanDirection = true;
				nbSteps = xWidth;
			}
		}
		else
		{
			if (yHeight > gradient.length) {
				nbSteps = gradient.length;
			} else {
				gradientIsBiggerThanDirection = true;
				nbSteps = yHeight;
			}

		}

		float colorIndex = startToFinish ? 0 : gradient.length - 1;

		if ( gradientIsBiggerThanDirection )
		{

			final float colorStep = (float)gradient.length / (float)nbSteps;

			if (horizontal)
			{
				int currentX = xStart;

				for (int i = 0; i < nbSteps; i++) {
					final int actualColorIndex = normalizeColorIndex(gradient, colorIndex);
					g.setColor(gradient[actualColorIndex]);
					g.fillRect(currentX, yStart, 1, yHeight);
					currentX++;
					if (startToFinish) {
						colorIndex += colorStep;
					} else {
						colorIndex -= colorStep;
					}
				}
			}
			else {
				int currentY = yStart;

				for (int j = 0; j < nbSteps; j++) {
					final int actualColorIndex = normalizeColorIndex(gradient, colorIndex);
					g.setColor(gradient[actualColorIndex]);
					g.fillRect(xStart, currentY, xWidth, 1);
					currentY++;
					if (startToFinish) {
						colorIndex += colorStep;
					} else {
						colorIndex -= colorStep;
					}
				}
			}
		}
		else
		{
			if (horizontal) {
				final int stepWidth = xWidth / nbSteps;
				// if the nb of colors in gradient is not a divider of the width
				// of the drawing area there will be a gap that we will need to compensate for
				final int gap = xWidth - (nbSteps * stepWidth);

				int currentX = xStart;
				int gapFilling = 0;
				for (int i = 0; i < nbSteps; i++) {
					int actualStepWidth = stepWidth;
					if (gap > 0 && gapFilling < gap) {
						gapFilling++;
						actualStepWidth++;
					}
					if (startToFinish) {
						g.setColor(gradient[i]);
					} else {
						g.setColor(gradient[(gradient.length - 1) - i]);
					}
					g.fillRect(currentX, yStart, actualStepWidth, yHeight);
					currentX += actualStepWidth;
				}
			} else {
				final int stepHeight = yHeight / nbSteps;
				// if the nb of colors in gradient is not a divider of the width
				// of the drawing area there will be a gap that we will need to compensate for
				final int gap = yHeight - (nbSteps * stepHeight);

				int currentY = yStart;
				int gapFilling = 0;
				for (int i = 0; i < nbSteps; i++) {
					int actualStepHeight = stepHeight;
					if (gap > 0 && gapFilling < gap) {
						gapFilling++;
						actualStepHeight++;
					}
					if (startToFinish) {
						g.setColor(gradient[i]);
					} else {
						g.setColor(gradient[(gradient.length - 1) - i]);
					}
					g.fillRect(xStart, currentY, xWidth, actualStepHeight);
					currentY += actualStepHeight;
				}
			}

		}

	}

	public static int[] getGradient(GraphicsContext g, int startColor, int endColor) {
		// get color components
		float currentRed = ColorHelper.getRed(startColor);
		float currentGreen = ColorHelper.getGreen(startColor);
		float currentBlue = ColorHelper.getBlue(startColor);
		int endRed = ColorHelper.getRed(endColor);
		int endGreen = ColorHelper.getGreen(endColor);
		int endBlue = ColorHelper.getBlue(endColor);

		// compute the max number of steps for the array and for the progress
		// factor
		int stepsRed = (int) (endRed - currentRed);
		int stepsGreen = (int) (endGreen - currentGreen);
		int stepsBlue = (int) (endBlue - currentBlue);
		int maxSteps = Math.max(Math.abs(stepsRed), Math.max(Math.abs(stepsGreen), Math.abs(stepsBlue))) + 1;

		int[] colors = new int[maxSteps];
		int length = 0;

		// compute the components progress step
		float stepRed = (float) stepsRed / maxSteps;
		float stepGreen = (float) stepsGreen / maxSteps;
		float stepBlue = (float) stepsBlue / maxSteps;

		int lastColor = -1;
		while (--maxSteps >= 0) {
			// compute color and save it if different than the previous one
			int color = ColorHelper.getColor((int) currentRed, (int) currentGreen, (int) currentBlue);
			int displayColor = g.getDisplayColor(color);
			if (displayColor != lastColor) {
				try {
					colors[length++] = displayColor;
				} catch (Exception e) {
					e.printStackTrace();
				}
				lastColor = displayColor;
			}
			// step
			currentRed += stepRed;
			currentGreen += stepGreen;
			currentBlue += stepBlue;
		}

		// crop result array to real height
		int[] result = new int[length];
		System.arraycopy(colors, 0, result, 0, length);
		return result;
	}

	/**
	 * Entry Point for the example.
	 *
	 * @param args
	 *            Not used.
	 */
	public static void main(String[] args) {
		MicroUI.start();

		// We will need to access the display to draw stuff
		final Display display = Display.getDefaultDisplay();

		Gradient sample = new Gradient(display);
		sample.show();
	}

}
