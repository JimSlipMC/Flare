package net.maattah.flare.commands.player;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.maattah.flare.Main;
import net.maattah.flare.utils.Lang;

public class ReclaimCommand implements CommandExecutor{

	/*
	 * 
	 * Will be making this use GROUPS later. Dont mess with it.
	 * 
	 */
    public boolean onCommand(CommandSender sender, Command cmd ,String label, String[] args) {
        Player player = (Player) sender;
        if (cmd.getName().equalsIgnoreCase("reclaim") && sender.hasPermission(Main.getNode() +".reclaim")) {
                if (Main.getInstance().redeemed.contains(String.valueOf(player.getUniqueId()))) {
                    player.sendMessage(Lang.RECLAIM_ALREADY_CLAIMED.toString());
                } else {
                    Bukkit.getServer().broadcastMessage(Lang.RECLAIM_CLAIMED.toString());
                    for (String c : Main.getInstance().getConfig().getStringList("RECLAIM.PERKS")) {
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), (c).replace("<player>", player.getName()));
                    }
                }
                Main.getInstance().redeemed.set(String.valueOf(player.getUniqueId()), "PERKS CLAIMED");
                Main.getInstance().saveCustomYml(Main.getInstance().redeemed, Main.getInstance().rdYML);
            } 
        return false;
    }
}