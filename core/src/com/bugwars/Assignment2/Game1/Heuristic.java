package com.bugwars.Assignment2.Game1;

public class Heuristic {
    /**
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

    private Tile end;

    public Heuristic(Tile end){
        this.end = end;

    }

    /**
     * This function will calculate the Euclidean distance from the current node to the target
     * node and return the calculated value.
     * @param x distance from current node to target node on the x-axis
     * @param y distance from current node to target node on the y-axis
     * @return the distance between the current node and the target node
     */
    private float estimatedCost(float x, float y){
        float distance = (float) Math.sqrt((Math.pow(x,2) + Math.pow(y,2)));

        return distance;
    }

    private void costBetweenNodes(){

    }
}
