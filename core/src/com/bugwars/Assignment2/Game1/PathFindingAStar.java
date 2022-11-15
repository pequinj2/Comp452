package com.bugwars.Assignment2.Game1;

import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;

public class PathFindingAStar {

    private HashMap<Integer, Tile> graph;
    private Tile start, end, current;
    private Heuristic hue;

    private PriorityQueue<Tile> open;

    public PathFindingAStar(HashMap<Integer, Tile> graph, Tile start, Tile end, Heuristic hue){
        this.graph = graph;
        this.start = start;
        this.end = end;
        this.hue = hue;

        open = new PriorityQueue<>(256, new TileCompare());
        start.setEstimatedTotalCost(hue.estimatedCost(start));
        start.setConnection(null);
        open.add(start);

        findPath();

    }

    private void findPath(){


        while(!open.isEmpty()){
            System.out.println("Start while, size of open: " + open.size());
            current = open.poll();
            System.out.println("Current node: " + current.getID());

            // Get the connections attached to the current Tile
            List<Integer> connections = current.getConnections();
            System.out.println("Current node connections: " + connections);

            Tile connectionTile = current;
            for(Integer conn:connections){
                connectionTile= graph.get(conn);
                System.out.println("This is the connection: " + conn);
                // Get the 'F' cost
                float endCost = connectionTile.getEstimatedTotalCost();
                // Get previous from Tiles cost and cost to the connection tile
                // This is the new 'G' cost
                float currentCost = current.getCostSoFar() + connectionTile.getCostSoFar();
                System.out.println("This is the new 'G' cost: " + currentCost);

                if(connectionTile.getState() == Tile.Category.CLOSED){
                    // If a shorter route wasn't found then skip
                    if(connectionTile.getCostSoFar() <= currentCost){
                        continue;
                    }
                    // Else put it back on the open list
                    connectionTile.setState(1);
                    // Calculate new heuristic and set it
                    float newEndCost = connectionTile.getEstimatedTotalCost() - connectionTile.getCostSoFar();
                    connectionTile.setEstimatedTotalCost(newEndCost);
                }
                else if(connectionTile.getState() == Tile.Category.UNVISITED){
                    // Set Tile to 'OPEN'
                    connectionTile.setState(1);
                    // calculate estimated cost to end node and cost so far - update Tile
                    // Update 'F' cost
                    connectionTile.setEstimatedTotalCost(hue.estimatedCost(connectionTile) + currentCost);
                    System.out.println("This is the new 'F' cost: " + connectionTile.getEstimatedTotalCost());
                    System.out.println("This is the new 'H' cost: " + hue.estimatedCost(connectionTile));
                    connectionTile.setCostSoFar(currentCost);
                    open.add(connectionTile);

                }else{ // Else tile is 'OPEN'
                    // Compare the 'G' cost, if the route isn't better than skip
                    if(connectionTile.getCostSoFar() <= currentCost){
                        continue;
                    }
                    // Calculate new heuristic and set it
                    float newEndCost = connectionTile.getEstimatedTotalCost() - connectionTile.getCostSoFar();
                    connectionTile.setEstimatedTotalCost(newEndCost);
                    System.out.println("Calculate new heuristic and set it: " + newEndCost);

                }

                connectionTile.setConnection(current);

            }
            if(current.getNode().equals("End")){
                break;
            }


        }
        if(!(current.getNode().equals("End"))){
            System.out.println("End node could not be reached :(");
        }
        else{
            printOut();
        }

    }


    private void printOut(){

        System.out.println(current.getID());
        while(!(current.getNode().equals("Start"))){
            Tile getConnectingNode = current.getConnection();
            System.out.println(getConnectingNode.getID());
            current = getConnectingNode;
        }
    }

}
