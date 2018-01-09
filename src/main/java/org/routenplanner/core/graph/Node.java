package org.routenplanner.core.graph;

public class Node {
    private int id;
    private float lon;
    private float lat;
    private float w; // Weight to next node (für robert)
    // liste mit allen anliegenden Knaten in ausgansrichtung (Befahrbar)

    public Node(int id, float lon, float lat) {
        this.id = id;
        this.lon = lon;
        this.lat = lat;
    }
}
