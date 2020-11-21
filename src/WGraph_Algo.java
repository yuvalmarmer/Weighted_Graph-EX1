import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

/**
 * WGraph_Algo implements weighted_graph_algorithms.
 * WGraph_Algo is class with algorithms that can be done on the graph.
 */
public class WGraph_Algo implements weighted_graph_algorithms {


    private weighted_graph graph;

    /**
     * Default constructor that create clean WGraph_Algo
     */
    public WGraph_Algo(){}
    /**
     * Init the WGraph_Algo with graph.
     * @param g - the graph that WGraph_Algo will work on.
     */
    @Override
    public void init(weighted_graph g) {
        this.graph = g;
    }
    /**
     * Returns the graph that initialized into WGraph_Algo
     * @return the weighted_graph
     */
    @Override
    public weighted_graph getGraph() {
        return this.graph;
    }
    /**
     * Copy method doing a deep-copy of the graph,
     * Creates new WGraph_DS and adding all the vertices into the new graph for the current graph.
     * Connecting all nodes with connect function.
     * and return a new graph that is deep copy from the original graph.
     * @return new WGraph_DS that cloned
     */
    @Override
    public weighted_graph copy() {
        weighted_graph graph_copy = new WGraph_DS();
        for(node_info node : this.graph.getV()){
            graph_copy.addNode(node.getKey());
            graph_copy.getNode(node.getKey()).setTag(node.getTag());
        }
        for(node_info key_node : this.graph.getV()){
            if(graph_copy.edgeSize()==graph.edgeSize())break;
            for(node_info key_node2 : this.graph.getV(key_node.getKey())){
                if(this.graph.getEdge(key_node.getKey(),key_node2.getKey())>-1) {
                    graph_copy.connect(key_node.getKey(), key_node2.getKey(), this.graph.getEdge(key_node.getKey(),key_node2.getKey()));
                }
            }
        }
        return graph_copy;
    }
    /**
     * Checks if all nodes in the graph are connected.
     * Creates new MAP of visited nodes and empty stack.
     * First we add the first node in the graph into the stack.
     * After first node in the stack we will pop out node from the stack and mark him as visited with HashMap,
     * and adding all vertices that connected to him into the stack, and if not mark as visited so mark it to visited.
     * Doing that for all nodes.
     * After finish checking each node and mark them and their neighbors.
     * If the size of keys in the map equals to the size of nodes in the graph then we visited all nodes in the graph and it means that he is connected.
     * Else he isnt connected
     * If isnt connected return false, else true.
     * @return true - if all nodes are connected.
     */
    @Override
    public boolean isConnected() {
        HashMap<node_info, Boolean> visited = new HashMap<>();
        Stack<node_info> stack = new Stack<>();
        for(node_info node : this.graph.getV()){
            stack.push(node);
            break;
        }
        while(!stack.isEmpty()){
            node_info temp = stack.pop();
            visited.put(temp,true);
            for(node_info node2 : this.graph.getV(temp.getKey())){
                if(visited.get(node2) == null) {
                    stack.push(node2);
                    visited.put(node2, true);
                }
            }
        }


        return (this.graph.nodeSize() == visited.size());
    }
    /**
     * Return the shortest path from the shortestPath algorithm
     * and returns the tag for the last node that represents the weighted path,
     * that represent the distance.
     * if there isnt path return -1;
     * @return int - the weight of the shortest path. else -1.
     */
    @Override
    public double shortestPathDist(int src, int dest) {
        List<node_info> path = shortestPath(src,dest);
        if(path.size()==0) return -1;
        return path.get(path.size()-1).getTag();
    }
    /**
     * Return list of node_info the represents the shorts path from src to dest (using Dijkstra algorithm).
     * Implements of the algorithm Dijkstra is using 2 HashMaps and PriorityQueue, one for storing the nodes that already visited
     * and the second map represents the parents node (Key = the node, Value = the parent of node).
     * And the priorityQueue will contain the nodes and prioritise them by their tag.
     * The algorithm starts with adding src node to the queue and set his tag to 0 (Tag will represents the sum weights until to this node from src).
     * While the Queue isnt empty and we didnt find the dest node the algorithm will poll the first node from the queue mark him as visited
     * and add his tag(Sum of weights until him) to all vertices that he connected with + the weight of the edge between them and put it as there tag.
     * Adding them to the priority queue and it willprioritizee the queue by the minimum tag of the node.
     * If we find the src node we exit the while loop and starting backtracking the path from dest to src with the parents map.
     * And return the path
     * @param src - start node
     * @param dest - end (target) node
     * @return List - List of nodes that are the road from src to dest.
     */
    @Override
    public List<node_info> shortestPath(int src, int dest) {
        LinkedList<node_info> path = new LinkedList<>();
        PriorityQueue<node_info> queue = new PriorityQueue<>();
        HashMap<node_info, Boolean> visited = new HashMap<>();
        HashMap<node_info, node_info> parents = new HashMap<>();
        boolean flag = false;
        //Adding src node into the queue and set his priority to 0
        this.graph.getNode(src).setTag(0);
        queue.add(this.graph.getNode(src));
        while(!queue.isEmpty() && !flag){
            node_info curr = queue.poll();
            if(!visited.containsKey(curr)){
                visited.put(curr, true);
                if(curr.getKey()==dest) {
                    flag=true;
                }
                else {
                    for (node_info neighbor : this.graph.getV(curr.getKey())) {
                        if (!visited.containsKey(neighbor)) {
                            if(neighbor == null){
                                System.out.println();
                            }
                            double w = this.graph.getEdge(curr.getKey(), neighbor.getKey());
                            neighbor.setTag(w + curr.getTag());
                            parents.put(neighbor, curr);
                            queue.add(neighbor);
                        }
                    }
                }
            }
        }
        if(flag){
            node_info node = this.graph.getNode(dest);
            while(node.getKey() != src){
                path.addFirst(node);
                node = parents.get(node);
            }
            path.addFirst(node);
        }
        return path;

    }

