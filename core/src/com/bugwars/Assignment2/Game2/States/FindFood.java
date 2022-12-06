package com.bugwars.Assignment2.Game2.States;

import com.bugwars.Assignment2.Game2.StateMachine.AntPlayer;
import com.bugwars.Assignment2.Game2.StateMachine.AntPlayerData;
import com.bugwars.Assignment2.Game2.StateMachine.AntPlayerState;
import com.bugwars.Assignment2.Game2.StateMachine.StateManager;

/**
 * This class inherits from 'AntPlayerState' so it has access to everything from this class.
 * This is the starting state for each ant which will get them to search for food.
 */
public class FindFood extends AntPlayerState {

    public FindFood(AntPlayer player, StateManager stateMachine, AntPlayerData playerData) {
        super(player, stateMachine, playerData);
    }


}
