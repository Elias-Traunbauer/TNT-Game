package com.aktenkoffer.playerdata;

public class PlayerData 
{
	public String name;
	
	public int lives;
	
	public boolean dead;
	
	public PlayerData(String name)
	{
		lives = 3;
		
		this.name = name;
	}
}
