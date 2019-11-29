/*
 * Java
 *
 * Copyright 2016-2019 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.howto.microui.image;

import java.io.IOException;

import ej.microui.MicroUI;
import ej.microui.display.Colors;
import ej.microui.display.Display;
import ej.microui.display.Displayable;
import ej.microui.display.GraphicsContext;
import ej.microui.display.Image;
import ej.microui.display.transform.ImageScale;
import ej.microui.util.EventHandler;


/**
 * This class shows how to use the ImageScale utility class.
 * The example draws an image scaled up to the display smallest axis
 */
public class ScaledImages extends Displayable {

	public ScaledImages(Display display) {
		super(display);
	}

	@Override
	public void paint(GraphicsContext g) {

		final int DISPLAY_WIDTH = getDisplay().getWidth();
		final int DISPLAY_HEIGHT = getDisplay().getHeight();

		// fill up background with black
		g.setColor(Colors.BLACK);
		g.fillRect(0, 0, DISPLAY_WIDTH, DISPLAY_HEIGHT);


		try {
			Image microejImage = Image.createImage("/images/microej.png");

			final int IMAGE_WIDTH = microejImage.getWidth();
			final int IMAGE_HEIGHT = microejImage.getHeight();

			// compute scaling factors on both dimensions (ensure float division is used)
			final float horizontalScalingFactor = (float) DISPLAY_WIDTH / (float) IMAGE_WIDTH;
			final float verticalScalingFactor = (float) DISPLAY_HEIGHT / (float) IMAGE_HEIGHT;

			// keep only the smallest one
			final float smallestScalingFactor = Math.min(horizontalScalingFactor, verticalScalingFactor);

			float actualScalingFactor = smallestScalingFactor;
			// if enlarging, round scaling factor for better rendering
			if ( smallestScalingFactor > 1.0  )
			{
				actualScalingFactor = Math.round(smallestScalingFactor);
			}				
			
			// compute image top left anchor
			final int imageWidthScaled = (int) (IMAGE_WIDTH * actualScalingFactor);
			final int imageHeightScaled = (int) (IMAGE_HEIGHT * actualScalingFactor);
			final int anchorX = (DISPLAY_WIDTH - imageWidthScaled) / 2;
			final int anchorY = (DISPLAY_HEIGHT - imageHeightScaled) / 2;

			// draw scaled image
			ImageScale.Singleton.setFactor(actualScalingFactor);
			ImageScale.Singleton.draw(g, microejImage, anchorX, anchorY,
					GraphicsContext.TOP | GraphicsContext.LEFT);

		} catch (IOException e) {
			throw new AssertionError(e);
		}

	}

	@Override
	public EventHandler getController() {
		// No event handling is required for this sample.
		
		return null;
	}

	/**
	 * Entry Point for the example.
	 *
	 * @param args
	 *             Not used.
	 */
	public static void main(String[] args) {
		// A call to MicroUI.start is required to initialize the graphics
		// runtime environment
		MicroUI.start();

		// We will need to access the display to draw stuff
		final Display display = Display.getDefaultDisplay();

		ScaledImages scaledImages = new ScaledImages(display);
		scaledImages.show();
	}

}
