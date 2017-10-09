package edu.cpp.cs.cs141.final_prog;
/**
 * @author Mostafa, Charlie, Saul, and Jae.
 *
 */
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

public class GameEngine {
	private MenuUserInterface menuInt = new MenuUserInterface();
	private UserInterface userInt;
	private GameEntities[] enemyList;
	private int[] enemyLocation = new int[2];
	private int[] playerLocation = new int[2];
	private int[] radarLocation = new int[2];
	private int[] invincibilityLocation = new int[2];
	private int[] bulletLocation = new int[2];
	private Scanner kb = new Scanner(System.in);
	private String answer;
	private boolean load;
	
	public GameEngine(){
		int gameMode = (menuInt.gameManual()); // 0 = NewGame in NormalM - 1 = NewGame in DebugM - 2 = LoadGame in NormalM - 3 = LoadGame in DebugM 
		
		userInt = new UserInterface();
		userInt.getGrid().setPlayerLocation();
		userInt.getGrid().setRoomLocation();
		userInt.getGrid().setEnemyLocation();
		userInt.getGrid().setBulletLocation();
		userInt.getGrid().setRadarLocation();
		userInt.getGrid().setInvincibilityLocation();
		userInt.getGrid().setEmptyLocation();
		userInt.getGrid().setBriefCaseLocation(userInt.getGrid().setRoomLocation());
		
		switch(gameMode){
			case 0://NewGame in NormalMode
				userInt.printNormalGrid(userInt.getGrid().getGrid());
				play("normal", false);
				break;
			case 1://NewGame in DebugMode
				userInt.printGrid(userInt.getGrid().getGrid());
				play("debug", false);
				break;
				
			case 2://LoadGame in NormalMode
				play("normal", true);
				break;
			case 3://LoadGame in DebugMode
				play("debug", true);
		}	
		menuInt.finish();
	}
	
	public void play(String gameMode, boolean load){
		int livesOfPlayer = userInt.getGrid().getPlayerHealth();
		this.load = load;
		while (livesOfPlayer > 0){
			String saveAnswer = (afterEachTurn(gameMode, this.load));
			livesOfPlayer = userInt.getGrid().getPlayerHealth();
			this.load = false;
			if(saveAnswer.equals("yes")){
				break;
			}
		}
	}
	
	
	public String afterEachTurn(String gameMode, boolean load){
		if(load == true){
			userInt.loadGrid(readSave());
			if(gameMode.equals("normal"))
				userInt.printNormalGrid(userInt.getGrid().getGrid());
		
			else if(gameMode.equals("debug"))
				userInt.printGrid(userInt.getGrid().getGrid());
		}
		String saveAnswer = (saveQuestion());
		
		if (saveAnswer.equals("no")){
			userInt.moveEntities();
			isPlayerAroundEnemyVerTwo();
		
			if(gameMode.equals("normal"))
				userInt.printNormalGrid(userInt.getGrid().getGrid());
		
			else if(gameMode.equals("debug"))
				userInt.printGrid(userInt.getGrid().getGrid());
		
			userInt.playerUpdating();
			powerUpUse();
			userInt.lookPlayer();
		}	
		return saveAnswer;
	}
	public String saveQuestion(){
		String saveAnswer = (userInt.promptForSave());
		if(saveAnswer.equals("yes")){
			writeSave(userInt.getGrid());
			userInt.saveGameMessage();		
		}
		return saveAnswer;
	}
	public void powerUpUse(){
		/*playerLocation = userInt.getGrid().getLocation(userInt.getGrid().getPlayer());
		radarLocation = userInt.getGrid().getLocation(userInt.getGrid().getRadar());
		invincibilityLocation = userInt.getGrid().getLocation(userInt.getGrid().getInvincibility());
		bulletLocation = userInt.getGrid().getLocation(userInt.getGrid().getBullet());
		
		int yp = playerLocation[0];
		int xp = playerLocation[1];
		
		int yr = radarLocation[0];
		int xr = radarLocation[1];
		
		int yi = invincibilityLocation[0];
		int xi = invincibilityLocation[1];
		
		int yb = bulletLocation[0];
		int xb = bulletLocation[1];*/
		
		//String whichToApply;
		int i = 0;
		boolean foundPowerUp = false;
		
		while (i < 9 && foundPowerUp == false){
			for (int j = 0; j < 9; j++){
				if (userInt.getGrid().getLocation(userInt.getGrid().getPlayer()) == userInt.getGrid().getLocation(userInt.getGrid().getRadar())) {
					userInt.getGrid().interaction("radar");
					foundPowerUp = true;
					break;
				} else {
					if (userInt.getGrid().getLocation(userInt.getGrid().getPlayer()) == userInt.getGrid().getLocation(userInt.getGrid().getInvincibility())){
						userInt.getGrid().interaction("invincibility");
						foundPowerUp = true;
						break;
					} else {
						if (userInt.getGrid().getLocation(userInt.getGrid().getPlayer()) == userInt.getGrid().getLocation(userInt.getGrid().getBullet())){
							userInt.getGrid().interaction("bullet");
							foundPowerUp = true;
							break;
						}
					}
				}
			}
			i++;
		}
		/*switch(whichToApply){
		
			case "radar":
				
				break;
			case "invincibility":
				
				break;
			case "bullet":
				
				break;
			case "nothing":
				break;
		}*/
		
	}

