package com.bugwars.Assignment2.Game1;


import static com.badlogic.gdx.utils.TimeUtils.millis;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.bugwars.BugWars;
import com.bugwars.Helper.FadeScreen;
import com.bugwars.PauseMenu;

/**
 * This class will build the user generated map and display it properly to the screen for showcasing
 * the results of the A* algorithm
 */
public class RunMap implements Screen {

    private TiledMap map;
    private Button[] btnTiles;
    private TextureAtlas atlas, txtBtn;
    private TextureRegion dirt, grass, swamp, rock, ant, berry, visit, end;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;
    private TiledMapTileLayer.Cell cell;
    private TiledMapTile tile;
    private MapLayers layers;
    private TiledMapTileLayer mapLayer;
    private PathFindingAStar path;
    private Tile current;
    private long currentTime, startTime;
    private TextButton endBtn, redoBtn, mapBtn;
    private BitmapFont font, warningFont;
    private Skin skin;
    private Table topBtnTbl;
    private Stage stg;
    private SpriteBatch batch;
    private RunMap thisScreen;

    // Pause Menu
    private boolean isPaused = false;
    private PauseMenu pauseMenu;

    // Final calculations
    private int totalCost = 0;
    private int totalConnections = 0;

    public RunMap(Button[] btnTiles, OrthographicCamera camera, PathFindingAStar path, BugWars game){
        this.thisScreen = this;
        this.camera = camera;
        this.path = path;
        this.btnTiles = btnTiles;
        pauseMenu = new PauseMenu(this, game, camera);
        pauseMenu.assignment2Game1Listeners();
        map = new TiledMap();
        layers = map.getLayers();
        mapLayer = new TiledMapTileLayer(16, 16, 32, 32);
        batch = new SpriteBatch();

        // Load asset textures
        atlas = new TextureAtlas(Gdx.files.internal("Assignment2/GameOneTiles.atlas"));
        dirt = new TextureRegion(atlas.findRegion("Dirt_Tile"));
        grass = new TextureRegion(atlas.findRegion("Grass"));
        swamp = new TextureRegion(atlas.findRegion("Swamp_Tile"));
        rock = new TextureRegion(atlas.findRegion("Rock_Tile"));
        ant = new TextureRegion(atlas.findRegion("Start"));
        berry = new TextureRegion(atlas.findRegion("Berry"));
        visit = new TextureRegion(atlas.findRegion("Visited"));
        end = new TextureRegion(atlas.findRegion("Ended"));

        // Create the TiledMap of the map the user created
        startingMap();


        // Load the TiledMap for rendering and set the viewport
        renderer = new OrthogonalTiledMapRenderer(map);
        camera.setToOrtho(true, 512, 650);
        camera.position.y = 188;

        //path.setSimulation(this);
        // Set the start time to being algorithm when the fade screen is finished loading
        startTime = millis() + 2000;

        /**
         * Create the top buttons with their listeners
         */
        stg = new Stage();
        topBtnTbl = new Table();
        topBtnTbl.setFillParent(true);
        skin = new Skin();
        stg.addActor(topBtnTbl);
        //topBtnTbl.setDebug(true);

        txtBtn = new TextureAtlas((Gdx.files.internal("Hud/Buttons.atlas")));
        skin.addRegions(txtBtn);

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Retro Gaming.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 35; // font size
        font = generator.generateFont(parameter);
        // Warning Font Parameters
        Color color = new Color(255f/255f, 43f/255f, 145f/255f, 255f/255f);
        parameter.size = 25;
        warningFont = generator.generateFont(parameter);
        warningFont.setColor(color);
        generator.dispose(); // Once font is generated dispose of the generator

        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.font = font;
        style.up = skin.getDrawable("Button_Up");
        style.down = skin.getDrawable("Button_Down");

        endBtn = new TextButton("Show End",style);
        redoBtn = new TextButton("Redo Simulation",style);
        mapBtn = new TextButton("Map Style",style);
        topBtnTbl.add(endBtn).width(250).padLeft(100).padTop(20);
        topBtnTbl.add(redoBtn).width(450).padLeft(20).padTop(20);
        topBtnTbl.add(mapBtn).width(250).padLeft(20).padTop(20);
        topBtnTbl.top().left();

        // Show final results of simulation
        endBtn.addListener(new ClickListener(){
            @Override
            public void clicked (InputEvent event, float x, float y) {
                while(!(current.getNode().equals("End"))){
                    // If the goal can not be reached then break loop
                    if(current.getID() == -1){
                        noGoalMessage();
                        break;
                    }else {
                        setVisitedTile(current);
                        setCurrentTile(path.findPath());
                    }
                }

            }
        });

        // Reset the map to default - everything dirt tile
        redoBtn.addListener(new ClickListener(){
            @Override
            public void clicked (InputEvent event, float x, float y) {
                startTime = millis() + 2000;
                resetMap(); // reset map
                // Create the TiledMap of the map the user created
                startingMap();
                current = path.getStart();
                path.resetStart();
            }
        });

        // Go back to map editor
        mapBtn.addListener(new ClickListener(){
            @Override
            public void clicked (InputEvent event, float x, float y) {
                game.setScreen(new FadeScreen(game, thisScreen, new Game1(camera, game) ));

            }
        });

        current = path.getStart();
    }



