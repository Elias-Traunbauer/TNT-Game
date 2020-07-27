package com.aktenkoffer.main;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import com.aktenkoffer.playerdata.PlayerData;

import de.dytanic.cloudnet.bridge.CloudServer;
import de.dytanic.cloudnet.lib.server.ServerState;

public class TntMain 
{
	public static String prefix = "§8[§4§lTNT§8] ";
	
	public static ArrayList<PlayerData> players;
	
	public static boolean waiting;
	
	public static boolean over;
	
	public static boolean enoughPlayers;
	
	public static boolean playing;
	
	public static int time;
	
	public static int randomZahl(int minimal, int maximal)
	{
       Random random = new Random(); 
       return random.nextInt(maximal - minimal + 1) + minimal;  
	}
	
	public static void Initialize() 
	{
		CloudServer.getInstance().setServerState(ServerState.LOBBY);
		
		players = new ArrayList<PlayerData>();
		
		waiting = false;
		
		playing = false;
		
		enoughPlayers = false;
		
		over = false;
		
		time = -1;
		
		Bukkit.getWorld("world").setGameRuleValue("keepInventory", "true");
		
		Bukkit.getWorld("world").setGameRuleValue("doDaylightCycle", "false");
		
		Bukkit.getWorld("world").setDifficulty(Difficulty.EASY);
	}
	
	public static void RespawnPlayer(Player player)
	{
		if (playing) 
		{
			for (PlayerData playerData : players) 
			{
				if (player.getName().equals(playerData.name))
				{
					if (playerData.dead)
					{
						player.setGameMode(GameMode.SPECTATOR);
					}
					else
					{
						Bukkit.getWorld("world").setSpawnLocation(randomZahl(-70, 70), 100, randomZahl(-70, 70));
						
						player.getInventory().setItem(0, new ItemStack(Material.TNT, 1));
						
						player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 999999, 10));
						
						player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 999999, 3));
						
						player.setFoodLevel(19);

						player.setMaxHealth(500);
						
						player.setHealth(500);

						player.teleport(new Location(player.getWorld(), randomZahl(-70, 70), 100, randomZahl(-70, 70)));
					}
					
