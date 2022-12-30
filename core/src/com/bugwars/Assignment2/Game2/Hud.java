package com.bugwars.Assignment2.Game2;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.Align;
import com.bugwars.Assignment2.Game2.StateMachine.AntPlayer;
import com.bugwars.Helper.AssetManager;

import java.util.ArrayList;

/**
 * Will display the number of ants alive and dead at a time, as well as, initiate the starting value
 * ants
 */
public class Hud {

    private BitmapFont font;
    private int numOfAnts, numOfDeadAnts, numOfAntsWater, numOfAntsHome, numOfAntsBerry, numOfAntsFromBerry;
    private String aliveAnts, deadAnts, aliveAntsTotal, antsLookingForWater, antsLookingForHome, antsLookingForFood;

    private Stage stg = new Stage();
    private TextField num;
    private Label label;

    private AssetManager assetMgr;


    public Hud(AssetManager assetMgr){

        this.assetMgr = assetMgr;
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Retro Gaming.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 30; // font size
        font = generator.generateFont(parameter);

        generator.dispose(); // Once font is generated dispose of the generator

        // Set Starting statistics
        numOfAnts = 0;
        numOfDeadAnts = 0;
        numOfAntsBerry = 0;
        numOfAntsWater = 0;
        numOfAntsHome = 0;
        numOfAntsFromBerry = 0;
        aliveAnts = "Number of ants alive: " + numOfAnts;
        deadAnts = "Number of ants dead: " + numOfDeadAnts;

        // Set the staring area with TextField Btn to get user input
        TextField.TextFieldStyle sty = new TextField.TextFieldStyle();
        sty.background = assetMgr.getBtnUp();
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

    /**
     * Render current number of Alive and Dead ant numbers
     * @param batch
     */
    public void render(SpriteBatch batch){
        update();
        font.draw(batch, aliveAnts, 40, 850);
        font.draw(batch, deadAnts, 40, 800);

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
     * Check if the user has only put in numeric numbers
     * @param str
     * @return true if only numeric, else, false
     */
    private static boolean isNumeric(String str){
        return str != null && str.matches("[0-9.]+");
    }

    public int getNumOfAnts() {
        return numOfAnts;
    }

    /**
     * Render the final screen of stats
     * @param batch
     */
    public void renderFinal(SpriteBatch batch){
        Gdx.gl.glClearColor(0, 0, 0, 1); // Clear the previous screen of anything
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        font.draw(batch, aliveAnts, 40, 830);
        font.draw(batch, deadAnts, 40, 760);
        font.draw(batch, aliveAntsTotal, 40, 690);
        font.draw(batch, antsLookingForFood, 40, 620);
        font.draw(batch, antsLookingForWater, 40, 550);
        font.draw(batch, antsLookingForHome, 40, 480);

    }

    /**
     * Go through the ant array and tally up all the ants and which category they fit in, from there
     * create the strings at the end to display to the user
     * @param ants
     */
    public void setFinalResults(ArrayList<AntPlayer> ants){
        numOfAntsBerry = 0;
        numOfAntsHome = 0;
        numOfAntsWater = 0;
        for(AntPlayer ant: ants){
            TextureRegion temp = ant.getAntPic();
            if(temp.equals(assetMgr.getAnt())){
                numOfAntsBerry++; // Add ant looking for food

            }
            else if(temp.equals(assetMgr.getAntBerry())){
                numOfAntsHome++; // Add ant looking for home

            }
            if(temp.equals(assetMgr.getAntWater())){
                numOfAntsWater++; // Add ant looking for water

            }

        }
        aliveAnts = "Total of ants alive at end: " + numOfAnts;
        deadAnts = "Number of ants dead: " + numOfDeadAnts;
        aliveAntsTotal = "Ants created from berries: " + numOfAntsFromBerry;
        antsLookingForFood = "Ants still looking for berries: " + numOfAntsBerry;
        antsLookingForWater = "Ants still looking for water: " + numOfAntsWater;
        antsLookingForHome = "Ants still looking for the Ant Hill: " + numOfAntsHome;
    }

    public void setAliveAnts(){
        numOfAnts++; // Total # of ants
        numOfAntsFromBerry++; // Total ants created from berries


    }
    public void setDeadAnts(){
        numOfAnts--;
        numOfDeadAnts++;
    }





}
