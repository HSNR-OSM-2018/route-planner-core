package de.hsnr.osm2018.core.algoritms;

import de.hsnr.osm2018.data.graph.*;

public class SpeedAStar extends AStar {

    public SpeedAStar(Graph graph) {
        super(graph);
    }

    @Override
    public double computeHeuristic(Node start, Node destination, EdgeType eType) {


        switch (eType) {
            case MOTORWAY:
                return start.getDistance(destination) / 36.1111D; // 130km/h
            case MOTORWAY_LINK:
                return start.getDistance(destination) / 36.1111D; // 130km/h
            case TRUNK:
                return start.getDistance(destination) / 27.7778D; // 100km/h
            case TRUNK_LINK:
                return start.getDistance(destination) / 27.7778D; // 100km/h
            case RESIDENTIAL:
                return start.getDistance(destination) / 13.8889D; // 50km/h
            case SERVICE:
                return start.getDistance(destination) / 8.3333D; // 30km/h
            case LIVING_STREET:
                return start.getDistance(destination) / 2.7778; // 10km/h
            default:
                return start.getDistance(destination) / 16.6667; // 60km/h

        }


    }

    @Override
    public double getDistance(NodeContainer node, Edge edge) {
        return node.getD() + (((double) edge.getLength()) / (((double) edge.getSpeed()) / 3.6D));
    }
}