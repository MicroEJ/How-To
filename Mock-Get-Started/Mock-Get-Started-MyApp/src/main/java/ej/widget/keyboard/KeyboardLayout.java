/*
 * Copyright 2017-2021 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package ej.widget.keyboard;

/**
 * Represents a keyboard layout.
 */
public class KeyboardLayout {

	/**
	 * Lower case layout index.
	 */
	public static final int LOWER_CASE_LAYOUT_INDEX = 0;
	/**
	 * Upper case layout index.
	 */
	public static final int UPPER_CASE_LAYOUT_INDEX = 1;
	/**
	 * Numeric layout index.
	 */
	public static final int NUMERIC_LAYOUT_INDEX = 2;
	/**
	 * Symbol layout index.
	 */
	public static final int SYMBOL_LAYOUT_INDEX = 3;

	private static final String[] LOWER_CASE_LAYOUT = { "qwertyuiop", //$NON-NLS-1$
			"asdfghjkl\u0008", //$NON-NLS-1$
	"zxcvbnm" }; //$NON-NLS-1$
	private static final String[] UPPER_CASE_LAYOUT = { "QWERTYUIOP", //$NON-NLS-1$
			"ASDFGHJKL\u0008", //$NON-NLS-1$
	"ZXCVBNM" }; //$NON-NLS-1$
	private static final String[] NUMERIC_LAYOUT = { "1234567890", //$NON-NLS-1$
			"-/:;()$&@\"", //$NON-NLS-1$
	".,?!\'§¤" }; //$NON-NLS-1$
	private static final String[] SYMBOL_LAYOUT = { "[]{}#%^*+=", //$NON-NLS-1$
			"_\\|~<>€£¥\u25cf", //$NON-NLS-1$
	".,?!\'§¤" }; //$NON-NLS-1$
	private int currentLayout;

	/**
	 * Gets the characters of the first row of a keyboard.
	 *
	 * @return the string containing each character of the first row
	 */
	public String getFirstRow() {
		return getLayoutRowContent(0);
	}

	/**
	 * Gets the characters of the second row of a keyboard.
	 *
	 * @return the string containing each character of the second row
	 */
	public String getSecondRow() {
		return getLayoutRowContent(1);
	}

	/**
	 * Gets the characters of the third row of a keyboard.
	 *
	 * @return the string containing each character of the third row
	 */
	public String getThirdRow() {
		return getLayoutRowContent(2);
	}

	/**
	 * Get current keyboard layout.
	 *
	 * @return keyboard layout
	 */
	public int getCurrentLayout() {
		return this.currentLayout;
	}

	/**
	 * Set layout between LOWER_CASE_LAYOUT_INDEX and SYMBOL_LAYOUT_INDEX.
	 *
	 * @param layout
	 *            keyboard layout to be imposed
	 */
	public void setCurrentLayout(int layout) {
		if (layout < LOWER_CASE_LAYOUT_INDEX) {
			layout = LOWER_CASE_LAYOUT_INDEX;
		} else if (layout > SYMBOL_LAYOUT_INDEX) {
			layout = SYMBOL_LAYOUT_INDEX;
		}
		this.currentLayout = layout;
	}

	private String getLayoutRowContent(int row) {
		String result;
		switch (this.currentLayout) {
		case LOWER_CASE_LAYOUT_INDEX:
			result = LOWER_CASE_LAYOUT[row];
			break;
		case UPPER_CASE_LAYOUT_INDEX:
			result = UPPER_CASE_LAYOUT[row];
			break;
		case NUMERIC_LAYOUT_INDEX:
			result = NUMERIC_LAYOUT[row];
			break;
		default:
			result = SYMBOL_LAYOUT[row];
			break;
		}

		assert result != null;
		return result;
	}
}
