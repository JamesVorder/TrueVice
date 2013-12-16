//File: GameDriver.java

/**
 * This class runs the game for lab 19.
 * 
 * @author James Vorderbruggen
 * @version 3 December 2013
 */

import java.util.concurrent.*;
import java.io.*;
import java.util.*;

public class GameDriver{
  public GameLayout game;
  
  public void welcome(){
    System.out.println("///////////////////////////////////////////////////////////////////////////////");
    System.out.println("Welcome to True Vice!");
    System.out.println("This game is a bitter sattire that you might not even find funny.  Deal with it.");
    System.out.println("The premise is that you're a crooked cop... so a regular cop... that's just graduated from Police Academy.");
    System.out.println("Have fun!");
    System.out.println("///////////////////////////////////////////////////////////////////////////////");
  }
  
  public LocationDescription search(String location, String item){
    LinkedTransferQueue<LocationDescription> queue = new LinkedTransferQueue<LocationDescription>();
    game.get(location).mark();
    queue.add(game.get(location));
    while(!queue.isEmpty()){
      LocationDescription current = queue.remove();
      if(current.getItem().equals(item)){
        Iterator<String> iterator = game.iterator();
        while(iterator.hasNext()){
          game.get(iterator.next()).unmark();
        }
        return current;
      }
      Iterator<String> iterator = game.iteratorByLocation(current.getName());
      while(iterator.hasNext()){
        LocationDescription next = game.get(iterator.next());
        if(!next.isMarked()){
          game.get(next.getName()).mark();
          queue.add(next);
        }
      }
    }
    Iterator<String> iterator = game.iterator();
    while(iterator.hasNext()){
      game.get(iterator.next()).unmark();
    }
    return null;
  }
  
  public void listNames(){
    Iterator<String> names = game.iterator();
    System.out.println("///////////////////////////////////////////////////////////////////////////////");
    while(names.hasNext()){
      System.out.println(names.next());
    }
    System.out.println("///////////////////////////////////////////////////////////////////////////////");
  }
  
  public void listProperties(){
    System.out.println(game.locationToString(game.getCharacter().getLocation()));
  }
  
  public void listConnections(){
    Iterator<String> connections = game.iteratorByLocation(game.getCharacter().getLocation());
    System.out.println("///////////////////////////////////////////////////////////////////////////////");
    while (connections.hasNext()){
      System.out.println(connections.next());
    }
    System.out.println("///////////////////////////////////////////////////////////////////////////////");
  }
  
  public void jumpToLocation(String jumpTo){
    Iterator<String> connections = game.iteratorByLocation(game.getCharacter().getLocation());
    boolean found = false;
    while (connections.hasNext()){
      String temp = connections.next();
      if(jumpTo.equals(temp)){
        found = true;
        if(game.getCharacter().getPower() < game.get(temp).getEntry()){
          System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
          System.out.println("You have insufficient power to enter that area.  Go bust some heads and come back later.");
          System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        }
        else{
          game.getCharacter().setLocation(jumpTo);
        }
      }
    }
    if(!found){
      System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
      System.out.println("Could not find that location.");
      System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    }
  }
  
  public void performAction(String location, String action){
    String place = game.getCharacter().getLocation();
    Action actionTaken = game.getAction(place, action);
    game.getCharacter().addPower(actionTaken.getPower());
    game.getCharacter().addMoney(actionTaken.getMoney());
  }
  
  //***********************************************************************************************
  public void commandMenu(){
    System.out.println("");
    System.out.println(game.getCharacter().toString());
    System.out.println("Enter a command:");
    System.out.println("L to look around.");
    System.out.println("C to check the doors.");
    System.out.println("M to move.");
    System.out.println("B to get a birds eye view of the organization.");
    System.out.println("S to search for an item.");
    System.out.println("A to perform an action.");
    System.out.println("Q to quit.");
    System.out.println("");
  }
  
  public void doCommand()throws IOException{
    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    String command = in.readLine();
    command = command.toUpperCase();
    try{
    switch(command.charAt(0)){
      case 'L': 
        this.listProperties();
        break;
      case 'C': 
        this.listConnections();
        break;
      case 'M':
        System.out.println("************************************************************");
        System.out.println("Where would you like to travel to?");
        System.out.println("************************************************************");
        String newloc = in.readLine();
 System.out.println("");
        this.jumpToLocation(newloc);
        break;
      case 'B':
        this.listNames();
        break;
      case 'S':
        System.out.println("************************************************************");
        System.out.println("What would you like to search for?");
        System.out.println("************************************************************");
        String item = in.readLine();
 System.out.println("");
        try{
        String place = this.search(game.getCharacter().getLocation(), item).getName();
        System.out.println("///////////////////////////////////////////////////////////////////////////////");
        System.out.println("The item was found at " + place + ".");
        System.out.println("///////////////////////////////////////////////////////////////////////////////");
 System.out.println("");
        }
        catch(NullPointerException npe){
          System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
          System.out.println("The item could not be found.");
          System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
   System.out.println("");
        }
        break;
      case 'A':
        try{
        System.out.println("************************************************************");
        System.out.println("What action would you like to perform?");
        System.out.println("You can...");
        System.out.println(game.getActionsList(game.getCharacter().getLocation()));
        System.out.println("************************************************************");
      }
        catch(NullPointerException npe){
          System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
          System.out.println("There are no actions to perform at this location.");
          System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
   System.out.println("");
        }
        try{
          String action = in.readLine();
          System.out.println("");
          this.performAction(game.getCharacter().getLocation(), game.getAction(game.getCharacter().getLocation(), action).getName());
        }
        catch(NullPointerException npe){
          System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
          System.out.println("The action you entered cannot be performed at this location.");
          System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
   System.out.println("");
        }
        break;
        
      case 'Q':
        System.exit(0);
        break;
    }
    }
    catch(StringIndexOutOfBoundsException siobe){
      System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
      System.out.println("You need to enter a command to continue.");
      System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
      
    }
  }
  //************************************************************************************************
  public static void main(String[] args){
    GameDriver truevice = new GameDriver();
    try{
      truevice.game = new GameLayout("locations.Locations", "connections.Connections", "actions.Actions");
      truevice.welcome();
      while(1 == 1){
        truevice.commandMenu();
        truevice.doCommand();
      }
    }
    catch(Exception e){
      System.out.println(e.toString());
    }
  }
}
