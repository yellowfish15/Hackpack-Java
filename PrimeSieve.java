import java.util.ArrayList;

/*
 * Sieve of Eratosthenes:
 * 
 * Return all prime numbers between 1 and N
 * in O(N log (log N)) time
 * 
 */

public class PrimeSieve {

	public static ArrayList<Integer> getPrimes(int N) {
		ArrayList<Integer> ret = new ArrayList<Integer>();

		boolean prime[] = new boolean[N + 1];
		for (int i = 2; i <= N; i++)
			prime[i] = true;

		for (int p = 2; p * p <= N; p++)
			if (prime[p])
				for (int i = p * p; i <= N; i += p)
					prime[i] = false;
		
		for(int i = 2; i <= N; i++) 
			if(prime[i])
				ret.add(i);
		return ret;
	}

	public static void main(String[] args) {
		
		int num1 = 521;
		System.out.println("All of the primes between 2 and " + num1 + " are:\n" + getPrimes(num1));
		System.out.println();
		
		int num2 = 42;
		System.out.println("All of the primes between 2 and " + num2 + " are:\n" + getPrimes(num2));
		System.out.println();
		
		int num3 = 134;
		System.out.println("All of the primes between 2 and " + num3 + " are:\n" + getPrimes(num3));
		System.out.println();
		
	}

}
