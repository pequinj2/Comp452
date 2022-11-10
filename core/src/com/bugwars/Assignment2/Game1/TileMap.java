package com.bugwars.Assignment2.Game1;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import java.util.PriorityQueue;

public class TileMap {

    private Table tbl;
    private Stage stg;
    private Skin skin;
    private TextureAtlas atlas;
    private Tile[] tiles = new Tile[16 * 16];

    public TileMap(Stage stg){
        this.stg = stg;
        tbl = new Table();
        tbl.setDebug(true);
        tbl.setFillParent(true);
        skin = new Skin();
        atlas = new TextureAtlas((Gdx.files.internal("Assignment2/GameOneTiles.atlas")));
        skin.addRegions(atlas);

        tbl.padRight(120);
        tbl.padTop(55);

        /**
         * Implement the priority queue of our nodes, because the map is a 16x16 graph we need a
         * space of 256. Use TileCompare to sort the nodes properly in a min-Heap structure.
         */
        PriorityQueue<Tile> queue = new PriorityQueue<>(256, new TileCompare());

        for(int j = 0; j<16; j++ ){
            for(int i=0; i<16; i++) {
                Tile tile = tiles[(i*16) + j];
                tbl.right().add(tile).width(50).height(50);
            }
            tbl.row();

        }
        stg.addActor(tbl);

    }

    public Table getTable(){
        return tbl;
    }

}
