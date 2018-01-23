package de.hsnr.osm2018.core.algoritms;

import de.hsnr.osm2018.data.graph.Edge;
import de.hsnr.osm2018.data.graph.Graph;
import de.hsnr.osm2018.data.graph.Node;
import de.hsnr.osm2018.data.graph.NodeContainer;
import de.hsnr.osm2018.data.path.PathFinder;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

public abstract class AStar extends PathFinder {

    private HashMap<Long, NodeContainer> mContainer = new HashMap<>();

    public AStar(Graph graph) {
        super(graph);
    }

    public NodeContainer getContainer(Node node) {
        if (mContainer.containsKey(node.getId())) {
            return mContainer.get(node.getId());
        }
        mContainer.put(node.getId(), new NodeContainer(node));
        return mContainer.get(node.getId());
    }

    public void clearContainer() {
        mContainer = new HashMap<>();
    }

    @Override
    public boolean run(Node start, Node destination) {
        int counter = 0, cDouble = 0;
        double dist, h;
        PriorityQueue<NodeContainer> openList = new PriorityQueue<>(20, Comparator.comparingDouble(NodeContainer::getF));
        ArrayList<NodeContainer> closedList = new ArrayList<>();

        NodeContainer startContainer = getContainer(start);
        startContainer.setD(0.0);
        startContainer.setF(0.0);

        openList.add(startContainer);
        NodeContainer neighbour;
        while (!openList.isEmpty()) {
            NodeContainer u = openList.poll();
            if (u.getId() == destination.getId()) {
                System.out.printf("Durchgangene Knoten: %d, Doppelte: %d, Gesamt: %d\n", counter, cDouble, counter + cDouble);
                clearPath();
                NodeContainer current = getContainer(destination);
                addPathNode(current.getNode(), current.getD());
                while (current.getId() != start.getId()) {
                    current = getContainer(current.getParent());
                    addPathNode(0, current.getNode(), current.getD());
                }
                return true;
            }

            if (!closedList.contains(u)) {
                closedList.add(u);
                for (Edge e : u.getEdges()) {
                    neighbour = getContainer(e.getDestinationNode());
                    if (neighbour.getId() == start.getId()) {
                        continue;
                    }
                    if (neighbour.getId() == u.getId()) {
                        continue;
                        //neighbour = getContainer(e.getStartNode());
                    }
                    dist = getDistance(u, e);
                    // System.out.printf("dist %f \n", dist);
                    h = neighbour.getNode().getDistance(destination);

                    if (openList.contains(neighbour) && neighbour.getD() > dist) {
                        cDouble++;
                        neighbour.setD(dist);
                        neighbour.setF(h + dist);
                        neighbour.setParent(u.getNode());
                    } else if (neighbour.getParent() == null) {
                        counter++;
                        neighbour.setD(dist);
                        neighbour.setF(h + dist);
                        neighbour.setParent(u.getNode());
                        openList.add(neighbour);
                    }
                }
            }
        }
        return false;
    }

    public abstract double getDistance(NodeContainer node, Edge edge);
}
