package com.bugwars.Assignment3.Game2;

import com.bugwars.Objects.Enemy.Centipede2;

public class QLearning {

    private Centipede2 centipede;
    private int score = 0;

    public QLearning(Centipede2 centipede){
        this.centipede = centipede;

    }


    public void reward(){
        if(centipede.getHealth()==0){
            score = -10;
        }
    }
}
