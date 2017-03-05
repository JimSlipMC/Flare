package net.maattah.flare.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import net.maattah.flare.Main;
import net.maattah.flare.utils.FlarePlayer;

public class JoinHandler implements Listener {
	
	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.HIGH)
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		FlarePlayer player = new FlarePlayer(e.getPlayer().getUniqueId());
		player.createUser(e.getPlayer());
		if(player.getUserFile().getBoolean("firstJoin")) {
			player.getUserFile().set("firstJoin", false);
			player.saveUserFile();
			p.getInventory().addItem(new ItemStack(Material.FISHING_ROD, 1));
			p.getInventory().addItem(new ItemStack(Material.COOKED_BEEF, 32));
			// Add spawn shit here :)
		}
		
		if(player.getUserFile().getStringList("notes") != null) {
			for(String note : player.getUserFile().getStringList("notes")) {
				for(Player staff : Bukkit.getOnlinePlayers()) {
					if(staff.hasPermission(Main.getNode() + ".notes.onjoin")) {
						staff.sendMessage(note);
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		FlarePlayer player = new FlarePlayer(e.getPlayer().getUniqueId());
		player.saveUserFile();
	}

}
