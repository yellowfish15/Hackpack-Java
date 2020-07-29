import java.util.*;

/*
 * Lowest Common Ancestor Algorithm:
 * 
 * This algorithm allows you to find the lowest
 * common ancestor of any two nodes in a tree
 * in O(logN) time assuming there are N nodes.
 * 
 * This implementation uses a sparse table.
 * 
 * The memory space used in this algorithm is 
 * O(NlogN) for the sparse table.
 * 
 */

class LCA {

	final int N, SIZE; // # of nodes and log2(N)
	int[][] sparse;
	int[] depth; // depth of each node
	ArrayList<Integer>[] adj;

	public LCA(ArrayList<Integer>[] adj, int N) {
		this.adj = adj;
		this.N = N;
		this.SIZE = (int) (Math.ceil(Math.log(N) / Math.log(2)));
		this.sparse = new int[N][SIZE]; // table stores the 2^j ancestor of node i
		for (int i = 0; i < N; i++)
			Arrays.fill(sparse[i], -1);
		bfs(0, new boolean[N]);
		for (int j = 1; j < SIZE; j++)
			for (int i = 0; i < N; i++)
				if (sparse[i][j - 1] != -1)
					sparse[i][j] = sparse[sparse[i][j - 1]][j - 1];
	}

	// bfs to find depth and initial parent of each node
	private void bfs(int curr, boolean[] visited) {
		depth = new int[N];
		depth[curr] = 0; // assuming curr is the root
		Deque<Integer> queue = new LinkedList<>();
		queue.offer(curr);
		while (!queue.isEmpty()) {
			curr = queue.poll();
			visited[curr] = true;
			for (int child : adj[curr])
				if (!visited[child]) {
					queue.offer(child);
					sparse[child][0] = curr;
					depth[child] = depth[curr] + 1;
				}
		}
	}

	// returns the id of the LCA node between nodes a and b
	public int query(int a, int b) {
		// converts node to 0-based
		a = a - 1;
		b = b - 1;
		if (depth[a] < depth[b]) {
			int temp = a;
			a = b;
			b = temp;
		}
		for (int j = SIZE - 1; j >= 0; j--) {
			int ancestor = sparse[a][j];
			if (ancestor == -1 || depth[ancestor] < depth[b])
				continue;
			a = ancestor;
		}
		if (a == b)
			return a + 1;
		for (int j = SIZE - 1; j >= 0; j--) {
			if (sparse[a][j] == sparse[b][j])
				continue;
			a = sparse[a][j];
			b = sparse[b][j];
		}
		return sparse[a][0] + 1;
	}

	// Driver method
	public static void main(String[] args) {

		int N = 12; // # of nodes (index 0 ... N-1)
		ArrayList<Integer>[] adj = new ArrayList[N];
		for (int i = 0; i < N; i++)
			adj[i] = new ArrayList<Integer>();
		adj[0].add(1);
		adj[0].add(2);
		adj[0].add(3);
		adj[1].add(5);
		adj[1].add(4);
		adj[5].add(11);
		adj[5].add(10);
		adj[4].add(9);
		adj[2].add(6);
		adj[2].add(7);
		adj[2].add(8);

		LCA lca = new LCA(adj, N);
		System.out.println("LCA of node 12 and 4 is node " + lca.query(12, 4));
		System.out.println("LCA of node 8 and 9 is node " + lca.query(8, 9));
		System.out.println("LCA of node 5 and 10 is node " + lca.query(5, 10));
		System.out.println("LCA of node 6 and 5 is node " + lca.query(6, 5));
		System.out.println("LCA of node 9 and 3 is node " + lca.query(9, 3));
		System.out.println("LCA of node 11 and 12 is node " + lca.query(11, 12));
	}
}
