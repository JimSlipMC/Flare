package net.maattah.flare.commands.staff;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.maattah.flare.Main;
import net.maattah.flare.utils.Lang;

public class GMSCommand implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("gms")) {
			if(!(sender instanceof Player)) { sender.sendMessage(Lang.PREFIX.toString() + Lang.PLAYER_ONLY.toString()); return true; }
			Player p = (Player) sender;
			
			if(p.hasPermission(Main.getNode() + ".gamemode")) {
				if(args.length == 0) {
					p.setGameMode(GameMode.SURVIVAL);
					p.sendMessage(Lang.PREFIX.toString() + Lang.GAMEMODE_SURVIVAL.toString());
					return true;
				}
				
				else if(args.length == 1) {
					if(p.hasPermission(Main.getNode() + ".gamemode.other")) {
						Player target = Bukkit.getPlayerExact(args[0]);
						if(target != null) {
							target.setGameMode(GameMode.SURVIVAL);
							p.sendMessage(Lang.PREFIX.toString() + Lang.GAMEMODE_SURVIVAL_OTHER.toString().replace("%player%", target.getName()));
							target.sendMessage(Lang.PREFIX.toString() + Lang.GAMEMODE_SURVIVAL_OTHER2.toString());
							return true;
						}
					}
				} else {
					p.sendMessage(Lang.NO_PERMS.toString());
				}
			} else {
				p.sendMessage(Lang.NO_PERMS.toString());
			}
		}
		return false;
	}

}
