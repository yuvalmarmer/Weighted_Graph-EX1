import java.util.*;

/**
 * Graph_DS class implements weighted graph interface .
 * Graph_DS represents a data structure of undirected weighted graph with nodes,and edges.
 */
public class WGraph_DS implements weighted_graph{
    /**
     * Return the hashmap of vertices.
     * @return Hashmap that represents the vertices in the graph.
     */
    public HashMap<Integer, node_info> getVertices() {
        return vertices;
    }

    private HashMap<Integer, node_info> vertices;
    private HashMap<Integer, HashMap<node_info, Double>> edges;
    private int edge_count;
    private int mc;

    public HashMap<Integer, HashMap<node_info, Double>> getEdges() {
        return edges;
    }

    /**
     * Default constructor that create clean weighted graph.
     */
    public WGraph_DS(){
        this.vertices = new HashMap<>();
        this.edges = new HashMap<>();
        this.edge_count = 0;
        this.mc = 0;
    }

    /**
     * Returns the node with given key from Hashmap that represents the vertices.
     * if the node dosent exists return null.
     * @param key - the node key
     * @return Node - node_info object .
     */
    @Override
    public node_info getNode(int key) {
        if(this.vertices.containsKey(key))
            return this.vertices.get(key);
        return null;
    }
    /**
     * Checks if exist edge between node1 and node2.
     * Check if edges Hashmap contains the key of node1 and value of node2.
     * @param node1  key for node1
     * @param node2  key for node2
     * @return boolean - true if exist, else false.
     */

    @Override
    public boolean hasEdge(int node1, int node2) {
        //Node1 exists and have edge to node2
        if(this.edges.containsKey(node1) && this.edges.get(node1).containsKey(getNode(node2))) return true;
        return false;
    }

    /**
     * Returns the weight of the edge between node1 and node2.
     * Check if this edge exists and return the weight from the hashmap
     * @param node1 - key for node1
     * @param node2 - key for node2
     * @return double - the weight of the edge .
     */
    @Override
    public double getEdge(int node1, int node2) {
        if(hasEdge(node1, node2)){
            return this.edges.get(node1).get(this.getNode(node2));
        }
        return -1;
    }
    /**
     * Add new node into the graph vertices hashmap.
     * and creates new NodeInfo object to the new node.
     * @param key - key for new node
     */
    @Override
    public void addNode(int key) {
        this.vertices.put(key, new NodeInfo(key, 0));
        ++this.mc;
    }
    /**
     * Connect two nodes with edge with given weight.
     * Only if the two nodes exists in the graph than map them in the edges hashmap
     * and weighing them with given weight.
     * @param node1 - key for node1
     * @param node2 - key for node2
     * @param w - weight for the edge
     */
    @Override
    public void connect(int node1, int node2, double w) {
        if(this.getNode(node1) != null && this.getNode(node2) != null && !this.hasEdge(node1, node2)) {
            if (node1 == node2) return;
            if (this.edges.get(node1) == null) {
                this.edges.put(node1, new HashMap<>());
            }
            if (this.edges.get(node2) == null) {
                this.edges.put(node2, new HashMap<>());
            }
            //Connect node1 to node2
            this.edges.get(node1).put(this.getNode(node2), w);

            //Connect node2 to node1
            this.edges.get(node2).put(this.getNode(node1), w);

            ++this.edge_count;
            ++this.mc;
        }
    }
    /**
     * Returns a collection of node_info that represent the vertices of the graph.
     * The collection is the values of the vertices hashmap
     * @return Collection - Collection of all vertices.
     */
    @Override
    public Collection<node_info> getV() {
        return this.vertices.values();
    }

    @Override
    public Collection<node_info> getV(int node_id) {
        if(this.edges.containsKey(node_id)) {
            return this.edges.get(node_id).keySet();
        }
        return Collections.emptyList();
    }
    /**
     * Returns the given node from the graph after removing it.
     * Removing all the edges that connected to the given node and delete the edge between them,
     * By remove the edges from the edges hashmap.
     * and in the end delete the node from the vertices hashmap.
     * If node desnt exist return null.
     * @param key - key for node
     * @return node_info - The node that was deleted.
     */
    @Override
    public node_info removeNode(int key) {
        node_info main_node = null;
        if(this.vertices.containsKey(key)){
            Collection<node_info> neighbors = getV(key);
            ArrayList<Integer> keys = new ArrayList<>();
            for (node_info node : neighbors) {
                keys.add(node.getKey());
            }

            for(int nodeKey : keys){
                removeEdge(nodeKey, key);
            }
            main_node = this.vertices.get(key);
            this.vertices.remove(key);
        }
        ++this.mc;
        return main_node;
    }

    /**
     * Remove edge from node1 to node2.
     * Delete from edges hashmap the maping from node1 to node2 and delete the weight and in the opposite direction.
     * @param node1 - id for node1.
     * @param node2 - id for node2.
     */
    @Override
    public void removeEdge(int node1, int node2) {
        if(hasEdge(node1, node2)){
            this.edges.get(node1).remove(getNode(node2));
            this.edges.get(node2).remove(getNode(node1));
            --this.edge_count;
            ++this.mc;
        }
    }
    /**
     * Returns the number of nodes in the graph.
     * the size of vertices values
     * @return number of nodes in the graph.
     */
    @Override
    public int nodeSize() {
        return this.vertices.size();
    }
    /**
     * Returns the number of edges in the graph.
     * returns the edges counter.
     * @return number of edges in the graph.
     */
    @Override
    public int edgeSize() {
        return this.edge_count;
    }
    /**
     * Return the movement counter
     * @return mc - the number of movements in the graph.
     */
    @Override
    public int getMC() {
        return this.mc;
    }


    /**
     * NodeInfo class that represents the nodes in the graph
     */
    private class NodeInfo implements node_info, Comparable<node_info>{
        private int key;
        private double tag;
        /**
         * Default constructor for NodeInfo ,
         * Creates a node with given key and tag.
         */
        public NodeInfo(int key, double tag){
            this.key = key;
            this.tag = tag;
        }
        /**
         * Return the the key of node
         * @return key of the node.
         */
        @Override
        public int getKey() {
            return key;
        }
        /**
         * Return the info of the node
         * @return info string of the node.
         */
        @Override
        public String getInfo() {
            return this.key + "," + this.tag;
        }
        /**
         * Set the info to node_info
         * break the string and set the key and tag from the metadata.
         * @param s MetaData string
         */
        @Override
        public void setInfo(String s) {
            try {
                String metadata[] = s.split(",");
                this.key = Integer.parseInt(metadata[0]);
                this.tag = Double.parseDouble(metadata[1]);
            }
            catch (Exception e){
                System.out.println("Cannot read metadata for the node");
            }
        }
        /**
         * Return the nodes tag
         * @return nodes tag
         */
        @Override
        public double getTag() {
            return tag;
        }
        /**
         * Setting the tag for node
         * @param t tag for the node.
         */
        @Override
        public void setTag(double t) {
            this.tag = t;
        }
        /**
         * Compering two node by there tag.
         * For the priority queue.
         * @param o the node to comparing
         */
        @Override
        public int compareTo(node_info o) {
            if(this.getTag() > o.getTag()) return 1;
            if(this.getTag() < o.getTag()) return -1;
            return 0;
        }
        /**
         * Return the tag of the node
         * @return the key of node in string
         */
        public String toString(){
            return Integer.toString(this.key);
        }
    }


}
