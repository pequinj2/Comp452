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
     * Reminder of Box2D body coordinates:
     * https://stackoverflow.com/questions/53987670/libgdx-box2d-body-is-being-place-at-the-wrong-y-coordinate
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
            //fixtureDef.filter.groupIndex = 1;

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
            // By setting the group index to -1 these specific objects won't collide
            // This works in our scenario because all circles are going to be the centipede body
            // If we have more circles we'd have to change how this is created
            // https://www.iforce2d.net/b2dtut/collision-filtering
            //fixtureDef.filter.groupIndex = -1;

            // Create our fixture and attach it to the body
            Fixture fixture = body.createFixture(fixtureDef);

            // Remember to dispose of any shapes after you're done with them!
            // BodyDef and FixtureDef don't need disposing, but shapes do.
            shape.dispose();
        }


        return body;
    }

    /**
     * Create the Assignment1 boarder boundaries
     * @param world
     */
    public static void createGameBorder(World world){

        // ***** Left border
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        // Set our body's starting position in the world
        bodyDef.position.set(0, 0);

        bodyDef.fixedRotation = false;
        Body body = world.createBody(bodyDef); // Create the body in our world
        body.setUserData("Boarder");

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
        body.setUserData("Boarder");

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
        body.setUserData("Boarder");

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
        body.setUserData("Boarder");

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

    /**
     * Create the web sac pickup bodies
     * @param world
     * @return
     */
    public static Body createWebSac(World world){
        BodyDef bodyDef = new BodyDef();
        // Create a static body that stays still or dynamic body so it moves around and is affected by forces
        bodyDef.type = BodyDef.BodyType.StaticBody;
        // Set our body's starting position in the world
        bodyDef.position.set((int)(Math.random()*592 - 40), (int)(Math.random()*432 - 40));

        // If the Body is NOT dynamic then turn rotation off
        bodyDef.fixedRotation = true;
        Body body = world.createBody(bodyDef); // Create the body in our world

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(10, 10);
        // Create a fixture definition to apply our shape to
        FixtureDef fixtureDef = new FixtureDef();

        fixtureDef.shape = shape;
        // By setting the group index to -1 these specific objects won't collide
        // This works in our scenario because all circles are going to be the centipede body
        // If we have more circles we'd have to change how this is created
        // https://www.iforce2d.net/b2dtut/collision-filtering
        //fixtureDef.filter.groupIndex = -1;

        // Create our fixture and attach it to the body
        Fixture fixture = body.createFixture(fixtureDef);

        // Remember to dispose of any shapes after you're done with them!
        // BodyDef and FixtureDef don't need disposing, but shapes do.
        shape.dispose();


        return body;

    }

    /**
     * Create SENSOR objects
     * Objects made:
     *  - Web
     *  - Projectile
     * @param world
     * @return
     */
    public static Body createProjectiles(World world, float x, float y, float radius) {
        BodyDef bodyDef = new BodyDef();
        // Create a static body that stays still or dynamic body so it moves around and is affected by forces
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        // Set our body's starting position in the world
        bodyDef.position.set(x, y);

        // If the Body is NOT dynamic then turn rotation off
        bodyDef.fixedRotation = true;
        Body body = world.createBody(bodyDef); // Create the body in our world
        CircleShape shape = new CircleShape();
        shape.setRadius(radius  );
        // Create a fixture definition to apply our shape to
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = 10;
        fixtureDef.shape = shape;
        fixtureDef.isSensor = true;

        // Create our fixture and attach it to the body
        Fixture fixture = body.createFixture(fixtureDef);

        // Remember to dispose of any shapes after you're done with them!
        // BodyDef and FixtureDef don't need disposing, but shapes do.
        shape.dispose();

        return body;
    }


}
