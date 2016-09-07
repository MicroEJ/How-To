/*
 * Java
 *
 * Copyright 2016 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
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
public class ScaledImages {

	public void display() {
		// We will need to access the display to draw stuff
		final Display display = Display.getDefaultDisplay();

		// A displayable is an object that will be drawn on the display
		Displayable displayable = new Displayable(display) {
			@Override
			public void paint(GraphicsContext g) {

				final int DISPLAY_WIDTH = display.getWidth();
				final int DISPLAY_HEIGHT = display.getHeight();

				// fill up background with black
				g.setColor(Colors.BLACK);
				g.fillRect(0, 0, DISPLAY_WIDTH, DISPLAY_HEIGHT);


				try {
					Image microejImage = Image.createImage("/images/microej.png");

					final int IMAGE_WIDTH = microejImage.getWidth();
					final int IMAGE_HEIGHT = microejImage.getHeight();

					// compute scaling factors on both dimensions					
					final float horizontalScalingFactor = DISPLAY_WIDTH / IMAGE_WIDTH;
					final float verticalScalingFactor = DISPLAY_HEIGHT / IMAGE_HEIGHT;
					// keep only the smallest one
					final float actualScalingFactor = Math.min(horizontalScalingFactor, verticalScalingFactor);
					
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
		};

		displayable.show();
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

		ScaledImages scaledImages = new ScaledImages();
		scaledImages.display();
	}

}
