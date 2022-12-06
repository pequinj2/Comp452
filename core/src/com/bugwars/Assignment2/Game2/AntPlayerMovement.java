package com.bugwars.Assignment2.Game2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class AntPlayerMovement {

    private int currentCellID;
    private int previousCellID;
    private int newCellId;
    private ArrayList<Integer> wayHome;
    private Random randomNum;
    private int maxSize = 15;

    public AntPlayerMovement(int currentCellID){
        this.currentCellID = currentCellID;
        this.previousCellID = currentCellID;
        this.wayHome = new ArrayList<>();
        wayHome.add(currentCellID);
        randomNum = new Random();

    }

    /**
     * Wander steering behavior that is modified to search for a random cell to visit that isn't
     * the previous cell
     */
    public void wander(int currentID){
        currentCellID = currentID;
        int currX = (int)currentCellID/16;
        int currY = ((int)currentCellID - (currX * 16));
        int newPosition = previousCellID;
        List<Integer> numList;

        /**
         * The following will generate a random number that represents the neighbor around the ant
         * at its current position. The 'temp' variable is equal to the 'previousCellID' because we
         * want a cell that ISN'T the one we just came from, so, keep looking until a cell is found.
         */
        while(newPosition == previousCellID) {
            int temp = 0;

            if (currX - 1 < 0) { // Can't do A, B, C
                if (currY - 1 < 0) {
                    // do D, E, F
                    // Generate num between 3 & 5 - includes 3 and 5
                    numList = Arrays.asList(3,4,5);
                    int position = randomNum.nextInt(numList.size());
                    temp = numList.get(position);


                } else if (currY + 1 > maxSize) {
                    // do F, G, H
                    // Generate num between 5 & 7 - includes 5 and 7
                    numList = Arrays.asList(5,6,7);
                    int position = randomNum.nextInt(numList.size());
                    temp = numList.get(position);


                } else {
                    // do D, E, F, G, H
                    // Generate num between 3 & 7 - includes 3 and 7
                    numList = Arrays.asList(3,4,5,6,7);
                    int position = randomNum.nextInt(numList.size());
                    temp = numList.get(position);


                }
            } else if (currX + 1 > maxSize) { // Can't do E, F, G
                if (currY - 1 < 0) {
                    // do B, C, D
                    // Generate num between 1 & 3 - includes 1 and 3
                    numList = Arrays.asList(1,2,3);
                    int position = randomNum.nextInt(numList.size());
                    temp = numList.get(position);


                } else if (currY + 1 > maxSize) {
                    // do A, B, H
                    // Generate num between 0 & 1 or 7 - includes 0 and 1
                    numList = Arrays.asList(0, 1, 7);
                    int position = randomNum.nextInt(numList.size());
                    temp = numList.get(position);


                } else {
                    // do A, B, C, D, H
                    // Generate num between 0 & 3 or 7 - includes 0 and 3
                    numList = Arrays.asList(0, 1, 2, 3, 7);
                    int position = randomNum.nextInt(numList.size());
                    temp = numList.get(position);

                }

            } else if (currY - 1 < 0) {
                // do B, C, D, E, F
                // Generate num between 1 & 5 - includes 1 and 5
                numList = Arrays.asList(1,2,3,4,5);
                int position = randomNum.nextInt(numList.size());
                temp = numList.get(position);


            } else if (currY + 1 > maxSize) {
                // do A, B, F, G, H
                // Generate num between 0 & 1 or 5 & 7 - includes 0, 1, 5, 7
                numList = Arrays.asList(0, 1, 5, 6, 7);
                int position = randomNum.nextInt(numList.size());
                temp = numList.get(position);


            } else {
                // do All
                // Generate num between 0 & 7 - includes 0 and 7
                numList = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7);
                int position = randomNum.nextInt(numList.size());
                temp = numList.get(position);

            }

            //TODO get cell ID and compare to prev
            newPosition = switchCheck(temp, currX, currY);
        }


    }

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
}
