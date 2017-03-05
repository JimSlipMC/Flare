package net.maattah.flare.commands.staff;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.maattah.flare.Main;
import net.maattah.flare.staff.VanishHandler;
import net.maattah.flare.utils.Lang;

public class VanishCommand implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("vanish") || cmd.getName().equalsIgnoreCase("v")) {
			if(!(sender instanceof Player)) { sender.sendMessage(Lang.PREFIX.toString() + Lang.PLAYER_ONLY.toString()); return true; }
			Player p = (Player) sender;
			
			if(p.hasPermission(Main.getNode() + ".vanish")) {
				if(!VanishHandler.vanished.contains(p.getUniqueId())) {
					VanishHandler.vanishPlayer(p);
					sender.sendMessage(Lang.PREFIX.toString() + "You are now completely hidden to all normal players.");
				} else {
					VanishHandler.unvanishPlayer(p);
					sender.sendMessage(Lang.PREFIX.toString() + "You are now visbile to all players.");
				}
			}
		}
		return false;
	}

}
