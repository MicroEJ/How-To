/*
 * Java
 *
 * Copyright 2018-2019 MicroEJ Corp. All rights reserved. 
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.mock.style;

import ej.style.font.FontProfile;
import ej.style.font.loader.AbstractFontLoader;

/**
 * The font loader used in the application.
 */
public class WidgetsFontLoader extends AbstractFontLoader {

	private static final int LARGE_HEIGHT = 30;
	private static final int MEDIUM_HEIGHT = 20;
	private static final int SMALL_HEIGHT = 18;

	@Override
	protected int getFontHeight(FontProfile fontProfile) {
		switch (fontProfile.getSize()) {
		case LENGTH:
			return fontProfile.getSizeValue();
		case LARGE:
			return LARGE_HEIGHT;
		case SMALL:
			return SMALL_HEIGHT;
		case MEDIUM:
		default:
			return MEDIUM_HEIGHT;
		}
	}

}
