package com.bugwars.Assignment3.Game2.CentipedeAttacks;

import static com.badlogic.gdx.utils.TimeUtils.millis;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Timer;
import com.bugwars.Assignment3.Game2.AI.States.BossState;
import com.bugwars.Assignment3.Game2.AI.States.StateManager;
import com.bugwars.Objects.Enemy.Centipede;
import java.util.Enumeration;

/**
 * This state is the base state for the boss, this state will follow the spider player
 * around and will first off a Burst shot (AOE) at the user. This state will also decide
 * which state to switch to next.
 */
public class Patrol extends BossState {

    private int healthFinal, healthMid;
    // Character AI
    private long timerBurstShot, currentTime;
    // Centipede AOE
    private boolean aoeDelay = false;

    public Patrol(Centipede boss, Body spider, StateManager stateMachine) {
        super(boss, spider, stateMachine);
        // These health values are used to add attacks to the Centipede' attack pool
        healthFinal = (int)(boss.getHealth() * 0.5);
        healthMid = (int)(boss.getHealth() * 0.7);
        // Centipede burst shot
        timerBurstShot = millis() + (5*1000);
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
        boss.moveBoss(spider);
        //Centipede AOE timer and call ******************************************************
        currentTime = millis();
        if(currentTime > timerBurstShot){
            boss.aoeShot2();
            boss.playAOE();
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

        }
    }

    @Override
    public void DoCheck() {
        super.DoCheck();
        // boss.getY()-100 = max Y distance the spider has to be in for lunge attack
        if(spider.getPosition().y < boss.getY() && spider.getPosition().y > (boss.getY()-100) &&
                spider.getPosition().x > (boss.getX()-40) && spider.getPosition().x < (boss.getX()+40)){
            stateMachine.ChangeState(boss.lunge);
        }
        else{
            Heuristic();
        }

        // Get state to run based on state weight
        float keyValue = 0;
        BossState stateToCall = null;
        Enumeration<BossState> keys = boss.attackSelector.keys();
        while(keys.hasMoreElements()){
            BossState checkState= keys.nextElement();
            // Get the state will the highest priority, save it and compare to the rest
            if(keyValue < boss.attackSelector.get(checkState) && checkState!=this){
                stateToCall = checkState;
                keyValue = boss.attackSelector.get(checkState);
            }

        }
        // Change state to the one with the highest priority
        if(stateToCall != null ){
            stateMachine.ChangeState(stateToCall);
        }

    }

    /**
     * This is the heuristic that is used to calculate which attack the boss should do next.
     * It uses Utility Theory to calculate the threat level that the boss is at. Example:
     * If the boss is low health the threat level will be high to try and defeat the player.
     */
    public void Heuristic(){
        float spidX = spider.getPosition().x;
        float healthCheck = boss.getHealth();
        float xValue = spidX/364;

        // beam and tail utility hold the weight at which these should of should not be used
        // based on the boss's health and player position
        float beamUtility = Math.max(Math.min((1-((healthCheck-5)/(20-5)))*(1-xValue)+xValue,1),0);
        // Because we want the inverse percentage of the X-axis for the tail swipe attack
        xValue = Math.abs(1-xValue);
        float tailUtility = Math.max(Math.min((1-((healthCheck-5)/(20-5)))*(1-xValue)+xValue,1),0);

        // Check if boss health is low enough to be considered as an attack and that the spider
        // is in approximation of the beam
        if (healthCheck <= healthFinal && boss.getBody().getPosition().x <= spidX+40 && boss.getBody().getPosition().x >= spidX-40){
            boss.attackSelector.put(boss.beam,beamUtility);

        }
        else{ // If beam is not considered then set tail probability high
            tailUtility = 0.85f;
        }
        // Check if health is low enough to be considered for attack and make sure the tail isn't
        // already running
        if(healthCheck <= healthMid && !boss.tailRunning){
            boss.attackSelector.put(boss.tail,tailUtility);
            return;
        }
        // else keep patrolling
        boss.attackSelector.put(boss.patrol,1f);

    }


    @Override
    public void printState() {
        System.out.println("Patrol State");
    }
}
