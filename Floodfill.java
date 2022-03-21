/*
 * Flood-fill Implementation
 * Includes cardinal directions as well as diagonals
 * 
 */

import java.io.*;
import java.util.*;

public class Floodfill {
	
	// grid traversal directions if needed
	// (these variables aren't used in this implementation, 
	// they're just for reference)
	static String[] dirs = {"N", "NE", "E", "SE", "S", "SW", "W", "NW"};
	static int[] dirR = {-1,-1,0,1,1,1,0,-1};
	static int[] dirC = {0,1,1,1,0,-1,-1,-1};
	
	static class Pair {
		int row, col;

		public Pair(int row, int col) {
			this.row = row;
			this.col = col;
		}
	}

	public static void main(String[] args) throws IOException {
		int ROWS = 4;
		int COLS = 7;
		int[][] grid = { { 0, 3, 4, 4, 2, 9, 4 }, { 4, 2, 4, 3, 2, 1, 8 }, { 4, 2, 4, 3, 2, 1, 8 },
				{ 2, 0, 4, 4, 4, 4, 5 } };
		boolean[][] vis = new boolean[ROWS][COLS];
		
		// starting row and starting column
		int startRow = 3;
		int startCol = 0;
		vis[startRow][startCol] = true;
		int val = grid[startRow][startCol];

		LinkedList<Pair> q = new LinkedList<Pair>();
		q.add(new Pair(startRow, startCol));
		while (!q.isEmpty()) {
			Pair p = q.poll();
			int r = p.row;
			int c = p.col;
			
			// do something to this position (update value, etc.)
			
			// check north
			if (r > 0 && grid[r - 1][c] == val && !vis[r - 1][c]) {
				q.push(new Pair(r - 1, c));
				vis[r - 1][c] = true;
			}
			// check south
			if (r < ROWS - 1 && grid[r + 1][c] == val && !vis[r + 1][c]) {
				q.push(new Pair(r + 1, c));
				vis[r + 1][c] = true;
			}
			// check west
			if (c > 0 && grid[r][c - 1] == val && !vis[r][c - 1]) {
				q.push(new Pair(r, c - 1));
				vis[r][c - 1] = true;
			}
			// check east
			if (c < COLS - 1 && grid[r][c + 1] == val && !vis[r][c + 1]) {
				q.push(new Pair(r, c + 1));
				vis[r][c + 1] = true;
			}

			// consider diagonals too
			/*
			// check north-west
			if (r > 0 && c > 0 && grid[r - 1][c - 1] == val && !vis[r - 1][c - 1]) {
				q.push(new Pair(r - 1, c - 1));
				vis[r - 1][c - 1] = true;
			}
			// check north-east
			if (r > 0 && c < COLS - 1 && grid[r - 1][c + 1] == val && !vis[r - 1][c + 1]) {
				q.push(new Pair(r - 1, c + 1));
				vis[r - 1][c + 1] = true;
			}
			// check south-west
			if (r < ROWS - 1 && c > 0 && grid[r + 1][c - 1] == val && !vis[r + 1][c - 1]) {
				q.push(new Pair(r + 1, c - 1));
				vis[r + 1][c - 1] = true;
			}
			// check south-east
			if (r < ROWS - 1 && c < COLS - 1 && grid[r + 1][c + 1] == val && !vis[r + 1][c + 1]) {
				q.push(new Pair(r + 1, c + 1));
				vis[r + 1][c + 1] = true;
			}
			*/
		}
	}
}