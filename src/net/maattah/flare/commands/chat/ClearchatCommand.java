package net.maattah.flare.commands.chat;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.maattah.flare.Main;
import net.maattah.flare.utils.C;

public class ClearchatCommand implements CommandExecutor {
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("cc") || cmd.getName().equalsIgnoreCase("clearchat")) {
			if(sender.hasPermission(Main.getNode() + ".chat.clear")) {
				for(Player player : Bukkit.getOnlinePlayers()) {
					if(!player.hasPermission(Main.getNode() + ".chat.noclear")) {
						for (int x = 0; x < 150; x++){
							player.sendMessage("");
						}
					}
				}
				Bukkit.broadcastMessage(C.GREEN + "Chat has been cleared by " + sender.getName() + ".");
			}
		}
		return false;
	}
}