package com.aktenkoffer.handler;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

public class DamageEvent implements Listener
{
	 @EventHandler
	 public void HandleDamage(EntityDamageEvent event)
	 {
		 if (!event.getCause().equals(DamageCause.BLOCK_EXPLOSION)) 
		 {
			 event.setCancelled(true);
		 }
	 }
}
