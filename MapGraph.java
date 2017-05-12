/**
 * @author UCSD MOOC development team and YOU
 * 
 * A class which reprsents a graph of geographic locations
 * Nodes in the graph are intersections between 
 *
 */
package roadgraph;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

import geography.GeographicPoint;
import util.GraphLoader;

/**
 * @author UCSD MOOC development team and YOU
 * 
 * A class which represents a graph of geographic locations
 * Nodes in the graph are intersections between 
 *
 */
public class MapGraph {
	//TODO: Add your member variables here in WEEK 3
	private int numVertices;
	private int numEdges;
	private static Map<GeographicPoint,Node> adjListsMap;
	
	private static List<Node> queue;
	private static Set<Node> visited;		
	private static Map<Node, List<Node>> parentMap;
	private static List<GeographicPoint> parentList;
	private List<Node> childList;
	private static Map<Node, List<GeographicPoint>> tempPathList;
	
	//optional association of String labels to vertices 
	private static Map<GeographicPoint,String> vertexLabels;
	
	/** 
	 * Create a new empty MapGraph 
	 */
	public MapGraph()
	{
		// TODO: Implement in this constructor in WEEK 3
		numVertices = 0;
		numEdges = 0;
		adjListsMap = new HashMap<GeographicPoint,Node>();
		vertexLabels = new HashMap<GeographicPoint,String>();
	}
	
	/**
	 * Get the number of vertices (road intersections) in the graph
	 * @return The number of vertices in the graph.
	 */
	public int getNumVertices()
	{
		//TODO: Implement this method in WEEK 3
		//return 0;
		return numVertices;
	}
	
	/**
	 * Return the intersections, which are the vertices in this graph.
	 * @return The vertices in this graph as GeographicPoints
	 */
	public Set<GeographicPoint> getVertices()
	{
		//TODO: Implement this method in WEEK 3
		return new HashSet<GeographicPoint>(adjListsMap.keySet());
		
	}
	
	/**
	 * Get the number of road segments in the graph
	 * @return The number of edges in the graph.
	 */
	public int getNumEdges()
	{
		//TODO: Implement this method in WEEK 3
		//return 0;
		return numEdges;
	}

	
	
	/** Add a node corresponding to an intersection at a Geographic Point
	 * If the location is already in the graph or null, this method does 
	 * not change the graph.
	 * @param location  The location of the intersection
	 * @return true if a node was added, false if it was not (the node
	 * was already in the graph, or the parameter is null).
	 */
	public boolean addVertex(GeographicPoint location)
	{
		// TODO: Implement this method in WEEK 3
		Node newVertex = new Node(location);
		
		if (location != null && !adjListsMap.containsKey(location)) {
			adjListsMap.put(location, newVertex);
			numVertices ++;
			return true;
		}
		else return false;
	}
	
	/**
	 * Adds a directed edge to the graph from pt1 to pt2.  
	 * Precondition: Both GeographicPoints have already been added to the graph
	 * @param from The starting point of the edge
	 * @param to The ending point of the edge
	 * @param roadName The name of the road
	 * @param roadType The type of the road
	 * @param length The length of the road, in km
	 * @throws IllegalArgumentException If the points have not already been
	 *   added as nodes to the graph, if any of the arguments is null,
	 *   or if the length is less than 0.
	 */
	public void addEdge(GeographicPoint from, GeographicPoint to, String roadName,
			String roadType, double length) throws IllegalArgumentException {

		//TODO: Implement this method in WEEK 3
		if (!adjListsMap.containsKey(from) || !adjListsMap.containsKey(to) || from == null || to == null || length <= 0) {
				throw new IllegalArgumentException();
		}
				
		Node fromNode = adjListsMap.get(from);
		Node toNode = adjListsMap.get(to);
		Edge newEdge = new Edge(fromNode, toNode);
		
		List<Edge> newEdgeList = new ArrayList<Edge>();
		if (fromNode.getEdgeList() == null) fromNode.setEdgeList(newEdgeList);
		fromNode.getEdgeList().add(newEdge);
		
		newEdge.setRoadName(roadName);
		newEdge.setRoadType(roadType);
		newEdge.setLength(length);
		numEdges ++;
		
	}
	

	/** Find the path from start to goal using breadth first search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest (unweighted)
	 *   path from start to goal (including both start and goal).
	 */
	public List<GeographicPoint> bfs(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
        Consumer<GeographicPoint> temp = (x) -> {};
        return bfs(start, goal, temp);
	}
	
