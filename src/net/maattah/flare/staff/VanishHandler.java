package net.maattah.flare.staff;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import net.maattah.flare.Main;
import net.maattah.flare.utils.Handler;

public class VanishHandler extends Handler implements Listener {
	public static ArrayList<UUID> vanished;
	
	public VanishHandler(Main plugin) {
		super(plugin);
		vanished = new ArrayList<UUID>();
	}
	
	@SuppressWarnings("deprecation")
	public static void vanishPlayer(Player p) {
		vanished.add(p.getUniqueId());
		for(Player players : Bukkit.getOnlinePlayers()) {
			if(!players.hasPermission(Main.getNode() + ".vanish.see")) {
				players.hidePlayer(p);
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	public static void unvanishPlayer(Player p) {
		vanished.remove(p.getUniqueId());
		for(Player players : Bukkit.getOnlinePlayers()) {
				players.showPlayer(p);
		}
	}
}