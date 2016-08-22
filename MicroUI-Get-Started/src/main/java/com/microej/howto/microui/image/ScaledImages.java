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

		// A displayable is an object that will draw on the display
		Displayable displayable = new Displayable(display) {
			@Override
			public void paint(GraphicsContext g) {

				// fill up background with black
				g.setColor(Colors.BLACK);
				g.fillRect(0, 0, display.getWidth(), display.getHeight());


				try {
					Image microejImage = Image.createImage("/images/microej.png");

					// compute scaling factor
					final int imageSmallestDimension = Math.min(microejImage.getWidth(), microejImage.getHeight());
					final int displaySmallestDimension = Math.min(display.getWidth(), display.getHeight());
					final float scalingFactor = displaySmallestDimension / imageSmallestDimension;

					// compute image top left anchor
					final int imageWidthScaled = (int) (microejImage.getWidth() * scalingFactor);
					final int imageHeightScaled = (int) (microejImage.getHeight() * scalingFactor);
					final int anchorX = (display.getWidth() - imageWidthScaled) / 2;
					final int anchorY = (display.getHeight() - imageHeightScaled) / 2;

					// draw scaled image
					ImageScale.Singleton.setFactor(scalingFactor);
					ImageScale.Singleton.draw(g, microejImage, anchorX, anchorY,
							GraphicsContext.TOP | GraphicsContext.LEFT);

				} catch (IOException e) {
					throw new AssertionError(e);
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

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// A call to MicroUI.start is required to initialize the graphics
		// runtime environment
		MicroUI.start();

		ScaledImages scaledImages = new ScaledImages();
		scaledImages.display();
	}

}
