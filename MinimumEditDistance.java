/*
 * Levenshtein Distance:
 * 
 * Find the minimum edit distance needed to get from one string to another
 * with Dynamic Programming
 * 
 * Backtracking functionality supported
 * 
 */

import java.util.ArrayList;
import java.util.Collections;

public class MinimumEditDistance {

	// get pure numerical edit distance
	// to get from string a to string b
	public static int getEditDistance(String a, String b) {
		int N = a.length();
		int M = b.length();
		
		int[][] dp = new int[N+1][M+1];
		for(int i = 0; i <= N; i++) {
			for(int j = 0; j <= M; j++) {
				if(i == 0)
					dp[i][j] = j;
				else if(j == 0)
					dp[i][j] = i;
				else if(a.charAt(i-1) == b.charAt(j-1))
					dp[i][j] = dp[i-1][j-1];
				else {
					dp[i][j]=1+Math.min(dp[i-1][j-1], Math.min(dp[i-1][j], dp[i][j-1]));
				}
			}
		}
		return dp[N][M];
	}
	
	// gets edit distance with backtracking
	// to get from string a to string b
	public static ArrayList<String> getEditDistanceBack(String a, String b) {
		
		int N = a.length();
		int M = b.length();
		
		int[][] dp = new int[N+1][M+1];
		
		for(int i = 0; i <= N; i++) {
			for(int j = 0; j <= M; j++) {
				if(i == 0)
					dp[i][j] = j;
				else if(j == 0)
					dp[i][j] = i;
				else if(a.charAt(i-1) == b.charAt(j-1))
					dp[i][j] = dp[i-1][j-1]; // keep character
				else {
					dp[i][j] = dp[i][j-1]; // insert
					if(dp[i-1][j] < dp[i][j]) // remove
						dp[i][j] = dp[i-1][j];
					if(dp[i-1][j-1] < dp[i][j]) // replace
						dp[i][j] = dp[i-1][j-1];
					dp[i][j]+=1;
				}
			}
		}
		
		ArrayList<String> ret = new ArrayList<String>();
		int row = N, col = M;
		
		while(row > 0 || col > 0) {
			if(row > 0 && dp[row-1][col] == dp[row][col]-1) { // remove
				ret.add("Remove '" + a.charAt(row-1) + "' at position " + (row-1));
				row--;
			} else if(col > 0 && dp[row][col-1] == dp[row][col]-1) { // insert
				ret.add("Insert '" + b.charAt(col-1) + "' at position " + (col-1));
				col--;
			} else if(row > 0 && col > 0 && dp[row-1][col-1] == dp[row][col]) { // keep
				//ret.add("Keep '" + a.charAt(row-1) + "' at position " + (row-1));
				row--;
				col--;
			} else if(row > 0 && col > 0 && dp[row-1][col-1] == dp[row][col]-1) { // replace
				ret.add("Replace '" + a.charAt(row-1) + "' at position " + (col-1) + " with '" + b.charAt(col-1) + "'");
				row--;
				col--;
			}
		}
		Collections.reverse(ret);
		return ret;
	}
	
	
	public static void main(String[] args) {
		String str1 = "kangaroo";
		String str2 = "keyboard";
		
		// get from "kangaroo" to "keyboard"
		System.out.println(getEditDistance(str1, str2));
		System.out.println(getEditDistanceBack(str1, str2));
		
		str1 = "tuesday";
		str2 = "thursday";
		
		// get from "tuesday" to "thursday"
		System.out.println(getEditDistance(str1, str2));
		System.out.println(getEditDistanceBack(str1, str2));
		
		str1 = "a";
		str2 = "a";
		
		// get from "a" to "a"
		System.out.println(getEditDistance(str1, str2));
		System.out.println(getEditDistanceBack(str1, str2));
	}
	
}
