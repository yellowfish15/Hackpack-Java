/*
 * Greatest Common Divisor and Least Common Multiple:
 * 
 * The two methods below find the greatest common 
 * divisor of two numbers in O(log(min(a,b)))
 * 
 * The Least Common Multiple method uses the GCD method
 * 
 */

public class GCDLCM {

	// find greatest common divisor of a and b
	public static int GCD(int a, int b) {
		while(b != 0) {
			a %= b;
			int temp = a;
			a = b;
			b = temp;
		}
		return a;
	}
	
	// finds least common multiple of a and b
	public static int LCM(int a, int b) {
		return (a/GCD(a, b))*b;
	}
	
	public static void main(String[] args) {
		System.out.println("The GCD of 9 and 6 is " + GCD(9, 6));
		System.out.println("The LCM of 6 and 9 is " + LCM(6, 9));
		System.out.println("The GCD of 112 and 64 is " + GCD(112, 64));
		System.out.println("The LCM of -3 and 5 is " + LCM(-3, 5));
	}

}
