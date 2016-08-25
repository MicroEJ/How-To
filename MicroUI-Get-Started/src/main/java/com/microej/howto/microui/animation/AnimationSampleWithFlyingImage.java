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
import ej.microui.display.FlyingImage;
import ej.microui.display.GraphicsContext;
import ej.microui.display.Image;
import ej.microui.util.EventHandler;

/**
 * This class shows how to create an animation using MicroUI Flying Images and
 * TimerTask
 *
 * Note that the whole display is NOT redrawn on every frame
 */
public class AnimationSampleWithFlyingImage {

	private static final int ANIMATION_TIME = 40; // in ms

	private int imageX;
	private final int imageY;
	private Image microejImage;
	private FlyingImage flyingImage;
	private final Display display;
	private Displayable displayable;

	/**
	 * Timer task doing an horizontal linear motion of MicroEJ.
	 */
	private class HorizontalAnimatorTask extends TimerTask {

		final int ABSOLUTE_INCREMENT = 2;
		AnimationSampleWithFlyingImage animated;
		private final int leftLimit;
		private final int rightLimit;

		/**
		 * Whether MicroEJ is going left or right.
		 */
		private boolean left = true;

		public HorizontalAnimatorTask(AnimationSampleWithFlyingImage animated) {
			this.animated = animated;
			leftLimit = animated.microejImage.getWidth() / 2;
			rightLimit = animated.display.getWidth() - animated.microejImage.getWidth() / 2;
		}

		@Override
		public void run() {
			if (animated.imageX <= leftLimit) {
				left = true;
			} else if (animated.imageX >= rightLimit) {
				left = false;
			}
			animated.imageX += (left) ? ABSOLUTE_INCREMENT : -ABSOLUTE_INCREMENT;

			flyingImage.setLocation(animated.imageX, animated.imageY);
		}
	}

	/**
	 * Creates the displayable and shows it with the flying image.
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
			}

			@Override
			public EventHandler getController() {
				// No event handling is required for this sample.

				return null;
			}
		};
		displayable.show();
		flyingImage.show();
	}

	/**
	 * Starts the animation.
	 */
	public void animate() {
		HorizontalAnimatorTask animator = new HorizontalAnimatorTask(this);
		Timer animationTimer = new Timer();
		animationTimer.schedule(animator, ANIMATION_TIME, ANIMATION_TIME);
	}

	/**
	 * Instantiate an AnimationSampleWithFlyingImage.
	 */
	public AnimationSampleWithFlyingImage(Display display) {
		this.display = display;

		try {
			microejImage = Image.createImage("/images/microej.png");
			flyingImage = new FlyingImage(microejImage);
		} catch (IOException e) {
			throw new AssertionError(e);
		}

		// Starts at the center of the screen.
		this.imageX = display.getWidth() / 2 - microejImage.getWidth() / 2;
		this.imageY = display.getHeight() / 2 - microejImage.getHeight() / 2;

		this.createDisplayable();
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
		final Display display = Display.getDefaultDisplay();

		AnimationSampleWithFlyingImage animationSample = new AnimationSampleWithFlyingImage(display);
		animationSample.animate();
	}

}
