/*
 * Manachers Algorithm:
 * This algorithm allows you to find the length
 * of the longest palindromic subsequence within a
 * given String in O(N) time if the String is length N.
 * 
 * The implementation below only works on Strings
 * that don't use the special characters '$', '@', and '#'
 * 
 * Any other ascii character is valid
 * 
 * If the String could contain the three special characters above,
 * then simply change the value of those characters to ones that
 * aren't used in the createArr method below
 */
public class Manachers {

	// searches for the length of the longest palindromic in str
	public static int compute(String str) {
		if (str.isEmpty())
			return 0;
		char[] T = createArr(str); // padded string
		// stores max length of palindrome starting at each character
		int[] P = new int[T.length];
		// stores the center and right boundary of current largest palindrome
		int C = 0, R = 0;

		int maxLen = 0;
		for (int i = 1; i < T.length - 1; i++) {
			int mirr = 2 * C - i;
			if (i < R)
				P[i] = Math.min(R - i, P[mirr]);
			while (T[i + (1 + P[i])] == T[i - (1 + P[i])])
				P[i]++;
			if (i + P[i] > R) {
				C = i;
				R = i + P[i];
			}
			maxLen = Math.max(P[i], maxLen);
		}
		return maxLen;
	}

	// convert str into a char array
	// pad the spaces in between with special characters
	public static char[] createArr(String str) {
		char[] arr = new char[str.length() * 2 + 3];
		for (int i = 1; i < arr.length - 1; i++)
			arr[i] = i % 2 == 0 ? str.charAt((i / 2) - 1) : '#';
		arr[0] = '$'; // special character #1
		arr[arr.length - 1] = '@'; // special character #2
		return arr;
	}

	// driver method
	public static void main(String[] args) {

		String str = "baaaab";
		System.out.println(compute(str));
	}

}
