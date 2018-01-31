package de.hsnr.osm2018.core.algoritms;

import de.hsnr.osm2018.data.graph.*;

public class DijkstraAStar extends AStar {

    public DijkstraAStar(Graph graph) {
        super(graph);
    }

    @Override
    public double computeHeuristic(Node start, Node destination, EdgeType eType) {
        return 0;
    }

    @Override
    public double getDistance(NodeContainer node, Edge edge) {
        return 0;
    }
}