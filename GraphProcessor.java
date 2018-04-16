import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * This class adds additional functionality to the graph as a whole.
 *
 * Contains an instance variable, {@link #graph}, which stores information for all the vertices and edges.
 * @see #populateGraph(String)
 *  - loads a dictionary of words as vertices in the graph.
 *  - finds possible edges between all pairs of vertices and adds these edges in the graph.
 *  - returns number of vertices added as Integer.
 *  - every call to this method will add to the existing graph.
 *  - this method needs to be invoked first for other methods on shortest path computation to work.
 * @see #shortestPathPrecomputation()
 *  - applies a shortest path algorithm to precompute data structures (that store shortest path data)
 *  - the shortest path data structures are used later
 *    to quickly find the shortest path and distance between two vertices.
 *  - this method is called after any call to populateGraph.
 *  - It is not called again unless new graph information is added via populateGraph().
 * @see #getShortestPath(String, String)
 *  - returns a list of vertices that constitute the shortest path between two given vertices,
 *    computed using the precomputed data structures computed as part of {@link #shortestPathPrecomputation()}.
 *  - {@link #shortestPathPrecomputation()} must have been invoked once before invoking this method.
 * @see #getShortestDistance(String, String)
 *  - returns distance (number of edges) as an Integer for the shortest path between two given vertices
 *  - this is computed using the precomputed data structures computed as part of {@link #shortestPathPrecomputation()}.
 *  - {@link #shortestPathPrecomputation()} must have been invoked once before invoking this method.
 *
 * @author sapan (sapan@cs.wisc.edu)
 *
 */
public class GraphProcessor {

    /**
     * Graph which stores the dictionary words and their associated connections
     */
    private Graph<String> graph;
    private ArrayList<String> vertices; //stores all vertices in the graph
    private int[][] dist; //matrix of distances between nodes
    private int[][] pred; //predecessor matrix
    private boolean[] visited; //keeps track of visited nodes

    /**
     * Constructor for this class. Initializes instances variables to set the starting state of the object
     */
    public GraphProcessor() {
        this.graph = new Graph<>();
		this.vertices = new ArrayList<>();
    }

    /**
     * Builds a graph from the words in a file. Populate an internal graph, by adding words from the dictionary as vertices
     * and finding and adding the corresponding connections (edges) between
     * existing words.
     *
     * Reads a word from the file and adds it as a vertex to a graph.
     * Repeat for all words.
     *
     * For all possible pairs of vertices, finds if the pair of vertices is adjacent {@link WordProcessor#isAdjacent(String, String)}
     * If a pair is adjacent, adds an undirected and unweighted edge between the pair of vertices in the graph.
     *
     * @param filepath file path to the dictionary
     * @return Integer the number of vertices (words) added
     * @throws IOException
     */
    public Integer populateGraph(String filepath) throws IOException {
    	int count = 0;
    	Stream<String> wordStream = WordProcessor.getWordStream(filepath);

		ArrayList<String> stringsFromStream = wordStream.map(string -> string.split(" "))
				.flatMap(Arrays::stream).collect(Collectors.toCollection(ArrayList::new));

		for ( String token : stringsFromStream ) {
			graph.addVertex(token);
			vertices.add(token);
		}

    	/*
    	 * Tests whether node1 is adjacent to node2, and if they are, add an edge between the two nodes
    	 */
		for ( String node1 : graph.getAllVertices() ) {
			for ( String node2 : graph.getAllVertices() ) {
				if ( WordProcessor.isAdjacent(node1, node2) ) {
					graph.addEdge(node1, node2);
				}
			}
			count++;
		}

        return count;
    }


    /**
     * Gets the list of words that create the shortest path between word1 and word2
     *
     * Example: Given a dictionary,
     *             cat
     *             rat
     *             hat
     *             neat
     *             wheat
     *             kit
     *  shortest path between cat and wheat is the following list of words:
     *     [cat, hat, heat, wheat]
     *
     * @param word1 first word
     * @param word2 second word
     * @return List<String> list of the words
     */
    public List<String> getShortestPath(String word1, String word2) {
    	List<String> list = new ArrayList<>();

        int index1 = vertices.indexOf(word1.toUpperCase());
        int index2 = vertices.indexOf(word2.toUpperCase());

        // Only look for path if words are not the same
		if ( !word1.equals(word2) ) {

			// after last pred, index1 will be -1
			while ( index1 != -1 ) {
				list.add(vertices.get(index1).toLowerCase());
				index1 = pred[index2][index1];
			}
		}

    	return list;
    }

    /**
     * Gets the distance of the shortest path between word1 and word2
     *
     * Example: Given a dictionary,
     *             cat
     *             rat
     *             hat
     *             neat
     *             wheat
     *             kit
     *  distance of the shortest path between cat and wheat, [cat, hat, heat, wheat]
     *   = 3 (the number of edges in the shortest path)
     *
     * @param word1 first word
     * @param word2 second word
     * @return Integer distance
     */
    public Integer getShortestDistance(String word1, String word2) {

		word1 = word1.toUpperCase();
		word2 = word2.toUpperCase();

    	// if word one equals word 2, OR if either word are not included in graph
		// -1 gets returned as shortest distance
		if ( word1.equals(word2) ) {
			return -1;
		}
		else if ( !vertices.contains(word1) || !vertices.contains(word2) ) {
			return -1;
		}

		int word1Index = vertices.indexOf(word1);
		int word2Index = vertices.indexOf(word2);
		return dist[word1Index][word2Index];
    }

    /**
     * Computes shortest paths and distances between all possible pairs of vertices.
     * This method is called after every set of updates in the graph to recompute the path information.
     * Any shortest path algorithm can be used (Djikstra's or Floyd-Warshall recommended).
     */
    public void shortestPathPrecomputation() {
    	visited = new boolean[vertices.size()];
    	dist = new int[vertices.size()][vertices.size()];
    	pred = new int[vertices.size()][vertices.size()];
    	for(int i = 0; i < vertices.size(); i++) {
    		for(int j = 0; j < vertices.size(); j++) {
        		dist[i][j] = -1;
        		pred[i][j] = -1;
        	}
    	}
    	for(String vertex : graph.getAllVertices()) {
    		BFS(vertices.indexOf(vertex));
    	}
    }
    /**
     * Computes shortest distances and best predecessors in relation to the source node
     * @param src the source node
     */
    private void BFS(int src) {
    	LinkedList<String> queue = new LinkedList<>();
    	for(int i = 0; i < vertices.size(); i++) {
    		visited[i] = false;
    	}
    	queue.add(vertices.get(src));
    	visited[src] = true;
    	dist[src][src] = 0;
    	int index;
    	while(!queue.isEmpty()) {
    		String u = queue.remove();
    		Iterable<String> neighbors = graph.getNeighbors(u);
    		for(String s : neighbors) {
    			index = vertices.indexOf(s);
    			if(!visited[index]) {
    				queue.add(s);
    				visited[index] = true;
    				dist[src][index] = dist[src][vertices.indexOf(u)] + 1; //sets distance to predecessor distance + 1
    				pred[src][index] = vertices.indexOf(u); //sets predecessor
    			}
    		}
    	}
    }
}