	/** Find the path from start to goal using breadth first search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest (unweighted)
	 *   path from start to goal (including both start and goal).
	 */
	public List<GeographicPoint> bfs(GeographicPoint start, 
			 					     GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		// TODO: Implement this method in WEEK 3
		
		//Step 0: Assign startNode goalNode as objects of class Node via a lookup of the adjacency List HashMap
		Node startNode = adjListsMap.get(start);
		Node goalNode = adjListsMap.get(goal);
				
		//Step 1: initialize variables and lists
		queue = new ArrayList<Node>();
		visited = new HashSet<Node>();		
		parentMap = new HashMap<Node, List<Node>>();
		List<Node> childList = new ArrayList<Node>();
		parentList = new ArrayList<GeographicPoint>();
		Boolean anyleft = true;
		Node currNode = startNode;
		
		//Step 2: Add startNode to queue
		queue.add(currNode);
		childList.add(currNode);
		
		//Step 3: Loop through queue while not empty, DeQueue node from top of Queue, if currNode==goalNode return parentList
		while(anyleft) {
			
			//Step 3A: Set currNode to equal the 'first in line' node of the queue, remove it from queue and add it to visited list
			currNode = queue.get(0);
			queue.remove(0);
			visited.add(currNode);
			
			//Step 3B: Check to see if currNode equals goalNode and return parentList
			if(currNode.equals(goalNode)) {	
				setParentList(startNode, goalNode);
				anyleft = false;
				return parentList;
			}

			//Step 3C: Helper Method: add non-visited Nodes, with edges to the currNode, to the end of the queue
			addNotVisitedNodestoQueue(currNode);
			
			//Step 3D: Helper Method: Create parent map key for currNode, adding not visited (n) neighbors to List of children 
			setParentMap(currNode);
			// (optional: print)
			// System.out.println(parentMapPrint());

			//Step 3E: Helper Method: add non-visited Nodes, with edges to the currNode, to the visited list
			addNodesToVisited(currNode);			
			
			//optional print
			//System.out.println(queueandvisitedPrint(currNode));
			
			if (queue.isEmpty()) anyleft = false;
			
		} //end while
		
		
		// Hook for visualization.  See writeup.
		//nodeSearched.accept(next.getLocation());
		nodeSearched.accept(currNode.getNode());

		return null;
	}
	
	//New support method for week3
	
	/**Method:addNotVisitedNodestoQueue()
	 * Add non-visited Nodes with edges to the current Node, to the end of the queue
	 * 
	 * @param pCurrNode The current Node
	 */
	private void addNotVisitedNodestoQueue(Node pCurrNode) {
				
		if(pCurrNode.getEdgeList() != null) {
			for (int i = 0; i < pCurrNode.edgeList.size(); i++) {
				Node toLocation = pCurrNode.edgeList.get(i).getToLocation();
				if(!visited.contains(toLocation)) {
					queue.add(toLocation);
				}
			}
		}
				
	}
	
	/**Method:addNodesToVisited()
	 * Add Nodes, with edges to the current Node, to the visited list
	 * 
	 * @param pCurrNode The current Node
	 */
	private void addNodesToVisited(Node pCurrNode) {
				
		if(pCurrNode.getEdgeList() != null) {
			for (int i = 0; i < pCurrNode.edgeList.size(); i++) {
				Node toLocation = pCurrNode.edgeList.get(i).getToLocation();
				if(!visited.contains(toLocation)) {
					visited.add(toLocation);
				}
			}
		}
				
	}
	
	//New support method for week3
	
	/**Method:setParentMap()
	 * Add current node key to the ParentMap with hashmap to a list of it's neighbor Nodes not on visited list
	 * 
	 * @param pCurrNode The current Node
	 */
	private void setParentMap(Node pCurrNode) {
			
		if(pCurrNode.getEdgeList() != null) {
			for (int i = 0; i < pCurrNode.edgeList.size(); i++) {
				Node toLocation = pCurrNode.edgeList.get(i).getToLocation();
				if(!visited.contains(toLocation)) {
					if(parentMap.containsKey(pCurrNode)) {
						List<Node> mapchildList = parentMap.get(pCurrNode);
						mapchildList.add(toLocation);
						parentMap.put(pCurrNode, mapchildList);
					}
					else {
						List<Node> mapchildList = new ArrayList<Node>();
						mapchildList.add(toLocation);
						parentMap.put(pCurrNode, mapchildList);
					}
				}
							
			}
		}
	}
	
	//New support method for week3
	
