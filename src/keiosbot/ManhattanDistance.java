/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package keiosbot;

/**
 *
 * @author keios
 */
public class ManhattanDistance {
    public static int manhattanDistance(String target, String current){
        String currentpos[] = current.split(",");
        String targetpos[] = target.split(",");
        
        int currentY = Integer.parseInt(currentpos[0]);
        int currentX = Integer.parseInt(currentpos[1]);
        int targetY = Integer.parseInt(targetpos[0]);
        int targetX = Integer.parseInt(targetpos[1]);
        
        int distance = Math.abs(targetY - currentY) + Math.abs(targetX - currentX);
        return distance;
    }
}
