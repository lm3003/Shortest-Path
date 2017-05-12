Class: MapGraph

Modifications made to MapGraph (what and why): 
Added the member variables to calculate the number of vertices and number of Edges within the map. Also added a hashmap to generate the adjacency list for geographic location and MapNodes.
Added Implementation for MapGraph default constructor (giving initail values to all memeber variables ). Implemented methods to return number of vertices, edges (in the entire map). Also, methods to add vertices (location -> MapNodes, hashmap)and corresponding  edges (for particular vertice node). Finally implemented breadth first search using queue datastructure and reconstructed the shortest path from parent HashMap

Class name: MapNodes

Purpose and description of class:
This serves a intersection node for each location (intersection) which is implemented using the adjacency list in MapGraph. It has a list (adjEdges of type MapEdges) used to store all the (incoming and outgoing) edges for a particular intersection.

Class name: MapEdges

Purpose and description of class:
This is used to store all the information regarding an edge to a MapNode. It contains the start point, end point , road name, road type and distance which helps easily in tracking the correct edges for for a particular node 

Overall Design Justification (4-6 sentences):
The overall class design consists of three classes. MapGraph (stores the entire map information), MapNodes (these are nodes correspoinding the each intersection) and MapEdges (used to store the edge information). MapGraph uses adjacency list to maintain information regarding location and its MapNode. MapNode has an arrayList which helps in tracking all the incoming and outgoing edges (MapEdges) for a particular node. MapEdges essentially stores all ther requisite information regarding edges.