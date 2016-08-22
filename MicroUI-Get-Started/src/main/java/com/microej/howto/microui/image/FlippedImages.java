/*
 * Java
 *
 * Copyright 2016 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.microej.howto.microui.image;

import java.io.IOException;

import ej.microui.MicroUI;
import ej.microui.display.Colors;
import ej.microui.display.Display;
import ej.microui.display.Displayable;
import ej.microui.display.GraphicsContext;
import ej.microui.display.Image;
import ej.microui.display.transform.ImageFlip;
import ej.microui.display.transform.ImageFlip.Action;
import ej.microui.util.EventHandler;


/**
 * This class shows how to use the ImageFlip utility class
 */
public class FlippedImages {

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


				try {
					Image microejImage = Image.createImage("/images/microej.png");

					final int left = display.getWidth() / 4;
					final int top = display.getHeight() / 4;
					final int right = display.getWidth() / 4 * 3;
					final int bottom = display.getHeight() / 4 * 3;

					// top-left corner - no flip
					{
						g.drawImage(microejImage, left, top,
								GraphicsContext.HCENTER | GraphicsContext.VCENTER);
					}

					// top-right corner - 90° flip
					{
						ImageFlip.Singleton.setAction(Action.FLIP_90);
						ImageFlip.Singleton.draw(g, microejImage, right, top,
								GraphicsContext.HCENTER | GraphicsContext.VCENTER);
					}

					// bottom-left corner - 180° flip
					{
						ImageFlip.Singleton.setAction(Action.FLIP_180);
						ImageFlip.Singleton.draw(g, microejImage, left, bottom,
								GraphicsContext.HCENTER | GraphicsContext.VCENTER);
					}

					// bottom-right corner - 270° flip
					{
						ImageFlip.Singleton.setAction(Action.FLIP_270);
						ImageFlip.Singleton.draw(g, microejImage, right, bottom,
								GraphicsContext.HCENTER | GraphicsContext.VCENTER);
					}

				} catch (IOException e) {
					throw new AssertionError(e);
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

		FlippedImages transparentImages = new FlippedImages();
		transparentImages.display();
	}

}
