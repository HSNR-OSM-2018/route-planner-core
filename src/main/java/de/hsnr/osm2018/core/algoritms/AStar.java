package de.hsnr.osm2018.core.algoritms;

import de.hsnr.osm2018.data.graph.*;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.ArrayList;

public class AStar {

    private double computeHeuristic(Node current, Node goal) {
        return Math.abs(goal.getLongitude() - current.getLongitude()) + Math.abs(goal.getLatitude() - current.getLatitude());
    }

    public boolean runAStar(Graph graph, Node root, Node goal) {
        PriorityQueue<Node> openlist = new PriorityQueue<>(20, new Comparator<Node>() {
            @Override
            public int compare(Node o1, Node o2) {
                return Double.compare(o1.getF(), o2.getF());
            }
        });
        ArrayList<Node> closedlist = new ArrayList<>();
        root.setD(0.0);

        openlist.add(root);

        while (!openlist.isEmpty()) {
            Node u = openlist.poll();
            if (u == goal) {
                return true;
            }
            if (!closedlist.contains(u)) {
                closedlist.add(u);
                for (Edge e : u.getEdges()) {
                    Node neighbour = e.getDestinationNode(graph);
                    if (neighbour == u) {
                        neighbour = e.getStartNode();
                    }
                    double dist = u.getD() + e.getLength();
                    double h = this.computeHeuristic(goal, neighbour);

                    if (openlist.contains(neighbour) && neighbour.getD() > dist) {
                        neighbour.setD(dist);
                        neighbour.setF(h + dist);
                        neighbour.setParent(u);
                    } else if (neighbour.getParent() == null) {
                        neighbour.setD(dist);
                        neighbour.setF(h + dist);
                        neighbour.setParent(u);
                        openlist.add(neighbour);
                    }

                }
            }
        }
        return false;
    }

    public boolean runAStarWithSpeed(Graph graph, Node root, Node goal) {
        PriorityQueue<Node> openlist = new PriorityQueue<>(20, new Comparator<Node>() {
            @Override
            public int compare(Node o1, Node o2) {
                return Double.compare(o1.getF(), o2.getF());
            }
        });
        ArrayList<Node> closedlist = new ArrayList<>();
        root.setD(0.0);

        openlist.add(root);

        while (!openlist.isEmpty()) {
            Node u = openlist.poll();
            if (u == goal) {
                return true;
            }
            if (!closedlist.contains(u)) {
                closedlist.add(u);
                for (Edge e : u.getEdges()) {
                    Node neighbour = e.getDestinationNode(graph);
                    if (neighbour == u) {
                        neighbour = e.getStartNode();
                    }
                    double dist = u.getD() + ((e.getLength()/1000)/e.getSpeed());
                    double h = (this.computeHeuristic(goal, neighbour));

                    if (openlist.contains(neighbour) && neighbour.getD() > dist) {
                        neighbour.setD(dist);
                        neighbour.setF(h + dist);
                        neighbour.setParent(u);
                    } else if (neighbour.getParent() == null) {
                        neighbour.setD(dist);
                        neighbour.setF(h + dist);
                        neighbour.setParent(u);
                        openlist.add(neighbour);
                    }

                }
            }
        }
        return false;
    }

    public ArrayList<Node> getPath(Node root, Node goal) {
        ArrayList<Node> path = new ArrayList<>();
        Node current = goal;
        path.add(current);
        while (current != root) {
            current = current.getParent();
            path.add(0, current);
        }
        return path;
    }
}
