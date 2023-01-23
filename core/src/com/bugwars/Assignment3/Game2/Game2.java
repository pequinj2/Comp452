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
import com.badlogic.gdx.utils.TimeUtils;
import com.bugwars.Assignment1.EndState;
import com.bugwars.Assignment3.Game2.Objects.Ants.Ant;
import com.bugwars.BugWars;
import com.bugwars.Helper.Animator;
import com.bugwars.Helper.BodyHelperService;
import com.bugwars.Helper.TileMapHelper;
import com.bugwars.Objects.Enemy.Centipede2;
import com.bugwars.Objects.Pickups.WebSac;
import com.bugwars.PauseMenu;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Game2 implements Screen {

    private OrthographicCamera camera, hudCamera;
    private SpriteBatch batch, hudBatch; //To render our sprites
    private World world;
    private Box2DDebugRenderer box2DBug;
    private int viewPortWidth = 400;
    private int viewPortHeight = 450;
    private float stateTime;
    private EndState currentState;
    private boolean isEndGame = false;
    private Random random;

    // Pause Menu
    private boolean isPaused = false;
    private PauseMenu pauseMenu;

    //Animations
    private Animation<TextureRegion> antAnimation, centipedeMouthAnimation; // Must declare frame type (TextureRegion)
    private TextureRegion centipedeFrame, antFrame;
    private Animator ani;

    // Textures
    private TextureRegion centipedeBody, centipedeButt;
    private TextureAtlas allTextures;

    // Objects for our tile map
    private OrthogonalTiledMapRenderer orthoTileRender;
    private TileMapHelper tileMapHelper;
    private Centipede2 centipedeEnemy;
    private Array<Body> centipedeBodies;

    // Character AI
    private long currentTime;

    // Player Hud
    private Hud hud;

    // Web Sac Pickups
    private WebSac webPickup1, webPickup2, webPickup3;

    // Pause Menu
    private BugWars game;

    // Keep track of Ants
    private Array<Ant> ants = new Array<Ant>();
    private long startTime = TimeUtils.millis();

    private List<Integer> clockWise = Arrays.asList(0,3,1,2);
    private int currentMove = 0;

    public Game2(OrthographicCamera camera, BugWars game){
        this.camera = camera;
        pauseMenu = new PauseMenu(this, game, camera);
        pauseMenu.assignment3Game2Listeners();

        this.game = game;
        this.camera = camera;
        this.batch = new SpriteBatch();
        hudBatch = new SpriteBatch(); // player and enemy health bars
        this.world = new World(new Vector2(0,0), false); //Gravity is the Vector2
        this.box2DBug = new Box2DDebugRenderer();

        this.tileMapHelper = new TileMapHelper(this);
        /**
         * Getting the map we've loaded from TileMapHelper, may need to pass in String name to
         * make it more portable.
         */
        this.orthoTileRender = tileMapHelper.setupMap1();



        // Instantiate the collision listener
        createCollisionListener();

        // Create Centipede enemy ***************************************************
        Body bodyEnemyHead = BodyHelperService.createBody(
                100 + 16, // Position
                100 + 16, // Position
                32, // Box size
                32, // Box size
                1,
                false,
                world);

        setCentipede(new Centipede2(world,16, 16, bodyEnemyHead, 30));
        centipedeEnemy.initBody(world); // initialize the rest of the centipede body
        centipedeEnemy.initDistanceJoint(world);
        centipedeBodies = centipedeEnemy.getCentipede();
        centipedeBodies = centipedeEnemy.getCentipede();
        centipedeEnemy.setMaxSpeed(1200);

        // *************************************************************************

        // Get Sprite animations
        ani = new Animator();
        centipedeMouthAnimation = ani.CentipedeMouthAnimator();
        antAnimation = ani.antAnimator();

        allTextures = new TextureAtlas(Gdx.files.internal("maps/Assignment1TexturePack.atlas"));
        centipedeBody = new TextureRegion(allTextures.findRegion("Centipede_Body"));
        centipedeButt = new TextureRegion(allTextures.findRegion("Centipede_Butt"));


        // Create world boarders
        BodyHelperService.createGameBorder(world);
        stateTime = 0f;

        //set Cameras
        camera.setToOrtho(false, viewPortWidth, viewPortHeight); // Camera is effecting the size of the tile map shown
        hudCamera = new OrthographicCamera();
        hudCamera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); // Camera is effecting the size of the tile map shown,

        // Initialize player HUD
        hud = new Hud(100);

        // Generate Web Sac pickups
        Body bodyWebSac1 = BodyHelperService.createWebSac(world);
        Body bodyWebSac2 = BodyHelperService.createWebSac(world);
        Body bodyWebSac3 = BodyHelperService.createWebSac(world);

        webPickup1 = new WebSac(bodyWebSac1, world);
        webPickup2 = new WebSac(bodyWebSac2, world);
        webPickup3 = new WebSac(bodyWebSac3, world);


        // Initialize the End Game
        currentState = new EndState();
        random = new Random();
        //createAnt();

    }



    @Override
    public void show() {

    }

    public void update(){
        
        centipedeEnemy.checkUserInput(currentMove); // Update with action
        int index = clockWise.indexOf(currentMove);
        if(index == 3){
            index = 0;
        }else{
            index++;
        }
        currentMove = clockWise.get(index);
        Vector3 position = camera.position;
        position.x = Math.round(centipedeEnemy.getHead().getPosition().x);
        position.y = Math.round(centipedeEnemy.getHead().getPosition().y);

        camera.position.set(position);

        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
            isPaused = !isPaused;
            System.out.println(isPaused);

        }

        // Stop the camera position so it doesn't go out of bounds
        camera.position.x = MathUtils.clamp(camera.position.x, viewPortWidth/2f, 851-viewPortWidth/2f );
        camera.position.y = MathUtils.clamp(camera.position.y, viewPortHeight/2f, 627-viewPortHeight/2f );

        hud.update(centipedeEnemy.getHealth());


    }

    @Override
    public void render(float delta) {
        if(isPaused){ // check if game is paused
            pause();
        }else {
            update();
            camera.update();
            hudCamera.update();
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
            // Render environment and setup cameras
            batch.setProjectionMatrix(camera.combined);
            hudBatch.setProjectionMatrix(hudCamera.combined);
            orthoTileRender.setView(camera);
            box2DBug.render(world, camera.combined);
            orthoTileRender.render();

            // Get current frame of animation for the current stateTime
            centipedeFrame = centipedeMouthAnimation.getKeyFrame(stateTime, true);
            // Get current frame of animation for the current stateTime
            antFrame = antAnimation.getKeyFrame(stateTime, true);

            if(centipedeEnemy.getHealth() <= 0.0){ // Player lost, rerun test
                resetGame();

            }else if(centipedeEnemy.getHealth() <= 0.0){ // Player wins, run end game screen



            }else {
                update();
                batch.begin();
                // Draw Web Sac Pickups
                webPickup1.render(batch);
                webPickup2.render(batch);
                webPickup3.render(batch);

                // Render the Centipede (used in more then 1 location, therefore, made into a helper method)
                centipedeRender();
                for(Ant ant: ants){ // Go through the Ant array and render all ants
                    ant.update();
                    antRender(ant);
                }

                batch.end();
                // Remove all the dead ants
                removeAnt();

            }
            if((TimeUtils.millis()-startTime) > 10000){
                if(ants.size != 1){
                    createAnt();
                }

                startTime=TimeUtils.millis();
                //startTime=TimeUtils.millis()*10;
            }else{

                //System.out.println(startTime);
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

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    /**
     * Helper function that will initiate the creation of Box2D's contact listener
     * How to setup Box2D's contact listener:
     * https://stackoverflow.com/questions/15453176/how-to-use-the-libgdx-contactlistener by Rod Hyde
     * Thorough explanation of Box2D's contact box listener:
     * https://www.youtube.com/watch?v=pJ_M_fACtB8 by The Coding Train
     */
    private void createCollisionListener(){
        CollisionListenerHelper2 colListen = new CollisionListenerHelper2(world, this);
    }

    public void setCentipede (Centipede2 centipede){
        this.centipedeEnemy = centipede;
    }

    /**
     * The centipede is rendered in a couple of different places depending on what state the game is
     * in (RUNNING or LOSE). code put in helper method to unclog 'render' function
     */
    private void centipedeRender(){
        // Draw centipede body

        for (Body centipede : centipedeBodies) {

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

        }
        // Draw centipede butt
        batch.draw(centipedeButt,
                centipedeEnemy.getCentipedeButt().getPosition().x, // Position
                centipedeEnemy.getCentipedeButt().getPosition().y, // Position
                16, // Center of character
                16, // Center of character
                centipedeButt.getRegionWidth(),
                centipedeButt.getRegionHeight(),
                2, //Resize
                2,
                0); // Rotation
        batch.draw(centipedeFrame,
                centipedeEnemy.getHead().getPosition().x - 8, // Position
                centipedeEnemy.getHead().getPosition().y - 8, // Position
                8, // Center of character
                8, // Center of character
                16,
                16,
                2, //Resize
                2,
                centipedeEnemy.getRotation()); // Rotation
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

    public void antRender(Ant ant){
        batch.draw(antFrame,
                ant.getX(), // Position
                ant.getY(), // Position
                16, // Center of character
                16, // Center of character
                antFrame.getRegionWidth(),
                antFrame.getRegionHeight(),
                1, //Resize
                1,
                ant.getRotation()); // Rotation
    }

    public void createAnt(){

        int x = random.nextInt((608-75)+75);
        int y = random.nextInt((448-50)+50);
        System.out.println(x + " " + y);
        Body body = BodyHelperService.createBody(
                x *2, // Position
                y *2, // Position
                24, // Box size
                32, // Box size
                0,
                false,
                world);

        ants.add(new Ant(body));
    }

    public void addScore(){
        hud.incrementCount();

    }

    public void removeAnt(){
        for(int i =0; i < ants.size; i++ ) { // Go through and destroy any web bodies that may need to be
            if (ants.get(i).isDeadAnt()) {
                world.destroyBody(ants.get(i).getBody());
                ants.removeIndex(i);
            }
        }

    }

    public void resetGame(){
        // Remove WebSac bombs
        webPickup1.removeSac();
        webPickup2.removeSac();
        webPickup3.removeSac();

        // Generate Web Sac pickups
        Body bodyWebSac1 = BodyHelperService.createWebSac(world);
        Body bodyWebSac2 = BodyHelperService.createWebSac(world);
        Body bodyWebSac3 = BodyHelperService.createWebSac(world);

        webPickup1 = new WebSac(bodyWebSac1, world);
        webPickup2 = new WebSac(bodyWebSac2, world);
        webPickup3 = new WebSac(bodyWebSac3, world);

        // Remove ants
        ants.clear();

        // Reset Centipede Position
        centipedeEnemy.resetPosition(100);


    }
}
