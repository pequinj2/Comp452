package com.bugwars.Assignment3.Game1;

import com.badlogic.gdx.graphics.g2d.TextureRegion;


/**
 * Class for the game pieces in the game that can be referenced by CreateScene class to display all
 * of the game pieces that have been placed
 */
public class Disk {

    private TextureRegion pic;
    private int x, y;

    public Disk(TextureRegion pic, int x, int y){
        this.pic = pic;
        this.x = x;
        this.y = y;

    }

    public TextureRegion getPic(){
        return pic;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
