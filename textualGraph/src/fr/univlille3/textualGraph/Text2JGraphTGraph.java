/**
 * 
 */
package fr.univlille3.textualGraph;

import grph.algo.distance.FloydWarshallAlgorithm;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.xml.transform.TransformerConfigurationException;

import org.jgrapht.DirectedGraph;
import org.jgrapht.GraphPath;
import org.jgrapht.Graphs;
import org.jgrapht.alg.BellmanFordShortestPath;
import org.jgrapht.alg.ConnectivityInspector;
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.alg.EulerianCircuit;
import org.jgrapht.alg.FloydWarshallShortestPaths;
import org.jgrapht.alg.HamiltonianCycle;
import org.jgrapht.alg.KShortestPaths;
import org.jgrapht.alg.MinSourceSinkCut;
import org.jgrapht.alg.NeighborIndex;
import org.jgrapht.alg.StoerWagnerMinimumCut;
import org.jgrapht.alg.StrongConnectivityInspector;
import org.jgrapht.alg.TarjanLowestCommonAncestor;
import org.jgrapht.alg.VertexCovers;
import org.jgrapht.alg.cycle.SzwarcfiterLauerSimpleCycles;
import org.jgrapht.ext.DOTExporter;
import org.jgrapht.ext.StringEdgeNameProvider;
import org.jgrapht.ext.StringNameProvider;
import org.jgrapht.graph.*;
import org.neo4j.graphalgo.impl.shortestpath.FloydWarshall;
import org.xml.sax.SAXException;
import org.jgrapht.ext.GmlExporter;
import org.jgrapht.ext.GraphMLExporter;

/**
 * @author antonio
 * @date 1 juil. 2015 2015
 * textualGraph
 * fr.univlille3.textualGraph
 * Text2JGraphTGraph.java
 * 
 */
