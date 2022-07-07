/*
 * Java
 *
 * Copyright 2016-2022 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.howto.microui.drawing;

import ej.drawing.ShapePainter;
import ej.library.ui.MicroEJColors;
import ej.microui.MicroUI;
import ej.microui.display.Display;
import ej.microui.display.Displayable;
import ej.microui.display.GraphicsContext;
import ej.microui.display.Painter;

/**
 * This class shows how to draw lines (straight or curved) of varying thickness and fade.
 *
 * <p>
 * Drawings are similar to the ones of the {@link BasicDrawings} example, except that antialiased bordering is added.
 * </p>
 *
 */
public class BasicDrawingsAntiAliased extends Displayable {

	private static final int TRIANGLE_RADIUS_DIVIDER = 20;
	private static final int CIRCLE_START_ANGLE = 25;
	private static final int CIRCLE_ARC_ANGLE = 310;
	private static final int CIRCLE_ARC_HEIGHT = 40;
	private static final int CIRCLE_THICKNESS = 4;
	private static final int CIRCLE_FADE = 4;
	private static final int DIAGONAL_LINE_THICKNESS = 10;
	private static final int DIAGONAL_LINE_FADE = 10;
	private static final int LINE_THICKNESS = 2;
	private static final int LINE_FADE = 1;

	private final int displayWidth;
	private final int displayHeight;

	private final int displayCenterX;
	private final int displayCenterY;

	private final int ellipseWidth;
	private final int ellipseHeight;

	/**
	 * Instantiates a BasicDrawingsAntiAliased instance.
	 */
	public BasicDrawingsAntiAliased() {

		final Display display = Display.getDisplay();

		this.displayWidth = display.getWidth();
		this.displayHeight = display.getHeight();

		this.displayCenterX = this.displayWidth / 2;
		this.displayCenterY = this.displayHeight / 2;

		this.ellipseWidth = this.displayWidth / 4;
		this.ellipseHeight = this.displayHeight / 4;
	}

	@Override
	public void render(GraphicsContext g) {

		// fill up background with black
		g.setColor(MicroEJColors.CONCRETE_BLACK_75);
		Painter.fillRectangle(g, 0, 0, this.displayWidth, this.displayHeight);

		// fill up half the area with white
		g.setColor(MicroEJColors.WHITE);
		Painter.fillRectangle(g, 0, 0, this.displayWidth / 2, this.displayHeight);

		drawLine(g);

		drawThickFilledCircle(g);

		drawRoundedRectangle(g);

		drawEllipse(g);

		drawCircleArc(g);

		drawTriangle(g);

	}

	private void drawLine(GraphicsContext g) {
		// antialiased rendering
		// draw a line across the area diagonal
		g.setColor(MicroEJColors.TURQUOISE);

		// draw a thick faded line
		ShapePainter.drawThickFadedLine(g, 0, 0, this.displayWidth, this.displayHeight, DIAGONAL_LINE_THICKNESS,
				DIAGONAL_LINE_FADE, ShapePainter.Cap.NONE, ShapePainter.Cap.NONE);
	}

	private void drawThickFilledCircle(GraphicsContext g) {
		// draw a circle at the center of the area
		g.setColor(MicroEJColors.CORAL);

		final int circleDiameter = this.displayWidth / 2;
		// Note that x and y parameters are the top left coordinates
		// of the circle bounding box.
		// Therefore some offset of half the rectangle width and
		// height has to be applied to center the circle on the
		// display
		final int x = this.displayCenterX - circleDiameter / 2;
		final int y = this.displayCenterY - circleDiameter / 2;

		Painter.fillCircle(g, x, y, circleDiameter);

		g.setColor(MicroEJColors.CORAL);

		// draw a circle with thickness and fade
		final int centerX = this.displayCenterX - circleDiameter / 2;
		final int centerY = this.displayCenterY - circleDiameter / 2;

		ShapePainter.drawThickFadedCircle(g, centerX, centerY, circleDiameter, CIRCLE_THICKNESS, CIRCLE_FADE);
	}

