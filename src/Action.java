//File: Action.java

/**
 * This class holds attributes of an action.
 * 
 * @author James Vorderbruggen
 * @version 8 December 2013
 */

public class Action{
  
  private int power;
  private double money;
  private String name;
  
  public Action(String name, int power, double money){
    this.name = name;
    this.power = power;
    this.money = money;
  }
  
  public String getName(){
    return name;
  }
  
  public int getPower(){
    return power;
  }
  
  public double getMoney(){
    return money;
  }
}