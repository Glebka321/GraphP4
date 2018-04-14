import java.io.IOException;
import java.util.*;
import java.util.function.Supplier;
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
    	//WordProcessor wp = new WordProcessor();
    	Stream<String> wordStream = WordProcessor.getWordStream(filepath);
    	//wordStream.forEach(x -> graph.addVertex(x));

		ArrayList<String> list = wordStream.map(string -> string.split(" ")).flatMap(Arrays::stream).collect(Collectors.toCollection(ArrayList::new));

		for ( String token : list ) {
			graph.addVertex(token);
		}

    	/*
    	 * Tests whether node1 is adjacent to node2, and if they are, add an edge between the two nodes
    	 */
    	for(String node1 : graph.getAllVertices())
    	{
    		for(String node2 : graph.getAllVertices())
    		{
    			if(!node1.equals(node2) && WordProcessor.isAdjacent(node1, node2))
    			{
    				graph.addEdge(node1, node2);
    			}
    		}
    		count++;
    	}

		System.exit(10000);
    	shortestPathPrecomputation();
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
        return null;
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
        return null;
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
    	for(String vertex : graph.getAllVertices()) {
    		BFS(vertices.indexOf(vertex));
    	}
    }
    /**
     * Computes shortest distances and best predecessors in relation to the source node
     * @param src the source node
     */
    private void BFS(int src) {
    	Queue<String> q = new LinkedList<>();
    	for(int i = 0; i < vertices.size(); i++) {
    		visited[i] = false;
    	}
    	q.add(vertices.get(src));
    	visited[src] = true;
    	dist[src][src] = 0;
    	int index;
    	while(!q.isEmpty()) {
    		String u = q.remove();
    		Iterable<String> neighbors = graph.getNeighbors(u);
    		for(String s : neighbors) {
    			index = vertices.indexOf(s);
    			if(!visited[index]) {
    				q.add(s);
    				visited[index] = true;
    				dist[src][index] = dist[src][vertices.indexOf(u)] + 1; //sets distance to predecessor distance + 1
    				pred[src][index] = vertices.indexOf(u); //sets predecessor
    			}
    		}
    	}
    }
}