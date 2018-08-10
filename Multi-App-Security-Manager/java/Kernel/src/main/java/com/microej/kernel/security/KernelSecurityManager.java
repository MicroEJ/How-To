/*
 * Java
 *
 * Copyright 2013-2018 IS2T. All rights reserved.
 * For demonstration purpose only.
 * IS2T PROPRIETARY. Use is subject to license terms.
 */
package com.microej.kernel.security;

import java.security.Permission;
import java.util.HashMap;

import ej.kf.Feature;
import ej.kf.Kernel;

/**
 * An example of {@link SecurityManager} implementation based on <code>Kernel/Feature</code> runtime model.
 */
public class KernelSecurityManager extends SecurityManager {

	/**
	 * Registered permission checks
	 */
	private final HashMap<Class<? extends Permission>, FeaturePermissionChecker> permissionsMap;

	public KernelSecurityManager(){
		// Initialize permissions checks
		permissionsMap = new HashMap<Class<? extends Permission>, FeaturePermissionChecker>();

	}

	/**
	 * Sets a {@link FeaturePermissionChecker} for a specific permission.
	 *
	 * @param permission
	 *            the permission to check.
	 * @param checker
	 *            the {@link FeaturePermissionChecker} used.
	 */
	public void setFeaturePermissionChecker(Class<? extends Permission> permission, FeaturePermissionChecker checker){
		permissionsMap.put(permission, checker);
	}

	/**
	 * Removes the {@link FeaturePermissionChecker} for a permission.
	 *
	 * @param permission
	 */
	public void removeFeaturePermissionChecker(Class<? extends Permission> permission) {
		permissionsMap.remove(permission);
	}

	@Override
	public void checkPermission(Permission permission) {
		if(!Kernel.isInKernelMode()){
			Feature feature = (Feature)Kernel.getContextOwner();
			Kernel.enter();
			FeaturePermissionChecker checker = permissionsMap.get(permission.getClass());
			if(checker != null){
				checker.checkPermission(permission, feature);
			} else {
				noCheckerFound(permission, feature);
			}
		} else {
			kernelPermission(permission);
		}
	}

	/**
	 * Checks if the kernel has the permission.
	 *
	 * @param permission
	 *            the {@link Permission}
	 * @throws SecurityException
	 *             if the kernel doesn't have Permission
	 */
	private void kernelPermission(Permission permission) throws SecurityException {
		// Kernel has all the rights: no checks
	}

	/**
	 * Manages when no registered {@link FeaturePermissionChecker} for this kind of
	 * Permission.
	 *
	 * @param permission
	 *            the {@link Permission}
	 * @param feature
	 *            the {@link Feature}
	 *
	 * @throws SecurityException
	 *             if the feature doesn't have Permission
	 */
	private void noCheckerFound(Permission permission, Feature feature) throws SecurityException {
		// Implementation choice is to always throw a SecurityException by default.
		throw new SecurityException();
	}
}
