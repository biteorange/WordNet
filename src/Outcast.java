public class Outcast {
	private WordNet wordnet;
	// constructor takes a WordNet object
	public Outcast(WordNet wordnet) {
		this.wordnet = wordnet;
	}

	// given an array of WordNet nouns, return an outcast
	public String outcast(String[] nouns) {
		String out = nouns[0]; int maxDist = -1;
		int n = nouns.length;
		for (int i = 0; i < n; i++) {
			int dist = 0;
			for (String noun : nouns) {
				if (!noun.equals(nouns[i])) 
					dist += wordnet.distance(nouns[i], noun);
			}
			if (dist > maxDist) {
				out = nouns[i]; maxDist = dist;
			}
		}
		return out;
	}

	// unit test client
	public static void main(String[] args) {
	    WordNet wordnet = new WordNet(args[0], args[1]);
	    Outcast outcast = new Outcast(wordnet);
	    for (int t = 2; t < args.length; t++) {
	        String[] nouns = In.readStrings(args[t]);
	        StdOut.println(args[t] + ": " + outcast.outcast(nouns));
	    }
	}
}