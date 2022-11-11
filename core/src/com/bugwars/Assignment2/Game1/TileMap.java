package com.bugwars.Assignment2.Game1;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import java.util.ArrayList;
import java.util.List;

public class TileMap {

    private Table tbl;
    private Stage stg;
    private Skin skin;
    private TextureAtlas atlas;
    private Tile[] tiles = new Tile[16 * 16];

    private int maxSize = 15;
    private Button[] btnTiles;



    public TileMap(Button[] btnTiles){
        this.btnTiles = btnTiles;
        for(int j = 0; j<16; j++ ) {
            for (int i = 0; i < 16; i++) {
                String currStyle = "";
                Button tile = btnTiles[(i*16) + j];

                // Get Tile image
                if(tile.getStyle().up == null){
                    currStyle = tile.getStyle().checked.toString();

                }else{
                    currStyle = tile.getStyle().up.toString();
                }

                // Rock obstacle found, no connections or cost, add tile and go to next button in list
                if(currStyle == "Rock_Tile"){
                    Tile newTile = new Tile((i*16) + j,currStyle);
                    tiles[(i*16) + j] = newTile;

                }else{
                    Tile newTile = new Tile((i*16) + j, currStyle);
                    System.out.println("First print: "+j +", "+ i);
                    List<Integer> list = getConnections(j,i);
                    System.out.println("2nd print: "+j +", "+ i + "Here is the list: " + list);
                }



                Tile newTile = new Tile((i*16) + j, currStyle);

            }
        }


    }

    public Table getTable(){
        return tbl;
    }

    /**
     * The helper function below will create a list of connection nodes from the point that was given
     * to the function. Point = (row, col) which represents the button that was used in the original
     * tile map the user created.
     * @param row
     * @param col
     * @return an array of the connecting Tiles (nodes)
     */
    private List<Integer> getConnections(int row, int col){

        List<Integer> connections = new ArrayList<>();


        if(row-1 < 0){ // Can't do A, B, C
            if(col-1 <0){
                // do D, E, F
                System.out.println("do D, E, F");
                connections.add(switchCheck("D", row, col));
                connections.add(switchCheck("E", row, col));
                connections.add(switchCheck("F", row, col));

            }else if(col+1 > maxSize){
                // do F, G, H
                System.out.println("do F, G, H");
                connections.add(switchCheck("F", row, col));
                connections.add(switchCheck("G", row, col));
                connections.add(switchCheck("H", row, col));

            }else{
                // do D, E, F, G, H
                System.out.println("do D, E, F, G, H");
                connections.add(switchCheck("D", row, col));
                connections.add(switchCheck("E", row, col));
                connections.add(switchCheck("F", row, col));
                connections.add(switchCheck("G", row, col));
                connections.add(switchCheck("H", row, col));

            }
        }else if(row+1 > maxSize){ // Can't do E, F, G
            if(col-1 <0){
                // do B, C, D
                System.out.println("do B, C, D");
                connections.add(switchCheck("B", row, col));
                connections.add(switchCheck("C", row, col));
                connections.add(switchCheck("D", row, col));



            }else if(col+1 > maxSize){
                // do A, B, H
                System.out.println("do A, B, H");
                connections.add(switchCheck("A", row, col));
                connections.add(switchCheck("B", row, col));
                connections.add(switchCheck("H", row, col));

            }else{
                // do A, B, C, D, H
                System.out.println("ddo A, B, C, D, H");
                connections.add(switchCheck("A", row, col));
                connections.add(switchCheck("B", row, col));
                connections.add(switchCheck("C", row, col));
                connections.add(switchCheck("D", row, col));
                connections.add(switchCheck("H", row, col));
            }

        }else if(col-1 <0){
            // do B, C, D, E, F
            connections.add(switchCheck("B", row, col));
            connections.add(switchCheck("C", row, col));
            connections.add(switchCheck("D", row, col));
            connections.add(switchCheck("E", row, col));
            connections.add(switchCheck("F", row, col));

        }
        else if(col+1 > maxSize){
            // do A, B, F, G, H
            connections.add(switchCheck("A", row, col));
            connections.add(switchCheck("B", row, col));
            connections.add(switchCheck("F", row, col));
            connections.add(switchCheck("G", row, col));
            connections.add(switchCheck("H", row, col));

        }
        else{
            // do All
            System.out.println("ALL");
            connections.add(switchCheck("A", row, col));
            connections.add(switchCheck("B", row, col));
            connections.add(switchCheck("C", row, col));
            connections.add(switchCheck("D", row, col));
            connections.add(switchCheck("E", row, col));
            connections.add(switchCheck("F", row, col));
            connections.add(switchCheck("G", row, col));
            connections.add(switchCheck("H", row, col));
        }

        connections.removeIf(val -> val < 0);
        return connections;

    }

