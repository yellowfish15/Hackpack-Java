/*
 * Heavy-Light Decomposition:
 * Given a tree, perform the following operations, if there are N nodes:
 * 
 * 1. Update the value of an individual node in O(log N) time.
 * 
 * 2. Find lowest common ancestor of two nodes u and v in O(log N) time.
 * 
 * 3. Given node A and B, find the XOR of all node values 
 *    between A and B inclusive in O(log N) time.
 *   
 * This algorithm uses the Sparse Table and Segment Tree data structures.
 */

import java.util.*;

// HLD for XOR (from cowland)
class HLD {

	// value at each node
	int[] value;

	// LCA component
	final int SIZE; // log height of tree
	int N;
	int[][] sparse;
	int[] depth; // depth of each node
	ArrayList<ArrayList<Integer>> edges;

	// Chain component and Seg Tree
	SegTree tree;
	int[] subsize; // size of each sub-tree
	int[] heavy; // heaviest child of each node
	int[] chainNum; // which chain each node belongs to
	int[] chainHead; // head node of each chain
	int[] arr; // modified array of nodes for segment tree
	int[] pos; // position of each node in arr

	// pointer used to build arr
	int pointer;

	public HLD(ArrayList<ArrayList<Integer>> edges, int N, int[] value) {
		this.edges = edges;
		this.N = N;
		this.value = value;

		// initialize sparse table to query LCA
		SIZE = (int) (Math.ceil(Math.log(N) / Math.log(2)));
		initializeSparse();

		// initialiaze chain component
		initializeChain();
	}

	private void initializeSparse() {
		depth = new int[N + 1];
		sparse = new int[N + 1][SIZE];

		for (int[] arr : sparse)
			Arrays.fill(arr, -1);

		// dfs to find parent of each node
		dfs(-1, 1, 0);
		for (int j = 1; j < SIZE; j++)
			for (int i = 1; i <= N; i++)
				if (sparse[i][j - 1] != -1)
					sparse[i][j] = sparse[sparse[i][j - 1]][j - 1];
	}

	private void dfs(int par, int curr, int d) {
		sparse[curr][0] = par;
		depth[curr] = d;
		for (int child : edges.get(curr))
			if (child != par)
				dfs(curr, child, d + 1);
	}

	private void initializeChain() {
		subsize = new int[N + 1];
		heavy = new int[N + 1];
		chainNum = new int[N + 1];
		chainHead = new int[N];
		arr = new int[N];
		pos = new int[N + 1];

		Arrays.fill(chainHead, -1);
		pointer = 0;
		dfs(-1, 1);
		hld(1, -1, 1);
		tree = new SegTree(arr);
	}

	// 1. find heaviest child of each node
	// 2. find size of each subtree rooted at every node
	public int dfs(int par, int currNode) {
		if (currNode > N)
			return 0;

		subsize[currNode] = 1;
		int maxSize = -1;
		int maxChild = -1;
		for (int next : edges.get(currNode)) {
			if (next != par) {
				int childSize = dfs(currNode, next);
				subsize[currNode] += childSize;
				if (childSize > maxSize) {
					maxSize = childSize;
					maxChild = next;
				}
			}
		}
		heavy[currNode] = maxChild;
		return subsize[currNode];
	}

	// decomposes the tree into chains
	public int hld(int currChain, int par, int currNode) {
		if (currNode == -1)
			return currChain;

		pos[currNode] = pointer;
		arr[pointer] = value[currNode];
		pointer++;
		chainNum[currNode] = currChain;
		if (chainHead[currChain] == -1)
			chainHead[currChain] = currNode;
		currChain = hld(currChain, currNode, heavy[currNode]);
		for (int next : edges.get(currNode))
			if (next != par && next != heavy[currNode])
				currChain = hld(currChain + 1, currNode, next);
		return currChain;
	}

