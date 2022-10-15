package com.bugwars.Objects.Projectiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Timer;
import com.bugwars.Helper.BodyHelperService;
import com.bugwars.Objects.Pickups.WebSac;
import com.bugwars.Objects.Player.Spider;

import java.util.ArrayList;

public class WebShooter {


    private int speed = 100;
    private WebSac sac;
    private Spider body;
    private Body smallRadii;
    private ArrayList<Body> radiiBodies;
    private float rotateRadii = 24;
    private World world;

    //Bullets
    private ArrayList<Web> webs;


    public WebShooter(World world, Spider body){
        this.body = body;
        this.world = world;
        //web.setUserData(this);

        smallRadii = BodyHelperService.createWebShooter(world, body.getX(), body.getY(), 4f);
        smallRadii.setUserData("radius");

        // Arraylist of the radius position sensors, these sensors will flag the ai that the 'WebShooter'
        // object is within range of the Spider Player
        radiiBodies = new ArrayList<Body>();
        //radiiBodies.add(smallRadii);

        // Bullet array
        webs = new ArrayList<Web>();


    }

    /**
     * Set the velocity and vector of where the web shot is going
     */
    public void fireWebbing(){

        int count = 0;
        /*if(radiiBodies.size() == 0){ // ERROR: no webs to fire
            return;
        }*/
        Web fireWeb = webs.get(0);
        fireWeb.current = Web.WebState.FIRE;
        world.destroyBody(radiiBodies.get(0));
        radiiBodies.remove(0);
        Vector2 temp;

        switch(body.getRotation()){
            case(-90):
                temp = new Vector2(1216, body.getY());
                temp.nor();
                temp.scl(500);
                fireWeb.setResult(temp);

                break;
            case(90):
                //fireWeb.getBody().setLinearVelocity(0, body.getY());
                temp = new Vector2(0, body.getY());
                temp.nor();
                temp.scl(500);
                fireWeb.setResult(temp);
                break;
            case(0):
                //fireWeb.getBody().setLinearVelocity(body.getX(),800 );
                temp = new Vector2(body.getX(),800);
                temp.nor();
                temp.scl(500);
                fireWeb.setResult(temp);
                break;
            case(-180):
                //fireWeb.getBody().setLinearVelocity(body.getX(),0 );
                temp = new Vector2(body.getX(),0 );
                temp.nor();
                temp.scl(500);
                fireWeb.setResult(temp);
                break;
        }

    }



    /**
     * Check if webbing collides
     */
    private void update(){
        /** Use players 'rotation' variable to know which way player is facing
         *  -90  : RIGHT
         *   90  : LEFT
         *    0  : UP
         *  -180 : DOWN
         */
        switch(body.getRotation()){
            case(-90):
                for(Body rad : radiiBodies){
                    rad.setTransform(body.getBody().getPosition().sub(rotateRadii,0),0);
                    rotateRadii += 16;
                }
                rotateRadii = 24; // reset radii base value
                break;
            case(90):
                for(Body rad : radiiBodies){
                    rad.setTransform(body.getBody().getPosition().add(rotateRadii,0),0);
                    rotateRadii += 16;
                }
                rotateRadii = 24;
                break;
            case(0):
                for(Body rad : radiiBodies){
                    rad.setTransform(body.getBody().getPosition().sub(0,rotateRadii),0);
                    rotateRadii += 16;
                }
                rotateRadii = 24;
                break;
            case(-180):
                for(Body rad : radiiBodies){
                    rad.setTransform(body.getBody().getPosition().add(0,rotateRadii),0);
                    rotateRadii += 16;
                }
                rotateRadii = 24;
                break;
        }


    }

    /**
     * Render the visual of the webbing
     */
    public void render(SpriteBatch batch){
        update();
        for(Web wb : webs){
            if(wb.current == Web.WebState.FOLLOW){
                wb.followPlayer(); // get the last element added to the list and tell it to follow the player
            }
            wb.render(batch);
        }


    }

    public int getArraySize(){
        return radiiBodies.size();
    }

    public void setSac(WebSac web){


    }

    public void loadWeb(World world, WebSac sac){
        this.sac = sac;
        smallRadii = BodyHelperService.createWebShooter(world, body.getX(), body.getY(), 4f);
        smallRadii.setUserData("radius");
        radiiBodies.add(smallRadii);
        webs.add(new Web(world, sac, smallRadii)); // add a new web object


    }


}
