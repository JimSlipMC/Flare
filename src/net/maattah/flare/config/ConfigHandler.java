package net.maattah.flare.config;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;

import net.maattah.flare.Main;
import net.maattah.flare.utils.Handler;

public class ConfigHandler extends Handler {
	
	private boolean deathban_enabled;
	
	private List<String> deathban_usage_message;
	private List<String> deathban_check_message;
	private List<String> lives_usage_message;
	
	private int pvp_timer_duration;
	private List<String> pvp_timer_disabled_commands;
	private List<Material> pvp_timer_disabled_items = new ArrayList<Material>();
	private List<String> glass_protected_regions;
	
	public ConfigHandler(Main plugin) {
		super(plugin);
	}

	@SuppressWarnings("deprecation")
	public void enable() {
		this.deathban_enabled = this.getInstance().getDeathBanFile().getBoolean("deathban-enabled");
		
		this.deathban_usage_message = this.getInstance().getConfig().getStringList("deathban-usage-message");
		this.deathban_check_message = this.getInstance().getConfig().getStringList("deathban-check-message");
		this.lives_usage_message = this.getInstance().getConfig().getStringList("lives-usage-message");
	
		this.setPvp_timer_duration(this.getInstance().getConfigFile().getInt("pvptimer.duration"));
		this.pvp_timer_disabled_commands = this.getInstance().getConfigFile().getStringList("pvptimer.disabled-commands");
		this.pvp_timer_disabled_items.clear();
		for(String material : this.getInstance().getConfigFile().getStringList("pvptimer.disabled-items")) {
			this.pvp_timer_disabled_items.add(Material.getMaterial(Integer.valueOf(material)));
		}
		this.glass_protected_regions = this.getInstance().getConfigFile().getStringList("pvptimer.protected-regions");
	}


	public boolean isDeathbanEnabled() {
		return deathban_enabled;
	}

	public List<String> getDeathbanUsageMessage() {
		return deathban_usage_message;
	}

	public List<String> getDeathbanCheckMessage() {
		return deathban_check_message;
	}

	public List<String> getLivesUsageMessage() {
		return lives_usage_message;
	}
	
	public List<String> getPvpTimerDisabledCommands() {
		return pvp_timer_disabled_commands;
	}
	
	public List<Material> getPvPTimerDisabledItems() {
		return pvp_timer_disabled_items;
	}
	
	public List<String> getGlassProtectedRegions() {
		return glass_protected_regions;
	}

	public int getPvp_timer_duration() {
		return pvp_timer_duration;
	}

	public void setPvp_timer_duration(int pvp_timer_duration) {
		this.pvp_timer_duration = pvp_timer_duration;
	}
}