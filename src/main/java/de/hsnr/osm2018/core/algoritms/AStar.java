package de.hsnr.osm2018.core.algoritms;

import de.hsnr.osm2018.data.graph.*;

import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.ArrayList;

public class AStar {

    private HashMap<Long, NodeContainer> mContainer = new HashMap<>();

    private double computeHeuristic(Node current, Node goal) {
        return Math.abs(current.getLongitude() - goal.getLongitude()) + Math.abs(current.getLatitude() - goal.getLatitude());
    }

    private NodeContainer getContainer(Node node) {
        if (mContainer.containsKey(node.getId())) {
            return mContainer.get(node.getId());
        }
        mContainer.put(node.getId(), new NodeContainer(node));
        return mContainer.get(node.getId());
    }

    public boolean runAStar(Graph graph, Node root, Node goal) {
        int counter = 0, cdouble = 0;
        PriorityQueue<NodeContainer> openlist = new PriorityQueue<>(20, new Comparator<NodeContainer>() {
            @Override
            public int compare(NodeContainer o1, NodeContainer o2) {
                return Double.compare(o1.getF(), o2.getF());
            }
        });
        ArrayList<NodeContainer> closedlist = new ArrayList<>();

        NodeContainer rootContainer = getContainer(root);
        rootContainer.setD(0.0);
        rootContainer.setF(0.0);

        openlist.add(rootContainer);
        NodeContainer neighbour;
        while (!openlist.isEmpty()) {
            NodeContainer u = openlist.poll();
            if (u.getNode().getId() == goal.getId()) {
                System.out.printf("Durchgangene Knoten: %d, Doppelte: %d, Gesamt: %d\n", counter, cdouble, counter+cdouble);
                return true;
            }

            if (!closedlist.contains(u)) {
                closedlist.add(u);
                for (Edge e : u.getNode().getEdges()) {
                    neighbour = getContainer(e.getDestinationNode());
                    if(neighbour.getNode().getId() == root.getId()){
                        continue;
                    }
                    if (neighbour.getNode().getId() == u.getNode().getId()) {
                        neighbour = getContainer(e.getStartNode());
                    }
                    double dist = u.getD() + ((double)e.getLength())/(1000);
                   // System.out.printf("dist %f \n", dist);
                    double h = this.computeHeuristic(goal, neighbour.getNode());


                    if (openlist.contains(neighbour) && neighbour.getD() > dist) {
                        counter++;
                        neighbour.setD(dist);
                        neighbour.setF(h + dist);
                        neighbour.setParent(u);
                    } else if (neighbour.getParent() == null) {
                        cdouble++;
                        neighbour.setD(dist);
                        neighbour.setF(h + dist);
                        neighbour.setParent(u.getNode());
                        openlist.add(neighbour);
                    }


                }
            }
        }
        return false;
    }

    public boolean runAStarWithSpeed(Graph graph, Node root, Node goal) {
        int counter = 0, cdouble = 0;
        PriorityQueue<NodeContainer> openlist = new PriorityQueue<>(20, new Comparator<NodeContainer>() {
            @Override
            public int compare(NodeContainer o1, NodeContainer o2) {
                return Double.compare(o1.getF(), o2.getF());
            }
        });
        ArrayList<NodeContainer> closedlist = new ArrayList<>();

        NodeContainer rootContainer = getContainer(root);
        rootContainer.setD(0.0);

        openlist.add(rootContainer);

        while (!openlist.isEmpty()) {
            NodeContainer u = openlist.poll();
            if (u.getNode().getId() == goal.getId()) {
                System.out.printf("Durchgangene Knoten: %d, Doppelte: %d, Gesamt: %d\n", counter, cdouble, counter+cdouble);
                return true;
            }
            if (!closedlist.contains(u)) {
                closedlist.add(u);
                for (Edge e : u.getNode().getEdges()) {
                    if (e.getSpeed() == 0){
                        continue;
                    }
                    NodeContainer neighbour = getContainer(e.getDestinationNode());

                    if(neighbour.getNode().getId() == root.getId()){
                        continue;
                    }

                    if (neighbour.getNode().getId() == u.getNode().getId()) {
                        neighbour = getContainer(e.getStartNode());
                    }



                    double dist = u.getD() + ( ((double)(e.getLength()/1000))/e.getSpeed());
                    double h = (this.computeHeuristic(goal, neighbour.getNode()));

                    if (openlist.contains(neighbour) && neighbour.getD() > dist) {
                        cdouble++;
                        neighbour.setD(dist);
                        neighbour.setF(h + dist);
                        neighbour.setParent(u.getNode());
                    } else if (neighbour.getParent() == null) {
                        counter++;
                        neighbour.setD(dist);
                        neighbour.setF(h + dist);
                        neighbour.setParent(u.getNode());
                        openlist.add(neighbour);
                    }

                }
            }
        }
        return false;
    }

    public ArrayList<NodeContainer> getPath(Node root, Node goal) {
        ArrayList<NodeContainer> path = new ArrayList<>();
        NodeContainer current = getContainer(goal);
        path.add(current);
        while (current.getNode().getId() != root.getId()) {
            current = getContainer(current.getParent());
            path.add(0, current);
        }
        return path;
    }
}
