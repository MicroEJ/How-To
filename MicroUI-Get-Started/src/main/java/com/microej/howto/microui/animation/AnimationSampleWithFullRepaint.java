/*
 * Java
 *
 * Copyright 2016 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.microej.howto.microui.animation;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import ej.microui.MicroUI;
import ej.microui.display.Colors;
import ej.microui.display.Display;
import ej.microui.display.Displayable;
import ej.microui.display.GraphicsContext;
import ej.microui.display.Image;
import ej.microui.util.EventHandler;

/**
 * This class shows how to create an animation using MicroUI and TimerTask
 *
 * Note that the whole display is redrawn on every frame
 */
public class AnimationSampleWithFullRepaint {

	private static final int ANIMATION_TIME = 40; // in ms

	private int imageX;
	private final int imageY;
	private Image microejImage;
	private final Display display;
	private Displayable displayable;

	/**
	 * Timer task doing an horizontal linear motion of MicroEJ.
	 */
	class HorizontalAnimatorTask extends TimerTask {

		private final int ABSOLUTE_INCREMENT = 2;
		private final AnimationSampleWithFullRepaint animated;
		private int horizontalIncrement = ABSOLUTE_INCREMENT;
		private final int leftLimit;
		private final int rightLimit;

		public HorizontalAnimatorTask(AnimationSampleWithFullRepaint animated) {
			this.animated = animated;
			final int animatedImageHalfWidth = animated.microejImage.getWidth() / 2;
			leftLimit = animatedImageHalfWidth;
			rightLimit = animated.display.getWidth() - animatedImageHalfWidth;
		}

		@Override
		public void run() {
			animated.imageX += horizontalIncrement;
			if (animated.imageX > rightLimit) {
				animated.imageX = rightLimit;
				horizontalIncrement = -ABSOLUTE_INCREMENT;
			} else {
				if (animated.imageX < leftLimit) {
					animated.imageX = leftLimit;
					horizontalIncrement = ABSOLUTE_INCREMENT;
				}
			}
			animated.displayable.repaint();
		}
	}

	/**
	 * Creates the displayable and shows it.
	 */
	public void createDisplayable() {

		displayable = new Displayable(display) {
			@Override
			public void paint(GraphicsContext g) {

				// fill up background with black
				g.setColor(Colors.BLACK);
				g.fillRect(0, 0, display.getWidth(), display.getHeight());

				// fill up half the area with white
				g.setColor(Colors.WHITE);
				g.fillRect(0, 0, display.getWidth()/2, display.getHeight());
				g.drawImage(microejImage, imageX, imageY, GraphicsContext.HCENTER | GraphicsContext.VCENTER);

			}

			@Override
			public EventHandler getController() {
				// No event handling is required for this sample.
				return null;
			}
		};

		displayable.show();
	}

	public void animate() {
		HorizontalAnimatorTask animator = new HorizontalAnimatorTask(this);
		Timer animationTimer = new Timer();
		animationTimer.schedule(animator, ANIMATION_TIME, ANIMATION_TIME);
	}

	/**
	 * Instantiate an AnimationSampleWithFullRepaint.
	 */
	public AnimationSampleWithFullRepaint(Display display) {
		this.display = display;

		// Starts at the center of the screen.
		this.imageX = display.getWidth() / 2;
		this.imageY = display.getHeight() / 2;

		try {
			microejImage = Image.createImage("/images/microej.png");
		} catch (IOException e) {
			throw new AssertionError(e);
		}
		this.createDisplayable();
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
		final Display display = Display.getDefaultDisplay();

		AnimationSampleWithFullRepaint animationSample = new AnimationSampleWithFullRepaint(display);
		animationSample.animate();
	}

}
