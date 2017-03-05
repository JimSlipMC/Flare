package net.maattah.flare.scoreboard;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scoreboard.Scoreboard;

import net.maattah.flare.Main;
import net.maattah.flare.utils.Handler;

public class ScoreboardObjectHandler extends Handler implements Listener {
	
	private Map<UUID, ScoreboardObject> sbData;

	public ScoreboardObjectHandler(Main plugin) {
		super(plugin);
		this.sbData = new HashMap<UUID, ScoreboardObject>();
	}

	@SuppressWarnings("deprecation")
	public void enable() {
		for (Player player : Bukkit.getOnlinePlayers()) {
			this.loadData(player);
		}
		Main.getInstance().getServer().getPluginManager().registerEvents(this, Main.getInstance());
	}
	
	@SuppressWarnings("deprecation")
	public void reload() {
		for (Player player : Bukkit.getOnlinePlayers()) {
			this.reloadData(player);
		}
	}

	public ScoreboardObject getScoreboardFor(Player player) {
		return this.sbData.get(player.getUniqueId());
	}

	public void loadData(Player player) {
		Scoreboard scoreboard = Main.getInstance().getServer().getScoreboardManager().getNewScoreboard();
        player.setScoreboard(scoreboard);
        this.sbData.put(player.getUniqueId(), new ScoreboardObject(scoreboard, ChatColor.translateAlternateColorCodes('&', Main.getInstance().getConfig().getString("scoreboard.title"))));
    }
	
	public void reloadData(Player player) {
		if(this.sbData.containsKey(player.getUniqueId())) {
			Scoreboard scoreaboard = player.getScoreboard();
	        player.setScoreboard(scoreaboard);
	        this.sbData.put(player.getUniqueId(), new ScoreboardObject(scoreaboard, ChatColor.translateAlternateColorCodes('&', Main.getInstance().getConfig().getString("scoreboard.title"))));
		}
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		this.loadData(player);
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		for(String entries : player.getScoreboard().getEntries()) {
			player.getScoreboard().resetScores(entries);
		}
		this.sbData.remove(player);
	}
	
	@EventHandler 
	public void onPlayerKick(PlayerKickEvent event) {
		Player player = event.getPlayer();
		for(String entries : player.getScoreboard().getEntries()) {
			player.getScoreboard().resetScores(entries);
		}
		this.sbData.remove(player);
	}
}
