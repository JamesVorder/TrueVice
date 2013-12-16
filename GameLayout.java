//File: GameLayout.java
/**
 * This class handles the connection of all the locations to eachother in True Vice.
 * 
 * @author James Vorderbruggen
 * @version 26 November 2013
 */

import java.io.*;
import java.util.*;

public class GameLayout{
  
  private Map<String, ArrayList<String>> connections;
  private Map<String, LocationDescription> locations;
  private Character character = new Character();
  private Map<String, ArrayList<Action>> actions;
  
  public GameLayout(String locationsFile, String connectionsFile, String actionsFile) throws Exception, FileNotFoundException, IOException{
  //Locations*************************************************************************************************
    BufferedReader reader = new BufferedReader(new FileReader(locationsFile));
    locations = new TreeMap<String, LocationDescription>();
    while(reader.ready()){
      String name = reader.readLine();
      int entry = Integer.parseInt(reader.readLine());
      String description = reader.readLine();
      String item = reader.readLine();
      LocationDescription newLocation = new LocationDescription(name, entry, description, item);
      locations.put(name, newLocation);
      String extra = reader.readLine();
      if(!extra.equals("_")){
        throw new Exception("The locations file was not set up properly. Found " + extra + " but expected _");
      }
    }
    reader.close();
  //Connections************************************************************************************************
    BufferedReader readertwo = new BufferedReader(new FileReader(connectionsFile));
    connections = new TreeMap<String, ArrayList<String>>();
    while(readertwo.ready()){
      ArrayList<String> links = new ArrayList<String>();
      String name = readertwo.readLine();
      int numConnections = Integer.parseInt(readertwo.readLine());
      for(int i = 0; i < numConnections; i++){
        links.add(readertwo.readLine());
      }
      String extra = readertwo.readLine();
      if(!extra.equals("_")){
        throw new Exception("The connections file was not set up properly. Found " + extra + " but expected _");
      }
      connections.put(name, links);
    }
    readertwo.close();
  //Actions*****************************************************************************************************
    BufferedReader readerthree = new BufferedReader(new FileReader(actionsFile));
    actions = new TreeMap<String, ArrayList<Action>>();
    while(readerthree.ready()){
      ArrayList<Action> actionsByLocation = new ArrayList<Action>();
      String name = readerthree.readLine();
      int numActions = Integer.parseInt(readerthree.readLine());
      for(int i = 0; i < numActions; i++){
        String actionName = readerthree.readLine();
        int power = Integer.parseInt(readerthree.readLine());
        double money = Double.parseDouble(readerthree.readLine());
        actionsByLocation.add(new Action(actionName, power, money));
      }
      String extra = readerthree.readLine();
      if(!extra.equals("_")){
        throw new Exception("The actions file was not set up properly. Found " + extra + " but expected _");
      }
      actions.put(name, actionsByLocation);
    }
    readerthree.close();
  }
  
  public Iterator<String>  iterator(){
    Iterator<String> iterator = locations.keySet().iterator();
    return iterator;
  }
  
  public Iterator<String> iteratorByLocation(String location){
    if(connections.get(location) == null){
      return null;
    }
    return connections.get(location).iterator();
  }
  
  public String getLocationDescription(String name){
    return locations.get(name).getDescription();
  }
  
  public LocationDescription get(String name){
    return locations.get(name);
  }
  
  public String locationToString(String location){
    String locationString = "";
    Iterator<String> iterator = this.iterator();
    locationString += String.format("%n" + locations.get(location).toString());
    locationString += String.format("%n" + "This location is connected to ");
    Iterator<String> connectionsIterator = this.iteratorByLocation(location);
    while(connectionsIterator.hasNext()){
      locationString += String.format("%n" + connectionsIterator.next());
    }
    return locationString;
  }
  
  public Character getCharacter(){
    return this.character;
  }
  
  public Action getAction(String location, String actionName){
    Iterator<Action> iterator = actions.get(location).iterator();
    while(iterator.hasNext()){
      Action next = iterator.next();
      if(next.getName().equals(actionName)){
        return next;
      }
    }
    return null;
  }
  
  public String getActionsList(String location){
    Iterator<Action> iterator = actions.get(location).iterator();
    String output = "";
    while(iterator.hasNext()){
      String next = iterator.next().getName();
      output += String.format("%s%n", next);
    }
    return output;
  }
  
  public void writeToFile(String fileName) throws IOException{
    PrintWriter printer = new PrintWriter(new FileWriter(fileName));
    Iterator<String> iterator = this.iterator();
    while (iterator.hasNext()){
      String location = iterator.next();
      printer.println(locations.get(location).toString());
      printer.println("This location is connected to ");
      Iterator<String> connectionsIterator = this.iteratorByLocation(location);
      while(connectionsIterator.hasNext()){
        printer.println(connectionsIterator.next());
      }
      printer.println("");
    }
    printer.close();
  }
}