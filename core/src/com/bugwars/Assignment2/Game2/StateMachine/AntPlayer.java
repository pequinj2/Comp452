package com.bugwars.Assignment2.Game2.StateMachine;

import com.bugwars.Assignment2.Game2.States.FindFood;

public class AntPlayer {

    // https://www.youtube.com/watch?v=OjreMoAG9Ec
    private StateManager stateManager;
    private AntPlayerState currentState;
    public FindFood findFoodState;

    private AntPlayerData playerData;

    /**
     * When the ant is created, initialize the state machine
     */
    public void Awake(){
        this.stateManager = new StateManager();
        findFoodState = new FindFood(this, stateManager, playerData );
        Start();

    }

    private void Start(){

        stateManager.Initialize(findFoodState);

    }

    private void Update(){
        currentState = stateManager.getCurrentState();
        currentState.Update();
    }

    public StateManager getStateManager() {
        return stateManager;
    }

    public void setStateManager(StateManager stateManager) {
        this.stateManager = stateManager;
    }

    public void setFindFoodState(FindFood findFoodState) {
        this.findFoodState = findFoodState;
    }

    public FindFood getFindFoodState() {
        return findFoodState;
    }
}
