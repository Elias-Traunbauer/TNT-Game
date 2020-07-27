package com.aktenkoffer.handler;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import com.aktenkoffer.main.TntMain;
import com.aktenkoffer.playerdata.PlayerData;

public class InvClick implements Listener
{
	@EventHandler
    public void onClick(InventoryClickEvent event)
	{
        Player player = (Player) event.getWhoClicked();

        if(event.getInventory().getName().equals("§aTeleporter")) 
        {
            event.setCancelled(true);
            
			for (PlayerData p : TntMain.players)
			{
				if (event.getCurrentItem().getItemMeta().getDisplayName().equals("§f" + p.name)) 
				{
					player.teleport(Bukkit.getPlayer(p.name));
					
					player.closeInventory();
					
					break;
				}
			}
        }
    }
}
