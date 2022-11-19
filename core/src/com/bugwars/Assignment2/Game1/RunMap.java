package com.bugwars.Assignment2.Game1;


import static com.badlogic.gdx.utils.TimeUtils.millis;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.Timer;

/**
 * This class will build the user generated map and display it properly to the screen for showcasing
 * the results of the A* algorithm
 */
public class RunMap implements Screen {

    private TiledMap map;
    private Button[] btnTiles;
    private TextureAtlas atlas;
    private TextureRegion dirt, grass, swamp, rock, ant, berry, visit;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;
    private TiledMapTileLayer.Cell cell;
    private TiledMapTile tile;
    private MapLayers layers;
    private TiledMapTileLayer mapLayer;
    private PathFindingAStar path;
    private Tile visited, current;
    private long currentTime, startTime;


    public RunMap(Button[] btnTiles, OrthographicCamera camera, PathFindingAStar path){
        this.camera = camera;
        this.path = path;
        map = new TiledMap();
        this.btnTiles = btnTiles;
        layers = map.getLayers();
        mapLayer = new TiledMapTileLayer(16, 16, 32, 32);

        atlas = new TextureAtlas(Gdx.files.internal("Assignment2/GameOneTiles.atlas"));
        dirt = new TextureRegion(atlas.findRegion("Dirt_Tile"));
        grass = new TextureRegion(atlas.findRegion("Grass"));
        swamp = new TextureRegion(atlas.findRegion("Swamp_Tile"));
        rock = new TextureRegion(atlas.findRegion("Rock_Tile"));
        ant = new TextureRegion(atlas.findRegion("Start"));
        berry = new TextureRegion(atlas.findRegion("Berry"));
        visit = new TextureRegion(atlas.findRegion("Visited"));


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

        renderer = new OrthogonalTiledMapRenderer(map);
        camera.setToOrtho(true, 512, 650);
        camera.position.y = 188;

        path.setSimulation(this);

        startTime = millis() + 2000;
        System.out.println(TimeUtils.millis() + " new time = " +startTime);

    }



    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1); // Clear the previous screen of anything
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        renderer.render();
        camera.update();
        renderer.setView(camera);

        if(current == null) {
            currentTime = millis();
            if(currentTime >= startTime){
                //setVisitedTile(current);
                setCurrentTile(path.findPath());
                startTime = millis() + 1000;
            }
        }
        else if(!(current.getNode().equals("End"))){
            //System.out.println("In else if");
            currentTime = millis();
            if(currentTime >= startTime){
                setVisitedTile(current);
                setCurrentTile(path.findPath());
                startTime = millis() + 1000;
            }
        }
        else if(current.getNode().equals("End")){
            System.out.println("In end");
            while(!(current.getNode().equals("Start"))){
                Tile getConnectingNode = current.getConnection();
                System.out.println(getConnectingNode.getID());
                current = getConnectingNode;
            }

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
        }
        return null;
    }

    public void setVisitedTile(Tile visitedPath){
        visited = visitedPath;
        float id = visitedPath.getID();
        int x = (int)id/16;
        int y = (int)id - (x * 16);
        TiledMapTile tileVisit = new StaticTiledMapTile(visit);
        cell.setTile(tileVisit);
        mapLayer.setCell(x,y, cell);
        layers.add(mapLayer);

    }

    public void setCurrentTile(Tile currentPath){
        current = currentPath;
        float id = currentPath.getID();
        int x = (int)id/16;
        int y = (int)id - (x * 16);
        cell = new TiledMapTileLayer.Cell();
        TiledMapTile tileCurr = new StaticTiledMapTile(ant);
        cell.setTile(tileCurr);
        mapLayer.setCell(x,y, cell);
        layers.add(mapLayer);

    }
}
