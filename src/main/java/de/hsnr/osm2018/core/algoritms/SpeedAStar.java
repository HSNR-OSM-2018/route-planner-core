package de.hsnr.osm2018.core.algoritms;

import de.hsnr.osm2018.data.graph.Edge;
import de.hsnr.osm2018.data.graph.Graph;
import de.hsnr.osm2018.data.graph.NodeContainer;

public class SpeedAStar extends AStar {

    public SpeedAStar(Graph graph) {
        super(graph);
    }

    @Override
    public double getDistance(NodeContainer node, Edge edge) {
        return node.getD() + (((double) (edge.getLength() / 1000)) / edge.getSpeed());
    }
}