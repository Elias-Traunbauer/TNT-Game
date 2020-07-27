package com.aktenkoffer.handler;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreakHandler implements Listener
{
	@EventHandler
	public void OnBreakBlock(BlockBreakEvent event) 
	{
		event.setCancelled(true);
	}
}
