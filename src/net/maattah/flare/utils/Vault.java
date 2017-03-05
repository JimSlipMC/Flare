package net.maattah.flare.utils;

import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

import net.maattah.flare.Main;
import net.milkbowl.vault.permission.Permission;

public class Vault {

	public static Permission permission;
	
	public static boolean perms() {
		RegisteredServiceProvider<Permission> permissionProvider = Main.getInstance().getServer().getServicesManager().getRegistration(Permission.class);
		if (permissionProvider != null) {
			permission = (Permission) permissionProvider.getProvider();
		}
		return permission != null;
	}
	
	public static String getPlayerGroup(Player player) {
		return permission.getPrimaryGroup(player);
	}
}
