package com.bugwars.Assignment2.Game2.States;

import com.bugwars.Assignment2.Game2.StateMachine.AntPlayer;
import com.bugwars.Assignment2.Game2.StateMachine.AntPlayerState;
import com.bugwars.Assignment2.Game2.StateMachine.StateManager;

/**
 * This class inherits from 'AntPlayerState' so it has access to everything from this class.
 * This is the starting state for each ant which will get them to search for food.
 */
public class FindFood extends AntPlayerState {

    public FindFood(AntPlayer player, StateManager stateMachine) {
        super(player, stateMachine);

    }

    @Override
    public void Enter() {
        super.Enter();
        //player.movement.wander();
    }

    @Override
    public void Exit() {
        super.Exit();
    }

    @Override
    public void Update() {
        super.Update();
        //DoCheck();
        player.movement.wander();



    }

    @Override
    public void DoCheck() {
        super.DoCheck();
        if(player.checkBerry()){
            System.out.println("found Berry");
            stateMachine.ChangeState(player.findHome);
            return;
        }
        if(player.checkPoison()){
            System.out.println("found death");
            stateMachine.ChangeState(player.foundDeath);
            return;
        }


    }
}
