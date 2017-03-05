package net.maattah.flare.utils;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.entity.Entity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class StringUtils {

	public static String formatMilisecondsToSeconds(Long time) {
        float seconds = (time + 0.0f) / 1000.0f;
        
        String string = String.format("%1$.1f", seconds);
        
        return string;
	}
	
	public static String formatSecondsToMinutes(int time) {
		int seconds = time % 60;
		int minutes = time / 60;
		
		String string = String.format("%02d:%02d", minutes, seconds);
		
		return string;
	}
	
	public static String formatSecondsToHours(int time) {
		int hours = time / 3600;
		int minutes = (time % 3600) / 60;
		int seconds = time % 60;
		
		String string = String.format("%02d:%02d:%02d", hours, minutes, seconds);
				
		return string;
	}
	
	public static String getEffectNamesList(ArrayList<PotionEffect> effects) {
		StringBuilder names = new StringBuilder();
		for(PotionEffect effect : effects) {
			names.append(getPotionEffectName(effect.getType())).append(", ");
		}
		if(names.length() != 0) {
			names.delete(names.length() - 2, names.length());
		}

		return names.toString();
	}
	
	public static String getPotionEffectName(PotionEffectType type) {
		switch(type.getName()) {
			case "ABSORPTION": return "Absorption";
			case "BLINDNESS": return "Blindness";
			case "CONFUSION": return "Confusion";
			case "DAMAGE_RESISTANCE": return "Resistance";
			case "FAST_DIGGING": return "Haste";
			case "FIRE_RESISTANCE": return "Fire Resistance";
			case "HARM": return "Instant Damage";
			case "HEAL": return "Instant Health";
			case "HEALTH_BOOST": return "Health Boost";
			case "HUNGER": return "Hunger";
			case "INCREASE_DAMAGE": return "Strength";
			case "INVISIBILITY": return "Invisibility";
			case "JUMP": return "Jump";
			case "NIGHT_VISION": return "Night Vision";
			case "POISON": return "Poison";
			case "REGENERATION": return "Regeneration";
			case "SATURATION": return "Saturation";
			case "SLOW": return "Slowness";
			case "SLOW_DIGGING": return "Slow Digging";
			case "SPEED": return "Speed";
			case "WATER_BREATHING": return "Water Breathing";
			case "WEAKNESS": return "Weakness";
			case "WITHER": return "Wither";
		}
		return "";
	}	
	
	public static String getEntityName(Entity entity) {
		switch(entity.getType().name()) {
			case "BLAZE": return "Blaze"; 
			case "CAVE_SPIDER": return "Cave Spider";
			case "CREEPER": return "Creeper";
			case "ENDERMAN": return "Enderman";
			case "IRON_GOLEM": return "Iron Golem";
			case "MAGMA_CUBE": return "Magma Cube";
			case "PIG_ZOMBIE": return "Pig Zombie";
			case "PLAYER": return "Player";
			case "SILVERFISH": return "Silverfish";
			case "SKELETON": return "Skeleton";
			case "SLIME": return "Slime";
			case "SPIDER": return "Spider";
			case "VILLAGER": return "Villager";
			case "WITCH": return "Witch";
			case "WITHER": return "Wither";
			case "WOLF":return "Wolf";
			case "ZOMBIE": return "Zombie";
		}
		return "";
	}
	
	public static String getWorldName(Location location) {
		String worldName = "";
		World world = location.getWorld();
		if(world.getEnvironment().equals(Environment.NORMAL)) {
			worldName = "World";
		} else if(world.getEnvironment().equals(Environment.NETHER)) {
			worldName = "Nether";
		} else if(world.getEnvironment().equals(Environment.THE_END)) {
			worldName = "End";
		} else {
			worldName = world.getName();
		}
		return worldName;
	}
	
}

