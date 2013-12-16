//File: Character.java

/**
 * The character that's in my game. (He's a nasty wasty politician)
 * 
 * @author James Vorderbruggen
 * @version 6 December 2013
 */

public class Character{
  
  private int power;
  private double money;
  private String currentLocation;
  
  public Character(){
    power = 0;
    money = 1000.0;
    currentLocation = "Police Academy";
  }
  
  public Character(int power, double money, String location){
    this.power = power;
    this.money = money;
    this.currentLocation = location;
  }
// Money stuff **************************************************************
  public void addMoney(double addend){
    this.money += addend;
  }
  
  public void subtractMoney(double subtrahend){
    this.money -= subtrahend;
  }
  
  public double getMoney(){
    return this.money;
  }
//Power stuff *************************************************************
  public void addPower(int addend){
    this.power += addend;
  }
  
  public void subtractPower(int subtrahend){
    this.power -= subtrahend;
  }
  
  public int getPower(){
    return this.power;
  }
//Locational *************************************************************
  public void setLocation(String location){
    this.currentLocation = location;
  }
  
  public String getLocation(){
    return this.currentLocation;
  }
//ToString ****************************************************************
  public String toString(){
    return String.format("You have $%.2f and %d power.", this.money, this.power);
  }
}