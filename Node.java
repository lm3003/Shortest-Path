/**
 * @author UCSD MOOC development team and YOU
 * 
 * A class which constructs and stores map vertices and an edgeList of edges
 * Nodes in the graph are intersections between streets
 *
 */
package roadgraph;

import java.util.ArrayList;
import java.util.List;

import geography.GeographicPoint;

public class Node {
	
	double latitude;
	double longitude;
	GeographicPoint node;
	List<Edge> edgeList;
	
	// constructors
	public Node(double latitude, double longitutude) {
		
		node = new GeographicPoint(latitude, longitude);
		edgeList = new ArrayList<Edge>();
		
	}
	
	public Node(GeographicPoint pNode) {
		
		node = pNode;
		
	}
	
	//Getters and Setters
	public void setNode(GeographicPoint pNode) {
		this.node = pNode;
	}
	public GeographicPoint getNode() {
		return node;
	}
	public void setEdgeList(List<Edge> pEdgeList) {
		this.edgeList = pEdgeList;
	}
	public List<Edge> getEdgeList() {
		return edgeList;
	}

}
