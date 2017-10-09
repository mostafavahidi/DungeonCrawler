package edu.cpp.cs.cs141.final_prog;
/**
 * @author Mostafa, Charlie, Saul, and Jae.
 *
 */
import java.util.Scanner;

public class MenuUserInterface {
	Scanner kb = new Scanner(System.in);
	
	  public void enterToContinue(){
	    	System.out.println("\nPress enter to continue");
	    	kb.nextLine();    
	  }
	  
	  public void invalidOption(){
		  System.out.println("Your input was invalid.");
		  System.out.println("Please enter a valid option.");
	  }
	
	  public int gameMenu(){
		  int option;
		  int answer = 1337;
		  int option2 = 7331;
		  
		  do{ 
			  System.out.println("\nWhat game mode do you want to play?");
			  System.out.println("[1]Normal Mode ");
			  System.out.println("[2]Debug Mode");
			  option2 = kb.nextInt();
			  if(option2 == 1){
				  
			  }
			  else if (option2 == 2){
				  
			  }
			  else{
					invalidOption();
					enterToContinue();
			  }
		  }
		  while(option2 < 1 || option2 > 2);
		 
		  do{
			  System.out.println("\nWould you like to:");
			  System.out.println("[1]Start a New Game");
			  System.out.println("[2]Load Your Current Game Save");
			  option = kb.nextInt();
			  if (option < 1 || option > 2){
				  invalidOption();
			  }
		  }
		  while (option < 1 || option > 2);
    	
		  switch(option){
    			case 1://Start Game
    				if(option2 == 1){ //Start Game in Normal Mode
    					answer = 0;
    				}
    				else if(option2 == 2){ //Start Game in Debug Mode
    					answer = 1;
    				}
    				break;
    			
    			case 2://Load Game
    				if(option2 == 1){ //Load Game in Normal Mode
    					answer = 2;	
    				}
    				else if(option2 == 2){ //Load game in Debug Mode
    					answer = 3;
    				}
		  }
    	
		  return answer;
	  }
	
	  public int gameManual(){
		  System.out.println("Welcome to Weaboo Graveyard, Sensei!");
		  int option = 1337;
		  int option2 = 1337;
		  int answer = 1337;
		  do{
			  do{ System.out.println("\nWhat would you like to view/do?");
    				System.out.println("[1]Game Objectives");
    				System.out.println("[2]Map Legend");
    				System.out.println("[3]Game Lore");
    				System.out.println("[4]Credits");
    				System.out.println("[5]Play Game");
    				option = kb.nextInt();
    				if(option < 1 || option > 5){
    					invalidOption();
    				}
			  } 
			  while(option < 1 || option > 5);	
		
			  switch(option){
					case 1:
						gameObjectives();
						break;
					case 2:
						mapLegend();
						break;
					case 3:
						gameLore();
						break;
					case 4:
						credits();
						break;
					case 5:
						answer = (gameMenu());
			  }
			  if(option == 5){
				  break;
			  }
			  if(option != 5){
				  System.out.println("\nWould you like to view something else?");
				  System.out.println("[1]Yes");
				  System.out.println("[2]No");
				  option2 = kb.nextInt();
			  }	
		  	}
		  	while(option2 == 1);
		  	if(option2 == 2){
		  		answer = (gameMenu());
		  	}
		  	return answer;
	  }
	  
	  public void gameObjectives(){
		  System.out.println("\nThe game takes place inside a labrynth with 81 grids.");
		  System.out.println("You, the sensei, have the duty of retrieving the missing documents in a briefcase.");
		  System.out.println("But not only is it that simple, you must also get the briefcase amongst a horde of undead weaboo's");
		  System.out.println("You move one time per turn which is followed by the weaboo's making their own turn.");
		  System.out.println("There are also beneficial power-ups to help you on your journey.");
		  System.out.println("You are to retrive the briefcase without losing all your lives");
	  }
	  
	  public void mapLegend(){
		  System.out.println("\n[R] is a Room");
		  System.out.println("[P] is the Player");
		  System.out.println("[E] is an Enemy");
		  System.out.println("[I] is an Invincibility power-up");
		  System.out.println("[*] is the Radar power-up");
		  System.out.println("[B] is the Bullet power-up");
		  System.out.println("[R C] is the room with the BriefCase");
	  }
	  
	  public void gameLore(){
		  System.out.println("\nYou enter a dungeon. You don't know why you did.");
		  kb.nextLine();
		  enterToContinue();
		  System.out.println("You hear something in the distance. What could it be?");
		  enterToContinue();
		  System.out.println("You take a couple steps forward and feel something on the floor. What is it?");
		  enterToContinue();
		  System.out.println("You back away. Suddenly everything around you gets enclosed and you are at a corner.");
		  enterToContinue();
		  System.out.println("You pull out your gun and decide to look around.");
		  enterToContinue();
		  System.out.println("You decide you're going to check out the enclosed area. You're capable of anything. Besides, you are the sensei.");
		  enterToContinue();
		  System.out.println("LORE FINISHED");
	  }
	  
	  public void credits(){
		  System.out.println("\nSaul Galaviz as production manager");
		  System.out.println("Charlie When as senior logic designer");
		  System.out.println("Mostafa Vahidi as senior code-developer");
		  System.out.println("and ft. Jae Yi as the team manager"); 
	  }
	  
	  public void finish(){
		  System.out.println("\nThanks for playing Sensei!");
	  }
	  
}
