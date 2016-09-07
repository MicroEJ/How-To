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
import ej.microui.display.transform.ImageFlip;
import ej.microui.display.transform.ImageFlip.Action;
import ej.microui.util.EventHandler;


/**
 * This class shows how to use the ImageFlip utility class
 */
public class FlippedImages {

	public void display() {
		// We will need to access the display to draw stuff
		final Display display = Display.getDefaultDisplay();

		// A displayable is an object that will be drawn on the display
		Displayable displayable = new Displayable(display) {
			@Override
			public void paint(GraphicsContext g) {

				final int DISPLAY_WIDTH = display.getWidth();
				final int DISPLAY_HEIGHT = display.getHeight();

				final int left = DISPLAY_WIDTH / 4;
				final int top = DISPLAY_HEIGHT / 4;
				final int right = 3 * DISPLAY_WIDTH / 4;
				final int bottom = 3 * DISPLAY_HEIGHT / 4;

				// fill up background with black
				g.setColor(Colors.BLACK);
				g.fillRect(0, 0,DISPLAY_WIDTH, DISPLAY_HEIGHT);


				try {
					Image microejImage = Image.createImage("/images/microej.png");

					{ // top-left corner - no flip
						g.drawImage(microejImage, left, top,
								GraphicsContext.HCENTER | GraphicsContext.VCENTER);
					}

					{ // top-right corner - 90° flip
						ImageFlip.Singleton.setAction(Action.FLIP_90);
						ImageFlip.Singleton.draw(g, microejImage, right, top,
								GraphicsContext.HCENTER | GraphicsContext.VCENTER);
					}

					{ // bottom-left corner - 180° flip
						ImageFlip.Singleton.setAction(Action.FLIP_180);
						ImageFlip.Singleton.draw(g, microejImage, left, bottom,
								GraphicsContext.HCENTER | GraphicsContext.VCENTER);
					}

					{ // bottom-right corner - 270° flip
						ImageFlip.Singleton.setAction(Action.FLIP_270);
						ImageFlip.Singleton.draw(g, microejImage, right, bottom,
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

		FlippedImages transparentImages = new FlippedImages();
		transparentImages.display();
	}

}
