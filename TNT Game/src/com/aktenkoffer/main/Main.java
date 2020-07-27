package com.aktenkoffer.main;

import java.sql.SQLException;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import com.aktenkoffer.commands.Start;
import com.aktenkoffer.handler.BlockBreakHandler;
import com.aktenkoffer.handler.DamageEvent;
import com.aktenkoffer.handler.FoodEvenbt;
import com.aktenkoffer.handler.InvClick;
import com.aktenkoffer.handler.MessageHandler;
import com.aktenkoffer.handler.OnDead;
import com.aktenkoffer.handler.OnDrop;
import com.aktenkoffer.handler.OnItemUse;
import com.aktenkoffer.handler.OnJoinHandler;
import com.aktenkoffer.handler.OnLeaveHandler;
import com.aktenkoffer.handler.OnPickup;
import com.aktenkoffer.handler.OnPlayerRespawn;
import com.aktenkoffer.handler.PlaceEvent;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

public class Main extends JavaPlugin 
{
	StatsAPI api;
	
	int tickTime = 0;
	
	private static Main main;
	
	public static int randomZahl(int minimal, int maximal)
	{
       Random random = new Random(); 
       return random.nextInt(maximal - minimal + 1) + minimal;  
	}
	
	public static Main getInstance() 
	{
		return main;
	}
	
	public void sendToaRandomServerInGroup(Plugin plugin, Player player, String group)
    {
        plugin.getServer().getMessenger().registerOutgoingPluginChannel(plugin, "cloudnet:main");
        ByteArrayDataOutput byteArrayDataOutput = ByteStreams.newDataOutput();
        byteArrayDataOutput.writeUTF("Connect");
        byteArrayDataOutput.writeUTF(group);
        player.sendPluginMessage(plugin, "cloudnet:main", byteArrayDataOutput.toByteArray());
    }

    public void sendToFallback(Plugin plugin, Player player)
    {
        plugin.getServer().getMessenger().registerOutgoingPluginChannel(plugin, "cloudnet:main");
        ByteArrayDataOutput byteArrayDataOutput = ByteStreams.newDataOutput();
        byteArrayDataOutput.writeUTF("Fallback"); //Connect to the fallback server in the iteration
        player.sendPluginMessage(plugin, "cloudnet:main", byteArrayDataOutput.toByteArray());
    }
	
	@Override
	public void onEnable()
	{
		main = this;
		
		//StatsAPI.Initialize();
		
		//StatsAPI.setupStatsAPI("TNT", "localhost", 3306, "GameStats", "TNTStats", "access");
		
		//try 
		//{
			//StatsAPI.connectToSQLManagerManager();
		//} 
		//catch (ClassNotFoundException | SQLException e) 
		//{
		//	e.printStackTrace();
		//}
		
		Bukkit.getServer().getMessenger().registerOutgoingPluginChannel(getInstance(), "BungeeCord");
		
		TntMain.Initialize();
		
		new BukkitRunnable()
		{
		    @Override
		    public void run()
		    {
		    	OnSecond();
		    	
		    	for (Player player : Bukkit.getOnlinePlayers())
		    	{
					if (player.getLocation().getY() < 0) 
					{
						player.teleport(new Location(player.getWorld(), randomZahl(-70, 70), 100, randomZahl(-70, 70)));
						
			            player.setHealth(500);
			            
			            TntMain.PlayerDied(player);
			            
			            for (Player p : Bukkit.getOnlinePlayers())
			            {
			            	p.sendMessage(TntMain.prefix + "§4" + player.getName() + " §cist ins Leere gesprungen!");
			            }
					}
				}
		    }
		    
		}.runTaskTimer(this, 0L, 20L);
		
		getCommand("start").setExecutor(new Start());
		
		getServer().getPluginManager().registerEvents(new MessageHandler(), this);
		
		getServer().getPluginManager().registerEvents(new DamageEvent(), this);
		
		getServer().getPluginManager().registerEvents(new OnJoinHandler(), this);
		
		getServer().getPluginManager().registerEvents(new OnLeaveHandler(), this);
		
		getServer().getPluginManager().registerEvents(new PlaceEvent(), this);
		
		getServer().getPluginManager().registerEvents(new BlockBreakHandler(), this);
		
		getServer().getPluginManager().registerEvents(new OnPlayerRespawn(), this);
		
		getServer().getPluginManager().registerEvents(new OnPickup(), this);
		
		getServer().getPluginManager().registerEvents(new OnDrop(), this);
		
		getServer().getPluginManager().registerEvents(new FoodEvenbt(), this);
		
		getServer().getPluginManager().registerEvents(new OnDead(), this);
		
		getServer().getPluginManager().registerEvents(new OnItemUse(), this);
		
		getServer().getPluginManager().registerEvents(new InvClick(), this);
	}
	
	private void OnSecond()
	{
		TntMain.OnSecond();
	}
	
	@Override
	public void onDisable() 
	{
		// TODO Auto-generated method stub
		super.onDisable();
	}

	@Override
	public void onLoad() 
	{
		// TODO Auto-generated method stub
		super.onLoad();
	}
}