    private String getButtonStyle(Button tile){
        String currStyle = "";

        // Get Tile image
        if(tile.getStyle().up == null){
            currStyle = tile.getStyle().checked.toString();
        }else{
            currStyle = tile.getStyle().up.toString();
        }

        return currStyle;
    }

    /**
     * Below is a list of the areas in the map (abstract matrix) that the helper function will look
     * at, they're lettered as to unclog the amount of comments in the code.
     * A) (-1, -1)
     * B) (-1, ~)
     * C) (-1, +1)
     * D) (~, +1)
     * E) (+1, +1)
     * F) (+1, ~)
     * G) (+1, -1)
     * H) (~, -1)
     * @param letter
     * @param row
     * @param col
     * @return
     */
    private int switchCheck(String letter, int row, int col){

        Button tile;
        String style = "";
        int tileID = 0;

        switch (letter){
            case "A":
                tile = btnTiles[((row-1)*16) + col-1];
                style = getButtonStyle(tile);
                if(style.equals("Rock_Tile")){
                    tileID = -1;
                    break;
                }
                tileID = ((row-1)*16)  + col-1;
                break;
            case "B":
                tile = btnTiles[((row-1)*16) + col];
                style = getButtonStyle(tile);
                if(style.equals("Rock_Tile")){
                    tileID = -1;
                    break;
                }
                tileID = ((row-1)*16) + col;
                break;
            case "C":
                tile = btnTiles[((row-1)*16) + col+1];
                style = getButtonStyle(tile);
                if(style.equals("Rock_Tile")){
                    tileID = -1;
                    break;
                }
                tileID = ((row-1)*16) + col+1;
                break;
            case "D":
                tile = btnTiles[(row*16) + col+1];
                style = getButtonStyle(tile);
                int idk = (row*16) + col+1;
                System.out.println(idk + " D " + style);
                if(style.equals("Rock_Tile")){
                    tileID = -1;
                    break;
                }
                tileID = (row*16) + col+1;
                break;
            case "E":
                tile = btnTiles[((row+1)*16) + col+1];
                style = getButtonStyle(tile);
                System.out.println(((row+1)*16) + col+1 + " E " + style);
                if(style.equals("Rock_Tile")){
                    tileID = -1;
                    break;
                }
                tileID = ((row+1)*16) + col+1;
                break;
            case "F":
                tile = btnTiles[((row+1)*16)+ col];
                style = getButtonStyle(tile);
                System.out.println(((row+1)*16)+ col + " F " + style);
                if(style.equals("Rock_Tile")){
                    tileID = -1;
                    break;
                }
                tileID = ((row+1)*16)+ col;
                break;
            case "G":
                tile = btnTiles[((row*16)+1) + col-1];
                style = getButtonStyle(tile);
                System.out.println(" G " + style);
                if(style.equals("Rock_Tile")){
                    tileID = -1;
                    break;
                }
                tileID = ((row+1)*16)+ col-1;
                break;
            case "H":
                tile = btnTiles[(row*16) + col-1];
                style = getButtonStyle(tile);
                System.out.println(" H " + style);
                if(style.equals("Rock_Tile")){
                    tileID = -1;
                    break;
                }
                tileID = (row*16) + col-1;
                break;
        }

        return tileID;
    }

}
