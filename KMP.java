/*
 * Knuth-Morris-Pratt Algorithm:
 * Given 2 Strings - a and b - check if b is contained in a.
 * If b is contained in a, return the first position in a that b is located.
 * If b is not contained in a, return -1.
 * 
 * If:
 * - String a is size N
 * - String b is size M
 * 
 * This algorithm will take O(N+M) time and O(M) space.
 * 
 */

public class KMP {

	// the position that String b is in String a
	// returns -1 if String b is not in String a
	public static int compare(String a, String b) {
		int[] temp = getTemp(b);
		int pos = 0;
		for(int i = 0; i < a.length(); i++) {
			if(b.charAt(pos) == a.charAt(i))
				pos++;
			else {
				if(pos > 0 && temp[pos-1] > 0) {
					pos = temp[pos-1];
					i--;
				} else
					pos = 0;
			}
			if(pos == b.length())
				return i-b.length()+1;
		}
		return -1;
	}

	// gets suffix/prefix temp array of a String
	public static int[] getTemp(String s) {
		int j = 0;
		int[] temp = new int[s.length()];
		for (int i = 1; i < temp.length; i++) {
			while (j > 0 && s.charAt(i) != s.charAt(j))
				j = temp[j - 1];
			temp[i] = j;
			if (s.charAt(j) == s.charAt(i)) {
				temp[i]++;
				j++;
			}
		}
		return temp;
	}

	// driver method
	public static void main(String[] args) {
		String a = "qjeuemdixkdgnozfxlzk";
		String b = "qeue";

		System.out.println(compare(a, b));
	}

}
