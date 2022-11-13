package com.bugwars.Assignment2.Game1;

import java.util.PriorityQueue;

public class PathFindingAStar {

    private PriorityQueue<Tile> graph;
    private Tile start, end;
    private Heuristic hue;

    private PriorityQueue<Tile> open;

    public PathFindingAStar(PriorityQueue<Tile> graph, Tile start, Tile end, Heuristic hue){
        this.graph = graph;
        this.start = start;
        this.end = end;
        this.hue = hue;

        open = new PriorityQueue<>(256, new TileCompare());

    }


}