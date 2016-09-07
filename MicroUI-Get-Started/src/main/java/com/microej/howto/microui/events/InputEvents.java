/*
 * Java
 *
 * Copyright 2016 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.microej.howto.microui.events;

import ej.microui.MicroUI;
import ej.microui.display.Colors;
import ej.microui.display.Display;
import ej.microui.display.Displayable;
import ej.microui.display.GraphicsContext;
import ej.microui.event.Event;
import ej.microui.event.EventGenerator;
import ej.microui.event.generator.Pointer;
import ej.microui.util.EventHandler;

/**
 * This class shows how to handle touch input events
 */
public class InputEvents extends Displayable implements EventHandler {


	private final Display display;
	private int nbClicks;
	private int pointerX;
	private int pointerY;
	private String message;

	public InputEvents(Display display) {
		super(display);
		this.display = display;
		nbClicks = 0;
		pointerX = 0;
		pointerY = 0;
		this.message = "Touch the screen !";
	}

	@Override
	public void paint(GraphicsContext g) {

		final int DISPLAY_WIDTH = display.getWidth();
		final int DISPLAY_HEIGHT = display.getHeight();

		// fill up background with black
		g.setColor(Colors.BLACK);
		g.fillRect(0, 0, DISPLAY_WIDTH, DISPLAY_HEIGHT);

		// use White color to render text
		g.setColor(Colors.WHITE);

		g.drawString(message, DISPLAY_WIDTH / 2, DISPLAY_HEIGHT / 2,
				GraphicsContext.HCENTER | GraphicsContext.VCENTER);

	}

	@Override
	public EventHandler getController() {
		return this;
	}

	@Override
	public boolean handleEvent(int event) {
		boolean eventProcessed = false;

		// Gets the event generator.
		final int genId = Event.getGeneratorID(event);
		EventGenerator gen;
		try {
			gen = EventGenerator.get(genId);
		} catch (IndexOutOfBoundsException e) {
			gen = null;
			message = "unknown event " + event;
		}

		if (gen != null) {
			// Gets the type of event.
			final int type = Event.getType(event);
			if (Event.POINTER == type) {
				Pointer pointer = (Pointer) gen;

				pointerX = pointer.getAbsoluteX();
				pointerY = pointer.getAbsoluteY();

				if (Pointer.isReleased(event)) {
					nbClicks++;
				}

				message = "NbClicks : " + nbClicks + " - Last Click at : " + pointerX + " , " + pointerY;

			} else { // The event is NOT a pointer.
				// Gets the event raw data.
				final int data = Event.getData(event);
				message = "Event not managed, type=" + type + " data=" + data;
			}
			eventProcessed = true;
		}

		if (eventProcessed) {
			this.repaint();
		}
		return eventProcessed;
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

		InputEvents sample = new InputEvents(display);
		sample.show();
	}


}
