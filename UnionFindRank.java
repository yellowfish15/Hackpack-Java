/*
 * Union Find Rank:
 * Union Find with union priority based 
 * on the rank of ancestor subtrees rather
 * than size
 * 
 * This approach is more efficient than
 * organizing by size.
 * 
 */

class UnionFindRank {

	int numElements;
	int[] rank; // rank of subtree at root node #
	int[] ancestor; // ancestor of node at #

	public UnionFindRank(int size) {

		if (size <= 0)
			throw new IllegalArgumentException("Size cannot be less than 0!!");

		numElements = size;
		rank = new int[size];
		ancestor = new int[size];

		for (int k = 0; k < size; k++) {
			rank[k] = 0;
			ancestor[k] = k;
		}

	}

	// finds the ancestor of node
	public int find(int node) {
		int parent = ancestor[node];
		while (ancestor[parent] != parent)
			parent = ancestor[parent];
		if (parent != node)
			ancestor[node] = parent;
		return parent;
	}

	// gets the rank of the subtree at root node #
	public int getRank(int node) {
		return rank[node];
	}

	// gets the number of elements
	public int getSize() {
		return numElements;
	}

	// gets the ancestor of node #
	public int getAncestor(int node) {
		return ancestor[node];
	}

	// checks if two nodes have already been merged into same set
	public boolean isConnected(int nodeA, int nodeB) {
		return find(nodeA) == find(nodeB);
	}

	// merges two sets together
	public void union(int nodeA, int nodeB) {

		int ancestorA = find(nodeA);
		int ancestorB = find(nodeB);

		if (ancestorA == ancestorB)
			return;

		if (rank[ancestorA] < rank[ancestorB])
			ancestor[ancestorA] = ancestorB;
		else if (rank[ancestorA] > rank[ancestorB])
			ancestor[ancestorB] = ancestorA;
		else {
			ancestor[ancestorA] = ancestorB;
			rank[ancestorB]++;
		}
	}
}
