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
import ej.microui.display.transform.ImageDeformation;
import ej.microui.util.EventHandler;


/**
 * This class shows how to use the ImageDeformation utility class
 */
public class DeformedImages {

	public void display() {
		// We will need to access the display to draw stuff
		final Display display = Display.getDefaultDisplay();

		// A displayable is an object that will be drawn on the display
		Displayable displayable = new Displayable(display) {
			@Override
			public void paint(GraphicsContext g) {

				// fill up background with black
				g.setColor(Colors.BLACK);
				g.fillRect(0, 0, display.getWidth(), display.getHeight());

				final int displayWidth = display.getWidth();
				final int displayCenterX = displayWidth / 2;
				final int displayHeight = display.getHeight();
				final int displayCenterY = displayHeight / 2;

				final int left = displayWidth / 4;
				final int top = displayHeight / 4;
				final int right = displayWidth / 4 * 3;
				final int bottom = displayHeight / 4 * 3;

				try {
					Image microejImage = Image.createImage("/images/microej.png");

					{ // top-left corner - stretch X and Y
						int xys[] = { 0, 0, displayCenterX - 1, 0, displayCenterX - 1, displayCenterY - 1, 0,
								displayCenterY - 1 };
						ImageDeformation.Singleton.draw(g, microejImage, xys, 0, 0,
								GraphicsContext.TOP | GraphicsContext.LEFT);
					}

					{ // top-right corner - stretch Y
						int xys[] = { 0, 0, microejImage.getWidth() - 1, 0, microejImage.getWidth() - 1,
								displayCenterY - 1, 0, displayCenterY - 1 };

						g.translate(right, 0);
						ImageDeformation.Singleton.draw(g, microejImage, xys, 0, 0,
								GraphicsContext.TOP | GraphicsContext.HCENTER);
						g.translate(-right, 0);

					}

					{ // bottom-left corner - stretch X
						int xys[] = { 0, 0, displayCenterX - 1, 0, displayCenterX - 1, microejImage.getHeight() - 1, 0,
								microejImage.getHeight() - 1 };
						g.translate(0, bottom);
						ImageDeformation.Singleton.draw(g, microejImage, xys, 0, 0,
								GraphicsContext.VCENTER | GraphicsContext.LEFT);
						g.translate(0, -bottom);
					}

					{ // bottom-right corner - NO stretch
						int xys[] = { 0, 0, microejImage.getWidth() - 1, 0, microejImage.getWidth() - 1,
								microejImage.getHeight() - 1, 0, microejImage.getHeight() - 1 };
						g.translate(right, bottom);
						ImageDeformation.Singleton.draw(g, microejImage, xys, 0, 0,
								GraphicsContext.HCENTER | GraphicsContext.VCENTER);
						g.translate(-right, -bottom);
					}


					// draw grid for visual alignment control
					// divide display in 4 sections
					g.setColor(Colors.YELLOW);
					g.drawLine(0, displayCenterY, displayWidth, displayCenterY);
					g.drawLine(displayCenterX, 0, displayCenterX, displayHeight);

					// divide each section along horizontal & vertical axis
					g.setColor(Colors.MAGENTA);
					g.drawLine(0, top, displayWidth, top);
					g.drawLine(0, bottom, displayWidth, bottom);
					g.drawLine(left, 0, left, displayHeight);
					g.drawLine(right, 0, right, displayHeight);

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

		DeformedImages deformedImages = new DeformedImages();
		deformedImages.display();
	}

}
