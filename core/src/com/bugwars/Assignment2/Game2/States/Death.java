package com.bugwars.Assignment2.Game2.States;

import com.bugwars.Assignment2.Game2.StateMachine.AntPlayer;
import com.bugwars.Assignment2.Game2.StateMachine.AntPlayerState;
import com.bugwars.Assignment2.Game2.StateMachine.StateManager;

public class Death extends AntPlayerState {


    public Death(AntPlayer player, StateManager stateMachine) {
        super(player, stateMachine);
    }

    @Override
    public void Enter() {
        super.Enter();
        System.out.println("In death for ant " + player.antID);
        player.dispose();
        Exit();
    }

    @Override
    public void Exit() {
        super.Exit();
        System.out.println("In Death Exit");
    }
}
