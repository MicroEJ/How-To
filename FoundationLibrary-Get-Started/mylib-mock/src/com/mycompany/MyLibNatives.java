package com.mycompany;

/**
 * Class define all native.
 */
public class MyLibNatives {

	public static int factorial(int number){
		if (number == 0) {
			return 1;
		}
		int fact = 1; // this will be the result
		for (int i = 1; i <= number; i++) {
			fact *= i;
		}
		return fact;
	}
}
