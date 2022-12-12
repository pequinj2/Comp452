package com.bugwars.Assignment2.Game2.States;

import com.bugwars.Assignment2.Game2.StateMachine.AntPlayer;
import com.bugwars.Assignment2.Game2.StateMachine.AntPlayerState;
import com.bugwars.Assignment2.Game2.StateMachine.StateManager;

public class FindWater extends AntPlayerState {


    public FindWater(AntPlayer player, StateManager stateMachine) {
        super(player, stateMachine);
    }

    @Override
    public void Enter() {
        super.Enter();
    }

    @Override
    public void Exit() {
        super.Exit();
    }

    @Override
    public void Update() {
        super.Update();
        player.movement.wander();
    }

    @Override
    public void DoCheck() {
        super.DoCheck();
        if(player.checkWater()){
            System.out.println("found Water");
            stateMachine.ChangeState(player.findFoodState);
            return;
        }
        if(player.checkPoison()){
            System.out.println("found death");
            stateMachine.ChangeState(player.foundDeath);
            return;
        }
    }
}
