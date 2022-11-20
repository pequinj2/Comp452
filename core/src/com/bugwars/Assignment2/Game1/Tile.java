package com.bugwars.Assignment2.Game1;

import java.io.Serializable;
import java.util.List;

/**
 * This Tile class is used for create the Tile Map of the game, the user will be able to put a tile
 * on the map to customize the route the "Ant" will take. This class extends the Actor class so
 * it can be used with the Table variable in GenerateGraph.java.
 */
public class Tile implements Serializable {

    private float costSoFar;
    private float estimatedTotalCost;
    private String node = "";
    private List<Integer> list;

    public Tile(int tileID) {
        this.tileID = tileID;
    }


    public enum Category{
        CLOSED, OPEN, UNVISITED
    }
    private Category currentState;
    // Intertwine PriorityQueue and node array pg 229
    private Tile nextTile;
    private String img;
    private int tileID;
    private Tile connection;

    public Tile(int tileID, String img, List<Integer> list, float costSoFar, float estimatedTotalCost){
        this.tileID = tileID;
        this.img = img;
        this.list = list;
        this.costSoFar = costSoFar;
        this.estimatedTotalCost = estimatedTotalCost;
        currentState = Category.UNVISITED;

    }

    public Tile(Tile tile) {
        this.tileID = tile.tileID;
        this.img = tile.img;
        this.list = tile.list;
        this.costSoFar = tile.costSoFar;
        this.estimatedTotalCost = tile.estimatedTotalCost;
        this.currentState = tile.currentState;
        this.node = tile.node;
    }

    public void setStart(){
        node = "Start";
    }

    public void setEnd(){
        node = "End";
    }

    public void setState(int state){
        switch(state){
            case 0:
                currentState = Category.CLOSED;
                break;
            case 1:
                currentState = Category.OPEN;
                break;
            case 2:
                currentState = Category.UNVISITED;
                break;

        }
    }

    public void setCostSoFar(float newCost){
        costSoFar = newCost;

    }

    public void setEstimatedTotalCost(float newEstimatedCost){
        estimatedTotalCost = newEstimatedCost;

    }

    public float getEstimatedTotalCost(){
        return estimatedTotalCost;
    }

    public float getCostSoFar(){
        return costSoFar;

    }

    public float getID(){
        return tileID;
    }

    public String getNode(){
        return node;
    }

    public List<Integer> getConnections(){
        return list;
    }

    public Category getState(){
        return currentState;

    }

    public void setConnection(Tile connectionTile) {
        connection = connectionTile;
    }

    public Tile getConnection(){
        return connection;
    }

}
