package com.bugwars.Assignment2.Game1;


import com.badlogic.gdx.Gdx;
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

/**
 * This class will build the user generated map and display it properly to the screen for showcasing
 * the results of the A* algorithm
 */
public class RunMap {

    private TiledMap map;
    private Button[] btnTiles;
    private TextureAtlas atlas;
    private TextureRegion dirt, grass, swamp, rock, ant, berry;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;

    public RunMap(Button[] btnTiles, OrthographicCamera camera){
        this.camera = camera;
        map = new TiledMap();
        this.btnTiles = btnTiles;
        MapLayers layers = map.getLayers();
        TiledMapTileLayer mapLayer = new TiledMapTileLayer(16, 16, 32, 32);


        atlas = new TextureAtlas(Gdx.files.internal("Assignment2/GameOneTiles.atlas"));
        dirt = new TextureRegion(atlas.findRegion("Dirt_Tile"));
        grass = new TextureRegion(atlas.findRegion("Grass"));
        swamp = new TextureRegion(atlas.findRegion("Swamp_Tile"));
        rock = new TextureRegion(atlas.findRegion("Rock_Tile"));
        ant = new TextureRegion(atlas.findRegion("Start"));
        berry = new TextureRegion(atlas.findRegion("Berry"));

        for (int j=0; j<16; j++) {
            for (int i=0; i<16; i++) {
                Button btn = btnTiles[(i*16) + j];
                final TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
                TiledMapTile tile = new StaticTiledMapTile(new TextureRegion(getTexture(btn)));
                cell.setTile(tile);
                mapLayer.setCell(i, j, cell);
                layers.add(mapLayer);
            }
        }
        float unitScale = 1;
        renderer = new OrthogonalTiledMapRenderer(map, unitScale);


    }

    public void render(SpriteBatch batch){
        Gdx.gl.glClearColor(0, 0, 0, 1); // Clear the previous screen of anything
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        renderer.setView(camera);
        renderer.render();





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
        }
        return null;
    }
}
