package com.aktenkoffer.handler;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import com.aktenkoffer.main.TntMain;
import com.aktenkoffer.playerdata.PlayerData;


public class PlaceEvent  implements Listener
{
	public static int randomZahl(int minimal, int maximal)
	{
       Random random = new Random(); 
       return random.nextInt(maximal - minimal + 1) + minimal;  
	}
	
	@EventHandler
	public void HandleBlockPlaced(BlockPlaceEvent event) 
	{
		Player executor = event.getPlayer();
		
		event.getBlockPlaced().setType(Material.AIR);
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				try {
					Thread.sleep(250);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				for (PlayerData yee : TntMain.players) 
				{
					if (yee.name.equals(event.getPlayer().getName()))
					{
						if (!TntMain.over && TntMain.IsAlive(yee.name))
							executor.getInventory().setItem(0, new ItemStack(Material.TNT, 1));
					}
				}
				
			}
		}).start();
		
		event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.EXPLODE, 3.0f, 1.0f);
		
		event.getPlayer().getWorld().playEffect(event.getBlockPlaced().getLocation(), Effect.EXPLOSION_HUGE, 5);
		
		for(Player damagedPlayer : Bukkit.getOnlinePlayers()) 
		{
			if (damagedPlayer.getName().equals(event.getPlayer().getName()) || damagedPlayer.getGameMode() == GameMode.ADVENTURE) continue;
			
		    double damage = 100d - (10 * event.getBlockPlaced().getLocation().distance(damagedPlayer.getLocation()));
		    
		    if(damage > 0) 
		    {
		    	damagedPlayer.playSound(damagedPlayer.getLocation(), Sound.EXPLODE, 3.0f, 1.0f);
		    	
		        if(damagedPlayer.getHealth() <= damage) 
		        {
		        	damagedPlayer.teleport(new Location(damagedPlayer.getWorld(), randomZahl(-70, 70), 100, randomZahl(-70, 70)));
		        	
		            damagedPlayer.setHealth(500d);
		            
		            event.getPlayer().setHealth(500);
		            
		            TntMain.PlayerDied(damagedPlayer);
		            
		            for (Player p : Bukkit.getOnlinePlayers())
		            {
		            	p.sendMessage(TntMain.prefix + "§4" + damagedPlayer.getName() + " §cwurde von §a" + event.getPlayer().getName() + " §cgetötet§f!");
		            }
		        }
		        else
		            damagedPlayer.setHealth(damagedPlayer.getHealth() - damage);
		    }
		}
	}
}
