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

			{ // antialiased bordering
				// Since we are drawing on the edge of the rounded
				// rectangle,
				// and that the thickness is greater than 1,
				// background colors to consider are
				// - the one of the rounded rectangle
				// - the one of the enclosing circle
				// So we cannot optimize the drawing performed by
				// AntiAliasedShapes using g.setBackgroundColor(...) API

				final int halfRectangleWidth = rectangleWidth/2;
				final int halfRectangleHeight = rectangleHeight/2;

				final int halfArcWidth = arcWidth/2;
				final int halfArcHeight = arcHeight/2;

				final int horizontalSidesStartX = displayCenterX - halfRectangleWidth + halfArcWidth;
				final int horizontalSidesEndX = displayCenterX + halfRectangleWidth - halfArcWidth;
				final int horizontalSidesStartY = displayCenterY - halfRectangleHeight;
				final int horizontalSidesEndY = displayCenterY + halfRectangleHeight;

				final int verticalSidesStartY = displayCenterY - halfRectangleHeight + halfArcHeight;
				final int verticalSidesEndY = displayCenterY + halfRectangleHeight - halfArcHeight;
				final int verticalSidesStartX = displayCenterX - halfRectangleWidth;
				final int verticalSidesEndX = displayCenterX + halfRectangleWidth;

				final int topLeftEllipseX = displayCenterX - halfRectangleWidth;
				final int topLeftEllipseY = displayCenterY - halfRectangleHeight;

				final int bottomLeftEllipseX = displayCenterX - halfRectangleWidth;
				final int bottomLeftEllipseY = displayCenterY + halfRectangleHeight - arcHeight;
						
				final int topRightEllipseX = displayCenterX + halfRectangleWidth - arcWidth;
				final int topRightEllipseY = displayCenterY - halfRectangleHeight;

				final int bottomRightEllipseX = displayCenterX + halfRectangleWidth - arcWidth;
				final int bottomRightEllipseY = displayCenterY + halfRectangleHeight - arcHeight;

				AntiAliasedShapes.Singleton.setThickness(2);
				AntiAliasedShapes.Singleton.setFade(2);
				
				//top , bottom, left and right straight lines
				AntiAliasedShapes.Singleton.drawLine(g, horizontalSidesStartX, horizontalSidesStartY,horizontalSidesEndX, horizontalSidesStartY);
				AntiAliasedShapes.Singleton.drawLine(g, horizontalSidesStartX, horizontalSidesEndY,horizontalSidesEndX, horizontalSidesEndY);
				AntiAliasedShapes.Singleton.drawLine(g, verticalSidesStartX, verticalSidesStartY,verticalSidesStartX, verticalSidesEndY);
				AntiAliasedShapes.Singleton.drawLine(g, verticalSidesEndX, verticalSidesStartY,verticalSidesEndX, verticalSidesEndY);

				//use less thickness when drawing corners using ellipse so as to avoid pixel artifacts on junction points with straight lines
				AntiAliasedShapes.Singleton.setThickness(1);
				AntiAliasedShapes.Singleton.setFade(1);

				//top, bottom, left and right corners
				AntiAliasedShapes.Singleton.drawEllipse(g, topLeftEllipseX, topLeftEllipseY, arcWidth, arcHeight);
				AntiAliasedShapes.Singleton.drawEllipse(g, bottomLeftEllipseX, bottomLeftEllipseY, arcWidth, arcHeight);
				AntiAliasedShapes.Singleton.drawEllipse(g, topRightEllipseX, topRightEllipseY, arcWidth, arcHeight);
				AntiAliasedShapes.Singleton.drawEllipse(g, bottomRightEllipseX, bottomRightEllipseY, arcWidth, arcHeight);
			}

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
			final int endAngle = startAngle + arcAngle;

			{ // antialiased bordering
				// The background is made up of a single solid color,
				// so we can optimize the drawing performed by AntiAliasedShapes
				// using g.setBackgroundColor(...) API.
				// This will avoid reading the color of each pixel in memory
				// before merging it with the foreground color.
				g.setBackgroundColor(MicroEJColors.TURQUOISE);

				final int thicknessCompensation = 1;
				final int thickness = 2;

				// specify line thickness and fade
				AntiAliasedShapes.Singleton.setThickness(thickness);
				AntiAliasedShapes.Singleton.setFade(2);

				AntiAliasedShapes.Singleton.drawCircleArc(g, x, y, diameter, startAngle , arcAngle);
				// The background is not homogeneous anymore, so we can not reuse the optimization.
				g.removeBackgroundColor();

				g.fillCircleArc(x, y, diameter   , startAngle  , arcAngle );


				double startAngleXOffset = (radius + thickness/2) * Math.sin(Math.toRadians(startAngle - thicknessCompensation + 90 ));
				double startAngleYOffset = (radius + thickness/2) * Math.cos(Math.toRadians(startAngle - thicknessCompensation + 90 ));

				double endAngleXOffset = (radius + thickness/2) * Math.sin(Math.toRadians(endAngle + thicknessCompensation + 90 ));
				double endAngleYOffset = (radius + thickness/2) * Math.cos(Math.toRadians(endAngle + thicknessCompensation + 90 ));

				//g.setColor(MicroEJColors.POMEGRANATE);
				AntiAliasedShapes.Singleton.drawLine(g, displayCenterX, displayCenterY,
						(int) (displayCenterX + startAngleXOffset), (int) (displayCenterY + startAngleYOffset));
				AntiAliasedShapes.Singleton.drawLine(g, displayCenterX, displayCenterY,
						(int) (displayCenterX + endAngleXOffset), (int) (displayCenterY + endAngleYOffset));

			}

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
