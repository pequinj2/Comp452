package com.bugwars.Assignment2.Game2.StateMachine;

/**
 * Holds a variable to our current state and a function to change current state
 */
public class StateManager {

    private AntPlayerState currentState;

    /**
     * Initialize the starting state for an ant
     * @param startingState
     */
    public void Initialize(AntPlayerState startingState){
        this.currentState = startingState;
        currentState.Enter();
    }

    /**
     * Change the state of the ant
     * @param newState
     */
    public void ChangeState(AntPlayerState newState){
        currentState.Exit();
        currentState = newState;
        currentState.Enter();

    }

    public AntPlayerState getCurrentState (){
        return currentState;
    }


}
