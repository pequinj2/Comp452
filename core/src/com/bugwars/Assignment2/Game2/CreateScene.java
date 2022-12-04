package com.bugwars.Assignment2.Game2;

import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.bugwars.Helper.AssetManager;

import java.util.Random;

public class CreateScene {

    private TiledMapTileLayer.Cell cell;
    private TiledMapTile tile;
    private MapLayers layers;
    private TiledMapTileLayer mapLayer;
    private TiledMap map;
    private Random randomNum;
    private AssetManager assetMgr;


    public CreateScene(AssetManager assetMgr){
        this.assetMgr = assetMgr;
        map = new TiledMap();
        layers = map.getLayers();
        mapLayer = new TiledMapTileLayer(16, 16, 32, 32);

        for (int j=0; j<16; j++) {
            for (int i=0; i<16; i++) {

                cell = new TiledMapTileLayer.Cell();
                tile = new StaticTiledMapTile(assetMgr.getDirt());
                cell.setTile(tile);
                mapLayer.setCell(i,j, cell.setFlipVertically(true));
                layers.add(mapLayer);

            }
        }

        randomNum = new Random();

    }


    public TiledMap getMap() {
        return map;
    }

    public void generateBerry(){
        int tempI = randomNum.nextInt(16) + 1;
        int tempJ = randomNum.nextInt(16) + 1;

        cell = mapLayer.getCell(tempI,tempJ);

        while (!(cell.getTile().getTextureRegion().equals(assetMgr.getDirt()))){
            tempI = randomNum.nextInt(16) + 1;
            tempJ = randomNum.nextInt(16) + 1;

            cell = mapLayer.getCell(tempI,tempJ);
        }

        TiledMapTile newTile = new StaticTiledMapTile(assetMgr.getFood());
        cell = mapLayer.getCell(tempI,tempJ);
        cell.setTile(newTile);
    }

    public void generateItems(int itemID){
        int tempI = randomNum.nextInt(16) + 1;
        int tempJ = randomNum.nextInt(16) + 1;

        cell = mapLayer.getCell(tempI,tempJ);

        while (!(cell.getTile().getTextureRegion().equals(assetMgr.getDirt()))){
            tempI = randomNum.nextInt(16) + 1;
            tempJ = randomNum.nextInt(16) + 1;

            cell = mapLayer.getCell(tempI,tempJ);
        }

        TiledMapTile newTile;
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
            default:
                newTile = new StaticTiledMapTile(assetMgr.getDirt());
                break;
        }

        cell = mapLayer.getCell(tempI,tempJ);
        cell.setTile(newTile);

    }

    public void generateWater(){

    }
}
