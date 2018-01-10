package de.hsnr.osm2018.routenplanner.core.graph;

import de.hsnr.osm2018.routenplanner.core.graph.*;

public class Edge implements Comparable<Edge>{
    private float w; // weight
    private Node start;
    private Node end;

    public Edge(float wheigth, Node start, Node end) {
        this.w = wheigth;
        this.start = start;
        this.end = end;
    }

    public float getWeight() {
        return w;
    }

    public Node getStart() {
        return start;
    }

    public Node getEnd() {
        return end;
    }

    /*
    @Override
    public boolean equals(Object other) {
        if (other == null) return false;
        if (other == this) return true;
        if (!(other instanceof Edge)) return false;
        Edge otherEdge = (Edge) other;
        if (this.getWeight() == otherEdge.getWeight()) {
            return true;
        }
        return false;

    }*/
    @Override
    public int compareTo(Edge other) {
        return Float.compare(this.getWeight(), other.getWeight());
    }
}
