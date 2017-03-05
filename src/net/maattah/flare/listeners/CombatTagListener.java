package net.maattah.flare.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import net.maattah.flare.utils.FlarePlayer;

import java.util.HashMap;
import java.util.Map;

public class CombatTagListener implements Listener {

    public static Map<Player, Long> combat = new HashMap<>();
    private long cooldownDuration = 30000L;

    public void init() {
        this.reload();
    }

    public void reload() {
        this.cooldownDuration = (long)30000L;
    }

    public static long getMillisecondLeft(Player player) {
        return combat.containsKey(player)?Math.max(((Long)combat.get(player)).longValue() - System.currentTimeMillis(), 0L):0L;
    }

    public static boolean isCooldownActive(Player player) {
        return combat.containsKey(player)?System.currentTimeMillis() < ((Long) combat.get(player)).longValue():false;
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        combat.remove(event.getPlayer());
    }

    @EventHandler
    public void onPlayerKick(PlayerKickEvent event) {
        combat.remove(event.getPlayer());
    }

    @EventHandler
    public void onPlayerTagged(EntityDamageByEntityEvent event) {
    	if(event.getDamager() != null) return;
        Player player = (Player) event.getEntity();
        Player player1 = (Player) event.getDamager();
        if (player1 instanceof Player) {
            if (player instanceof  Player) {
            	FlarePlayer user = new FlarePlayer(player.getUniqueId());
                if (user.getPvPTime() <= 0) {
                    combat.put(player, Long.valueOf(System.currentTimeMillis() + this.cooldownDuration));
                    combat.put(player1, Long.valueOf(System.currentTimeMillis() + this.cooldownDuration));
                }
            
            }
        }
    }

    @EventHandler
    public void onResetDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        if (player instanceof  Player) {
            combat.remove(player);
        }
    }
}