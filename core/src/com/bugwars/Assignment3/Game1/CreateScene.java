package com.bugwars.Assignment3.Game1;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.Array;
import com.bugwars.Helper.AssetMangerAssignment3;

/**
 * This class is responsible for displaying all apporiate textures, game finishing text and
 * their positions
 */
public class CreateScene {

    private AssetMangerAssignment3 assetMgr;
    private TextureRegion board, spiderProf, antProf, currentPiece, antPiece, spiderPiece,currentGlow, endPiece;
    private Array<Disk> disks = new Array<>();
    private Array<Disk> finishDisks = new Array<>();
    private Move move;
    private int rowDepth;
    private Game1 game;
    private int currentPieceX, currentPieceY, currentGlowX, currentGlowY, positionDrop;
    private boolean renderFinish = false;
    private BitmapFont font;
    private String finishText = "";

    public CreateScene(Game1 game){
        this.game = game;
        assetMgr = new AssetMangerAssignment3();
        board = assetMgr.getGameBoard();
        spiderProf = assetMgr.getSpiderProfile();
        antProf = assetMgr.getAntProfile();
        antPiece = assetMgr.getAntDisk();
        spiderPiece = assetMgr.getSpiderDisk();
        endPiece = assetMgr.getEndPiece();
        currentGlowY = 225;

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Retro Gaming.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 48; // font size
        font = generator.generateFont(parameter);

        generator.dispose(); // Once font is generated dispose of the generator


    }

    public void render (SpriteBatch batch){
        batch.draw(board, 292,50);
        batch.draw(spiderProf, 918,275,252,375);
        batch.draw(antProf, 30,275,252,375);
        batch.draw(currentGlow, currentGlowX,currentGlowY,350,500);
        batch.draw(currentPiece,currentPieceX, currentPieceY);
        for(Disk d: disks){ // Show all currently placed disks
            batch.draw(d.getPic(), d.getX(), d.getY());
        }
        if(renderFinish){
            font.draw(batch, finishText, 300, 750);
            if(finishDisks.size != 0){ // Game does not end in a tie, show winning disks
                for(Disk f: finishDisks){
                    batch.draw(f.getPic(), f.getX(), f.getY());
                }
            }

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
     * Show the animation of the game piece dropping and check if the winning piece has been dropped.
     * If winner is found then flag the finishing render
     */
    public void dropPiece(){

        int depth = (rowDepth*100) + 50;
        if(currentPieceY > depth){
            currentPieceY-=5;
        }
        else{
            disks.add(new Disk(currentPiece,currentPieceX, currentPieceY));
            if(game.checkForWinner()){
                renderFinish=true;
            }else{
                game.setCurrentAction();
                game.changeCurrentPlayer();
            }


        }


    }

    /**
     * Show the current piece whether its the ant or spider character currently playing
     * and make the appropriate character picture glow to signify whos turn it is.
     * Ant=0
     * Spider=1
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

/*    public void addDisk(){
        disks.add(new Disk(currentPiece,currentPieceX, currentPieceY));
    }*/

    /**
     * rowDepth is the Row value that the disk will fall to
     * @param rowDepth
     */
    public void setRowDepth(int rowDepth) {
        this.rowDepth = rowDepth;
    }

    /**
     * Depending on who wins, this function will change the game winning/losing text appropriately
     * @param winner
     */
    public void finishScreen(int winner){

        switch(winner){
            case 0:
                finishText = "Ant wins!!";
                break;
            case 1:
                finishText = "Spider wins!!";
                break;
            case 2:
                finishText = "Game Over - Tie!";
                break;
        }

    }

    /**
     * This function takes in the winning moves and coverts them to the 'endPiece' texture
     * and their appropriate pixel coordinates to show on the screen
     * @param moves
     */
    public void finishingPieces(Move[] moves){
        for(Move move: moves){
            int x = move.getX();
            int y = move.getY();
            System.out.println("Finishing Moves: "+ x +", "+y);
            currentPiece = endPiece;
            currentPieceX = y * 88 + 292;
            currentPieceY = (x*100) + 50;
            System.out.println("Finishing coordinates: "+ currentPieceX +", "+currentPieceY);
            finishDisks.add(new Disk(currentPiece,currentPieceX, currentPieceY));
        }
    }
}
