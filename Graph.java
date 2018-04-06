package GraphP4;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Undirected and unweighted graph implementation
 * 
 * @param <E> type of a vertex
 * 
 * @author sapan (sapan@cs.wisc.edu)
 * 
 */
public class Graph<E> implements GraphADT<E> {
    
    /**
     * Instance variables and constructors
     */
	
	
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
	
	public Graph()
	{
		vertices = new ArrayList<E>();
		edges = new boolean[20][20];
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
        vertices.add(vertex);
        return vertex;
        
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
    	if(vertex == null)
    		return null;
		if(vertices.remove(vertex))
			return vertex;
		else
			return null;
        
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
    	if(vertices.contains(vertex1) && vertices.contains(vertex2))
    	{
    		edges[vertices.indexOf(vertex1)][vertices.indexOf(vertex2)] = true;
    		edges[vertices.indexOf(vertex2)][vertices.indexOf(vertex1)] = true;
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
    	if(vertices.contains(vertex1) && vertices.contains(vertex2))
    	{
    		edges[vertices.indexOf(vertex1)][vertices.indexOf(vertex2)] = false;
    		edges[vertices.indexOf(vertex2)][vertices.indexOf(vertex1)] = false;
    		return true;
    	}
    	//One or more of the vertices are not in the graph
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
    	 * Both vertices exist in the graph and the vertices
    	 * are not the same
    	 */
        if(edges[vertices.indexOf(vertex1)][vertices.indexOf(vertex2)]
        	&& edges[vertices.indexOf(vertex2)][vertices.indexOf(vertex1)]
        	&& !vertex1.equals(vertex2))
        	return true;
    	
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
         * Checks if an edge exists between the given vertex and all
         * other vertices, and if so, the other vertice that shares an edge
         * to the given vertice is added to the neighbors arraylist
         */
        for(int col = 0; col < edges.length; col++)
        {
        	if(edges[vertices.indexOf(vertex)][col])
        	{
        		neighbors.add(vertices.get(vertices.indexOf(col)));
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
        return (Iterable<E>) vertices;
        
    }

}


/*
 * Sets up Graphnodes, which are the vertices of the
 * Graph. Each Graphnode contains a generic data, and
 * a boolean variable to decide if it is visited or not
 * for use in algorithms
 */
class Graphnode<T> {
	private T data;
	private boolean visited;
//	private ArrayList<Graphnode<T>> edges;
	
	
	/*
	 * Constructor for the graphnode
	 * initializes the data of the node
	 * to the data entered, and sets
	 * visited to false
	 */
	public Graphnode(T data)
	{
		this.data = data;
		visited = false;
//		edges = new ArrayList<Graphnode<T>>();
	}
	
	/*
	 * @return is whether the node has been marked
	 * as visited
	 */
	public boolean isVisited()
	{
		return visited;
	}
	
	/*
	 * @return is the data stored
	 * in the Graphnode
	 */
	public T getData()
	{
		return data;
	}
	
//	public void addEdge(Graphnode node)
//	{
//		edges.add(node);
//		node.edges.add(this);
//		
//	}
//	
//	public void removeEdge(Graphnode node)
//	{
//		edges.remove(node);
//		node.edges.remove(this);
//	}
	
	
	
	
}