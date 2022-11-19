package com.bugwars.Assignment2.Game1;

import com.badlogic.gdx.scenes.scene2d.ui.Button;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * This class will generate the graph, a priority queue, of the map the user had just completed.
 * It will go through the button tiles and create Tile objects adding them to the queue as well as
 * finding the Start and End Tiles. It will also generated a list of connected to tiles to each tile.
 */
public class GenerateGraph {

    private HashMap<Integer, Tile> graph = new HashMap<Integer, Tile>();
    private int maxSize = 15;
    private Button[] btnTiles;
    private Tile newTile, startTile, endTile;
    private PriorityQueue<Tile> queue;
    private Map<String, Integer> tileCosts = new HashMap<String, Integer>() {{
        put("Berry", 0);
        put("Start", 0);
        put("Dirt_Tile", 1);
        put("Grass", 3);
        put("Swamp_Tile", 4);
        put("Rock_Tile", -1);
    }};

    public GenerateGraph(Button[] btnTiles){
        this.btnTiles = btnTiles;
        /**
         * Implement the priority queue of our nodes, because the map is a 16x16 graph we need a
         * space of 256. Use TileCompare to sort the nodes properly in a min-Heap structure.
         */
        queue = new PriorityQueue<>(256, new TileCompare());
        for(int j = 0; j<16; j++ ) {
            for (int i = 0; i < 16; i++) {
                String currStyle = "";
                Button tile = btnTiles[(i*16) + j];

                // Get Tile image
                if(tile.getStyle().up == null){
                    currStyle = tile.getStyle().checked.toString();

                }else{
                    currStyle = tile.getStyle().up.toString();
                }

                // Rock obstacle found, no connections or cost, add tile and go to next button in list
                if(currStyle != "Rock_Tile"){
                    // Get the connections of the current tile (node), these are a simple integer
                    // list that represent a Tile's ID
                    List<Integer> list = getConnections(i,j);
                    // Get the cost of the node
                    float costSoFar = tileCosts.get(currStyle);
                    // Get the estimate cost
                    float estimatedCost = 0;
                    // Generate new Tile object and add it to the priority queue
                    newTile = new Tile((i*16) + j, currStyle, list, costSoFar, estimatedCost);
                    graph.put((i*16) + j, newTile);
                    queue.add(newTile);

                }
                // Check if we found the start and end (Berry) tiles
                if(currStyle == "Start"){
                    startTile = newTile;
                    startTile.setStart();
                }
                if(currStyle == "Berry"){
                    endTile = newTile;
                    endTile.setEnd();
                }


            }
        }
    }

    /**
     * The helper function below will create a list of connection nodes from the point that was given
     * to the function. Node = (row, col) which represents the button that was used in the original
     * tile map the user created.
     * A list of integers is returned that represents tiles connected to the 'From Tile'. This list
     * is made to exclude tiles that represent rocks since the ant cant go there.
     * @param row
     * @param col
     * @return an array of the connecting Tiles (nodes)
     */
    private List<Integer> getConnections(int row, int col){

        List<Integer> connections = new ArrayList<>();


        if(row-1 < 0){ // Can't do A, B, C
            if(col-1 <0){
                // do D, E, F

                connections.add(switchCheck("D", row, col));
                connections.add(switchCheck("E", row, col));
                connections.add(switchCheck("F", row, col));

            }else if(col+1 > maxSize){
                // do F, G, H

                connections.add(switchCheck("F", row, col));
                connections.add(switchCheck("G", row, col));
                connections.add(switchCheck("H", row, col));

            }else{
                // do D, E, F, G, H
                connections.add(switchCheck("D", row, col));
                connections.add(switchCheck("E", row, col));
                connections.add(switchCheck("F", row, col));
                connections.add(switchCheck("G", row, col));
                connections.add(switchCheck("H", row, col));

            }
        }else if(row+1 > maxSize){ // Can't do E, F, G
            if(col-1 <0){
                // do B, C, D

                connections.add(switchCheck("B", row, col));
                connections.add(switchCheck("C", row, col));
                connections.add(switchCheck("D", row, col));



            }else if(col+1 > maxSize){
                // do A, B, H

                connections.add(switchCheck("A", row, col));
                connections.add(switchCheck("B", row, col));
                connections.add(switchCheck("H", row, col));

            }else{
                // do A, B, C, D, H

                connections.add(switchCheck("A", row, col));
                connections.add(switchCheck("B", row, col));
                connections.add(switchCheck("C", row, col));
                connections.add(switchCheck("D", row, col));
                connections.add(switchCheck("H", row, col));
            }

        }else if(col-1 <0){
            // do B, C, D, E, F

            connections.add(switchCheck("B", row, col));
            connections.add(switchCheck("C", row, col));
            connections.add(switchCheck("D", row, col));
            connections.add(switchCheck("E", row, col));
            connections.add(switchCheck("F", row, col));

        }
        else if(col+1 > maxSize){
            // do A, B, F, G, H
            connections.add(switchCheck("A", row, col));
            connections.add(switchCheck("B", row, col));
            connections.add(switchCheck("F", row, col));
            connections.add(switchCheck("G", row, col));
            connections.add(switchCheck("H", row, col));

        }
        else{
            // do All
            connections.add(switchCheck("A", row, col));
            connections.add(switchCheck("B", row, col));
            connections.add(switchCheck("C", row, col));
            connections.add(switchCheck("D", row, col));
            connections.add(switchCheck("E", row, col));
            connections.add(switchCheck("F", row, col));
            connections.add(switchCheck("G", row, col));
            connections.add(switchCheck("H", row, col));
        }

        // Remove any negative integers from the list
        connections.removeIf(val -> val < 0);

        // return connection list
        return connections;

    }

