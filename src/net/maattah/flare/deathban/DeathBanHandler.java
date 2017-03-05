package net.maattah.flare.deathban;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.time.DurationFormatUtils;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import net.maattah.flare.Main;
import net.maattah.flare.deathban.commands.DeathBanCommand;
import net.maattah.flare.utils.Handler;
import net.maattah.flare.utils.InventoryUtils;
import net.maattah.flare.utils.Lang;
import net.maattah.flare.utils.StringUtils;
import net.maattah.flare.utils.Vault;

public class DeathBanHandler extends Handler implements Listener {
	
	private File deathbanFolder;
	private File deathbansFolder;
	private File inventoriesFolder;
	private File livesFolder;
	
	private List<DeathBanTime> deathBanTimes;
	private List<UUID> playerConfirmation;
	
	public DeathBanHandler(Main plugin) {
		super(plugin);
		this.deathBanTimes = new ArrayList<DeathBanTime>();
		this.playerConfirmation = new ArrayList<UUID>();
	}
	
	public void enable() {
		this.deathbanFolder = new File(Main.getInstance().getDataFolder(), "deathban");
		this.deathbansFolder = new File(this.deathbanFolder, "deathbans");
		this.inventoriesFolder = new File(this.deathbanFolder, "inventories");
		this.livesFolder = new File(this.deathbanFolder, "lives");
		this.loadDeathBanTimes();
		this.getInstance().getServer().getPluginManager().registerEvents(this, this.getInstance());
		this.getInstance().getServer().getPluginManager().registerEvents(new DeathBanCommand(), this.getInstance());
	}
	
	public void disable() {
		this.deathBanTimes.clear();
		this.playerConfirmation.clear();
	}
	
	public void loadDeathBanTimes() {
		ConfigurationSection section = this.getInstance().getDeathBanFile().getConfiguration().getConfigurationSection("deathban-times");
		for(String rank : section.getKeys(false)) {
			DeathBanTime deathBanTime = new DeathBanTime();
			deathBanTime.setGroup(rank);
			deathBanTime.setTime(section.getInt(rank + ".time"));
			
			this.deathBanTimes.add(deathBanTime);
		}
	}
	
