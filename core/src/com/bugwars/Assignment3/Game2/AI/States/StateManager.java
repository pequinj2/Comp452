package com.bugwars.Assignment3.Game2.AI.States;

/**
 * Holds a variable to our current state and a function to change current state
 */
public class StateManager {

        private BossState currentState;

        /**
         * Initialize the starting state for an ant
         * @param startingState
         */
        public void Initialize(BossState startingState){
            this.currentState = startingState;
            currentState.Enter();
        }

        /**
         * Change the state of the ant
         * @param newState
         */
        public void ChangeState(BossState newState){
            currentState.Exit();
            currentState = newState;
            currentState.Enter();

        }

        public BossState getCurrentState (){
            return currentState;
        }




}
