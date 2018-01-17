package de.hsnr.osm2018.core.algoritms;

import de.hsnr.osm2018.data.graph.*;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.ArrayList;

public class AStar {

    private double computeHeuristic(Node current, Node goal) {
        //return Math.abs(current.getLongitude() - goal.getLongitude()) + Math.abs(current.getLatitude() - goal.getLatitude());
        double dx = Math.abs(current.getLongitude() - goal.getLatitude());
        double dy = Math.abs(goal.getLongitude() - goal.getLatitude());
        return Math.sqrt((dx*dx)+(dy*dy));
    }

    public boolean runAStar(Graph graph, Node root, Node goal) {
        int counter = 0, cdouble = 0;
        PriorityQueue<Node> openlist = new PriorityQueue<>(20, new Comparator<Node>() {
            @Override
            public int compare(Node o1, Node o2) {
                return Double.compare(o1.getF(), o2.getF());
            }
        });
        ArrayList<Node> closedlist = new ArrayList<>();
        root.setD(0.0);
        root.setF(0.0);

        openlist.add(root);
        Node neighbour;
        while (!openlist.isEmpty()) {
            Node u = openlist.poll();
            if (u.getId().longValue() == goal.getId().longValue()) {
                System.out.printf("Durchgangene Knoten: %d, Doppelte: %d, Gesamt: %d\n", counter, cdouble, counter+cdouble);
                return true;
            }

            if (!closedlist.contains(u)) {
                closedlist.add(u);
                for (Edge e : u.getEdges()) {
                    neighbour = e.getDestinationNode(graph);
                    if(neighbour.getId().longValue() == root.getId().longValue()){
                        continue;
                    }
                    if (neighbour.getId().longValue() == u.getId().longValue()) {
                        neighbour = e.getStartNode();
                    }
                    double dist = u.getD() + ((double)e.getLength())/(1000);
                   // System.out.printf("dist %f \n", dist);
                    double h = this.computeHeuristic(goal, neighbour);


                    if (openlist.contains(neighbour) && neighbour.getD() > dist) {
                        counter++;
                        neighbour.setD(dist);
                        neighbour.setF(h + dist);
                        neighbour.setParent(u);
                    } else if (neighbour.getParent() == null) {
                        cdouble++;
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
        int counter = 0, cdouble = 0;
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
            if (u.getId().longValue() == goal.getId().longValue()) {
                System.out.printf("Durchgangene Knoten: %d, Doppelte: %d, Gesamt: %d\n", counter, cdouble, counter+cdouble);
                return true;
            }
            if (!closedlist.contains(u)) {
                closedlist.add(u);
                for (Edge e : u.getEdges()) {
                    if (e.getSpeed() == 0){
                        continue;
                    }
                    Node neighbour = e.getDestinationNode(graph);

                    if(neighbour.getId().longValue() == root.getId().longValue()){
                        continue;
                    }

                    if (neighbour.getId().longValue() == u.getId().longValue()) {
                        neighbour = e.getStartNode();
                    }



                    double dist = u.getD() + ( ((double)(e.getLength()/1000))/e.getSpeed());
                    double h = (this.computeHeuristic(goal, neighbour));

                    if (openlist.contains(neighbour) && neighbour.getD() > dist) {
                        cdouble++;
                        neighbour.setD(dist);
                        neighbour.setF(h + dist);
                        neighbour.setParent(u);
                    } else if (neighbour.getParent() == null) {
                        counter++;
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
        while (current.getId().longValue() != root.getId().longValue()) {
            current = current.getParent();
            path.add(0, current);
        }
        return path;
    }
}
