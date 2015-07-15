/**
 * 
 */
package fr.univlille3.textualGraph;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.xml.transform.TransformerConfigurationException;

import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.alg.KShortestPaths;
import org.jgrapht.alg.cycle.JohnsonSimpleCycles;
import org.jgrapht.ext.DOTExporter;
import org.jgrapht.ext.GmlExporter;
import org.jgrapht.ext.GraphMLExporter;
import org.jgrapht.ext.IntegerEdgeNameProvider;
import org.jgrapht.ext.IntegerNameProvider;
import org.jgrapht.ext.StringEdgeNameProvider;
import org.jgrapht.ext.StringNameProvider;
import org.jgrapht.graph.*;
import org.xml.sax.SAXException;
/**
 * @author antonio
 *
 */
public class Text2Graph {
	

	/**
	 * @param args
	 * @throws SAXException 
	 * @throws TransformerConfigurationException 
	 */
	public static void main(String[] args) throws TransformerConfigurationException, SAXException {
		// TODO Auto-generated method stub
		DefaultDirectedWeightedGraph<String, DefaultWeightedEdge> g = 
				new DefaultDirectedWeightedGraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);

	        String v1 = "{S}";
	        String v2 = "our";
	        String v3 = "rooms";
	        String v4 = "were";
	        String v10 = "gorgeous";
	        String v6 = "very";
	        String v7 = "and";
	        String v8 = "{E}";
	        String v9 = "clean";
	        String v5 = "beautiful";


	        
	        
	        
	        
	        // add the vertices
	        g.addVertex(v1);
	        g.addVertex(v2);
	        g.addVertex(v3);
	        g.addVertex(v4);
	        g.addVertex(v5);
	        g.addVertex(v6);
	        g.addVertex(v7);
	        g.addVertex(v8);
	        g.addVertex(v9);
	        g.addVertex(v10);

	        
	        DefaultWeightedEdge e1 = new DefaultWeightedEdge();
	        g.setEdgeWeight(e1, 1);
	        DefaultWeightedEdge e2 = new DefaultWeightedEdge();
	        g.setEdgeWeight(e2, (double)1/10);
	        DefaultWeightedEdge e3 = new DefaultWeightedEdge();
	        g.setEdgeWeight(e3, 2);
	        DefaultWeightedEdge e4 = new DefaultWeightedEdge();
	        g.setEdgeWeight(e4, 1);
	        DefaultWeightedEdge e5 = new DefaultWeightedEdge();
	        g.setEdgeWeight(e5, (double)1/5);
	        DefaultWeightedEdge e6 = new DefaultWeightedEdge();
	        DefaultWeightedEdge e7 = new DefaultWeightedEdge();
	        DefaultWeightedEdge e8 = new DefaultWeightedEdge();
	        DefaultWeightedEdge e9 = new DefaultWeightedEdge();
	        DefaultWeightedEdge e10 = new DefaultWeightedEdge();
	        DefaultWeightedEdge e11 = new DefaultWeightedEdge();
	        g.setEdgeWeight(e11, 1);
	        DefaultWeightedEdge e12 = new DefaultWeightedEdge();
	        g.setEdgeWeight(e12, 2);
	        DefaultWeightedEdge e13 = new DefaultWeightedEdge();
	        g.setEdgeWeight(e13, 1);


	        // add edges to create a circuit
	        g.addEdge(v1, v3, e3);
	        g.addEdge(v1, v2, new DefaultWeightedEdge());
	        g.addEdge(v3, v4, e2);
	        g.addEdge(v4, v5, e3);
	        g.addEdge(v2, v3, e4);
	        g.addEdge(v3, v4, e5);
	        g.addEdge(v4,v5, e6);
	        g.addEdge(v5, v7, e7);
	        g.addEdge(v5, v8, e11);
	        g.addEdge(v7,  v6, e8);
	        g.addEdge(v6, v9, new DefaultWeightedEdge());
	        g.addEdge(v6, v6, new DefaultWeightedEdge());
	        g.addEdge(v9, v8, e10);
	        g.addEdge(v7, v9, e12);
	        g.addEdge(v4, v10, e13);	        
	        g.addEdge(v10, v8, new DefaultWeightedEdge());


	        
	        
