package com.aktenkoffer.handler;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.aktenkoffer.main.TntMain;

public class OnJoinHandler implements Listener
{
	@EventHandler
	public void HandleJoinedPlayer(PlayerJoinEvent event) 
	{
		event.setJoinMessage("§d" + event.getPlayer().getName() + " ist da!");
		
		TntMain.PlayerJoin(event.getPlayer());
	}
}
