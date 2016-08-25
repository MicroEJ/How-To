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

	public InputEvents(Display display) {
		super(display);
		this.display = display;
		nbClicks = 0;
		pointerX = 0;
		pointerY = 0;
	}

	@Override
	public void paint(GraphicsContext g) {

		// fill up background with black
		g.setColor(Colors.BLACK);
		g.fillRect(0, 0, display.getWidth(), display.getHeight());

		// use White color to render text
		g.setColor(Colors.WHITE);


		String pointerMessage = "NbClicks : " + nbClicks + " - Last Click at : " + pointerX + " , " + pointerY;
		g.drawString(pointerMessage, display.getWidth() / 2, display.getHeight() / 2,
				GraphicsContext.HCENTER | GraphicsContext.VCENTER);

	}

	@Override
	public EventHandler getController() {
		return this;
	}

	@Override
	public boolean handleEvent(int event) {
		boolean eventWasProcessed = false;

		// Gets the event generator.
		final int genId = Event.getGeneratorID(event);
		EventGenerator gen;
		try {
			gen = EventGenerator.get(genId);
		} catch (IndexOutOfBoundsException e) {
			gen = null;
			System.out.println("unknown event");
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

				eventWasProcessed = true;
			} else { // The event was a pointer.
				// Gets the event raw data.
				final int data = Event.getData(event);
				System.out.println("Event not managed, type=" + type + " data=" + data);
			}
		}

		if (eventWasProcessed) {
			this.repaint();
		}
		return eventWasProcessed;
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
