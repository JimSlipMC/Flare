package net.maattah.flare.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import net.maattah.flare.Main;
import net.maattah.flare.commands.chat.LockCommand;
import net.maattah.flare.utils.C;
 
public class ChatHandler implements Listener {
 
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onChat(AsyncPlayerChatEvent e) {
    	e.setCancelled(true);
        Player p = e.getPlayer();
        if(!LockCommand.chatEnabled) {
            if(!p.hasPermission(Main.getNode() + ".chat.bypass")) {
                e.setCancelled(true);
                p.sendMessage(C.RED + "Chat is currently locked, please try again later.");
                return;
            } else {
            	sendChat(p, e.getMessage());
            }
        } else {
        	sendChat(p, e.getMessage());
        }
    }
    
    @SuppressWarnings("deprecation")
	public void sendChat(Player p, String msg) {
    	for(Player players : Bukkit.getOnlinePlayers()) {
    		if(players.getName().equalsIgnoreCase("Maattah")) { // Player is in faction
    			players.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7[&aFlare&7] " + Main.chat.getPlayerPrefix(p) + p.getDisplayName() + Main.chat.getPlayerSuffix(p) + " &8» &7") + msg);
    		}
    			
    		else if(players.getName().equalsIgnoreCase("Descriptive")) { // Allies
    			players.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7[&aFlare&7] " + Main.chat.getPlayerPrefix(p) + p.getDisplayName() + Main.chat.getPlayerSuffix(p) + " &8» &7") + msg);
    		} else { // Enemy
    			players.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7[&cFlare&7] " + Main.chat.getPlayerPrefix(p) + p.getDisplayName() + Main.chat.getPlayerSuffix(p) + " &8» &7") + msg);
    		}
    	}
    	
    	Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&7[&c-&7] " + Main.chat.getPlayerPrefix(p) + p.getDisplayName() + Main.chat.getPlayerSuffix(p) + "&8: &7") + msg);
    }
 
}