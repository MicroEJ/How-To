/*
 * Java
 *
 * Copyright 2016-2022 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.howto.microui.image;

import ej.drawing.TransformPainter;
import ej.microui.MicroUI;
import ej.microui.display.Colors;
import ej.microui.display.Display;
import ej.microui.display.Displayable;
import ej.microui.display.GraphicsContext;
import ej.microui.display.Image;
import ej.microui.display.Painter;

/**
 * This class shows how to use the TransformPainter utility class with scale draw method. The example draws an image
 * scaled up to the display smallest axis
 */
public class ScaledImages extends Displayable {

	@Override
	public void render(GraphicsContext g) {

		final Display display = Display.getDisplay();

		final int displayWidth = display.getWidth();
		final int displayHeight = display.getHeight();

		// fill up background with black
		g.setColor(Colors.BLACK);
		Painter.fillRectangle(g, 0, 0, displayWidth, displayHeight);

		Image image = Image.getImage("/images/mascot.png"); //$NON-NLS-1$

		final int imageWidth = image.getWidth();
		final int imageHeight = image.getHeight();

		// compute scaling factors on both dimensions (ensure float division is used)
		final float horizontalScalingFactor = (float) displayWidth / imageWidth;
		final float verticalScalingFactor = (float) displayHeight / imageHeight;

		// keep only the smallest one
		final float actualScalingFactor = Math.min(horizontalScalingFactor, verticalScalingFactor);

		// compute image top left anchor
		final int imageWidthScaled = (int) (imageWidth * actualScalingFactor);
		final int imageHeightScaled = (int) (imageHeight * actualScalingFactor);
		final int anchorX = (displayWidth - imageWidthScaled) / 2;
		final int anchorY = (displayHeight - imageHeightScaled) / 2;
		// draw scaled image

		TransformPainter.drawScaledImageBilinear(g, image, anchorX, anchorY, actualScalingFactor, actualScalingFactor);

	}

	/**
	 * Entry Point for the example.
	 *
	 * @param args
	 *            Not used.
	 */
	public static void main(String[] args) {
		// A call to MicroUI.start is required to initialize the graphics
		// runtime environment
		MicroUI.start();

		ScaledImages sample = new ScaledImages();
		Display.getDisplay().requestShow(sample);
	}

	@Override
	public boolean handleEvent(int event) {
		// No event handling is required for this sample.
		return false;
	}

}