					break;
				}
			}
		}
	}
	
	public static void Start() 
	{
		if (playing) return;
		
		CloudServer.getInstance().changeToIngame();
		
		Bukkit.getWorld("world").setSpawnLocation(randomZahl(-70, 70), 100, randomZahl(-70, 70));
		
		playing = true;
		
		for (Player player : Bukkit.getOnlinePlayers()) 
		{
			player.teleport(new Location(player.getWorld(), randomZahl(-70, 70), 100, randomZahl(-70, 70)));
			
			players.add(new PlayerData(player.getName()));
			
			player.sendMessage(prefix + "§dLos gehts!");
			
			ItemStack item = new ItemStack(Material.TNT);
			
			ItemStack itemm = new ItemStack(Material.BLAZE_ROD);
			
			ItemMeta metaa = itemm.getItemMeta();
			
			metaa.setDisplayName("§cTNT §eZauberstab");
			
			itemm.setItemMeta(metaa);
			
			player.getInventory().setItem(0, item);
			
			player.getInventory().setItem(1, itemm);
			
			player.setFoodLevel(19);
			
			player.setMaxHealth(500);
			
			player.setHealth(500);
			
			player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 999999, 10));
			
			player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 999999, 3));
		}
		
		time = -1;
	}
	
	public static void PlayerDied (Player player) 
	{
		player.getInventory().setItem(0, new ItemStack(Material.TNT, 1));
		
		for (PlayerData playerData : players) 
		{
			if (player.getName().equals(playerData.name))
			{
				playerData.lives--;
				
				if (playerData.lives <= 0)
				{
					playerData.dead = true;
					
					player.setHealth(20);
					
					for (PlayerData playerD : players) 
					{
						if (!playerD.dead)
						{
							Bukkit.getPlayer(playerD.name).hidePlayer(player);
						}
					}
					
					player.setGameMode(GameMode.ADVENTURE);
					
					player.setAllowFlight(true);

					player.setMaxHealth(20);
					
					player.getInventory().clear();
					
					ItemStack item = new ItemStack(Material.COMPASS);
					
					ItemMeta meta = item.getItemMeta();
					
					meta.setDisplayName("§aTeleporter");
					
					item.setItemMeta(meta);
					
					player.getInventory().setItem(4, item);
					
					for (Player playerE : Bukkit.getOnlinePlayers()) 
					{
						playerE.sendMessage(prefix + "§f" + playerData.name + " ist §causgeschieden!");
					}
				}
				else
				{
					for (Player playerE : Bukkit.getOnlinePlayers()) 
					{
						playerE.sendMessage(prefix + "§f" + playerData.name + " hat noch §a" + playerData.lives + " §fLeben!");
					}
				}
				
				break;
			}
		}		
		
		CheckForWinner();
	}
	
	public static boolean IsAlive(String name) 
	{
		for (PlayerData playerData : players)
		{
			if (name.equals(playerData.name)) 
			{
				if (!playerData.dead) 
				{
					return true;
				}
			}
		}
		
		return false;
	}
	
	public static Inventory createTeleporter() 
	{
		Inventory inventory = Bukkit.createInventory(null, 9, "§aTeleporter");

		int index = 0;
		
		try 
		{
			for (PlayerData pl : players)
			{
				if (!pl.dead) 
				{
					ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
			        SkullMeta sm = (SkullMeta) item.getItemMeta();
			        sm.setOwner(pl.name);
			        sm.setDisplayName("§f" + pl.name);
			        item.setItemMeta(sm);
	                inventory.setItem(index, item);
	                
	                index++;
				}
			}
		}
		catch (Exception ex)
		{
			for (Player i : Bukkit.getOnlinePlayers()) 
			{
				i.sendMessage(ex.getMessage());
			}
		}
		
        for (int i = 0; i < inventory.getSize(); ++i) 
        {
            if (inventory.getItem(i) == null) 
            {
            	ItemStack stack = new ItemStack(Material.STAINED_GLASS_PANE,1 , (short) 7);
            	ItemMeta meta = stack.getItemMeta();
            	meta.setDisplayName("§d");
            	stack.setItemMeta(meta);
                inventory.setItem(i, stack);
            }
        }
        
        return inventory;
	}
	
	public static void 
	SpoonfeedingFürAktenkoffer_WarumMussIchNeBewerbungsFunktionMachen_Alter_DerGanzeFractureCoreIsVonMir_MachWenigstensCreditsFürDenCoreHin_DerCoreIsNedInApache2_0(Location location, float yaw, float pitch, String name, Player player) 
	{
	    TNTPrimed tnt = (TNTPrimed) location.getWorld().spawnEntity(location, EntityType.PRIMED_TNT);

	    tnt.setCustomName(name);
	    
	    tnt.setCustomNameVisible(false);

	    tnt.setFuseTicks(Integer.MAX_VALUE);
	    
	    tnt.setVelocity(new Vector(-Math.sin(Math.toRadians(yaw)), Math.sin(Math.toRadians(-pitch)), Math.cos(Math.toRadians(yaw))));
	            
	    new Thread(() -> 
	    {
	        while((tnt.getLocation().getWorld().getBlockAt(tnt.getLocation().subtract(0d, 1d, 0d)).isEmpty() && !tnt.getLocation().getWorld().getBlockAt(tnt.getLocation().subtract(0d, 1d, 0d)).isLiquid()) && tnt.getLocation().getWorld().getBlockAt(tnt.getLocation().subtract(0d, 1d, 0d)).getType() != Material.GRASS && tnt.getLocation().getWorld().getBlockAt(tnt.getLocation().subtract(0d, 1d, 0d)).getType() != Material.LONG_GRASS);
	        
	        Location loc = tnt.getLocation();
	        
	        try 
	        {
	        	tnt.teleport(tnt.getLocation().subtract(0, tnt.getLocation().getY(), 0));
	        }
	        catch (Exception ex)
	        {
	        	
	        }
	        
	        player.playSound(player.getLocation(), Sound.EXPLODE, 3.0f, 1.0f);
			
	        player.getWorld().playEffect(loc, Effect.EXPLOSION_LARGE, 5);
			
			for(Player damagedPlayer : Bukkit.getOnlinePlayers()) 
			{
				if (damagedPlayer.getName().equals(player.getName()) || damagedPlayer.getGameMode() == GameMode.ADVENTURE) continue;
				
			    double damage = 100d - (10 * loc.distance(damagedPlayer.getLocation()));
			    
			    if(damage > 0) 
			    {
			    	damagedPlayer.playSound(damagedPlayer.getLocation(), Sound.EXPLODE, 3.0f, 1.0f);
			    	
			        if(damagedPlayer.getHealth() <= damage) 
			        {
			        	damagedPlayer.teleport(new Location(damagedPlayer.getWorld(), randomZahl(-70, 70), 100, randomZahl(-70, 70)));
			        	
			            damagedPlayer.setHealth(500d);
			            
			            player.setHealth(500);
			            
			            TntMain.PlayerDied(damagedPlayer);
			            
			            for (Player p : Bukkit.getOnlinePlayers())
			            {
			            	p.sendMessage(TntMain.prefix + "§4" + damagedPlayer.getName() + " §cwurde von §a" + name + " §cgetötet§f!");
			            }
			        }
			        else
			            damagedPlayer.setHealth(damagedPlayer.getHealth() - damage);
			    }
			}
			
	    }).start();
	}
	
	public static void CheckForWinner() 
	{
		if (!playing || over) return;
		
		ArrayList<String> alive = new ArrayList<String>();
		
		for (PlayerData player : players) 
		{
			if (!player.dead)
			{
				alive.add(player.name);
			}
		}
		
		if (alive.size() == 1)
		{
			for (Player player : Bukkit.getOnlinePlayers()) 
			{
				player.getInventory().clear();
				
				player.setAllowFlight(true);
				
				player.setGameMode(GameMode.ADVENTURE);
				
				player.setMaxHealth(20);
				
				player.sendMessage(prefix + "§a§l" + alive.get(0) + " §fhat gewonnen!");
				
				player.sendMessage(prefix + "§fServer wird in 10 sek. gestoppt.");
				
				//try 
				//{
				//	SQLManager.setData("uuid, wins", "'YEET', '1'");
				//} 
				//catch (SQLException e) 
				//{
				//	e.printStackTrace();
				//}
				
				player.removePotionEffect(PotionEffectType.SPEED);
				
				player.removePotionEffect(PotionEffectType.JUMP);
			}
			
			for (Player player : Bukkit.getOnlinePlayers())
			{
				ItemStack item = new ItemStack(Material.SLIME_BALL);
				
				ItemMeta meta = item.getItemMeta();
				
				meta.setDisplayName("§aZur Lobby");
				
				item.setItemMeta(meta);
				
				ItemStack itemm = new ItemStack(Material.TNT);
				
				ItemMeta metaa = itemm.getItemMeta();
				
				metaa.setDisplayName("§cNochmal Spielen");
				
				itemm.setItemMeta(metaa);
				
				player.getInventory().clear();
				
				new Thread(new Runnable() {
					
					@Override
					public void run() 
					{
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						player.getInventory().setItem(0, item);
						
						player.getInventory().setItem(1, itemm);
					}
				}).start();
			}
			
			time = 10;
			
			over = true;
			
			return;
		}
		
		if (Bukkit.getOnlinePlayers().size() == 1) 
		{
			for (Player playerY : Bukkit.getOnlinePlayers()) 
			{
				playerY.setGameMode(GameMode.ADVENTURE);
				
				playerY.sendMessage(prefix + "§a§l" + playerY.getName() + " §fhat gewonnen!");
				
				playerY.sendMessage(prefix + "§fServer wird in 10 sek. gestoppt.");
				
				playerY.getInventory().clear();
				
				playerY.removePotionEffect(PotionEffectType.SPEED);
				
				playerY.removePotionEffect(PotionEffectType.JUMP);
			}
		}
	}
	
	public static void PlayerJoin(Player player)
	{
		player.teleport(new Location(player.getWorld(), 2, 8, 16));
		
		if (playing) 
		{
			player.setHealth(20);
			
			for (PlayerData playerD : players) 
			{
				if (!playerD.dead)
				{
					Bukkit.getPlayer(playerD.name).hidePlayer(player);
				}
			}
			
			player.setGameMode(GameMode.ADVENTURE);
			
			player.setAllowFlight(true);
			
			player.setMaxHealth(20);
			
			player.getInventory().clear();
			
			ItemStack item = new ItemStack(Material.COMPASS);
			
			ItemMeta meta = item.getItemMeta();
			
			meta.setDisplayName("§aTeleporter");
			
			item.setItemMeta(meta);
			
			player.getInventory().setItem(4, item);
		}
		else if (waiting)
		{
			time -= 10;
			
			if (time < 1) 
			{
				time = 1;
			}
		}
		else if (Bukkit.getOnlinePlayers().size() >= 2)
		{
			waiting = true;
					
			enoughPlayers = true;
			
			time = 60;
		}
	}
	
	public static void PlayerLeave(String name) 
	{
		for (Player player : Bukkit.getOnlinePlayers()) 
		{
			player.sendMessage(prefix + "§f" + name + " hat uns verlassen!");
		}
		
		if (playing)
		{
			for (PlayerData playerData : players) 
			{
				if (name.equals(playerData.name))
				{
					players.remove(playerData);
					
					break;
				}
			}
		}
		else if (waiting && Bukkit.getOnlinePlayers().size() < 2)
		{
			waiting = false;
			
			enoughPlayers = false;
			
			time = -1;
		}
		
		CheckForWinner();
	}
	
	public static void OnSecond() 
	{
		if (over)
		{
			time--;
			
			if (time <= 0)
			{
				for (Player playerY : Bukkit.getOnlinePlayers()) 
				{
					Main.getInstance().sendToFallback(Main.getInstance(), playerY);
				}
				
				Bukkit.getServer().shutdown();
			}
			
			return;
		}
		
		if (playing)
		{
			for (Player player : Bukkit.getOnlinePlayers()) 
			{
				player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 999999, 10));
			
				player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 999999, 3));
			
				player.setFoodLevel(10);
			}
		}
		
		if (time > 0)
		{
			time--;
			
			for (Player player : Bukkit.getOnlinePlayers()) 
			{
				player.setLevel(time);
			}
			
			if (time <= 5)
			{
				for (Player player : Bukkit.getOnlinePlayers()) 
				{
					player.sendMessage(prefix + "§fStart in §a" + time);
				}
			}
		}
		else if (time == 0)
		{
			CloudServer.getInstance().changeToIngame();
			
			playing = true;
			
			Bukkit.getWorld("world").setSpawnLocation(randomZahl(-70, 70), 100, randomZahl(-70, 70));
			
			for (Player player : Bukkit.getOnlinePlayers()) 
			{
				players.add(new PlayerData(player.getName()));
				
				player.teleport(new Location(player.getWorld(), randomZahl(-70, 70), 100, randomZahl(-70, 70)));
				
				player.sendMessage(prefix + "§dLos gehts!");
				
				ItemStack item = new ItemStack(Material.TNT);
				
				ItemStack itemm = new ItemStack(Material.BLAZE_ROD);
				
				ItemMeta metaa = itemm.getItemMeta();
				
				metaa.setDisplayName("§cTNT §eZauberstab");
				
				itemm.setItemMeta(metaa);
				
				player.getInventory().setItem(0, item);
				
				player.getInventory().setItem(1, itemm);
				
				player.setFoodLevel(10);
				
				player.setMaxHealth(500);
				
				player.setHealth(500);
				
				player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 999999, 10));
				
				player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 999999, 3));
			}
			
			time--;
		}
	}
}
