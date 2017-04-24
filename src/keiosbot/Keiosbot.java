/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package keiosbot;

import java.net.*;
import java.io.*;
import java.util.Random;



/**
 *
 * @author keios
 */
public class Keiosbot {
    
    private final String name;
    private final String pass;
    
    public Keiosbot(String name, String pass){
        this.name = name;
        this.pass = pass;
    }   //keiosbot
    
    public String getName(){
        return this.name;
    }
    
    public String getPass(){
        return this.pass;
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception{
        Keiosbot keiosbot = new Keiosbot("keios", "aaa");
        new Thread(() -> keiosbot.run(keiosbot)).start();
    }
    
    public void run(Keiosbot keiosbot){
        System.out.println("Running bot " + name);
        final int turnDuration = Integer.parseInt(sendGet(keiosbot, "turn"));
        while(true) {
            sendGet(keiosbot, "move");
            try{
                Thread.sleep(turnDuration);
            }
            catch (InterruptedException e){
                e.printStackTrace();
            }
            
        }
    }
    
    public static String sendGet(Keiosbot keiosbot, String method){
        StringBuilder result = new StringBuilder();
        
        try{
            URL url = new URL(getUrl(keiosbot, method));
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null){
                result.append(line);
            }
            rd.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return result.toString();
        
    }
    private final static Random random = new Random();
    
    public static String randomDirection(){
        int num = random.nextInt(4);
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
    
    public static String getUrl(Keiosbot keiosbot, String method){
        String newurl = null;
        switch (method){
            case "move":
                newurl = "http://localhost:8080/api/act?name=" + keiosbot.getName() + "&pass=" + keiosbot.getPass() + "&action=move&target=" + randomDirection();
                break;
            case "turn":
                newurl = "http://localhost:8080/api/turn-duration";
                break;
            default:
                newurl = "http://localhost:8080/api/alive";
                break;
        }
        return newurl;
    }
    
    
    
} //class
