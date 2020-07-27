package com.aktenkoffer.handler;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class FoodEvenbt implements Listener
{
	@EventHandler
	public void Hunger(FoodLevelChangeEvent event) 
	{
		event.setCancelled(true);
	}
}