    /**
     * Using Gson to dump the WGraph_DS into a json format and save it in the file.
     * Dump the hashmap of vertices as "Vertices" key in json.
     * Dump the hashmap of edges as "Edges" key in json.
     * And write the json string into file.
     * @param file - the file name (may include a relative path).
     * @return true if saved successful
     */
    @Override
    public boolean save(String file) {
        Gson gson = new Gson();
        //Save the vertices
        JsonArray vertices = gson.toJsonTree(((WGraph_DS) this.graph).getV()).getAsJsonArray();
        //Save the edges
        JsonObject edges_string = gson.toJsonTree(((WGraph_DS) this.graph).getEdges()).getAsJsonObject();
        JsonObject json = new JsonObject();
        //Put them in jsonObject
        json.add("Vertices", vertices);
        json.add("Edges", edges_string);
        try {
            PrintWriter writer = new PrintWriter(new File(file));
            writer.write(gson.toJson(json));
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;


    }
    /**
     * Using Gson to read the json string from file.
     * Reading the json string from the giving path to the file.
     * Getting the "vertices" value from the json object and adding all vertices into the new WGraph_DS.
     * Gettung all edges form "Edges" key and connect all nodes by the values in the json.
     * @param file - the file name (may include a relative path).
     * @return true if loaded successful
     */
    @Override
    public boolean load(String file) {
        try{
            this.graph = new WGraph_DS();
            Gson gson = new Gson();
            //Type for JsonObject
            Type JsonObjectType = new TypeToken<JsonObject>() {}.getType();
            //Parse json string into object
            JsonReader reader = new JsonReader(new FileReader(file));
            JsonObject graph_json = gson.fromJson(reader, JsonObjectType);
            JsonArray vertices = graph_json.get("Vertices").getAsJsonArray();
            JsonObject edges = graph_json.get("Edges").getAsJsonObject();
            //Adding all vertices and add them to graph
            for(JsonElement ver  : vertices){
                JsonObject verObj = ver.getAsJsonObject();
                this.graph.addNode(verObj.get("key").getAsInt());
                this.graph.getNode(verObj.get("key").getAsInt()).setTag(verObj.get("tag").getAsDouble());
            }
            //Connect all edges
            Set<Map.Entry<String, JsonElement>> edgesSet = edges.entrySet();
            for(Map.Entry<String,JsonElement> edge : edgesSet){
                Set<Map.Entry<String, JsonElement>> neighborSet = edge.getValue().getAsJsonObject().entrySet();
                for(Map.Entry<String,JsonElement> neighbor : neighborSet){
                    this.graph.connect(Integer.parseInt(edge.getKey()),Integer.parseInt(neighbor.getKey()), neighbor.getValue().getAsDouble());
                }
            }
        }
        catch (Exception e){
            return false;
        }
        return true;
    }


}
