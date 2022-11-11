package com.bugwars.Assignment2.Game1;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;


/**
 * This class is used to show the different tiles the user has the options at picking and placing
 * them on a map
 */
public class TileSelector {

    private Stage stg;
    private Skin skin;
    private Table tbl, topBtnTbl;
    private TextureAtlas tiles, txtBtn;
    private SpriteBatch batch;

    private Button openTerrain, grassland, swampland, rock, start, goal;
    private TextButton runBtn, resetBtn;
    private BitmapFont font, warningFont;

    private Boolean showTooltip = false;
    private Boolean showTileSelected = false;
    private String text = "";
    private Drawable current;
    private Button[] btnTiles = new Button[16 * 16];

    private TextureRegion newTextureRegion;

    int startCheck = 0;
    int endCheck = 0;
    private Boolean runCheck = false;
    private String warningText = "";
    private TileMap map;

    public TileSelector(OrthographicCamera camera, Stage stg){
        this.stg = stg;
        tbl = new Table();
        topBtnTbl = new Table();
        stg.addActor(tbl);
        stg.addActor(topBtnTbl);
        tbl.setDebug(true);
        tbl.setFillParent(true);
        topBtnTbl.setDebug(true);
        topBtnTbl.setFillParent(true);
        skin = new Skin();
        tiles = new TextureAtlas((Gdx.files.internal("Assignment2/GameOneTiles.atlas")));
        skin.addRegions(tiles);

        batch = new SpriteBatch();

        openTerrain = new Button(skin.getDrawable("Dirt_Tile"),skin.getDrawable("Dirt_Tile_Highlight"));
        grassland = new Button(skin.getDrawable("Grass"),skin.getDrawable("Grass_Highlight"));
        swampland = new Button(skin.getDrawable("Swamp_Tile"),skin.getDrawable("Swamp_Tile_Highlight"));
        rock = new Button(skin.getDrawable("Rock_Tile"),skin.getDrawable("Rock_Tile_Highlight"));
        start = new Button(skin.getDrawable("Start"),skin.getDrawable("Start_Highlight"));
        goal = new Button(skin.getDrawable("Berry"),skin.getDrawable("Berry_Highlight"));

        tbl.add(openTerrain).width(160).height(110).padTop(20).padBottom(20).padLeft(10);
        tbl.row();
        tbl.add(grassland).width(160).height(110).padTop(20).padBottom(20).padLeft(10);
        tbl.row();
        tbl.add(swampland).width(160).height(110).padTop(20).padBottom(20).padLeft(10);
        tbl.row();
        tbl.add(rock).width(160).height(110).padTop(20).padBottom(20).padLeft(10);
        tbl.row();
        tbl.add(start).width(160).height(110).padTop(20).padBottom(20).padLeft(10);
        tbl.row();
        tbl.add(goal).width(160).height(110).padTop(20).padBottom(20).padLeft(10);
        tbl.top().left();

        // If there is a warning displayed clear it
        tbl.addListener(new ClickListener(){
            @Override
            public void clicked (InputEvent event, float x, float y) {
                warningText = "";

            }
        });

        Gdx.input.setInputProcessor(stg);

        /**
         * Attach listeners to all of the Left Side Bar Buttons using method 'addClickListeners'
         */
        openTerrain.addListener(addClickListerns("Cost = 1","Dirt_Tile", "Dirt_Tile_Highlight" ));
        grassland.addListener(addClickListerns("Cost = 3","Grass", "Grass_Highlight" ));
        swampland.addListener(addClickListerns("Cost = 4","Swamp_Tile", "Swamp_Tile_Highlight" ));
        rock.addListener(addClickListerns("Block","Rock_Tile", "Rock_Tile_Highlight" ));
        start.addListener(addClickListerns("Start","Start", "Start_Highlight" ));
        goal.addListener(addClickListerns("End","Berry", "Berry_Highlight" ));



        /**
         * Create the top buttons with their listeners
         */
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

        runBtn = new TextButton("Run",style);
        resetBtn = new TextButton("Reset",style);
        topBtnTbl.add(runBtn).padLeft(400).padTop(20);
        topBtnTbl.add(resetBtn).padLeft(200).padTop(20);
        topBtnTbl.top().left();

        // Run button to check the map and run the algorithm
        runBtn.addListener(new ClickListener(){
            @Override
            public void clicked (InputEvent event, float x, float y) {
                printButtons();

            }
        });

        // Reset the map to default - everything dirt tile
        resetBtn.addListener(new ClickListener(){
            @Override
            public void clicked (InputEvent event, float x, float y) {
                resetMap();
                warningText = "Map has been reset!";

            }
        });

        // Stop showing tooltips if mouse enters the buttons
        topBtnTbl.addListener(new ClickListener(){
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                showTileSelected = false;
            }
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                showTileSelected = true;
            }

        });



        // Create the Tile Map with default layout
        TileMap(stg);
    }

    public void render(SpriteBatch batch){
        Gdx.gl.glClearColor(0, 0, 0, 1); // Clear the previous screen of anything
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stg.act(Gdx.graphics.getDeltaTime());
        stg.draw();
        batch.begin();
        warningFont.draw(batch, warningText, 200, 800);
        //warningFont.draw(batch, "There can only be 1 berry on the map, please try again.", 200, 750);
        batch.end();


    }

    public String getText(){
        return text;
    }

    public TextureRegion getTexture(){
        return newTextureRegion;
    }

    public void dispose(){

    }

    public boolean getShowTooltip() {
        return showTooltip;
    }

    public boolean getShowTileSelected() {
        return showTileSelected;
    }

    /**
     * Create the tile map that the user is allowed to click on to create the graph of their choosing
     * to run the A* Algorithm.
     * @param stg
     */
    public void TileMap(Stage stg){
        this.stg = stg;
        tbl = new Table(); // Setup the table that the buttons (clickable tiles) will be held in
        tbl.setDebug(true);
        tbl.setFillParent(true);

        // Center it onto the screen
        tbl.padRight(40);
        tbl.padBottom(70);
        tbl.bottom().right();

        // Default Tile Skin
        current = skin.getDrawable("Dirt_Tile");

        // if there is a warning displayed clear it
        tbl.addListener(new ClickListener(){
            @Override
            public void clicked (InputEvent event, float x, float y) {
                warningText = "";

            }
        });

        // Create a 16x16 tile map by using a Table and an array of Buttons that represent the tiles
        for(int j = 0; j<16; j++ ){
            for(int i=0; i<16; i++) {
                // Create a new button (tile) for the map and set its default image to "Dirt_Tile"
                Button tile = new Button(skin.getDrawable("Dirt_Tile"));

                // Put the tile button in an array
                btnTiles[(i*16) + j] = tile;
                //Button.ButtonStyle style = new Button.ButtonStyle();

                /**
                 * This click listener is used to change this button skins when the user has a
                 * different skin selected.
                 */
                tile.addListener(new ClickListener(){
                    @Override
                    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        super.enter(event, x, y, pointer, fromActor);
                        if(Gdx.input.isButtonPressed(Input.Buttons.LEFT) && pointer == -1) {
                            // Get the current tile clicked on the map
                            Button currentBtn = (Button) event.getTarget();
                            // Get the current tile's style (button image)
                            Button.ButtonStyle currStyle = currentBtn.getStyle();
                            // Set the 'up' field to the current image of the tile the user has selected.
                            // Up is used so the user can click and drag the images they want
                            currStyle.up = current;
                            currStyle.checked = current;
                            // Set the 'checked' field to the current image of the tile the user has selected.
                            //currStyle.checked = current;
                            // Set this updated style to the current button tile on the map
                            currentBtn.setStyle(currStyle);

                        }

                    }


                   @Override
                    public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                        super.touchDown(event, x, y, pointer, button);
                        // Get the current tile clicked on the map
                        Button currentBtn = (Button)event.getTarget();
                        // Get the current tile's style (button image)
                        Button.ButtonStyle currStyle = currentBtn.getStyle();
                        // Set the 'checked' field to the current image of the tile the user has selected.
                        currStyle.up = current;
                        currStyle.checked = current;
                        // Set this updated style to the current button tile on the map
                        currentBtn.setStyle(currStyle);


                        return true;
                    }



                });
                tbl.right().add(tile).width(60).height(40);
            }
            tbl.row();

        }

        stg.addActor(tbl);


    }

    /**
     * Helper method that initiates a clicklistener to the button that called it.
     * @param text1 - Text to show in the tooltip
     * @param tileName - Tile to show on the map
     * @param tileNameHighlight - Tile to show in the tool tip
     * @return - the newly initiates ClickListener
     */
    private ClickListener addClickListerns(final String text1, final String tileName, final String tileNameHighlight){

        return ( new ClickListener(){
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                showTooltip=true; // Show the cost or description of the tile
                text = text1;
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                showTooltip=false; // Stop showing the cost or description of the tile
            }

            @Override
            public void clicked (InputEvent event, float x, float y) {
                // Show small tile and assign the appropriate and get the 'current' tile image to
                // show on the map

                showTileSelected = true;
                newTextureRegion = skin.get(tileNameHighlight, TextureRegion.class);
                current = skin.getDrawable(tileName);
            }
        });
    }


    public void printButtons(){
       startCheck = 0;
       endCheck = 0;

        for(int j = 0; j<16; j++ ) {
            for (int i = 0; i < 16; i++) {
                String currStyle = "";
                Button tile = btnTiles[(i*16) + j];
                // Get the current style in each tile to check for Berries and Ants (End and Start points)
                if(tile.getStyle().up == null){
                    currStyle = tile.getStyle().checked.toString();

                }else{
                    currStyle = tile.getStyle().up.toString();
                }

                if(currStyle == "Berry"){
                    endCheck += 1;
                }
                if(currStyle == "Start"){
                    startCheck += 1;
                }

            }

        }

        // Check the number of Berries and Ants and issue a warning if there are too many or not enough
        if(endCheck > 1){
            warningText = "There are too many berries!\nThere can only be 1 berry on the map, please try again.";
        }
        else if(endCheck == 0){
            warningText = "There is no berry!\nPlace 1 berry on the map to continue.";
        }
        else if(startCheck > 1){
            warningText = "There are too many ants!\nThere can only be 1 ant on the map, please try again.";
        }
        else if(startCheck == 0){
            warningText = "There is no ant!\nPlace 1 ant on the map to continue.";
        }
        else{
            warningText = "";
            TileMap map = new TileMap(btnTiles);
        }

        runCheck = true;
    }

    /**
     * Helper function that will reset the map to its original Dirt only state
     */
    private void resetMap(){
        for(int j = 0; j<16; j++ ) {
            for (int i = 0; i < 16; i++) {
                Button tile = btnTiles[(i*16) + j];
                // Get the current tile's style (button image)
                Button.ButtonStyle currStyle = tile.getStyle();
                // Set the 'checked' field to the Dirt image
                currStyle.up = skin.getDrawable("Dirt_Tile");
                currStyle.checked = skin.getDrawable("Dirt_Tile");
                tile.setStyle(currStyle);
            }
        }
    }

    /**
     * @return true or false if the run button has been hit or not
     */
    public Boolean getRun() {
        return runCheck;
    }

    /**
     * @return warningText to see if there is a problem with the map
     */
    public String getWarningText(){
        return warningText;
    }

    public TileMap getMap() {

        return map;
    }
}


