/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package keiosbot.bots;

import java.util.List;
import keiosbot.BFS;
import keiosbot.Board;
import keiosbot.Tile;

/**
 *
 * @author keios
 */
public class BFSbot {
    Board board;
    String name;
    String pass;
    
    public BFSbot(Board board, String name, String pass){
        this.board = board;
        this.name = name;
        this.pass = pass;       
    }
    
    public static List<String> getActions(){
        String position = Board.getCurrentPosition();
        Tile startTile = Board.getTile(position);
        if (startTile == null){
            String coords[] = position.split(",");
            int xCoord = Integer.parseInt(coords[0]);
            int yCoord = Integer.parseInt(coords[1]);
            startTile = new Tile(Board.getBoard()[yCoord][xCoord], position);
        }
        List<String> actions = BFS.bfs(startTile);
        if (actions.isEmpty()){
            actions.add(RandomBot.randomDirection());
            return actions;
        }
        return actions;       
    }
}
