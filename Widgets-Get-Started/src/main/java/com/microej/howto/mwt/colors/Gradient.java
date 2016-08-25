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
public class Gradient {

	public void display() {
		final Display display = Display.getDefaultDisplay();

		Displayable displayable = new Displayable(display) {
			@Override
			public void paint(GraphicsContext g) {

				// draw gradient on areas smaller than the number of colors in
				// the gradient
				{
					int[] greenToWhiteGradient = getGradient(g, Colors.GREEN, Colors.WHITE);
					drawGradientLeftToRight(g, greenToWhiteGradient, 0, 0, 32, display.getHeight() / 2);

					int[] whiteToRedGradient = getGradient(g, Colors.WHITE, Colors.RED);
					drawGradientLeftToRight(g, whiteToRedGradient, 32, 0, 64, display.getHeight() / 2);
				}

				// draw gradient on areas with more columns than the number of
				// colors in the gradient
				{
					int[] redToWhiteGradient = getGradient(g, Colors.RED, Colors.WHITE);
					drawGradientLeftToRight(g, redToWhiteGradient, 64, 0, display.getWidth() / 2,
							display.getHeight() / 2);

					int[] whiteToBlueGradient = getGradient(g, Colors.WHITE, Colors.BLUE);
					drawGradientLeftToRight(g, whiteToBlueGradient, display.getWidth() / 2, 0, display.getWidth(),
							display.getHeight() / 2);
				}

				// draw gradient
				// * from right to left
				// * from left to right
				{
					int[] whiteToBlackGradient = getGradient(g, Colors.WHITE, Colors.BLACK);
					drawGradientRightToLeft(g, whiteToBlackGradient, 0, display.getHeight() / 2, display.getWidth(),
							display.getHeight() / 4 * 3);
					drawGradientLeftToRight(g, whiteToBlackGradient, 0, display.getHeight() / 4 * 3, display.getWidth(),
							display.getHeight());
				}

			}

			@Override
			public EventHandler getController() {
				// No event handling is performed for this sample, therefore do
				// not bother with implementing this
				return null;
			}
		};

		displayable.show();
	}

	public static void drawGradientLeftToRight(GraphicsContext g, int[] gradient, int xStart, int yStart, int xEnd,
			int yEnd) {
		drawGradient(g, gradient, xStart, yStart, xEnd, yEnd, true);
	}

	public static void drawGradientRightToLeft(GraphicsContext g, int[] gradient, int xStart, int yStart, int xEnd,
			int yEnd) {
		drawGradient(g, gradient, xStart, yStart, xEnd, yEnd, false);
	}

	/**
	 * Fills the rectangle specified by (xStart,yStart,yEnd,yEnd) with vertical
	 * stripes of homogeneous width using colors from the gradient for each
	 * stripe. <br/>
	 * If the width of the rectangle is greated than the number of colors in the
	 * gradient then the number of stripes is the number of colors in the
	 * gradient. <br/>
	 * If the width of the rectangle is lower than the number of colors in the
	 * gradient then the number of stripes is the width of the rectangle.
	 *
	 * @param g
	 * @param gradient
	 * @param xStart
	 * @param yStart
	 * @param xEnd
	 * @param yEnd
	 */
	public static void drawGradient(GraphicsContext g, int[] gradient, int xStart, int yStart, int xEnd, int yEnd,
			boolean leftToRight) {
		int xWidth = xEnd - xStart;
		int yHeight = yEnd - yStart;

		int nbSteps = 0;

		boolean gradientIsWiderThanRectangle = false;
		if ( xWidth > gradient.length )
		{
			nbSteps = gradient.length;
		}
		else
		{
			gradientIsWiderThanRectangle = true;
			nbSteps = xWidth;
		}

		int colorIndex = leftToRight ? 0 : gradient.length - 1;

		if ( gradientIsWiderThanRectangle )
		{

			int colorStep = gradient.length / nbSteps;
			int currentX = xStart;

			for ( int i = 0; i < nbSteps ; i++ )
			{
				g.setColor(gradient[colorIndex]);
				g.fillRect(currentX, yStart, 1, yHeight);
				currentX++;
				if (leftToRight) {
					colorIndex += colorStep;
				} else {
					colorIndex -= colorStep;
				}
			}
		}
		else
		{
			final int stepWidth = xWidth / nbSteps;
			// if the nb of colors in gradient is not a divider of the width of the
			// drawing area
			// there will be a gap if we
			final int gap = xWidth - (nbSteps * stepWidth);

			int currentX = xStart;
			int gapFilling = 0;
			for ( int i = 0; i < nbSteps ; i++ )
			{
				int actualStepWidth = stepWidth;
				if (gap > 0 && gapFilling < gap) {
					gapFilling++;
					actualStepWidth++;
				}
				if (leftToRight) {
					g.setColor(gradient[i]);
				} else {
					g.setColor(gradient[(gradient.length - 1) - i]);
				}
				g.fillRect(currentX, yStart, actualStepWidth, yHeight);
				currentX += actualStepWidth;
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
	 * @param args
	 */
	public static void main(String[] args) {
		MicroUI.start();

		Gradient sample = new Gradient();
		sample.display();
	}

}
