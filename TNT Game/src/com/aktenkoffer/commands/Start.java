package com.aktenkoffer.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.aktenkoffer.main.TntMain;

public class Start implements CommandExecutor
{
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) 
	{
		if (sender instanceof Player) 
		{
			Player player = (Player)sender;
			
			if (player.hasPermission("tnt.admin"))
			{
				TntMain.Start();
			}
		}
		
		return true;
	}

}
