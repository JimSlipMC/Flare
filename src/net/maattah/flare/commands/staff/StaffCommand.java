package net.maattah.flare.commands.staff;

import java.util.ArrayList;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.maattah.flare.Main;
import net.maattah.flare.staff.VanishHandler;
import net.maattah.flare.utils.C;
import net.maattah.flare.utils.Lang;

public class StaffCommand implements CommandExecutor {
	
	public static VanishHandler vHandler;
	
	static {
		vHandler = new VanishHandler(Main.getInstance());
	}
	
	public static ArrayList<Player> staffToggle = new ArrayList<>();
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player) sender;
		if (cmd.getName().equalsIgnoreCase("staff")) {
		if (staffToggle.contains(player)) {
			staffToggle.remove(player);
			disabledStaffMode(player);
			player.sendMessage(Lang.PREFIX.toString() + Lang.STAFFMODE_DISABLE.toString());
			return true;
	
		} else {
			staffToggle.add(player);
			enabledStaffMode(player);
			player.sendMessage(Lang.PREFIX.toString() + Lang.STAFFMODE_ENABLE.toString());
			return true;
			}
		} 
		return false;
	}
	
	public void enabledStaffMode(Player player) {
		
		ItemStack watch = new ItemStack(Material.getMaterial(Main.getInstance().getConfig().getString("STAFFMODE.RTP.ITEM")));
		ItemMeta watchMeta = watch.getItemMeta();
		watchMeta.setDisplayName(C.translateTxtToColor(Main.getInstance().getConfig().getString("STAFFMODE.RTP.DISPLAYNAME")));
		watch.setItemMeta(watchMeta);
		
		ItemStack we = new ItemStack(Material.getMaterial(Main.getInstance().getConfig().getString("STAFFMODE.ANYTHING.ITEM")));
		ItemMeta weMeta = we.getItemMeta();
		weMeta.setDisplayName(C.translateTxtToColor(Main.getInstance().getConfig().getString("STAFFMODE.ANYTHING.DISPLAYNAME")));
		we.setItemMeta(weMeta);
		
		ItemStack bars = new ItemStack(Material.getMaterial(Main.getInstance().getConfig().getString("STAFFMODE.FREEZE.ITEM")));
		ItemMeta barsMeta = bars.getItemMeta();
		barsMeta.setDisplayName(C.translateTxtToColor(Main.getInstance().getConfig().getString("STAFFMODE.FREEZE.DISPLAYNAME")));
		bars.setItemMeta(barsMeta);
		
		ItemStack v = new ItemStack(Material.getMaterial(Main.getInstance().getConfig().getString("STAFFMODE.VANISH_ON.ITEM")));
		ItemMeta vMeta = v.getItemMeta();
		vMeta.setDisplayName(C.translateTxtToColor(Main.getInstance().getConfig().getString("STAFFMODE.VANISH_ON.DISPLAYNAME")));
		v.setItemMeta(vMeta);
		
		ItemStack h = new ItemStack(Material.getMaterial(Main.getInstance().getConfig().getString("STAFFMODE.HIDEHANDS.ITEM")));
		ItemMeta hMeta = h.getItemMeta();
		hMeta.setDisplayName(C.translateTxtToColor(Main.getInstance().getConfig().getString("STAFFMODE.HIDEHANDS.DISPLAYNAME")));
		h.setItemMeta(hMeta);
		
		ItemStack book = new ItemStack(Material.getMaterial(Main.getInstance().getConfig().getString("STAFFMODE.INSPECTOR.ITEM")));
		ItemMeta bMeta = book.getItemMeta();
		bMeta.setDisplayName(C.translateTxtToColor(Main.getInstance().getConfig().getString("STAFFMODE.INSPECTOR.DISPLAYNAME")));
		book.setItemMeta(bMeta);
		
		player.getInventory().setItem(Main.getInstance().getConfig().getInt("STAFFMODE.ANYTHING.SLOT"), we);
		player.getInventory().setItem(Main.getInstance().getConfig().getInt("STAFFMODE.FREEZE.SLOT"), bars);
		player.getInventory().setItem(Main.getInstance().getConfig().getInt("STAFFMODE.VANISH_ON.SLOT"), v);
		player.getInventory().setItem(Main.getInstance().getConfig().getInt("STAFFMODE.HIDEHANDS.SLOT"), h);
		player.getInventory().setItem(Main.getInstance().getConfig().getInt("STAFFMODE.RTP.SLOT"), watch);
		player.getInventory().setItem(Main.getInstance().getConfig().getInt("STAFFMODE.INSPECTOR.SLOT"), book);
	
	}
	
	public void disabledStaffMode(Player player) {
		player.getInventory().clear();
		player.setGameMode(GameMode.SURVIVAL);
	}
}