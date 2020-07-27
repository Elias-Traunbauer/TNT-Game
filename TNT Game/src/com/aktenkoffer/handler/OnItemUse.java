package com.aktenkoffer.handler;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.aktenkoffer.main.Main;
import com.aktenkoffer.main.TntMain;
import com.aktenkoffer.playerdata.PlayerData;

public class OnItemUse implements Listener
{
	@EventHandler
	public void Use (PlayerInteractEvent event)
	{
		try 
		{
			if (event.getItem().getItemMeta().getDisplayName().equals("§cTNT §eZauberstab")) 
			{
				Location loc = event.getPlayer().getLocation().add(0, 1, 0);
				
				event.getPlayer().getInventory().setItem(1, null);
				
				TntMain.SpoonfeedingFürAktenkoffer_WarumMussIchNeBewerbungsFunktionMachen_Alter_DerGanzeFractureCoreIsVonMir_MachWenigstensCreditsFürDenCoreHin_DerCoreIsNedInApache2_0(loc, event.getPlayer().getLocation().getYaw(), event.getPlayer().getLocation().getPitch(), event.getPlayer().getName(), event.getPlayer());
				
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						ItemStack itemm = new ItemStack(Material.BLAZE_ROD);
						
						ItemMeta metaa = itemm.getItemMeta();
						
						metaa.setDisplayName("§cTNT §eZauberstab");
						
						itemm.setItemMeta(metaa);

						for (PlayerData iterable_element : TntMain.players) 
						{
							if (iterable_element.name.equals(event.getPlayer().getName()))
							if (!TntMain.over && TntMain.IsAlive(iterable_element.name))
								event.getPlayer().getInventory().setItem(1, itemm);
						}
						
					}
				}).start();
			}
			else
			if (event.getItem().getItemMeta().getDisplayName().equals("§aZur Lobby"))
			{
				Main.getInstance().sendToaRandomServerInGroup(Main.getInstance(), event.getPlayer(), "Lobby");
			}
			else
			if (event.getItem().getItemMeta().getDisplayName().equals("§cNochmal Spielen"))
			{
				Main.getInstance().sendToaRandomServerInGroup(Main.getInstance(), event.getPlayer(), "TNT");
			}
			else
			if (event.getItem().getItemMeta().getDisplayName().equals("§aTeleporter")) 
			{
				event.getPlayer().openInventory(TntMain.createTeleporter());
			}
		}
		catch (Exception ex)
		{
			
		}
	}
}
