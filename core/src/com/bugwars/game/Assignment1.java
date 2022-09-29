package com.bugwars.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.bugwars.Helper.Animator;
import com.bugwars.Helper.TileMapHelper;
import com.bugwars.Objects.Player.Spider;

public class Assignment1 implements Screen {

    private Texture bugWarsImage;
    private SpriteBatch batch; //To render our sprites
    private World world; //To store our box 2D bodies - *** May not need this? Sounds like its for gravity***
    private Box2DDebugRenderer box2DBug;
    private OrthographicCamera camera;
    private OrthographicCamera cam;
    private int viewPortWidth = 600;
    private int viewPortHeight = 700;
    private int screenWidth = 1216;
    private int screenHeight = 896;
    private float stateTime;

    // Objects for our tile map
    private OrthogonalTiledMapRenderer orthoTileRender;
    private TileMapHelper tileMapHelper;
    private Spider spiderPlayer;
    RectangleMapObject rectangleMap;
    Rectangle spiderRect;

    //Animations
    private Animation<TextureRegion> walkAnimation; // Must declare frame type (TextureRegion)

    public Assignment1(OrthographicCamera camera){
        //this.game = game;
        this.camera = camera;
        camera.setToOrtho(false, viewPortWidth, viewPortHeight); // Camera is effecting the size of the tile map shown,
        this.batch = new SpriteBatch();
        this.world = new World(new Vector2(0,0), false); //Gravity is the Vector2
        this.box2DBug = new Box2DDebugRenderer();

        this.tileMapHelper = new TileMapHelper(this);
        /**
         * Getting the map we've loaded from TileMapHelper, may need to pass in String name to
         * make it more portable.
         */
        this.orthoTileRender = tileMapHelper.setupMap1();

        rectangleMap= (RectangleMapObject)orthoTileRender.getMap().getLayers().get("Sprites").getObjects().get("spider");
        spiderRect = rectangleMap.getRectangle();

        bugWarsImage = new Texture(Gdx.files.internal("bugwarssplash.png"));
        //game.batch.begin();
        Animator ani = new Animator();
        walkAnimation = ani.Animator();
        stateTime = 0f;

    }

    /**
     * This is where we'll update our game
     */
    private void update(){
        handleInput();
        /**
         * World.step explanation
         * tells the physics engine that 1/60th of a second has passed every time you call it.
         * If your game loop is being called more than 60 times a second it will go fast; less than
         * 60 times a second and it'll be slow. The number of times it gets called per second will
         * depend on the speed of the underlying hardware, so this method will end up in different
         * behavior on different devices.
         * https://gamedev.stackexchange.com/questions/144847/box2d-world-step-on-android-game-using-libgdx
         */
        world.step(1/60f, 6, 2);

        camera.update();
        /**
         * https://stackoverflow.com/questions/33703663/understanding-the-libgdx-projection-matrix
         * Call setProjectionMatrix whenever you have moved the camera or resized the screen.
         * Camera.combined describes where things in your game world should be rendered onto the screen
         */
        batch.setProjectionMatrix(camera.combined);
        orthoTileRender.setView(camera); // << Why are we doing this?

        spiderPlayer.update();


        if (Gdx.input.isTouched(Input.Keys.ESCAPE)) {
            this.hide();
            System.out.println("back........");
            //game.setScreen(new MainMenuScreen(game));
        }

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
        /*// Translate viewport camera depending on where the player is moving
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            camera.translate(-3, 0, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            camera.translate(3, 0, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            camera.translate(0, 3, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            camera.translate(0, -3, 0);
        }

         */
        // Stop the camera position so it doesn't go out of bounds
        camera.position.x = MathUtils.clamp(camera.position.x, viewPortWidth/2f, screenWidth-viewPortWidth/2f );
        camera.position.y = MathUtils.clamp(camera.position.y, viewPortHeight/2f, screenHeight-viewPortHeight/2f );


    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        this.update();

        Gdx.gl.glClearColor(0,0,0,1); // Clear the previous screen of anything
        /**
         * https://stackoverflow.com/questions/34164309/gl-color-buffer-bit-regenerating-which-memory
         * Explains why clearing the color buffer is important
         */
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stateTime += Gdx.graphics.getDeltaTime();
        // Render our tile map before the game objects
        orthoTileRender.render();


        // Get current frame of animation for the current stateTime
        TextureRegion currentFrame = walkAnimation.getKeyFrame(stateTime, true);
        batch.begin();
        //batch.draw(bugWarsImage, 0,0);
        //System.out.println("Print floats " + spiderRect.getX() + "  " + spiderRect.getY());
        boolean flip = spiderPlayer.getFlipLR();
        boolean flipDown = spiderPlayer.getFlipUD();
        int postion = spiderPlayer.postion();
        batch.draw(currentFrame,
                spiderPlayer.getX(),
                spiderPlayer.getY(),
                0,
                0,
                currentFrame.getRegionWidth(),
                currentFrame.getRegionHeight(),
                1,
                1,
                postion);

        batch.end();
        box2DBug.render(world, camera.combined);//<<- PPM = Pixel Per Meters


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

    public World getWorld() {
        return world;
    }

    public void setSpider (Spider spider){
        this.spiderPlayer = spider;

    }
}
