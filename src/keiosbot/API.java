/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package keiosbot;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import keiosbot.bots.BFSbot;
import keiosbot.bots.RandomBot;
import keiosbot.bots.WiseBot;

/**
 *
 * @author keios
 */
public class API {
     /**
     * 
     * @param keiosbot 
     * @param method
     * @return 
     */
    public static String sendGet(String name, String pass, String type, String method){
        StringBuilder result = new StringBuilder();
        
        try{
            URL url = new URL(generateUrl(name, pass, type, method));
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null){
                result.append(line + ("\n"));
            }
            rd.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return result.toString().trim();       
    }
    
    
    public static String generateUrl(String name, String pass, String type, String method){
        String newurl = null;
        String baseurl = "http://localhost:8080/api/";
        
        switch (method){
            case "move":{
                String direction;
               
                switch(type){
                    case "random":
                        direction = RandomBot.randomDirection();
                        break;
                    case "wise":
                        direction = WiseBot.getAction();
                        break;
                    case "BFS":
                        List<String> actions = BFSbot.getActions();
                        direction = actions.remove(actions.size() - 1);
                        break;
                    default:
                        direction = "north";
                        break;
                }
                String attackDir = "no";
                try{
                    attackDir = Board.checkForAttack();
                }
                catch (NumberFormatException e){
                    System.out.println("Couldn't find player pos");
                }
                if ("no".equals(attackDir)){
                    newurl = baseurl + "act?name=" + name + "&pass=" + pass + "&action=move&target=" + direction;                    
                }
                else{
                    newurl = baseurl + "act?name=" + name + "&pass=" + pass + "&action=attack&target=" + attackDir;  
                }
                break;
                
            } //case "move"
            
            case "turnduration":
                newurl = baseurl + "turn-duration";
                break;
                
            case "addplayer":
                newurl = baseurl + "add-player?name=" + name + "&pass=" + pass;
                break;
            
            case "getboard":
                newurl = baseurl + "board?name=" + name;
                break;
                
            case "nextboard":
                newurl = baseurl + "next-board?name=" + name;
                break;
                
            default:
                newurl = baseurl + "alive";
                break;
        }
        return newurl;
    }
}
