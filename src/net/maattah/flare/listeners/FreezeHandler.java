package net.maattah.flare.listeners;

import java.util.ArrayList;

import org.bukkit.command.CommandSender;
import org.bukkit.Bukkit;
import org.bukkit.event.player.PlayerQuitEvent;

import net.maattah.flare.Main;
import net.maattah.flare.utils.Lang;

import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.Listener;

public class FreezeHandler implements Listener {
    public static ArrayList<String> frozen;
    
    @EventHandler
    public void onPlayerMove(final PlayerMoveEvent e) {
        final Player p = e.getPlayer();
        if (FreezeHandler.frozen.contains(p.getName())) {
            e.setTo(e.getFrom());
            for(String msg : Main.getInstance().getConfig().getStringList("freeze")) {
            	p.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
            }
        }
    }
    
    @EventHandler
    public void onDamage(final EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player) {
            final Player p = (Player)event.getEntity();
            if (FreezeHandler.frozen.contains(p.getName())) {
                event.setCancelled(true);
                ((CommandSender) event.getDamager()).sendMessage(Lang.PREFIX.toString() + Lang.FREEZE_NOHIT.toString());
            }
        }
    }
    
    @EventHandler
    public void onBreak(final BlockBreakEvent event) {
        final Player p = event.getPlayer();
        if (FreezeHandler.frozen.contains(p.getName())) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(Lang.PREFIX.toString() + Lang.FREEZE_BREAK.toString());
        }
    }
    
    @EventHandler
    public void onPlace(final BlockPlaceEvent event) {
        final Player p = event.getPlayer();
        if (FreezeHandler.frozen.contains(p.getName())) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(Lang.PREFIX.toString() + Lang.FREEZE_PLACE.toString());
        }
    }
    
	@SuppressWarnings("deprecation")
	@EventHandler
    public void onPlayerQuit(final PlayerQuitEvent event) {
        final Player p = event.getPlayer();
        if (FreezeHandler.frozen.contains(p.getName())) {
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "ban " + p.getName() + " Logging out while frozen");
            for (final Player players : Bukkit.getOnlinePlayers()) {
                if (players.hasPermission("ss.use")) {
                    players.sendMessage(Lang.PREFIX.toString() + Lang.FREEZE_ALERT.toString().replaceAll("%player%", p.getPlayer().getName()));
                }
            }
        }
    }
}