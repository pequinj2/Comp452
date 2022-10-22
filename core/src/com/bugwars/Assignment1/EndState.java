package com.bugwars.Assignment1;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import com.bugwars.Helper.Animator;
import com.bugwars.Objects.Enemy.Centipede;

public class EndState {

    private TextureAtlas endGameTextures;
    private Texture winLabel = new Texture(Gdx.files.internal("End Game/You_Win_Label.png"));
    private Texture loseLabel = new Texture(Gdx.files.internal("End Game/You_Died_Label.png"));
    private Animation<TextureRegion> winAnimation, loseAnimation;
    private Animator ani;
    private float clock = 0;

    private TextureRegion centipedeHead, centipedeBody, centipedeButt, spiderBody, spiderGhost_1, spiderGhost_2;
    private TextureRegion spidWeb, spidBody, winFrame;

    public enum EndGameStat{
        WIN,LOSE, RUNNING
    }

    private EndGameStat currentEndGameState;
    private SpriteBatch endBatch = new SpriteBatch();
    private SpriteBatch labelBatch = new SpriteBatch();

    public EndState(){
        ani = new Animator();
        endGameTextures = new TextureAtlas(Gdx.files.internal("End Game/EndState.atlas"));
        winAnimation = ani.winGameAnimator();
        loseAnimation = ani.loseGameAnimator();
        //Win textures
        centipedeHead = new TextureRegion(endGameTextures.findRegion("Centipede_Dead_Head"));
        centipedeBody = new TextureRegion(endGameTextures.findRegion("Centipede_Dead_Body"));
        centipedeButt = new TextureRegion(endGameTextures.findRegion("Centipede_Dead_Butt"));
        spidWeb = new TextureRegion(endGameTextures.findRegion("You_Win_Web"));
        spidBody = new TextureRegion(endGameTextures.findRegion("You_Win_Spider"));
        // Lose textures
        spiderBody = new TextureRegion(endGameTextures.findRegion("You_Died_Spider"));
        spiderGhost_1 = new TextureRegion(endGameTextures.findRegion("You_Died_Start"));
        spiderGhost_2 = new TextureRegion(endGameTextures.findRegion("You_Died_Ghost"));

        currentEndGameState = EndGameStat.RUNNING;

    }


    public void winStateRender(Centipede enemyHead, Array<Body> enemyBodies, Matrix4 cam, float stateTime){

        endBatch.setProjectionMatrix(cam);
        endBatch.begin();
        currentEndGameState = EndGameStat.WIN;


        endBatch.draw(centipedeHead,
                enemyHead.getX()-8,
                enemyHead.getY()-8,
                8, // Center of character
                8, // Center of character
                16,
                16,
                2, //Resize
                2,
                0); // Rotation);
        for (Body centipede : enemyBodies) {
            endBatch.draw(centipedeBody,
                    centipede.getPosition().x, // Position
                    centipede.getPosition().y, // Position
                    16, // Center of character
                    16, // Center of character
                    centipedeBody.getRegionWidth(),
                    centipedeBody.getRegionHeight(),
                    2, //Resize
                    2,
                    0); // Rotation

        }
        endBatch.draw(centipedeButt,
                enemyHead.getCentipedeButt().getPosition().x-8,
                enemyHead.getCentipedeButt().getPosition().y-8,
                8, // Center of character
                8, // Center of character
                16,
                16,
                2, //Resize
                2,
                0); // Rotation););

        endBatch.end();
        winFrame = winAnimation.getKeyFrame(stateTime, true);
        labelBatch.begin();
        labelBatch.draw(winLabel,30,30, 1050, 700);
        labelBatch.draw(spidWeb, 127,85,70,400);
        labelBatch.draw(spidBody, 105,30,100,100);
        labelBatch.draw(winFrame, 60,40,200,80);
        labelBatch.end();
    }

    public void loseStateRender(){
        endBatch.begin();
        currentEndGameState = EndGameStat.LOSE;
        clock += Gdx.graphics.getDeltaTime();
        endBatch.draw(loseLabel,30,30, 1050, 700);
        endBatch.draw(spiderBody, 1040, 100, 50,50);
        if (clock > 1){ // Animation Timer
            endBatch.draw(spiderGhost_1, 1050, 140, 25,50);
            if(clock > 2){
                endBatch.draw(spiderGhost_2, 1040, 200, 50,60);
            }
            if(clock > 3) {
                clock = 0;
            }

        }
        endBatch.end();


    }



}
