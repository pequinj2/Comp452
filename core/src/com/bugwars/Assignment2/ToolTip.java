package com.bugwars.Assignment2;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector3;

public class ToolTip {

    private Texture tooltipLabel;
    private float x, y, height, width;
    private OrthographicCamera camera;
    private Vector3 mouse;
    private BitmapFont font;
    private String text = "";
    private TextureRegion selectedTile;



    public ToolTip(OrthographicCamera camera){
        this.camera = camera;
        tooltipLabel = new Texture(Gdx.files.internal("Assignment2/ToolTip.png"));

        height=tooltipLabel.getHeight();
        width=tooltipLabel.getWidth();

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Retro Gaming.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 25; // font size
        font = generator.generateFont(parameter);
        font.setColor(Color.BLACK);
        generator.dispose(); // Once font is generated dispose of the generator
    }

    private void update(){
        mouse = new Vector3(Gdx.input.getX(), Gdx.input.getY(),0);
        camera.unproject(mouse);

    }

    public void render(SpriteBatch batch){
        update();

        batch.begin();
        batch.draw(tooltipLabel, mouse.x, mouse.y);
        font.draw(batch, text,mouse.x + width/5, mouse.y+height/1.4f);
        batch.end();
    }

    public void renderTileLabel(SpriteBatch batch){
        update();

        batch.begin();
        batch.draw(selectedTile, mouse.x, mouse.y,64,64);
        batch.end();
    }

    public void setText(String newText){
        text = newText;

    }

    public void setLabel(TextureRegion newLabel){
        text = "";
        selectedTile = newLabel;

    }


}
