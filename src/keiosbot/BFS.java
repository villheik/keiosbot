/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package keiosbot;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 *
 * @author keios
 */
public class BFS {
    public static List<String> bfs (Tile start){
        Queue queue = new LinkedList();
        List<String> moves = new ArrayList<>();
        start.setIfVisited(true);
        queue.add(start);
        
        while (!queue.isEmpty()){
            Tile tile = (Tile)queue.remove();
            if (tile.getType().equals(Tile.Type.TREASURE)){
                return moves;
            }
            
            Tile childTile;
            System.out.println("111");
            while((childTile=tile.getUnvisitedNeighbour()) != null){
                childTile.setIfVisited(true);
                moves.add(tile.getDirection(childTile));
                queue.add(childTile);
                System.out.println("asd");
            }
        }
        return null;
    }
}