    @Override
    public void show() {

    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1); // Clear the previous screen of anything
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.input.setInputProcessor(stg);
        renderer.render();
        camera.update();
        renderer.setView(camera);
        stg.act(Gdx.graphics.getDeltaTime());
        stg.draw();

        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
            isPaused = !isPaused;
        }

        if(isPaused){ // check if game is paused
            pause();
        }else {


            // Display warning text if goal can not be found
            if (current.getID() == -1) {
                noGoalMessage();
            }
            // Else if we found the end
            else if (current.getNode().equals("End")) {
                Tile check = current;
                totalCost = (int)current.getCostSoFar();
                totalConnections = 0;
                while (!(check.getNode().equals("Start"))) {
                    totalConnections++;
                    Tile getConnectingNode = check.getConnection();
                    setEndingTile(getConnectingNode);
                    check = getConnectingNode;
                }
                reachedGoalMessage();

            }
            // If we haven't found the end tile, keep looking
            else {
                currentTime = millis();
                if (currentTime >= startTime) {
                    setVisitedTile(current);
                    setCurrentTile(path.findPath());
                    startTime = millis() + 500;
                }
            }

        }


    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {
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
        renderer.dispose();
        map.dispose();
        atlas.dispose();
        txtBtn.dispose();
        batch.dispose();
        warningFont.dispose();
        stg.dispose();
        skin.dispose();
        camera.setToOrtho(false);
        camera.position.y = 0;

    }

    /**
     * Helper function that will return the loaded asset image
     * @param btn
     * @return Texture region of the image we need
     */
    private TextureRegion getTexture(Button btn){
        String currStyle = "";
        // Get Tile image
        if(btn.getStyle().up == null){
            currStyle = btn.getStyle().checked.toString();

        }else{
            currStyle = btn.getStyle().up.toString();
        }

        switch(currStyle){
            case "Dirt_Tile":
                return dirt;
            case "Grass":
                return grass;
            case "Swamp_Tile":
                return swamp;
            case "Rock_Tile":
                return rock;
            case "Start":
                return ant;
            case "Berry":
                return berry;
            case "Visited":
                return visit;
            case "Ended":
                return end;
        }
        return null;
    }

    /**
     * Set the previous tile we were at as the purple visited tile image
     * @param visitedPath
     */
    public void setVisitedTile(Tile visitedPath){

        if(visitedPath != null) {
            float id = visitedPath.getID();
            int x = (int) id / 16;
            int y = (int) id - (x * 16);
            TiledMapTile visitedTile = new StaticTiledMapTile(visit);
            cell = mapLayer.getCell(x,y);
            cell.setTile(visitedTile);
        }
    }

    /**
     * Set the current tile we're at as the image of the ant
     * @param currentPath
     */
    public void setCurrentTile(Tile currentPath){

        if(currentPath != null) {
            float id = currentPath.getID();
            int x = (int) id / 16;
            int y = (int) id - (x * 16);
            cell = new TiledMapTileLayer.Cell();
            TiledMapTile tileCurr = new StaticTiledMapTile(ant);
            cell.setTile(tileCurr);
            mapLayer.setCell(x, y, cell);
            layers.add(mapLayer);
            current = currentPath;
        }
    }

    /**
     * Helper function that will show a yellow path indicating the final path the ant would take
     * from start to finish
     * @param endPath
     */
    public void setEndingTile(Tile endPath){

        float id = endPath.getID();
        int x = (int)id/16;
        int y = (int)id - (x * 16);
        TiledMapTile tileEnd = new StaticTiledMapTile(end);
        cell = mapLayer.getCell(x,y);
        cell.setTile(tileEnd);

    }

    /**
     * Helper function that will generate the tile map from the Button array made by the user
     */
    private void startingMap(){

        for (int j=0; j<16; j++) {
            for (int i=0; i<16; i++) {
                Button btn = btnTiles[(i*16) + j];
                cell = new TiledMapTileLayer.Cell();
                tile = new StaticTiledMapTile(getTexture(btn));
                cell.setTile(tile);
                mapLayer.setCell(i,j, cell.setFlipVertically(true));
                layers.add(mapLayer);

            }
        }
    }

    /**
     * Helper function that will reset the tile images back to their original state
     */
    private void resetMap(){

        for (int j=0; j<16; j++) {
            for (int i=0; i<16; i++) {
                Button btn = btnTiles[(i*16) + j];
                cell = mapLayer.getCell(i,j).setFlipVertically(true);
                cell.getTile().setTextureRegion(getTexture(btn));

            }
        }
    }

    private void noGoalMessage(){
        String warningText = "Berry can not be reached :(";
        batch.begin();
        warningFont.draw(batch, warningText, 200, 800);
        batch.end();
    }

    private void reachedGoalMessage(){
        String warningText = "Berry has been found!\nIt took " + totalConnections + " moves and had a path cost of " + totalCost;
        batch.begin();
        warningFont.draw(batch, warningText, 200, 800);
        batch.end();
    }
}
