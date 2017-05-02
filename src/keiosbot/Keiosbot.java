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
public class Keiosbot {
      
    private final String botType;
    private final String name;
    private final String pass;
    
    public Keiosbot(String name, String pass, String bottype){
        this.name = name;
        this.pass = pass;
        this.botType = bottype;
    }   
    
    public String getName(){
        return this.name;
    }
    
    public String getPass(){
        return this.pass;
    }
    
    public String getType(){
        return this.botType;
    }
    /**
     * @param args komentoriviargumentit
     */
    public static void main(String[] args) throws Exception{
        Keiosbot keiosbot = new Keiosbot("keios", "aaa", "wise");
        // Keiosbot randombot = new Keiosbot("random", "aaa", "random");
        //Keiosbot BFSbot = new Keiosbot("bfs", "aaa", "BFS");
        String map = API.sendGet(keiosbot.getName(), keiosbot.getPass(), keiosbot.getType(), "getboard");
        new Board(map);
        new Thread(() -> run(keiosbot)).start();
        // new Thread(() -> run(randombot)).start();
        //new Thread(() -> run(BFSbot)).start();
    }
    
    public static void run(Keiosbot keiosbot){
        System.out.println("Running bot " + keiosbot.getName());
        final int turnDuration = Integer.parseInt(API.sendGet(keiosbot.getName(), keiosbot.getPass(), keiosbot.getType(), "turnduration"));
        API.sendGet(keiosbot.getName(), keiosbot.getPass(), keiosbot.getType(), "addplayer");
        String map;
        while(true) {
            //refresh map
            map = API.sendGet(keiosbot.getName(), keiosbot.getPass(), keiosbot.getType(), "getboard");
            Board.refreshBoard(map);
            API.sendGet(keiosbot.getName(), keiosbot.getPass(), keiosbot.getType(), "move");
            try{
                Thread.sleep(turnDuration / 2);
            }
            catch (InterruptedException e){
                e.printStackTrace();
            }            
        }//loop
    } //run
} //class
