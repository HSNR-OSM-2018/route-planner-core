package de.hsnr.osm2018.core.algoritms;

import de.hsnr.osm2018.data.graph.*;
import de.hsnr.osm2018.data.path.PathFinder;

import java.util.*;
import java.util.logging.Logger;

public abstract class AStar extends PathFinder {

    private Logger logger = Logger.getLogger(AStar.class.getSimpleName());

    private HashMap<Long, NodeContainer> mContainer = new HashMap<>();

    public void clearContainer() {
        mContainer = new HashMap<>();
    }

    public abstract double computeHeuristic(Node start, Node destination, EdgeType eType);

    public abstract double getDistance(NodeContainer node, Edge edge);

    public AStar(Graph graph) {
        super(graph);
    }

    public NodeContainer getContainer(Node node) {
        if (mContainer.containsKey(node.getId())) {
            return mContainer.get(node.getId());
        }
        NodeContainer nodeContainer = new NodeContainer(node);
        mContainer.put(node.getId(), nodeContainer);
        return nodeContainer;
    }

    public void updateNode(NodeContainer n, double dist, double h, Node u){
        n.setD(dist);
        n.setF(h + dist);
        n.setParent(u);
    }

    @Override
    public boolean run(Node start, Node destination) {
        logger.info("Starting AStar for start = " + start + " and destination " + destination);
        long startTime = System.currentTimeMillis();
        int counter = 0, cDouble = 0;
        double dist, h;
        PriorityQueue<NodeContainer> openList = new PriorityQueue<>(20, Comparator.comparingDouble(NodeContainer::getF));
        //ArrayList<NodeContainer> closedList = new ArrayList<>();
        Set<NodeContainer> closedList = new HashSet<>();

        NodeContainer startContainer = getContainer(start);
        startContainer.setD(0.0);
        startContainer.setF(0.0);

        long t1,t2=0, timedist1, timedist2=0, timeopenlist1, timeopenlist2=0, timewhile1, timewhile2=0, timeif1, timeif2=0;

        openList.add(startContainer);
        NodeContainer neighbour, u, current;
        while (!openList.isEmpty()) {
            t1= System.currentTimeMillis();
            u = openList.poll();
            if (u.getId() == destination.getId()) {
                System.out.printf("Durchgangene Knoten: %d, Doppelte: %d, Gesamt: %d\n", counter, cDouble, counter + cDouble);
                clearPath();
                current = getContainer(destination);
                addPathNode(current.getNode(), current.getD());

                timewhile1=System.currentTimeMillis();
                while (current.getId() != start.getId()) {
                    current = getContainer(current.getParent());
                    addPathNode(0, current.getNode(), current.getD());
                }
                timewhile2=(System.currentTimeMillis()-timewhile1);

                long time = System.currentTimeMillis() - startTime;
                logger.info("Found path in " + (time ) + " miliseconds");
                /*logger.info("T2: "+ (t2)+" miliseconds");
                logger.info("Time Dist: "+ (timedist2)+" miliseconds");
                logger.info("Time Openlist: "+ (timeopenlist2)+" miliseconds");
                logger.info("Time While: "+ (timewhile2)+" miliseconds");
                logger.info("Time IF: "+ (timeif2)+" miliseconds");*/
                return true;
            }

            timeif1=System.currentTimeMillis();
            if (!closedList.contains(u)) {
                closedList.add(u);
                for (Edge e : u.getEdges()) {
                    neighbour = getContainer(e.getDestinationNode());
                    if (neighbour.getId() == start.getId()) {
                        continue;
                    }
                    if (neighbour.getId() == u.getId()) {
                        //neighbour = getContainer(e.getStartNode());
                        continue;
                    }
                    timedist1=System.currentTimeMillis();

                    dist = getDistance(u, e);
                    // System.out.printf("dist %f \n", dist);
                    h = computeHeuristic(neighbour.getNode(), destination, e.getType());

                    timedist2=timedist2+(System.currentTimeMillis() - timedist1);

                    timeopenlist1=System.currentTimeMillis();
                    if (openList.contains(neighbour) && neighbour.getD() > dist) {
                        cDouble++;
                        updateNode(neighbour, dist, h, u.getNode());
                    } else if (neighbour.getParent() == null) {
                        counter++;
                        updateNode(neighbour, dist, h, u.getNode());
                        openList.add(neighbour);
                    }
                    timeopenlist2=timeopenlist2+(System.currentTimeMillis()-timeopenlist1);
                }
            }
            timeif2=timeif2+(System.currentTimeMillis() - timeif1);
            t2=t2+(System.currentTimeMillis()-t1);
        }
        long time = System.currentTimeMillis() - startTime;
        logger.info("Found no path in " + (time / 1000) + " seconds");

        return false;
    }
}
