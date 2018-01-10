package de.hsnr.osm2018.routenplanner.core.graph;

import de.hsnr.osm2018.routenplanner.core.graph.*;
import java.util.ArrayList;
import java.util.Collections;

public class Node {
    private int id;
    private float lon;
    private float lat;
    private float w; // Weight
    private float h; // Heuristik
    private ArrayList<Edge> edges; // List of edges from this Node

    public Node(int id, float lon, float lat) {
        this.id = id;
        this.lon = lon;
        this.lat = lat;
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
        return w;
    }

    public ArrayList<Edge> getEdges() {
        return edges;
    }

    public Edge getMinEdge(){
        return Collections.min(this.edges);
    }

    public void computeWeight(Node goal, Edge e){
        this.h = Math.abs(goal.lon - this.lon) + Math.abs(goal.lat - this.lat);
        this.w = this.h + e.getWeight();
    }
}