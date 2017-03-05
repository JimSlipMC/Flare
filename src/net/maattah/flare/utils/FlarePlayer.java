package net.maattah.flare.utils;

import org.bukkit.entity.Player;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.file.FileConfiguration;
import java.io.File;
import java.util.UUID;
import org.bukkit.event.Listener;

import net.maattah.flare.Main;

public class FlarePlayer implements Listener {
    private UUID u;
    private File userFile;
    private FileConfiguration userConfig;
    
    public FlarePlayer(final UUID u) {
        this.setU(u);
        this.userFile = new File(Main.getInstance().getDataFolder() + File.separator + "users", u + ".yml");
        this.userConfig = YamlConfiguration.loadConfiguration(this.userFile);
    }
    
    public void createUser(final Player player) {
        if (!this.userFile.exists()) {
            try {
                final YamlConfiguration userConfig = YamlConfiguration.loadConfiguration(this.userFile);
                userConfig.set("kills", 0);
                userConfig.set("deaths", 0);
                userConfig.set("playtime", 0);
                userConfig.set("ip", player.getAddress().getAddress().toString().replace("/", ""));
                userConfig.set("ores.coal", 0);
                userConfig.set("ores.iron", 0);
                userConfig.set("ores.redstone", 0);
                userConfig.set("ores.lapis", 0);
                userConfig.set("ores.gold", 0);
                userConfig.set("ores.diamonds", 0);
                userConfig.set("ores.emeralds", 0);
                userConfig.set("timers.pvp", Main.getInstance().getConfig().getInt("pvptimer.duration"));
                userConfig.set("timers.gapple", 0);
                userConfig.set("firstJoin", true);
                userConfig.save(this.userFile);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    public FileConfiguration getUserFile() {
        return this.userConfig;
    }
    
    public void saveUserFile() {
        try {
            this.getUserFile().save(this.userFile);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public int getKills() {
    	return this.userConfig.getInt("kills");
    }
    
    public int getDeaths() {
    	return this.userConfig.getInt("deaths");
    }
    
    public void setKills(int i) {
    	this.userConfig.set("kills", i);
    	this.saveUserFile();
    }
    
    public void setDeaths(int i) {
    	this.userConfig.set("deaths", i);
    	this.saveUserFile();
    }
    
    public String getIp() {
    	return this.userConfig.getString("ip");
    }
    
    public int getCoal() {
    	return this.userConfig.getInt("ores.coal");
    }
    
    public int getIron() {
    	return this.userConfig.getInt("ores.iron");
    }
    
    public int getRedstone() {
    	return this.userConfig.getInt("ores.redstone");
    }
    
    public int getLapis() {
    	return this.userConfig.getInt("ores.lapis");
    }
    
    public int getGold() {
    	return this.userConfig.getInt("ores.gold");
    }
    
    public int getDiamonds() {
    	return this.userConfig.getInt("ores.diamonds");
    }
    
    public int getEmeralds() {
    	return this.userConfig.getInt("ores.emeralds");
    }
    
    public void setCoal(int i) {
    	this.userConfig.set("ores.coal", i);
    	this.saveUserFile();
    }
    
    public void setIron(int i) {
    	this.userConfig.set("ores.iron", i);
    	this.saveUserFile();
    }
    
    public void setRedstone(int i) {
    	this.userConfig.set("ores.redstone", i);
    	this.saveUserFile();
    }
    
    public void setLapis(int i) {
    	this.userConfig.set("ores.lapis", i);
    	this.saveUserFile();
    }
    
    public void setGold(int i) {
    	this.userConfig.set("ores.gold", i);
    	this.saveUserFile();
    }
    
    public void setDiamonds(int i) {
    	this.userConfig.getInt("ores.diamonds", i);
    	this.saveUserFile();
    }
    
    public void setEmeralds(int i) {
    	this.userConfig.set("ores.emeralds", i);
    	this.saveUserFile();
    }
    
    public int getPvPTime() {
    	return this.userConfig.getInt("timers.pvp");
    }
    
    public void setPvPTime(int i) {
    	this.userConfig.set("timers.pvp", i);
    	this.saveUserFile();
    }

	public UUID getU() {
		return u;
	}

	public void setU(UUID u) {
		this.u = u;
	}
    
}