	private void drawRoundedRectangle(GraphicsContext g) {
		// draw a rounded rectangle at the center of the area
		g.setColor(MicroEJColors.POMEGRANATE);

		final int rectangleWidth = this.displayWidth / 3;
		final int rectangleHeight = this.displayHeight / 3;
		final int arcWidth = CIRCLE_ARC_HEIGHT * (this.displayWidth / this.displayHeight);

		// Note that x and y parameters are the top left coordinates
		// of the rectangle bounding box.
		// Therefore some offset of half the rectangle width and
		// height has to be applied to center the rectangle on the
		// display
		final int x = this.displayCenterX - rectangleWidth / 2;
		final int y = this.displayCenterY - rectangleHeight / 2;

		Painter.fillRoundedRectangle(g, x, y, rectangleWidth, rectangleHeight, arcWidth, CIRCLE_ARC_HEIGHT);

	}

	private void drawEllipse(GraphicsContext g) {
		// draw an ellipse at the center of the area
		g.setColor(MicroEJColors.TURQUOISE);

		// Note that x and y parameters are the top left coordinates
		// of the ellipse bounding box.
		// Therefore some offset of half the ellipse width and
		// height has to be applied to center the ellipse on the
		// display
		final int x = this.displayCenterX - this.ellipseWidth / 2;
		final int y = this.displayCenterY - this.ellipseHeight / 2;

		// antialiased bordering
		g.setBackgroundColor(MicroEJColors.POMEGRANATE);

		// draw an ellipse with thickness and fade
		ShapePainter.drawThickFadedEllipse(g, x, y, this.ellipseWidth, this.ellipseHeight, 2, 2);

		Painter.fillEllipse(g, x, y, this.ellipseWidth, this.ellipseHeight);
	}

	private void drawCircleArc(GraphicsContext g) {
		// draw a circle arc at the center of the area
		g.setColor(MicroEJColors.BONDI);

		final int circleDiameter = this.displayHeight / 6;
		final int radius = circleDiameter / 2;

		// Note that x and y parameters are the top left coordinates
		// of the circle bounding box.
		// Therefore some offset of half the circle diameter
		// has to be applied to center the circle on the display
		final int x = this.displayCenterX - radius;
		final int y = this.displayCenterY - radius;

		Painter.fillCircleArc(g, x, y, circleDiameter, CIRCLE_START_ANGLE, CIRCLE_ARC_ANGLE);

	}

	private void drawTriangle(GraphicsContext g) {
		// draw a triangle at the center of the area
		g.setColor(MicroEJColors.CHICK);

		final int radius = this.displayHeight / TRIANGLE_RADIUS_DIVIDER;
		final int centerX = this.displayCenterX;
		final int leftX = this.displayCenterX - radius;
		final int rightX = this.displayCenterX + radius;
		final int topY = this.displayCenterY - radius;
		final int bottomY = this.displayCenterY + radius;
		final int[] xys = { leftX, bottomY, // Bottom left vertex
				centerX, topY, // Top vertex
				rightX, bottomY // Bottom right vertex
		};

		ShapePainter.fillPolygon(g, xys);

		// antialiased bordering
		// The background is not homogeneous anymore, so we can not reuse the optimization.
		g.removeBackgroundColor();

		// draw lines with thickness and fade
		ShapePainter.drawThickFadedLine(g, leftX, bottomY, centerX, topY, LINE_THICKNESS, LINE_FADE,
				ShapePainter.Cap.NONE, ShapePainter.Cap.NONE);
		ShapePainter.drawThickFadedLine(g, centerX, topY, rightX, bottomY, LINE_THICKNESS, LINE_FADE,
				ShapePainter.Cap.NONE, ShapePainter.Cap.NONE);
		ShapePainter.drawThickFadedLine(g, rightX, bottomY, leftX, bottomY, LINE_THICKNESS, LINE_FADE,
				ShapePainter.Cap.NONE, ShapePainter.Cap.NONE);

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

		BasicDrawingsAntiAliased sample = new BasicDrawingsAntiAliased();
		Display.getDisplay().requestShow(sample);
	}

	@Override
	public boolean handleEvent(int event) {
		// No event handling is required for this sample.
		return false;
	}

}
