package com.bugwars.Assignment2.Game2;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.bugwars.Assignment2.Game2.StateMachine.AntPlayer;
import com.bugwars.Helper.AssetManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * This class is used to generate the textures on the map including the items that are generated.
 */
public class CreateScene {

    private TiledMapTileLayer.Cell cell;
    private TiledMapTile tile;
    private MapLayers layers;
    private TiledMapTileLayer mapLayer, antLayer;
    private TiledMap map;
    private Random randomNum;
    private AssetManager assetMgr;
    private int antHillHome;
    private List<Integer> poisonLocations = new ArrayList<>();
    private List<Integer> waterLocations = new ArrayList<>();
    private List<Integer> berryLocations = new ArrayList<>();

    private TextureRegion antPic;


    public CreateScene(AssetManager assetMgr){
        this.assetMgr = assetMgr;
        map = new TiledMap();
        layers = map.getLayers();
        mapLayer = new TiledMapTileLayer(16, 16, 32, 32);
        antLayer = new TiledMapTileLayer(16, 16, 32, 32);

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
        // This layer will be used to show the ants on the map so we don;t have to flip tiles back
        // and forth
        for (int j=0; j<16; j++) {
            for (int i=0; i<16; i++) {
                cell = new TiledMapTileLayer.Cell();
                antLayer.setCell(i,j, cell.setFlipVertically(true));
                layers.add(antLayer);

            }
        }

        randomNum = new Random();

    }


    /**
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
        int temp;
        // Get the texture that is needed to be generated
        switch(itemID){
            case 0:
                newTile = new StaticTiledMapTile(assetMgr.getFood());
                temp = cell.getTile().getId();
                berryLocations.add(temp);
                break;
            case 1:
                newTile = new StaticTiledMapTile(assetMgr.getPoison());
                temp = cell.getTile().getId();
                System.out.println("Posion at positions " + temp);
                poisonLocations.add(temp);
                break;
            case 2:
                newTile = new StaticTiledMapTile(assetMgr.getWater());
                temp = cell.getTile().getId();
                waterLocations.add(temp);
                break;
            case 3:
                newTile = new StaticTiledMapTile(assetMgr.getAntHill());
                antHillHome = cell.getTile().getId();
                System.out.println("Cell ID of Hill Home " + antHillHome);
                break;
            default:
                newTile = new StaticTiledMapTile(assetMgr.getDirt());
                break;
        }

        // Update cell with new chosen item
        cell = mapLayer.getCell(tempI,tempJ);
        cell.setTile(newTile);

    }

    /**
     * Return the anthill cell ID location
     * @return
     */
    public int getAntHillHome(){
        return antHillHome;
    }


    public void cellUpdateAntPos(AntPlayer ant, int antPosition){
        int antX = antPosition/16;
        int antY = antPosition - (antX*16);
        TiledMapTile newTile= new StaticTiledMapTile(ant.getAntPic());
        newTile.setId(antPosition);
        cell = antLayer.getCell(antX, antY);
        cell.setTile(newTile);


    }

    public void cellUpdateAntPrevPos(int antPosition){
        int antX = antPosition/16;
        int antY = antPosition - (antX*16);
        cell = antLayer.getCell(antX, antY);
        cell.setTile(null);


    }

    /**
     * Check the map cell that the ant is currently on to see what its stepping on.
     * This is used to check if an ant has reached a berry, stepped on poison, water and the Ant Hill.
     * @param antPosition
     * @return
     */
    public String checkCell(int antPosition){
        int antX = antPosition/16;
        int antY = antPosition - (antX*16);
        TextureRegion currentTexture = mapLayer.getCell(antX,antY ).getTile().getTextureRegion();

        if(currentTexture.equals(assetMgr.getFood())){
            return "Berry";

        }
        if(currentTexture.equals(assetMgr.getPoison())){
            return "Poison";

        }
        if(currentTexture.equals(assetMgr.getWater())){
            return "Water";

        }
        if(currentTexture.equals(assetMgr.getAntHill())){
            return "Home";

        }

        return "Dirt";

    }

    /**
     * Go through the 'AntLayer' and remove the dead ants
     * @param antPos
     */
    public void removeDeadAnt(int antPos){
        int antX = antPos/16;
        int antY = antPos - (antX*16);
        cell = antLayer.getCell(antX,antY );
        cell.setTile(null);

    }

    /**
     * See if an ant is already in a cell
     * @param antPosition
     * @return
     */
    public boolean checkForAnt(int antPosition){
        int antX = antPosition/16;
        int antY = antPosition - (antX*16);
        TiledMapTile temp = antLayer.getCell(antX,antY ).getTile();

        if(temp == null){
            return false;
        }
        TextureRegion currentAntTexture = antLayer.getCell(antX,antY ).getTile().getTextureRegion();

        // Check if there is an Ant already at this cell
        if(currentAntTexture.equals(assetMgr.getAnt()) || currentAntTexture.equals(assetMgr.getAntBerry()) || currentAntTexture.equals(assetMgr.getAntWater())){
            return true;
        }else{
            return false;
        }

    }

}
