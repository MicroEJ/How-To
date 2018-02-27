/*
 * Java
 *
 * Copyright 2018 IS2T. All rights reserved.
 * IS2T PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mycompany;

public class MyLib {

	/**
	 * @see API javadoc
	 */
	public static int factorial(int number) {
		if (number < 0) {
			throw new IllegalArgumentException("Factorial cannot be negative");
		}
		if (number == 0 || number == 1) {
			return 1;
		}
		return MyLibNatives.factorial(number);
	}
}