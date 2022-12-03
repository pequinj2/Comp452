package com.bugwars.Assignment2.Game2;

import com.bugwars.Assignment2.Game2.States.FindFood;

public class AntPlayer {

    private enum GetState{
        SEARCH_FOOD,
        SEARCH_WATER,
        FIND_HOME
    }

    private StateManager currentState;

    public AntPlayer(){
        currentState = new FindFood();

    }

    private void switchStates(){
        /*switch(currentState){
            case SEARCH_FOOD:
            case SEARCH_WATER:
            case FIND_HOME:
            case DIE:
        }*/
    }
}
