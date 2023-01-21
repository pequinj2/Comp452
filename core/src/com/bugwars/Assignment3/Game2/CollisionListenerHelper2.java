package com.bugwars.Assignment3.Game2;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.bugwars.Objects.Enemy.Centipede;
import com.bugwars.Objects.Enemy.Centipede2;
import com.bugwars.Objects.Pickups.WebSac;
import com.bugwars.Objects.Player.Spider;
import com.bugwars.Objects.Projectiles.Projectile;
import com.bugwars.Objects.Projectiles.Web;

/**
 * Collision controller of all Box2D objects and sensors
 */
public class CollisionListenerHelper2 {

    private World world;

    public CollisionListenerHelper2(World world){
        this.world = world;


        world.setContactListener(new ContactListener() {

            private World world;
            /**
             * When 2 objets collide, get their information and run code based on that collision
             * @param contact
             */
            @Override
            public void beginContact(Contact contact) {
                this.world = world;
                // Get which Fixtures collided
                Fixture f1 = contact.getFixtureA();
                Fixture f2 = contact.getFixtureB();

                // Get which bodies are attached to those fixtures
                Body b1 = f1.getBody();
                Body b2 = f2.getBody();

                Object o1 = b1.getUserData();
                Object o2 = b2.getUserData();

                if(o2.getClass() == Centipede2.class || o1.getClass() == Centipede2.class){
                    if(o1.getClass() == WebSac.class){  // Centipede takes damage
                        Centipede2 cent = (Centipede2) o2;
                        WebSac wb = (WebSac) o1;
                        WebSac.SacState check = wb.getState();
                        if(check == WebSac.SacState.LVL_3){ // Only damage the Centipede if the shot has been fired
                            cent.removeHealth(10);
                            wb.removeSac();

                        }


                    }
                    else if(o2.getClass() == WebSac.class){ // Centipede takes damage
                        Centipede2 cent = (Centipede2) o1;
                        WebSac wb = (WebSac) o2;
                        WebSac.SacState check = wb.getState();
                        if(check == WebSac.SacState.LVL_3){ // Only damage the Centipede if the shot has been fired
                            cent.removeHealth(10);
                            wb.removeSac();

                        }

                    }


                }
                else if( o1.getClass() == Web.class || o2.getClass() == Web.class){
                    if(o1.equals("Boarder")){  // Web Shot hits boarder and is destroyed
                        Web web = (Web) o2;
                        if(web.current != Web.WebState.FOLLOW){
                            web.setStateKill();
                        }
                    }
                    else if(o2.equals("Boarder")){
                        Web web = (Web) o1;
                        if(web.current != Web.WebState.FOLLOW){
                            web.setStateKill();
                        }
                    }
                }
            }

            @Override
            public void endContact(Contact contact) {

            }

            /**
             * Precheck the collisions of the websacs to see if they're ready to pick up:
             * If not, player passes through them
             * If so, player will pick up (this will be resolved in "beginContact")
             * @param contact
             * @param oldManifold
             */
            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {

                // Get which Fixtures collided
                Fixture f1 = contact.getFixtureA();
                Fixture f2 = contact.getFixtureB();

                // Get which bodies are attached to those fixtures
                Body b1 = f1.getBody();
                Body b2 = f2.getBody();

                Object o1 = b1.getUserData();
                Object o2 = b2.getUserData();

                /**
                 * This will make the WebSac pickups NON-CONTACTABLE, meaning the player can walk across
                 * them until they reach their final LVL 3 stage - then the player can run into them.
                 */
                if(o1 != null && o1.getClass() == WebSac.class ) {
                    WebSac wb = (WebSac) o1;
                    WebSac.SacState check = wb.getState();

                    if (check != WebSac.SacState.LVL_3) {
                        contact.setEnabled(false);
                    }
                }
                if(o2 != null && o2.getClass() == WebSac.class) {
                    WebSac wb = (WebSac) o2;
                    WebSac.SacState check = wb.getState();

                    if (check != WebSac.SacState.LVL_3) {
                        contact.setEnabled(false);
                    }
                }

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {

            }
        });
    }
}
