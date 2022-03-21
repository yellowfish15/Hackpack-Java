import java.util.*;

public class Permutation {

	// with duplicates (prints it out)
	public static void permute(String s, String answer) {
		if (s.length() == 0) {
			System.out.print(answer + ",");
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

	// returns set with permutations
	// removes duplicates
	public static void permuteSet(String s, String answer, HashSet<String> set) {
		if(set.contains(answer))
			return;
		
		if (s.length() == 0) {
			set.add(answer);
			return;
		}

		for (int i = 0; i < s.length(); i++) {
			char ch = s.charAt(i);
			String left_substr = s.substring(0, i);
			String right_substr = s.substring(i + 1);
			String rest = left_substr + right_substr;
			permuteSet(rest, answer + ch, set);
		}
	}

	public static void main(String[] args) {
		String s = "aaa";

		System.out.println("All possible permutations of string " + s + " are:");
		permute(s, "");

		HashSet<String> permutations = new HashSet<String>();
		permuteSet(s, "", permutations);
		
		System.out.println("\n\nAll unique permutations of string " + s + " are:");
		for(String p: permutations)
			System.out.println(p);
	}
}
