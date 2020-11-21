# Weighted Graph - Ex1
This project contains weighted graph data structure and implementation of several algorithms on weighted undirected graph.
## WGraph_DS
Weighted graph DS contains several methods such as adding, removing nodes to/from the graph.<br/>
The class also have a connect method that create edges between two nodes with given wight,and remove edge method that remove the edge between two nodes.<br/>
WGraph DS have a private class that represents nodes in the graph named "NodeInfo" that has its own simple DS.
Each node in the graph has its uniqe key and tag. The tag used for helping the graph algorithms.
## WGraph_Algo
This class implements several algorithms on weighted undirected graph such as finding the shortest path from one node to another, 
get the shortest distance between two nodes.checking if the graph connected (means that all nodes are reachable from each node).
Class contains deepcopy methods that create a deep-copied graph.  
### ShortestPath - Dijkstra
The algorithm that used for finding the shortest path between two nodes is Dijkstra.
The algorithm using priority queue for prioritize the nodes and giving the shortestPath.<br/>
references used for the implementation and for more information : https://www.coursera.org/lecture/advanced-data-structures/core-dijkstras-algorithm-2ctyF
### Save / Load
Save method uses Gson library for saving the graph in json format to file.<br/>
Json Format
``` json
{
    "Vertices" : [{
        "key" : tag
    }],
    "Edges" :  {
        "key_node1" : {
            "key_node2" : weight
        }
     }
}
```
The load function also using Gson for parsing the json file and building a weighted graph.

## Tests class
The 'tests' directory contains testes for all public methods that in both of the classes.
Uses Junit 5 for testes functions. You can look up for examples there.

## Requirements
* Java JDK 15 installed
* Junit 5 library
* Gson library (I used gson.2.8.6 can be download from Maven "com.google.code.gson:gson:2.8.6")

## Example
Simple use of this WGraph_DS and WGraph_Algo  
``` java
        //Creating new graph
        WGraph_DS graph = new WGraph_DS();
        //Adding two nodes into the graph
        graph.addNode(0);
        graph.addNode(1);
        //Connect the with weight edges size 10
        graph.connect(0,1,10);
        //Creating new GraphAlog
        weighted_graph_algorithms graph_algo = new WGraph_Algo();
        //Initialize the graph into graph_algo
        graph_algo.init(graph); 
        //Check if graph connected
        System.out.println(graph_algo.isConnected());
```
