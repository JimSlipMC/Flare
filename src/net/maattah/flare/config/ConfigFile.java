package net.maattah.flare.config;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;

import net.maattah.flare.Main;

public class ConfigFile {
	
    private Main plugin;
    private File file;
    private YamlConfiguration configuration;
    
    public ConfigFile() {
        (this.plugin = Main.getInstance()).saveDefaultConfig();
        this.file = new File(this.plugin.getDataFolder(), "config.yml");
        this.configuration = YamlConfiguration.loadConfiguration(this.file);
    }
    
    public void load() {
    	 this.file = new File(this.plugin.getDataFolder(), "config.yml");
         this.configuration = YamlConfiguration.loadConfiguration(this.file);
    }
    
    public YamlConfiguration getConfiguration() {
    	return this.configuration;
    }
    
    public double getDouble(String path) {
        if (this.configuration.contains(path)) {
            return this.configuration.getDouble(path);
        }
        return 0.0;
    }
    
    public int getInt(String path) {
        if (this.configuration.contains(path)) {
            return this.configuration.getInt(path);
        }
        return 0;
    }
    
    public boolean getBoolean(String path) {
        return this.configuration.contains(path) && this.configuration.getBoolean(path);
    }
    
    public String getString(String path) {
        if (this.configuration.contains(path)) {
            return ChatColor.translateAlternateColorCodes('&', this.configuration.getString(path));
        }
        return "String at path: " + path + " not found!";
    }
    
    public List<String> getStringList(String path) {
        if (this.configuration.contains(path)) {
            final ArrayList<String> strings = new ArrayList<String>();
            for (final String string : this.configuration.getStringList(path)) {
                strings.add(ChatColor.translateAlternateColorCodes('&', string));
            }
            return strings;
        }
        return Arrays.asList("String List at path: " + path + " not found!");
    }
}
