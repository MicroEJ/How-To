/*
 * Java
 *
 * Copyright 2018-2019 MicroEJ Corp. All rights reserved. 
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.mock.keyboard;

import ej.widget.keyboard.Layout;

/**
 * Represents a numeric layout
 */
public class NumericLayout implements Layout {

	@Override
	public String getFirstRow() {
		return "1234567890"; //$NON-NLS-1$
	}

	@Override
	public String getSecondRow() {
		return "-/:;()$&@\""; //$NON-NLS-1$
	}

	@Override
	public String getThirdRow() {
		return ".,?!\'§¤"; //$NON-NLS-1$
	}
}
