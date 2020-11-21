import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class WGraph_AlgoTest2 {
    @Test
    @DisplayName("DeepCopy testing")
    public void DeepCopyTest(){
        WGraph_DS graph = new WGraph_DS();
        //Adding 6 nodes into the graph and 2 edges
        graph.addNode(1);
        graph.addNode(2);
        graph.addNode(3);
        graph.addNode(4);
        graph.addNode(5);
        graph.addNode(6);
        graph.connect(1,2,1);
        graph.connect(1,3,2);
        graph.connect(1,4,3);
        weighted_graph_algorithms graph2 = new WGraph_Algo();
        graph2.init(graph);
        weighted_graph NewGraph = graph2.copy();
        Assertions.assertEquals(NewGraph.nodeSize(),graph.nodeSize());
        Assertions.assertEquals(NewGraph.getEdge(1,4), graph.getEdge(4,1));

    }
    @Test
    @DisplayName("isConnected algo test")
    public void isConnectedTest(){
        WGraph_DS graph = new WGraph_DS();
        graph.addNode(1);
        graph.addNode(2);
        graph.addNode(3);
        graph.addNode(4);
        graph.connect(1,2,1);
        graph.connect(2,3,1);
        graph.connect(3,4,1);
        weighted_graph_algorithms graph2 = new WGraph_Algo();
        graph2.init(graph);
        Assertions.assertTrue(graph2.isConnected());

    }

    @Test
    @DisplayName("Checking the ShortestPath dist algo")
    public void shortestPathDist(){
        WGraph_DS graph = new WGraph_DS();
        graph.addNode(0);
        graph.addNode(1);
        graph.addNode(2);
        graph.addNode(3);
        graph.addNode(4);
        graph.connect(0,1,2);
        graph.connect(1,2,1);
        graph.connect(1,4,6);
        graph.connect(2,3,2);
        graph.connect(3,4,1);
        weighted_graph_algorithms graph2 = new WGraph_Algo();
        graph2.init(graph);
        Assertions.assertEquals(graph2.shortestPathDist(0,4),6);
    }

    @Test
    @DisplayName("Checking the shortestPath")
    public void shortestPath(){
        WGraph_DS graph = new WGraph_DS();
        graph.addNode(0);
        graph.addNode(1);
        graph.addNode(2);
        graph.addNode(3);
        graph.addNode(4);
        graph.connect(0,1,2);
        graph.connect(1,2,1);
        graph.connect(1,4,6);
        graph.connect(2,3,2);
        graph.connect(3,4,1);
        weighted_graph_algorithms graph2 = new WGraph_Algo();
        graph2.init(graph);
        //5 Nodes in the path from 0-4
        Assert.assertEquals(graph2.shortestPath(0,4).size(), 5);
    }

    @Test
    @DisplayName("Check Save and Load functions")
    public void saveLoad(){
        WGraph_DS graph = new WGraph_DS();
        for(int i=0;i<1000;++i){
            graph.addNode(i);
        }
        Random r = new Random(1);
        for(int i=0;i<100;i++){
            graph.connect(i, i+100,r.nextDouble()*1000);
        }

        WGraph_Algo algo = new WGraph_Algo();
        algo.init(graph);
        algo.save("TestSave.json");
        WGraph_Algo algo2 = new WGraph_Algo();
        algo2.load("TestSave.json");
        Assert.assertEquals(algo2.getGraph().edgeSize(), graph.edgeSize());
        Assert.assertEquals(algo2.getGraph().nodeSize(), graph.nodeSize());
    }


    @Test
    @DisplayName("Huge graph test")
    public void bigGraph(){
        WGraph_DS graph = new WGraph_DS();
        for(int i=0;i<1000000;++i){
            graph.addNode(i);
        }
        Random r = new Random(1);
        for(int i=0;i<100000;i++){
            graph.connect(i, i+1,r.nextDouble()*1000);
        }
        WGraph_Algo algo = new WGraph_Algo();
        algo.init(graph);
        algo.save("TestSave.json");
        WGraph_Algo algo2 = new WGraph_Algo();
        algo2.load("TestSave.json");
        Assert.assertEquals(algo2.getGraph().edgeSize(), graph.edgeSize());
        Assert.assertEquals(algo2.getGraph().nodeSize(), graph.nodeSize());
    }
}
