package com.bugwars.Assignment2.Game1;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

/**
 * This Tile class is used for create the Tile Map of the game, the user will be able to put a tile
 * on the map to customize the route the "Ant" will take. This class extends the Actor class so
 * it can be used with the Table variable in TileMap.java.
 */
public class Tile extends Button {

    private float costSoFar;
    private float estimatedTotalCost;
    private String node = "Start";
    private float Connection;
    private enum Category{
        CLOSED, OPEN, UNVISITED
    }
    // Intertwine PriorityQueue and node array pg 229
    private Tile nextTile;
    private Drawable img;

    public Tile(Drawable img){
        this.img = img;

    }

}
