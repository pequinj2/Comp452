package com.bugwars.Assignment2.Game1;

import java.util.Comparator;

/**
 * This class will be used to compare nodes and place them in a min-heap, it also calculates the
 * Euclidean distance for the heuristic
 */
public class TileCompare implements Comparator<Tile> {

    public TileCompare(){

    }

    @Override
    public int compare(Tile tile, Tile t1) {

        if(tile.getEstimatedTotalCost() > t1.getEstimatedTotalCost()){
            return 1;
        }else if(tile.getEstimatedTotalCost() < t1.getEstimatedTotalCost()){
            return -1;
        }
        return 0;
    }


}
