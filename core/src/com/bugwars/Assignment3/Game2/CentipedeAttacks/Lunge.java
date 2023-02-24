package com.bugwars.Assignment3.Game2.CentipedeAttacks;

import com.badlogic.gdx.physics.box2d.Body;
import com.bugwars.Assignment3.Game2.AI.States.BossState;
import com.bugwars.Assignment3.Game2.AI.States.StateManager;
import com.bugwars.Objects.Enemy.Centipede;

/**
 * This state will preform a lunge attack at the player if the spider is too close to the
 * Centipede's head
 */
public class Lunge extends BossState {

    private int attackOrReturn = 0;

    public Lunge(Centipede boss, Body spider, StateManager stateMachine) {
        super(boss, spider, stateMachine);
    }

    @Override
    public void Enter() {
        super.Enter();
        boss.lungeAttack();
        boss.playLunge();
    }

    @Override
    public void Exit() {
        super.Exit();
        attackOrReturn = 0;
        boss.attackSelector.put(boss.lunge,0f);

    }

    @Override
    public void Update() {
        super.Update();
        // If boss head has reached the max length its allowed to travel then return to original position
        if (attackOrReturn == 0) {
            boss.lungeAttack();
        }else{
            boss.lungeAttackReturn();
        }

    }

    @Override
    public void DoCheck() {
        super.DoCheck();
        if(boss.getY() <= (310-40)){ // Check if head has reached the end point (note 310 is head starting position)
            attackOrReturn = 1;
        }
        else if(boss.getY() == 310){ // At starting point change states
            stateMachine.ChangeState(boss.patrol);
        }
    }

    @Override
    public void printState() {
        System.out.println("Lunge State");
    }
}
