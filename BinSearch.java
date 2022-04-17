/*
 * Binary Searches:
 * 
 * Several binary search methods to use at your disposal!
 * 
 * For reference to binary searching on big integers, see "Practice Contest 4-16-2022 (2016 State)"/p6.java
 * 
 */

public class BinSearch {
	
	
	
	// return first index of a value in an array or -1 if value is absent
	public static int searchFirst(int val, int[] arr) {
		int lo = 0, hi = arr.length - 1, m, f = -1;

		// As long as this loop is processing, we need to consider
		// values in the INCLUSIVE range [lo, hi].
		while (lo <= hi) {
			m = (lo + hi) / 2;
			if (arr[m] < val)
				lo = m + 1;
			else {
				if (arr[m] == val)
					f = m;
				hi = m - 1;
			}
		}
		return f;
	}

	// return last index of a value in an array or -1 if value is absent
	public static int searchLast(int val, int[] arr) {
		int lo = 0, hi = arr.length - 1, m, f = -1;

		// As long as this loop is processing, we need to consider
		// values in the INCLUSIVE range [lo, hi].
		while (lo <= hi) {
			m = (lo + hi) / 2;
			if (arr[m] > val)
				hi = m - 1;
			else {
				if (arr[m] == val)
					f = m;
				lo = m + 1;
			}
		}
		return f;
	}

	// return position of first element that is strictly greater than val in arr
	// returns -1 if no such element exists
	public static int searchNext(int val, int[] arr) {
		int lo = 0, hi = arr.length - 1, m, f = -1;

		// As long as this loop is processing, we need to consider
		// values in the INCLUSIVE range [lo, hi].
		while (lo <= hi) {
			m = (lo + hi) / 2;
			if (arr[m] <= val)
				lo = m + 1;
			else {
				f = m;
				hi = m - 1;
			}
		}
		return f;
	}

	// return position of first element that is strictly less than val in arr
	// returns -1 if no such element exists
	public static int searchPrev(int val, int[] arr) {
		int lo = 0, hi = arr.length - 1, m, f = -1;

		// As long as this loop is processing, we need to consider
		// values in the INCLUSIVE range [lo, hi].
		while (lo <= hi) {
			m = (lo + hi) / 2;
			if (arr[m] >= val)
				hi = m - 1;
			else {
				f = m;
				lo = m + 1;
			}
		}
		return f;
	}

	// Driver Method
	public static void main(String[] args) {

		int[] arr = { 1, 3, 5, 5, 5, 8, 100, 242, 2140 };
		System.out.println("The position of first element with a value of 5 is " + searchFirst(5, arr));
		System.out.println("The position of first element with a value of 3 is " + searchFirst(3, arr));
		System.out.println("The position of first element with a value of 69 is " + searchFirst(69, arr));
		System.out.println("The position of last element with a value of 5 is " + searchLast(5, arr));
		System.out.println("The position of last element with a value of 3 is " + searchLast(3, arr));
		System.out.println("The position of last element with a value of 69 is " + searchLast(69, arr));
		System.out.println("The position of first element strictly greater than 5 is " + searchNext(5, arr));
		System.out.println("The position of first element strictly greater than 3 is " + searchNext(3, arr));
		System.out.println("The position of first element strictly greater than 2140 is " + searchNext(2140, arr));
		System.out.println("The position of first element strictly less than 5 is " + searchPrev(5, arr));
		System.out.println("The position of first element strictly less than 242 is " + searchPrev(242, arr));
		System.out.println("The position of first element strictly less than 1 is " + searchPrev(1, arr));
	}

}
