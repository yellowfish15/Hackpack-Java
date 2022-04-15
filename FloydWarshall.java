import java.util.*;
import java.io.*;

public class FloydWarshall {

	static final long INF = Long.MAX_VALUE / 100;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer str = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(str.nextToken()); // size of grid (N by N)
		int M = Integer.parseInt(str.nextToken()); // number of edges
		long[][] dist = new long[N][N];
		for (int i = 0; i < N; i++) {
			Arrays.fill(dist[i], INF);
			dist[i][i] = 0;
		}
		
		// initialize dist array
		for (int i = 0; i < M; i++) {
			str = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(str.nextToken()) - 1;
			int b = Integer.parseInt(str.nextToken()) - 1;
			long weight = Integer.parseInt(str.nextToken());
			// if graph is bidirectional
			dist[a][b] = dist[b][a] = Math.min(dist[a][b], weight);
		}
		
		// calculate all pairs shortest path
		for (int k = 0; k < N; k++)
			for (int i = 0; i < N; i++)
				for (int j = 0; j < N; j++)
					if (dist[i][k] + dist[k][j] < dist[i][j]) 
                        dist[i][j] = dist[i][k] + dist[k][j]; 
	}
}
