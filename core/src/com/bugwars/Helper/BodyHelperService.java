package com.bugwars.Helper;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
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
     * @param isStatic is it a Static object or dynamic (player or AI)
     * @param world the world in which these objects will be created in
     * @return Return created body
     */
    public static Body createBody(float x, float y, float width, float height, boolean isStatic, World world){
        BodyDef bodyDef = new BodyDef();
        // Create a static body that stays still or dynamic body so it moves around and is affected by forces
        bodyDef.type = isStatic ? BodyDef.BodyType.StaticBody : BodyDef.BodyType.DynamicBody;
        // Set our body's starting position in the world
        bodyDef.position.set(x, y);
        // If the Body is NOT dynamic then turn rotation off
        bodyDef.fixedRotation = isStatic ? true : false;
        Body body = world.createBody(bodyDef); // Create the body in our world

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
        return body;
    }
}
