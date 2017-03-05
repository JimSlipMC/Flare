package net.maattah.flare.commands.staff;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.maattah.flare.Main;
import net.maattah.flare.utils.Lang;

public class FlyCommand implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("fly")) {
			if(!(sender instanceof Player)) { sender.sendMessage(Lang.PREFIX.toString() + Lang.PLAYER_ONLY.toString()); return true; }
			Player p = (Player) sender;
			
			if(p.hasPermission(Main.getNode() + ".fly")) {
				if(args.length == 0) {
					if(p.getAllowFlight()) {
						p.setAllowFlight(false);
						p.sendMessage(Lang.PREFIX.toString() + Lang.FLY_DISABLED.toString().replace("%player%", p.getName()));
						return true;
					} else {
						p.setAllowFlight(true);
						p.sendMessage(Lang.PREFIX.toString() + Lang.FLY_ENABLED.toString().replace("%player%", p.getName()));
						return true;
					}
				}
				
				
				if(args.length == 1) {
					if(p.hasPermission(Main.getNode() + ".fly.other")) {
						Player target = Bukkit.getPlayerExact(args[0]);
						if(target != null) {
							if(target.getAllowFlight()) {
								target.setAllowFlight(false);
								p.sendMessage(Lang.PREFIX.toString() + Lang.FLY_DISABLED.toString().replace("%player%", target.getName()));
								target.sendMessage(Lang.PREFIX.toString() + Lang.FLY_DISABLED_OTHER.toString().replace("%player%", p.getName()));
								return true;
							} else {
								target.setAllowFlight(true);
								p.sendMessage(Lang.PREFIX.toString() + Lang.FLY_ENABLED.toString().replace("%player%", target.getName()));
								target.sendMessage(Lang.PREFIX.toString() + Lang.FLY_ENABLED_OTHER.toString().replace("%player%", p.getName()));
								return true;
							}
						} else {
							p.sendMessage(Lang.PREFIX.toString() + Lang.INVALID_PLAYER.toString());
							return true;
						}
					} else {
						p.sendMessage(Lang.NO_PERMS.toString());
					}
				}
			} else {
				p.sendMessage(Lang.NO_PERMS.toString());
			}
		}
		return false;
	}

}
