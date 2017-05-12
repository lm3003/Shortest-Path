/**
 * @author UCSD MOOC development team and YOU
 * 
 * A class which constructs and stores map edges, from start to goal Node
 * Edges are streets, intersections of one or more streets are Nodes
 *
 */
package roadgraph;

public class Edge {
	
	Node fromLocation;
	Node toLocation;
	String roadName;
	String roadType;
	double length;
	
	// constructor
	public Edge(Node pFrom, Node pTo) {
	
		fromLocation = pFrom;
		toLocation = pTo;
		
	}
	
	//Getters and Setters
	public void setRoadName(String pRoadName) {
		this.roadName = pRoadName;
	}
	public void setRoadType(String pRoadType) {
		this.roadType = pRoadType;
	}
	public void setLength(double pLength) {
		this.length = pLength;
	}
	public Node getFromLocation() {
		return fromLocation;
	}
	public Node getToLocation() {
		return toLocation;
	}
	public String getRoadName() {
		return roadName;
	}
	public String getRoadType() {
		return roadType;
	}
	public double getLength() {
		return length;
	}
	
}
