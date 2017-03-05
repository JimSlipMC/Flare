package net.maattah.flare.commands.staff;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.maattah.flare.Main;
import net.maattah.flare.listeners.FreezeHandler;
import net.maattah.flare.utils.Lang;

public class FreezeCommand implements CommandExecutor {
	
	@Override
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (cmd.getName().equalsIgnoreCase("ss") || cmd.getName().equalsIgnoreCase("freeze")) {
            if (sender.hasPermission(Main.getNode() + ".ss")) {
                if (args.length == 0) {
                    sender.sendMessage(ChatColor.RED + "Usage: /ss <player>");
                    return true;
                }
                final Player target = Bukkit.getServer().getPlayer(args[0]);
                if (target == null) {
                    sender.sendMessage(Lang.PREFIX.toString() + Lang.INVALID_PLAYER.toString());
                    return true;
                }
                if (FreezeHandler.frozen.contains(target.getName())) {
                    FreezeHandler.frozen.remove(target.getName());
                    target.sendMessage(Lang.PREFIX.toString() + Lang.UNFREEZE_PLAYER.toString());
                    sender.sendMessage(Lang.PREFIX.toString() + Lang.UNFREEZE.toString().replace("%player%", target.getName()));
                    return true;
                }
                FreezeHandler.frozen.add(target.getName());
                	for(String msg : Main.getInstance().getConfig().getStringList("freeze")) {
                		target.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
                	}
                	sender.sendMessage(Lang.PREFIX.toString() + Lang.FREEZE.toString().replace("%player%", target.getName()));
                return true;
            }
            else {
                sender.sendMessage(Lang.PREFIX.toString() + Lang.NO_PERMS.toString());
            }
        }
        return false;
    }

}
