package net.maattah.flare;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import net.maattah.flare.commands.FlareCommand;
import net.maattah.flare.commands.chat.ClearchatCommand;
import net.maattah.flare.commands.chat.LockCommand;
import net.maattah.flare.commands.player.CoordsCommand;
import net.maattah.flare.commands.player.HelpCommand;
import net.maattah.flare.commands.player.OresCommand;
import net.maattah.flare.commands.player.PvpCommand;
import net.maattah.flare.commands.player.ReclaimCommand;
import net.maattah.flare.commands.player.ReportCommand;
import net.maattah.flare.commands.player.RequestCommand;
import net.maattah.flare.commands.player.StatsCommand;
import net.maattah.flare.commands.staff.FlyCommand;
import net.maattah.flare.commands.staff.FreezeCommand;
import net.maattah.flare.commands.staff.GMCCommand;
import net.maattah.flare.commands.staff.GMSCommand;
import net.maattah.flare.commands.staff.TPCommand;
import net.maattah.flare.commands.staff.TPHereCommand;
import net.maattah.flare.commands.staff.TpposCommand;
import net.maattah.flare.commands.staff.VanishCommand;
import net.maattah.flare.config.ConfigFile;
import net.maattah.flare.config.ConfigHandler;
import net.maattah.flare.config.DeathBanFile;
import net.maattah.flare.deathban.DeathBanHandler;
import net.maattah.flare.deathban.commands.DeathBanCommand;
import net.maattah.flare.deathban.commands.LivesCommand;
import net.maattah.flare.listeners.ChatHandler;
import net.maattah.flare.listeners.CombatTagListener;
import net.maattah.flare.listeners.EnderPearlListener;
import net.maattah.flare.listeners.FreezeHandler;
import net.maattah.flare.listeners.JoinHandler;
import net.maattah.flare.listeners.PVPTimerListener;
import net.maattah.flare.listeners.StatHandler;
import net.maattah.flare.scoreboard.ScoreboardObject;
import net.maattah.flare.scoreboard.ScoreboardObjectHandler;
import net.maattah.flare.staff.VanishHandler;
import net.maattah.flare.utils.FlarePlayer;
import net.maattah.flare.utils.Lang;
import net.maattah.flare.utils.StringUtils;
import net.maattah.flare.utils.Utils;
import net.maattah.flare.utils.Vault;
import net.maattah.flare.utils.WorldGuard;
import net.milkbowl.vault.chat.Chat;

public class Main extends JavaPlugin {
	public static Main instance;
	public static Chat chat = null;
	public static YamlConfiguration lang;
	public static File langFile;
	private ConfigFile configFile;
	private ConfigHandler configHandler;
	private DeathBanFile deathBanFile;
	private DeathBanHandler deathBanHandler;
	private PVPTimerListener pvpTimerHandler;
	private ScoreboardObjectHandler scoreboardDataHandler;
	private VanishHandler vanishHandler;
	public static ArrayList<String> donators = new ArrayList<String>();
	public File rdYML = new File(getDataFolder() + File.separator + "reclaim" + File.separator + "data.yml");
	public FileConfiguration redeemed = YamlConfiguration.loadConfiguration(this.rdYML);
	
	public Main() {
		this.configHandler = new ConfigHandler(this);
		this.scoreboardDataHandler = new ScoreboardObjectHandler(this);
		this.deathBanHandler = new DeathBanHandler(this);
		this.pvpTimerHandler = new PVPTimerListener(this);
		this.vanishHandler = new VanishHandler(this);
	}
	
	public void onEnable() {
		instance = this;
		Utils.start();
		FreezeHandler.frozen = new ArrayList<String>();
		saveDefaultConfig();
		Lang.loadLang();
		Vault.perms();
		WorldGuard.setup();
		this.setupChat();
		this.saveDataTimer();
		this.setupDirectories();
		this.registerCommands();
		this.registerListeners();
		this.scoreboardDataHandler.enable();
		this.setupScoreboard();
		this.deathBanFile = new DeathBanFile();
		this.deathBanHandler.enable();
		this.configFile = new ConfigFile();
		this.configHandler.enable();
		this.pvpTimerHandler.enable();
		this.vanishHandler.enable();
	}
	
	public void onDisable() {
		this.deathBanHandler.disable();
		donators.clear();
		instance = null;
	}
	
