/**
 * 
 */
package fr.univlille3.textualGraph;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.jgrapht.GraphPath;
import org.jgrapht.alg.BellmanFordShortestPath;
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.alg.FloydWarshallShortestPaths;
import org.jgrapht.alg.KShortestPaths;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;


/**
 * @author antonio
 * @date 10 juil. 2015 2015
 * textualGraph
 * fr.univlille3.textualGraph
 * Text2WeightedGraph.java
 * 
 */
public class Text2WeightedGraph {
	static ArrayList<String> _lines = new ArrayList<String>();
	static DefaultDirectedWeightedGraph<String, DefaultWeightedEdge> _dwg = new DefaultDirectedWeightedGraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);
	static DefaultDirectedWeightedGraph<String, DefaultWeightedEdge> _hubs = new DefaultDirectedWeightedGraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);
	static String START = "";
	static String END = "";
	
	
	public static void printGraphToDot(DefaultDirectedWeightedGraph<String, DefaultWeightedEdge> dwg){
        Set<DefaultWeightedEdge> es = dwg.edgeSet();
		System.out.println("digraph G {\nrankdir=LR;");
        for(DefaultWeightedEdge ee : es){
        	System.out.println("\"" + dwg.getEdgeSource(ee) + "\"" 
        							+ " -> \"" + dwg.getEdgeTarget(ee) 
        							+ "\" [label=\"" 
        							+ dwg.getEdgeWeight(ee) + "\"];");
        }
        System.out.println("\n}");
	}
	
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
	
	public static ArrayList<String> tokenizeText(String aText, String delimiter){
		//TODO: use guava splitter?
		ArrayList<String> out = new ArrayList<String>();
		
		String[] tmp = aText.split(delimiter);
		for(String t: tmp){
			if(!t.equals("")){//trimming null tokens
				out.add(t);
			}
		}
		return out;
	}
	
	void BuildDirectedWeightedGraphFromText(ArrayList<String> tokens){
		//String v1 = "A}";
        //String v2 = "B";
		//g.addVertex(v1);
		//g.addVertex(v2);
		//DefaultWeightedEdge e1 = new DefaultWeightedEdge(); 
		//g.setEdgeWeight(e1, 1);
		//g.addEdge(v1, v2, e1);
		
		for(int i = 0; i < tokens.size()-1; i++){//do this for all tokens minus the 2 last ones
			String w1 ="";
			String w2 = "";
				
			w1 = tokens.get(i);
			w2 = tokens.get(i+1);
			if(_dwg.containsEdge(w1, w2)){
				//System.err.println(w1 + " -> " + w2 + " already exists!");
				//System.err.println("updating weight");
				
				DefaultWeightedEdge temp = _dwg.getEdge(w1, w2);
				double w = _dwg.getEdgeWeight(temp);
				//System.err.println("weight was " + w);
				if(Double.compare(w, 1) <= 1){//weight was 1 or has already been updated
					w = (double) w/2;
					//System.err.println("decreasing weight to " + w);

				}
				else{
					w = (double)1/(w+1);
					//System.err.println("set weight to " + w);
				}
				_dwg.setEdgeWeight(temp, w);
				//System.err.println("weight updated to " + _dwg.getEdgeWeight(temp));

			}
			//System.err.println("adding " + w1 + " -> " + w2);

			_dwg.addVertex(w1);
			_dwg.addVertex(w2);
			DefaultWeightedEdge edge = new DefaultWeightedEdge(); 
			_dwg.setEdgeWeight(edge,1);//default weight is 1
			_dwg.addEdge(w1, w2, edge);
		}	
		
	}
	
	static void getDijkstraShortesPath(String start, String end){
		DijkstraShortestPath<String, DefaultWeightedEdge> dijk = new DijkstraShortestPath<String, DefaultWeightedEdge>(_dwg, start, end);
		System.out.println("shortest path: " + dijk.getPath() + ", weight: " + dijk.getPathLength());
	}
	
	static void walkThroughHubs(String start, String end){//walk the graph through its hubs
		//base case: graph represents 2 basic graphs
		//start from start
		//get immediate neighbor(s?)
		//for each successor, test whether it's a hub
		//if it's a hub, add it to the final path
		//if not, go to next hub
		
		//System.err.println("start: " + start);
		printAllHubs();
		
	}
	
	static void searchAllHubs(){
		Set<String> vertices = _dwg.vertexSet();
		for(String v: vertices){
			if(isHub(v)){
				
			}
		}
	}
	
	static void printAllHubs(){
		Set<String> vertices = _dwg.vertexSet();
		System.out.println("Digraph g {\nrankdir=LR;");

		for(String v: vertices){
			if(isHub(v)){
				//System.err.println("Hub found : " + v);
				
			}
		}
		System.out.println("}");

	}
	
	
	
	static boolean isHub(String vertex){//determines whether a given vertex is a hub, i.e. it has > 1 incoming edges
		//TODO: extend method to any graph
		boolean res = false;

		if(_dwg.inDegreeOf(vertex) > 1){
			//System.err.println("incoming edges of " + vertex + ": " +_dwg.incomingEdgesOf(vertex));
			//System.err.println("hub: " + vertex);
			Set<DefaultWeightedEdge> e = _dwg.incomingEdgesOf(vertex);
			for(DefaultWeightedEdge ed : e ){
				if(_dwg.getEdgeWeight(ed) < 1){
					System.out.println("\"" + _dwg.getEdgeSource(ed) + "\" -> \"" + _dwg.getEdgeTarget(ed)  + "\" [label=\"" + _dwg.getEdgeWeight(ed) + "\"];");
					res = true;
				}
			}
			res = true;
		}
		else if(_dwg.inDegreeOf(vertex) == 1){//if weight of incoming edge < 1 (means the node is frequent)
			Set<DefaultWeightedEdge> e = _dwg.incomingEdgesOf(vertex);
				for(DefaultWeightedEdge ed : e ){
					if(_dwg.getEdgeWeight(ed) < 1){
						System.out.println("\"" + _dwg.getEdgeSource(ed) + "\" -> \"" + _dwg.getEdgeTarget(ed)  + "\" [label=\"" + _dwg.getEdgeWeight(ed) + "\"];");
						res = true;
					}
				}
		}


		return res;
	}
	
	void unionDWGraph(DefaultDirectedWeightedGraph<String, DefaultWeightedEdge> graph1, DefaultDirectedWeightedGraph<String, DefaultWeightedEdge> graph2){//union of 2 graphs
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Text2WeightedGraph twg = new Text2WeightedGraph();
		START = args[1];
		END = args[2];
		System.err.println("searching for " + START + ", " + END);
		/*String test = "this , is, a tricky... string!!!";
		System.err.println(tokenizeText(test, "[ ,\\.!;]"));
		*/
		String test = "{S} well the rooms were pretty clean, gorgeous and really sunny, even though they were very noisy!!!{E}\n"
					+ "{S} the rooms were noisy {E}\n"
					+ "{S} the rooms were noisy {E}\n"
					+ "{S} the rooms were also noisy {E}\n"
					+ "{S} both our rooms were very very noisy {E}\n"
					+ "{S} the our rooms were only so so... {E}\n"
					+ "{S} our rooms were not very clean, though! {E}\n"	
					+ "{S} both our rooms were extremely well situated, but also very noisy {E}\n"					
					+ "{S} the rooms were very clean {E}\n"
					+ "{S} the rooms were very very clean {E}"; 
		String test2 = "{S} k a t a b t u  {E}\n"
						+ "{S} k a t a b t a  {E}\n"
						+ "{S} k a t a b t i  {E}\n"
						+ "{S} k a t a b a  {E}\n"
						+ "{S} k a t a b a t  {E}\n"
						+ "{S} k a t a b t u m aa {E}\n"
						+ "{S} k a t a b aa {E}\n"
						+ "{S} k a t a b a t aa {E}\n"
						+ "{S} k a t a b n aa {E}\n"
						+ "{S} k a t a b t u m  {E}\n"
						+ "{S} k a t a b t u nn a  {E}\n"
						+ "{S} k a t a b uu {E}\n"
						+ "{S} k a t a b n a  {E}\n"
						+ "{S} k i t a b u {E}\n"
						+ "{S} m e k t u b {E}\n"
						+ "{S} a k t u b u {E}\n"
						+ "{S} k a t a b t u nn a {E}";
		String test3 = "{S} k a t a b t u  {E}\n"
					+ "{S} k i t a b u {E}\n"
					+ "{S} m e k t u b {E}\n"
					+ "{S} a k t u b u {E}\n"
					+ "{S} k a t a b t u nn a {E}";
		
		String test4 = 	"{S} Thales lance une opération de rachat hostile sur Natixis {E}\n"
						+"{S} Thales lance une opération hostile sur Natixis {E}\n"
						+"{S} Thales lance une opération publique d'achat jugée hostile sur Natixis {E}\n"
						+"{S} Thales lance une OPA hostile sur Natixis {E}";

		String test5 = "{S} le chat mange la souris {E}\n"
						+ "{S} le très gros chat mange la toute petite souris {E}\n"
						+ "{S} la petite souris mange le fromage {E}\n";
		String test6 = "{S} m e k t u b {E}\n"
						+ "{S} k i t a b {E}";
		String test7 = "{S} small cell lung cancer {E}\n"
				+ "{S} small cell lung cancer ( sclc ) {E}\n"
				+ "{S} small cell neuroendocrine carcinoma ( scc ) {E}\n"
				+ "{S} small lesions {E}\n"
				+ "{S} small-cell carcinoma {E}\n"
				+ "{S} small-cell lung cancer {E}\n"
				+ "{S} small-cell lung cancer ( sclc ) {E}\n";
						
		//ArrayList<String> tokens = tokenizeText(test, "[ ,\\?\\.;:!\n\r]+"); 
		//System.err.println(tokens);
		//ArrayList<String> tokens = tokenizeText(test4, "[ ,\\?\\.;:!\n\r']+"); 
		//ArrayList<String> tokens = tokenizeText(test7, "[ ,\\?\\.;:!\n\r']+"); 
		loadTextFromFile(args[0]);
		ArrayList<String> tokens = new ArrayList<String>();
		for(String line : _lines){
			tokens.addAll(tokenizeText(line, "[ ,\\?\\.;:!\n\r']+"));
		}
		
		twg.BuildDirectedWeightedGraphFromText(tokens);
		System.out.println("Main graph:\n---------------------");
		printGraphToDot(_dwg);
		getDijkstraShortesPath(START, END);
		//getDijkstraShortesPath("{S}", "{E}");

		System.out.println("Bellmann Ford shortest paths:");
		BellmanFordShortestPath<String, DefaultWeightedEdge> b = new BellmanFordShortestPath<String, DefaultWeightedEdge>(_dwg, START, 10, 0.5);
		System.out.println(b.getPathEdgeList(END));
		//System.out.println(b.getPathEdgeList("cancer"));

		
		FloydWarshallShortestPaths<String, DefaultWeightedEdge> f = new FloydWarshallShortestPaths<String, DefaultWeightedEdge>(_dwg);
		System.out.println("Floyd Marshall shortest paths:\n" + "diameter: " + f.getDiameter() + "\n" + f.getShortestPath(START, END));
		//System.out.println("Floyd Marshall shortest paths:\n" + "diameter: " + f.getDiameter() + "\n" + f.getShortestPath("{S}", "cancer"));

		KShortestPaths<String , DefaultWeightedEdge> k = new KShortestPaths<String, DefaultWeightedEdge>(_dwg, START, 5);
		List<GraphPath<String, DefaultWeightedEdge>> l = k.getPaths(END);
		for(int i = 0; i < l.size(); i++){
			System.out.println(l.get(i));
		}
		System.out.println("Hub graph:\n---------------------");

		walkThroughHubs(START, END);
		//walkThroughHubs("{S}", "{E}");

	}

}