	public int findLCA(int u, int v) {
		// u must be less than v
		if (depth[v] < depth[u]) {
			int temp = v;
			v = u;
			u = temp;
		}
		// raise v (the lower node) to u's depth
		for (int j = SIZE - 1; j >= 0; j--) {
			int ancestor = sparse[v][j];
			if (ancestor != -1) {
				if (depth[ancestor] < depth[u])
					continue;
				v = ancestor;
			}
		}
		if (u == v)
			return u;

		// raise u and v simultaneously
		for (int j = SIZE - 1; j >= 0; j--) {
			int ancestorU = sparse[u][j];
			int ancestorV = sparse[v][j];
			if (ancestorU != ancestorV) {
				u = ancestorU;
				v = ancestorV;
			}
		}
		return sparse[u][0];
	}

	// updates the node at position a to newVal
	public void updateVal(int a, int newVal) {
		int posA = pos[a];
		arr[posA] = newVal;
		value[a] = newVal;
		tree.update(posA, newVal);
	}

	// queries total xor of all edges between a node and its ancestor
	public int findXOR(int a, int ancestor) {
		int XOR = 0;
		int chain = chainNum[a];
		int curr = a;
		while (chainNum[ancestor] != chain) {
			XOR ^= tree.query(pos[chainHead[chain]], pos[curr]);
			curr = sparse[chainHead[chain]][0];
			chain = chainNum[curr];
		}
		XOR ^= tree.query(pos[ancestor], pos[curr]);
		return XOR;
	}

	static class SegTree {
		int[] tree;
		int[] arr;

		final int HEIGHT;
		final int T_SIZE; // size of tree

		public SegTree(int[] arr) {
			this.arr = new int[arr.length];
			for (int i = 0; i < arr.length; i++)
				this.arr[i] = arr[i];
			HEIGHT = (int) Math.ceil(Math.log(arr.length) / Math.log(2));
			T_SIZE = (int) Math.pow(2, HEIGHT + 1) - 1;
			tree = new int[T_SIZE + 1];
			build(0, arr.length - 1, 0);
		}

		// get children
		private int getLeft(int curr) {
			return curr * 2 + 1;
		}

		private int getRight(int curr) {
			return curr * 2 + 2;
		}

		// builds the XOR seg tree
		private int build(int low, int high, int curr) {
			if (low == high) {
				tree[curr] = arr[low];
				return tree[curr];
			}
			int mid = (low + high) / 2;
			tree[curr] = build(low, mid, getLeft(curr)) ^ build(mid + 1, high, getRight(curr));
			return tree[curr];
		}

		// update value of element in the array
		public void update(int pos, int val) {
			arr[pos] = val;
			updateRecur(0, arr.length - 1, pos, val, 0);
		}

		private int updateRecur(int low, int high, int pos, int val, int curr) {
			if (curr >= tree.length || curr < 0)
				return 0;
			// out of bounds
			if (low > pos || high < pos) {
				return tree[curr];
			}
			// update this position
			if (low == high) {
				if (low == pos)
					tree[curr] = val;
				return tree[curr];
			}
			// partial overlap so keep searching
			int mid = (low + high) / 2;
			tree[curr] = updateRecur(low, mid, pos, val, getLeft(curr))
					^ updateRecur(mid + 1, high, pos, val, getRight(curr));
			return tree[curr];
		}

		// process a range query
		public int query(int start, int end) {
			if (start == end)
				return arr[start];
			return queryRecur(0, arr.length - 1, start, end, 0);
		}

		// query xor between start and end nodes
		private int queryRecur(int low, int high, int start, int end, int curr) {
			// total overlap
			if (low >= start && high <= end)
				return tree[curr];
			// no overlap
			if (low > end || high < start)
				return 0;
			// partial overlap so keep going down tree
			int mid = (low + high) / 2;
			return queryRecur(low, mid, start, end, getLeft(curr))
					^ queryRecur(mid + 1, high, start, end, getRight(curr));
		}
	}
}