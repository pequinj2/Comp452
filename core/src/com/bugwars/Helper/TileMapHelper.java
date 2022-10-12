package com.bugwars.Helper;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.bugwars.Objects.Player.Spider;
import com.bugwars.game.Assignment1;

/**
 * This helper class will load the maps created in Tiled to build our levels.
 * Code for this tile helper was taken from the tutorial:
 * https://www.youtube.com/watch?v=PMiodP2q8-4
 * "libGDX 2D Platformer Tutorial #2 - Creating a TiledMap" by Small Pixel Games
 */
public class TileMapHelper {

    private TiledMap tiledMap;
    private Assignment1 gameWorld;

    public TileMapHelper(Assignment1 gameWorld){
        this.gameWorld = gameWorld;
    }

    // Load our level 1 map
    public OrthogonalTiledMapRenderer setupMap1(){
        tiledMap = new TmxMapLoader().load("maps/level1.tmx");
        //parseMapObjects(tiledMap.getLayers().get("Sprites").getObjects());
        return new OrthogonalTiledMapRenderer(tiledMap);
    }

    // Load our objects
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
                    gameWorld.setSpider(new Spider(rectangle.getWidth(), rectangle.getHeight(), body, 100));
                }


            }

        }
    }
}