	public void registerCommands() {
		getCommand("flare").setExecutor(new FlareCommand());
		getCommand("lock").setExecutor(new LockCommand());
		getCommand("l").setExecutor(new LockCommand());
		getCommand("cc").setExecutor(new ClearchatCommand());
		getCommand("clearchat").setExecutor(new ClearchatCommand());
		getCommand("ores").setExecutor(new OresCommand());
		getCommand("fly").setExecutor(new FlyCommand());
		getCommand("gmc").setExecutor(new GMCCommand());
		getCommand("gms").setExecutor(new GMSCommand());
		getCommand("stats").setExecutor(new StatsCommand());
		getCommand("tp").setExecutor(new TPCommand());
		getCommand("tphere").setExecutor(new TPHereCommand());
		getCommand("tppos").setExecutor(new TpposCommand());
		getCommand("ss").setExecutor(new FreezeCommand());
		getCommand("freeze").setExecutor(new FreezeCommand());
		getCommand("report").setExecutor(new ReportCommand());
        getCommand("request").setExecutor(new RequestCommand());
        getCommand("helpop").setExecutor(new RequestCommand());
        getCommand("lives").setExecutor(new LivesCommand());
        getCommand("deathban").setExecutor(new DeathBanCommand());
        getCommand("help").setExecutor(new HelpCommand());
        getCommand("coords").setExecutor(new CoordsCommand());
        getCommand("v").setExecutor(new VanishCommand());
        getCommand("vanish").setExecutor(new VanishCommand());
        getCommand("pvp").setExecutor(new PvpCommand(this));
        getCommand("reclaim").setExecutor(new ReclaimCommand());
	}
	