	/**Method:setParentList()
	 * Determine parentList of geographic points on the shortest path from start to goal
	 * 
	 * @param pStartNode The start Node
	 * @param pGoalNode The Goal Node
	 */
	private void setParentList(Node pStartNode, Node pGoalNode) {
		
		//Declare and Initialize variables
		Node parent = pStartNode;
		childList = new ArrayList<Node>();
		List<GeographicPoint> row0List = new ArrayList<GeographicPoint>();
		tempPathList = new HashMap<Node, List<GeographicPoint>>();
		
		//Add the first row of the tempPathList to be a List of size one equal to the parent node
		//int counter = 0; // used for debugging while loop only
		row0List.add(parent.getNode());
		tempPathList.put(parent,row0List);		
		
		//Add children to the childList of the parent
		for (Node n : parentMap.get(parent)) {
			childList.add(n);
		}
		
		//check if startNode equals GoalNode, then parentList has only one entry startNode = goal, zero hop path. Else continue method
		if (pStartNode.equals(pGoalNode)) {
			parentList.add(pStartNode.getNode());
		}
		else {
			// new helper method updatePathList() Creates and updates an Array List of paths from start to goal
			updatePathList(parent, pGoalNode);
			
			while(!parent.equals(pGoalNode)) {
			//while(counter < 9) {
				
				while(childList.size() > 0) {
								
					// new helper method getNewParent() gets the next parent from the childList of Nodes
					parent = getNewParent();
					updatePathList(parent, pGoalNode);
							
				} // end while childList not empty
				
				//Add children to the childList of the parent, exclude child nodes with no children (self parent loop)
				getParentNodes(pGoalNode);
				
				// new helper method reachedGoal() checks if goal node has been reached in the tempPathList, assigns parentList
				if (reachedGoal(pGoalNode) == true) {
					parent = pGoalNode;
					break;
				}
				//counter++;
			} // end while parent not equal goal node		
			
		} // end else if start equal goal
		
	
	} // end method
	
	//New support method for week3
	
	/** Return a list of nodes representing the last node on each of the path lists
	 * @return an object of class Node
	 */
	public void getParentNodes(Node pGoalNode) {
		
		childList.clear();
		if(tempPathList.containsKey(pGoalNode)) {
			childList.add(pGoalNode);
		}		
		else {
			for(Node newParentNode : tempPathList.keySet()) {				
				if(parentMap.get(newParentNode) != null && !parentMap.get(newParentNode).isEmpty() || newParentNode.equals(pGoalNode)) {
					childList.add(newParentNode);
				}
			}
		} // end else
						
	}
	
	//New support method for week3
	
	/** Return a new parent from childList of Nodes
	 * @return an object of class Node
	 */
	public Node getNewParent() {
		Node newParent = childList.get(0);
		childList.remove(0);
	
		return newParent;
			
	}
		
	//New support method for week3
		
	/** Updates tempPathList array with new child onto existing path list containing parent node, creating new path lists for each child edge
	* @param A Class Node object representing the parent
	*/
	public void updatePathList(Node parent, Node goal) {
		
		// Initialize variables
		List<Node> childMap = new ArrayList<Node>();
		Node child = null;
		int cmSize = 0;
		
		if(parentMap.get(parent) != null) {
			for(Node checkNode: parentMap.get(parent)) {
				if(parentMap.get(checkNode) != null || checkNode.equals(goal)) {
					childMap.add(checkNode);
				}
			}
		}
		cmSize = childMap.size();
		
		// find parent node key in the HashMap of pathlists
		List<GeographicPoint> parentRow = new ArrayList<GeographicPoint>();
		parentRow = tempPathList.get(parent);
		
		if(!childMap.isEmpty()) {
			for (int cm=0; cm < cmSize; cm++) {
				List<GeographicPoint> newRow = new ArrayList<GeographicPoint>();
				child = childMap.get(0);
				childMap.remove(0);
				newRow.addAll(parentRow);
				newRow.add(child.getNode());
				tempPathList.put(child, newRow);
			} // end for child map loop
			tempPathList.remove(parent);
		}
				
	} // end method
	
	//New support method for week3
	
	/** Checks all paths to see if goal has been reached; updates static variable parentList = the return variable for the bfs method
	* @param A Class Node object representing the goal node
	* @return true if the goal was found
	*/
	public boolean reachedGoal(Node goal) {
		
		boolean reachedGoal = false;
		// test print to console
		//System.out.println(printTempPathList());
		
		reachedGoal = tempPathList.containsKey(goal);
		if(reachedGoal) {
			parentList = tempPathList.get(goal);
			return reachedGoal;
		}
		
		return reachedGoal;
		
	}
	
