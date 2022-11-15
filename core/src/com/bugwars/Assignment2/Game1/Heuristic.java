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
        int y = (int)id - (x * 16);
        // Add 32 because each square is 32x32 pixels so this accounts for the distance travelling
        endX = (x) * 32;
        endY = (y) * 32;

    }

    /**
     * This function will calculate the Euclidean distance from the current node to the target
     * node and return the calculated value.
     * Euclidean distance reference:
     * https://stackoverflow.com/questions/16869920/a-heuristic-calculation-with-euclidean-distance
     * @return the distance between the current node and the target node
     */
    public float estimatedCost(Tile currentTile){
        // Use the current node's ID to calculate the x and y coordinates
        float id = currentTile.getID();
        int currX = (int)id/16;
        int currY = ((int)id - (currX * 16));

        // x distance from current node to target node on the x-axis
        // y distance from current node to target node on the y-axis
        float x = abs(endX - currX);
        float y = abs(endY - currY);

        float distance = (float) Math.sqrt((Math.pow(x,2) + Math.pow(y,2)));

        return distance;
    }

}
