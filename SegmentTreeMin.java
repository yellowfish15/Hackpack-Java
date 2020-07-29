/*
 * Segment Tree Min: 
 * Implement the segment tree to find the minimum value over any range.
 */
class SegmentTreeMin {

	long[] arr; // original array of longegers
	long[] tree; // array to store segment tree
	long height;
	int maxSize;

	int STARTINDEX = 0;
	int ENDINDEX;

	SegmentTreeMin(int n) {
		arr = new long[n];
		height = (int) Math.ceil(Math.log(n) / Math.log(2));
		maxSize = (int) Math.pow(2, height + 1) - 1;
		tree = new long[maxSize];
		ENDINDEX = n - 1;
	}

	// build the segment tree
	public void buildSegTree(int[] elements) {
		for (int k = 0; k < arr.length; k++)
			arr[k] = elements[k];

		buildSegTreeRec(STARTINDEX, ENDINDEX, 0);
	}

	public long buildSegTreeRec(int low, int high, int current) {

		if (low == high) {
			tree[current] = arr[low];
			return tree[current];
		}

		int mid = (low + high) / 2;
		tree[current] = Math.min(buildSegTreeRec(low, mid, leftChild(current)),
				buildSegTreeRec(mid + 1, high, rightChild(current)));

		return tree[current];
	}

	public int leftChild(int k) {
		return 2 * k + 1;
	}

	public int rightChild(int k) {
		return 2 * k + 2;
	}

	// find the minimum among arr[x] to arr[y]
	public long getMin(long x, long y) {
		return getMinRec(STARTINDEX, ENDINDEX, x, y, 0);
	}

	// find the minimum recursively
	private long getMinRec(long startIndex, long endIndex, long x, long y, int current) {

		if (x <= startIndex && y >= endIndex) // [x, y] contains
												// [startIndex, endIndex]
			return tree[current];

		if (endIndex < x || startIndex > y) // [x, y] doesn't overlap
											// [startIndex, endIndex]
			return Long.MAX_VALUE;

		long mid = (startIndex + endIndex) / 2;
		long left = getMinRec(startIndex, mid, x, y, leftChild(current));
		long right = getMinRec(mid + 1, endIndex, x, y, rightChild(current));
		return Math.min(left, right);
	}

	// set the value at position "pos" to "val": arr[pos] = val
	public void setValue(int pos, long val) {
		if (val >= arr[pos])
			return;
		arr[pos] = val;
		setValueRec(STARTINDEX, ENDINDEX, pos, 0, val);
	}

	private long setValueRec(long low, long high, long pos, int current, long val) {
		if (low > pos || high < pos) {
			return tree[current];
		}

		if (low == high) {
			if (low == pos)
				tree[current] = val;
			return tree[current];
		}

		long mid = (low + high) / 2;

		long left = setValueRec(low, mid, pos, leftChild(current), val);
		long right = setValueRec(mid + 1, high, pos, rightChild(current), val);
		tree[current] = Math.min(left, right);
		return tree[current];
	}
}