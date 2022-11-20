package com.bugwars.Assignment2.Game1;

import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;

public class PathFindingAStar {

    private HashMap<Integer, Tile> graph;
    private static HashMap<Integer, Tile> startingGraph;
    private Tile start, end, current, noGoal;
    private Heuristic hue;

    private PriorityQueue<Tile> open;
    private RunMap simulation;
    private float newEndCost;
    private float initializedStart;

    public PathFindingAStar(HashMap<Integer, Tile> graph, Tile start, Tile end, Heuristic hue){
        this.graph = graph;
        setHashMap(graph);
        this.start = start;
        initializedStart = start.getID();
        this.end = end;
        this.hue = hue;

        open = new PriorityQueue<>(256, new TileCompare());
        start.setEstimatedTotalCost(hue.estimatedCost(start, 1));
        start.setConnection(null);
        open.add(start);

        noGoal = new Tile(-1);

    }

    public Tile findPath(){

        if(open.size() == 0){
            System.out.println("no more nodes :(");
            return noGoal;
        }
        else {
            current = open.poll();
            System.out.println("Current node: " + current.getID());

            // Get the connections attached to the current Tile
            List<Integer> connections = current.getConnections();
            System.out.println("Current node connections: " + connections);
            float endCost = current.getEstimatedTotalCost();
            System.out.println("Current node F cost: " + endCost);

            Tile connectionTile = current;
            for (Integer conn : connections) {
                connectionTile = graph.get(conn);
                System.out.println("This is the connection: " + conn);
                // Get the 'F' cost
                endCost = connectionTile.getEstimatedTotalCost();
                // Get previous from Tiles cost and cost to the connection tile
                // This is the new 'G' cost
                float currentCost = current.getCostSoFar() + connectionTile.getCostSoFar();
                System.out.println("This is the new 'G' cost: " + currentCost);

                if (connectionTile.getNode().equals("End")) {
                    System.out.println("at end return");
                    connectionTile.setCostSoFar(currentCost);
                    connectionTile.setEstimatedTotalCost(currentCost);
                    connectionTile.setConnection(current);
                    if (!open.contains(connectionTile)) {
                        //System.out.println("*** ADD TO QUEUE ***");
                        open.add(connectionTile);
                    }
                    current.setState(0);
                    return current;
                }

                if (connectionTile.getState() == Tile.Category.CLOSED) {
                    System.out.println("*** In CLOSED ***");
                    // If a shorter route wasn't found then skip
                    if (connectionTile.getCostSoFar() <= currentCost) {
                        continue;
                    }
                    // Else put it back on the open list
                    connectionTile.setState(1);
                    // Calculate new heuristic and set it
                    newEndCost = connectionTile.getEstimatedTotalCost() - connectionTile.getCostSoFar();
                    //connectionTile.setEstimatedTotalCost(newEndCost);
                } else if (connectionTile.getState() == Tile.Category.UNVISITED) {
                    System.out.println("*** In UNVISITED ***");
                    // Set Tile to 'OPEN'
                    connectionTile.setState(1);
                    // calculate estimated cost to end node and cost so far - update Tile
                    // Update 'F' cost
                    //connectionTile.setEstimatedTotalCost(hue.estimatedCost(connectionTile) + currentCost);
                    newEndCost = hue.estimatedCost(connectionTile, connectionTile.getCostSoFar());
                    //System.out.println("This is the new 'F' cost: " + connectionTile.getEstimatedTotalCost());
                    //System.out.println("This is the new 'H' cost: " + hue.estimatedCost(connectionTile, connectionTile.getCostSoFar()));
                    //connectionTile.setCostSoFar(currentCost);


                } else { // Else tile is 'OPEN'
                    System.out.println("*** In OPEN ***");
                    // Compare the 'G' cost, if the route isn't better than skip
                    //.out.println("Old G cost in OPEN : " + connectionTile.getCostSoFar() );
                    if (connectionTile.getCostSoFar() >= currentCost) {
                        continue;
                    }
                    newEndCost = connectionTile.getEstimatedTotalCost() - connectionTile.getCostSoFar();
                    // Calculate new heuristic and set it
                    //float newEndCost = connectionTile.getEstimatedTotalCost() - connectionTile.getCostSoFar();
                    //connectionTile.setEstimatedTotalCost(newEndCost);
                    //System.out.println("Calculate new heuristic and set it: " + newEndCost);

                }

                System.out.println("*** UPDATE ALL ***");
                //Update 'G' value
                connectionTile.setCostSoFar(currentCost);
                // Update 'F' cost with 'H' (pass to heuristic function) and 'G'
                System.out.println("This is the new 'H' cost: " + newEndCost);
                connectionTile.setEstimatedTotalCost(newEndCost + currentCost);
                // Set the Tile connected to the tile we're currently at
                connectionTile.setConnection(current);
                System.out.println("This is the new 'F' cost: " + connectionTile.getEstimatedTotalCost());
                //System.out.println("This is the new 'H' cost: " + hue.estimatedCost(connectionTile, connectionTile.getCostSoFar()));

                if (!open.contains(connectionTile)) {
                    System.out.println("*** ADD TO QUEUE ***");
                    open.add(connectionTile);
                }

            }

            current.setState(0);
            return current;

        }

    }

     /*public void render(){
        Gdx.gl.glClearColor(0, 0, 0, 1); // Clear the previous screen of anything
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        simulation.render(Gdx.graphics.getDeltaTime());
    }*/


    public void resetStart(){
        graph.clear();
        copyHashMap(startingGraph);
        System.out.println(graph.size());
        open.clear();
        start = graph.get((int)initializedStart);
        start.setEstimatedTotalCost(hue.estimatedCost(start, 1));
        start.setConnection(null);
        System.out.println(start.getID());
        open.add(start);
    }



    private void copyHashMap(HashMap<Integer, Tile> oldGraph){
        //HashMap<Integer, Tile> newGraph = new HashMap<Integer, Tile>();
        for(Integer tileID : oldGraph.keySet()){
            graph.put(tileID, new Tile(oldGraph.get(tileID)));
        }

    }

    private void setHashMap(HashMap<Integer, Tile> oldGraph){
        startingGraph = new HashMap<Integer, Tile>();
        for(Integer tileID : oldGraph.keySet()){
            startingGraph.put(tileID, new Tile(oldGraph.get(tileID)));
        }

    }
    private void dispose(){

    }

    public Tile getStart() {
        return start;
    }
}
