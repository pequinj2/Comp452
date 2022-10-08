package com.bugwars.Objects.Enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.DistanceJointDef;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.utils.Array;
import com.bugwars.Helper.BodyHelperService;
import com.bugwars.Objects.Entity;
import com.bugwars.Objects.Player.Damage;
import com.bugwars.Objects.Player.Health;

import java.util.Random;

public class Centipede extends Entity implements Health, Damage {

    private Texture texture;
    private float velX, velY, x, y, width, height;
    private boolean direcLR, direcUD;
    private int position = 0;
    private Body body1, body2, body3, body4, butt;
    private Array<Body> centipedeBody = new Array<Body>();
    private DistanceJointDef joint1, joint2, joint3, joint4, joint5;
    Random ranNum;

    // Implement constructor
    public Centipede(float width, float height, Body body) {
        super(width, height, body);
        this.speed = 40f;
        this.width = width;
        this.height = height;
        texture = new Texture(Gdx.files.internal("Centipede_Head_0.png"));
        ranNum = new Random();

    }

    @Override
    public void update() {
        x = body.getPosition().x; // Will be the center of the body
        y = body.getPosition().y;



    }

    @Override
    public void render(SpriteBatch batch) {

    }

    @Override
    public void dealDamage(Entity entity) {

    }

    @Override
    public void recoverHealth() {

    }

    /**
     * Vector explanation for game dev:
     * https://www.youtube.com/watch?v=m7VY1T6f8Ak
     * @param target players current position
     * @param enemy enemy AIs current position
     */
    public void seekTarget(Vector2 target, Vector2 enemy){
        float maxSpeed = 80;
        int speedTime = ranNum.nextInt(3);
        Vector2 newPosition = target.sub(enemy);
        newPosition.nor();
        newPosition.scl(maxSpeed);

        body.setLinearVelocity(newPosition); // Head toward the targets position at max speed
        butt.setLinearDamping(1.5f); // Damping placed to keep the end body somewhat in line with the head

    }

    public void initBody(World world){
        body1 = BodyHelperService.createBody(
                210 + 16, // Position
                210 + 16, // Position
                32, // Box size
                32, // Box size
                1,
                false,
                world);
        body2 = BodyHelperService.createBody(
                220 + 16, // Position
                220 + 16, // Position
                32, // Box size
                32, // Box size
                1,
                false,
                world);
        body3 = BodyHelperService.createBody(
                230 + 16, // Position
                230 + 16, // Position
                32, // Box size
                32, // Box size
                1,
                false,
                world);
        body4 = BodyHelperService.createBody(
                240 + 16, // Position
                240 + 16, // Position
                32, // Box size
                32, // Box size
                1,
                false,
                world);
        butt = BodyHelperService.createBody(
                250 + 16, // Position
                250 + 16, // Position
                32, // Box size
                32, // Box size
                1,
                false,
                world);
        //centipedeBody.add(body);
        centipedeBody.add(body1);
        centipedeBody.add(body2);
        centipedeBody.add(body3);
        centipedeBody.add(body4);
        //centipedeBody.add(butt);


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

    public Array<Body> getCentipede(){
        return centipedeBody;
    }

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

        world.createJoint(joint1); // create the joint in our game world

        // Attach body part 1 to body part 2
        joint2 = new DistanceJointDef();
        joint2.bodyA = body1;
        joint2.bodyB = body2;
        joint2.collideConnected = false;
        joint2.length = jointLength;
        joint2.dampingRatio = dampRatio;
        joint2.frequencyHz = freqRatio;

        world.createJoint(joint2); // create the joint in our game world

        // Attach body part 2 to body part 3
        joint3 = new DistanceJointDef();
        joint3.bodyA = body2;
        joint3.bodyB = body3;
        joint3.collideConnected = false;
        joint3.length = jointLength;
        joint3.dampingRatio = dampRatio;
        joint3.frequencyHz = freqRatio;

        world.createJoint(joint3); // create the joint in our game world

        // Attach body part 3 to body part 4
        joint4 = new DistanceJointDef();
        joint4.bodyA = body3;
        joint4.bodyB = body4;
        joint4.collideConnected = false;
        joint4.length = jointLength;
        joint4.dampingRatio = dampRatio;
        joint4.frequencyHz = freqRatio;

        world.createJoint(joint4); // create the joint in our game world

        // Attach body part 4 to centipede butt
        joint5 = new DistanceJointDef();
        joint5.bodyA = body4;
        joint5.bodyB = butt;
        joint5.collideConnected = false;
        joint5.length = jointLength;
        joint5.dampingRatio = dampRatio;
        joint5.frequencyHz = freqRatio;

        world.createJoint(joint5); // create the joint in our game world

       /* float localAX = 8;
        float localAY = 8;
        float localBX = -8;
        float localBY = -8;
        float lowAngle = -180;
        float upAngle = 180;
        joint1 = new RevoluteJointDef();
        joint1.bodyA = body;
        joint1.bodyB = body1;
        joint1.collideConnected = true;
        joint1.localAnchorA.set(localAX,localAY);
        joint1.localAnchorB.set(localBX,localBY);
        joint1.enableMotor = false;
        joint1.enableLimit = true;
        joint1.lowerAngle = lowAngle;
        joint1.upperAngle = upAngle;


        world.createJoint(joint1); // create the joint in our game world

        // Attach body part 1 to body part 2
        joint2 = new RevoluteJointDef();
        joint2.bodyA = body1;
        joint2.bodyB = body2;
        joint2.collideConnected = true;
        joint2.localAnchorA.set(localAX,localAY);
        joint2.localAnchorB.set(localBX,localBY);
        joint2.enableMotor = false;
        joint2.enableLimit = true;
        joint2.lowerAngle = lowAngle;
        joint2.upperAngle = upAngle;


        world.createJoint(joint2); // create the joint in our game world

        // Attach body part 2 to body part 3
        joint3 = new RevoluteJointDef();
        joint3.bodyA = body2;
        joint3.bodyB = body3;
        joint3.collideConnected = true;
        joint3.localAnchorA.set(localAX,localAY);
        joint3.localAnchorB.set(localBX,localBY);
        joint3.enableMotor = false;
        joint3.enableLimit = true;
        joint3.lowerAngle = lowAngle;
        joint3.upperAngle = upAngle;

        world.createJoint(joint3); // create the joint in our game world

        // Attach body part 3 to body part 4
        joint4 = new RevoluteJointDef();
        joint4.bodyA = body3;
        joint4.bodyB = body4;
        joint4.collideConnected = true;
        joint4.localAnchorA.set(localAX,localAY);
        joint4.localAnchorB.set(localBX,localBY);
        joint4.enableMotor = false;
        joint4.enableLimit = true;
        joint4.lowerAngle = lowAngle;
        joint4.upperAngle = upAngle;


        world.createJoint(joint4); // create the joint in our game world

        // Attach body part 4 to centipede butt
        joint5 = new RevoluteJointDef();
        joint5.bodyA = body4;
        joint5.bodyB = butt;
        joint5.collideConnected = true;
        joint5.localAnchorA.set(localAX,localAY);
        joint5.localAnchorB.set(localBX,localBY);
        joint5.enableMotor = false;
        joint5.enableLimit = true;
        joint5.lowerAngle = lowAngle;
        joint5.upperAngle = upAngle;


        world.createJoint(joint5); // create the joint in our game world*/

    }
}
