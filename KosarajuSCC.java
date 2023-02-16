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

	private int N;
	private int numComponents; // number of components after running Kosaraju
	private int[] postID;
	private ArrayList<Integer>[] adj; // adjacency list
	private ArrayList<Integer>[] jda; // reverse adjacency list

	public KosarajuSCC(int N, ArrayList<Integer>[] adj, ArrayList<Integer>[] jda) {
		this.N = N;
		this.adj = adj;
		this.jda = jda;
		this.postID = new int[N];
	}

	// returns new currID of each node
	private int calcPostDFS(int curr, short[] seen, int currID) {
		Deque<Integer> stack = new LinkedList<Integer>();
		stack.push(curr);
		while (!stack.isEmpty()) {
			curr = stack.peek();
			if (seen[curr] > 1) {
				stack.pop();
				continue;
			}
			if(seen[curr] == 0) {
				seen[curr]++;
				for (int child : adj[curr])
					if (seen[child] == 0)
						stack.push(child);
			} else {
				seen[curr]++;
				postID[currID++] = curr;
				stack.pop();
			}
		}
		return currID;
	}

	private void reverseDFS(int curr, int[] who, short[] seen, int currID) {
		Deque<Integer> stack = new LinkedList<>();
		stack.push(curr);
		while (!stack.isEmpty()) {
			curr = stack.pop();
			who[curr] = currID;
			seen[curr] = 1;
			for (int par : jda[curr])
				if (seen[par] == 0)
					stack.push(par);
		}
	}

	// returns the the component that each node belongs to
	public int[] getComponents() {
		short[] seen = new short[N];
		int currID = 0;
		for (int i = 0; i < N; i++)
			if (seen[i] == 0)
				currID = calcPostDFS(i, seen, currID);

		// perform reverse DFS on the reverse post-order nodes
		seen = new short[N];
		currID = 0;
		int[] who = new int[N];
		for (int i = N - 1; i >= 0; i--)
			if (seen[postID[i]] == 0) {
				reverseDFS(postID[i], who, seen, currID);
				currID++;
			}
		numComponents = currID;
		return who;
	}

	private void componentDFS(int curr, int[] who, Component[] cAdj, boolean[] seen) {
		Deque<Integer> stack = new LinkedList<>();
		stack.push(curr);
		seen[curr] = true;
		while (!stack.isEmpty()) {
			curr = stack.pop();

			Component currComponent = cAdj[who[curr]];
			currComponent.size++;
			for (int child : adj[curr]) {
				// this component points to a different child component
				if (who[child] != who[curr]) {
					if(currComponent.adj.add(who[child])) {
						currComponent.outDegree++;
						cAdj[who[child]].inDegree++;
					}
				}
				if (!seen[child]) {
					seen[child] = true;
					stack.push(child);
				}
			}
		}
	}

	// returns the details for each component
	public Component[] getComponentAdj(int[] who) {
		// adjacency list for components
		Component[] cAdj = new Component[numComponents];
		for (int i = 0; i < numComponents; i++)
			cAdj[i] = new Component();

		boolean[] seen = new boolean[N];
		for (int i = 0; i < N; i++)
			if (!seen[i])
				componentDFS(i, who, cAdj, seen);
		return cAdj;
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
		int[] who = scc.getComponents();
		System.out.println("Components: " + Arrays.toString(who));

		System.out.println("\nComponent Adjacency List:");
		Component[] cAdj = scc.getComponentAdj(who);
		for (int i = 0; i < cAdj.length; i++) {
			System.out.println(i + ":");
			System.out.println("-> " + cAdj[i].adj);
			System.out.println("Size: " + cAdj[i].size);
			System.out.println("In Degree: " + cAdj[i].inDegree);
			System.out.println("Out Degree: " + cAdj[i].outDegree);
			System.out.println();
		}

	}
}

class Component {
	int size; // number of nodes in the component
	int inDegree, outDegree; // degree of component
	HashSet<Integer> adj;

	public Component() {
		adj = new HashSet<Integer>();
	}
}
