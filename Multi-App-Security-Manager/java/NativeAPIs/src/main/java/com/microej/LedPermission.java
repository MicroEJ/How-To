/*
 * Java
 *
 * Copyright 2018 IS2T. All rights reserved.
 * For demonstration purpose only.
 * IS2T PROPRIETARY. Use is subject to license terms.
 */
package com.microej;

import java.security.BasicPermission;

/**
 * Permission to access to the Led.
 */
public class LedPermission extends BasicPermission {

	private static final String LED = "LED";

	/**
	 * Get a singleton to a {@link LedPermission}.
	 */
	public static final LedPermission INSTANCE = new LedPermission();

	private LedPermission() {
		super(LED);
	}

}
