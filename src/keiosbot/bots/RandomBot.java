/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package keiosbot.bots;

import java.util.Random;
import keiosbot.Board;

public class RandomBot {
    
    private final static Random randomdir = new Random();
    
    public static String randomDirection(){
        int num = randomdir.nextInt(4);
        String direction;
        switch(num){
            case 0:
                direction = "south";
                break;
            case 1:
                direction = "north";
                break;
            case 2:
                direction = "west";
                break;
            case 3:
                direction = "east";
                break;
            default:
                direction = "south";
                break; 
        }
        return direction;
    }
}
