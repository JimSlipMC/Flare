package net.maattah.flare.listeners;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.maattah.flare.Main;
import net.maattah.flare.commands.staff.StaffCommand;
import net.maattah.flare.utils.C;
import net.maattah.flare.utils.Lang;

public class StaffListener implements Listener {
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		ItemStack item = event.getItem();
		if (item != null) {
			if (StaffCommand.staffToggle.contains(player)) {
			if (item.getItemMeta().getDisplayName().equals(C.translateTxtToColor(Main.getInstance().getConfig().getString("STAFFMODE.RTP.DISPLAYNAME")))) {
				Random r = new Random();
                ArrayList<Player> players = new ArrayList<Player>();
                for (Player online : Bukkit.getServer().getOnlinePlayers()) {
                    if (online != player) {
                        players.add(online);
                    }
                }
                int index = r.nextInt(players.size());
                Player loc = (Player)players.get(index);
                player.teleport(loc);
                player.sendMessage(Lang.PREFIX.toString() + Lang.STAFFMODE_RANDOMTELEPORTER.toString().replace("<player>", loc.getName()));
			}
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onVanish(PlayerInteractEvent e) {
		Player player = e.getPlayer();
		ItemStack item = e.getItem();
		if (item != null) {
		if (item.getItemMeta().getDisplayName().equals(C.translateTxtToColor(Main.getInstance().getConfig().getString("STAFFMODE.VANISH_ON.DISPLAYNAME")))) {
			ItemStack v2 = new ItemStack(Material.valueOf(Main.getInstance().getConfig().getString("STAFFMODE.VANISH_OFF.ITEM")));
			ItemMeta v2Meta = v2.getItemMeta();
			v2Meta.setDisplayName(C.translateTxtToColor(Main.getInstance().getConfig().getString("STAFFMODE.VANISH_OFF.DISPLAYNAME")));
			v2.setItemMeta(v2Meta);
			for (Player vanished : Bukkit.getServer().getOnlinePlayers()) {
				vanished.hidePlayer(player);
			}
			player.sendMessage(Lang.STAFFMODE_VANISH_ON.toString());
			player.getInventory().setItem(Main.getInstance().getConfig().getInt("STAFFMODE.VANISH_ON.SLOT"), v2);
		}
		if (item.getItemMeta().getDisplayName().equals(C.translateTxtToColor(Main.getInstance().getConfig().getString("STAFFMODE.VANISH_OFF.DISPLAYNAME")))) {
			ItemStack v = new ItemStack(Material.valueOf(Main.getInstance().getConfig().getString("STAFFMODE.VANISH_ON.ITEM")));
			ItemMeta vMeta = v.getItemMeta();
			vMeta.setDisplayName(C.translateTxtToColor(Main.getInstance().getConfig().getString("STAFFMODE.VANISH_ON.DISPLAYNAME")));
			v.setItemMeta(vMeta);
			for (Player vanished : Bukkit.getServer().getOnlinePlayers()) {
				vanished.showPlayer(player);
			}
			player.sendMessage(Lang.STAFFMODE_VANISH_OFF.toString());
			player.getInventory().setItem(Main.getInstance().getConfig().getInt("STAFFMODE.VANISH_ON.SLOT"), v);
			}
		}
	}
	
	@EventHandler
	public void onEntityInteract(PlayerInteractEntityEvent event) {
		Player player = event.getPlayer();
		Player target = (Player) event.getRightClicked();
		ItemStack item = event.getPlayer().getItemInHand();
		if (StaffCommand.staffToggle.contains(player)) {
		if (item != null) {
		
			if (item.getItemMeta().getDisplayName().equals(C.translateTxtToColor(Main.getInstance().getConfig().getString("STAFFMODE.INSPECTOR.DISPLAYNAME")))) {
				player.openInventory(target.getInventory());
				player.sendMessage(Lang.PREFIX.toString() + Lang.STAFFMODE_INVENTORY_INSPECT.toString().replace("<target>", target.getName()));
				event.setCancelled(true);
			}
		}
	}
	}
	
	@EventHandler
	public void onBreak(BlockBreakEvent event) {
		Player player = event.getPlayer();
		if (StaffCommand.staffToggle.contains(player)) {
			event.setCancelled(true);
			player.sendMessage(Lang.PREFIX.toString() + Lang.STAFFMODE_BREAK.toString());
		}
	}
	
	@EventHandler
	public void onPlace(BlockPlaceEvent event) {
		Player player = event.getPlayer();
		if (StaffCommand.staffToggle.contains(player)) {
			event.setCancelled(true);
			player.sendMessage(Lang.PREFIX.toString() + Lang.STAFFMODE_PLACE.toString());
		}
	}
}