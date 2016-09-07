/*
 * Java
 *
 * Copyright 2016 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.microej.howto.microui.drawing;


import ej.microui.MicroUI;
import ej.microui.display.Colors;
import ej.microui.display.Display;
import ej.microui.display.Displayable;
import ej.microui.display.GraphicsContext;
import ej.microui.util.EventHandler;

/**
 * This class shows the main drawing primitives available from MicroUI
 */
public class Primitives {

	public void display() {
		// We will need to access the display to draw stuff
		final Display display = Display.getDefaultDisplay();

		// A displayable is an object that will be drawn on the display
		Displayable displayable = new Displayable(display) {
			@Override
			public void paint(GraphicsContext g) {

				final int DISPLAY_WIDTH = display.getWidth();
				final int DISPLAY_HEIGHT = display.getHeight();
				
				final int displayCenterX = DISPLAY_WIDTH / 2;
				final int displayCenterY = DISPLAY_HEIGHT / 2;

				// fill up background with black
				g.setColor(Colors.BLACK);
				g.fillRect(0, 0, DISPLAY_WIDTH, DISPLAY_HEIGHT);

				// fill up half the area with white
				g.setColor(Colors.WHITE);
				g.fillRect(0, 0, DISPLAY_WIDTH/2, DISPLAY_HEIGHT);

				// draw a gray line across the area diagonal
				g.setColor(Colors.GRAY);
				g.setStrokeStyle(GraphicsContext.SOLID);
				g.drawLine(0, 0, DISPLAY_WIDTH, DISPLAY_HEIGHT);

				{ // draw a maroon circle at the center of the area
					g.setColor(Colors.MAROON);

					final int diameter = DISPLAY_WIDTH / 2;

					// Note that x and y parameters are the top left coordinates
					// of the circle bounding box.
					// Therefore some offset of half the rectangle width and
					// height have to be applied to center the circle on the
					// display
					final int x = displayCenterX - diameter / 2;
					final int y = displayCenterY - diameter / 2;

					g.fillCircle(x, y, diameter);
				}

				{ // draw a red rounded rectangle at the center of the area
					g.setColor(Colors.RED);

					final int rectangleWidth = DISPLAY_WIDTH / 3;
					final int rectangleHeight = DISPLAY_HEIGHT / 3;
					final int arcHeight = 20;
					final int arcWidth = 20 * (DISPLAY_WIDTH / DISPLAY_HEIGHT);

					// Note that x and y parameters are the top left coordinates
					// of the rectangle bounding box.
					// Therefore some offset of half the rectangle width and
					// height have to be applied to center the rectangle on the
					// display
					final int x = displayCenterX - rectangleWidth / 2;
					final int y = displayCenterY - rectangleHeight / 2;

					g.fillRoundRect(x, y, rectangleWidth, rectangleHeight, arcWidth, arcHeight);
				}

				{ // draw a yellow ellipse at the center of the area
					g.setColor(Colors.YELLOW);

					final int ellipseWidth = DISPLAY_WIDTH / 4;
					final int ellipseHeight = DISPLAY_HEIGHT / 4;
					// Note that x and y parameters are the top left coordinates
					// of the ellipse bounding box.
					// Therefore some offset of half the ellipse width and
					// height have to be applied to center the ellipse on the
					// display
					final int x = displayCenterX - ellipseWidth / 2;
					final int y = displayCenterY - ellipseHeight / 2;
					g.fillEllipse(x, y, ellipseWidth, ellipseHeight);
				}

				{ // draw a blue circle arc at the center of the area
					g.setColor(Colors.BLUE);

					final int diameter = DISPLAY_HEIGHT / 6;
					final int radius = diameter / 2;

					// Note that x and y parameters are the top left coordinates
					// of the circle bounding box.
					// Therefore some offset of half the circle diameter
					// has to be applied to center the circle on the display
					final int x = displayCenterX - radius;
					final int y = displayCenterY - radius;
					g.fillCircleArc(x, y, diameter, 25, 310);
				}

				{ // draw a green triangle at the center of the area
					g.setColor(Colors.LIME);

					final int radius = DISPLAY_HEIGHT / 20;
					final int centerX = displayCenterX;
					final int leftX = displayCenterX - radius;
					final int rightX = displayCenterX + radius;
					final int topY = displayCenterY - radius;
					final int bottomY = displayCenterY + radius;
					final int xys[] = { leftX, bottomY, // Bottom left vertex
							centerX, topY, // Top vertex
							rightX, bottomY // Bottom right vertex
					};

					g.fillPolygon(xys);
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
	 *            Not used.
	 */
	public static void main(String[] args) {
		// A call to MicroUI.start is required to initialize the graphics
		// runtime environment
		MicroUI.start();

		Primitives sample = new Primitives();
		sample.display();
	}

}
