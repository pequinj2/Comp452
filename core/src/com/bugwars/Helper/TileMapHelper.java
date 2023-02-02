package com.bugwars.Helper;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.bugwars.Assignment1.Assignment1;
import com.bugwars.Assignment3.Game2.Game2;

/**
 * This helper class will load the maps created in Tiled to build our levels.
 * Code for this tile helper was taken from the tutorial:
 * https://www.youtube.com/watch?v=PMiodP2q8-4
 * "libGDX 2D Platformer Tutorial #2 - Creating a TiledMap" by Small Pixel Games
 */
public class TileMapHelper {

    private TiledMap tiledMap;
    private Assignment1 gameWorld;
    private Game2 gameWorld2;

    public TileMapHelper(Assignment1 gameWorld){
        this.gameWorld = gameWorld;
    }
    public TileMapHelper(Game2 gameWorld){
        this.gameWorld2 = gameWorld;
    }

    // Load our level 1 map
    public OrthogonalTiledMapRenderer setupMap1(){
        tiledMap = new TmxMapLoader().load("maps/level1.tmx");
        //parseMapObjects(tiledMap.getLayers().get("Sprites").getObjects());
        return new OrthogonalTiledMapRenderer(tiledMap);
    }

    // Load our level 1 map
    public OrthogonalTiledMapRenderer setupMap2(){
        tiledMap = new TmxMapLoader().load("maps/Assignment3/Game2Floor.tmx");
        return new OrthogonalTiledMapRenderer(tiledMap, 0.3f);
    }

    // Not used but kept for future reference
    private void parseMapObjects(MapObjects mapObjects){
        for(MapObject mapObject: mapObjects){

            if(mapObject instanceof RectangleMapObject){
                Rectangle rectangle = ((RectangleMapObject) mapObject).getRectangle();
                String rectangleName = mapObject.getName();
                //System.out.println("This is the rectangle name: " + rectangleName);

                if(rectangleName.equals("spider")){
                    Body body = BodyHelperService.createBody(
                            rectangle.getX() + rectangle.getWidth(),
                            rectangle.getY() + rectangle.getHeight(),
                            rectangle.getWidth(),
                            rectangle.getHeight(),
                            0,
                            false,
                            gameWorld.getWorld());
                    //gameWorld.setSpider(new Spider(rectangle.getWidth(), rectangle.getHeight(), body, 100, world));
                }


            }

        }
    }

    public void dispose(){
        tiledMap.dispose();
    }
}
