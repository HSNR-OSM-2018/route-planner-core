package de.hsnr.osm2018.core.algoritms;

import de.hsnr.osm2018.data.graph.Graph;
import de.hsnr.osm2018.data.graph.Node;
import de.hsnr.osm2018.data.graph.NodeContainer;

public class DistanceGreedy extends DistanceAStar{


    public DistanceGreedy(Graph graph) {
        super(graph);
    }

    @Override
    public void updateNode(NodeContainer n, double dist, double h, Node u) {
        n.setD(dist);
        n.setF(h);
        n.setParent(u);
    }
}
