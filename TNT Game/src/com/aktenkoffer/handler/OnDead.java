package com.aktenkoffer.handler;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class OnDead implements Listener
{
	@EventHandler
	public void OnDeead(PlayerDeathEvent event) 
	{
		event.setDeathMessage("");
	}
}
