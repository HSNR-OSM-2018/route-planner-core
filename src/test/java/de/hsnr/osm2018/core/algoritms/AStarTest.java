package de.hsnr.osm2018.core.algoritms;

import de.hsnr.osm2018.data.path.PathContainer;
import de.hsnr.osm2018.provider.provider.PbfProvider;
import de.hsnr.osm2018.provider.provider.RandomProvider;
import org.junit.Before;
import org.junit.Test;
import de.hsnr.osm2018.data.graph.*;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

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

        n1.addEdge(new Edge(n1, n2, 6, (short) 60, EdgeType.PRIMARY));
        n1.addEdge(new Edge(n1, n3, 2, (short) 110, EdgeType.PRIMARY));
        n1.addEdge(new Edge(n1, n4, 3, (short) 60, EdgeType.PRIMARY));
        n2.addEdge(new Edge(n2, n4, 2, (short) 110, EdgeType.PRIMARY));
        n3.addEdge(new Edge(n3, n4, 5, (short) 60, EdgeType.PRIMARY));
        n3.addEdge(new Edge(n3, n5, 3, (short) 60, EdgeType.PRIMARY));
        n4.addEdge(new Edge(n4, n5, 1, (short) 110, EdgeType.PRIMARY));
        n5.addEdge(new Edge(n5, n6, 7, (short) 60, EdgeType.PRIMARY));
        n5.addEdge(new Edge(n5, n6, 7, (short) 110, EdgeType.PRIMARY));

        this.start = n1;
        this.goal = n6;
    }

    @Test
    public void runAStar() {
        DistanceAStar a = new DistanceAStar(this.graph);
        assertTrue(a.run(this.start, this.goal));
    }

    @Test
    public void randomTest() {
        int cpath = 0;
        Instant t1, t2;
        Duration elapse;
        RandomProvider provider = new RandomProvider(1000000);
        Graph graph = provider.getGraph();
        Node start = graph.getNode(1L);
        Node goal = graph.getNode(950000L);

        DistanceAStar a = new DistanceAStar(graph);
        t1 = Instant.now();
        a.run(start, goal);
        t2 = Instant.now();
        elapse = Duration.between(t1, t2);
        List<PathContainer> path = a.getPath();
        for (PathContainer n : path) {
            cpath++;
            System.out.printf("Node %d, Gewicht: %f ", n.getNode().getId(), n.getDistance());
        }
        System.out.printf("\nGewicht : %f, Knoten: %d, Dauer %dms \n", a.getContainer(goal).getD(), cpath, elapse.toMillis());

        System.out.printf("start\n");
        a.clearContainer();
        System.out.printf("end\n");

        t1 = Instant.now();
        a.run(start, goal);
        t2 = Instant.now();
        elapse = Duration.between(t1, t2);
        path = a.getPath();
        for (PathContainer n : path) {
            cpath++;
            System.out.printf("Node %d, Gewicht: %f ", n.getNode().getId(), n.getDistance());
        }
        System.out.printf("\nGewicht : %f, Knoten: %d, Dauer %dms \n", a.getContainer(goal).getD(), cpath, elapse.toMillis());
    }

    @Test
    public void duesseldorfTest() {
        int cpath = 0;
        Instant t1, t2;
        Duration elapse;
        PbfProvider provider = new PbfProvider("ddorf.pbf");
        Graph graph = provider.getGraph();
        Node start = graph.getNode(1544556049L);
        Node goal = graph.getNode(34018014L);

        AStar a = new SpeedAStar(graph);
        t1 = Instant.now();
        a.run(start, goal);
        t2 = Instant.now();
        elapse = Duration.between(t1, t2);
        List<PathContainer> path = a.getPath();
        for (PathContainer n : path) {
            cpath++;
            System.out.printf("Node %d, Gewicht: %f ", n.getNode().getId(), n.getDistance());
        }
        System.out.printf("\nGewicht : %f, Knoten: %d, Dauer %dms \n", a.getContainer(goal).getD(), cpath, elapse.toMillis());

        System.out.printf("start\n");
        a = new DistanceAStar(graph);
        System.out.printf("end\n");

        cpath = 0;
        t1 = Instant.now();
        a.run(start, goal);
        t2 = Instant.now();
        elapse = Duration.between(t1, t2);
        path = a.getPath();
        for (PathContainer n : path) {
            cpath++;
            System.out.printf("Node %d, Gewicht: %f ", n.getNode().getId(), n.getDistance());
        }
        System.out.printf("\nGewicht : %f, Knoten: %d, Dauer %dms \n", a.getContainer(goal).getD(), cpath, elapse.toMillis());
    }

    @Test
    public void getPath() {
        DistanceAStar a = new DistanceAStar(this.graph);
        SpeedAStar b = new SpeedAStar(this.graph);
        b.run(this.start, this.goal);
        List<PathContainer> pathWithSpeed = b.getPath();

        for (PathContainer n : pathWithSpeed) {
            System.out.printf("Node %d, ", n.getNode().getId());
        }
        System.out.printf("\n");

        a.run(this.start, this.goal);
        List<PathContainer> path = a.getPath();
        for (PathContainer n : path) {
            System.out.printf("Node %d, ", n.getNode().getId());
        }
        assertTrue(true);
    }
}