	//New support method for week3
	
	/** Return a String representation of the tempPathList
	 * @return A string representation of the tempPathList
	 */
	public String printTempPathList() {
		String s = "\ntempPathList\n";
		for (Node n : tempPathList.keySet()) {
			if(vertexLabels != null) {
				s += "[" + listString(tempPathList.get(n)) + "] ";			
			}
			else {
			s += tempPathList.get(n).toString();
			}
		}
			return s;
	}
	
	
	//New support method for week3
	
	/** Return a String representation of the graph
	 * @return A string representation of the graph
	 */
	public String toString() {
		String s = "\nGraph with " + numVertices + " vertices and " + numEdges + " edges.\n";
		//s += "Degree sequence: " + degreeSequence() + ".\n";
		if (vertexLabels != null) {
			if (numVertices <= 20) s += adjacencyVertexLabels();
		}
		else {
			if (numVertices <= 20) s += adjacencyString();
		}
		return s;
	}
	
	//New support method for week3
	
	/**
	 * Generate string representation of adjacency list, using Geographic Points
	 * @return the String
	 */
	private String adjacencyString() {
		String s = "Adjacency list";
		s += " (size " + getNumVertices() + "+" + getNumEdges() + "):";

		for (GeographicPoint v : adjListsMap.keySet()) {
			s += "\n\t"+v+": ";
			for (Edge edge : adjListsMap.get(v).edgeList) { 
				String w = edge.getToLocation().getNode().toString();
				s += "["+w+"], ";
			}
		}
		return s;
	}
	
	//New support method for week3
	
		/**
		 * Generate string representation of adjacency list, using Vertex Labels
		 * @return the String
		 */
		private String adjacencyVertexLabels() {
			String s = "Adjacency list";
			s += " (size " + getNumVertices() + "+" + getNumEdges() + "):";
			for (GeographicPoint v : adjListsMap.keySet()) {
				String v1 = vertexLabels.get(v);
				s += "\n\t"+v1+": ";
				for (Edge edge : adjListsMap.get(v).edgeList) { 
					String w = vertexLabels.get(edge.getToLocation().getNode());
					s += "["+w+"], ";
				}
			}
			return s;
		}
		
	//New support method for week3
	
	/**
	 * Generate string representation of testRoute
	 * 
	 * @param testRoute the List of GeographicPoints to generate the string
	 * @return the String
	 */
	private static String listString(List<GeographicPoint> testRoute) {
		String s = "";
		if(!parentList.isEmpty()) {
			s = "\n\tShortest Path Output";
			s += "\n\t"+"From start to goal: ";
		}
		String w = "";
		for (GeographicPoint pt : testRoute) {
			if(vertexLabels != null) {
				w = vertexLabels.get(pt);
			}
			else {
				w = pt.toString();
			}
			s += "[" + w + "] ";
		}
		return s;
	}
	
	//New support method for week3
		
	/**
	 * Generate string representation of parentMap
	 * @return the String
	 */
	private String parentMapPrint() {
		String s = "parentMap Coordinates Parent: Child";
		String w = "null";
		for (Node parent : parentMap.keySet()) {
			s += "\n\t["+parent.getNode().toString()+"]: ";
			if (parentMap.get(parent) != null) {
				for (Node child : parentMap.get(parent)) {
					w = child.getNode().toString();
					s += "["+w+"], ";
				}
			}
		}
		return s;
	}
	
	//New support method for week3
	
	/**
	 * Set vertexLabels
	 * Alphabetical progression
	 */
	private static void setVertexLabels() {
		
		//String[] alphaList = {"A","B"};
		List<String> alphaList = Arrays.asList("A","B","D","F","G","H","C","E","I");
		int i = 0;
		
		for (GeographicPoint v : adjListsMap.keySet()) {
			vertexLabels.put(v,alphaList.get(i));
			i++;
		}
		
	}
		
	//New support method for week3
	
	/**
	 * Generate string representation of queue, current Node and visited lists
	 * @return the String
	 */
	private String queueandvisitedPrint(Node pCurrNode) {
		String s ="";
		for (Node q: queue) {
			s += "queue: ["+q.getNode().toString()+"]: ";
		}
		s += "\n\t current Node ["+pCurrNode.getNode().toString()+"]: ";
		for (Node v: visited) {
			s += "visited: ["+v.getNode().toString()+"]: ";
		}
		
		return s;
	}

