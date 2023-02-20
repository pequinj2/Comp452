package com.bugwars.Assignment3.Game2.CentipedeAttacks;

import com.badlogic.gdx.physics.box2d.Body;
import com.bugwars.Assignment3.Game2.AI.States.BossState;
import com.bugwars.Assignment3.Game2.AI.States.StateManager;
import com.bugwars.Objects.Enemy.Centipede;

public class TailSwipe extends BossState {


    public TailSwipe(Centipede boss, Body spider, StateManager stateMachine) {
        super(boss, spider, stateMachine);
    }

    @Override
    public void Enter() {
        super.Enter();
        boss.bossStopMoving();
        boss.tailAttack(spider);
        boss.playTail();
    }

    @Override
    public void Exit() {
        super.Exit();
        boss.tailBasePosition();

    }

    @Override
    public void Update() {
        super.Update();
        boss.bossStopMoving();
        boss.tailAttack(spider);

    }

    @Override
    public void DoCheck() {
        super.DoCheck();
        if(boss.getRetractTail()){
            // Tail just ran so change the weight
            boss.attackSelector.put(boss.tail,0f);
            stateMachine.ChangeState(boss.patrol);
        }
    }


    @Override
    public void printState() {
        System.out.println("Tail Swipe State");
    }
}
