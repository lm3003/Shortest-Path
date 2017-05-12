/**
 * 
 */
package roadgraph;

import java.util.ArrayList;
import java.util.List;

import geography.GeographicPoint;

/**
 * @author RFlood 3/14/17
 *
 * The MapEdge class defines an edge (road) between two 
 * vertices (intersections) on a map. It holds the endpoints,
 * the edge's name, type and length.
 */
public class MapEdge {

	// member variables
	private GeographicPoint startLoc;
	private GeographicPoint endLoc;
	
	private String edgeName;  // name of road
	private String edgeType;  // type of road (eg. freeway, residential, etc.)
	private double length;    // length of road in km
	
	// constructors
	public MapEdge(GeographicPoint startLoc, GeographicPoint endLoc, 
						String edgeName, String edgeType, double length) {
		this.startLoc = startLoc;
		this.endLoc = endLoc;
		this.edgeName = edgeName;
		this.edgeType = edgeType;
		this.length = length;
	}	
	public MapEdge(GeographicPoint startLoc, GeographicPoint endLoc) {
		this.startLoc = startLoc;
		this.endLoc = endLoc;
		this.edgeName = "";
		this.edgeType = "";
		this.length = 0;
	}

	// return a mapEdge as a String
	public String toString()
	{
		String s = this.edgeName + " is a " +this.edgeType+" ";
	    s += this.length + " km long.\n";
	    //String s = String.format("%s is a %s %d km long.",this.edgeName, this.edgeType, this.length);
		s += "  StartLoc:"+ startLoc.toString() + "\n  EndLoc:  "+ endLoc.toString() +"\n";
		
		return s;
	}

	// get the start location's lat and lon
	public GeographicPoint getStartLoc() { return this.startLoc; }
	// get the end location's lat and lon
	public GeographicPoint getEndLoc() { return this.endLoc; }	
	// get the road name
	public String getEdgeName() { return this.edgeName; }	
	// get the road type
	public String getEdgeType() { return this.edgeType; }
	// get the length of the road segment
	public double getLength() { return this.length; }	

}
