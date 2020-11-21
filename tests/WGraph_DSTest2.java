import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class WGraph_DSTest2 {

    @Test
    @DisplayName("Create weighted graph with 10 nodes and 0 edges")
    public void creatGraph() {
        WGraph_DS graph = new WGraph_DS();
        for(int i=0;i<10;++i){
            graph.addNode(i);
        }
        Assert.assertEquals(graph.nodeSize(), 10);
        Assert.assertEquals(graph.edgeSize(), 0);
    }

    //Create weighted graph with 10 nodes and 10 edges with Random edges
    @Test
    @DisplayName("Graph with edges")
    public void graphWithEdges() {
        WGraph_DS graph = new WGraph_DS();
        for(int i=0;i<10;++i){
            graph.addNode(i);
        }
        Random rand = new Random(1);

        for (int i = 0; i < 9; i++) {
            double w = rand.nextDouble()*10;
            System.out.println("Connect " + i + " with " + (i+1) + ", weight " +  w);
            graph.connect(i,i+1,w);
        }
        double w = rand.nextDouble()*10;
        System.out.println("Connect 9 with 0, weight " +  w);
        graph.connect(9,0,w);

        Assert.assertEquals(graph.nodeSize(), 10);
        Assert.assertEquals(graph.edgeSize(), 10);
    }

    //Create graph with 10 vertices and 10 edges and remove 3 of them
    @Test
    @DisplayName("Remove edges")
    public void removeEdge() {
        WGraph_DS graph = new WGraph_DS();
        for(int i=0;i<10;++i){
            graph.addNode(i);
        }
        Random rand = new Random(1);

        for (int i = 0; i < 9; i++) {
            double w = rand.nextDouble()*10;
            System.out.println("Connect " + i + " with " + (i+1) + ", weight " +  w);
            graph.connect(i,i+1,w);
        }
        double w = rand.nextDouble()*10;
        System.out.println("Connect 9 with 0, weight " +  w);
        graph.connect(9,0,w);

        graph.removeEdge(1,2);
        graph.removeEdge(1,2);
        graph.removeEdge(2,3);
        graph.removeEdge(5,6);
        Assert.assertEquals(graph.nodeSize(), 10);
        Assert.assertEquals(graph.edgeSize(), 7);
    }

    //Create graph with 10 vertices with 5 edges to node '1' and delete it.
    @Test
    @DisplayName("Remove nodes")
    public void removeNode() {
        WGraph_DS graph = new WGraph_DS();
        for(int i=0;i<10;++i){
            graph.addNode(i);
        }
        Random rand = new Random(1);

        for (int i = 0; i < 5; i++) {
            double w = rand.nextDouble()*10;
            System.out.println("Connect 0 with " + (i+1) + ", weight " +  w);
            graph.connect(0,i+1,w);
        }
        node_info node = graph.removeNode(0);
        graph.removeNode(0);
        Assert.assertEquals(graph.nodeSize(), 9);
        Assert.assertEquals(graph.edgeSize(), 0);
        Assert.assertEquals(node.getKey(), 0);
    }

    @Test
    @DisplayName("Edge sized check")
    public void edgeSize(){
        WGraph_DS graph = new WGraph_DS();
        for(int i=0;i<100;++i){
            graph.addNode(i);
        }
        Random rand = new Random(1);

        for (int i = 0; i < 50; i++) {
            double w = rand.nextDouble()*10;
            System.out.println("Connect 0 with " + (i+1) + ", weight " +  w);
            graph.connect(0,i+1,w);
        }

        Assert.assertEquals(graph.edgeSize(), 50);
    }

    @Test
    @DisplayName("Connect nodes test")
    public void ConnectivityTest(){
        WGraph_DS graph = new WGraph_DS();
        graph.addNode(1);
        graph.addNode(2);
        graph.addNode(3);
        graph.addNode(4);
        graph.connect(1,2,6);
        graph.connect(1,4,5);
        Assertions.assertEquals(graph.getEdge(1,2),6);
        Assertions.assertEquals(graph.getEdge(2,1),6);
        Assertions.assertEquals(graph.getEdge(1,4),5);
        Assertions.assertEquals(graph.getEdge(4,1),5);
        Assertions.assertTrue(graph.hasEdge(1, 2));
        Assertions.assertTrue(graph.hasEdge(2, 1));
        Assertions.assertTrue(graph.hasEdge(1, 4));
        Assertions.assertTrue(graph.hasEdge(4, 1));
    }
}
