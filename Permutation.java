import java.util.HashMap;
import java.util.HashSet;

public class Permutation {

	// with duplicates (prints it out)
	public static void permute(String s, String answer) {
		if (s.length() == 0) {
			System.out.println(answer);
			return;
		}

		for (int i = 0; i < s.length(); i++) {
			char ch = s.charAt(i);
			String left_substr = s.substring(0, i);
			String right_substr = s.substring(i + 1);
			String rest = left_substr + right_substr;
			permute(rest, answer + ch);
		}
	}
	
	// get all unique permutations of string str (no duplicates)
	public static void permuteNoDups(HashSet<String> seen, String str, int l, int r) {
		if (l == r) {
			if (!seen.contains(str)) {
				seen.add(str);
				System.out.println(str);
			}
		} else {
			for (int i = l; i <= r; i++) {
				str = swap(str, l, i);
				permuteNoDups(seen, str, l + 1, r);
				str = swap(str, l, i);
			}
		}
	}

	// swap character at position i and j in string a
	public static String swap(String a, int i, int j) {
		char temp;
		char[] charArray = a.toCharArray();
		temp = charArray[i];
		charArray[i] = charArray[j];
		charArray[j] = temp;
		return String.valueOf(charArray);
	}
	
	// calculate the number of unique permutations of String a
	public static long calculateNumberOfPermutations(String a) {
		long[] factorial = new long[a.length()+1];
		factorial[0] = factorial[1] = 1;
		for(int i = 2; i < factorial.length; i++)
			factorial[i] = i*factorial[i-1];
		
		HashMap<Character, Integer> count = new HashMap<Character, Integer>();
		for(int i = 0; i < a.length(); i++) {
			char c = a.charAt(i);
			if(!count.containsKey(c))
				count.put(c, 1);
			else
				count.put(c, count.get(c)+1);
		}
		long ans = factorial[a.length()];
		for(char c: count.keySet())
			ans /= factorial[count.get(c)];
		return ans;
	}

	public static void main(String[] args) {
		String s = "ABAD";

		System.out.println("All possible permutations of string " + s + " are:");
		permute(s, "");
		System.out.println();
		
		System.out.println("All unique permutations of string " + s + " are:");
		permuteNoDups(new HashSet<String>(), s, 0, s.length()-1);
		System.out.println();
		
		System.out.println("String \"" + s + "\" has " + calculateNumberOfPermutations(s) + " unique permutations!");
		System.out.println();
		
	}
}
