package com.aktenkoffer.handler;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class OnDrop implements Listener
{
	@EventHandler
	public void OnDropEvent(PlayerDropItemEvent event) 
	{
		event.setCancelled(true);
	}
}
