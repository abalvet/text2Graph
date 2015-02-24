package fr.univlille3.textualGraph;

import java.io.*;
import java.nio.charset.Charset;
import java.util.*;

import org.apache.commons.*;
import org.apache.commons.io.*;
import org.apache.commons.collections4.*;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;

public class CSV2Gephi {
	DualHashBidiMap<String, String> _nodeMap = new DualHashBidiMap<String, String>();
	String _outLinks = "";
	String _outNodes = "";
	
	public void ReadNodeFile(String aCSVFilename){
		//reads a node list in csv format, and indexes each node in a HashMap
		File csvData = new File(aCSVFilename);
		try{
			List<String> lines = new ArrayList<String>();
			lines = FileUtils.readLines(csvData);//TODO: check consistency of file (id exists but no node)
		//CSVParser parser = CSVParser.parse(csvData, Charset.defaultCharset(), CSVFormat.TDF);
			for(String record : lines){
				String[] entry = record.split("\t");
				this._nodeMap.put(entry[0], entry[1]);
				this._outNodes = this._outNodes + entry[0] + "\t" + entry[1] + "\n";
				//System.err.println("adding: " + entry[0] + "," + entry[1]);
			}
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public void ReadLinksFile(String aCSVFilename){
		//reads a links file in csv format, and retrieves each node's ID to generate a
		// idSource --> idTarget output
		File linkData = new File(aCSVFilename);
		try{
			List<String> lines = new ArrayList<String>();
			lines = FileUtils.readLines(linkData);//TODO: check consistency of file (source | target | weight)
			String source, target, weight;
			String nodeinfo = "";
			for(String record : lines){
				String[] entry = record.split("\t");
				source = entry[0];
				target = entry[1];
				weight = entry[2];
				if((this._nodeMap.getKey(source) != "null") || (this._nodeMap.getKey(target)) != "null"){
				nodeinfo = nodeinfo 
						+ this._nodeMap.getKey(source) 
						+ "\t" + this._nodeMap.getKey(target) 
						+ "\t" + weight 
						+ "\n";
				//System.err.println(this._nodeMap.getKey(source) + " --> " + this._nodeMap.getKey(target) + ":" + weight);
				}
				//TODO: test for null values
			}
			this._outLinks = nodeinfo;

		}
		catch(IOException e){
			e.printStackTrace();
			
		}
	}
	
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CSV2Gephi t = new CSV2Gephi();
		String in = args[0];
		String out = args[1];
		t.ReadNodeFile(in);
		t.ReadLinksFile(out);
		System.out.println(t._outNodes);
		System.out.println(t._outLinks);
	}

}
