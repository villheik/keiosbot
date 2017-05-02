/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package keiosbot;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author keios
 */
public class Tile {
    public enum Type{
        WALL,
	FLOOR,
	PLAYER,
	OTHER_PLAYER,
	TREASURE,
	MONSTER,
	UNKNOWN,
	SPAWN_POINT;
    }
    
    private Type type;
    private List<Tile> neighbours;
    private boolean isVisited;
    private int yCoord;
    private int xCoord;
    
    public Tile(char newTile, String coordinates){
        switch(newTile){
            case ('#'):
                this.type = Type.WALL;
                break;
            case ('.'):
                this.type = Type.FLOOR;
                break;
            case ('@'):
                this.type = Type.PLAYER;
                break;
            case ('P'):
                this.type = Type.OTHER_PLAYER;
                break;
            case ('$'):
                this.type = Type.TREASURE;
                break;
            case ('e'):
                this.type = Type.MONSTER;
                break;
            case (' '):
                this.type = Type.UNKNOWN;
                break;
            case (':'):
                this.type = Type.SPAWN_POINT;
                break;
            default:
                this.type = Type.UNKNOWN;
                break;
                       
        } //switch
        
        this.isVisited = false;
        
        this.neighbours = new ArrayList<>();
        String coords[] = coordinates.split(",");
        this.xCoord = Integer.parseInt(coords[0]);
        this.yCoord = Integer.parseInt(coords[1]);
        
    }//constructor
    
    public void addNeighbours(char[][] board){
        String coord;
        Tile neighbour;
                       
        if (checkNeighbour(this.xCoord + 1, this.yCoord, board)){
            coord = Integer.toString(this.xCoord + 1) + "," + Integer.toString(this.yCoord);
            neighbour = Board.getTile(coord);

            if (neighbour == null){
                neighbour = new Tile(board[this.yCoord][this.xCoord + 1], coord);
            }                   
            this.neighbours.add(neighbour);
        }
              
        if (checkNeighbour(this.xCoord - 1, this.yCoord, board)){
            coord = Integer.toString(this.xCoord - 1) + "," + Integer.toString(this.yCoord);
            neighbour = Board.getTile(coord);
            
            if (neighbour == null){
                neighbour = new Tile(board[this.yCoord][this.xCoord - 1], coord);
            }
            this.neighbours.add(neighbour);
        }
        
        if (checkNeighbour(this.xCoord, this.yCoord + 1, board)){
            coord = Integer.toString(this.xCoord) + "," + Integer.toString(this.yCoord + 1);
            neighbour = Board.getTile(coord);
            
            if (neighbour == null){
                neighbour = new Tile(board[this.yCoord + 1][this.xCoord], coord);              
            }
            this.neighbours.add(neighbour);
            
        }
        
        if (checkNeighbour(this.xCoord, this.yCoord - 1, board)){
            coord = Integer.toString(this.xCoord) + "," + Integer.toString(this.yCoord - 1);
            neighbour = Board.getTile(coord);
            
            if (neighbour == null){
                neighbour = new Tile(board[this.yCoord - 1][this.xCoord], coord);
            }
            this.neighbours.add(neighbour);          
        }       
    }
    
    public boolean checkNeighbour(int x, int y, char[][] board){
        if ((y >= 0) && (y < board.length)&& (x >= 0) && (x < board[y].length)){
            if (board[y][x] != '#' && board[y][x] != ' '){
                return true;
            }          
        }
        return false;
    }
    
    public Type getType(){
        return this.type;
    }
    
    public List<Tile> getNeighbours(){
        return this.neighbours;
    }
    
    public void setIfVisited(boolean visited){
        this.isVisited = visited;
    }
    
    public boolean getIfVisited(){
        return this.isVisited;
    }
    
    public Tile getUnvisitedNeighbour(){
        for (int i = 0; i < this.neighbours.size(); i++){
            Tile neighbour = neighbours.get(i);
            if (!neighbour.isVisited){
                return neighbour;
            }
        }
        return null;
    }
    
    public String getDirection(Tile child){
        if (this.yCoord == child.yCoord - 1){
            return "north";
        }
        
        if (this.yCoord == child.yCoord - 1){
            return "south";
        }
        
        if (this.xCoord == child.xCoord - 1){
            return "east";
        }
        
        if (this.xCoord == child.xCoord + 1){
            return "west";
        }
        return null;
    }
} //class
