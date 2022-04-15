/*
 * Longest Common Subsequence Implementation:
 * 
 * Given two strings, s1 and s2, find a longest
 * common subsequence between them
 * 
 * Use Dynamic Programming
 * 
 */


public class LongestCommonSubsequence {
 
	public static String getSequence(String s1, String s2) {
		int N = s1.length();
		int M = s2.length();
		String[][] dp = new String[N+1][M+1];
		for (int i = 0; i <= N; i++)
			dp[i][0] = "";
		for (int i = 0; i <= M; i++)
			dp[0][i] = "";
		for(int i = 1; i <= N; i++) {
			for(int j = 1; j <= M; j++) {
				// take either dp[i-1][j] or dp[i][j-1] depending on which string is longer
				dp[i][j] = dp[i-1][j].length() > dp[i][j-1].length()?dp[i-1][j]:dp[i][j-1];
				// if character at i and j are the same, check upper-left diagonal
				if(s1.charAt(i-1) == s2.charAt(j-1))
					if(dp[i-1][j-1].length()+1 > dp[i][j].length())
						dp[i][j] = dp[i-1][j-1] + s1.charAt(i-1);
			}
		}
		return dp[N][M];
	}
	
	public static void main(String[] args) {
		
		String common = getSequence("aeogfwaoefhw", "aouwfognawg");
		System.out.println(common);

	}

}
