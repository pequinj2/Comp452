package com.bugwars.Assignment3.Game2;

import static com.badlogic.gdx.utils.TimeUtils.millis;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.bugwars.Assignment3.BeamObject;
import com.bugwars.BugWars;
import com.bugwars.Helper.Animator;
import com.bugwars.Helper.AssetManagerA3G2;
import com.bugwars.Helper.BodyHelperService;
import com.bugwars.Helper.CollisionListenerHelper;
import com.bugwars.Helper.TileMapHelper;
import com.bugwars.Objects.Enemy.Centipede;
import com.bugwars.Objects.Pickups.WebSac;
import com.bugwars.Objects.Player.Spider;
import com.bugwars.PauseMenu;

/**
 * This is the main hub of the game that controls most of what happens including camera controls,
 * world and some sprite rendering
 */
public class Game2 implements Screen {

    private SpriteBatch batch, hudBatch; //To render our sprites
    private World world; //To store our box 2D bodies - *** May not need this? Sounds like its for gravity***
    private Box2DDebugRenderer box2DBug;
    private OrthographicCamera camera, hudCamera;
    private int viewPortWidth = 700;
    private int viewPortHeight = 750;
    /*private int viewPortWidth = (int) (700*0.3);
    private int viewPortHeight = (int) (750*0.3);*/

    private int mapWidth = (int)(1216 * 0.3);
    private int mapHeight = (int)(896 *0.3);
    private float stateTime;

    // Objects for our tile map
    private OrthogonalTiledMapRenderer orthoTileRender;
    private TileMapHelper tileMapHelper;
    private Spider spiderPlayer;
    private Centipede centipedeEnemy;
    private Array<Body> centipedeBodies;

    //Animations
    private Animation<TextureRegion> walkAnimation, centipedeMouthAnimation; // Must declare frame type (TextureRegion)
    private TextureRegion centipedeFrame;
    private Animator ani;

    // Textures
    private TextureRegion centipedeBody, centipedeButt;
    private TextureAtlas allTextures;



    // Player Hud
    private PlayerHud hud;

    // Web Sac Pickups
    private WebSac webPickup1, webPickup2, webPickup3;



    // Pause Menu
    private BugWars game;
    private boolean isPaused = false;
    private PauseMenu pauseMenu;

    // End Game
    private EndState currentState;
    private boolean isEndGame = false;

    private int centStartHealth = 100;

    private AssetManagerA3G2 assetMgr = new AssetManagerA3G2();
    private BeamObject beam;



    public Game2(OrthographicCamera camera, BugWars game){
        this.game = game;
        this.camera = camera;
        this.batch = new SpriteBatch();
        hudBatch = new SpriteBatch(); // player and enemy health bars
        this.world = new World(new Vector2(0,0), false); //Gravity is the Vector2
        this.box2DBug = new Box2DDebugRenderer();
        this.pauseMenu = new PauseMenu(this, game, camera);
        pauseMenu.assignment3Game2Listeners();
        this.tileMapHelper = new TileMapHelper(this);
        /**
         * Getting the map we've loaded from TileMapHelper, may need to pass in String name to
         * make it more portable.
         */
        this.orthoTileRender = tileMapHelper.setupMap2();

        // Instantiate the collision listener
        createCollisionListener();

        // Create Spider character ***************************************************
        Body body = BodyHelperService.createBody(
                100 + 16, // Position
                100 + 16, // Position
                16, // Box size
                16, // Box size
                0,
                false,
                world);
        setSpider(new Spider(16, 16, body, 100, world));
        spiderPlayer.setSpeed(5);
        spiderPlayer.setSound(assetMgr);
        // **************************************************************************

        // Initialize Special Boss Beam
        beam = new BeamObject(assetMgr, spiderPlayer);

        // Create Centipede enemy ***************************************************
        Body bodyEnemyHead = BodyHelperService.createBody(
                350, // Position
                620, // Position
                80, // Box size
                80, // Box size
                1,
                false,
                world);

        /*Body bodyEnemyHead = BodyHelperService.createProjectiles(
                world,
                350, // Position
                550, // Position
                80 // Box size
        );*/
        setCentipede(new Centipede(world,128, 128, bodyEnemyHead, centStartHealth));
        centipedeEnemy.initTail(); // initialize the rest of the centipede body
        centipedeEnemy.setBehaviors(spiderPlayer.getBody());
        centipedeEnemy.setBeam(beam);

        // *************************************************************************

        // Get Sprite animations
        ani = new Animator();
        walkAnimation = ani.AnimatorSpider();
        centipedeMouthAnimation = ani.CentipedeMouthAnimator();

        allTextures = new TextureAtlas(Gdx.files.internal("maps/Assignment1TexturePack.atlas"));
        centipedeBody = new TextureRegion(allTextures.findRegion("Centipede_Body"));
        centipedeButt = new TextureRegion(allTextures.findRegion("Centipede_Butt"));
        centipedeEnemy.setSounds(assetMgr);

        // Create world boarders
        BodyHelperService.createGameBorder2(world, mapWidth, mapHeight);
        stateTime = 0f;

        //set Cameras
        camera.rotate(90);
        camera.setToOrtho(false, viewPortWidth, viewPortHeight); // Camera is effecting the size of the tile map shown
        hudCamera = new OrthographicCamera();
        hudCamera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); // Camera is effecting the size of the tile map shown,

