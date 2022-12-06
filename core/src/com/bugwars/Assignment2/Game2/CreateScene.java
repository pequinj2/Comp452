package com.bugwars.Assignment2.Game2;

import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.bugwars.Helper.AssetManager;

import java.awt.Point;
import java.util.Random;

import jdk.internal.vm.compiler.collections.Pair;

/**
 * This class is used to generate the textures on the map including the items that are generated.
 */
public class CreateScene {

    private TiledMapTileLayer.Cell cell;
    private TiledMapTile tile;
    private MapLayers layers;
    private TiledMapTileLayer mapLayer;
    private TiledMap map;
    private Random randomNum;
    private AssetManager assetMgr;
    private Point antHillHome;


    public CreateScene(AssetManager assetMgr){
        this.assetMgr = assetMgr;
        map = new TiledMap();
        layers = map.getLayers();
        mapLayer = new TiledMapTileLayer(16, 16, 32, 32);

        // Create base 'Dirt_Tile' map
        for (int j=0; j<16; j++) {
            for (int i=0; i<16; i++) {
                cell = new TiledMapTileLayer.Cell();
                tile = new StaticTiledMapTile(assetMgr.getDirt());
                tile.setId((i*16) + j);
                cell.setTile(tile);
                mapLayer.setCell(i,j, cell.setFlipVertically(true));
                layers.add(mapLayer);

            }
        }

        randomNum = new Random();

    }


    /**
     *
     * @return current status of map
     */
    public TiledMap getMap() {
        return map;
    }


    /**
     * Get a random X and Y value from 0 to 15, get that current cell and see if its free (labeled
     * 'Dirt_Tile' if free), if so generate chosen item there - if not keep checking until one is
     * found.
     * @param itemID
     */
    public void generateItems(int itemID){
        int tempI = randomNum.nextInt(16) ;
        int tempJ = randomNum.nextInt(16) ;

        cell = mapLayer.getCell(tempI,tempJ);

        // If current cell isn't a 'Dirt_Tile', keep looking
        while (!(cell.getTile().getTextureRegion().equals(assetMgr.getDirt()))){
            tempI = randomNum.nextInt(16) ;
            tempJ = randomNum.nextInt(16) ;

            cell = mapLayer.getCell(tempI,tempJ);
        }

        TiledMapTile newTile;
        // Get the texture that is needed to be generated
        switch(itemID){
            case 0:
                newTile = new StaticTiledMapTile(assetMgr.getFood());
                break;
            case 1:
                newTile = new StaticTiledMapTile(assetMgr.getPoison());
                break;
            case 2:
                newTile = new StaticTiledMapTile(assetMgr.getWater());
                break;
            case 3:
                antHillHome = new Point(tempI, tempJ);
                newTile = new StaticTiledMapTile(assetMgr.getAntHill());
                break;
            default:
                newTile = new StaticTiledMapTile(assetMgr.getDirt());
                break;
        }

        // Update cell with new chosen item
        cell = mapLayer.getCell(tempI,tempJ);
        cell.setTile(newTile);

    }

    public Point getAntHillHome(){
        return antHillHome;
    }

}
