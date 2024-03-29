import java.io.*;
import java.util.*;

/*
 * Iterative DFS:
 * This allows you to perform Depth First Search
 * on a directed graph or tree iteratively
 * 
 */

public class IterativeDFS {

	static int N;
	static ArrayList<Integer>[] adj;

	// prints all not-yet visited vertices
	// reachable from curr
	public static void DFS(int curr, boolean[] visited) {
		Deque<Integer> stack = new LinkedList<>();
		stack.push(curr);

		while (!stack.isEmpty()) {
			curr = stack.pop();

			if (!visited[curr]) {
				System.out.println(curr + 1 + " ");

				// do stuff for this node

				visited[curr] = true;
			}

			for (int child : adj[curr])
				if (!visited[child])
					stack.push(child);
		}
	}

	// driver method
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer str = new StringTokenizer(br.readLine());
		N = Integer.parseInt(str.nextToken()); // # of vertices in graph
		int M = Integer.parseInt(str.nextToken()); // # of edges in graph

		// initialize adjacency list
		adj = new ArrayList[N];
		for (int i = 0; i < N; i++)
			adj[i] = new ArrayList<Integer>();

		// reads in all the edges
		for (int i = 0; i < M; i++) {
			str = new StringTokenizer(br.readLine());
			// a points to b
			int a = Integer.parseInt(str.nextToken()) - 1;
			int b = Integer.parseInt(str.nextToken()) - 1;
			adj[a].add(b);
		}

		boolean[] visited = new boolean[N];
		for (int i = 0; i < N; i++)
			if (!visited[i])
				DFS(i, visited);
	}
}
