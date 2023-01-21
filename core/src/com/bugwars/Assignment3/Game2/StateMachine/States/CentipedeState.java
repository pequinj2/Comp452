package com.bugwars.Assignment3.Game2.StateMachine.States;


import com.bugwars.Objects.Enemy.Centipede;

/**
 * Parent class of the different states the Centipede boss can be in
 */
public class CentipedeState {
    protected Centipede boss;
    protected StateManager stateMachine;

    public CentipedeState(Centipede boss, StateManager stateMachine){
        this.boss = boss;
        this.stateMachine = stateMachine;

    }

    /**
     * Enter gets called when we enter a specific state
     */
    public void Enter(){
        //DoCheck();

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
