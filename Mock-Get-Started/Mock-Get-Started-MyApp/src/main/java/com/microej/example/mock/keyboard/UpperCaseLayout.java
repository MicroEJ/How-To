/*
 * Java
 *
 * Copyright 2018-2022 MicroEJ Corp. All rights reserved. 
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.mock.keyboard;

import ej.widget.keyboard.Layout;

/**
 * Represents an upper case layout
 */
public class UpperCaseLayout implements Layout {

	@Override
	public String getFirstRow() {
		return "QWERTYUIOP"; //$NON-NLS-1$
	}

	@Override
	public String getSecondRow() {
		return "ASDFGHJKL\00"; //$NON-NLS-1$
	}

	@Override
	public String getThirdRow() {
		return "ZXCVBNM"; //$NON-NLS-1$
	}
}