	public void isPlayerAroundEnemyVerTwo() throws IndexOutOfBoundsException{
		try{
			for (int i = 0; i < 9; i++){
				for (int j = 0; j < 9; j++){
					switch (i){
					
					case 0:
						switch (j){
							case 0:
								if ((userInt.getGrid().getEntity(i, j).getName() == "enemy" && userInt.getGrid().getEntity(i + 1, j).getName() == "player") || (userInt.getGrid().getEntity(i, j).getName() == "enemy" && userInt.getGrid().getEntity(i , j + 1).getName() == "player")){
									userInt.getGrid().setPlayerLocation();
									for(int y = 0; y < 9; y++){
										for (int x = 0; x < 9; x++){
											if (userInt.getGrid().getEntity(y, x).getName() == "player" && (y != 8 && x != 0)){
												userInt.getGrid().setEntityToEmpty(y,x);
											}
										}
									}
									userInt.getGrid().decPlayerHealth();
									System.out.println("You were hurt by a weaboo.");
								}
								break;
							case 1:
							case 2:
							case 3:
							case 4:
							case 5:
							case 6:
							case 7:
								
								if ((userInt.getGrid().getEntity(i, j).getName() == "enemy" && userInt.getGrid().getEntity(i , j + 1).getName() == "player") || (userInt.getGrid().getEntity(i, j).getName() == "enemy" && userInt.getGrid().getEntity(i , j - 1).getName() == "player") || (userInt.getGrid().getEntity(i, j).getName() == "enemy" && userInt.getGrid().getEntity(i + 1, j ).getName() == "player")){
									userInt.getGrid().setPlayerLocation();
									for(int y = 0; y < 9; y++){
										for (int x = 0; x < 9; x++){
											if (userInt.getGrid().getEntity(y, x).getName() == "player" && (y != 8 && x != 0)){
												userInt.getGrid().setEntityToEmpty(y,x);
											}
										}
									}
									userInt.getGrid().decPlayerHealth();
									System.out.println("You were hurt by a weaboo.");
								}
								break;
							case 8:
								if ((userInt.getGrid().getEntity(i, j).getName() == "enemy" && userInt.getGrid().getEntity(i +1 , j).getName() == "player") || (userInt.getGrid().getEntity(i, j).getName() == "enemy" && userInt.getGrid().getEntity(i , j - 1).getName() == "player")){
									userInt.getGrid().setPlayerLocation();
									for(int y = 0; y < 9; y++){
										for (int x = 0; x < 9; x++){
											if (userInt.getGrid().getEntity(y, x).getName() == "player" && (y != 8 && x != 0)){
												userInt.getGrid().setEntityToEmpty(y,x);
											}
										}
									}
									userInt.getGrid().decPlayerHealth();
									System.out.println("You were hurt by a weaboo.");
								}
								break;
							default:
								if ((userInt.getGrid().getEntity(i, j).getName() == "enemy" && userInt.getGrid().getEntity(i - 1, j).getName() == "player") || (userInt.getGrid().getEntity(i, j).getName() == "enemy" && userInt.getGrid().getEntity(i + 1, j ).getName() == "player") || (userInt.getGrid().getEntity(i, j).getName() == "enemy" && userInt.getGrid().getEntity(i , j + 1).getName() == "player") || (userInt.getGrid().getEntity(i, j).getName() == "enemy" && userInt.getGrid().getEntity(i , j - 1 ).getName() == "player")){
									userInt.getGrid().setPlayerLocation();
									for(int y = 0; y < 9; y++){
										for (int x = 0; x < 9; x++){
											if (userInt.getGrid().getEntity(y, x).getName() == "player" && (y != 8 && x != 0)){
												userInt.getGrid().setEntityToEmpty(y,x);
											}
										}
									}
									userInt.getGrid().decPlayerHealth();
									System.out.println("You were hurt by a weaboo.");
								}
						}
						break;
					case 1:
					case 2:
					case 3:
					case 4:
					case 5:
					case 6:
					case 7:
						switch(j){
							case 0:
								if ((userInt.getGrid().getEntity(i, j).getName() == "enemy" && userInt.getGrid().getEntity(i - 1, j).getName() == "player") || (userInt.getGrid().getEntity(i, j).getName() == "enemy" && userInt.getGrid().getEntity(i + 1, j).getName() == "player") || (userInt.getGrid().getEntity(i, j).getName() == "enemy" && userInt.getGrid().getEntity(i , j + 1).getName() == "player")){
									userInt.getGrid().setPlayerLocation();
									for(int y = 0; y < 9; y++){
										for (int x = 0; x < 9; x++){
											if (userInt.getGrid().getEntity(y, x).getName() == "player" && (y != 8 && x != 0) ){
												userInt.getGrid().setEntityToEmpty(y,x);
											}
										}
									}
									userInt.getGrid().decPlayerHealth();
									System.out.println("You were hurt by a weaboo.");
								}
								break;
							case 8:
								if ((userInt.getGrid().getEntity(i, j).getName() == "enemy" && userInt.getGrid().getEntity(i - 1, j).getName() == "player") || (userInt.getGrid().getEntity(i, j).getName() == "enemy" && userInt.getGrid().getEntity(i + 1, j).getName() == "player") || (userInt.getGrid().getEntity(i, j).getName() == "enemy" && userInt.getGrid().getEntity(i , j - 1).getName() == "player")){
									userInt.getGrid().setPlayerLocation();
									for(int y = 0; y < 9; y++){
										for (int x = 0; x < 9; x++){
											if (userInt.getGrid().getEntity(y, x).getName() == "player" && (y != 8 && x != 0)){
												userInt.getGrid().setEntityToEmpty(y,x);
											}
										}
									}
									userInt.getGrid().decPlayerHealth();
									System.out.println("You were hurt by a weaboo.");
								}
								break;
							default:
								if ((userInt.getGrid().getEntity(i, j).getName() == "enemy" && userInt.getGrid().getEntity(i - 1, j).getName() == "player") || (userInt.getGrid().getEntity(i, j).getName() == "enemy" && userInt.getGrid().getEntity(i + 1, j ).getName() == "player") || (userInt.getGrid().getEntity(i, j).getName() == "enemy" && userInt.getGrid().getEntity(i , j + 1).getName() == "player") || (userInt.getGrid().getEntity(i, j).getName() == "enemy" && userInt.getGrid().getEntity(i , j - 1 ).getName() == "player")){
									userInt.getGrid().setPlayerLocation();
									for(int y = 0; y < 9; y++){
										for (int x = 0; x < 9; x++){
											if (userInt.getGrid().getEntity(y, x).getName() == "player" && (y != 8 && x != 0)){
												userInt.getGrid().setEntityToEmpty(y,x);
											}
										}
									}
									userInt.getGrid().decPlayerHealth();
									System.out.println("You were hurt by a weaboo.");
								}
						}
						break;
					case 8:
						switch (j){
							case 0:
								if ((userInt.getGrid().getEntity(i, j).getName() == "enemy" && userInt.getGrid().getEntity(i - 1, j).getName() == "player") || (userInt.getGrid().getEntity(i, j).getName() == "enemy" && userInt.getGrid().getEntity(i , j + 1).getName() == "player")){
									userInt.getGrid().setPlayerLocation();
									for(int y = 0; y < 9; y++){
										for (int x = 0; x < 9; x++){
											if (userInt.getGrid().getEntity(y, x).getName() == "player" && (y != 8 && x != 0)){
												userInt.getGrid().setEntityToEmpty(y,x);
											}
										}
									}
									userInt.getGrid().decPlayerHealth();
									System.out.println("You were hurt by a weaboo.");
									
								}
								break;
							case 1:
							case 2:
							case 3:
							case 4:
							case 5:
							case 6:
							case 7:
								
								if ((userInt.getGrid().getEntity(i, j).getName() == "enemy" && userInt.getGrid().getEntity(i , j + 1).getName() == "player") || (userInt.getGrid().getEntity(i, j).getName() == "enemy" && userInt.getGrid().getEntity(i , j - 1).getName() == "player") || (userInt.getGrid().getEntity(i, j).getName() == "enemy" && userInt.getGrid().getEntity(i - 1, j ).getName() == "player")){
									userInt.getGrid().setPlayerLocation();
									for(int y = 0; y < 9; y++){
										for (int x = 0; x < 9; x++){
											if (userInt.getGrid().getEntity(y, x).getName() == "player" && (y != 8 && x != 0)){
												userInt.getGrid().setEntityToEmpty(y,x);
											}
										}
									}
									userInt.getGrid().decPlayerHealth();
									System.out.println("You were hurt by a weaboo.");
									break;
								}
							case 8:
								if ((userInt.getGrid().getEntity(i, j).getName() == "enemy" && userInt.getGrid().getEntity(i - 1, j).getName() == "player") || (userInt.getGrid().getEntity(i, j).getName() == "enemy" && userInt.getGrid().getEntity(i , j - 1).getName() == "player")){
									userInt.getGrid().setPlayerLocation();
									for(int y = 0; y < 9; y++){
										for (int x = 0; x < 9; x++){
											if (userInt.getGrid().getEntity(y, x).getName() == "player" && (y != 8 && x != 0)){
												userInt.getGrid().setEntityToEmpty(y,x);
											}
										}
									}
									userInt.getGrid().decPlayerHealth();
									System.out.println("You were hurt by a weaboo.");
								}
						}
						break;
					default:
						if ((userInt.getGrid().getEntity(i, j).getName() == "enemy" && userInt.getGrid().getEntity(i - 1, j).getName() == "player") || (userInt.getGrid().getEntity(i, j).getName() == "enemy" && userInt.getGrid().getEntity(i + 1, j ).getName() == "player") || (userInt.getGrid().getEntity(i, j).getName() == "enemy" && userInt.getGrid().getEntity(i , j + 1).getName() == "player") || (userInt.getGrid().getEntity(i, j).getName() == "enemy" && userInt.getGrid().getEntity(i , j - 1 ).getName() == "player")){
							userInt.getGrid().setPlayerLocation();
							for(int y = 0; y < 9; y++){
								for (int x = 0; x < 9; x++){
									if (userInt.getGrid().getEntity(y, x).getName() == "player" && (y != 8 && x != 0)){
										userInt.getGrid().setEntityToEmpty(y,x);
									}
								}
							}
							userInt.getGrid().decPlayerHealth();
							System.out.println("You were hurt by a weaboo.");
						}
					}
				}
			}
		} catch (IndexOutOfBoundsException e){
			e.printStackTrace();
		}
		
	}	
//	public void isPlayerAroundEnemy(){
//		enemyList = userInt.getGrid().getEnemyLocation();
//		playerLocation = userInt.getGrid().getLocation(userInt.getGrid().getPlayer());
//		for (int i = 0; i < enemyList.length; i++){
//			enemyLocation = userInt.getGrid().getLocation(enemyList[i]);
//			int ye = enemyLocation[0];
//			int xe = enemyLocation[1];
//			int yp = playerLocation[0];
//			int xp = playerLocation[1];
//			if ((xe == xp && ((ye + 1 == yp) || (ye - 1 == yp))) || (ye == yp && ((xe + 1 == xp) || (xe - 1 == xp)))){
//				userInt.getGrid().setPlayerLocation();
//				userInt.getGrid().decPlayerHealth();
//				System.out.println("You were hurt by a weaboo.");
//				break;
//			} 
//		}
	
	public void writeSave(Grid grid) {
		try {
			FileOutputStream fos = new FileOutputStream("gameSave.dat");
			ObjectOutputStream oos = new ObjectOutputStream(fos);

			oos.writeObject(grid);

			oos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	public static Grid readSave() {
		Grid grid = null;

		try {
			FileInputStream fis = new FileInputStream("gameSave.dat");
			ObjectInputStream ois = new ObjectInputStream(fis);

			grid = (Grid) ois.readObject();

			ois.close();
		} catch (IOException | ClassNotFoundException e) {
			System.out.println("No previous game load files exist.");
		}

		return grid;
	}
	
}
