package com.bugwars.Assignment2.Game2;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.Align;
import com.bugwars.Helper.AssetManager;

/**
 * Will display the number of ants alive and dead at a time, as well as, initiate the starting value
 * ants
 */
public class Hud {

    private BitmapFont font;
    private int numOfAnts, numOfDeadAnts;
    private String aliveAnts, deadAnts;

    private Stage stg = new Stage();
    private TextField num;
    private Label label;

    public Hud(AssetManager assetMgr){

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Retro Gaming.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 30; // font size
        font = generator.generateFont(parameter);

        generator.dispose(); // Once font is generated dispose of the generator

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
        num.setX(450);
        num.setY(400);
        num.setAlignment(Align.center);

        Label.LabelStyle labelSty = new Label.LabelStyle();
        labelSty.font = font;
        labelSty.fontColor = Color.WHITE;
        label = new Label("Enter number of starting ants then press <Enter>", labelSty);
        label.setX(150);
        label.setY(520);

        stg.addActor(num);
        stg.addActor(label);


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

    /**
     * Check if text entered by user is numeric or not
     * @return
     */
    public Boolean getText(){
        String temp  = num.getText();

        if(isNumeric(temp)){
            numOfAnts = Integer.valueOf(temp);
            return true; // Text is numeric

        }
        else{
            label.setText("Only enter numbers - please try again");
            return false;
        }
    }

    /**
     *
     * @param str
     * @return
     */
    private static boolean isNumeric(String str){
        return str != null && str.matches("[0-9.]+");
    }

    public int getNumOfAnts() {
        return numOfAnts;
    }
}
