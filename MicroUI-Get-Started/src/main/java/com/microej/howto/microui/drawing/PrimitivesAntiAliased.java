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
import ej.microui.display.shape.AntiAliasedShapes;
import ej.microui.util.EventHandler;

/**
 * This class shows how to draw lines (straight or curved) of varying thickness
 * and fade using the AntialiasedShapes class
 */
public class PrimitivesAntiAliased {

	public void display() {
		// We will need to access the display to draw stuff
		final Display display = Display.getDefaultDisplay();

		// A displayable is an object that will draw on the display
		Displayable displayable = new Displayable(display) {
			@Override
			public void paint(GraphicsContext g) {

				final int displayCenterX = display.getWidth() / 2;
				final int displayCenterY = display.getHeight() / 2;

				// fill up background with black
				g.setColor(Colors.BLACK);
				g.fillRect(0, 0, display.getWidth(), display.getHeight());

				// fill up half the area with white
				g.setColor(Colors.WHITE);
				g.fillRect(0, 0, display.getWidth() / 2, display.getHeight());


				// draw a dotted red line across the area diagonal
				g.setColor(Colors.GRAY);
				g.setStrokeStyle(GraphicsContext.DOTTED);
				// g.drawLine(0, 0, display.getWidth(), display.getHeight());

				// specify line thickness
				AntiAliasedShapes.Singleton.setThickness(10);
				// specify line thickness
				AntiAliasedShapes.Singleton.setFade(10);

				AntiAliasedShapes.Singleton.drawLine(g, 0, 0, display.getWidth(), display.getHeight());

				{
					// draw a maroon arc at the center of the area
					g.setColor(Colors.MAROON);

					final int radius = display.getWidth() / 2;

					// Note that x and y parameters are the top left coordinates
					// of the rectangle bounding box.
					// Therefore some offset of half the rectangle width and
					// height have to be applied to center the rectangle on the
					// display
					final int x = displayCenterX - radius / 2;
					final int y = displayCenterY - radius / 2;

					g.fillCircleArc(x, y, radius, 45, 270);

					AntiAliasedShapes.Singleton.setThickness(9);
					// specify line thickness
					AntiAliasedShapes.Singleton.setFade(9);

					AntiAliasedShapes.Singleton.drawCircleArc(g, x, y, radius, 45, 270);

				}

				{
					// draw a red rounded rectangle at the center of the area
					g.setColor(Colors.RED);

					final int rectangleWidth = display.getWidth() / 3;
					final int rectangleHeight = display.getHeight() / 3;
					final int arcHeight = 20;
					final int arcWidth = 20 * (display.getWidth() / display.getHeight());

					// Note that x and y parameters are the top left coordinates
					// of the rectangle bounding box.
					// Therefore some offset of half the rectangle width and
					// height have to be applied to center the rectangle on the
					// display
					final int x = displayCenterX - rectangleWidth / 2;
					final int y = displayCenterY - rectangleHeight / 2;

					g.fillRoundRect(x, y, rectangleWidth, rectangleHeight, arcWidth, arcHeight);

				}

				{
					// draw a yellow ellipse at the center of the area
					g.setColor(Colors.YELLOW);

					final int ellipseWidth = display.getWidth() / 4;
					final int ellipseHeight = display.getHeight() / 4;
					// Note that x and y parameters are the top left coordinates
					// of the ellipse bounding box.
					// Therefore some offset of half the ellipse width and
					// height have to be applied to center the ellipse on the
					// display
					final int x = displayCenterX - ellipseWidth / 2;
					final int y = displayCenterY - ellipseHeight / 2;
					g.fillEllipse(x, y, ellipseWidth, ellipseHeight);

					// specify line thickness
					AntiAliasedShapes.Singleton.setThickness(8);
					// specify line thickness
					AntiAliasedShapes.Singleton.setFade(8);
					AntiAliasedShapes.Singleton.drawEllipse(g, x, y, ellipseWidth, ellipseHeight);
				}

				{
					// draw a blue circle at the center of the area
					g.setColor(Colors.BLUE);

					final int diameter = display.getHeight() / 6;
					final int radius = diameter / 2;

					// Note that x and y parameters are the top left coordinates
					// of the circle bounding box.
					// Therefore some offset of half the circle diameter
					// has to be applied to center the circle on the display
					final int x = displayCenterX - radius;
					final int y = displayCenterY - radius;
					g.fillCircle(x, y, diameter);

					// specify line thickness
					AntiAliasedShapes.Singleton.setThickness(6);
					// specify line thickness
					AntiAliasedShapes.Singleton.setFade(6);
					AntiAliasedShapes.Singleton.drawCircle(g, x, y, diameter);

				}

				{
					// draw a green triangle at the center of the area
					g.setColor(Colors.LIME);

					final int radius = display.getHeight() / 20;
					final int xys[] = { displayCenterX - radius, displayCenterY + radius, displayCenterX,
							displayCenterY - radius, displayCenterX + radius, displayCenterY + radius };
					g.fillPolygon(xys);

					// specify line thickness
					AntiAliasedShapes.Singleton.setThickness(4);
					// specify line thickness
					AntiAliasedShapes.Singleton.setFade(4);
					AntiAliasedShapes.Singleton.drawLine(g, xys[0], xys[1], xys[2], xys[3]);
					AntiAliasedShapes.Singleton.drawLine(g, xys[2], xys[3], xys[4], xys[5]);
					AntiAliasedShapes.Singleton.drawLine(g, xys[4], xys[5], xys[0], xys[1]);

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

	public void displayOld() {
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

				// specify line thickness
				AntiAliasedShapes.Singleton.setThickness(10);
				// specify line thickness
				AntiAliasedShapes.Singleton.setFade(10);

				g.setColor(Colors.RED);
				// use an API almost identical to GraphicsContext.drawLine
				AntiAliasedShapes.Singleton.drawLine(g, 0, 0, display.getWidth(), display.getHeight());

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

					g.setColor(Colors.GREEN);
					// use an API almost identical to
					// GraphicsContext.drawEllipse
					AntiAliasedShapes.Singleton.drawEllipse(g, (display.getWidth() / 2) - (ellipseWidth / 2),
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

					g.setColor(Colors.GREEN);

					// use an API almost identical to GraphicsContext.drawCircle
					AntiAliasedShapes.Singleton.drawCircle(g, (display.getWidth() / 2) - radius,
							(display.getHeight() / 2) - radius, diameter);

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

		PrimitivesAntiAliased sample = new PrimitivesAntiAliased();
		sample.display();
	}

}
