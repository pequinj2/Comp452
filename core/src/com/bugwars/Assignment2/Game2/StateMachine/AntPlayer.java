package com.bugwars.Assignment2.Game2.StateMachine;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.bugwars.Assignment2.Game2.AntMovement.AntPlayerMovement;
import com.bugwars.Assignment2.Game2.CreateScene;
import com.bugwars.Assignment2.Game2.Game2;
import com.bugwars.Assignment2.Game2.States.Death;
import com.bugwars.Assignment2.Game2.States.FindFood;
import com.bugwars.Assignment2.Game2.States.FindHome;
import com.bugwars.Assignment2.Game2.States.FindWater;

public class AntPlayer {

    // https://www.youtube.com/watch?v=OjreMoAG9Ec
    private StateManager stateManager;
    private AntPlayerState currentState;
    public FindFood findFoodState;
    public Death foundDeath;
    public FindHome findHome;
    public FindWater findWater;

    private AntPlayerData playerData;
    public AntPlayerMovement movement;
    private TextureRegion antPic;
    private TextureRegion antPicBerry;
    private TextureRegion antPicWater;
    private TextureRegion antCurrentPic;
    public CreateScene map;

    private boolean antAlive = true;
    private Game2 game;

    public int antID;

    /**
     * When the ant is created, initialize the state machine
     */
    public void Awake(int antHome, TextureRegion antPic, TextureRegion antPicBerry, TextureRegion antPicWater,CreateScene map, int antID, Game2 game){
    System.out.println("Awake");
        this.stateManager = new StateManager();
        currentState = new AntPlayerState(this, stateManager);
        this.movement = new AntPlayerMovement(antHome);
        this.map = map;
        this.antID = antID;
        this.game = game;
        this.antPic = antPic;
        this.antPicBerry = antPicBerry;
        this.antPicWater = antPicWater;
        antCurrentPic = antPic;
        findFoodState = new FindFood(this, stateManager );
        foundDeath = new Death(this, stateManager );
        findHome = new FindHome(this, stateManager );
        findWater =  new FindWater(this, stateManager );
        Start();

    }

    private void Start(){
        stateManager.Initialize(findFoodState);

    }

    public void Update(){
        currentState = stateManager.getCurrentState();
        currentState.Update();
    }

    public int antCurrentPos(){
        return movement.getCurrentCellId();
    }

    public int antPreviousPos(){
        return movement.getPreviousCellID();
    }

    /**
     * 'Death' state will set 'antAlive' variable to false to indicate death
     */
    public void dispose() {
        antAlive = false;
    }

    /**
     * Return if the ant is alive
     * @return
     */
    public boolean getAntAlive(){
        return antAlive;
    }

    /**
     * 'FindFood' state will call this function to see if the ant is stepping on a berry
     * @return
     */
    public boolean checkBerry(){
        String currentCell = map.checkCell(antCurrentPos());
        if(currentCell.equals("Berry")){
            System.out.println("Berry found");
            // Set the ant picture to the 'pink' ant to indicate it has a berry and looking for home
            antCurrentPic = antPicBerry;
            return true;

        }
        return false;


    }

    /**
     * 'FindFood', 'FindHome' and 'FindWater' states will call this function to see if the ant is
     * stepping on poison
     * @return
     */
    public boolean checkPoison(){
        String currentCell = map.checkCell(antCurrentPos());
        if(currentCell.equals("Poison")){
            //TODO double check what is setting the ant alive or dead - add sound bite
            System.out.println("Death found");
            return true;

        }

        return false;
    }

    /**
     * 'FindHome' state will call this function to see if the ant is stepping on an AntHill
     * @return
     */
    public boolean checkHome(){

        String currentCell = map.checkCell(antCurrentPos());
        System.out.println("Anthill check "+currentCell+ " at pos " + antCurrentPos());
        if(currentCell.equals("Home")){
            game.makeNewAnt();
            System.out.println("Anthill found");
            // Set the ant picture to the 'blue' ant to indicate its thirsty and looking for water
            antCurrentPic = antPicWater;
            return true;

        }

        return false;
    }

    /**
     * 'FindWater' state will call this function to see if the ant is stepping on water
     * @return
     */
    public boolean checkWater(){

        String currentCell = map.checkCell(antCurrentPos());
        System.out.println("Water check "+currentCell+ " at pos " + antCurrentPos());
        if(currentCell.equals("Water")){
            System.out.println("Water found");
            // Set the ant picture back to normal
            antCurrentPic = antPic;
            return true;

        }

        return false;
    }

    public TextureRegion getAntPic(){
        return antCurrentPic;
    }
}
