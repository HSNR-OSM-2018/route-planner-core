package de.hsnr.osm2018.core.algoritms;

import de.hsnr.osm2018.data.graph.Edge;
import de.hsnr.osm2018.data.graph.Graph;
import de.hsnr.osm2018.data.graph.Node;
import de.hsnr.osm2018.data.graph.NodeContainer;

public class SpeedAStar extends AStar {

    public SpeedAStar(Graph graph) {
        super(graph);
    }

    @Override
    public double computeHeuristic(Node start, Node destination, short speed) {
        return start.getDistance(destination) / (((double)speed) * 3.6D);
    }

    @Override
    public double getDistance(NodeContainer node, Edge edge) {
        return node.getD() + (((double) edge.getLength()) / (((double)edge.getSpeed())/3.6D));
    }
}