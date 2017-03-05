package net.maattah.flare.commands.staff;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.maattah.flare.Main;
import net.maattah.flare.utils.Lang;

public class TPCommand implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("tp")) {
			if(!(sender instanceof Player)) { sender.sendMessage(Lang.PREFIX.toString() + Lang.PLAYER_ONLY.toString()); return true; }
			Player p = (Player) sender;
			
			if(p.hasPermission(Main.getNode() + ".tp")) {
				if(args.length == 0) {
					p.sendMessage(Lang.PREFIX.toString() + Lang.TP_USAGE.toString());
					return true;
				}
			
				else if(args.length == 1) {
					Player target = Bukkit.getPlayerExact(args[0]);
					if(target != null) {
						p.sendMessage(Lang.PREFIX.toString() + Lang.TELEPORT.toString().replace("%player%", target.getName()));
						p.teleport(target);
						return true;
					} else {
						p.sendMessage(Lang.PREFIX.toString() + Lang.INVALID_PLAYER.toString());
					}
				}
			} else {
				p.sendMessage(Lang.NO_PERMS.toString());
			}
		}
		return false;
	}

}
