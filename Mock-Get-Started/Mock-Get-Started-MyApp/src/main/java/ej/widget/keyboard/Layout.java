/*
 * Java
 *
 * Copyright 2018 IS2T. All rights reserved.
 * For demonstration purpose only.
 * IS2T PROPRIETARY. Use is subject to license terms.
 */
package ej.widget.keyboard;

/**
 * Represents a keyboard layout
 */
public interface Layout {

	/**
	 * Gets the characters of the first row of a keyboard
	 *
	 * @return the string containing each character of the first row
	 */
	public abstract String getFirstRow();

	/**
	 * Gets the characters of the second row of a keyboard
	 *
	 * @return the string containing each character of the second row
	 */
	public abstract String getSecondRow();

	/**
	 * Gets the characters of the third row of a keyboard
	 *
	 * @return the string containing each character of the third row
	 */
	public abstract String getThirdRow();
}
