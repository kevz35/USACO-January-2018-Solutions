import java.io.*;
import java.util.*;

public class CowAtLarge {

	public static boolean submit = true;
	public static String fileName = "atlarge";

	public static void main(String[] args) throws Exception {
		FastScanner s = new FastScanner(System.in);
		PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		if (submit) {
			s = new FastScanner(fileName + ".in");
			pw = new PrintWriter(new FileWriter(fileName + ".out"));
		}
		CowAtLarge cal = new CowAtLarge();
		cal.run(s, pw);
		pw.close();
	}

	ArrayList<Integer>[] treeAdj;
	boolean[] visited;
	int[] depth, parent;

	public void run(FastScanner s, PrintWriter pw) throws Exception {
		int N = s.nextInt();
		int K = s.nextInt() - 1;
		ArrayList<Integer>[] adj = new ArrayList[N];

		treeAdj = new ArrayList[N];

		for (int i = 0; i < N; i++) {
			adj[i] = new ArrayList<Integer>();
			treeAdj[i] = new ArrayList<Integer>();
		}

		for (int i = 1; i < N; i++) {
			int a = s.nextInt() - 1;
			int b = s.nextInt() - 1;
			adj[a].add(b);
			adj[b].add(a);
		}

		visited = new boolean[N];
		depth = new int[N];
		parent = new int[N];
		parent[K] = -1;

		dfs(K, adj, 0);

		PriorityQueue<int[]> pq = new PriorityQueue<>((a,b)->a[0]-b[0]);

		for (int i = 0; i < N; i++) {
			if (treeAdj[i].isEmpty()) 
				pq.add(new int[]{depth[i],i});
		}

		Arrays.fill(visited, false);
		int ans = 0;
		while(!pq.isEmpty()) {
			int[] curr = pq.poll();
			boolean found = false;
			int node = curr[1];
			while(node != K) {
				if (visited[node]) {
					found = true;
					break;
				}
				else {
					node = parent[node];
				}
			}
			if (!found) {
				ans++;
				int stop = (curr[0]+1)/2;
				node = curr[1];
				for (int i = curr[0]; i >= stop; i--){
					visited[node] = true;
					node = parent[node];
				}
			}
		}

		pw.println(ans);

	}

	public void dfs(int n, ArrayList<Integer>[] adj, int d) {
		visited[n] = true;
		depth[n] = d;
		for (int child: adj[n]) {
			if (visited[child])
				continue;
			treeAdj[n].add(child);
			parent[child] = n;
			dfs(child, adj, d + 1);
		}
	}

	static class FastScanner {
		BufferedReader br;
		StringTokenizer st;
		boolean hasMoreEle;

		public FastScanner(InputStream stream) {
			br = new BufferedReader(new InputStreamReader(stream));
		}

		public FastScanner(String fileName) throws Exception {
			br = new BufferedReader(new FileReader(new File(fileName)));
		}

		public String next() throws Exception {
			if (!hasMoreEle) {
				st = new StringTokenizer(br.readLine());
			}
			String temp = st.nextToken();
			hasMoreEle = st.hasMoreTokens();
			return temp;
		}

		public int nextInt() throws Exception {
			return Integer.parseInt(this.next());
		}

		public double nextDouble() throws Exception {
			return Double.parseDouble(this.next());
		}

		public long nextLong() throws Exception {
			return Long.parseLong(this.next());
		}

		public String nextLine() throws Exception {
			if (hasMoreEle) {
				StringBuilder str = new StringBuilder();
				str.append(st.nextToken());
				while (st.hasMoreTokens()) {
					str.append(' ');
					str.append(st.nextToken());
				}
				return str.toString();
			} else {
				return br.readLine();
			}
		}
	}
}
