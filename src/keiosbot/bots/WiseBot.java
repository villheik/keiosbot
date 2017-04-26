/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package keiosbot.bots;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import keiosbot.Board;
/**
 *
 * @author keios
 */
public class WiseBot {
    
    Board board;
    String name;
    String pass;
    
    public WiseBot(Board board, String name, String pass){
        this.board = board;
        this.name = name;
        this.pass = pass;       
    }
    
    public static String getAction(){
        ArrayList<String> legalMoves = new ArrayList<>();
        try{
            legalMoves = Board.getLegalMoves();     
        }
        catch (NumberFormatException e){
            System.out.println("Couldn't find player");
        }
        List<Integer> scores = new ArrayList<>();
      
        //Calculate score for each move
        for (int i = 0; i < legalMoves.size(); i++){
            scores.add(evaluationFunction(legalMoves.get(i)));
            
        }
        
        int max = -1;
        
        //Get the move with max score
        try{
            max = Collections.max(scores);
            
        }
        catch (NoSuchElementException e){
            return keiosbot.bots.RandomBot.randomDirection();
        }
        
        //If multiple max scores
        List<Integer> maxIndices = new ArrayList<>();
        for (int i = 0; i < scores.size(); i++){
            if (scores.get(i).equals(max)){
                maxIndices.add(i);
            }
        }
                      
        Random random = new Random();
        int chosenIndex = maxIndices.get(random.nextInt(maxIndices.size()));
        
        return legalMoves.get(chosenIndex);
    }
    
    public static int evaluationFunction(String move){   

        int score = 50;
        String currentpos = Board.getNextPosition(move);
        
        //Avoid monsters
        ArrayList<String> monsters = Board.getMonsters();
        int monsterDist = 0;
        int newMonsterDist;
        for (int i = 0; i < monsters.size(); i++){
            newMonsterDist = manhattanDistance(monsters.get(i), currentpos);
            //if a monster is really close, give this move a really bad score
            if (newMonsterDist < 3){
                return newMonsterDist;
            }
            else{
                monsterDist += newMonsterDist * 3;
            }
          
        }
        //Value attacking other players twice as much as getting treasure
        ArrayList<String> otherPlayers = Board.getOtherPlayers();
        int playerDist = 1000;
        int newPlayerDist;
        for (int i = 0; i < otherPlayers.size(); i++){
            newPlayerDist = manhattanDistance(otherPlayers.get(i), currentpos);
            if (newPlayerDist < playerDist){
                playerDist = newPlayerDist;
            }
        }
        //Don't hunt the other players if they're not close
        if (playerDist < 5){
            score -= playerDist * 2;
        }
        //If other player really close, ignore treasure
        if (playerDist < 3){
            return score;
        }
        
        ArrayList<String> treasures = Board.getTreasures();
        int treasureDist = 1000;
        int newTreasureDist;
        for (int i = 0; i < treasures.size(); i++){
            newTreasureDist = manhattanDistance(treasures.get(i), currentpos);
            if (newTreasureDist < treasureDist) {
                treasureDist = newTreasureDist;
            }
        }
        score -= treasureDist;
        return score;
    }
    
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
