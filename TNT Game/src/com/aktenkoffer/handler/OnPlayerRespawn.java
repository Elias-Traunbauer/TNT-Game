package com.aktenkoffer.handler;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

import com.aktenkoffer.main.TntMain;

public class OnPlayerRespawn implements Listener
{
	@EventHandler
	public void Respawn(PlayerRespawnEvent event) 
	{
		TntMain.RespawnPlayer(event.getPlayer());
	}
}
