package edu.cpp.cs.cs141.final_prog;
/**
 * @author Mostafa, Charlie, Saul, and Jae.
 *
 */
import java.io.Serializable;
import java.util.Random;

public class Grid implements Serializable{
	private static final long serialVersionUID = 3942596125728495029L;
	private int x,y;
	private int[][] arrLocation;
	private int[] location = new int[2];
	private int[] newLocation = new int[2];
	private boolean canMove;
	private String storedObject;
	private int[] storedLocation = new int[2];
	private boolean thereIsEnemy = false;
	
	private Random rand = new Random();
	private GameEntities[][] grid = new GameEntities[9][9];
	private Enemy[] enemy = new Enemy[5];
	private Player player = new Player();
	private BriefCase brief = new BriefCase();
	private Room[] room = new Room[9];
	private AdditionalBullet bullet = new AdditionalBullet();
	private Invincibility invincibility = new Invincibility();
	private Radar radar = new Radar();
	private Movement mv = new Movement();
	
	public Grid(){
		
	}
	public void setEntityToEmpty(int y, int x){
		grid[y][x] = null;
		GameEntities newEmpty = new Empty();
		grid[y][x] = newEmpty;
	}
	public GameEntities[][] getGrid(){
		return this.grid;
	}
	public GameEntities getRadar(){
		return this.radar;
	}
	public GameEntities getInvincibility(){
		return this.invincibility;
	}
	public GameEntities getBullet(){
		return this.bullet;
	}
	public GameEntities setPlayerLocation(){
  		grid[8][0] = player;
  		return player;
	}
	public GameEntities setPlayerLocation(int[] newLocation){
		x = newLocation[0];
		y = newLocation[1];
		grid[x][y] = player;
		return player;
	}
	public GameEntities getPlayer(){
		return this.player;
	}
	public GameEntities getBriefCase(){
		return this.brief;
	}
	public GameEntities setBriefCaseLocation(GameEntities[] room){
		location = getLocation(room[rand.nextInt(8)]);
		grid[location[0]][location[1]] = brief;
		return brief;
	}
	public GameEntities[] setEnemyLocation(){
		for(int i = 0; i < enemy.length; ++i){
			enemy[i] = new Enemy();
		}
		for (int i = 0; i < 5; i++){
			do{ 
				x = rand.nextInt(9);
				y = rand.nextInt(9);
			}while((grid[x][y] != null)||(x == 7 && y == 0)||(x == 8 && y == 0)||(x == 8 && y == 1));
			grid[x][y] = enemy[i];
		}
		return enemy;
	}
	public GameEntities setEnemyLocation(GameEntities enemy, int[] newLocation){
		x = newLocation[0];
		y = newLocation[1];
		grid[x][y] = enemy;
		return enemy;
	}
	public GameEntities[] getEnemyLocation(){
		return enemy;
	}
	public GameEntities[] setRoomLocation(){
		for(int i = 0; i < room.length; ++i){
			room[i] = new Room();
		}
		grid[7][7] = room[8];
		grid[7][4] = room[7];
		grid[7][1] = room[6];
		grid[4][7] = room[5];
		grid[4][4] = room[4];
		grid[4][1] = room[3];
		grid[1][7] = room[2];
		grid[1][4] = room[1];
		grid[1][1] = room[0];
		return room;
	}
	public void setBulletLocation(){
		do{
			x = rand.nextInt(9);
		    y = rand.nextInt(9);
		} while(grid[x][y] != null);
		grid[x][y] = bullet;
	}
	public void setBulletLocation(int[] location){
		x = location[0];
		y = location[1];
		grid[x][y] = bullet;
	}
	public void setInvincibilityLocation(){
		do{
			x = rand.nextInt(9);
		    y = rand.nextInt(9);
		} while(grid[x][y] != null);
			grid[x][y] = invincibility;
	}
	public void setInvincibilityLocation(int[] location){
		x = location[0];
		y = location[1];
		grid[x][y] = invincibility;
	}
	public void setRadarLocation(){
		int x;
		int y;
		do{
			x= rand.nextInt(9);
		    y = rand.nextInt(9);
		} while (grid[x][y] != null);
			grid[x][y] = radar;
	}
	public void setRadarLocation(int[] location){
		x = location[0];
		y = location[1];
		grid[x][y] = radar;
	}
	public void setEmptyLocation(){
		for (int i = 0; i < 9; i++){
			for (int j = 0; j < 9; j++){
				if (grid[i][j] == null){
					GameEntities empty = new Empty();
					grid[i][j] = empty;
				}
			}
		}
	}
	public void setEmptyLocation(int[] location){
		x = location[0];
		y = location[1];
		grid[x][y] = new Empty();
	}
	public GameEntities getEntity(int x, int y){
		return grid[x][y];
	}
	public int[] getLocation(GameEntities entity){
		for(int i = 0; i < grid.length; ++i){
			for(int j = 0; j < grid.length; ++j){
				if(grid[i][j] == entity){
					location[0] = i;
					location[1] = j;
				}
			}
		}
		return location;
	}
	public int[][] getArrLocation(GameEntities[] arrEntity){
		int[][] arrLocation = new int[arrEntity.length][2];
		for(int i = 0; i < arrEntity.length; ++i){
			location = new int[2];
			location = getLocation(arrEntity[i]);
			arrLocation[i][0] = location[0];
			arrLocation[i][1] = location[1];
		}
		return arrLocation;
	}
	public void movePlayer(char command){
		location = getLocation(getPlayer());
		newLocation = mv.moving(command, location);
		try{
			canMove();
			if(canMove){
				setPlayerLocation(newLocation);
				setEmptyLocation(location);
				interaction(storedObject);
			} else {
				setPlayerLocation(location);
			}
		}catch(ArrayIndexOutOfBoundsException e){
			System.err.println("Out of Bounds. Try again.");
			setPlayerLocation(location);
		}
	}
	
