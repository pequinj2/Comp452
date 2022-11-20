package com.bugwars.Assignment2.Game1;

import static java.lang.Math.abs;

/**
 * This class will find and return the Heuristic value from a node to the set End Node
 * f(n) = g(n) + h(n)
 *
 * f(n) is the potential distance between start and target node - n=current
 * g(n) is the distance between the start and current node - n=current
 * h(n) is the estimated distance between the current node and the target node - n=current
 *
 * h(n) is the heuristic
 *
 * We want to minimize f(n) to find the shortest path
 */
public class Heuristic {

    private float endX, endY;

    public Heuristic(Tile end){
        float id = end.getID();
        int x = (int)id/16;
        int y = ((int)id - (x * 16));

        // Translate the extracted x and y coordinates
        // Multiply by 32 as this is the pixel size and add 16 as to signify the center of the tile
        endX = x*32+16;
        endY = y*32+16;

    }

    /**
     * This function will calculate the Euclidean distance from the current node to the target
     * node and return the calculated value.
     * Chebyshev distance reference:
     * https://www.reddit.com/r/roguelikedev/comments/59u44j/warning_a_and_manhattan_distance/
     * @return the distance between the current node and the target node
     */
    public float estimatedCost(Tile currentTile, float cost){
        // Use the current node's ID to calculate the x and y coordinates
        float id = currentTile.getID();
        int currX = (int)id/16;
        int currY = ((int)id - (currX * 16));

        // x distance from current node to target node on the x-axis
        // y distance from current node to target node on the y-axis
        // Translate the extracted x and y coordinates
        // Multiply by 32 as this is the pixel size and add 16 as to signify the center of the tile
        float x = abs(endX - (currX*32 +16));
        float y = abs(endY - (currY*32+16));

        // Multiply by the cost of the tile because we're using a weighted graph
        float distance = cost * Math.max(x,y);

        return distance;
    }

}
