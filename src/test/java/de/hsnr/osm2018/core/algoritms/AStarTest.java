package de.hsnr.osm2018.core.algoritms;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import de.hsnr.osm2018.data.graph.*;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class AStarTest {
    private Graph g;
    private Node start;
    private Node goal;

    @Before
    public void setUp() throws Exception {
        ArrayList<Node> nodes = new ArrayList<>();
        ArrayList<Edge> edges = new ArrayList<>();

        ArrayList<Edge> n1e = new ArrayList<>();
        ArrayList<Edge> n2e = new ArrayList<>();

        Node n1 = new Node(1,0.0,0.0,new ArrayList<>());
        nodes.add(n1);
        Node n2 = new Node(2,1.0,0.0,new ArrayList<>());
        nodes.add(n2);
        Node n3 = new Node(3,0.0,1.0,new ArrayList<>());
        nodes.add(n3);
        Node n4 = new Node(4,1.0,1.0,new ArrayList<>());
        nodes.add(n4);
        Node n5 = new Node(5,1.0,2.0,new ArrayList<>());
        nodes.add(n5);
        Node n6 = new Node(6,1.0,2.0,new ArrayList<>());
        nodes.add(n6);

        Edge e1 = new Edge(n1,n2,6,(short)30,EdgeType.PRIMARY);
        edges.add(e1);
        Edge e2 = new Edge(n1,n3,2,(short)30,EdgeType.PRIMARY);
        edges.add(e2);
        Edge e3 = new Edge(n1,n4,3,(short)30,EdgeType.PRIMARY);
        edges.add(e3);
        Edge e4 = new Edge(n2,n4,2,(short)30,EdgeType.PRIMARY);
        edges.add(e4);
        Edge e5 = new Edge(n3,n4,5,(short)30,EdgeType.PRIMARY);
        edges.add(e5);
        Edge e6 = new Edge(n3,n5,3,(short)30,EdgeType.PRIMARY);
        edges.add(e6);
        Edge e7 = new Edge(n4,n5,1,(short)30,EdgeType.PRIMARY);
        edges.add(e7);
        Edge e8 = new Edge(n5,n6,7,(short)30,EdgeType.PRIMARY);
        edges.add(e8);
        Edge e9 = new Edge(n5,n6,7,(short)30,EdgeType.PRIMARY);
        edges.add(e9);


        n1.addEdge(e1);
        n1.addEdge(e2);
        n1.addEdge(e3);

        n2.addEdge(e1);
        n2.addEdge(e4);

        n3.addEdge(e2);
        n3.addEdge(e5);
        n3.addEdge(e6);

        n4.addEdge(e3);
        n4.addEdge(e4);
        n4.addEdge(e5);
        n4.addEdge(e7);

        n5.addEdge(e6);
        n5.addEdge(e7);
        n5.addEdge(e8);
        n5.addEdge(e9);

        n6.addEdge(e8);
        n6.addEdge(e9);

        this.g = new Graph(nodes,edges);
        this.start = n1;
        this.goal = n6;
    }

    @Test
    public void runAStar() {
        AStar a = new AStar();
        assertTrue(a.runAStar(this.start,this.goal));
    }

    @Test
    public void getPath() {
        AStar a = new AStar();
        a.runAStar(this.start,this.goal);
        ArrayList<Node> path = a.getPath(this.start, this.goal);
        for(Node n : path){
            System.out.printf("Node %d, ",n.getId());
        }
        assertTrue(true);
    }
}