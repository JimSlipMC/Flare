package net.maattah.flare.commands.staff;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.maattah.flare.Main;
import net.maattah.flare.utils.Lang;

public class TpposCommand implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("tppos")) {
			if(!(sender instanceof Player)) { sender.sendMessage(Lang.PREFIX.toString() + Lang.PLAYER_ONLY.toString()); return true; }
			Player p = (Player) sender;
			if(p.hasPermission(Main.getNode() + ".tppos")) {
				if(args.length == 0) {
					return true;
				}
			
				else if(args.length == 1) {
					return true;
				}
			
				else if(args.length == 2) {
					return true;
				}
			
				else if(args.length == 3) {
					World world = p.getWorld();
					double x = Double.valueOf(args[0]);
					double y = Double.valueOf(args[1]);
					double z = Double.valueOf(args[2]);
					p.teleport(new Location(world, x, y, z));
					return true;
				}
			} else {
				p.sendMessage(Lang.NO_PERMS.toString());
			}
		}
		return false;
	}
	
}