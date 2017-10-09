package edu.cpp.cs.cs141.final_prog;
/**
 * @author Mostafa, Charlie, Saul, and Jae.
 *
 */
public class Player extends GameEntities {
	private int health = 3;
	private int ammo = 1;
	
	public Player() {
		super.setName("player");
	}
	
	public int setHealth(){
		health = 3;
		return health;
	}
	public void setHealth(int input){
		this.health = input;
	}
	public int getHealth(){
		return this.health;
	}
	public int getAmmo(){
		return ammo;
	}
	public void setAmmo(int input){
		this.ammo = input;
	}
	
}