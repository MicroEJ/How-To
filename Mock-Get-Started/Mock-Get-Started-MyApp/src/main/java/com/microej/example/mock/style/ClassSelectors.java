/*
 * Java
 *
 * Copyright 2018-2019 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.mock.style;

// TODO: Auto-generated Javadoc
/**
 * Class selectors used in the stylesheet.
 */
public enum ClassSelectors {


	/**
	 * The form class selector.
	 */
	FORM(1),

	/**
	 * The result label class selector.
	 */
	RESULT_LABEL(2),

	/**
	 * The space class selector.
	 */
	SPACE_KEY_SELECTOR(3),
	/**
	 * The backspace class selector.
	 */
	BACKSPACE_KEY_SELECTOR(4),
	/**
	 * The shift key inactive class selector.
	 */
	SHIFT_KEY_INACTIVE_SELECTOR(5),
	/**
	 * The shift key active class selector.
	 */
	SHIFT_KEY_ACTIVE_SELECTOR(6),
	/**
	 * The switch mapping class selector.
	 */
	SWITCH_MAPPING_KEY_SELECTOR(7),
	/**
	 * The special key class selector.
	 */
	SPECIAL_KEY_SELECTOR(8),
	/**
	 * The selection class selector.
	 */
	CLASS_SELECTOR_SELECTION(9),
	/**
	 * The clear button class selector.
	 */
	CLASS_SELECTOR_CLEAR_BUTTON(10);

	/** The num. */
	int num;

	/**
	 * Instantiates a new class selectors.
	 *
	 * @param num the num
	 */
	ClassSelectors(int num) {
		this.num = num;
	}

	/** The get value. */
	public int getValue() {
		return this.num;
	}
}
