package com.bugwars.Assignment2.Game2.States;

import com.bugwars.Assignment2.Game2.StateMachine.AntPlayer;
import com.bugwars.Assignment2.Game2.StateMachine.AntPlayerState;
import com.bugwars.Assignment2.Game2.StateMachine.StateManager;

/**
 * This class inherits from 'AntPlayerState' so it has access to everything from this class.
 * Part of the Ant FSM, this will use the 'wander' algorithm and look for 'water' and 'poison' tiles
 */
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
            stateMachine.ChangeState(player.findFoodState);
            return;
        }
        if(player.checkPoison()){
            stateMachine.ChangeState(player.foundDeath);
            return;
        }
    }
}
