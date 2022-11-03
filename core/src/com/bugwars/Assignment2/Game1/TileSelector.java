package com.bugwars.Assignment2.Game1;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextTooltip;
import com.badlogic.gdx.scenes.scene2d.ui.Tooltip;
import com.badlogic.gdx.scenes.scene2d.ui.TooltipManager;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.bugwars.Assignment2.ToolTip;

import org.w3c.dom.Text;

import javax.swing.plaf.ToolTipUI;

/**
 * This class is used to show the different tiles the user has the options at picking
 */
public class TileSelector {

    private Stage stg;
    private Skin skin;
    private Table tbl;
    private TextureAtlas tiles;
    private SpriteBatch batch;

    private Button openTerrain, grassland, swampland, rock;

    private Boolean showTooltip = false;
    private Boolean showTileSelected = false;
    private String text = "";

    private TextureRegion newTextureRegion;

    public TileSelector(OrthographicCamera camera){
        stg = new Stage();
        tbl = new Table();
        stg.addActor(tbl);
        tbl.setDebug(true);
        tbl.setFillParent(true);
        skin = new Skin();
        tiles = new TextureAtlas((Gdx.files.internal("Assignment2/GameOneTiles.atlas")));
        skin.addRegions(tiles);

        batch = new SpriteBatch();

        openTerrain = new Button(skin.getDrawable("Dirt_Tile"),skin.getDrawable("Dirt_Tile_Highlight"));
        grassland = new Button(skin.getDrawable("Grass"),skin.getDrawable("Grass_Highlight"));
        swampland = new Button(skin.getDrawable("Swamp_Tile"),skin.getDrawable("Swamp_Tile_Highlight"));
        rock = new Button(skin.getDrawable("Rock_Tile"),skin.getDrawable("Rock_Tile_Highlight"));

        tbl.add(openTerrain).width(170).height(185).padTop(20).padBottom(20).padLeft(10);
        tbl.row();
        tbl.add(grassland).width(170).height(185).padTop(20).padBottom(20).padLeft(10);
        tbl.row();
        tbl.add(swampland).width(170).height(185).padTop(20).padBottom(20).padLeft(10);
        tbl.row();
        tbl.add(rock).width(170).height(185).padTop(20).padBottom(20).padLeft(10);
        tbl.top().left();

        Gdx.input.setInputProcessor(stg);

        openTerrain.addListener(new ClickListener(){
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                if(showTileSelected==false){
                    showTooltip=true;
                    text = "Cost = 1";
                }

            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                showTooltip=false;
            }

            @Override
            public void clicked (InputEvent event, float x, float y) {
                showTooltip=false;
                if(showTileSelected==true){
                    showTileSelected = false;
                }else{
                    showTileSelected = true;
                    newTextureRegion = skin.get("Dirt_Tile", TextureRegion.class);

                }



            }
        });






    }

    public void render(SpriteBatch batch){


        stg.act(Gdx.graphics.getDeltaTime());
        stg.draw();


    }

    public String getText(){
        return text;
    }

    public TextureRegion getTexture(){
        return newTextureRegion;
    }

    public void dispose(){

    }

    public boolean getShowTooltip() {
        return showTooltip;
    }

    public boolean getShowTileSelected() {
        return showTileSelected;
    }
}
