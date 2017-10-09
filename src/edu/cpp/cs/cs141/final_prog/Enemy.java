package edu.cpp.cs.cs141.final_prog;
/**
 * @author Mostafa, Charlie, Saul, and Jae.
 *
 */
public class Enemy extends GameEntities{
	private static final long serialVersionUID = -5451092991767400024L;
	private String storedObject;
	private int[] storedLocation = new int[2];
	private boolean alive = true;
	
	public Enemy(){
		super.setName("enemy");
	}
	
	public String storeObjectName(String objectName){
		storedObject = objectName;
		return storedObject;
	}
	
	public int[] storeObjectLocation(int[] location){
		storedLocation = location;
		return storedLocation;
	}
	
	public String getObjectName(){
		return storedObject;
	}
	
	public int[] getObjectLocation(){
		return storedLocation;
	}
	
	public boolean shot(){
		alive = false;
		return alive;
	}
}
