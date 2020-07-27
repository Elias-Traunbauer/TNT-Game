package com.aktenkoffer.handler;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class OnPickup implements Listener
{
	@EventHandler
	public void OnPickUp(PlayerPickupItemEvent event) 
	{
		event.setCancelled(true);
	}
}
