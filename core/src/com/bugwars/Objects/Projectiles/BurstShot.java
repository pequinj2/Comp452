package com.bugwars.Objects.Projectiles;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;

public class BurstShot {

    private ArrayList<Projectile> projArray;

    public BurstShot(World world, Body origin){
        this.projArray = new ArrayList<Projectile>();
        Projectile p = new Projectile(world, origin.getPosition().x, origin.getPosition().y);

    }

    public void fireBurst(){

    }


    private void seperate(){

    }

    private void align(){

    }

    private void seek(){

    }
}
