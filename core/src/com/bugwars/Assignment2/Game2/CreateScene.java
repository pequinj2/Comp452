package com.bugwars.Assignment2.Game2;

import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.bugwars.Helper.AssetManager;

public class CreateScene {

    private TiledMapTileLayer.Cell cell;
    private TiledMapTile tile;
    private MapLayers layers;
    private TiledMapTileLayer mapLayer;
    private TiledMap map;


    public CreateScene(AssetManager assetMgr){

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
    }


    public TiledMap getMap() {
        return map;
    }
}
