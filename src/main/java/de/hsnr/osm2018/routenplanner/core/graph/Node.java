package de.hsnr.osm2018.routenplanner.core.graph;

import de.hsnr.osm2018.routenplanner.core.graph.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Node implements Comparator{
    private int id;
    private float lon;
    private float lat;
    private float g; // Weight
    private float f;
    private Node parent;

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public Node getParent() {

        return parent;
    }

    private ArrayList<Edge> edges; // List of edges from this Node

    public Node(int id, float lon, float lat) {
        this.id = id;
        this.lon = lon;
        this.lat = lat;
        this.g = 0;
    }

    public Node(int id, float lon, float lat, ArrayList<Edge> edges) {
        this.id = id;
        this.lon = lon;
        this.lat = lat;
        this.edges = edges;
    }

    public void addEdge(Edge e){
        this.edges.add(e);
    }

    public int getId() {
        return id;
    }

    public float getLon() {
        return lon;
    }

    public float getLat() {
        return lat;
    }

    public float getWeight() {
        return g;
    }
    public void setWeight(float weight){
        this.g = weight;
    }

    public ArrayList<Edge> getEdges() {
        return edges;
    }

    public Edge getMinEdge(){
        return Collections.min(this.edges);
    }
    public float getF(){
        return f;
    }

    public void computeHeuristic(Node goal){
        float h = Math.abs(goal.lon - this.lon) + Math.abs(goal.lat - this.lat);
        this.f = this.g + h;
    }

    @Override
    public int compare(Object node1, Object node2) {
        Node o = (Node) node1;
        Node t1 = (Node) node2;
        if(o.getF() > t1.getF()){
            return 1;
        }

        else if (o.getF() < t1.getF()){
            return -1;
        }

        else{
            return 0;
        }
    }

}