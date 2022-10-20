package com.bugwars.Assignment1;

import static com.badlogic.gdx.utils.TimeUtils.millis;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
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
import com.bugwars.Helper.Animator;
import com.bugwars.Helper.BodyHelperService;
import com.bugwars.Helper.CollisionListenerHelper;
import com.bugwars.Helper.TileMapHelper;
import com.bugwars.Objects.Enemy.Centipede;
import com.bugwars.Objects.Pickups.WebSac;
import com.bugwars.Objects.Player.Spider;

public class Assignment1 implements Screen {

    private Texture bugWarsImage;
    private SpriteBatch batch, hudBatch, pauseBatch; //To render our sprites
    private World world; //To store our box 2D bodies - *** May not need this? Sounds like its for gravity***
    private Box2DDebugRenderer box2DBug;
    private OrthographicCamera camera, hudCamera;
    private OrthographicCamera cam;
    private int viewPortWidth = 300;
    private int viewPortHeight = 350;
    private int screenWidth = 1216;
    private int screenHeight = 896;
    private float stateTime;

    // Objects for our tile map
    private OrthogonalTiledMapRenderer orthoTileRender;
    private TileMapHelper tileMapHelper;
    private Spider spiderPlayer;
    private Centipede centipedeEnemy;
    private Array<Body> centipedeBodies;

    //Animations
    private Animation<TextureRegion> walkAnimation, centipedeMouthAnimation; // Must declare frame type (TextureRegion)

    // Textures
    private TextureRegion centipedeBody, centipedeButt;
    private TextureAtlas allTextures;

    // Character AI
    private float targetPosition, enemyPosition;
    private long timerBurstShot, currentTime;

    // Player Hud
    private PlayerHud hud;

    // Web Sac Pickups
    private WebSac webPickup1, webPickup2, webPickup3;

    // Centtipede AOE
    private boolean aoeDelay = false;

    // Pause Menu
    private boolean isPaused = false;
    private PauseMenu pauseMenu = new PauseMenu();




    public Assignment1(OrthographicCamera camera){

        this.camera = camera;
        this.batch = new SpriteBatch();
        hudBatch = new SpriteBatch();
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

        //*********** PAUSE SCREEN ***************************************************
        bugWarsImage = new Texture(Gdx.files.internal("bugwarssplash2.png"));
        pauseBatch = new SpriteBatch();

        // Create Spider character ***************************************************
        Body body = BodyHelperService.createBody(
                100 + 16, // Position
                100 + 16, // Position
                32, // Box size
                32, // Box size
                0,
                false,
                world);
        setSpider(new Spider(16, 16, body, 100, world));
        // **************************************************************************

        // Create Centipede enemy ***************************************************
        Body bodyEnemyHead = BodyHelperService.createBody(
                200 + 16, // Position
                200 + 16, // Position
                32, // Box size
                32, // Box size
                1,
                false,
                world);

        setCentipede(new Centipede(world,16, 16, bodyEnemyHead, 100));
        centipedeEnemy.initBody(world); // initialize the rest of the centipede body
        centipedeEnemy.initDistanceJoint(world);
        centipedeBodies = centipedeEnemy.getCentipede();
        centipedeBodies = centipedeEnemy.getCentipede();

        // *************************************************************************

        bugWarsImage = new Texture(Gdx.files.internal("bugwarssplash.png"));
        // Get Sprite animations
        Animator ani = new Animator();
        walkAnimation = ani.AnimatorSpider();
        centipedeMouthAnimation = ani.CentipedeMouthAnimator();

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
        hud = new PlayerHud(spiderPlayer.getHealth(),centipedeEnemy.getHealth());

        // Generate Web Sac pickups
        Body bodyWebSac1 = BodyHelperService.createWebSac(world);
        Body bodyWebSac2 = BodyHelperService.createWebSac(world);
        Body bodyWebSac3 = BodyHelperService.createWebSac(world);

        webPickup1 = new WebSac(bodyWebSac1, world);
        webPickup2 = new WebSac(bodyWebSac2, world);
        webPickup3 = new WebSac(bodyWebSac3, world);

        // Centipede burst shot
        timerBurstShot = millis() + (10*1000);

    }

