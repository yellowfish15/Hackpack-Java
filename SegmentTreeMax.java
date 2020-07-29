/*
 * Segment Tree Max:
 * Implement the segment tree to find the maximum value over any range.
 * 
 *  The initial array is saved in "arr". If memory becomes an issue, "arr"
 *  can be removed -- just comment the statements involving "arr".
 */

class SegmentTreeMax {
	// accept an integer array "arr"
	// build another array "tree" representing the segment tree

	int[] arr; // original array of integers
	int[] tree; // array to store segment tree

	int height;
	int maxSize;

	int STARTINDEX = 0;
	int ENDINDEX;

	SegmentTreeMax(int n) {
		arr = new int[n];

		height = (int) Math.ceil(Math.log(n) / Math.log(2));
		maxSize = (int) Math.pow(2, height + 1) - 1;
		tree = new int[maxSize];
		ENDINDEX = n - 1;
	}

	int maxVal(int x, int y) {
		return (x > y) ? x : y;
	}

	public int leftChild(int k) {
		return 2 * k + 1;
	}

	public int rightChild(int k) {
		return 2 * k + 2;
	}

	// build the segment tree
	public void buildSegTree(int[] elements) {
		for (int k = 0; k < arr.length; k++)
			arr[k] = elements[k];

		buildSegTreeRec(STARTINDEX, ENDINDEX, 0);
	}

	public int buildSegTreeRec(int low, int high, int current) {

		if (low == high) {
			tree[current] = arr[low];
			return tree[current];
		}

		int mid = (low + high) / 2;
		tree[current] = maxVal(buildSegTreeRec(low, mid, leftChild(current)),
				buildSegTreeRec(mid + 1, high, rightChild(current)));

		return tree[current];
	}

	// find the maximum among arr[x] to arr[y]
	public int getMax(int x, int y) {
		if (x < 0 || y >= arr.length)
			return Integer.MIN_VALUE;

		return getMaxRec(STARTINDEX, ENDINDEX, x, y, 0);
	}

	// find the maximum recursively
	private int getMaxRec(int startIndex, int endIndex, int x, int y, int current) {

		if (x <= startIndex && y >= endIndex) // [x, y] contains [startIndex, endIndex]
			return tree[current];

		if (endIndex < x || startIndex > y) // [x, y] doesn't overlap [startIndex, endIndex]
			return Integer.MIN_VALUE;

		int mid = (startIndex + endIndex) / 2;
		return maxVal(getMaxRec(startIndex, mid, x, y, leftChild(current)),
				getMaxRec(mid + 1, endIndex, x, y, rightChild(current)));
	}

	// set the value at position "pos" to "val": arr[pos] = val
	public void setValue(int pos, int val) {
		int diff = val - arr[pos];
		arr[pos] = val;
		setValueRec(STARTINDEX, ENDINDEX, pos, diff, 0);
	}

	private int setValueRec(int low, int high, int pos, int diff, int current) {
		if (low > pos || high < pos) {
			return tree[current];
		}

		if (low == high) {
			if (low == pos)
				tree[current] = tree[current] + diff;
			return tree[current];
		}

		int mid = (low + high) / 2;
		tree[current] = maxVal(setValueRec(low, mid, pos, diff, leftChild(current)),
				setValueRec(mid + 1, high, pos, diff, rightChild(current)));

		return tree[current];
	}
}