import java.io.*;
import java.util.*;

/*
 * Kosaraju's Strongly Connected Components Algorithm:
 * Given a directed graph, find all the strongly connected
 * components in the graph in O(V+E) time
 * 
 * This implementation uses iterative DFS'
 */

class KosarajuSCC {

	int N;
	int[] postID;
	ArrayList<Integer>[] adj; // adjacency list
	ArrayList<Integer>[] jda; // reverse adjacency list

	public KosarajuSCC(int N, ArrayList<Integer>[] adj, ArrayList<Integer>[] jda) {
		this.N = N;
		this.adj = adj;
		this.jda = jda;
	}

	// returns new currID of each node
	private int calcPostDFS(int curr, boolean[] seen, int currID) {
		postID = new int[N];
		Deque<Integer> stack = new LinkedList<>();
		stack.push(curr);
		while (!stack.isEmpty()) {
			curr = stack.peek();
			if (!seen[curr]) {
				seen[curr] = true;
				for (int child : adj[curr])
					if (!seen[child])
						stack.push(child);
			} else {
				postID[currID++] = curr;
				stack.pop();
			}
		}
		return currID;
	}

	private void reverseDFS(int curr, int[] who, boolean[] seen) {
		int head = curr;
		Deque<Integer> stack = new LinkedList<>();
		stack.push(curr);
		while (!stack.isEmpty()) {
			curr = stack.pop();
			who[curr] = head;
			seen[curr] = true;
			for (int par : jda[curr])
				if (!seen[par])
					stack.push(par);
		}
	}

	// returns the the component that each node belongs to
	public int[] getComponents() {
		// calculates post order value of each node in graph
		boolean[] seen = new boolean[N];
		int currID = 0;
		for (int i = 0; i < N; i++)
			if (!seen[i])
				currID = calcPostDFS(i, seen, currID);
		// perform reverse DFS on the reverse post-order nodes
		seen = new boolean[N];
		int[] who = new int[N];
		for (int i = N - 1; i >= 0; i--)
			if (!seen[postID[i]])
				reverseDFS(postID[i], who, seen);
		return who;
	}

	public static void main(String[] args) throws IOException {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer str = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(str.nextToken()); // # of vertices in graph
		int M = Integer.parseInt(str.nextToken()); // # of edges in graph

		// initialize adjacency list
		ArrayList<Integer>[] adj = new ArrayList[N];
		ArrayList<Integer>[] jda = new ArrayList[N];
		for (int i = 0; i < N; i++) {
			adj[i] = new ArrayList<Integer>();
			jda[i] = new ArrayList<Integer>();
		}

		// reads in all the edges
		for (int i = 0; i < M; i++) {
			str = new StringTokenizer(br.readLine());
			// a points to b
			int a = Integer.parseInt(str.nextToken()) - 1;
			int b = Integer.parseInt(str.nextToken()) - 1;
			adj[a].add(b);
			jda[b].add(a);
		}

		KosarajuSCC scc = new KosarajuSCC(N, adj, jda);		
		System.out.println(Arrays.toString(scc.getComponents()));

	}
}
