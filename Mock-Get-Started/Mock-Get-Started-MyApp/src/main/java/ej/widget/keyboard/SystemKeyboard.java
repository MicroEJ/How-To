/*
 * Java
 *
 * Copyright 2018 IS2T. All rights reserved.
 * For demonstration purpose only.
 * IS2T PROPRIETARY. Use is subject to license terms.
 */
package ej.widget.keyboard;

import ej.microui.event.generator.Keyboard;

/**
 * Represents a simple keyboard registered in the system pool
 */
public class SystemKeyboard extends Keyboard {

	/**
	 * Constructor
	 */
	public SystemKeyboard() {
		super(1);
		addToSystemPool();
	}
}
