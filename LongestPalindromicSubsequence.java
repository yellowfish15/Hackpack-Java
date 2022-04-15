/*
 * Given a string, find the Longest Palindromic Subsequence
 * in O(N^2) time
 * 
 */

public class LongestPalindromicSubsequence {

	public static String getSequence(String s1) {
		String s2 = new StringBuilder(s1).reverse().toString();
		int N = s1.length();
		String[][] dp = new String[N+1][N+1];
		for (int i = 0; i <= N; i++)
			dp[i][0] = dp[0][i] = "";
		for(int i = 1; i <= N; i++) {
			for(int j = 1; j <= N; j++) {
				// take either dp[i-1][j] or dp[i][j-1] depending on which string is longer
				dp[i][j] = dp[i-1][j].length() > dp[i][j-1].length()?dp[i-1][j]:dp[i][j-1];
				// if character at i and j are the same, check upper-left diagonal
				if(s1.charAt(i-1) == s2.charAt(j-1))
					if(dp[i-1][j-1].length()+1 > dp[i][j].length())
						dp[i][j] = dp[i-1][j-1] + s1.charAt(i-1);
			}
		}
		return dp[N][N];
	}

	public static void main(String[] args) {

		String str = "6105273940456320451987605431261";
		System.out.println(getSequence(str));
		
		String str2 = "racecar";
		System.out.println(getSequence(str2));
		
		String str3 = "apple";
		System.out.println(getSequence(str3));
	}
}
