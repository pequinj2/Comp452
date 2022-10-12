package com.bugwars.Helper;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.bugwars.Objects.Enemy.Centipede;
import com.bugwars.Objects.Player.Spider;

public class CollisionListenerHelper {

    private World world;

    public CollisionListenerHelper(World world){
        this.world = world;


        world.setContactListener(new ContactListener() {
            /**
             * When 2 objets collide, get their information and run code based on that collision
             * @param contact
             */
            @Override
            public void beginContact(Contact contact) {
                // Get which Fixtures collided
                Fixture f1 = contact.getFixtureA();
                Fixture f2 = contact.getFixtureB();

                // Get which bodies are attached to those fixtures
                Body b1 = f1.getBody();
                Body b2 = f2.getBody();

                Object o1 = b1.getUserData();
                Object o2 = b2.getUserData();

                /**
                 * Check if the objects that collided are a spider and centipede,
                 * if so,
                 *  deduct health from Spider
                 * if not,
                 *  do nothing
                 */
                if ((o1.getClass() == Spider.class && o2.getClass() == Centipede.class)){
                    Spider sp = (Spider) o1;
                    sp.removeHealth(10);
                    System.out.println("Boom: " + sp.getHealth());

                }
                else if(o2.getClass() == Spider.class && o1.getClass() == Centipede.class){
                    System.out.println("Boom");

                }
                else{
                    System.out.println("Its a boarder");
                }


            }

            @Override
            public void endContact(Contact contact) {

            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {

            }
        });
    }
}
