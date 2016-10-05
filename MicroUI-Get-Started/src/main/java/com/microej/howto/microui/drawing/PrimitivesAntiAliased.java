/*
 * Java
 *
 * Copyright 2016 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.microej.com/open-source-bsd-license/.
 */
package com.microej.howto.microui.drawing;


import com.microej.howto.microui.MicroEJColors;

import ej.microui.MicroUI;
import ej.microui.display.Display;
import ej.microui.display.Displayable;
import ej.microui.display.GraphicsContext;
import ej.microui.display.shape.AntiAliasedShapes;
import ej.microui.util.EventHandler;

/**
 * This class shows how to draw lines (straight or curved) of varying thickness
 * and fade using the AntialiasedShapes class.<br/>
 *
 * Drawings are similar to the ones of the {@link Primitives} example, except
 * that antialiased bordering is added.
 *
 */
public class PrimitivesAntiAliased extends Displayable {

	public PrimitivesAntiAliased(Display display) {
		super(display);
	}
	
	@Override
	public void paint(GraphicsContext g) {

		final int DISPLAY_WIDTH = getDisplay().getWidth();
		final int DISPLAY_HEIGHT = getDisplay().getHeight();

		final int displayCenterX = DISPLAY_WIDTH / 2;
		final int displayCenterY = DISPLAY_HEIGHT / 2;

		// fill up background with black
		g.setColor(MicroEJColors.CONCRETE_BLACK_75);
		g.fillRect(0, 0, DISPLAY_WIDTH, DISPLAY_HEIGHT);

		// fill up half the area with white
		g.setColor(MicroEJColors.WHITE);
		g.fillRect(0, 0, DISPLAY_WIDTH / 2, DISPLAY_HEIGHT);

		{ // antialiased rendering
			// draw a line across the area diagonal
			g.setColor(MicroEJColors.TURQUOISE);

			// specify line thickness & fade
			AntiAliasedShapes.Singleton.setThickness(10);
			AntiAliasedShapes.Singleton.setFade(10);

			AntiAliasedShapes.Singleton.drawLine(g, 0, 0, DISPLAY_WIDTH, DISPLAY_HEIGHT);
		}

		{ // draw a circle at the center of the area
			g.setColor(MicroEJColors.CORAL);

			final int diameter = DISPLAY_WIDTH / 2;

			// Note that x and y parameters are the top left coordinates
			// of the circle bounding box.
			// Therefore some offset of half the rectangle width and
			// height has to be applied to center the circle on the
			// display
			final int x = displayCenterX - diameter / 2;
			final int y = displayCenterY - diameter / 2;

			g.fillCircle(x, y, diameter);

			{ // antialiased bordering
				// Since the circle overlays both the black and white halves of the screen
				// there are two background colors to consider,
				// so we cannot optimize the drawing performed by
				// AntiAliasedShapes using g.setBackgroundColor(...) API

				// specify line thickness & fade
				AntiAliasedShapes.Singleton.setThickness(4);
				AntiAliasedShapes.Singleton.setFade(4);

				AntiAliasedShapes.Singleton.drawCircle(g, x, y, diameter);
			}

		}

		{
			// draw a rounded rectangle at the center of the area
			g.setColor(MicroEJColors.POMEGRANATE);

			final int rectangleWidth = DISPLAY_WIDTH / 3;
			final int rectangleHeight = DISPLAY_HEIGHT / 3;
			final int arcHeight = 40;
			final int arcWidth = 40 * (DISPLAY_WIDTH / DISPLAY_HEIGHT);

			// Note that x and y parameters are the top left coordinates
			// of the rectangle bounding box.
			// Therefore some offset of half the rectangle width and
			// height has to be applied to center the rectangle on the
			// display
			final int x = displayCenterX - rectangleWidth / 2;
			final int y = displayCenterY - rectangleHeight / 2;
			
			g.fillRoundRect(x, y, rectangleWidth, rectangleHeight, arcWidth, arcHeight);
			//no primitives for antialiased rounded rectangle available yet
		}

		{
			// draw an ellipse at the center of the area
			g.setColor(MicroEJColors.TURQUOISE);

			final int ellipseWidth = DISPLAY_WIDTH / 4;
			final int ellipseHeight = DISPLAY_HEIGHT / 4;
			// Note that x and y parameters are the top left coordinates
			// of the ellipse bounding box.
			// Therefore some offset of half the ellipse width and
			// height has to be applied to center the ellipse on the
			// display
			final int x = displayCenterX - ellipseWidth / 2;
			final int y = displayCenterY - ellipseHeight / 2;

			{ // antialiased bordering
				// The background is made up of a single solid color,
				// so we can optimize the drawing performed by AntiAliasedShapes
				// using g.setBackgroundColor(...) API.
				// This will avoid reading the color of each pixel in memory
				// before merging it with the foreground color.
				g.setBackgroundColor(MicroEJColors.POMEGRANATE);
				// specify line thickness
				AntiAliasedShapes.Singleton.setThickness(2);
				// specify line thickness
				AntiAliasedShapes.Singleton.setFade(2);
				AntiAliasedShapes.Singleton.drawEllipse(g, x, y, ellipseWidth, ellipseHeight);
				g.fillEllipse(x, y, ellipseWidth, ellipseHeight);
			}
		}

		{
			// draw a circle arc at the center of the area
			g.setColor(MicroEJColors.BONDI);

			final int diameter = DISPLAY_HEIGHT / 6;
			final int radius = diameter / 2;

			// Note that x and y parameters are the top left coordinates
			// of the circle bounding box.
			// Therefore some offset of half the circle diameter
			// has to be applied to center the circle on the display
			final int x = displayCenterX - radius;
			final int y = displayCenterY - radius;
			final int startAngle = 25;
			final int arcAngle = 310;

			g.fillCircleArc(x, y, diameter, startAngle, arcAngle);
			//no primitives for antialiased circle arc available yet
		}

		{ // draw a triangle at the center of the area
			g.setColor(MicroEJColors.CHICK);

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

			{ // antialiased bordering
				// The background is not homogeneous anymore, so we can not reuse the optimization.
				g.removeBackgroundColor();
				// specify line thickness
				AntiAliasedShapes.Singleton.setThickness(2);
				// specify line thickness
				AntiAliasedShapes.Singleton.setFade(1);
				AntiAliasedShapes.Singleton.drawLine(g, leftX, bottomY, centerX, topY);
				AntiAliasedShapes.Singleton.drawLine(g, centerX, topY, rightX, bottomY);
				AntiAliasedShapes.Singleton.drawLine(g, rightX, bottomY, leftX, bottomY);
			}

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
	 *            Not used.
	 */
	public static void main(String[] args) {
		// A call to MicroUI.start is required to initialize the graphics
		// runtime environment
		MicroUI.start();

		// We will need to access the display to draw stuff
		final Display display = Display.getDefaultDisplay();

		PrimitivesAntiAliased sample = new PrimitivesAntiAliased(display);
		sample.show();
	}

}