    /**
     * This is where we'll update our Assignment1
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
        spiderPlayer.update();
        centipedeEnemy.update();


        //****************************************************************************************
        currentTime = millis();
        if(currentTime > timerBurstShot){
            centipedeEnemy.aoeShot();
            timerBurstShot = millis() + (10*1000);
            aoeDelay=true;
        }else{
            if(aoeDelay == true) { // Stop the centipede from moving so it can fire off its aoe
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        //centipedeEnemy.seekTarget(spiderPlayer.getBody().getPosition(), centipedeEnemy.getBody().getPosition());
                        aoeDelay=false;
                    }
                }, 3);
            }
            else{
                centipedeEnemy.seekTarget(spiderPlayer.getBody().getPosition(), centipedeEnemy.getBody().getPosition());
            }

        }

        hud.update(spiderPlayer.getHealth(), centipedeEnemy.getHealth());


    }

    /**
     * Method that will move the viewport camera around as the player is moving around the level
     * Logic for this was taken from LibGDX tutorial:
     * https://libgdx.com/wiki/graphics/2d/orthographic-camera
     */
    private void handleInput() {
        Vector3 position = camera.position;
        position.x = Math.round(spiderPlayer.getBody().getPosition().x);
        position.y = Math.round(spiderPlayer.getBody().getPosition().y);
        camera.position.set(position);

        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
            isPaused = !isPaused;
            System.out.println(isPaused);

        }

        // Stop the camera position so it doesn't go out of bounds
        camera.position.x = MathUtils.clamp(camera.position.x, viewPortWidth/2f, 608-viewPortWidth/2f );
        camera.position.y = MathUtils.clamp(camera.position.y, viewPortHeight/2f, 448-viewPortHeight/2f );


    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        if(isPaused){
            delta =0;
            pause();
        }else {


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
            this.update();

            Gdx.gl.glClearColor(0, 0, 0, 1); // Clear the previous screen of anything
            /**
             * https://stackoverflow.com/questions/34164309/gl-color-buffer-bit-regenerating-which-memory
             * Explains why clearing the color buffer is important
             */
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            stateTime += Gdx.graphics.getDeltaTime();
            // Render our tile map before the Assignment1 objects
            orthoTileRender.render();

            // Get current frame of animation for the current stateTime
            TextureRegion spiderFrame = walkAnimation.getKeyFrame(stateTime, true); // Spider Animation Walking
            TextureRegion centipedeFrame = centipedeMouthAnimation.getKeyFrame(stateTime, true);

            batch.begin();
            // Draw Web Sac Pickups
            webPickup1.render(batch);
            webPickup2.render(batch);
            webPickup3.render(batch);

            // Web shooter spawn
            spiderPlayer.render(batch);

            // Centipede aoe Shot
            centipedeEnemy.render(batch);

            //Draw centipede animation
            batch.draw(centipedeFrame,
                    centipedeEnemy.getX()-8, // Position
                    centipedeEnemy.getY()-8, // Position
                    8, // Center of character
                    8, // Center of character
                    16,
                    16,
                    2, //Resize
                    2,
                    spiderPlayer.getRotation()); // Rotation
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

            // Draw spider player
            int position = spiderPlayer.getRotation(); // Holds the rotation value so the player sprite is facing the right way
            batch.draw(spiderFrame,
                    spiderPlayer.getX(), // Position
                    spiderPlayer.getY(), // Position
                    spiderPlayer.getWidth() / 2, // Center of character
                    spiderPlayer.getHeight() / 2, // Center of character
                    spiderFrame.getRegionWidth(),
                    spiderFrame.getRegionHeight(),
                    2, //Resize
                    2,
                    position); // Rotation


            batch.end();
            // ********* COMMENT THIS LINE OUT WHEN IN PROD ***********
            // commenting out will remove collision boxes
            box2DBug.render(world, camera.combined);//<<- PPM = Pixel Per Meters

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
}
