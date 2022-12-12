package com.bugwars.Assignment2.Game2.StateMachine;

/**
 * Parent class of the ant's different states (FindFood, FindHome and FindWater).
 */
public class AntPlayerState {

    protected AntPlayer player;
    protected StateManager stateMachine;

    public AntPlayerState(AntPlayer player, StateManager stateMachine){
        this.player = player;
        this.stateMachine = stateMachine;

    }

    /**
     * Enter gets called when we enter a specific state
     */
    public void Enter(){
        //DoCheck();
        //System.out.println("Finding food");
    }

    /**
     * Gets called when we leave a state
     */
    public void Exit(){

    }

    /**
     * Gets called every frame
     */
    public void Update(){
        DoCheck();
    }

    /**
     * Check criteria if states need to be changed
     */
    public void DoCheck(){

    }
}
