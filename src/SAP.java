
public class SAP {
	// constructor takes a digraph (not necessarily a DAG)
	private Digraph G;
	private int V;
	public SAP(Digraph G) {
		this.G = G;
		this.V = G.V();
	}

	// length of shortest ancestral path between v and w; -1 if no such path
	public int length(int v, int w) {
		 BreadthFirstDirectedPaths bfsV = new BreadthFirstDirectedPaths(G, v);
		 BreadthFirstDirectedPaths bfsW = new BreadthFirstDirectedPaths(G, w);
		 int minLength = Integer.MAX_VALUE;
		 for (int i = 0; i < V; i++) {
			 int dist = bfsV.distTo(i) + bfsW.distTo(i);
			 if (dist < 0) continue;
			 minLength = minLength > dist ? dist : minLength;
		 }
		 return (minLength == Integer.MAX_VALUE ? -1 : minLength);
	}

	// a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
	public int ancestor(int v, int w) {
		BreadthFirstDirectedPaths bfsV = new BreadthFirstDirectedPaths(G, v);
		BreadthFirstDirectedPaths bfsW = new BreadthFirstDirectedPaths(G, w);
		int minAncestor = -1;
		int minLength = Integer.MAX_VALUE;
		for (int i = 0; i < V; i++) {
			int dist = bfsV.distTo(i) + bfsW.distTo(i);
			if (dist < 0) continue;
			if (minLength > dist) { 
				minAncestor = i;
				minLength = dist;
			}
		}
		return minAncestor;
	}

	// length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
	public int length(Iterable<Integer> v, Iterable<Integer> w) {
		BreadthFirstDirectedPaths bfsV = new BreadthFirstDirectedPaths(G, v);
		BreadthFirstDirectedPaths bfsW = new BreadthFirstDirectedPaths(G, w);
		int minLength = Integer.MAX_VALUE;
		for (int i = 0; i < V; i++) {
			int dist = bfsV.distTo(i) + bfsW.distTo(i);
			if (dist < 0) continue;
			minLength = minLength > dist ? dist : minLength;
		}
		return (minLength == Integer.MAX_VALUE ? -1 : minLength);
	}

	// a common ancestor that participates in shortest ancestral path; -1 if no such path
	public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
		BreadthFirstDirectedPaths bfsV = new BreadthFirstDirectedPaths(G, v);
		BreadthFirstDirectedPaths bfsW = new BreadthFirstDirectedPaths(G, w);
		int minAncestor = -1;
		int minLength = Integer.MAX_VALUE;
		for (int i = 0; i < V; i++) {
			int dist = bfsV.distTo(i) + bfsW.distTo(i);
			if (dist < 0) continue;
			if (minLength > dist) { 
				minAncestor = i;
				minLength = dist;
			}
		}
		return minAncestor;
	}
	
	public static void main(String[] args) {
	    In in = new In(args[0]);
	    Digraph G = new Digraph(in);
	    SAP sap = new SAP(G);
	    while (!StdIn.isEmpty()) {
	        int v = StdIn.readInt();
	        int w = StdIn.readInt();
	        int length   = sap.length(v, w);
	        int ancestor = sap.ancestor(v, w);
	        StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
	    }
	}

}
