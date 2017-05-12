/**
 * 
 */
package roadgraph;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import geography.GeographicPoint;


/**
 * @author RFlood 3/14/17
 *
 * The MapVertex class defines a vertex (intersection) on a map.

 */
public class MapVertex {
	
	// member variables
	private GeographicPoint loc;        // the lat/lon location of this vertex
	private ArrayList<MapEdge> edges;   // a list of edges (roads) that start at this vertex

	// Constructor
	public MapVertex(GeographicPoint loc) {
		this.loc = loc;
		this.edges = new ArrayList<MapEdge>();
	}
	
	// get the vertex's lat and lon
    public GeographicPoint getLoc() { return this.loc; }
    
	// get the array of edges
    public List<MapEdge> getEdges() {
    	return this.edges;
    }
    
    // add an edge to the vertex's list of edges
    public void setEdge(MapEdge edge) {
    	edges.add(edge);
    }
    
	// return a mapVertex as a String
	public String toString()
	{
		String s = loc.toString()+" has the following edges: \n";
		for (MapEdge e : edges) {
			s += e.toString()+"\n";
		}
	    //String s = String.format("%s is a %s %d km long.",this.edgeName, this.edgeType, this.length);
		//s += "  StartLoc:"+ startLoc.toString() + "\n  EndLoc:  "+ endLoc.toString() +"\n";
		
		return s;
	}
}
