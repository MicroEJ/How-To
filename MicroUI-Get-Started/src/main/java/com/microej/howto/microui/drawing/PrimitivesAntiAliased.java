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

		// A displayable is an object that will be drawn on the display
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

				// draw a gray line across the area diagonal
				g.setColor(Colors.GRAY);
				// specify line thickness
				AntiAliasedShapes.Singleton.setThickness(10);
				// specify line thickness
				AntiAliasedShapes.Singleton.setFade(10);
				AntiAliasedShapes.Singleton.drawLine(g, 0, 0, display.getWidth(), display.getHeight());

				{ // draw a maroon circle at the center of the area
					g.setColor(Colors.MAROON);

					final int diameter = display.getWidth() / 2;

					// Note that x and y parameters are the top left coordinates
					// of the rectangle bounding box.
					// Therefore some offset of half the rectangle width and
					// height have to be applied to center the rectangle on the
					// display
					final int x = displayCenterX - diameter / 2;
					final int y = displayCenterY - diameter / 2;

					g.fillCircle(x, y, diameter);

					AntiAliasedShapes.Singleton.setThickness(9);
					// specify line thickness
					AntiAliasedShapes.Singleton.setFade(9);
					AntiAliasedShapes.Singleton.drawCircle(g, x, y, diameter);

				}

				{
					// draw a red rounded rectangle at the center of the area
					g.setColor(Colors.RED);
					// The backround is full, so we can optimized the drawing of
					// the AntiAlisedShapes.
					// g.setBackgroundColor(Colors.MAROON);

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

					// specify line thickness
					AntiAliasedShapes.Singleton.setThickness(8);
					// specify line thickness
					int halfFade = 2;
					AntiAliasedShapes.Singleton.setFade(halfFade * 2);
					AntiAliasedShapes.Singleton.drawLine(g, x + halfFade, y - halfFade, x + rectangleWidth - halfFade,
							y - halfFade);
					AntiAliasedShapes.Singleton.drawLine(g, x + rectangleWidth - halfFade, y - halfFade,
							x + rectangleWidth - halfFade, y + rectangleHeight - halfFade);
					AntiAliasedShapes.Singleton.drawLine(g, x + halfFade, y + rectangleHeight - halfFade,
							x + rectangleWidth - halfFade, y + rectangleHeight - halfFade);
					AntiAliasedShapes.Singleton.drawLine(g, x - halfFade, y - halfFade, x - halfFade,
							y + rectangleHeight - halfFade);
				}

				{
					// draw a yellow ellipse at the center of the area
					g.setColor(Colors.YELLOW);
					// The background is full, so we can optimized the drawing
					// of AntiAliasedShapes using setBackgroundColor. This will
					// avoid to read the color of each pixel in the memory
					// before merging it with the foreground color.
					g.setBackgroundColor(Colors.RED);

					final int ellipseWidth = display.getWidth() / 4;
					final int ellipseHeight = display.getHeight() / 4;
					// Note that x and y parameters are the top left coordinates
					// of the ellipse bounding box.
					// Therefore some offset of half the ellipse width and
					// height have to be applied to center the ellipse on the
					// display
					final int x = displayCenterX - ellipseWidth / 2;
					final int y = displayCenterY - ellipseHeight / 2;

					// specify line thickness
					AntiAliasedShapes.Singleton.setThickness(8);
					// specify line thickness
					AntiAliasedShapes.Singleton.setFade(8);
					AntiAliasedShapes.Singleton.drawEllipse(g, x, y, ellipseWidth, ellipseHeight);
					g.fillEllipse(x, y, ellipseWidth, ellipseHeight);
				}

				{
					// draw a blue circle arc at the center of the area
					g.setColor(Colors.BLUE);
					// The background is full, so we can optimized the drawing
					// of AntiAliasedShapes using setBackgroundColor. This will
					// avoid to read the color of each pixel in the memory
					// before merging it with the foreground color.
					g.setBackgroundColor(Colors.YELLOW);

					final int diameter = display.getHeight() / 6;
					final int radius = diameter / 2;

					// Note that x and y parameters are the top left coordinates
					// of the circle bounding box.
					// Therefore some offset of half the circle diameter
					// has to be applied to center the circle on the display
					final int x = displayCenterX - radius;
					final int y = displayCenterY - radius;
					final int startAngle = 25;
					final int arcAngle = 310;

					// specify line thickness
					AntiAliasedShapes.Singleton.setThickness(6);
					// specify line thickness
					AntiAliasedShapes.Singleton.setFade(6);
					double xOffset = radius * Math.sin(arcAngle);
					double yOffset = radius * Math.cos(arcAngle);
					int tmp = 12;
					AntiAliasedShapes.Singleton.drawCircleArc(g, x, y, diameter, startAngle + tmp, arcAngle - tmp * 2);

					// The background is not full anymore, so we can not reuse
					// the
					// optimization.
					g.removeBackgroundColor();
					AntiAliasedShapes.Singleton.drawLine(g, displayCenterX, displayCenterY,
							(int) (displayCenterX + xOffset), (int) (displayCenterY + yOffset));
					AntiAliasedShapes.Singleton.drawLine(g, displayCenterX, displayCenterY,
							(int) (displayCenterX + xOffset), (int) (displayCenterY - yOffset));
					g.fillCircleArc(x, y, diameter, startAngle, arcAngle);
				}

				{ // draw a green triangle at the center of the area
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
				// No event handling is required for this sample.
				
				return null;
			}
		};

		displayable.show();
	}

	public void displayOld() {
		// We will need to access the display to draw stuff
		final Display display = Display.getDefaultDisplay();

		// A displayable is an object that will be drawn on the display
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
	 *            Not used.
	 */
	public static void main(String[] args) {
		// A call to MicroUI.start is required to initialize the graphics
		// runtime environment
		MicroUI.start();

		PrimitivesAntiAliased sample = new PrimitivesAntiAliased();
		sample.display();
	}

}