	        StringNameProvider<String> vname = new StringNameProvider<String>();
	        StringNameProvider<String> vlabel = new StringNameProvider<String>();
	        StringEdgeNameProvider<DefaultWeightedEdge> eid = new StringEdgeNameProvider<DefaultWeightedEdge>();
	        StringEdgeNameProvider<DefaultWeightedEdge> ename = new StringEdgeNameProvider<DefaultWeightedEdge>();
	        
	        
	     
	        //GmlExporter<String, DefaultWeightedEdge> gexp = new GmlExporter<String, DefaultWeightedEdge>(vname, vlabel, eid, ename);

	        //GraphMLExporter<String, DefaultWeightedEdge> gexp = 
	        		//new GraphMLExporter<String, DefaultWeightedEdge>(vname, vlabel,eid, ename);
	        DOTExporter<String, DefaultWeightedEdge> gexp = new DOTExporter<String, DefaultWeightedEdge>(vname, vlabel,ename);
	        
	        StringWriter sw = new StringWriter();
	        //gexp.export(sw, g);
	        //System.out.println(sw);
	        
	        /*DefaultWeightedEdge e1 = new DefaultWeightedEdge();
	        g.addEdge(v4, v2, e1);
	        g.setEdgeWeight(e1, 0.9);*/
	        //DefaultWeightedEdge e1 = new DefaultWeightedEdge();

	        /* System.out.println("testing graph edges v1 --> v2: " + g.containsEdge(v1, v2));
	        System.out.println("testing graph edges v2 --> v1: " + g.containsEdge(v2, v1));
	        System.out.println("testing graph edges v1 --> v4: " + g.containsEdge(v1, v4));
	        System.out.println("outdegrees of v1: " + g.outDegreeOf(v1));
	        System.out.println("graph to string:" + g.toString());
	        System.out.println("incoming edges of v4: " + g.incomingEdgesOf(v4));
	        System.out.println("outgoing edges of v1: " + g.outgoingEdgesOf(v1));
	        System.out.println("outgoing edges of v4: " + g.outgoingEdgesOf(v4));
	        //DefaultWeightedEdge e2 = g.getEdge(v1, v4);
	        JohnsonSimpleCycles<String, DefaultWeightedEdge> js = new JohnsonSimpleCycles<String, DefaultWeightedEdge>();
	        js.setGraph(g);
	        List<List <String>> cycles =  js.findSimpleCycles();
	        System.out.println("cycles: " + cycles);*/
	        Set<String> vs = g.vertexSet();
	        System.out.println("Vertex set");
	        for(String v:vs){
	        	System.out.println(v);
	        }
	        Set<DefaultWeightedEdge> es = g.edgeSet();
	        System.out.println();
	        System.out.println("digraph G {\nrankdir=LR;");
	        for(DefaultWeightedEdge ee : es){
	        	System.out.println("\"" + g.getEdgeSource(ee) + "\"" + " -> \"" + g.getEdgeTarget(ee) + "\" [label=\"" + g.getEdgeWeight(ee) + "\"];");
	        }
	        System.out.println("}");
	        DijkstraShortestPath<String, DefaultWeightedEdge> dij = new DijkstraShortestPath<String, DefaultWeightedEdge>(g, "{S}", "{E}");
	        System.out.println("Dijkstra: " + dij.getPath() + ", weight: " + dij.getPathLength() + ", nb of nodes: " + dij.getPathEdgeList().size());
	        
	        KShortestPaths<String, DefaultWeightedEdge> ks = new KShortestPaths<String, DefaultWeightedEdge>(g, "{S}", 2);
	        System.out.println("K: " + ks.getPaths("{E}") );
	        
	        
	}

}
