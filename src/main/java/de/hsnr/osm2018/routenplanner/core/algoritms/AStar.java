package de.hsnr.osm2018.routenplanner.core.algoritms;

import de.hsnr.osm2018.routenplanner.core.graph.*;
import java.util.PriorityQueue;
import java.util.ArrayList;

public class AStar {

    public boolean voidrunAStar(Node root, Node goal){
        PriorityQueue<Node> openlist = new PriorityQueue<>();
        ArrayList<Node> closedlist = new ArrayList<>();

        openlist.add(root);

        while(!openlist.isEmpty()){
            Node u = openlist.poll();
            if(u == goal){ // TODO überprüfen, ob compare equal überschreibt
                return true;
            }
            if(!closedlist.contains(u)) {
                closedlist.add(u);
                for (Edge e : u.getEdges()) {
                    Node neighbour = e.getEnd();
                    float dist = u.getWeight() + e.getWeight();

                    if(openlist.contains(neighbour) && neighbour.getWeight() > dist){
                        neighbour.setWeight(dist);
                        neighbour.computeHeuristic(goal);
                        neighbour.setParent(u);
                    } else if(neighbour.getParent() == null){
                        neighbour.setWeight(dist);
                        neighbour.computeHeuristic(goal);
                        neighbour.setParent(u);
                        openlist.add(neighbour);
                    }

                }
            }
        }
        return false;
    }

    public ArrayList<Node> getPath(Node goal){
        ArrayList<Node> = new ArrayList<>();
        Node current = goal;
        while(current.getParent() != null){

        }
    }
}
