package com.bugwars.Assignment3.Game2.CentipedeAttacks;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.bugwars.Assignment3.Game2.AI.States.BossState;
import com.bugwars.Assignment3.Game2.AI.States.StateManager;
import com.bugwars.Objects.Enemy.Centipede;

public class Beam extends BossState {

    public Beam(Centipede boss, Body spider, StateManager stateMachine) {
        super(boss, spider, stateMachine);
    }

    @Override
    public void Enter() {
        super.Enter();
        boss.beamObj.readyBeam(boss.getBody());
        boss.getBody().setLinearVelocity(new Vector2(0,0));
    }

    @Override
    public void Exit() {
        super.Exit();
        boss.beamObj.resetHeight();
        boss.attackSelector.put(boss.beam,4f);
        boss.beamObj.beamRunning = false;
    }

    @Override
    public void Update() {
        super.Update();
        boss.beamObj.moveBeam();
    }

    @Override
    public void DoCheck() {
        super.DoCheck();
        // Check if beam has hit the spider
        boss.beamObj.checkCollision(spider);
        // Beam has reached the end of the map - reset and go back to patrol state
        if(boss.beamObj.getY() <= 0){
            stateMachine.ChangeState(boss.patrol);
        }
    }


}