        // Initialize player HUD
        hud = new PlayerHud(spiderPlayer.getHealth(),centipedeEnemy.getHealth());

        // Generate Web Sac pickups
        Body bodyWebSac1 = BodyHelperService.createWebSac2(world, mapWidth, mapHeight);
        Body bodyWebSac2 = BodyHelperService.createWebSac2(world, mapWidth, mapHeight);
        Body bodyWebSac3 = BodyHelperService.createWebSac2(world, mapWidth, mapHeight);

        webPickup1 = new WebSac(bodyWebSac1, world);
        webPickup2 = new WebSac(bodyWebSac2, world);
        webPickup3 = new WebSac(bodyWebSac3, world);



        // Initialize the End Game
        currentState = new EndState();

    }

    /**
     * Update sprites, camera locations and any further character actions - such as the Centipede AOE
     */
    private void update(){

        handleInput();

        camera.update();
        hudCamera.update();
        /**
         * https://stackoverflow.com/questions/33703663/understanding-the-libgdx-projection-matrix
         * Call setProjectionMatrix whenever you have moved the camera or resized the screen.
         * Camera.combined describes where things in your Assignment1 world should be rendered onto the screen
         */
        batch.setProjectionMatrix(camera.combined);
        hudBatch.setProjectionMatrix(hudCamera.combined);

        orthoTileRender.setView(camera); // << Why are we doing this?

        // Getting and passing the positions of the Spider to the Centipede for Seeking Algorithm
        spiderPlayer.update2();
        centipedeEnemy.updateBehavior();
        //centipedeEnemy.update();



        // update hud visuals
        hud.update(spiderPlayer.getHealth(), centipedeEnemy.getHealth());


    }

    /**
     * Method that will move the viewport camera around as the player is moving around the level
     * Logic for this was taken from LibGDX tutorial:
     * https://libgdx.com/wiki/graphics/2d/orthographic-camera
     */
    private void handleInput() {
        Vector3 position = camera.position;
        //position.x = Math.round(spiderPlayer.getBody().getPosition().x);
        position.x = 0;
        position.y = Math.round(spiderPlayer.getBody().getPosition().y)+20;
        camera.position.set(position);

        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
            isPaused = !isPaused;
            System.out.println(isPaused);

        }

        // Stop the camera position so it doesn't go out of bounds
        //camera.position.x = MathUtils.clamp(camera.position.x, viewPortWidth/2f, mapWidth-viewPortWidth/2f );
        camera.position.y = MathUtils.clamp(camera.position.y, viewPortHeight/2f, mapHeight );


    }

    @Override
    public void show() {

    }

    /**
     * Main render method for the player and enemy sprites, also checks if pause is requested and
     * if game end state has been reached
     * @param delta
     */
    @Override
    public void render(float delta) {
        if(isPaused){ // check if game is paused
            delta =0;
            pause();
        }else {
           // System.out.println(centipedeEnemy.getBody().getPosition());
          //  System.out.println("Spider: "+spiderPlayer.getBody().getPosition());

            /**
             * World.step explanation
             * tells the physics engine that 1/60th of a second has passed every time you call it.
             * If your Assignment1 loop is being called more than 60 times a second it will go fast; less than
             * 60 times a second and it'll be slow. The number of times it gets called per second will
             * depend on the speed of the underlying hardware, so this method will end up in different
             * behavior on different devices.
             * https://gamedev.stackexchange.com/questions/144847/box2d-world-step-on-android-game-using-libgdx
             */
            world.step(delta, 6, 2);


            Gdx.gl.glClearColor(0, 0, 0, 1); // Clear the previous screen of anything
            /**
             * https://stackoverflow.com/questions/34164309/gl-color-buffer-bit-regenerating-which-memory
             * Explains why clearing the color buffer is important
             */
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            stateTime += Gdx.graphics.getDeltaTime();
            // Render our tile map before the Assignment1 objects
            orthoTileRender.render();
            //centipedeEnemy.moveBoss(spiderPlayer.getBody());
            // Get current frame of animation for the current stateTime
            TextureRegion spiderFrame = walkAnimation.getKeyFrame(stateTime, true); // Spider Animation Walking
            centipedeFrame = centipedeMouthAnimation.getKeyFrame(stateTime, true);

            if(spiderPlayer.getHealth() <= 0.0){ // Player lost, run end game screen
                hud.update(spiderPlayer.getHealth(), centipedeEnemy.getHealth());
                centipedeEnemy.updateStop();
                batch.begin();
                centipedeRender();
                batch.end();
                currentState.loseStateRender();
                handleEndGameInput();

            }else if(centipedeEnemy.getHealth() <= 0.0){ // Player wins, run end game screen
                hud.update(spiderPlayer.getHealth(), centipedeEnemy.getHealth());
                centipedeEnemy.updateStop();
                currentState.winStateRender(centipedeEnemy, centipedeBodies, camera.combined, stateTime);
                handleEndGameInput();


            }else {
                this.update();
                batch.begin();
                // Draw Web Sac Pickups
                webPickup1.render(batch);
                webPickup2.render(batch);
                webPickup3.render(batch);

                // Web shooter spawn
                spiderPlayer.render(batch);

                // Centipede aoe Shot
                centipedeEnemy.render2(batch);

                // Render the Centipede (used in more then 1 location, therefore, made into a helper method
                centipedeRender();

                // If the beam has been fired render beam
                if(beam.beamRunning()){
                    //System.out.println("Render Beam");
                    beam.renderBeam(batch);
                }


                // Draw spider player
                int position = spiderPlayer.getRotation(); // Holds the rotation value so the player sprite is facing the right way
                batch.draw(spiderFrame,
                        spiderPlayer.getX(), // Position
                        spiderPlayer.getY(), // Position
                        spiderPlayer.getWidth() / 2, // Center of character
                        spiderPlayer.getHeight() / 2, // Center of character
                        spiderFrame.getRegionWidth(),
                        spiderFrame.getRegionHeight(),
                        1, //Resize
                        1,
                        position); // Rotation
                batch.end();
            }


            // ********* COMMENT THIS LINE OUT WHEN IN PROD ***********
            // commenting out will remove collision boxes
            box2DBug.render(world, camera.combined);//<<- PPM = Pixel Per Meters

            // Render player and enemy health bars
            hudBatch.begin();
            hud.render(hudBatch);
            hudBatch.end();
        }


    }

    @Override
    public void resize(int width, int height) {

    }

    /**
     * Pause menu caller
     */
    @Override
    public void pause() {
        handleInput();
        pauseMenu.render();

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        batch.dispose();
        spiderPlayer.dispose();
        centipedeEnemy.dispose();
        ani.dispose();
        tileMapHelper.dispose();
        allTextures.dispose();
        webPickup1.dispose();
        webPickup2.dispose();
        webPickup3.dispose();
        hudBatch.dispose();
        pauseMenu.dispose();

    }

    public World getWorld() {
        return world;
    }

    public void setSpider (Spider spider){
        this.spiderPlayer = spider;

    }

    public void setCentipede (Centipede centipede){
        this.centipedeEnemy = centipede;
    }


    /**
     * Helper function that will initiate the creation of Box2D's contact listener
     * How to setup Box2D's contact listener:
     * https://stackoverflow.com/questions/15453176/how-to-use-the-libgdx-contactlistener by Rod Hyde
     * Thorough explanation of Box2D's contact box listener:
     * https://www.youtube.com/watch?v=pJ_M_fACtB8 by The Coding Train
     */
    private void createCollisionListener(){
        CollisionListenerHelper colListen = new CollisionListenerHelper(world);
    }

    /**
     * The centipede is rendered in a couple of different places depending on what state the game is
     * in (RUNNING or LOSE). code put in helper method to unclog 'render' function
     */
    private void centipedeRender(){
        //Draw centipede animation
        batch.draw(centipedeFrame,
                centipedeEnemy.getX() - 8, // Position
                centipedeEnemy.getY() - 8, // Position
                8, // Center of character
                8, // Center of character
                16,
                16,
                4, //Resize
                4,
                180); // Rotation
        // Draw centipede body

        /*for (Body centipede : centipedeBodies) {

            batch.draw(centipedeBody,
                    centipede.getPosition().x, // Position
                    centipede.getPosition().y, // Position
                    16, // Center of character
                    16, // Center of character
                    centipedeBody.getRegionWidth(),
                    centipedeBody.getRegionHeight(),
                    2, //Resize
                    2,
                    0); // Rotation

        }*/
        int bodyOffSets = 320;
        float buttX = centipedeEnemy.getCentipedeButt().getPosition().x;
        float buttY = centipedeEnemy.getCentipedeButt().getPosition().y;

        // Draw centipede butt
        batch.draw(centipedeButt,
                buttX, // Position
                buttY, // Position
                8, // Center of character
                8, // Center of character
                16,
                16,
                4, //Resize
                4,
                90); // Rotation

        for(int i =0; i<4; i++){
            buttX = buttX-50;

            batch.draw(centipedeBody,
                    buttX, // Position
                    buttY, // Position
                    8, // Center of character
                    8, // Center of character
                    16,
                    16,
                    4, //Resize
                    4,
                    0); // Rotation
        }



    }

    /**
     * Check if user has reached the end of the game - if so get 'Retry or Quit' screen ready
     */
    private void handleEndGameInput(){
        if(Gdx.input.isTouched() && isEndGame==false) {
            isEndGame = true;
        }
        if(isEndGame){
            pauseMenu.endScreenMenu();
        }
    }

    /**
     * Based on the spider's location and centipede health, set the weights on the centipede's
     * available attacks to make the proper decision.
     */
    private void weightCounter(){
        float spidX = spiderPlayer.getBody().getPosition().x;
        float spidY = spiderPlayer.getBody().getPosition().y;
        float centX = centipedeEnemy.getBody().getPosition().x;
        float centHealth = centipedeEnemy.getHealth();


        if(spidY >= (mapHeight-90)){
            // Check if the spider is directly below the Centipede - if so increase 'Lunge' attack weight
            if((spidX <= centX && spidX >= (centX-80)) || (spidX >= centX && spidX <= (centX+80))){

            }

        }else if(spidY <= (mapHeight-180)){
            //check if player is in lower half of the screen
            if(centHealth <= (centStartHealth*0.7)){
                // increase 'TailSwipe' attack weight
            }
            if(centHealth <= (centStartHealth*0.5)){
                // increase 'Beam' attack weight
            }

        }else{

        }

    }

    private void renderBeamShot(){

    }

}
