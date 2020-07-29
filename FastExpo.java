/* 
 * Fast (Binary) Exponentiation:
 * 
 * Gets the answer for numbers raised to very large exponents
 * (Specifically in log time)
 * 
 */

class FastExpo {

	static final long MOD = 1_000_000_007;

	// k^(2n) = (k^2)^n
	// k^(2n+1) = k^2n*k
	public static long pow(long k, long exp) {
		if (exp == 0)
			return 1;
		if (exp == 1)
			return k % MOD;
		else if (exp % 2 == 0)
			return pow(k * k % MOD, exp / 2) % MOD;
		else
			return (k % MOD * pow(k * k % MOD, exp / 2) % MOD) % MOD;

	}

	// Driver method
	public static void main(String[] args) {

		long k = 200;
		long exponent = 200;

		// stores k^exponent
		long answer = pow(k, exponent);
		System.out.println(answer);
	}
}
