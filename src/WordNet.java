import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class WordNet {
	private final HashMap<String, ArrayList<Integer>> dict;
	private final Digraph G;
	private final SAP sap;
	private final ArrayList<String> synset;
	// constructor takes the name of the two input files
	
	public WordNet(String synsets, String hypernyms) {
		dict = new HashMap<String, ArrayList<Integer>>();
		synset = new ArrayList<String>();
		
		// build the dictionary from noun to ids
		In inSyn = new In(synsets);
		int count = 0;
		while(inSyn.hasNextLine()) {
			String[] fields = inSyn.readLine().split(",");
			int id = Integer.parseInt(fields[0]);
			synset.add(fields[1]);
			String[] nouns = fields[1].split(" ");
			for (String noun : nouns) {
				if (!dict.containsKey(noun))
					dict.put(noun,  new ArrayList<Integer>());
				dict.get(noun).add(id);
			}
			count++;
		}
		inSyn.close();
		
		// build the graph
		boolean marked[] = new boolean[count];
		Arrays.fill(marked, false);
		G = new Digraph(count);
		In inHyper = new In(hypernyms);
		while(inHyper.hasNextLine()) {
			String[] vertices = inHyper.readLine().split(",");
			int v = Integer.parseInt(vertices[0]);
			if (vertices.length > 1) marked[v] = true; // with outgoing edge
			for (int i = 1; i < vertices.length; i++) 
				G.addEdge(v, Integer.parseInt(vertices[i]));
		}
        DirectedCycle finder = new DirectedCycle(G);
        
        int numRoots = 0;
        for(int i = 0; i < marked.length; i++) 
        	if (marked[i] == false) numRoots++;
        if (finder.hasCycle() || numRoots != 1)
        	throw new java.lang.IllegalArgumentException();
		inHyper.close();
		
		sap = new SAP(G);
	}

	// the set of nouns (no duplicates), returned as an Iterable
	public Iterable<String> nouns() {
		return dict.keySet();
	}

	// is the word a WordNet noun?
	public boolean isNoun(String word) {
		return dict.containsKey(word);
	}

	// distance between nounA and nounB (defined below)
	public int distance(String nounA, String nounB) {
		if (!dict.containsKey(nounA) || !dict.containsKey(nounB))
			throw new java.lang.IllegalArgumentException();
		
		return sap.length(dict.get(nounA), dict.get(nounB));
	}

	// a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
	// in a shortest ancestral path (defined below)
	public String sap(String nounA, String nounB) {
		if (!dict.containsKey(nounA) || !dict.containsKey(nounB))
			throw new java.lang.IllegalArgumentException();
		
		int id = sap.ancestor(dict.get(nounA),  dict.get(nounB));
		return synset.get(id);
	}

	// for unit testing of this class
	public static void main(String[] args) {
		WordNet wordnet = new WordNet(args[0], args[1]);
		while (!StdIn.isEmpty()) {
	        String noun1 = StdIn.readString();
	        String noun2 = StdIn.readString();
	        
	        int length   = wordnet.distance(noun1, noun2);
	        String ancestor = wordnet.sap(noun1, noun2);
	        StdOut.printf("dist = %d, ancestor = %s\n", length, ancestor);
	    }
	}
}
