/*
 * Java
 *
 * Copyright 2018 IS2T. All rights reserved.
 * For demonstration purpose only.
 * IS2T PROPRIETARY. Use is subject to license terms.
 */
package com.microej.kernel.security;

import java.security.Permission;

import com.microej.LedPermission;

import ej.kf.Feature;

/**
 * Checker for the {@link LedPermission}. Authorize only the feature with the
 * name "Hello".
 */
public class LedPermissionChecker implements FeaturePermissionChecker {

	@Override
	public void checkPermission(Permission p, Feature f) throws SecurityException {
		String name = f.getName();
		if (name == null || !name.equals("Hello")) {
			throw new SecurityException();
		}
	}

}
