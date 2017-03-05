package net.maattah.flare.commands.player;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import net.maattah.flare.Main;

public class HelpCommand implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("help")) {
			for(String string : Main.getInstance().getConfig().getStringList("help")) {
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', string));
			}
		}
		return false;
	}

}
