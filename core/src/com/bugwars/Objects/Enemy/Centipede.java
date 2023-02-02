package com.bugwars.Objects.Enemy;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.DistanceJointDef;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.bugwars.Assignment3.Game2.AI.States.BossState;
import com.bugwars.Assignment3.Game2.AI.States.StateManager;
import com.bugwars.Assignment3.Game2.CentipedeAttacks.Beam;
import com.bugwars.Assignment3.Game2.CentipedeAttacks.Patrol;
import com.bugwars.Assignment3.Game2.CentipedeAttacks.TailSwipe;
import com.bugwars.Helper.BodyHelperService;
import com.bugwars.Objects.Entity;
import com.bugwars.Objects.Player.Damage;
import com.bugwars.Objects.Player.Health;
import com.bugwars.Objects.Projectiles.SwarmShot;


public class Centipede extends Entity implements Health, Damage {

    private float x, y, width, height;
    private int position = 0;
    private Body body, body1, body2, body3, body4, butt;
    private Array<Body> centipedeBody = new Array<Body>();
    private DistanceJointDef joint1, joint2, joint3, joint4, joint5;
    private boolean delayStarting = true;
    private float maxSpeed = 100;
    private float linearDamping = 1.5f;
    private World world;

    // Burst Shot
    private SwarmShot aoeAttack;

    // *********************************************************************************
    // ADDED FOR ASSIGNMENT 3 GAME 2
    private float velX, velY;
    private boolean direcLR, direcUD;
    private int rotation = 0;
    private Beam beam;
    private TailSwipe tail;
    private int distance;
    private Vector2 result;
    private BossState bossState;
    private StateManager stateMachine;
    private Patrol patrol;


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
        beam = new Beam();
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

    public void updateBehavior(){

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
        body1 = BodyHelperService.createBody(
                610 + 16, // Position
                610 + 16, // Position
                32, // Box size
                32, // Box size
                1,
                false,
                world);
        body1.setUserData(this);
        body1.setActive(false);
        tail = new TailSwipe(body1);
    }

    public void moveBoss(Body spider){
        float timeToTarget = 0.1f;
        Vector2 radiiPos = new Vector2(spider.getPosition().x,0); // Make coordinates proper Vector2s
        Vector2 currentPos = new Vector2(body.getPosition().x,0);

        // Get displacement distance from enemy to target
        Vector2 newDirection = radiiPos.sub(currentPos);
        Vector2 temp = newDirection;
        Vector2 vel;

        float distance = newDirection.len();
        float targetRadii = 16f;
        float largeRadii = 64f;
        float targetSpeed;

        System.out.println(distance + " " + targetRadii);
        if(distance < targetRadii){
            result = temp.nor().scl(0); // At target, have no velocity
        }
        else {

            if (distance > largeRadii) {
                targetSpeed = 50;

            } else {
                targetSpeed = 50 * (distance / largeRadii) ;
            }

            vel = temp.nor().scl(targetSpeed); // Target Velocity
            Vector2 currentVelocity = body.getLinearVelocity(); // Current object velocity

            result = vel.sub(currentVelocity);
            result.scl(1 / timeToTarget);

            // Throttle acceleration if too fast
            if (result.len() > 50) {
                result.nor();
                result.scl(50);

            }
            body.setLinearVelocity(result);

        }
    }

    public void setBehaviors(Body spider){
        stateMachine = new StateManager();
        patrol = new Patrol(this, spider, stateMachine);
        stateMachine.Initialize(patrol);


    }

}
