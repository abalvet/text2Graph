/**
 * 
 */
package fr.univlille3.textualGraph;

import java.util.Iterator;
import java.util.Set;

import org.jgrapht.graph.*;
/**
 * @author antonio
 *
 */
public class Text2Graph {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DefaultDirectedWeightedGraph<String, DefaultWeightedEdge> g = 
				new DefaultDirectedWeightedGraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);

	        String v1 = "v1";
	        String v2 = "v2";
	        String v3 = "v3";
	        String v4 = "v4";
	        
	        
	        // add the vertices
	        g.addVertex(v1);
	        g.addVertex(v2);
	        g.addVertex(v3);
	        g.addVertex(v4);
	        
	        // add edges to create a circuit
	        g.addEdge(v1, v2);
	        g.addEdge(v1, v3);
	        g.addEdge(v1, v1);
	        g.addEdge(v1, v4);
	        g.addEdge(v2, v3);
	        g.addEdge(v3, v4);
	        g.addEdge(v4, v1);
	        
	        DefaultWeightedEdge e1 = new DefaultWeightedEdge();
	        g.addEdge(v4, v2, e1);
	        g.setEdgeWeight(e1, 0.9);
	        
	        System.out.println("testing graph edges v1 --> v2: " + g.containsEdge(v1, v2));
	        System.out.println("testing graph edges v2 --> v1: " + g.containsEdge(v2, v1));
	        System.out.println("testing graph edges v1 --> v4: " + g.containsEdge(v1, v4));
	        System.out.println("outdegrees of v1: " + g.outDegreeOf(v1));
	        System.out.println("graph to string:" + g.toString());
	        System.out.println("incoming edges of v4: " + g.incomingEdgesOf(v4));
	        System.out.println("outgoing edges of v1: " + g.outgoingEdgesOf(v1));
	        System.out.println("outgoing edges of v4: " + g.outgoingEdgesOf(v4));
	        DefaultWeightedEdge e2 = g.getEdge(v1, v4);
	        
	        System.out.println("getting edge v1 --> v4: " + g.getEdge(v1, v4) + ", weight: " + g.getEdgeWeight(e1) );
	        System.out.println("edges of v1: " + g.edgesOf(v1));
	        
	}

}
