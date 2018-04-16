import java.util.ArrayList;

/**
 * Undirected and unweighted graph implementation
 *
 * @param <E> type of a vertex
 *
 * @author sapan (sapan@cs.wisc.edu)
 *
 */
public class Graph<E> implements GraphADT<E> {

	// scale factor for resizing arrays
	private final double SCALE_FACTOR = 1.25;
	// Initial size of arrays
	private final int INITIAL_SIZE = 100;


	/*
	 * Stores all of the vertices of the graph as Graphnodes
	 * in the order they were added
	 */
	private ArrayList<E> vertices;

	/*
	 * Adjacency matrix, with the index of the node in the vertices
	 * arraylist as the index for the 2D matrix
	 * If an edge exists between the two graphnodes, edges[indexOfNode1][indexOfNode2]
	 * will be true
	 */
	private boolean[][] edges;

	/**
	 * Constructor for graph, creates initial
	 */
	public Graph() {
		vertices = new ArrayList<>();
		edges = new boolean[INITIAL_SIZE][INITIAL_SIZE];
	}


	/**
     * Add new vertex to the graph
     *
     * Valid argument conditions:
     * 1. vertex should be non-null
     * 2. vertex should not already exist in the graph
     *
     * @param vertex the vertex to be added
     * @return vertex if vertex added, else return null if vertex can not be added (also if valid conditions are violated)
     */
    @Override
    public E addVertex(E vertex) {
    	E vertexAdded = null;
		if ( !vertices.contains(vertex) && vertex != null ) {
			vertices.add(vertex);
			if ( vertices.size() >= edges.length ) {
				resizeAM();
			}
			vertexAdded = vertex;
		}
    	return vertexAdded;
    }

    /**
     * Remove the vertex and associated edge associations from the graph
     *
     * Valid argument conditions:
     * 1. vertex should be non-null
     * 2. vertex should exist in the graph
     *
     * @param vertex the vertex to be removed
     * @return vertex if vertex removed, else return null if vertex and associated edges can not be removed (also if valid conditions are violated)
     */
    @Override
    public E removeVertex(E vertex) {
    	E vertexRemoved = null;

		if ( vertex == null ) {
			return null;
		}

		if ( vertices.contains(vertex) ) {
			for ( int row = 0; row < edges.length; row++ ) {
				edges[vertices.indexOf(vertex)][row] = false;
				edges[row][vertices.indexOf(vertex)] = false;
			}

			vertices.remove(vertex);
			vertexRemoved = vertex;
		}

		return vertexRemoved;
    }

    /**
     * Add an edge between two vertices (edge is undirected and unweighted)
     *
     * Valid argument conditions:
     * 1. both the vertices should exist in the graph
     * 2. vertex1 should not equal vertex2
     *
     * @param vertex1 the first vertex
     * @param vertex2 the second vertex
     * @return true if edge added, else return false if edge can not be added (also if valid conditions are violated)
     */
    @Override
    public boolean addEdge(E vertex1, E vertex2) {
		if ( vertices.contains(vertex1) && vertices.contains(vertex2) && !vertex1.equals(vertex2) ) {
			int vertex1Index = vertices.indexOf(vertex1);
			int vertex2Index = vertices.indexOf(vertex2);

			edges[vertex1Index][vertex2Index] = true;
			edges[vertex2Index][vertex1Index] = true;

			return true;
		}
        return false;
    }

    /**
     * Remove the edge between two vertices (edge is undirected and unweighted)
     *
     * Valid argument conditions:
     * 1. both the vertices should exist in the graph
     * 2. vertex1 should not equal vertex2
     *
     * @param vertex1 the first vertex
     * @param vertex2 the second vertex
     * @return true if edge removed, else return false if edge can not be removed (also if valid conditions are violated)
     */
    @Override
    public boolean removeEdge(E vertex1, E vertex2) {
    	//The two vertices are in the graph and thus can be removed
		if ( vertices.contains(vertex1) && vertices.contains(vertex2) && !vertex1.equals(vertex2) ) {
			int vertex1Index = vertices.indexOf(vertex1);
			int vertex2Index = vertices.indexOf(vertex2);

			edges[vertex1Index][vertex2Index] = false;
			edges[vertex2Index][vertex1Index] = false;
			return true;
		}

    	//One or more of the vertices are not in the graph or vertex == vertex2
    	return false;

    }

    /**
     * Check whether the two vertices are adjacent
     *
     * Valid argument conditions:
     * 1. both the vertices should exist in the graph
     * 2. vertex1 should not equal vertex2
     *
     * @param vertex1 the first vertex
     * @param vertex2 the second vertex
     * @return true if both the vertices have an edge with each other, else return false if vertex1 and vertex2 are not connected (also if valid conditions are violated)
     */
    @Override
    public boolean isAdjacent(E vertex1, E vertex2) {
    	/*
    	 * One or more of the vertices is not in the Graph
    	 */
		if ( !vertices.contains(vertex1) || !vertices.contains(vertex2) ) {
			return false;
		}

    	/*
    	 * Both vertices exist in the graph and the vertices
    	 * are not the same
    	 */
		if ( edges[vertices.indexOf(vertex1)][vertices.indexOf(vertex2)]
				&& edges[vertices.indexOf(vertex2)][vertices.indexOf(vertex1)]
				&& !vertex1.equals(vertex2) ) {
			return true;
		}

    	return false;

    }

    /**
     * Get all the neighbor vertices of a vertex
     *
     * @param vertex the vertex
     * @return an iterable for all the immediate connected neighbor vertices
     */
    @Override
    public Iterable<E> getNeighbors(E vertex) {
        ArrayList<E> neighbors = new ArrayList<E>();

        /*
         * Vertex not in the Graph
         */
		if ( !vertices.contains(vertex) ) {
			return null;
		}

        /*
         * Checks if an edge exists between the given vertex and all
         * other vertices, and if so, the other vertice that shares an edge
         * to the given vertice is added to the neighbors arraylist
         */
		for ( int col = 0; col < edges.length; col++ ) {
			if ( edges[vertices.indexOf(vertex)][col] ) {
				neighbors.add(vertices.get((col)));
			}
		}

        return neighbors;
    }

    /**
     * Get all the vertices in the graph
     *
     * @return an iterable for all the vertices
     */
    @Override
    public Iterable<E> getAllVertices() {
        return vertices;
    }

    /**
     * Resizes the adjacency matrix
     */
    private void resizeAM() {
    	// new size is current size times scale factor.
    	int newSize = (int) (edges.length * SCALE_FACTOR);
    	boolean[][] newAM = new boolean[newSize][newSize];

    	// copy old array into new array and set
    	for(int i = 0; i < edges.length; i++ ) {
    		for(int j = 0; j < edges[0].length; j++) {
    			newAM[i][j] = edges[i][j];
    		}
    	}
    	edges = newAM;
    }
}