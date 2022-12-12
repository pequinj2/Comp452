package com.bugwars.Assignment2.Game2.AntMovement;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Class that handles the movement the ant will take, whether its wandering or looking for its way
 * home once food is found.
 */
public class AntPlayerMovement {

    private int currentCellID;
    private int previousCellID;
    private Random randomNum;
    private int maxSize = 15;
    private HeuristicGame2 hue;

    private List<Integer> numListA = Arrays.asList(3,4,5);
    private List<Integer> numListB = Arrays.asList(5,6,7);
    private List<Integer> numListC = Arrays.asList(3,4,5,6,7);
    private List<Integer> numListD = Arrays.asList(1,2,3);
    private List<Integer> numListE = Arrays.asList(0, 1, 7);
    private List<Integer> numListF = Arrays.asList(0, 1, 2, 3, 7);
    private List<Integer> numListG = Arrays.asList(1,2,3,4,5);
    private List<Integer> numListH = Arrays.asList(0, 1, 5, 6, 7);
    private List<Integer> numListI = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7);


    public AntPlayerMovement(int currentCellID){
        this.currentCellID = currentCellID;
        this.previousCellID = currentCellID;
        randomNum = new Random();
        // when this constructor is called 'currentCellID' is the Ant Hill location
        hue = new HeuristicGame2(currentCellID);

    }

    /**
     * Wander steering behavior that is modified to search for a random cell to visit that isn't
     * the previous cell
     */
    public void wander(){
        //currentCellID = currentID;
        int currX = (int)currentCellID/16;
        int currY = ((int)currentCellID - (currX * 16));
        int newPosition = previousCellID;

        /**
         * The following will generate a random number that represents the neighbor around the ant
         * at its current position. The 'temp' variable is equal to the 'newPosition' because we
         * want a cell that ISN'T the one we just came from, so, keep looking until a cell is found.
         * The 'IF' statements will make sure we aren't looking at cells that are on an edge of the
         * map.
         */
        while(newPosition == previousCellID) {
            int temp = 0;

            if (currX - 1 < 0) { // Can't do A, B, C
                if (currY - 1 < 0) {
                    // do D, E, F
                    // Generate num between 3 & 5 - includes 3 and 5
                    //numList = Arrays.asList(3,4,5);
                    int position = randomNum.nextInt(numListA.size());
                    temp = numListA.get(position);


                } else if (currY + 1 > maxSize) {
                    // do F, G, H
                    // Generate num between 5 & 7 - includes 5 and 7
                    //numList = Arrays.asList(5,6,7);
                    int position = randomNum.nextInt(numListB.size());
                    temp = numListB.get(position);


                } else {
                    // do D, E, F, G, H
                    // Generate num between 3 & 7 - includes 3 and 7
                    //numList = Arrays.asList(3,4,5,6,7);
                    int position = randomNum.nextInt(numListC.size());
                    temp = numListC.get(position);


                }
            } else if (currX + 1 > maxSize) { // Can't do E, F, G
                if (currY - 1 < 0) {
                    // do B, C, D
                    // Generate num between 1 & 3 - includes 1 and 3
                    //numList = Arrays.asList(1,2,3);
                    int position = randomNum.nextInt(numListD.size());
                    temp = numListD.get(position);


                } else if (currY + 1 > maxSize) {
                    // do A, B, H
                    // Generate num between 0 & 1 or 7 - includes 0 and 1
                    //numList = Arrays.asList(0, 1, 7);
                    int position = randomNum.nextInt(numListE.size());
                    temp = numListE.get(position);


                } else {
                    // do A, B, C, D, H
                    // Generate num between 0 & 3 or 7 - includes 0 and 3
                    //numList = Arrays.asList(0, 1, 2, 3, 7);
                    int position = randomNum.nextInt(numListF.size());
                    temp = numListF.get(position);

                }

            } else if (currY - 1 < 0) {
                // do B, C, D, E, F
                // Generate num between 1 & 5 - includes 1 and 5
                //numList = Arrays.asList(1,2,3,4,5);
                int position = randomNum.nextInt(numListG.size());
                temp = numListG.get(position);


            } else if (currY + 1 > maxSize) {
                // do A, B, F, G, H
                // Generate num between 0 & 1 or 5 & 7 - includes 0, 1, 5, 7
                //numList = Arrays.asList(0, 1, 5, 6, 7);
                int position = randomNum.nextInt(numListH.size());
                temp = numListH.get(position);


            } else {
                // do All
                // Generate num between 0 & 7 - includes 0 and 7
                //numList = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7);
                int position = randomNum.nextInt(numListI.size());
                temp = numListI.get(position);

            }

            //TODO get cell ID and compare to prev in 'while'
            newPosition = switchCheck(temp, currX, currY);
        }


        previousCellID = currentCellID;
        currentCellID = newPosition;

    }

    /**
     * Get the cell ID of a spot around the ant
     * @param caseToCheck value generated in 'wander'
     * @param row
     * @param col
     * @return cell ID of a spot around the ant's current position
     */
    public int switchCheck(int caseToCheck, int row, int col){

        int cellID = 0;

        switch(caseToCheck){
            case 0:
                cellID = ((row-1)*16)  + col-1;
                break;
            case 1:
                cellID = ((row-1)*16) + col;
                break;
            case 2:
                cellID = ((row-1)*16) + col+1;
                break;
            case 3:
                cellID = (row*16) + col+1;
                break;
            case 4:
                cellID = ((row+1)*16) + col+1;
                break;
            case 5:
                cellID = ((row+1)*16)+ col;
                break;
            case 6:
                cellID = ((row+1)*16)+ col-1;
                break;
            case 7:
                cellID = (row*16) + col-1;
                break;
        }

        return cellID;

    }

    /**
     * Once the ant has found food, find the way home using the visited cell list 'wayHome'
     *
     */
    public void returnHome(){
        // TODO
        //wayHome currentCellID
        int currX = (int)currentCellID/16;
        int currY = ((int)currentCellID - (currX * 16));
        int newPosition = previousCellID;

        float compareCell = 1000f;
        float compareHue = 0;

        int temp = 0;

            if (currX - 1 < 0) { // Can't do A, B, C
                if (currY - 1 < 0) {
                    // do D, E, F
                    // Generate num between 3 & 5 - includes 3 and 5
                    //numList = Arrays.asList(3,4,5);
                    for(int i=0; i< numListA.size(); i++){
                        //temp = numList.get(i);
                        temp = switchCheck(numListA.get(i), currX, currY);
                        compareHue = hue.estimatedCost(temp,1);
                        if(compareHue < compareCell){
                            newPosition = temp;
                            compareCell = compareHue;
                        }

                    }


                } else if (currY + 1 > maxSize) {
                    // do F, G, H
                    // Generate num between 5 & 7 - includes 5 and 7
                    //numList = Arrays.asList(5,6,7);
                    for(int i=0; i< numListB.size(); i++){
                        //temp = numList.get(i);
                        temp = switchCheck(numListB.get(i), currX, currY);
                        compareHue = hue.estimatedCost(temp,1);
                        if(compareHue < compareCell){
                            newPosition = temp;
                            compareCell = compareHue;
                        }

                    }


                } else {
                    // do D, E, F, G, H
                    // Generate num between 3 & 7 - includes 3 and 7
                    //numList = Arrays.asList(3,4,5,6,7);
                    for(int i=0; i< numListC.size(); i++){
                        //temp = numList.get(i);
                        temp = switchCheck(numListC.get(i), currX, currY);
                        compareHue = hue.estimatedCost(temp,1);
                        if(compareHue < compareCell){
                            newPosition = temp;
                            compareCell = compareHue;
                        }

                    }


                }
            } else if (currX + 1 > maxSize) { // Can't do E, F, G
                if (currY - 1 < 0) {
                    // do B, C, D
                    // Generate num between 1 & 3 - includes 1 and 3
                    //numList = Arrays.asList(1,2,3);
                    for(int i=0; i< numListD.size(); i++){
                        //temp = numList.get(i);
                        temp = switchCheck(numListD.get(i), currX, currY);
                        compareHue = hue.estimatedCost(temp,1);
                        if(compareHue < compareCell){
                            newPosition = temp;
                            compareCell = compareHue;
                        }

                    }


                } else if (currY + 1 > maxSize) {
                    // do A, B, H
                    // Generate num between 0 & 1 or 7 - includes 0 and 1
                    //numList = Arrays.asList(0, 1, 7);
                    for(int i=0; i< numListE.size(); i++){
                        //temp = numList.get(i);
                        temp = switchCheck(numListE.get(i), currX, currY);
                        compareHue = hue.estimatedCost(temp,1);
                        if(compareHue < compareCell){
                            newPosition = temp;
                            compareCell = compareHue;
                        }

                    }


                } else {
                    // do A, B, C, D, H
                    // Generate num between 0 & 3 or 7 - includes 0 and 3
                    //numList = Arrays.asList(0, 1, 2, 3, 7);
                    for(int i=0; i< numListF.size(); i++){
                        //temp = numList.get(i);
                        temp = switchCheck(numListF.get(i), currX, currY);
                        compareHue = hue.estimatedCost(temp,1);
                        if(compareHue < compareCell){
                            newPosition = temp;
                            compareCell = compareHue;
                        }

                    }

                }

            } else if (currY - 1 < 0) {
                // do B, C, D, E, F
                // Generate num between 1 & 5 - includes 1 and 5
                //numList = Arrays.asList(1,2,3,4,5);
                for(int i=0; i< numListG.size(); i++){
                    //temp = numList.get(i);
                    temp = switchCheck(numListG.get(i), currX, currY);
                    compareHue = hue.estimatedCost(temp,1);
                    if(compareHue < compareCell){
                        newPosition = temp;
                        compareCell = compareHue;
                    }

                }


            } else if (currY + 1 > maxSize) {
                // do A, B, F, G, H
                // Generate num between 0 & 1 or 5 & 7 - includes 0, 1, 5, 7
                //numList = Arrays.asList(0, 1, 5, 6, 7);
                for(int i=0; i< numListH.size(); i++){
                    //temp = numList.get(i);
                    temp = switchCheck(numListH.get(i), currX, currY);
                    compareHue = hue.estimatedCost(temp,1);
                    if(compareHue < compareCell){
                        newPosition = temp;
                        compareCell = compareHue;
                    }

                }


            } else {
                // do All
                // Generate num between 0 & 7 - includes 0 and 7
                //numList = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7);
                System.out.println("Look for home?");
                for(int i=0; i< numListI.size(); i++){

                    temp = switchCheck(numListI.get(i), currX, currY);
                    compareHue = hue.estimatedCost(temp,1);
                    if(compareHue < compareCell){
                        newPosition = temp;
                        compareCell = compareHue;
                    }

                }
                System.out.println("Go Home: " + newPosition);

            }


        previousCellID = currentCellID;
        currentCellID = newPosition;



    }


    public int getCurrentCellId(){
        return currentCellID;
    }

    public int getPreviousCellID(){
        return previousCellID;
    }

}
