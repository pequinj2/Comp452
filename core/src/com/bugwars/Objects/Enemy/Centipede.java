package com.bugwars.Objects.Enemy;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.DistanceJointDef;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.bugwars.Assignment3.BeamObject;
import com.bugwars.Assignment3.Game2.AI.States.BossState;
import com.bugwars.Assignment3.Game2.AI.States.StateManager;
import com.bugwars.Assignment3.Game2.CentipedeAttacks.Beam;
import com.bugwars.Assignment3.Game2.CentipedeAttacks.Lunge;
import com.bugwars.Assignment3.Game2.CentipedeAttacks.Patrol;
import com.bugwars.Assignment3.Game2.CentipedeAttacks.TailSwipe;
import com.bugwars.Helper.AssetManagerA3G2;
import com.bugwars.Helper.BodyHelperService;
import com.bugwars.Objects.Entity;
import com.bugwars.Objects.Player.Damage;
import com.bugwars.Objects.Player.Health;
import com.bugwars.Objects.Projectiles.SwarmShot;

import java.util.Dictionary;
import java.util.Hashtable;


public class Centipede extends Entity implements Health, Damage {

    private float x, y, width, height;
    private int position = 0;
    private Body body, body1, body2, body3, body4, butt;
    private Array<Body> centipedeBody = new Array<Body>();
    private DistanceJointDef joint1, joint2, joint3, joint4, joint5;
    private boolean delayStarting = true;
    private float maxSpeed = 100;
    private float linearDamping = 1.5f;
    private float tailReturnY;
    private World world;

    // Burst Shot
    private SwarmShot aoeAttack;

    // *********************************************************************************
    // ADDED FOR ASSIGNMENT 3 GAME 2
    private float velX, velY;
    private boolean tailHit = false;
    public boolean tailRunning = false;
    private int rotation = 0;

    private int distance;
    private Vector2 result, spiderLocation;
    private BossState bossState;
    private StateManager stateMachine;
    public Patrol patrol;
    public Beam beam;
    public BeamObject beamObj;
    public TailSwipe tail;
    public Lunge lunge;
    public Dictionary<BossState, Float> attackSelector = new Hashtable<BossState,Float>();
    private Sound tailSound, lungeSound, beamSound, aoeSound, hitTaken;


    // Implement constructor
    public Centipede(World world,float width, float height, Body body, float health) {
        super(width, height, body);
        this.world = world;
        this.speed = 40f;
        this.width = width;
        this.height = height;
        this.body = body;
        this.setHealth(health);
        body.setUserData(this);
        aoeAttack = new SwarmShot(world,body);

        distance = 364-(int)width;

    }

    @Override
    public void update() {
        x = body.getPosition().x; // Will be the center of the body
        y = body.getPosition().y;
    }
    public void updateStop() {
        body.setLinearVelocity(0,0);
        x = body.getPosition().x; // Will be the center of the body
        y = body.getPosition().y;
    }

