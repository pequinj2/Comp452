package com.bugwars.Objects.Projectiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.bugwars.Helper.BodyHelperService;

public class Projectile {

    private float x, y;
    private TextureAtlas textures = new TextureAtlas(Gdx.files.internal("maps/WebbingTexures.atlas"));
    private TextureRegion projTexture = new TextureRegion(textures.findRegion("Projectile"));
    private Body projBod;


    public Projectile(World world, float x, float y){
        this.x = x;
        this.y = y;
        projBod = BodyHelperService.createProjectiles(world, x, y, 4f);
        projBod.setUserData(this);
    }


    public void render(SpriteBatch batch){
        batch.draw(projTexture, projBod.getPosition().x, projBod.getPosition().y);
    }
}
