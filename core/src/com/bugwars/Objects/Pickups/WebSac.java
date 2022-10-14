package com.bugwars.Objects.Pickups;

import static com.badlogic.gdx.utils.TimeUtils.millis;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.bugwars.Helper.BodyHelperService;

public class WebSac {

    public enum SacState{
        LVL_0,
        LVL_1,
        LVL_2,
        LVL_3
    }
    private SacState current;
    private TextureAtlas textures = new TextureAtlas(Gdx.files.internal("maps/WebbingTexures.atlas"));
    private TextureRegion level1Web = new TextureRegion(textures.findRegion("Web_Sac1"));
    private TextureRegion level2Web = new TextureRegion(textures.findRegion("Web_Sac2"));
    private TextureRegion level3Web = new TextureRegion(textures.findRegion("Web_Sac3"));
    private long timer, currentTime;
    private Body sac;
    private World world;
    private int origin = 12;
    private int height = 12;
    private int width = 12;


    public WebSac(Body sac, World world){
        this.sac = sac;
        this.world = world;
        current = SacState.LVL_1;
        timer = millis() + (5*1000);
        sac.setUserData(this);

    }

    private void update(){
        if(sac == null){
            setSac();
        }
        currentTime = millis();
        if( this.getState() != SacState.LVL_3 && currentTime >= timer){
            switch(current){
                case LVL_0:
                    removeSac();
                    setSac();
                    current = SacState.LVL_1;
                    break;
                case LVL_1:
                    current = SacState.LVL_2;
                    timer = millis() + (5*1000);
                    break;
                case LVL_2:
                    current = SacState.LVL_3;
                    break;
            }
        }

    }

    public void render(SpriteBatch batch){
        update();
        // Render the sacs Textures depending on what state level its at
        switch(current){
            case LVL_0:
                break;
            case LVL_1:
                batch.draw(level1Web,
                        sac.getPosition().x,
                        sac.getPosition().y,
                        origin,
                        origin,
                        width,
                        height,
                        2,
                        2,
                        0);
                break;
            case LVL_2:
                batch.draw(level2Web,
                        sac.getPosition().x,
                        sac.getPosition().y,
                        origin,
                        origin,
                        width,
                        height,
                        2,
                        2,
                        0);
                break;
            case LVL_3:
                batch.draw(level3Web,
                        sac.getPosition().x,
                        sac.getPosition().y,
                        origin,
                        origin,
                        width,
                        height,
                        2,
                        2,
                        0);
                break;
        }


    }

    public SacState getState(){
        return current;
    }

    public void removeSac(){
        current = SacState.LVL_0;
        timer = millis() + (8 * 1000); // Extra time to regenerate the Web Sac pickup

    }

    public void setSac(){
        world.destroyBody(sac);
        sac = BodyHelperService.createWebSac(world);
        sac.setUserData(this);
    }

    public Body getBody(){ return sac; }



}
