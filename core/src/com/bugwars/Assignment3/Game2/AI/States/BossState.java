package com.bugwars.Assignment3.Game2.AI.States;

import com.badlogic.gdx.physics.box2d.Body;
import com.bugwars.Objects.Enemy.Centipede;

/**
 * https://refactoring.guru/design-patterns/state/java/example
 */
public abstract class BossState {

    protected Centipede boss;
    protected Body spider;
    protected StateManager stateMachine;

    public BossState(Centipede boss, Body spider, StateManager stateMachine){
        this.boss = boss;
        this.stateMachine = stateMachine;
        this.spider = spider;

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