public class Text2JGraphTGraph {
	static ArrayList<String> _lines = new ArrayList<String>();
	static DirectedGraph<String, DefaultEdge> _g = new DefaultDirectedGraph<String, DefaultEdge>(DefaultEdge.class);	

	
    // constructs a directed graph with the specified vertices and edges
	
	
	static void loadTextFromFile(String aFile){
		try {
			BufferedReader in = new BufferedReader(new FileReader(aFile));
			while(in.ready()){
				_lines.add(in.readLine());
			}
			in.close();
		} 
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	
	public void tokenizeText(String aText){
		//TODO later: we're working on a tokenized text already
		//format is one token 2gram per line, separated by a tab character
	}
	
	
	static void buildGraph(){
		DirectedGraph<String, DefaultEdge> dg = new DefaultDirectedGraph<String, DefaultEdge>(DefaultEdge.class);	
		
		TreeSet<String> tokens = new TreeSet<String>();
		for(int i = 0; i < _lines.size(); i++){
			tokens.add( _lines.get(i));//on a la liste unique des tokens
		}
		//on construit le graphe
		//        directedGraph.addVertex("a");
		//        directedGraph.addEdge("a", "b");
		String[] bigrams = new String[2];
		for (String s : tokens){
			bigrams = s.split("\t");
			dg.addVertex(bigrams[0]);
			dg.addVertex(bigrams[1]);
			//les 2 nodes ont été ajoutés
			dg.addEdge(bigrams[0], bigrams[1]);
			//on ajoute maintenant le lien entre les nodes
			//System.err.println("added: " + bigrams[0] + " -> " + bigrams[1]);
		}
		_g = dg;
		
	}
	
	static void printGraph(int type, File file) throws IOException{//types: DOT, GML, GraphML
		 StringNameProvider<String> vname = new StringNameProvider<String>();
	     StringNameProvider<String> vlabel = new StringNameProvider<String>();
	     StringEdgeNameProvider<DefaultEdge> eid = new StringEdgeNameProvider<DefaultEdge>();
	     StringEdgeNameProvider<DefaultEdge> ename = new StringEdgeNameProvider<DefaultEdge>();
	     FileWriter fw = new FileWriter(file);

	        
	        if(type == 1){
	        	System.err.println("Writing dot representation of " + file);
	        	DOTExporter<String, DefaultEdge> gexp = new DOTExporter<String, DefaultEdge>(vname, vlabel,ename);
		        StringWriter sw = new StringWriter();
		        gexp.export(sw, _g);
		        StringBuffer out = sw.getBuffer();
		        out.insert(12, "rankdir=LR;\n");
		        //System.out.println(sw);
		        BufferedWriter bw = new BufferedWriter(fw);
		        bw.write(out.toString());
		        bw.flush();
		        bw.close();
	        }
	        if(type == 2){
	        	System.err.println("Writing gml representation of " + file);
		        GmlExporter<String, DefaultEdge> gexp = new GmlExporter<String, DefaultEdge>(vname, vlabel, eid, ename);
		        StringWriter sw = new StringWriter();
		        gexp.export(sw, _g);
		        //System.out.println(sw);
		        StringBuffer out = sw.getBuffer();
		        BufferedWriter bw = new BufferedWriter(fw);
		        bw.write(out.toString());
		        bw.flush();
		        bw.close();

	        }
	        if(type == 3){
	        	System.err.println("Writing graphML representation of " + file);

		        GraphMLExporter<String, DefaultEdge> gexp = new GraphMLExporter<String, DefaultEdge>(vname, vlabel,eid, ename);
		        StringWriter sw = new StringWriter();
		        try {
					gexp.export(sw, _g);
				} catch (TransformerConfigurationException | SAXException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        StringBuffer out = sw.getBuffer();
		        BufferedWriter bw = new BufferedWriter(fw);
		        bw.write(out.toString());
		        bw.flush();
		        bw.close();
	        }
	}
	

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Text2JGraphTGraph t = new Text2JGraphTGraph();
		String inFile = args[0];
		loadTextFromFile(inFile);
		String out = args[0].replace(".csv", "_out");
		int type = 0;
		buildGraph();
		if(args.length > 2){
		if(args[2].equals("1")){
			System.err.println("dot file: " + out + ".dot");
			type = 1;
			out = out + ".dot";
		}
		else if(args[2].equals("2")){
			System.err.println("gml file: " + out + ".gml");
			type = 2;
			out = out + ".gml";
		}
		else if(args[2].equals("3")){
			System.err.println("graphML file");
			type =3;
			out = out + "_out.graphml";
		}
		File fout = new File(out);
		printGraph(type, fout);
		}
		
		/*StrongConnectivityInspector sci =
	            new StrongConnectivityInspector(t._g);
	        List stronglyConnectedSubgraphs = sci.stronglyConnectedSubgraphs();

	        // prints the strongly connected components
	        System.out.println("Strongly connected components:");
	        for (int i = 0; i < stronglyConnectedSubgraphs.size(); i++) {
	            System.out.println(stronglyConnectedSubgraphs.get(i));
	        }
	        System.out.println();
	        */

	        // Prints the shortest path from vertex i to vertex c. 
	        System.out.println("Shortest path from start to target");
	        System.out.println("Dijkstra:");
	        List path =
	            DijkstraShortestPath.findPathBetween(t._g, "sss", "eee");
	        System.out.println(path);
	        path = DijkstraShortestPath.findPathBetween(t._g, "sss", "eee");
	        System.out.println(path + "\n");
	        
	        path.removeAll(path);
	        
	        System.out.println("BellmanFord:");
	        BellmanFordShortestPath p = new BellmanFordShortestPath(_g, "sss", 10, 5.0);
	        path = BellmanFordShortestPath.findPathBetween(_g, "sss", "eee");
	        System.out.println(path);
	        p = new BellmanFordShortestPath(_g, "sssChunkVN", 10, 5.0);
	        path = BellmanFordShortestPath.findPathBetween(_g, "sss", "eee");
	        System.out.println(path + "\n");
	        path.removeAll(path);
	        
	        /*
	        FloydWarshallShortestPaths<String, DefaultEdge> f = new FloydWarshallShortestPaths<String, DefaultEdge>(_g);
	        System.out.println("FloydWarshall:");
	        List<GraphPath<String, DefaultEdge>> l = f.getShortestPaths("sss");
	        System.out.println(l);
	        */
	        
	        System.out.println("\nKShortestPaths:");
	        KShortestPaths<String, DefaultEdge> k = new KShortestPaths<String, DefaultEdge>(_g, "sss", 10);
	        List<GraphPath<String, DefaultEdge>> kp = k.getPaths("eee");
	        for(GraphPath<String, DefaultEdge> gp : kp){
	        	System.out.println(gp);
	        }
	        
	        /*System.out.println();
	        NeighborIndex<String, DefaultEdge> ni = new NeighborIndex<String, DefaultEdge>(_g);
	        System.out.println("neighbors of sss: " + ni.neighborListOf("sss"));
	        
	        System.out.println();
	        */
	        
	        /*ConnectivityInspector<String, DefaultEdge> ci = new ConnectivityInspector<String, DefaultEdge>(_g);
	        Set<String> conns = ci.connectedSetOf("rooms");
	        System.out.println("Connected set of rooms");
	        for(String v : conns){
	        	System.out.println(v);
	        }*/
	        
	        /*
	        StrongConnectivityInspector<String, DefaultEdge> sci = new StrongConnectivityInspector<String, DefaultEdge>(_g);
	        List<Set<String>> strong = sci.stronglyConnectedSets();
	        System.out.println("Strongly connected sets : \n" + strong + "\n");
	        */
	        
	        /*
	        System.out.println("Cycles");
	        
	        SzwarcfiterLauerSimpleCycles<String, DefaultEdge> cy = new SzwarcfiterLauerSimpleCycles<String, DefaultEdge>(_g);
	        List<List<String>> cycles = cy.findSimpleCycles();
	        for(List<String> sub : cycles){
	        	System.out.println(sub);
	        }
	        */
	        
	        /*
	        MinSourceSinkCut<String, DefaultEdge> mscut = new MinSourceSinkCut<String, DefaultEdge>(_g);
	        mscut.computeMinCut("sssChunkSN", "eeeChunkSN");
	        System.out.println("Mincut from sss: "+ mscut.getSinkPartition());
	        System.out.println("Mincut sink partition: " + mscut.getSinkPartition());
	        System.out.println("Mincut source partition: " + mscut.getSourcePartition());
	        System.out.println("Cut edges: " + mscut.getCutEdges());
	        */
	        System.out.println();
	        
	        //System.out.println("Predecessors of vertex \'calls\': " + Graphs.predecessorListOf(_g, "calls"));
	        //System.out.println("incoming edges of \'calls\': " + _g.incomingEdgesOf("calls").size());
	        Set<String> vset = _g.vertexSet();
	        for(String v : vset){
	        	System.out.println(v + " has " + _g.incomingEdgesOf(v).size() + " incoming edges");
	        }
	        
	}

}
