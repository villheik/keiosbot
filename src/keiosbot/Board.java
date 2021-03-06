/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package keiosbot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author keios
 */
public class Board {
    private static char[][] parsedBoard;
    
    public Board(String boardstring){
        final String[] rows = boardstring.split("\n");
        int y = rows.length;
        int x = rows[0].length();

        Board.parsedBoard = new char[y][x];
    }
    public static void refreshBoard(String boardstring){
        final String[] rows = boardstring.split("\n");
        
        for (int y = 0; y < rows.length; y++){
            char[] chars = rows[y].toCharArray();
            
            for (int x = 0; x < chars.length; x++){
                parsedBoard[y][x] = chars[x];
            }
        }
    }
    
    private static char north(int x, int y){
        try{
            return parsedBoard[y-1][x];
        }
        catch (ArrayIndexOutOfBoundsException e){
            return 'F';
        }
    }
    
    private static char south(int x, int y){
        try{
            return parsedBoard[y+1][x];
        }
        catch (ArrayIndexOutOfBoundsException e){
            return 'F';
        }
    }
    
    private static char east(int x, int y){
        try{
            return parsedBoard[y][x+1];
        }
        catch(ArrayIndexOutOfBoundsException e){
            return 'F';
        }
        
    }
    
    private static char west(int x, int y){
        try{
            return parsedBoard[y][x-1];
        }
        catch(ArrayIndexOutOfBoundsException e){
            return 'F';
        }
    }
    
    public static boolean checkLegality(char character){
        if (character == '#' || character == '@' || character == 'P' || character == 'e' || character == ' '){
            return false;
        }
        else if (character == '.' || character == '$' || character == ':'){
            return true;
        }       
        return false;   //unknown tile
    }
    
    public static ArrayList<String> getLegalMoves(){
        String pos[] = getCurrentPosition().split(",");
        ArrayList<String> legalmoves = new ArrayList();
        int x = Integer.parseInt(pos[0]);
        int y = Integer.parseInt(pos[1]);

        if (checkLegality(north(x, y))){
            legalmoves.add("north");
        }
        if (checkLegality(south(x, y))){
            legalmoves.add("south");
        }
        if (checkLegality(east(x, y))){
            legalmoves.add("east");
        }
        if (checkLegality(west(x, y))){          
            legalmoves.add("west");
        }
        return legalmoves;
    }//legalmoves
    
    public static String getCurrentPosition(){
        for (int y = 0; y < parsedBoard.length; y++){
            for (int x = 0; x < parsedBoard[y].length; x++){
                if (parsedBoard[y][x] == '@'){                                   
                    return x + "," + y;
                }
            }
        }
        return "";
    }
    
    public static String getNextPosition(String move) throws NumberFormatException{
        String pos[] = getCurrentPosition().split(",");
        int x = Integer.parseInt(pos[0]);
        int y = Integer.parseInt(pos[1]);
        
        switch (move){
            case "north":
                y--;
                break;
            case "south":
                y++;
                break;
            case "east":
                x++;
                break;
            case "west":
                x--;
                break;                  
        }  
        return x + "," + y;
    }
    public static ArrayList<String> getTreasures(){
        ArrayList<String> treasures = new ArrayList<>();
        for (int y = 0; y < parsedBoard.length; y++){
            for (int x = 0; x < parsedBoard[y].length; x++){
                if (parsedBoard[y][x] == '$'){                                   
                    treasures.add(x + "," + y);
                }
            }
        }
        return treasures;
    }
    
    public static ArrayList<String> getOtherPlayers(){
        ArrayList<String> otherPlayers = new ArrayList<>();
        for (int y = 0; y < parsedBoard.length; y++){
            for (int x = 0; x < parsedBoard[y].length; x++){
                if (parsedBoard[y][x] == 'P'){                                   
                    otherPlayers.add(x + "," + y);
                }
            }
        }
        return otherPlayers;
    }
    
    public static ArrayList<String> getMonsters(){
         ArrayList<String> monsters = new ArrayList<>();
        for (int y = 0; y < parsedBoard.length; y++){
            for (int x = 0; x < parsedBoard[y].length; x++){
                if (parsedBoard[y][x] == 'e'){                                   
                    monsters.add(x + "," + y);
                }
            }
        }
        return monsters;
    }
    
    public static String checkForAttack() throws NumberFormatException{
        String pos[] = getCurrentPosition().split(",");
        int x = Integer.parseInt(pos[0]);
        int y = Integer.parseInt(pos[1]);

        if (north(x, y) == 'P'){
            return "north";
        }
        if (south(x, y) == 'P'){
            return "south";
        }
        if (east(x, y) == 'P'){
            return "east";
        }
        if (west(x, y) == 'P'){          
            return "west";
        }
        
        return "no";
    }
    
}//class