    /**
     * Centipede update for Assignment 3 Game 2.
     * This update is used to call whatever behavior is currently running and to make sure the tail
     * goes back to its position outside of the game screen
     */
    public void updateBehavior(){
        bossState = stateMachine.getCurrentState();
        bossState.Update();
        x = body.getPosition().x; // Will be the center of the body
        y = body.getPosition().y;
        // Check butt
        float varX = butt.getPosition().x;

        if(varX>=355f && tailHit==false){
            // Tail did not hit the spider but hit the end of the screen so retract it
            retractTail();
            tailBasePosition();
        }
        else if(tailHit==true && tailRunning==true){
            tailBasePosition();
        }
        if(varX<=-100f){
            butt.setTransform(-90, tailReturnY, butt.getAngle());
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        aoeAttack.render(batch);

    }

    public void render2(SpriteBatch batch) {
        aoeAttack.render2(batch);

    }

    @Override
    public void dealDamage(Entity entity) {

    }

    @Override
    public void recoverHealth() {

    }

    @Override
    public void removeHealth(float damage) {
        float newHealth = health - damage;
        if(hitTaken != null){
            playHit();
        }
        setHealth(newHealth);

    }

    /**
     * Delay the initial AI start time when Assignment1 loads then seek out player.
     * @param target players current position
     * @param enemy enemy AIs current position
     */
    public void seekTarget(Vector2 target, Vector2 enemy){

        Vector2 temp = new Vector2(target); // Set new variable to target Vector so we don;t change original value

        if(delayStarting == true) { // Delay the initial AI start time when Assignment1 loads then seek out player.
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    delayStarting = false;
                }
            }, 3);

        }else{
            Vector2 newPosition = temp.sub(enemy); // Displacement distance from enemy to target
            newPosition.nor(); // Give the vector a proper direction
            newPosition.scl(maxSpeed);

            body.setLinearVelocity(newPosition); // Head toward the targets position at max speed
            butt.setLinearDamping(linearDamping); // Damping placed to keep the end body somewhat in line with the head

        }

    }

    public void aoeShot(){
        aoeAttack.fireSwarm(); // Fire off the AOE attack

    }

    public void aoeShot2(){
        aoeAttack.fireSwarm2(); // Fire off the AOE attack

    }


    public float getWidth(){
        return width;
    }
    public float getHeight(){
        return height;
    }

    public float getX(){
        return x;
    }
    public float getY(){
        return y;
    }

    /**
     * Return the rest of the centipede body for rendering
     * @return
     */
    public Array<Body> getCentipede(){
        return centipedeBody;
    }
    /**
     * Return centipede butt for rendering
     * @return
     */
    public Body getCentipedeButt(){
        return butt;
    }

    /**
     * Initialize the distance joints used for connecting all of the centipedes body parts together
     * How to initialize a distance joint tutorial
     * https://www.youtube.com/watch?v=yVFntdyvGWg - LibGDX - Box2D Joints - Distance Joint Introduction (Part 1?) by Conner Anderson
     * @param world
     */
    public void initDistanceJoint(World world){
        float jointLength = 32f;
        float dampRatio = 1;
        float freqRatio = 0;

        // Attached centipede head to first body part
        joint1 = new DistanceJointDef();
        joint1.bodyA = body;
        joint1.bodyB = body1;
        joint1.collideConnected = false;
        joint1.length = jointLength;
        joint1.dampingRatio = dampRatio; // Affects the spring of the joint
        joint1.frequencyHz = freqRatio; // Speed of oscillation

        world.createJoint(joint1); // create the joint in our Assignment1 world

        // Attach body part 1 to body part 2
        joint2 = new DistanceJointDef();
        joint2.bodyA = body1;
        joint2.bodyB = body2;
        joint2.collideConnected = false;
        joint2.length = jointLength;
        joint2.dampingRatio = dampRatio;
        joint2.frequencyHz = freqRatio;

        world.createJoint(joint2); // create the joint in our Assignment1 world

        // Attach body part 2 to body part 3
        joint3 = new DistanceJointDef();
        joint3.bodyA = body2;
        joint3.bodyB = body3;
        joint3.collideConnected = false;
        joint3.length = jointLength;
        joint3.dampingRatio = dampRatio;
        joint3.frequencyHz = freqRatio;

        world.createJoint(joint3); // create the joint in our Assignment1 world

        // Attach body part 3 to body part 4
        joint4 = new DistanceJointDef();
        joint4.bodyA = body3;
        joint4.bodyB = body4;
        joint4.collideConnected = false;
        joint4.length = jointLength;
        joint4.dampingRatio = dampRatio;
        joint4.frequencyHz = freqRatio;

        world.createJoint(joint4); // create the joint in our Assignment1 world

        // Attach body part 4 to centipede butt
        joint5 = new DistanceJointDef();
        joint5.bodyA = body4;
        joint5.bodyB = butt;
        joint5.collideConnected = false;
        joint5.length = jointLength;
        joint5.dampingRatio = dampRatio;
        joint5.frequencyHz = freqRatio;

        world.createJoint(joint5); // create the joint in our Assignment1 world

    }



    /**
     * Initialize the rest of the centipede body
     * @param world
     */
    public void initBody(World world){
        body1 = BodyHelperService.createBody(
                610 + 16, // Position
                610 + 16, // Position
                32, // Box size
                32, // Box size
                1,
                false,
                world);
        body1.setUserData(this);
        body2 = BodyHelperService.createBody(
                620 + 16, // Position
                620 + 16, // Position
                32, // Box size
                32, // Box size
                1,
                false,
                world);
        body2.setUserData(this);
        body3 = BodyHelperService.createBody(
                630 + 16, // Position
                630 + 16, // Position
                32, // Box size
                32, // Box size
                1,
                false,
                world);
        body3.setUserData(this);
        body4 = BodyHelperService.createBody(
                640 + 16, // Position
                640 + 16, // Position
                32, // Box size
                32, // Box size
                1,
                false,
                world);
        body4.setUserData(this);
        butt = BodyHelperService.createBody(
                650 + 16, // Position
                650 + 16, // Position
                32, // Box size
                32, // Box size
                1,
                false,
                world);
        butt.setUserData(this);

        centipedeBody.add(body1);
        centipedeBody.add(body2);
        centipedeBody.add(body3);
        centipedeBody.add(body4);



    }

    public void dispose(){
        aoeAttack.dispose();
    }

    // ********************************************************
    // ASSIGNMENT 3 GAME 2
    public void initTail(){
        butt = BodyHelperService.createBody(
                -90, // Position
                200,
                80, // Box size
                80, // Box size
                1,
                false,
                world);
        butt.setUserData(this);
        //body1.setActive(false);

      /*  body1 = BodyHelperService.createBody(
                -80, // Position
                200, // Position
                80, // Box size
                80, // Box size
                1,
                true,
                world);
        body1.setUserData(this);
        body2 = BodyHelperService.createBody(
                -80, // Position
                200,  // Position
                80, // Box size
                80, // Box size
                1,
                false,
                world);
        body2.setUserData(this);
        body3 = BodyHelperService.createBody(
                -80, // Position
                200,
                80, // Box size
                80, // Box size
                1,
                false,
                world);
        body3.setUserData(this);*/
        body4 = BodyHelperService.createBody(
                -90, // Position
                200,
                80, // Box size
                80, // Box size
                1,
                true,
                world);
        body4.setUserData(this);

        centipedeBody.add(body1);
        centipedeBody.add(body2);
        centipedeBody.add(body3);
        centipedeBody.add(body4);



    }

    /**
     * Moving the boss uses Arrive behavior to follow the player and slow down when it gets to the
     * player's position.
     * @param spider
     */
    public void moveBoss(Body spider){
        Vector2 radiiPos = new Vector2(spider.getPosition().x,body.getPosition().y); // Make coordinates proper Vector2s
        Vector2 currentPos = new Vector2(body.getPosition().x,body.getPosition().y);

        // Get displacement distance from enemy to target
        Vector2 newDirection = radiiPos.sub(currentPos);
        Vector2 temp = newDirection;
        Vector2 vel;

        float distance = newDirection.len();
        float targetRadii = 8f;
        float largeRadii = 64f;
        float targetSpeed;


        if(distance < targetRadii){
            result = temp.nor().scl(0); // At target, have no velocity
        }
        else {

            if (distance > largeRadii) {
                targetSpeed = 100;

            } else {
                targetSpeed = 100 * (distance / largeRadii) ;
            }
            vel = temp.nor().scl(targetSpeed); // Target Velocity
            result = vel;

        }
        body.setLinearVelocity(result);
    }


    /**
     * Run the tail swipe attack by moving the 'butt' body along the x-axis of the player's current
     * position. Function gets called multiple times until the tail has reached the end of the game
     * field.
     * @param spider
     */
    public void tailAttack(Body spider){

        if(tailRunning==false){
            //spiderLocation = new Vector2((int)(1216 * 0.3), spider.getPosition().y); // Only y position matters
            butt.setTransform(0, spider.getPosition().y, butt.getAngle());

            tailReturnY = spider.getPosition().y;
            tailRunning = true;
        }

        Vector2 temp = new Vector2(1216,0);
        temp.nor(); // Give the vector a proper direction
        temp.scl(maxSpeed);
        butt.setLinearVelocity(temp);

    }


    /**
     * Initialize all of the Centipede boss states and put them in a dictionary with an initial attack
     * weight. 'Patrol' is a base state.
     * @param spider
     */
    public void setBehaviors(Body spider){
        stateMachine = new StateManager();
        patrol = new Patrol(this, spider, stateMachine);
        tail = new TailSwipe(this, spider, stateMachine);
        lunge = new Lunge(this, spider, stateMachine);
        beam = new Beam(this, spider, stateMachine);
        attackSelector.put(patrol, 1f);
        attackSelector.put(tail, 0f);
        attackSelector.put(lunge, 1f);
        attackSelector.put(beam, 0f);
        stateMachine.Initialize(patrol);


    }

    /**
     * The tail has either hit the spider or the game boarder, retract tail to end.
     * Variable 'tailHit' is false until the conditions mentioned are met.
     */
    public void retractTail() {

        tailHit = !tailHit;
        /*if(tailHit== true){ // Tail hit is true therefore retract to position
            tailReturnY = butt.getPosition().y;// Set tail's y position for constant value in tailBasePosition method
        }*/
    }

    /**
     * Return True or False if the tail strike has hit the target or the wall of the game
     * @return
     */
    public boolean getRetractTail(){
        return tailHit;
    }

    /**
     * Put tail to regular position
     */
    public void tailBasePosition() {
        Vector2 temp = new Vector2(-900,0);
        temp.nor();
        temp.scl(maxSpeed);
        butt.setLinearVelocity(temp);
        if(butt.getPosition().x <= -100){
            retractTail();
            tailRunning=false;
        }
    }


    /**
     * Move Centipede head to attack spider
     */
    public void lungeAttack(){
        Vector2 attack = new Vector2(0,body.getPosition().y-80);
        attack.nor();
        attack.scl(-maxSpeed);
        body.setLinearVelocity(attack);
    }

    /**
     * Lunge attack has reached max depth so return to original position
     */
    public void lungeAttackReturn(){
        if(body.getPosition().y < 310){
            Vector2 attack = new Vector2(0,body.getPosition().y+180);
            attack.nor();
            attack.scl(maxSpeed);
            body.setLinearVelocity(attack);
        }else{
            body.setTransform(body.getPosition().x, 310, 0);
        }

    }

    /**
     * Stop moving centipede head, used when firing attacks at player
     */
    public void bossStopMoving(){
        body.setLinearVelocity(new Vector2(0,0));
    }

    /**
     * Give reference to the beam object
     * @param beam
     */
    public void setBeam(BeamObject beam){
        beamObj = beam;
    }

    /**
     * Apply sounds to the centipede's attacks
     * @param assetMgr
     */
    public void setSounds(AssetManagerA3G2 assetMgr){
        tailSound = assetMgr.getTailAttack();
        lungeSound = assetMgr.getLungeAttack();
        beamSound = assetMgr.getBeamAttack();
        aoeSound = assetMgr.getAoeAttack();
        hitTaken = assetMgr.getSpiderAttackHit();
    }

    public void playTail(){
        tailSound.play();
    }

    public void playLunge(){
        lungeSound.play();
    }

    public void playBeam(){
        beamSound.play();
    }

    public void playAOE(){
        aoeSound.play();
    }

    private void playHit(){
        hitTaken.play();
    }
}
