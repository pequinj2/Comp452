package com.bugwars.Assignment2.Game1;

import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;

/**
 * This class is the main A* algorithm, that will initialize the priority queue (a min-heap), set
 * the starting tile and the 'noGaol' tile to mark if a goal can not be found.
 */
public class PathFindingAStar {

    private HashMap<Integer, Tile> graph;
    private static HashMap<Integer, Tile> startingGraph;
    private Tile start, end, current, noGoal;
    private Heuristic hue;

    private PriorityQueue<Tile> open;
    private float newEndCost;
    private float initializedStart;

    public PathFindingAStar(HashMap<Integer, Tile> graph, Tile start, Tile end, Heuristic hue){
        this.graph = graph;
        // Set the original state of the graph - this is used later if the user wants to redo the
        // simulation
        setHashMap(graph);
        this.start = start;
        // Keep a record of the start node - used if the simulation is set to be redone
        initializedStart = start.getID();
        this.end = end;
        this.hue = hue;

        // Initialize the 'open' list as a min-heap priority queue and use the custom comparator
        // This comparator will compare the 'estimatedTotalCost' (or F(n)) when sorting
        open = new PriorityQueue<>(256, new TileCompare());
        // Set heuristic value
        start.setEstimatedTotalCost(hue.estimatedCost(start, 1));
        // Set connection to Null because we don't know what Tile the Ant will move to yet
        start.setConnection(null);
        // Add to our queue
        open.add(start);

        // Initialize Tile that will be returned if the no goal can be found
        // Only the -1 ID is set because it doesn't need any other info
        noGoal = new Tile(-1);

    }

    /**
     * A* Algorithm implementation
     * Keeping polling the top of the priority queue until either the 'End' Tile is found or there
     * are no more tiles to get from the queue.
     * @return Tile that was currently looked at or the 'noGoal' Tile if there are no more tiles to
     * look at
     */
    public Tile findPath(){

        // Check if there are any more tiles to be searched - if not, return the 'noGoal' Tile
        if(open.size() == 0){
            return noGoal;
        }
        else {
            // Get the Tile with the lowest F(n) cost
            current = open.poll();
            // Get the connections attached to the current Tile
            List<Integer> connections = current.getConnections();

            // Parse through the Tiles that are neighbors to the current tile we're at
            for (Integer conn : connections) {
                // Fetch the Tile thats connected to the current tile
                Tile connectionTile = graph.get(conn);

                // Get the new 'G' cost by adding the current tile 'costSoFar' to the connecting
                // tile's cost
                float currentCost = current.getCostSoFar() + connectionTile.getCostSoFar();

                // If we found the Berry then calculate costs and return
                if (connectionTile.getNode().equals("End")) {

                    connectionTile.setCostSoFar(currentCost);
                    connectionTile.setEstimatedTotalCost(currentCost);
                    // Set the connection from the current tile to the neighbor tile - this is used
                    // to back track and get the shortest path
                    connectionTile.setConnection(current);
                    // Add the connecting tile to the open queue if its not there
                    if (!open.contains(connectionTile)) {
                        open.add(connectionTile);
                    }
                    // Set current Tile's state to closed
                    current.setState(0);
                    return current;
                }

                // If Berry is not found then keep searching
                // If neighbor tile is 'CLOSED', double check for shorter path else continue
                if (connectionTile.getState() == Tile.Category.CLOSED) {
                    // If a shorter route wasn't found then skip
                    if (connectionTile.getCostSoFar() <= currentCost) {
                        continue;
                    }
                    // Else set state as open
                    connectionTile.setState(1);
                    // Calculate new heuristic and set it
                    newEndCost = connectionTile.getEstimatedTotalCost() - connectionTile.getCostSoFar();

                    // If tile is 'UNVISITED' then calculate heuristic and put it in the open list
                } else if (connectionTile.getState() == Tile.Category.UNVISITED) {
                    // Set Tile to 'OPEN'
                    connectionTile.setState(1);
                    // calculate new heuristic
                    newEndCost = hue.estimatedCost(connectionTile, connectionTile.getCostSoFar());

                } else { // Else tile is 'OPEN'
                    // Compare the 'G' cost, if the route isn't better than skip
                    if (connectionTile.getCostSoFar() <= currentCost) {
                        continue;
                    }
                    // calculate new heuristic
                    newEndCost = connectionTile.getEstimatedTotalCost() - connectionTile.getCostSoFar();

                }

                //Update 'G' value
                connectionTile.setCostSoFar(currentCost);
                // Update 'F' cost with 'H' (pass to heuristic function) and 'G'
                connectionTile.setEstimatedTotalCost(newEndCost + currentCost);
                // Set the Tile connected to the tile we're currently at
                connectionTile.setConnection(current);

                // If tile is not in Open tile then add it
                if (!open.contains(connectionTile)) {
                    open.add(connectionTile);
                }

            }
            // Set current Tile's state to 'CLOSED' then return
            current.setState(0);
            return current;

        }

    }

    /**
     * Helper function that clear the modified graph and reset the 'open' priority queue along with
     * the 'Start' tile's initial values.
     */
    public void resetStart(){
        graph.clear();
        // Reset the graph to the original state the user had it in
        copyHashMap(startingGraph);
        open.clear();
        //Reset stat tile and add to empty priority queue
        start = graph.get((int)initializedStart);
        start.setEstimatedTotalCost(hue.estimatedCost(start, 1));
        start.setConnection(null);
        open.add(start);
    }


    /**
     * Helper function that takes in a graph and copies it to the main 'graph' variable used in the
     * A* algorithm
     * @param oldGraph
     */
    private void copyHashMap(HashMap<Integer, Tile> oldGraph){
        for(Integer tileID : oldGraph.keySet()){
            graph.put(tileID, new Tile(oldGraph.get(tileID)));
        }

    }

    /**
     * Helper function that makes a copy of a graph and stores it for later reference
     * @param oldGraph
     */
    private void setHashMap(HashMap<Integer, Tile> oldGraph){
        startingGraph = new HashMap<Integer, Tile>();
        for(Integer tileID : oldGraph.keySet()){
            startingGraph.put(tileID, new Tile(oldGraph.get(tileID)));
        }

    }

    /**
     * Get the Starting Tile
     * @return
     */
    public Tile getStart() {
        return start;
    }
}
