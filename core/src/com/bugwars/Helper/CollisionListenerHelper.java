package com.bugwars.Helper;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.bugwars.Objects.Enemy.Centipede;
import com.bugwars.Objects.Pickups.WebSac;
import com.bugwars.Objects.Player.Spider;
import com.bugwars.Objects.Projectiles.WebShooter;

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
                if ((o1.getClass() == Spider.class || o2.getClass() == Spider.class)){
                    if(o1.getClass() == Centipede.class ) {
                        // Centipede hit - player takes damage
                        Spider sp = (Spider) o2;
                        sp.removeHealth(10);

                    }
                    else if(o2.getClass() == Centipede.class){
                        // Centipede hit - player takes damage
                        Spider sp = (Spider) o1;
                        sp.removeHealth(10);
                    }
                    else if(o1.getClass() == WebSac.class){
                        // Pick up web shooter
                        Spider sp = (Spider) o2;
                        WebSac wb = (WebSac) o1;
                        WebSac.SacState check = wb.getState();
                        if (check == WebSac.SacState.LVL_3) {
                            sp.setWebFlag(wb);
                            wb.removeSac();


                        }


                    }else if(o2.getClass() == WebSac.class ){
                        // Pick up web shooter
                        Spider sp = (Spider) o1;
                        WebSac wb = (WebSac) o2;
                        WebSac.SacState check = wb.getState();
                        if (check == WebSac.SacState.LVL_3) {
                            sp.setWebFlag(wb);
                            wb.removeSac();


                        }

                    }

                }
                else if(o2.getClass() == WebSac.class || o1.getClass() == WebSac.class){


                }
                else{
                    //System.out.println("Its a boarder");
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
