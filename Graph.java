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
	
	private ArrayList<E> vertices;
	private boolean[][] edges;
	
	public Graph()
	{
		vertices = new ArrayList<E>();
		edges = new boolean[20][20];
	}
	

    /**
     * {@inheritDoc}
     */
    @Override
    public E addVertex(E vertex) {
        vertices.add(vertex);
        return vertex;
        
    }

    /**
     * {@inheritDoc}
     */
    @Override
    //Test
    public E removeVertex(E vertex) {
    	if(vertex == null)
    		return null;
		if(vertices.remove(vertex))
			return vertex;
		else
			return null;
        
    }

    /**
     * {@inheritDoc}
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
     * {@inheritDoc}
     */
    @Override
    public boolean removeEdge(E vertex1, E vertex2) {
    	if(vertices.contains(vertex1) && vertices.contains(vertex2))
    	{
    		edges[vertices.indexOf(vertex1)][vertices.indexOf(vertex2)] = false;
    		edges[vertices.indexOf(vertex2)][vertices.indexOf(vertex1)] = false;
    	}
    	return false;
        
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isAdjacent(E vertex1, E vertex2) {
        if(edges[vertices.indexOf(vertex1)][vertices.indexOf(vertex2)]
        	&& edges[vertices.indexOf(vertex2)][vertices.indexOf(vertex1)]
        	&& !vertex1.equals(vertex2))
        	return true;
    	
    	return false;
        
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterable<E> getNeighbors(E vertex) {
        ArrayList<E> neighbors = new ArrayList<E>();
        
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
     * {@inheritDoc}
     */
    @Override
    public Iterable<E> getAllVertices() {
        return (Iterable<E>) vertices;
        
    }

}

class Graphnode<T> {
	private T data;
	private boolean visited;
//	private ArrayList<Graphnode<T>> edges;
	
	public Graphnode(T data)
	{
		this.data = data;
		visited = false;
//		edges = new ArrayList<Graphnode<T>>();
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