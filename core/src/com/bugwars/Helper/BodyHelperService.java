package com.bugwars.Helper;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class BodyHelperService {

    /**
     * Helper class that will create either Static or Dynamic bodies and return the newly created body.
     * @param x position to be rendered on map
     * @param y position to be rendered on map
     * @param width of sprite
     * @param height of sprite
     * @param PolyOrCircle int value of 0 or 1 indicating 0 for Polygon shape or 1 for circle shape
     * @param isStatic is it a Static object or dynamic (player or AI)
     * @param world the world in which these objects will be created in
     * @return Return created body
     */
    public static Body createBody(float x, float y, float width, float height, int PolyOrCircle, boolean isStatic, World world){
        BodyDef bodyDef = new BodyDef();
        // Create a static body that stays still or dynamic body so it moves around and is affected by forces
        bodyDef.type = isStatic ? BodyDef.BodyType.StaticBody : BodyDef.BodyType.DynamicBody;
        // Set our body's starting position in the world
        bodyDef.position.set(x/2, y/2);

        // If the Body is NOT dynamic then turn rotation off
        bodyDef.fixedRotation = isStatic ? true : false;
        Body body = world.createBody(bodyDef); // Create the body in our world

        if (PolyOrCircle == 0) {
            // PolygonShape is apart of Box2D, therefore we need to use it so we can use Box2Ds collision detection
            PolygonShape shape = new PolygonShape();
            shape.setAsBox(width/2, height/2);
            // Create a fixture definition to apply our shape to
            FixtureDef fixtureDef = new FixtureDef();
            fixtureDef.shape = shape;

            // Create our fixture and attach it to the body
            Fixture fixture = body.createFixture(fixtureDef);

            // Remember to dispose of any shapes after you're done with them!
            // BodyDef and FixtureDef don't need disposing, but shapes do.
            shape.dispose();
        }
        else {
            CircleShape shape = new CircleShape();
            shape.setRadius(width /2 );
            // Create a fixture definition to apply our shape to
            FixtureDef fixtureDef = new FixtureDef();
            fixtureDef.density = 10;
            fixtureDef.shape = shape;

            // Create our fixture and attach it to the body
            Fixture fixture = body.createFixture(fixtureDef);

            // Remember to dispose of any shapes after you're done with them!
            // BodyDef and FixtureDef don't need disposing, but shapes do.
            shape.dispose();
        }


        return body;
    }

    public static void createGameBorder(World world){

        // ***** Left border
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        // Set our body's starting position in the world
        bodyDef.position.set(0, 0);

        bodyDef.fixedRotation = false;
        Body body = world.createBody(bodyDef); // Create the body in our world

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(0, 448);
        // Create a fixture definition to apply our shape to
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;

        // Create our fixture and attach it to the body
        Fixture fixture = body.createFixture(fixtureDef);

        // Remember to dispose of any shapes after you're done with them!
        // BodyDef and FixtureDef don't need disposing, but shapes do.
        shape.dispose();

        // ***** Top border
        bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        // Set our body's starting position in the world
        bodyDef.position.set(0, 448);

        bodyDef.fixedRotation = false;
        body = world.createBody(bodyDef); // Create the body in our world

        shape = new PolygonShape();
        shape.setAsBox(608, 0);
        // Create a fixture definition to apply our shape to
        fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;

        // Create our fixture and attach it to the body
        fixture = body.createFixture(fixtureDef);

        // Remember to dispose of any shapes after you're done with them!
        // BodyDef and FixtureDef don't need disposing, but shapes do.
        shape.dispose();

        // ***** Right border
        bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        // Set our body's starting position in the world
        bodyDef.position.set(608, 0);

        bodyDef.fixedRotation = false;
        body = world.createBody(bodyDef); // Create the body in our world

        shape = new PolygonShape();
        shape.setAsBox(0, 608);
        // Create a fixture definition to apply our shape to
        fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;

        // Create our fixture and attach it to the body
        fixture = body.createFixture(fixtureDef);

        // Remember to dispose of any shapes after you're done with them!
        // BodyDef and FixtureDef don't need disposing, but shapes do.
        shape.dispose();

        // ***** Bottom border
        bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        // Set our body's starting position in the world
        bodyDef.position.set(0, 0);

        bodyDef.fixedRotation = false;
        body = world.createBody(bodyDef); // Create the body in our world

        shape = new PolygonShape();
        shape.setAsBox(608, 0);
        // Create a fixture definition to apply our shape to
        fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;

        // Create our fixture and attach it to the body
        fixture = body.createFixture(fixtureDef);

        // Remember to dispose of any shapes after you're done with them!
        // BodyDef and FixtureDef don't need disposing, but shapes do.
        shape.dispose();



    }
}
