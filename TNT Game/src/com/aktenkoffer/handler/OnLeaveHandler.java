package com.aktenkoffer.handler;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import com.aktenkoffer.main.TntMain;

public class OnLeaveHandler implements Listener
{
	@EventHandler
	public void HandleJoinedPlayer(PlayerQuitEvent event) 
	{
		TntMain.PlayerLeave(event.getPlayer().getName()); 
		
		event.setQuitMessage("");
	}
}
