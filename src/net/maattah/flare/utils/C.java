package net.maattah.flare.utils;

import org.bukkit.ChatColor;

import net.maattah.flare.Main;

public class C {
	
	public static String GREEN = ChatColor.GREEN.toString();
	public static String AQUA = ChatColor.AQUA.toString();
	public static String RED = ChatColor.RED.toString();
	public static String LIGHT_PURPLE = ChatColor.LIGHT_PURPLE.toString();
	public static String YELLOW = ChatColor.YELLOW.toString();
	public static String WHITE = ChatColor.WHITE.toString();
	public static String DARK_BLUE = ChatColor.DARK_BLUE.toString();
	public static String DARK_GREEN = ChatColor.DARK_GREEN.toString();
	public static String DARK_AQUA = ChatColor.DARK_AQUA.toString();
	public static String DARK_RED = ChatColor.DARK_RED.toString();
	public static String DARK_PURPLE = ChatColor.DARK_PURPLE.toString();
	public static String GOLD = ChatColor.GOLD.toString();
	public static String GRAY = ChatColor.GRAY.toString();
	public static String DARK_GRAY = ChatColor.DARK_GRAY.toString();
	public static String BLUE = ChatColor.BLUE.toString();
	public static String LINE = ChatColor.DARK_GRAY.toString() + ChatColor.STRIKETHROUGH.toString() + "--------------------------------------";
	public static String BOLD = ChatColor.BOLD.toString();
	public static String NAME = ChatColor.GOLD.toString() + Main.getInstance().getDescription().getName();
	public static String COMMAND = ChatColor.GOLD.toString() + Main.getInstance().getDescription().getName() + " b" + Main.getInstance().getDescription().getVersion();

	
	public static String translateTxtToColor(String msg) {
		return ChatColor.translateAlternateColorCodes('&', msg);
	}
	
	public static boolean isInt(String s) {
	    try {
	        Integer.parseInt(s);
	    } catch (NumberFormatException nfe) {
	        return false;
	    }
	    return true;
	}
	
}
