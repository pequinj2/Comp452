package com.bugwars.Assignment2.Game2.States;

import com.bugwars.Assignment2.Game2.StateMachine.AntPlayer;
import com.bugwars.Assignment2.Game2.StateMachine.AntPlayerState;
import com.bugwars.Assignment2.Game2.StateMachine.StateManager;

public class FindHome extends AntPlayerState {

    public FindHome(AntPlayer player, StateManager stateMachine) {
        super(player, stateMachine);
    }

    @Override
    public void Enter() {
        super.Enter();

    }

    @Override
    public void Exit() {
        //super.Exit();
    }

    @Override
    public void Update() {
        super.Update();
        //DoCheck();
        player.movement.returnHome();
    }

    @Override
    public void DoCheck() {
        super.DoCheck();
        System.out.println("In Home State");
        if(player.checkHome()){
            System.out.println("Found home, leaving, ant ID = " + player.antID);
            stateMachine.ChangeState(player.findWater);
            return;
        }


    }
}
