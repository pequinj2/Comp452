package com.bugwars.Assignment2.Game2;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.bugwars.Helper.AssetManager;


public class Hud {

    private BitmapFont font;
    private TextureRegion antPic;
    private int numOfAnts, numOfDeadAnts;
    private String aliveAnts, deadAnts;

    private Stage stg = new Stage();
    private TextField num;

    public Hud(AssetManager assetMgr){

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Retro Gaming.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 30; // font size
        font = generator.generateFont(parameter);

        generator.dispose(); // Once font is generated dispose of the generator

        antPic = assetMgr.getAnt();


        numOfAnts = 0;
        numOfDeadAnts = 0;
        aliveAnts = "Number of ants alive: " + numOfAnts;
        deadAnts = "Number of ants dead: " + numOfDeadAnts;

        TextField.TextFieldStyle sty = new TextField.TextFieldStyle();
        sty.background = assetMgr.getSkin();
        sty.font = font;
        sty.fontColor = Color.WHITE;
        num = new TextField("",sty);
        num.setWidth(300);
        num.setHeight(100);
        num.setX(300);
        num.setY(300);

        stg.addActor(num);


    }

    private void update(){
        aliveAnts = "Number of ants alive: " + numOfAnts;
        deadAnts = "Number of ants dead: " + numOfDeadAnts;
    }
    public void render(SpriteBatch batch){
        update();
        font.draw(batch, aliveAnts, 40, 850);
        font.draw(batch, deadAnts, 40, 800);

    }

    public void setAliveAnts(){
        numOfAnts++;
    }
    public void setDeadAnts(){
        numOfDeadAnts++;
    }

    public Stage getStg(){
        return stg;
    }

    public Boolean getText(){
        String temp  = num.getText();


        if(isNumeric(temp)){
            numOfAnts = Integer.valueOf(temp);
            return true;

        }
        else{
            return false;
        }
    }

    private static boolean isNumeric(String str){
        return str != null && str.matches("[0-9.]+");
    }
}