	/** Find the path from start to goal using Dijkstra's algorithm
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> dijkstra(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
		// You do not need to change this method.
        Consumer<GeographicPoint> temp = (x) -> {};
        return dijkstra(start, goal, temp);
	}
	
	/** Find the path from start to goal using Dijkstra's algorithm
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> dijkstra(GeographicPoint start, 
										  GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		// TODO: Implement this method in WEEK 4

		// Hook for visualization.  See writeup.
		//nodeSearched.accept(next.getLocation());
		
		return null;
	}

	/** Find the path from start to goal using A-Star search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> aStarSearch(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
        Consumer<GeographicPoint> temp = (x) -> {};
        return aStarSearch(start, goal, temp);
	}
	
	/** Find the path from start to goal using A-Star search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> aStarSearch(GeographicPoint start, 
											 GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		// TODO: Implement this method in WEEK 4
		
		// Hook for visualization.  See writeup.
		//nodeSearched.accept(next.getLocation());
		
		return null;
	}

	
	
	public static void main(String[] args)
	{
		System.out.print("Making a new map...");
		MapGraph firstMap = new MapGraph();
		System.out.print("DONE. \nLoading the map...");
		GraphLoader.loadRoadMap("data/testdata/simpletest.map", firstMap);
		setVertexLabels();
		System.out.print(firstMap);
		System.out.println("\nDONE.");
		
		// You can use this method for testing BFS for week 3  
		
		MapGraph simpleTestMap = new MapGraph();
		GraphLoader.loadRoadMap("data/testdata/simpletest.map", simpleTestMap);
		setVertexLabels();
		GeographicPoint testStart = new GeographicPoint(1.0, 1.0);
		GeographicPoint testEnd = new GeographicPoint(8.0, -1.0);

		System.out.println("BFS Shortest Path using simpletest.map");
		List<GeographicPoint> testroute = simpleTestMap.bfs(testStart,testEnd);
		if(testroute == null) {
			System.out.println("Goal not found at any node visited");
		}
		else {
			System.out.println(listString(testroute));
		}
		
		/* Here are some test cases you should try before you attempt 
		 * the Week 3 End of Week Quiz, EVEN IF you score 100% on the 
		 * programming assignment.
		 */
		/*
		MapGraph simpleTestMap = new MapGraph();
		GraphLoader.loadRoadMap("data/testdata/simpletest.map", simpleTestMap);
		
		GeographicPoint testStart = new GeographicPoint(1.0, 1.0);
		GeographicPoint testEnd = new GeographicPoint(8.0, -1.0);
		
		System.out.println("Test 1 using simpletest: Dijkstra should be 9 and AStar should be 5");
		List<GeographicPoint> testroute = simpleTestMap.dijkstra(testStart,testEnd);
		List<GeographicPoint> testroute2 = simpleTestMap.aStarSearch(testStart,testEnd);
		
		
		MapGraph testMap = new MapGraph();
		GraphLoader.loadRoadMap("data/maps/utc.map", testMap);
		
		// A very simple test using real data
		testStart = new GeographicPoint(32.869423, -117.220917);
		testEnd = new GeographicPoint(32.869255, -117.216927);
		System.out.println("Test 2 using utc: Dijkstra should be 13 and AStar should be 5");
		testroute = testMap.dijkstra(testStart,testEnd);
		testroute2 = testMap.aStarSearch(testStart,testEnd);
		
		
		// A slightly more complex test using real data
		testStart = new GeographicPoint(32.8674388, -117.2190213);
		testEnd = new GeographicPoint(32.8697828, -117.2244506);
		System.out.println("Test 3 using utc: Dijkstra should be 37 and AStar should be 10");
		testroute = testMap.dijkstra(testStart,testEnd);
		testroute2 = testMap.aStarSearch(testStart,testEnd);
		*/
		
		
		/* Use this code in Week 3 End of Week Quiz */
		/*MapGraph theMap = new MapGraph();
		System.out.print("DONE. \nLoading the map...");
		GraphLoader.loadRoadMap("data/maps/utc.map", theMap);
		System.out.println("DONE.");

		GeographicPoint start = new GeographicPoint(32.8648772, -117.2254046);
		GeographicPoint end = new GeographicPoint(32.8660691, -117.217393);
		
		
		List<GeographicPoint> route = theMap.dijkstra(start,end);
		List<GeographicPoint> route2 = theMap.aStarSearch(start,end);

		*/
		
	}
	
}
