package net.maattah.flare.utils;

import static com.sk89q.worldguard.bukkit.BukkitUtil.toVector;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.sk89q.worldedit.Vector;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

import net.maattah.flare.Main;

public class WorldGuard {
	
	private static WorldGuardPlugin worldGuard;
	
	public static void setup() {
        Plugin worldGuardPlugin = Main.getInstance().getServer().getPluginManager().getPlugin("WorldGuard");
        if (worldGuardPlugin == null || !(worldGuardPlugin instanceof WorldGuardPlugin)) {
        	worldGuard = null;
        } else {
            worldGuard = (WorldGuardPlugin) worldGuardPlugin;
        }
    }
	
	public static WorldGuardPlugin getWorldGuard() {
		return worldGuard;
	}

    @SuppressWarnings("deprecation")
	public static boolean isPvPEnabled(Player player) {

        Location loc = player.getLocation();
        World world = loc.getWorld();
        Vector vector = toVector(loc);

        RegionManager regionManager = worldGuard.getRegionManager(world);
        ApplicableRegionSet region = regionManager.getApplicableRegions(vector);
        
        return region.allows(DefaultFlag.PVP) || region.getFlag(DefaultFlag.PVP) == null;
    }
    
	public static ProtectedRegion getProtectedRegion(Location location) {
		for(ProtectedRegion region : worldGuard.getRegionManager(location.getWorld()).getApplicableRegions(location)) {
			if(region != null)
				return region;
		}
		return null;
	}

}
