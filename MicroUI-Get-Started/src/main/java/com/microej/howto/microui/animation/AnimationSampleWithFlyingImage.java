/*
 * Java
 *
 * Copyright 2016 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.microej.com/open-source-bsd-license/.
 */
package com.microej.howto.microui.animation;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import ej.microui.MicroUI;
import ej.microui.display.Colors;
import ej.microui.display.Display;
import ej.microui.display.Displayable;
import ej.microui.display.FlyingImage;
import ej.microui.display.GraphicsContext;
import ej.microui.display.Image;
import ej.microui.util.EventHandler;

/**
 * This class shows how to create an animation using MicroUI Flying Images and TimerTask.<p/>
 *
 * It will draw the MicroEJ robot moving from one side of the screen to the other.<p/>
 *
 * Note that the whole display is NOT redrawn on every frame.<p/>
 *
 * @see FlyingImage
 * @see TimerTask
 */
public class AnimationSampleWithFlyingImage extends Displayable {

	private static final int ANIMATION_PERIOD = 1000/60; // in ms - 60 frames per second

	private int imageX;
	private final int imageY;
	private Image microejImage;
	private FlyingImage flyingImage;

	/**
	 * Instantiate an AnimationSampleWithFlyingImage.
	 */
	public AnimationSampleWithFlyingImage(Display display) {
		super(display);

		try {
			microejImage = Image.createImage("/images/microej.png");
			flyingImage = new FlyingImage(microejImage);
		} catch (IOException e) {
			throw new AssertionError(e);
		}

		// Starts at the center of the screen.
		this.imageX = display.getWidth() / 2 - microejImage.getWidth() / 2;
		this.imageY = display.getHeight() / 2 - microejImage.getHeight() / 2;

		this.show();
		flyingImage.show();

	}

	/**
	 * Timer task doing an horizontal linear motion of MicroEJ logo.
	 */
	private class HorizontalAnimatorTask extends TimerTask {

		private final int ABSOLUTE_INCREMENT = 2;
		private final AnimationSampleWithFlyingImage animated;
		private int horizontalIncrement = ABSOLUTE_INCREMENT;
		private final int leftLimit;
		private final int rightLimit;

		public HorizontalAnimatorTask(AnimationSampleWithFlyingImage animated) {
			this.animated = animated;
			leftLimit = 0;
			rightLimit = animated.getDisplay().getWidth() - microejImage.getWidth();
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

			flyingImage.setLocation(animated.imageX, animated.imageY);
		}
	}

	/**
	 * Starts the animation.
	 */
	private void animate() {
		HorizontalAnimatorTask animator = new HorizontalAnimatorTask(this);
		Timer animationTimer = new Timer();
		animationTimer.schedule(animator, ANIMATION_PERIOD, ANIMATION_PERIOD);
	}

	@Override
	public void paint(GraphicsContext g) {

		final int DISPLAY_WIDTH = getDisplay().getWidth();
		final int DISPLAY_HEIGHT = getDisplay().getHeight();

		// fill up background with black
		g.setColor(Colors.BLACK);
		g.fillRect(0, 0, DISPLAY_WIDTH, DISPLAY_HEIGHT);

		// fill up half the area with white
		g.setColor(Colors.WHITE);
		g.fillRect(0, 0, DISPLAY_WIDTH/2, DISPLAY_HEIGHT);
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
	 *             Not used.
	 */
	public static void main(String[] args) {
		// A call to MicroUI.start is required to initialize the graphics
		// runtime environment
		MicroUI.start();

		// We will need to access the display to draw stuff
		final Display display = Display.getDefaultDisplay();

		AnimationSampleWithFlyingImage animationSample = new AnimationSampleWithFlyingImage(display);
		animationSample.animate();
	}

}
