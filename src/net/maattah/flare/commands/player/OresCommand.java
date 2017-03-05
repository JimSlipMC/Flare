package net.maattah.flare.commands.player;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.maattah.flare.utils.C;
import net.maattah.flare.utils.FlarePlayer;
import net.maattah.flare.utils.Lang;

public class OresCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("ores")) {
			if(!(sender instanceof Player)) { sender.sendMessage(Lang.PREFIX.toString() + Lang.PLAYER_ONLY.toString()); return true; }
			Player p = (Player) sender;
			if(args.length == 0) {
				sendOres(p);
				return true;
			}
			
			if(args.length == 1) {
				Player target = Bukkit.getPlayerExact(args[0]);
				if(target != null) {
					sendOres(p, target);
					return true;
				} else {
					p.sendMessage(Lang.PREFIX.toString() + Lang.INVALID_PLAYER.toString());
					return true;
				}
			}
			
		}
		return false;
	}
	
	public static void sendOres(Player p) {
		FlarePlayer player = new FlarePlayer(p.getUniqueId());
		p.sendMessage(C.GREEN + "Emeralds: %arg%".replace("%arg%", C.GRAY + player.getEmeralds()));
		p.sendMessage(C.AQUA + "Diamonds: %arg%".replace("%arg%", C.GRAY + player.getDiamonds()));
		p.sendMessage(C.GOLD + "Gold: %arg%".replace("%arg%", C.GRAY + player.getGold()));
		p.sendMessage(C.BLUE + "Lapis: %arg%".replace("%arg%", C.GRAY + player.getLapis()));
		p.sendMessage(C.RED + "Redstone: %arg%".replace("%arg%", C.GRAY + player.getRedstone()));
		p.sendMessage(C.GRAY + "Iron: %arg%".replace("%arg%", C.GRAY + player.getIron()));
		p.sendMessage(C.DARK_GRAY + "Coal: %arg%".replace("%arg%", C.GRAY + player.getCoal()));
	}
	
	public static void sendOres(Player p, Player target) {
		FlarePlayer player = new FlarePlayer(target.getUniqueId());
		p.sendMessage(C.GREEN + "Emeralds: %arg%".replace("%arg%", C.GRAY + player.getEmeralds()));
		p.sendMessage(C.AQUA + "Diamonds: %arg%".replace("%arg%", C.GRAY + player.getDiamonds()));
		p.sendMessage(C.GOLD + "Gold: %arg%".replace("%arg%", C.GRAY + player.getGold()));
		p.sendMessage(C.BLUE + "Lapis: %arg%".replace("%arg%", C.GRAY + player.getLapis()));
		p.sendMessage(C.RED + "Redstone: %arg%".replace("%arg%", C.GRAY + player.getRedstone()));
		p.sendMessage(C.GRAY + "Iron: %arg%".replace("%arg%", C.GRAY + player.getIron()));
		p.sendMessage(C.DARK_GRAY + "Coal: %arg%".replace("%arg%", C.GRAY + player.getCoal()));
	}

}
