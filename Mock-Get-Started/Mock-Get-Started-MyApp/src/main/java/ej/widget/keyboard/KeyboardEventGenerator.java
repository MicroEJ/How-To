/*
 * Copyright 2010-2022 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package ej.widget.keyboard;

import ej.microui.event.Event;
import ej.microui.event.EventGenerator;

/**
 * A Keyboard event generator generates events containing a character.
 */
public class KeyboardEventGenerator extends EventGenerator {

	/**
	 * ID of the keyboard event generator.
	 */
	public static final int EVENTGENERATOR_ID = 0x10;

	/**
	 * @return {@link #EVENTGENERATOR_ID}
	 */
	@Override
	public int getEventType() {
		return EVENTGENERATOR_ID;
	}

	/**
	 * Send a keyboard event to the application.
	 *
	 * @param c
	 *            the character to send.
	 */
	public void send(char c) {
		int microUIevent = Event.buildEvent(getEventType(), this, c);
		sendEvent(microUIevent);
	}

	/**
	 * Gets the character held by the keyboard event.
	 *
	 * @param event
	 *            the keyboard event.
	 * @return the character held by the keyboard event.
	 */
	public char getChar(int event) {
		return (char) Event.getData(event);
	}

}
