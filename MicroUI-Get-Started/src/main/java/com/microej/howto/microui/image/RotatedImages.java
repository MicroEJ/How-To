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
import ej.microui.display.transform.ImageRotation;
import ej.microui.util.EventHandler;


/**
 * This class shows how to use the ImageRotation utility class
 */
public class RotatedImages extends Displayable {

	public RotatedImages(Display display) {
		super(display);
	}

	@Override
	public void paint(GraphicsContext g) {

		final int DISPLAY_WIDTH = getDisplay().getWidth();
		final int DISPLAY_HEIGHT = getDisplay().getHeight();

		final int left = DISPLAY_WIDTH / 4;
		final int top = DISPLAY_HEIGHT / 4;
		final int right = 3 * DISPLAY_WIDTH / 4;
		final int bottom = 3 * DISPLAY_HEIGHT / 4;

		// fill up background with black
		g.setColor(Colors.BLACK);
		g.fillRect(0, 0, DISPLAY_WIDTH, DISPLAY_HEIGHT);


		try {
			Image microejImage = Image.createImage("/images/microej.png");

			{ // top-left corner - 45°
				ImageRotation rotation = new ImageRotation();
				rotation.setRotationCenter(left, top);
				rotation.setAngle(45);

				// Use the bilinear algorithm to render the image. This
				// algorithm performs better rendering than nearest
				// neighbor algorithm but it is slower to apply.
				rotation.drawBilinear(g, microejImage, left, top,
						GraphicsContext.HCENTER | GraphicsContext.VCENTER);
			}

			{ // top-right corner - 135°
				ImageRotation rotation = new ImageRotation();
				rotation.setRotationCenter(right, top);
				rotation.setAngle(135);

				// Use the bilinear algorithm to render the image. This
				// algorithm performs better rendering than nearest
				// neighbor algorithm but it is slower to apply.
				rotation.drawBilinear(g, microejImage, right, top,
						GraphicsContext.HCENTER | GraphicsContext.VCENTER);
			}

			{ // bottom-left corner - 45°
				ImageRotation rotation = new ImageRotation();
				rotation.setRotationCenter(left, bottom);
				rotation.setAngle(45);

				// Uses the nearest neighbor algorithm to render the
				// image. This algorithm is faster than bilinear
				// algorithm but its rendering is more simple.
				rotation.drawNearestNeighbor(g, microejImage, left, bottom,
						GraphicsContext.HCENTER | GraphicsContext.VCENTER);
			}

			{ // bottom-right corner - 135°
				ImageRotation rotation = new ImageRotation();
				rotation.setRotationCenter(right, bottom);
				rotation.setAngle(135);

				// Uses the nearest neighbor algorithm to render the
				// image. This algorithm is faster than bilinear
				// algorithm but its rendering is more simple.
				rotation.drawNearestNeighbor(g, microejImage, right, bottom,
						GraphicsContext.HCENTER | GraphicsContext.VCENTER);
			}
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

		RotatedImages transparentImages = new RotatedImages(display);
		transparentImages.show();
	}

}
