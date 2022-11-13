package com.bugwars.Assignment2.Game1;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

import java.util.List;

/**
 * This Tile class is used for create the Tile Map of the game, the user will be able to put a tile
 * on the map to customize the route the "Ant" will take. This class extends the Actor class so
 * it can be used with the Table variable in GenerateGraph.java.
 */
public class Tile extends Button {

    private float costSoFar;
    private float estimatedTotalCost;
    private String node = "";
    private List<Integer> list;
    private enum Category{
        CLOSED, OPEN, UNVISITED
    }
    private Category currentState;
    // Intertwine PriorityQueue and node array pg 229
    private Tile nextTile;
    private String img;
    private int tileID;

    public Tile(int tileID, String img, List<Integer> list, float costSoFar, float estimatedTotalCost){
        this.tileID = tileID;
        this.img = img;
        this.list = list;
        this.costSoFar = costSoFar;
        this.estimatedTotalCost = estimatedTotalCost;
        currentState = Category.OPEN;

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

    public float getID(){
        return tileID;
    }

}
