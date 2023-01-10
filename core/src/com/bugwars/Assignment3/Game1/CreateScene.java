package com.bugwars.Assignment3.Game1;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.bugwars.Helper.AssetMangerAssignment3;


public class CreateScene {

    private AssetMangerAssignment3 assetMgr;
    private TextureRegion board, spiderProf, antProf, currentPiece, antPiece, spiderPiece, currentGlow;
    private Array<Disk> disks = new Array<>();
    private Move move;
    private int rowDepth;
    private Game1 game;

    private int currentPieceX, currentPieceY, currentGlowX, currentGlowY, positionDrop;

    public CreateScene(Game1 game){
        this.game = game;
        assetMgr = new AssetMangerAssignment3();
        board = assetMgr.getGameBoard();
        spiderProf = assetMgr.getSpiderProfile();
        antProf = assetMgr.getAntProfile();
        antPiece = assetMgr.getAntDisk();
        spiderPiece = assetMgr.getSpiderDisk();
        currentGlowY = 225;


    }

    public void render (SpriteBatch batch){
        batch.draw(board, 292,50);
        batch.draw(spiderProf, 918,275,252,375);
        batch.draw(antProf, 30,275,252,375);
        batch.draw(currentGlow, currentGlowX,currentGlowY,350,500);
        batch.draw(currentPiece,currentPieceX, currentPieceY);
        for(Disk d: disks){
            batch.draw(d.getPic(), d.getX(), d.getY());
        }


    }

    /**
     * Change the current game piece's position based on the input from the user
     * @param position
     */
    public void movePiece(int position){
        currentPieceX = position * 88 + 292;
        currentPieceY = 670;

    }

    /**
     * Show the animation of the game piece dropping
     * @param position
     */
    public void dropPiece(int position){

        int depth = (rowDepth*100) + 50;
        if(currentPieceY > depth){
            currentPieceY-=5;
        }
        else{
            disks.add(new Disk(currentPiece,currentPieceX, currentPieceY));
            game.setCurrentAction(1);
        }


    }

    /**
     * Show the current piece whether its the ant or spider character currently playing
     * @param i
     */
    public void currentPiece(int i) {
        if(i == 0){
            currentPiece = antPiece;
            currentGlow = assetMgr.getAntGlow();
            currentGlowX = -30;

        }
        else{
            currentPiece = spiderPiece;
            currentGlow = assetMgr.getSpiderGlow();
            currentGlowX = 858; // 918-60

        }
    }

    public void addDisk(){
        disks.add(new Disk(currentPiece,currentPieceX, currentPieceY));
    }

    public void setRowDepth(int rowDepth) {
        this.rowDepth = rowDepth;
    }
}