    private String getButtonStyle(Button tile){
        String currStyle = "";

        // Get Tile image
        if(tile.getStyle().up == null){
            currStyle = tile.getStyle().checked.toString();
        }else{
            currStyle = tile.getStyle().up.toString();
        }

        return currStyle;
    }

    /**
     * Below is a list of the areas in the map (abstract matrix) that the helper function will look
     * at, they're lettered as to unclog the amount of comments in the code.
     * A) (-1, -1)
     * B) (-1, ~)
     * C) (-1, +1)
     * D) (~, +1)
     * E) (+1, +1)
     * F) (+1, ~)
     * G) (+1, -1)
     * H) (~, -1)
     * @param letter
     * @param row
     * @param col
     * @return
     */
    private int switchCheck(String letter, int row, int col){

        Button tile;
        String style = "";
        int tileID = 0;

        switch (letter){
            case "A":
                tile = btnTiles[((row-1)*16) + col-1];
                style = getButtonStyle(tile);
                if(style.equals("Rock_Tile")){
                    tileID = -1;
                    break;
                }
                tileID = ((row-1)*16)  + col-1;
                break;
            case "B":
                tile = btnTiles[((row-1)*16) + col];
                style = getButtonStyle(tile);
                if(style.equals("Rock_Tile")){
                    tileID = -1;
                    break;
                }
                tileID = ((row-1)*16) + col;
                break;
            case "C":
                tile = btnTiles[((row-1)*16) + col+1];
                style = getButtonStyle(tile);
                if(style.equals("Rock_Tile")){
                    tileID = -1;
                    break;
                }
                tileID = ((row-1)*16) + col+1;
                break;
            case "D":
                tile = btnTiles[(row*16) + col+1];
                style = getButtonStyle(tile);
                if(style.equals("Rock_Tile")){
                    tileID = -1;
                    break;
                }
                tileID = (row*16) + col+1;
                break;
            case "E":
                tile = btnTiles[((row+1)*16) + col+1];
                style = getButtonStyle(tile);
                if(style.equals("Rock_Tile")){
                    tileID = -1;
                    break;
                }
                tileID = ((row+1)*16) + col+1;
                break;
            case "F":
                tile = btnTiles[((row+1)*16)+ col];
                style = getButtonStyle(tile);
                if(style.equals("Rock_Tile")){
                    tileID = -1;
                    break;
                }
                tileID = ((row+1)*16)+ col;
                break;
            case "G":
                tile = btnTiles[((row+1)*16)+ col-1];
                style = getButtonStyle(tile);
                if(style.equals("Rock_Tile")){
                    tileID = -1;
                    break;
                }
                tileID = ((row+1)*16)+ col-1;
                break;
            case "H":
                tile = btnTiles[(row*16) + col-1];
                style = getButtonStyle(tile);
                if(style.equals("Rock_Tile")){
                    tileID = -1;
                    break;
                }
                tileID = (row*16) + col-1;
                break;
        }

        return tileID;
    }


    public HashMap<Integer, Tile> getQueue(){
        return graph;
    }

    public Tile getStartTile(){
        return startTile;
    }

    public Tile getEndTile(){
        return endTile;
    }

    public void setHeuristics(Heuristic hue) {
        for(Tile tile : graph.values()){
            tile.setEstimatedTotalCost(hue.estimatedCost(tile, tile.getCostSoFar()));

        }

    }
}
