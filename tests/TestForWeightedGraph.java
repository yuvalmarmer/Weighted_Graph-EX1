import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

class TestForWeightedGraph {
    WGraph_DS graph = new WGraph_DS();
    double EPS = 0.0001;

    @Test
    public void AddAndRemoveTest(){
        graph.addNode(1);
        graph.addNode(2);
        Assertions.assertEquals(graph.removeNode(2), graph.getNode(2));
    }

    @Test
    public void ConnectivityTest(){
        graph.addNode(1);
        graph.addNode(2);
        graph.connect(1,2,3.55);
        Assertions.assertEquals(graph.getEdge(1,2),3.55);
        Assertions.assertEquals(graph.getEdge(2,1),3.55);
        Assertions.assertTrue(graph.hasEdge(1, 2));
        Assertions.assertTrue(graph.hasEdge(2, 1));
    }

    @Test
    public void DeepCopyTest(){

        graph.addNode(1);
        graph.addNode(2);
        graph.connect(1,2,3.55);
        weighted_graph_algorithms graph2 = new WGraph_Algo();
        graph2.init(graph);
        weighted_graph NewGraph = graph2.copy();
        Assertions.assertEquals(NewGraph.nodeSize(),graph.nodeSize());
        Assertions.assertEquals(NewGraph.getEdge(1,2), graph.getEdge(2,1));

    }

    @Test
    public void isConnectedTest(){
        graph.addNode(1);
        graph.addNode(2);
        graph.connect(1,2,3.55);
        graph.addNode(3);
        graph.connect(1,3,6);
        weighted_graph_algorithms graph2 = new WGraph_Algo();
        graph2.init(graph);
        Assertions.assertTrue(graph2.isConnected());

    }
    @Test
    public void PQcheck() {
        PriorityQueue<node_info> PQ = new PriorityQueue<node_info>();
        graph.addNode(1);
        graph.addNode(2);
        graph.addNode(3);
        graph.getNode(1).setTag(5);
        graph.getNode(2).setTag(10);
        graph.getNode(3).setTag(2);
        PQ.offer(graph.getNode(1));
        PQ.offer(graph.getNode(2));
        PQ.offer(graph.getNode(3));
        Assertions.assertEquals(PQ.poll().getKey(), 3);
        Assertions.assertEquals(PQ.poll().getKey(), 1);
    }
    @Test
    public void Dijkstra(){
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
        System.out.println(graph.getV(2).size());
        weighted_graph_algorithms graph2 = new WGraph_Algo();
        graph2.init(graph);
        LinkedList<node_info> check = (LinkedList<node_info>) graph2.shortestPath(0,4);
        Assertions.assertEquals(graph2.shortestPathDist(0,4),6);



    }
    @Test
    public void Dijkstra2() {
        graph.addNode(0);
        graph.addNode(1);
        graph.addNode(2);
        graph.addNode(3);
        graph.addNode(4);
        graph.addNode(5);
        graph.addNode(6);
        graph.addNode(7);
        graph.connect(0,1,1);
        graph.connect(0,2,2);
        graph.connect(0,3,5);

        graph.connect(1,4,4);
        graph.connect(1,5,11);

        graph.connect(2,4,9);
        graph.connect(2,5,5);
        graph.connect(2,6,16);

        graph.connect(3,6,2);

        graph.connect(4,7,18);

        graph.connect(5,7,13);

        graph.connect(6,7,2);

// =======================================================================================

        weighted_graph_algorithms graph2 = new WGraph_Algo();
        graph2.init(graph);
        LinkedList<node_info> check = (LinkedList<node_info>) graph2.shortestPath(0,7);
        Assertions.assertEquals(check.size(),4);
        Assertions.assertEquals(graph2.shortestPathDist(0,7),9);
        Assertions.assertEquals(check.getLast().getKey(),7);

    }


    @Test
    public void BigGraph()  {
        long start= System.currentTimeMillis();
        for(int i=0;i<100000;i++)
            graph.addNode(i);
        for (int i=0;i<100000;i++){
            double temp = Math.random()*20;
            int first = (int)(Math.random()*100000);
            int second = (int)(Math.random()*100000);
            graph.connect(first,second,temp);
        }

        weighted_graph_algorithms graph2 = new WGraph_Algo();
        graph2.init(graph);
        graph2.save("Save.txt");

        WGraph_DS lol = new WGraph_DS();
        weighted_graph_algorithms lol2 = new WGraph_Algo();
        lol2.init(lol);
        lol2.load("Save.txt");



        long finish =System.currentTimeMillis();
        long timeElapsed = finish - start;
        System.out.println(timeElapsed);

        Assertions.assertEquals(graph.nodeSize(),lol2.getGraph().nodeSize());
        Assertions.assertEquals(graph.edgeSize(),lol2.getGraph().edgeSize());

    }

    @Test
    public void Save(){
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
        graph2.save("Save.json");



    }