	public void banPlayer(Player player, PlayerDeathEvent event) {
		if(this.getInstance().getConfigHandler().isDeathbanEnabled()) {
			File file = new File(this.deathbansFolder, player.getUniqueId().toString() + ".yml");
			if (!file.exists()) {
				try {
					file.createNewFile();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
			Location location = event.getEntity().getLocation();
			YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
			configuration.set("ban_until", Long.valueOf(System.currentTimeMillis() + this.getBanTime(player) * 60 * 1000L));
			configuration.set("death_message", event.getDeathMessage());
			configuration.set("coords", StringUtils.getWorldName(location) + ", " + location.getBlockX() + ", " + location.getBlockY() + ", " + location.getBlockZ());
			try {
				configuration.save(file);
			} catch (IOException ex2) {
				ex2.printStackTrace();
			}
			
			File invFile = new File(this.inventoriesFolder, player.getUniqueId() + ".yml");
			if (!invFile.exists()) {
				try {
					invFile.createNewFile();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
			YamlConfiguration configurationInv = YamlConfiguration.loadConfiguration(invFile);
			configurationInv.set("inventory", InventoryUtils.itemStackArrayToBase64(player.getInventory().getContents()));
			configurationInv.set("armor", InventoryUtils.itemStackArrayToBase64(player.getInventory().getArmorContents()));
			configurationInv.set("used", Boolean.valueOf(false));
			try {
				configurationInv.save(invFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void banVillager(Player player, Villager villager, EntityDeathEvent event) {
		if(this.getInstance().getConfigHandler().isDeathbanEnabled()) {
			File file = new File(this.deathbansFolder, player.getUniqueId().toString() + ".yml");
			if (!file.exists()) {
				try {
					file.createNewFile();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
			Location location = event.getEntity().getLocation();
			YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
			configuration.set("ban_until", Long.valueOf(System.currentTimeMillis() + this.getBanTime(player) * 60 * 1000L));
			configuration.set("death_message", "CombatLogger");
			configuration.set("coords", StringUtils.getWorldName(location) + ", " + location.getBlockX() + ", " + location.getBlockY() + ", " + location.getBlockZ());
			try {
				configuration.save(file);
			} catch (IOException ex2) {
				ex2.printStackTrace();
			}
			
			File invFile = new File(this.inventoriesFolder, player.getUniqueId() + ".yml");
			if (!invFile.exists()) {
				try {
					invFile.createNewFile();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
			
			ItemStack[] contents = (ItemStack[]) villager.getMetadata("Contents").get(0).value();
			ItemStack[] armor = (ItemStack[]) villager.getMetadata("Armor").get(0).value();
			
			YamlConfiguration configurationInv = YamlConfiguration.loadConfiguration(invFile);
			configurationInv.set("inventory", InventoryUtils.itemStackArrayToBase64(contents));
			configurationInv.set("armor", InventoryUtils.itemStackArrayToBase64(armor));
			configurationInv.set("used", Boolean.valueOf(false));
			try {
				configurationInv.save(invFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public long getBanTime(Player player) {
		long banTime = 0L;
		for(DeathBanTime deathBanTime : this.deathBanTimes) {
			if(Vault.getPlayerGroup(player).equalsIgnoreCase(deathBanTime.getGroup())) {
				banTime = deathBanTime.getTime();
			}
		}
		return banTime;
	}

	public void kickPlayer(Player player) {
		if(this.getInstance().getConfigHandler().isDeathbanEnabled()) {
			for(DeathBanTime deathBanTime : this.deathBanTimes) {
				if(Vault.getPlayerGroup(player).equalsIgnoreCase(deathBanTime.getGroup())) {
					int banTime = deathBanTime.getTime();
					player.kickPlayer(Lang.DEATHBAN_BAN_MESSAGE.toString().replace("<time>", DurationFormatUtils.formatDurationWords(banTime * 60L * 1000L, true, true)));
				}
			}
		}
	}
	
	public int getLives(Player player) {
		File file = new File(this.livesFolder, player.getUniqueId() + ".yml");
		YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
		int lives = configuration.getInt("lives");
		return lives;
	}
	
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
		Player player = event.getEntity();
		this.banPlayer(player, event);
		new BukkitRunnable() {
			public void run() {
				kickPlayer(player);
			}
		}.runTaskLater(Main.getInstance(), 10L);
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();

		File file = new File(this.livesFolder, player.getUniqueId() + ".yml");
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
			configuration.set("lives", Integer.valueOf(0));
			try {
				configuration.save(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	@EventHandler
	public void onPlayerLogin(PlayerLoginEvent event) {
		Player player = event.getPlayer();
		
		File file = new File(this.deathbansFolder, player.getUniqueId() + ".yml");
		if(file.exists()) {
			YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
			long banTime = configuration.getLong("ban_until");

			if(System.currentTimeMillis() < banTime) {
				if(this.getLives(player) <= 0) {
					event.setKickMessage(Lang.DEATHBAN_BAN_MESSAGE.toString().replace("<time>", DurationFormatUtils.formatDurationWords(banTime - System.currentTimeMillis(), true, true)));
					event.setResult(PlayerLoginEvent.Result.KICK_BANNED);
				} else {
					if(this.playerConfirmation.contains(player.getUniqueId())) {
						File newFile = new File(this.livesFolder, player.getUniqueId() + ".yml");
						YamlConfiguration newConfiguration = YamlConfiguration.loadConfiguration(newFile);
						int lives = newConfiguration.getInt("lives");
						newConfiguration.set("lives", Integer.valueOf(lives - 1));
						try {
							newConfiguration.save(newFile);
						} catch (IOException e) {
							e.printStackTrace();
						}
						file.delete();
						this.playerConfirmation.remove(player.getUniqueId());
					} else {
						event.setKickMessage(Lang.DEATHBAN_BAN_MESSAGE.toString().replace("<time>", DurationFormatUtils.formatDurationWords(banTime - System.currentTimeMillis(), true, true)) 
						+ "\n" + "\n" + Lang.DEATHBAN_JOIN_AGAIN_FOR_REVIVE.toString().replace("<amount>", String.valueOf(this.getLives(player))));
						event.setResult(PlayerLoginEvent.Result.KICK_BANNED);
						this.playerConfirmation.add(player.getUniqueId());
					}
				}
			} else {
				file.delete();
				this.playerConfirmation.remove(player.getUniqueId());
			}
		}
	}
	
	public class DeathBanTime {
		
		private String group;
		private int time;
		
		public DeathBanTime() { }

		public String getGroup() {
			return group;
		}

		public void setGroup(String group) {
			this.group = group;
		}

		public int getTime() {
			return time;
		}

		public void setTime(int time) {
			this.time = time;
		}
	}
}
