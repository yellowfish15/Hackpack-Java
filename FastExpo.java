import java.math.BigInteger;

/* 
 * Fast (Binary) Exponentiation:
 * 
 * Gets the answer for numbers raised to very large exponents
 * (Specifically in log time)
 * 
 * Added functionality for BigInteger (4/17/2022)
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
	
	// same thing as "pow" method but with BigInteger
	// does not do modulo because that is unnecessary
	public static BigInteger bigpow(BigInteger k, BigInteger exp) {
		if(exp.compareTo(new BigInteger("0")) == 0) {
			return new BigInteger("1");
		}
		if(exp.compareTo(new BigInteger("1")) == 0) {
			return k;
		} else if(exp.mod(new BigInteger("2")).compareTo(new BigInteger("0")) == 0) {
			return bigpow(k.multiply(k), exp.divide(new BigInteger("2")));
		} else { 
			return k.multiply(bigpow(k.multiply(k), exp.divide(new BigInteger("2"))));
		}
	}

	// Driver method
	public static void main(String[] args) {

		// with longs
		long k = 200;
		long exponent = 200;

		// stores k^exponent
		long answer = pow(k, exponent);
		System.out.println(answer);
		
		
		// now with BigInteger
		BigInteger bigK = new BigInteger("200");
		BigInteger bigExp = new BigInteger("200");
		
		BigInteger bigAnswer = bigpow(bigK, bigExp);
		System.out.println(bigAnswer);
	}
}
