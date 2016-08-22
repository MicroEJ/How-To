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

		// A displayable is an object that will draw on the display
		Displayable displayable = new Displayable(display) {
			@Override
			public void paint(GraphicsContext g) {

				// fill up background with black
				g.setColor(Colors.BLACK);
				g.fillRect(0, 0, display.getWidth(), display.getHeight());

				// fill up half the area with white
				g.setColor(Colors.WHITE);
				g.fillRect(0, 0, display.getWidth()/2, display.getHeight());

				// draw a dotted red line across the area diagonal
				g.setColor(Colors.RED);
				g.setStrokeStyle(GraphicsContext.DOTTED);
				g.drawLine(0, 0, display.getWidth(), display.getHeight());

				{
					// draw a blue circle at the center of the area
					final int ellipseWidth = display.getWidth() / 4;
					final int ellipseHeight = display.getHeight() / 4;
					g.setColor(Colors.YELLOW);
					// Note that x and y parameters are the top left coordinates
					// of the ellipse bounding box.
					// Therefore some offset of half the ellipse width and
					// height have to be applied to center the ellipse on the
					// display
					g.fillEllipse((display.getWidth() / 2) - (ellipseWidth / 2),
							(display.getHeight() / 2) - ellipseHeight / 2, ellipseWidth, ellipseHeight);
				}

				{
					// draw a blue circle at the center of the area
					final int diameter = display.getHeight() / 6;
					final int radius = diameter / 2;
					g.setColor(Colors.BLUE);
					// Note that x and y parameters are the top left coordinates
					// of the circle bounding box.
					// Therefore some offset of half the circle diameter
					// has to be applied to center the circle on the display
					g.fillCircle((display.getWidth() / 2) - radius, (display.getHeight() / 2) - radius, diameter);
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

		Primitives sample = new Primitives();
		sample.display();
	}

}