	public void interaction(String powerUpName){
		switch(powerUpName){
		case "invincibility":
			useInvincibility();
			break;
		case "radar":
			useRadar();
			break;
		case "bullet":
			useBullet();
			break;
		}
	}
	public void useRadar(){
		int [] locationOfBrief = getLocation(getBriefCase());
		int realRowLocation = locationOfBrief[0] + 1;
		int realColumnLocation = locationOfBrief[1] +1;
		System.out.println("You got the radar power-up.");
		System.out.println("The location of the brief-case is in row: " + realRowLocation + " - column: " + realColumnLocation + "");
	}
	
    public void useInvincibility(){
    	System.out.println("You got the invincibility power-up.");
    	player.setHealth(100);
    }
	
	public int useBullet(){
		System.out.println("You got the bullet power-up.");
		if(player.getAmmo() == 0){
			player.setAmmo(1);
		}
		return player.getAmmo();
	}
	
	public void moveEnemy(){
		arrLocation = getArrLocation(getEnemyLocation());
		int i = 0;
		try{
		while(i < arrLocation.length){
			int move = rand.nextInt(3);
			char command = 0;
			switch(move){
			case 0:
				command = 'w';
				break;
			case 1:
				command = 'a';
				break;
			case 2:
				command = 's';
				break;
			case 3:
				command = 'd';
				break;
			}
			location = arrLocation[i];
			newLocation = mv.moving(command, location);
			canMove();
			if(canMove){
				storedObject = enemy[i].getObjectName();
				try{
					checkEnemy(newLocation);
					if(thereIsEnemy){
						newLocation = location;
						thereIsEnemy = false;
						setEnemyLocation(enemy[i], newLocation);
					} else {
						setEnemyLocation(enemy[i], newLocation);
						setEmptyLocation(location);
					}
					switch(storedObject){
					case "invincibility":
						setInvincibilityLocation(enemy[i].getObjectLocation());
						break;
					case "radar":
						setRadarLocation(enemy[i].getObjectLocation());
						break;
					case "bullet":
						setBulletLocation(enemy[i].getObjectLocation());
						break;
					default: setEmptyLocation(enemy[i].getObjectLocation());
						break;
					}
				} catch (NullPointerException e){	
				}
				++i;
			}
		}
		}
		catch(ArrayIndexOutOfBoundsException e){
			moveEnemy();
		}
	}
	public boolean canMove(){
		x = newLocation[0];
		y = newLocation[1];
		if((x == 1 || x == 4 || x == 7) && (y == 1 || y == 4 || y == 7)){
			checkGameWin();
			canMove = false;
		} else {
			canMove = true;
		}
		if(grid[x][y] != null){
			checkLocation();
			storedLocation[0] = x;
			storedLocation[1] = y;
			storedObject = grid[x][y].getName();
		}
		return canMove;
	}
	public String checkLocation(){
		x = newLocation[0];
		y = newLocation[1];
		return storedObject;
	}
	public void checkEnemy(int[] possibleEnemyLocation){
		x = possibleEnemyLocation[0];
		y = possibleEnemyLocation[1];
		if(grid[x][y].getName().equals("enemy")){
			thereIsEnemy = true;
		}
	}
	public void checkGameWin(){
		if(getEntity(location[0], location[1]).getName().equals("player")){
			if(getEntity(newLocation[0], newLocation[1]).getName().equals("brief")){
				System.out.println("you win");
				System.exit(0);
			} else {
				System.out.println("no brief");
			}
		}
	}
	public int getPlayerHealth(){
		return player.getHealth();
	}
	
	public void decPlayerHealth(){
		player.decHealth();
	}
	
	public int getPlayerAmmo(){
		return player.getAmmo();
	}
}
