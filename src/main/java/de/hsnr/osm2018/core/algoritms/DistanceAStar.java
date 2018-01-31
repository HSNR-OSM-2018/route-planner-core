package de.hsnr.osm2018.core.algoritms;

import de.hsnr.osm2018.data.graph.*;

public class DistanceAStar extends AStar {

    public DistanceAStar(Graph graph) {
        super(graph);
    }

    @Override
    public double computeHeuristic(Node start, Node destination,EdgeType eType) {
        return start.getDistance(destination);
    }

    @Override
    public double getDistance(NodeContainer node, Edge edge) {
        //return node.getD() + ((double) edge.getLength()) / (1000);  // km
        return node.getD() + (double) edge.getLength();  // m
    }
}