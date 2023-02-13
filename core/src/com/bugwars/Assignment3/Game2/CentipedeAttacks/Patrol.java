package com.bugwars.Assignment3.Game2.CentipedeAttacks;

import static com.badlogic.gdx.utils.TimeUtils.millis;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Timer;
import com.bugwars.Assignment3.Game2.AI.States.BossState;
import com.bugwars.Assignment3.Game2.AI.States.StateManager;
import com.bugwars.Objects.Enemy.Centipede;

import java.util.Enumeration;

public class Patrol extends BossState {

    private long attackTime, startTime;
    private int healthFinal, healthMid;
    // Character AI
    private long timerBurstShot, currentTime;
    // Centipede AOE
    private boolean aoeDelay = false;

    public Patrol(Centipede boss, Body spider, StateManager stateMachine) {
        super(boss, spider, stateMachine);
        healthFinal = (int)(boss.getHealth() * 0.5);
        healthMid = (int)(boss.getHealth() * 0.7);
        // Centipede burst shot
        timerBurstShot = millis() + (10*1000);
    }


    @Override
    public void Enter() {
        super.Enter();
        startTime = System.currentTimeMillis();

    }

    @Override
    public void Exit() {
        super.Exit();
    }

    @Override
    public void Update() {
        super.Update();
        boss.moveBoss(spider);
        //Centipede AOE timer and call ******************************************************
        currentTime = millis();
        if(currentTime > timerBurstShot){
            boss.aoeShot2();
            timerBurstShot = millis() + (10*1000);
            aoeDelay=true;
        }else{
            if(aoeDelay == true) { // Stop the centipede from moving so it can fire off its aoe
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        aoeDelay=false;
                    }
                }, 3);
            }
            else{
                //centipedeEnemy.seekTarget(spiderPlayer.getBody().getPosition(), centipedeEnemy.getBody().getPosition());
            }

        }
    }

    @Override
    public void DoCheck() {
        super.DoCheck();
        attackTime = System.currentTimeMillis();
        long getDiff = (attackTime-startTime) *1000; // make milliseconds into seconds
        float healthCheck = boss.getHealth();

        //System.out.println("Check Ys: "+spider.getPosition().y +" , "+ boss.getY() +"Check Xs: "+spider.getPosition().x +" , "+ boss.getX());

        // If health is below a certain percentage introduce new boss attacks
        // Check length of dictionary to see if attacks need to be added
        if (healthCheck <= healthFinal){
            // "Beam" attack added

            //return;
        }
        else if(healthCheck <= healthMid && (boss.attackSelector.get(boss.tail) ==0)){
            // "Tail Swipe" added
            boss.attackSelector.put(boss.tail,boss.attackSelector.get(boss.tail) + 10);
            //return;
        }
        else if(spider.getPosition().y < boss.getY() && spider.getPosition().y > (boss.getY()-100) &&
                spider.getPosition().x > (boss.getX()-40) && spider.getPosition().x < (boss.getX()+40)){
            boss.attackSelector.put(boss.lunge,boss.attackSelector.get(boss.lunge) + 10);
        }
        else{

            //return;
        }

        // Get state to run based on state weight
        int keyValue = 0;
        BossState stateToCall = null;
        Enumeration<BossState> keys = boss.attackSelector.keys();
        while(keys.hasMoreElements()){
            BossState checkState= keys.nextElement();
            if(keyValue < boss.attackSelector.get(checkState)){
                stateToCall = checkState;
                keyValue = boss.attackSelector.get(checkState);
            }

        }

        if(stateToCall != null ){
            stateMachine.ChangeState(stateToCall);
        }

    }
}
