package edu.cpp.cs.cs141.final_prog;
/**
 * @author Mostafa, Charlie, Saul, and Jae.
 *
 */
import java.io.Serializable;
import java.util.Scanner;

public class UserInterface implements Serializable {
	private static final long serialVersionUID = -2409537569479140608L;
	private String gameEntityName;
	int[][] arrLocation;
	int[] location;
	
	private Grid grid = new Grid();
	private Scanner kb = new Scanner(System.in);
	
	public Grid getGrid(){
		return this.grid;
	}
	public void printGrid(GameEntities[][] grid){
		for(int x = 0; x < 9; x++){
			System.out.println();
			for(int y = 0; y < 9; y++){
				gameEntityName = this.grid.getEntity(x, y).getName();
				
				switch (gameEntityName){
				case "room": System.out.print("[ R ]");
					break;
				case "player": System.out.print("[ P ]");
					break;
				case "enemy": System.out.print("[ E ]");
					break;
				case "bullet": System.out.print("[ B ]");
					break;
				case "invincibility": System.out.print("[ I ]");
					break;
				case "radar": System.out.print("[ * ]");
					break;
				case "empty": System.out.print("[   ]");
					break;
				case "brief": System.out.print("[R C]");
				}
			}
		}
		System.out.println();
	}
	public void printNormalGrid(GameEntities[][] grid){
		for(int x = 0; x < 9; x++){
			System.out.println();
			for(int y = 0; y < 9; y++){
				gameEntityName = this.grid.getEntity(x, y).getName();
				
				switch (gameEntityName){
				case "brief": System.out.print("[ R ]");
					break;
				case "room": System.out.print("[ R ]");
					break;
				case "player": System.out.print("[ P ]");
					break;
				case "enemy": System.out.print("[   ]");
					break;
				case "invincibility": System.out.print("[ I ]");
					break;
				case "radar": System.out.print("[ * ]");
				    break;
				case "bullet": System.out.print("[ B ]");
				    break;
				case "empty": System.out.print("[   ]");
					break;
				}
			}
		}
		System.out.println();
	}
	public char getCommand(){
		System.out.println("\nIn which direction would you like to move to? (wasd keys)");
		System.out.println("[w]Up     [s]Down     [a]Left     [d]Right");
		char y = kb.nextLine().charAt(0);
		return y;
	}
	public int getMode(){
		System.out.println("Choose mode.");
		int mode = kb.nextInt();
		return mode;
	}
	public void moveEntities(){
		grid.movePlayer(getCommand());
		grid.moveEnemy();
	}
    public void lookPlayer(){
		System.out.println("\nIn which direction would you like to look? (wasd keys)");
		System.out.println("[w]Up     [s]Down     [a]Left     [d]Right");
		
		String direction = kb.nextLine();
		location = grid.getLocation(grid.getPlayer());
		String ifNoEnemy = "There is nothing ahead of you.";
		boolean enemyPresence = false;
		switch (direction){
			case "w":
				for (int y = location[0]; y > -1; y--){
						if (grid.getEntity(y, location[1]).getName() == "enemy"){
							enemyPresence = true;
							System.out.println("There is a weaboo in that direction!");
							break;
						} else {
							enemyPresence = false;
							
						}
				}
				
				if (enemyPresence == false){
					System.out.println(ifNoEnemy);
				}
				break;
			case "d":
					for (int x = location[1]; x < 9; x++){
						if (grid.getEntity(location[0], x).getName() == "enemy"){
							enemyPresence = true;
							System.out.println("There is a weaboo in that direction!");
							break;
						} else {
							enemyPresence = false;
							
						}
					}
					
			     if (enemyPresence == false){
					System.out.println(ifNoEnemy);
			     }
				
				break;
			case "a":
					for (int x = 0; x < location[1]; x++){
						if (grid.getEntity(location[0], x).getName() == "enemy"){
							enemyPresence = true;
							System.out.println("There is a weaboo in that direction!");
							break;
						} else {
							enemyPresence = false;
							
						}
					}
					
				if (enemyPresence == false){
					System.out.println(ifNoEnemy);
				}
				
				break;
			case "s":
				for (int y = location[0]; y < 9; y++){
						if (grid.getEntity(y, location[1]).getName() == "enemy"){
							enemyPresence = true;
							System.out.println("There is a weaboo in that direction!");
							break;
						} else {
							enemyPresence = false;
							
						}
				}
				
				if (enemyPresence == false){
					System.out.println(ifNoEnemy);
				}
				
				break;
			default:
				System.out.println("You've entered an invalid input. Try again.");
				lookPlayer();
				break;
		}
		 
		
	}
    
    public void playerUpdating(){
    	if (grid.getPlayerHealth() == 3){
    		System.out.println("You have 3 health points.");
    		System.out.println("You currently have " + grid.getPlayerAmmo() + " bullet(s).");
    	} else {
    		if (grid.getPlayerHealth() == 100){
    			System.out.println("You got the invincibility power-up. You are immune for the next five attacks.");
    			System.out.println("You currently have " + grid.getPlayerAmmo() + " bullet(s).");
    		} else {
    		
	    		System.out.println("Weaboos don't care much for stab wounds ... keep going, Sensei!");
	    		System.out.println("You have " + grid.getPlayerHealth() + " points.");
	    		System.out.println("Your currently have " + grid.getPlayerAmmo() + " bullet(s).");
    		}
    	}
    }
    
    public void loadGrid(Grid newGrid){
    	this.grid = newGrid;
    }
    public void getGridEntity(int y, int x){
    	grid.getEntity(y, x);
    }
    
    public String promptForSave(){
    	System.out.println("\nWould you like to save and quit?");
    	System.out.println("[no] or [yes]");
    	return kb.nextLine();
    }
    public void saveGameMessage(){
    	System.out.println("You have saved the game. The game will now exit.");
    }
    
}
