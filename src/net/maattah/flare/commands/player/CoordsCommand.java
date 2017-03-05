package net.maattah.flare.commands.player;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import net.maattah.flare.Main;

public class CoordsCommand implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("coords")) {
			for(String string : Main.getInstance().getConfig().getStringList("coords")) {
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', string));
			}
		}
		return false;
	}

}