    @Test
    public void Load(){
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
        WGraph_DS lol = new WGraph_DS();
        graph2.load("Save.json");

        Assertions.assertEquals(graph.edgeSize(),graph2.getGraph().edgeSize());
        Assertions.assertEquals(graph.nodeSize(),graph2.getGraph().nodeSize());


    }


    @Test
    public void Test10(){ //delete
        WGraph_DS theGraph = new WGraph_DS();
        theGraph.addNode(10);
        theGraph.addNode(2);
        theGraph.addNode(9);
        theGraph.addNode(1);
        theGraph.addNode(0);
        theGraph.addNode(4);
        theGraph.addNode(6);
        theGraph.addNode(21);
        theGraph.connect(10,2,8.2);
        theGraph.connect(10,9,0.8);
        theGraph.connect(9,2,10.01);
        theGraph.connect(9,1,5.6);
        theGraph.connect(9,21,18.3);
        theGraph.connect(1,0,7.2);
        theGraph.connect(0,4,8.4);
        theGraph.connect(0,21,3.2);
        theGraph.connect(0,6,1.0);
        theGraph.connect(6,21,1.2);
        weighted_graph_algorithms algo = new WGraph_Algo();
        algo.init(theGraph);
        double shortestDist = algo.shortestPathDist(10,21);
        Assertions.assertEquals(15.8, shortestDist,EPS);
    }
    @Test
    public void createConBigG(){ //delete
        WGraph_DS theGraph = new WGraph_DS();

        for(int i=0;i<100000;i++) // insert nodes
            theGraph.addNode(i);

        for (int i=0;i<100000-4;i++){ //insert edges
            double weightOfEdge = Math.random()*220;
            theGraph.connect(i,i+1,weightOfEdge);
            theGraph.connect(i,i+4,weightOfEdge);
        }
        weighted_graph_algorithms lol = new WGraph_Algo();
        lol.init(theGraph);
        double shortestDist = lol.shortestPathDist(10,88000);
        System.out.println(shortestDist);



    }
    @Test
    void shortestPathDistTime(){ //delete
        WGraph_DS theGraph = new WGraph_DS();
        for(int i=0;i<100;i++) // insert nodes
            theGraph.addNode(i);

        for (int i=0;i<100;i++){ //insert edges
            double weightOfEdge = Math.random()*22;
            theGraph.connect(i,i+1,weightOfEdge);
            theGraph.connect(i,i+4,weightOfEdge);
        }
        //createConBigG();
        weighted_graph_algorithms algo = new WGraph_Algo();
        algo.init(theGraph);
        long t1= System.currentTimeMillis();
        algo.shortestPathDist(0,99);
        long t2= System.currentTimeMillis();
        Assertions.assertTrue((t2-t1)/1000.0 < 10);
        System.out.println("time it takes for searching the shortest path in a BIG graph: " + (t2-t1)/1000.0 + " TRUE");
        List<node_info> aa = algo.shortestPath(0,10000);
        System.out.println("number of nodes in path: "+aa.size());
    }


    @Test
    public void creatingConnectedG(){ //delete
        WGraph_DS theGraph = new WGraph_DS();
        theGraph.addNode(10);
        theGraph.addNode(2);
        theGraph.addNode(9);
        theGraph.addNode(1);
        theGraph.addNode(0);
        theGraph.addNode(4);
        theGraph.addNode(6);
        theGraph.addNode(21);
        theGraph.connect(10,2,8.2);
        theGraph.connect(10,9,0.8);
        theGraph.connect(9,2,10.01);
        theGraph.connect(9,1,5.6);
        theGraph.connect(9,21,18.3);
        theGraph.connect(1,0,7.2);
        theGraph.connect(0,4,8.4);
        theGraph.connect(0,21,3.2);
        theGraph.connect(0,6,1.0);

        weighted_graph_algorithms algo = new WGraph_Algo();
        algo.init(theGraph);
        double shortestDist = algo.shortestPathDist(10,21);
        Assertions.assertEquals(16.8, shortestDist);
        System.out.println("Shortest path destination test, number 1- passed - TRUE");
    }



    @Test
    public void Test12(){
        WGraph_DS theGraph = new WGraph_DS();

        for(int i=0;i<100000;i++) // insert nodes
            theGraph.addNode(i);

        for (int i=0;i<100000-4;i++){ //insert edges
            double weightOfEdge = Math.random()*22;
            theGraph.connect(i,i+1,weightOfEdge);
            theGraph.connect(i,i+4,weightOfEdge);
        }


        weighted_graph_algorithms algo = new WGraph_Algo();
        algo.init(theGraph);
        long t1= System.currentTimeMillis();
        algo.shortestPathDist(0,90000);
        long t2= System.currentTimeMillis();
        Assertions.assertTrue((t2-t1)/1000.0 < 10);
        System.out.println("time it takes for searching the shortest path in a BIG graph: " + (t2-t1)/1000.0 + " TRUE");
        List<node_info> aa = algo.shortestPath(0,10000);
        System.out.println("number of nodes in path: "+aa.size());
    }








}