	public void registerListeners() {
		Bukkit.getServer().getPluginManager().registerEvents(new StatHandler(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new ChatHandler(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new JoinHandler(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new FreezeHandler(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new CombatTagListener(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new EnderPearlListener(), this);
		//Bukkit.getServer().getPluginManager().registerEvents(new StaffListener(), this);
	}
	
	private boolean setupChat() {
        RegisteredServiceProvider<Chat> rsp = getServer().getServicesManager().getRegistration(Chat.class);
        chat = rsp.getProvider();
        return chat != null;
    }
	
	public void saveDataTimer() {
		new BukkitRunnable() {
			@SuppressWarnings("deprecation")
			@Override
			public void run() {
				if(getConfig().getBoolean("groupmsg-enabled")) {
				donators.clear();
					for(Player player : Bukkit.getOnlinePlayers()) {
						if(Vault.getPlayerGroup(player).equalsIgnoreCase(getConfig().getString("permissions.groupmsg-group"))) {
							donators.add(player.getName());
						}
					}
					String players = String.join(", ", donators);
					Bukkit.getServer().broadcastMessage(getConfig().getString("groupmsg-message") + players);
				}
			}
		}.runTaskTimer(Main.getInstance(), 1200, 1200 * 5);
	}
	
	public void reload() {
		if (!getDataFolder().exists()) {
			getDataFolder().mkdirs();
		 }
		 
		 File file = new File(getDataFolder(), "config.yml");
		 if (!file.exists()) {
			 saveDefaultConfig();
		 } else {
			 reloadConfig();
		 }
		 Lang.loadLang();
	}
	
	public void setupDirectories() {
		if (!this.getDataFolder().exists()) {
			this.getDataFolder().mkdir();
		}

		File deathban = new File(this.getDataFolder(), "deathban");
		if (!deathban.exists()) {
			deathban.mkdir();
		}

		File deathbans = new File(deathban, "deathbans");
		if (!deathbans.exists()) {
			deathbans.mkdir();
		}

		File inventories = new File(deathban, "inventories");
		if (!inventories.exists()) {
			inventories.mkdir();
		}

		File lives = new File(deathban, "lives");
		if (!lives.exists()) {
			lives.mkdir();
		}
	}
	
	public void setupScoreboard() {
		new BukkitRunnable() {
			@SuppressWarnings("deprecation")
			public void run() {
				for (Player player : Bukkit.getServer().getOnlinePlayers()) {
					FlarePlayer user = new FlarePlayer(player.getUniqueId());
					int epearl = (int) ((double) EnderPearlListener.getMillisecondLeft(player) / 1000.0D);
					int spawnTag = (int) ((double) CombatTagListener.getMillisecondLeft(player) / 1000.0D);
					ScoreboardObject scoreboard = scoreboardDataHandler.getScoreboardFor(player);
					scoreboard.clear();
					if(player.hasPermission(Main.getNode() + ".staffsb") && getConfig().getBoolean("scoreboard.staffboard-enabled") || user.getPvPTime() > 0 || spawnTag != 0 || epearl != 0) {
						scoreboard.add(getConfig().getString("scoreboard.line"));
					}
					if(player.hasPermission(Main.getNode() + ".staffsb") && getConfig().getBoolean("scoreboard.staffboard-enabled")) {
						scoreboard.add(getConfig().getString("scoreboard.staff"));
						if(VanishHandler.vanished.contains(player.getUniqueId())) {
							scoreboard.add(getConfig().getString("scoreboard.visibility").replaceAll("%arg%", "&aVanished"));
						} else {
							scoreboard.add(getConfig().getString("scoreboard.visibility").replaceAll("%arg%", "&cVisible"));
						}
						if(player.getGameMode() == GameMode.CREATIVE) {
							scoreboard.add(getConfig().getString("scoreboard.gamemode").replaceAll("%arg%", "&aCreative"));
						} else if (player.getGameMode() == GameMode.SURVIVAL) {
							scoreboard.add(getConfig().getString("scoreboard.gamemode").replaceAll("%arg%", "&cSurvival"));
						}
						if(LockCommand.chatEnabled) {
							scoreboard.add(getConfig().getString("scoreboard.chat").replaceAll("%arg%", "&aUnlocked"));
						} else if(!LockCommand.chatEnabled) {
							scoreboard.add(getConfig().getString("scoreboard.chat").replaceAll("%arg%", "&cLocked"));
						}
						scoreboard.add(getConfig().getString("scoreboard.line"));
					}
					
					 if(spawnTag != 0) {
						 // Change the replacement to make the format 00:30
						 scoreboard.add(getConfig().getString("scoreboard.combat").replaceAll("%arg%", StringUtils.formatMilisecondsToSeconds(CombatTagListener.getMillisecondLeft(player)) + "s"));
					 }
					
					 if(epearl != 0) {
						scoreboard.add(getConfig().getString("scoreboard.enderpearl").replaceAll("%arg%", StringUtils.formatMilisecondsToSeconds(EnderPearlListener.getMillisecondLeft(player)) + "s"));
					 }
					
					if(user.getPvPTime() > 0) {
						scoreboard.add(getConfig().getString("scoreboard.pvptimer").replaceAll("%arg%", StringUtils.formatSecondsToMinutes(user.getPvPTime())));
					}
					
					// if(KothHandler.isKothRunning) {
						//scoreboard.add(getConfig().getString("scoreboard.koth").replaceAll("%kothname%", "Test").replaceAll("%arg%", KothHandler.getTime()));
					// }
					
					// if(LogoutHandler.logging.contains(player.getUniqueId()) {
						//scoreboard.add(getConfig().getString("scoreboard.logout").replaceAll("%arg%", LogoutHandler.getTime(player));
					// }
					
					// if(GappleCooldown.contains(player.getUniqueId()) {
						//scoreboard.add(getConfig().getString("scoreboard.gapple").replaceAll("%arg%", "6:00:00"));
					// }
					
					// if(HomeCommand.waiting.contains(player.getUniqueId()) {
						//scoreboard.add(getConfig().getString("scoreboard.home").replaceAll("%arg%", "10.0"));
					// }
					
					// if(StuckCommand.waiting.contains(player.getUniqueId()) {
						//scoreboard.add(getConfig().getString("scoreboard.stuck").replaceAll("%arg%", "2:30"));
					// }
					
					if(user.getPvPTime() > 0 || spawnTag != 0 || epearl != 0) {
						scoreboard.add(getConfig().getString("scoreboard.line"));
					}

					scoreboard.update(player);
				}
			}
		}.runTaskTimer(this, 2L, 2L);
	}
	
	public static Main getInstance() {
		return instance;
	}
	
	public static String getNode() {
		return Main.getInstance().getConfig().getString("permissions.node");
	}
	
	public YamlConfiguration getLang() {
	    return lang;
	}
	
	public File getLangFile() {
	    return langFile;
	}
	
	public ConfigFile getConfigFile() {
		return this.configFile;
	}

	public ConfigHandler getConfigHandler() {
		return this.configHandler;
	}

	public DeathBanFile getDeathBanFile() {
		return this.deathBanFile;
	}
	
	public void saveCustomYml(FileConfiguration ymlConfig, File ymlFile) {
		try {
			ymlConfig.save(ymlFile);
		}
		
		catch (IOException e) {
			e.printStackTrace();
		}
	}

}