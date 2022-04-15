/*
 * Prime Factorization:
 * 
 * Given an integer n, find all prime factors of n
 * 
 */

import java.util.ArrayList;

public class PrimeFactorization {

	public static ArrayList<Integer> getFactors(int n) {
		ArrayList<Integer> ret = new ArrayList<Integer>();
		
		// Print the number of 2s that divide n
		while (n % 2 == 0) {
			ret.add(2);
			n /= 2;
		}

		// n must be odd at this point. So we can
		// skip one element (Note i = i +2)
		for (int i = 3; i <= Math.sqrt(n); i += 2) {
			// While i divides n, print i and divide n
			while (n % i == 0) {
				ret.add(i);
				n /= i;
			}
		}

		// This condition is to handle the case when
		// n is a prime number greater than 2
		if (n > 2)
			ret.add(n);
		
		return ret;
	}

	public static void main(String[] args) {

		int num = 5215;
		System.out.println(getFactors(num));
		
		num = 128592;
		System.out.println(getFactors(num));
	}

}
