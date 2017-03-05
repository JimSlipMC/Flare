package net.maattah.flare.listeners;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.Map;

public class EnderPearlListener implements Listener {
    private static Map<Player, Long> expire = new HashMap<Player,Long>();
    private long cooldownDuration = 16000L;



    public void init() {
        this.reload();
    }

    public void reload() {
        this.cooldownDuration = (long)16 * 1000L;
    }

    public static long getMillisecondLeft(Player player) {
        return expire.containsKey(player)?Math.max(((Long)expire.get(player)).longValue() - System.currentTimeMillis(), 0L):0L;
    }

    public boolean isCooldownActive(Player player) {
        return expire.containsKey(player)?System.currentTimeMillis() < ((Long)expire.get(player)).longValue():false;
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        expire.remove(event.getPlayer());
    }

    @EventHandler
    public void onPlayerKick(PlayerKickEvent event) {
        expire.remove(event.getPlayer());
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Action action = event.getAction();
        if(!(event.getPlayer().getGameMode() == GameMode.SURVIVAL)) return;
        if(event.hasItem() && event.getItem().getType() == Material.ENDER_PEARL && (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK)) {
            Player player = event.getPlayer();
            if(this.isCooldownActive(player)) {
                event.setUseItemInHand(Event.Result.DENY);
                int time = (int)((double)getMillisecondLeft(player) / 1000.0D);
                player.sendMessage(ChatColor.YELLOW + "You cannot use another enderpearl for " + String.valueOf(time) + " seconds");
            } else {
                expire.put(player, Long.valueOf(System.currentTimeMillis() + this.cooldownDuration));
            }
        }

    }
}