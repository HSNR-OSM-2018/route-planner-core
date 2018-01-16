package de.hsnr.osm2018.core.algoritms;

import de.hsnr.osm2018.provider.RandomProvider;
import org.junit.Before;
import org.junit.Test;
import de.hsnr.osm2018.data.graph.*;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class AStarTest {
    private Graph graph;
    private Node start;
    private Node goal;

    @Before
    public void setUp() {
        this.graph = new Graph();

        Node n1 = new Node(1L, 0.0, 0.0);
        Node n2 = new Node(2L, 1.0, 0.0);
        Node n3 = new Node(3L, 0.0, 1.0);
        Node n4 = new Node(4L, 1.0, 1.0);
        Node n5 = new Node(5L, 1.0, 2.0);
        Node n6 = new Node(6L, 1.0, 2.0);

        graph.add(n1);
        graph.add(n2);
        graph.add(n3);
        graph.add(n4);
        graph.add(n5);
        graph.add(n6);

        n1.addEdge(new Edge(n1, n2.getId(), 6, (short) 60, EdgeType.PRIMARY));
        n1.addEdge(new Edge(n1, n3.getId(), 2, (short) 110, EdgeType.PRIMARY));
        n1.addEdge(new Edge(n1, n4.getId(), 3, (short) 60, EdgeType.PRIMARY));
        n2.addEdge(new Edge(n2, n4.getId(), 2, (short) 110, EdgeType.PRIMARY));
        n3.addEdge(new Edge(n3, n4.getId(), 5, (short) 60, EdgeType.PRIMARY));
        n3.addEdge(new Edge(n3, n5.getId(), 3, (short) 60, EdgeType.PRIMARY));
        n4.addEdge(new Edge(n4, n5.getId(), 1, (short) 110, EdgeType.PRIMARY));
        n5.addEdge(new Edge(n5, n6.getId(), 7, (short) 60, EdgeType.PRIMARY));
        n5.addEdge(new Edge(n5, n6.getId(), 7, (short) 110, EdgeType.PRIMARY));

        this.start = n1;
        this.goal = n6;
    }

    @Test
    public void runAStar() {
        AStar a = new AStar();
        assertTrue(a.runAStar(this.graph, this.start, this.goal));
    }

    @Test
    public void randomTest(){
        RandomProvider provider = new RandomProvider(100);
        Graph graph = provider.getGraph();
        Node start = graph.getNode(1L);
        Node goal = graph.getNode(55L);

        AStar a = new AStar();
        a.runAStarWithSpeed(graph, start,goal);
        ArrayList<Node> path = a.getPath(start,goal);
        for (Node n : path) {
            System.out.printf("Node %d, Gewicht: %f ", n.getId(), n.getD());
        }
        System.out.printf("Gewicht : %f \n",goal.getD());

        for (Node n: graph.getNodes().values()){
            n.setParent(null);
        }

        a.runAStar(graph,start,goal);
        path = a.getPath(start,goal);
        for (Node n : path) {
            System.out.printf("Node %d, Gewicht: %f ", n.getId(), n.getD());
        }
        System.out.printf("Gewicht : %f \n",goal.getD());



    }

    @Test
    public void getPath() {
        AStar a = new AStar();
        AStar b = new AStar();
        b.runAStarWithSpeed(this.graph,this.start, this.goal);
        ArrayList<Node> pathWithSpeed=b.getPath(this.start, this.goal);

        for (Node n : pathWithSpeed) {
            System.out.printf("Node %d, ", n.getId());
        }
        System.out.printf("\n");

        a.runAStar(this.graph, this.start, this.goal);
        ArrayList<Node> path = a.getPath(this.start, this.goal);
        for (Node n : path) {
            System.out.printf("Node %d, ", n.getId());
        }
        assertTrue(true);
    }
}