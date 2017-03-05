package net.maattah.flare.commands.player;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.maattah.flare.utils.C;
import net.maattah.flare.utils.FlarePlayer;
import net.maattah.flare.utils.Lang;

public class StatsCommand implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("stats")) {
			if(!(sender instanceof Player)) { sender.sendMessage(Lang.PREFIX.toString() + Lang.PLAYER_ONLY.toString()); return true; }
			Player p = (Player) sender;
			if(args.length == 0) {
				FlarePlayer player = new FlarePlayer(p.getUniqueId());
				p.sendMessage(C.RED + "Kills: " + player.getKills());
				p.sendMessage(C.RED + "Deaths: " + player.getDeaths());
			}
			
			else if(args.length == 1) {
				Player target = Bukkit.getPlayerExact(args[0]);
				if(target != null) {
					FlarePlayer player = new FlarePlayer(target.getUniqueId());
					p.sendMessage(C.RED + "Kills: " + player.getKills());
					p.sendMessage(C.RED + "Deaths: " + player.getDeaths());
				} else {
					p.sendMessage(Lang.PREFIX.toString() + Lang.INVALID_PLAYER.toString());
				}
			}
		}
		return false;
	}

}
