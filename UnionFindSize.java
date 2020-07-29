/*
 * Union Find Size:
 * Union Find with union priority based 
 * on the size of ancestor subtree
 * 
 * This approach is in some cases
 * more practical than organizing 
 * by rank
 * 
 */

class UnionFindSize {

	int numElements;
	int[] size; // size of subtree at root node #
	int[] ancestor; // ancestor of node at #

	public UnionFindSize(int N) {
		numElements = N;
		size = new int[N];
		ancestor = new int[N];

		// initializes arrays
		for (int k = 0; k < N; k++) {
			size[k] = 0;
			ancestor[k] = k;
		}
	}

	// finds the ancestor of node
	public int find(int node) {
		int par = ancestor[node];
		while (ancestor[par] != par) {
			ancestor[node] = par;
			par = ancestor[par];
		}
		return par;
	}

	// merges two sets together
	// returns true if union occured
	public boolean union(int a, int b) {
		int parA = find(a);
		int parB = find(b);

		// two sets have already been merged
		if (parA == parB)
			return false;

		// set size of a is larger
		// than or equal to b
		if (size[parA] >= size[parB]) {
			ancestor[parB] = parA;
			size[parA] += size[parB]+1;
		}

		// set size of b is larger than a
		if (size[parB] > size[parA]) {
			ancestor[parA] = parB;
			size[parB] += size[parA]+1;
		}

		return true;
	}

}