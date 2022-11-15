package com.bugwars.Assignment2.Game1;

import java.util.Comparator;

/**
 * This class will be used to compare nodes and place them in a min-heap, it also calculates the
 * Euclidean distance for the heuristic
 */
public class TileCompare implements Comparator<Tile> {

    public TileCompare(){

    }

    /**
     * Use the 'F' cost of each tile to compare them and place them in the appropriate order in the
     * priority queue
     * @param tile
     * @param t1
     * @return
     */
    @Override
    public int compare(Tile tile, Tile t1) {

        // getEstimatedTotalCost = 'F' cost
        if(tile.getEstimatedTotalCost() > t1.getEstimatedTotalCost()){
            return 1;
        }else if(tile.getEstimatedTotalCost() < t1.getEstimatedTotalCost()){
            return -1;
        }
        return 0;
    }


}
