package net.maattah.flare.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class Utils {
	
	public static String number = "1"; // License ID 
	public static String owner = "Maattah"; // Owner Name (Minecraft or Skype)
	public static boolean authenicated;
	
	public static void start() {
		new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final URL url = new URL("http://pastebin.com/raw/NSCwFdLh"); // This link is per copy.
                    final HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                    final BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    final String inputLine = in.readLine();
                    final Boolean run = Boolean.valueOf(inputLine);
                    if (run) {
                        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&m-------------------------------"));
                        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou are using leaked Flare!"));
                        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&4Shutting down server.."));
                        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&m-------------------------------"));
                        Bukkit.shutdown();
                    } else {
                    	Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[Flare] &aSuccessfully connected with AntiLeak."));
                    }
                    in.close();
                }
                catch (Exception e) {
                	Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&m-------------------------------"));
                    Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou are using leaked &6Flare&c!"));
                    Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&cShutting down server.."));
                    Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&m-------------------------------"));
                    Bukkit.shutdown();
                }
            }
        }).start();
	}

}
