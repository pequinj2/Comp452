package com.bugwars.Assignment3.Game2.StateMachine.States;


/**
 * Holds a variable to our current state and a function to change current state
 */
public class StateManager {

        private CentipedeState currentState;

        /**
         * Initialize the starting state for an ant
         * @param startingState
         */
        public void Initialize(CentipedeState startingState){
            this.currentState = startingState;
            currentState.Enter();
        }

        /**
         * Change the state of the ant
         * @param newState
         */
        public void ChangeState(CentipedeState newState){
            currentState.Exit();
            currentState = newState;
            currentState.Enter();

        }

        public CentipedeState getCurrentState (){
            return currentState;
        }
}
