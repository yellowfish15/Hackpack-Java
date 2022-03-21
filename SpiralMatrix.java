/*
 * Spiral Matrix Implementation
 * Print out a spiral matrix of size N x M
 */

public class SpiralMatrix {

	public static void main(String[] args) {
		int[] dirH = { 1, 0, -1, 0 }; // horizontal direction (columns)
		int[] dirV = { 0, 1, 0, -1 }; // vertical direction (rows)

		int N = 20;
		int M = 25;
		int[][] grid = new int[N][M];
		int currR = 0;
		int currC = -1;
		int num = 1;
		int steps = 0;
		int dir = 0;
		while (steps < N && steps < M) {
			for (int i = 0; i < (dir%2==0?M-steps:N-steps); i++) {
				currR += dirV[dir];
				currC += dirH[dir];
				grid[currR][currC] = num++;
			}

			dir++;
			dir %= 4;
			if (dir % 2 == 1)
				steps++;
		}
		
		// get number of digits in last number
		int maxLen = (int)(Math.log10(num-1)+1);

		// print out matrix
		for (int r = 0; r < N; r++) {
			for (int c = 0; c < M; c++)
				System.out.printf("%-" + maxLen + "s ", grid[r][c]);
			System.out.println();
		}
	}
}
