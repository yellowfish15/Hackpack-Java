import java.util.Arrays;

/*
 * Range Minimum Query using Sparse Table:
 * 
 * Queries the minimum between a range of values
 * in O(1) time. Assumes that there will no updates
 * to the list of values
 * 
 */

class RMQ {

	// table[i][j] stores the minimum
	// index from the range i to i+2^j
	// in the array "nums"
	int[][] table;
	int[] nums;
	
	public RMQ(int[] array) {
		int pow = (int) Math.floor(Math.log(array.length)/Math.log(2));
		table = new int[array.length][pow+1];
		for(int[] temp: table)
			Arrays.fill(temp, Integer.MAX_VALUE);
		
		nums = new int[array.length];
		for(int i = 0; i < array.length; i++) {
			nums[i] = array[i];
			table[i][0] = array[i];
		}
		
		// constructs table matrix
		for(int j = 1; j <= pow; j++) { // 2^j sized range
			for(int i = 0; i < array.length; i++) { // start
				int R = i+(int)(Math.pow(2, j))-1;
				if(R >= array.length)
					continue;
				
				if(table[i][j-1] < table[i+(int)Math.pow(2, j-1)][j-1])
					table[i][j] = table[i][j-1];
				else
					table[i][j] = table[R-(int)Math.pow(2, j-1)+1][j-1];
			}
		}
	}
	
	// gets the minimum value between
	// arr[L] and arr[R] inclusive
	public int getMin(int L, int R) {		
		int j = (int)Math.floor(Math.log(R-L+1)/Math.log(2));
		return Math.min(table[L][j], table[(R-(int)Math.pow(2, j)+1)][j]);
	}
	
	// Driver Method
	public static void main(String[] args) {
		
		int[] array = {7, 2, 3, 0, 5, 10, 3, 12, 18};
		RMQ st = new RMQ(array);
		System.out.println(st.getMin(4, 6));
	}
	
	

}
