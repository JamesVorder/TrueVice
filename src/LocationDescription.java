 //File: LocationDescription.java

/**
 * This class holds everything necessary to describe a location in True Vice.
 * 
 * @author James Vorderbruggen
 * @version 26 November 2013
 */

public class LocationDescription{
  
  private String name, description, item;
  private int entry;
  private boolean mark = false;
  
  public LocationDescription(String name, int entry, String description, String item){
    this.name = name;
    this.entry = entry;
    this.item = item;
    this.description = description;
  }
  
  public String getName(){
    return this.name;
  }
  
  public String getDescription(){
    return this.description;
  }
  
  public String getItem(){
    return this.item;
  }
  
  public int getEntry(){
    return this.entry;
  }
  
  public void mark(){
    this.mark = true;
  }
  
  public void unmark(){
    this.mark = false;
  }
  
  public boolean isMarked(){
    return mark;
  }
  
  public String toString(){
    return String.format(this.name + ": " + this.description + "This location has a " + item + ".");
  }
  
}