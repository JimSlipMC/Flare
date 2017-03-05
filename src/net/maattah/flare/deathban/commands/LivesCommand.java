package net.maattah.flare.deathban.commands;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import net.maattah.flare.Main;
import net.maattah.flare.utils.Lang;
import net.maattah.flare.utils.NumberUtils;
import net.md_5.bungee.api.ChatColor;

public class LivesCommand implements CommandExecutor {
	
	private File deathbanFolder = new File(Main.getInstance().getDataFolder(), "deathban");
	private File deathbansFolder = new File(this.deathbanFolder, "deathbans");
	private File livesFolder = new File(this.deathbanFolder, "lives");

	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd, String Commandlabel, String[] args) {
		if(cmd.getName().equalsIgnoreCase("lives")) {
			if(sender instanceof Player) {
				Player player = (Player) sender;
				if(args.length == 0) {
					sendUsage(player);
				} else if(args.length == 1) {
					if (args[0].equals("check")) {
						player.sendMessage(Lang.PREFIX.toString() + Lang.LIVES_COMMAND_CHECK_SELF.toString().replace("<lives>", String.valueOf(getLives(player))));
					} else if(args[0].equals("revive")) {
						player.sendMessage(Lang.PREFIX.toString() + Lang.LIVES_COMMAND_REVIVE_USAGE.toString());
					} else if(args[0].equals("send")) {
						player.sendMessage(Lang.PREFIX.toString() + Lang.LIVES_COMMAND_SEND_USAGE.toString());
					} else {
						this.sendUsage(player);
					}
				} else if(args.length == 2) {
					if(args[0].equals("check")) {
						Player target = Bukkit.getPlayer(args[1]);
						if(target != null) {
							player.sendMessage(Lang.PREFIX.toString() + Lang.LIVES_COMMAND_CHECK_OTHERS.toString().replace("<player>", target.getName()).replace("<lives>", String.valueOf(getLives(target))));
						} else {
							player.sendMessage(Lang.PREFIX.toString() + Lang.LIVES_COMMAND_PLAYER_NOT_ONLINE.toString().replace("<player>", args[1]));
						}
					} else if(args[0].equals("revive")) {
						OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
						revivePlayer(player, target);
					} else if(args[0].equals("send")) {
						player.sendMessage(Lang.PREFIX.toString() + Lang.LIVES_COMMAND_SEND_USAGE.toString());
					} else {
						this.sendUsage(player);
					}
				} else if(args.length == 3) {
					if(args[0].equals("send")) {
						Player target = Bukkit.getPlayer(args[1]);
						if(target != null) {
							if(target != player) {
								if(NumberUtils.isInteger(args[2])) {
									int amount = Integer.parseInt(args[2]);
									sendLives(player, target, amount);
								} else {
									player.sendMessage(Lang.PREFIX.toString() + Lang.INVALID_NUMBER.toString());
								}
							} else {
								player.sendMessage(Lang.PREFIX.toString() + Lang.LIVES_COMMAND_CAN_NOT_SEND_LIVES_TO_YOURSELF.toString());
							}
						} else {
							player.sendMessage(Lang.PREFIX.toString() + Lang.LIVES_COMMAND_PLAYER_NOT_ONLINE.toString().replace("<player>", args[1]));
						}
					} else if(args[0].equals("set")) {
						if(player.hasPermission(Main.getNode() + ".lives.set")) {
							Player target = Bukkit.getPlayer(args[1]);
							if(target != null) {
								if(NumberUtils.isInteger(args[2])) {
									int amount = Integer.parseInt(args[2]);
									setLives(player, target, amount);
								} else {
									player.sendMessage(Lang.PREFIX.toString() + Lang.INVALID_NUMBER.toString());
								}

							} else if(NumberUtils.isInteger(args[2])) {
								OfflinePlayer offline = Bukkit.getOfflinePlayer(args[1]);
								int amount = Integer.parseInt(args[2]);
								setLives(player, offline, amount);
							} else {
								player.sendMessage(Lang.PREFIX.toString() + Lang.INVALID_NUMBER.toString());
							}
						} else {
							player.sendMessage(Lang.NO_PERMS.toString());
						}
					} else if (args[0].equals("add")) {
						if(player.hasPermission(Main.getNode() + ".lives.add")) {
							Player target = Bukkit.getPlayer(args[1]);
							if(target != null) {
								if(NumberUtils.isInteger(args[2])) {
									int amount = Integer.parseInt(args[2]);
									addLives(player, target, amount);
								} else {
									player.sendMessage(Lang.PREFIX.toString() + Lang.INVALID_NUMBER.toString());
								}

							} else if(NumberUtils.isInteger(args[2])) {
								OfflinePlayer offline = Bukkit.getOfflinePlayer(args[1]);
								int amount = Integer.parseInt(args[2]);
								addLives(player, offline, amount);
							} else {
								player.sendMessage(Lang.PREFIX.toString() + Lang.INVALID_NUMBER.toString());
							}
						} else {
							player.sendMessage(Lang.NO_PERMS.toString());
						}
					} else {
						this.sendUsage(player);
					}
				} else {
					this.sendUsage(player);
				}
			} else if(sender instanceof ConsoleCommandSender) {
				if(args.length == 0) {
					sendUsage(sender);
				} else if(args.length == 1) {
					if (args[0].equals("check")) {
						sender.sendMessage(Lang.PLAYER_ONLY.toString());
					} else if(args[0].equals("revive")) {
						sender.sendMessage(Lang.PLAYER_ONLY.toString());
					} else if(args[0].equals("send")) {
						sender.sendMessage(Lang.PLAYER_ONLY.toString());
					} else {
						this.sendUsage(sender);
					}
				} else if(args.length == 2) {
					if(args[0].equals("check")) {
						Player target = Bukkit.getPlayer(args[1]);
						if(target != null) {
							sender.sendMessage(Lang.PREFIX.toString() + Lang.LIVES_COMMAND_CHECK_OTHERS.toString().replace("<player>", String.valueOf(getLives(target))));
						} else {
							sender.sendMessage(Lang.PREFIX.toString() + Lang.LIVES_COMMAND_PLAYER_NOT_ONLINE.toString().replace("<player>", args[1]));
						}
					} else if(args[0].equals("revive")) {
						sender.sendMessage(Lang.PLAYER_ONLY.toString());
					} else if(args[0].equals("send")) {
						sender.sendMessage(Lang.PLAYER_ONLY.toString());
					} else {
						this.sendUsage(sender);
					}
				} else if(args.length == 3) {
					if(args[0].equals("send")) {
						sender.sendMessage(Lang.PLAYER_ONLY.toString());
					} else if(args[0].equals("set")) {
						Player target = Bukkit.getPlayer(args[1]);
						if(target != null) {
							if(NumberUtils.isInteger(args[2])) {
								int amount = Integer.parseInt(args[2]);
								setLives(sender, target, amount);
							} else {
								sender.sendMessage(Lang.PREFIX.toString() + Lang.INVALID_NUMBER.toString());
							}

						} else if(NumberUtils.isInteger(args[2])) {
							OfflinePlayer offline = Bukkit.getOfflinePlayer(args[1]);
							int amount = Integer.parseInt(args[2]);
							setLives(sender, offline, amount);
						} else {
							sender.sendMessage(Lang.PREFIX.toString() + Lang.INVALID_NUMBER.toString());
						}

					} else if(args[0].equals("add")) {
						Player target = Bukkit.getPlayer(args[1]);
						if(target != null) {
							if(NumberUtils.isInteger(args[2])) {
								int amount = Integer.parseInt(args[2]);
								addLives(sender, target, amount);
							} else {
								sender.sendMessage(Lang.PREFIX.toString() + Lang.INVALID_NUMBER.toString());
							}
						} else if(NumberUtils.isInteger(args[2])) {
							OfflinePlayer offline = Bukkit.getOfflinePlayer(args[1]);
							int amount = Integer.parseInt(args[2]);
							addLives(sender, offline, amount);
						} else {
							sender.sendMessage(Lang.PREFIX.toString() + Lang.INVALID_NUMBER.toString());
						}
					} else {
						this.sendUsage(sender);
					}
				} else {
					this.sendUsage(sender);
				}
			}
		}

		return true;
	}

	public int getLives(Player player) {
		File file = new File(this.livesFolder, player.getUniqueId().toString() + ".yml");
		YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
		int lives = configuration.getInt("lives");
		return lives;
	}

	public void revivePlayer(Player player, OfflinePlayer target) {
		File file = new File(this.livesFolder, player.getUniqueId() + ".yml");
		YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
		int lives = configuration.getInt("lives");
		if (lives > 0) {
			File targetFile = new File(this.deathbansFolder, target.getUniqueId().toString() + ".yml");
			if (targetFile.exists()) {
				targetFile.delete();
				configuration.set("lives", Integer.valueOf(lives - 1));
				try {
					configuration.save(file);
				} catch (IOException e) {
					e.printStackTrace();
				}
				player.sendMessage(Lang.PREFIX.toString() + Lang.LIVES_COMMAND_SUCCESSFULLY_REVIVED_PLAYER.toString().replace("<player>", target.getName()));
			} else {
				player.sendMessage(Lang.PREFIX.toString() + Lang.DEATHBAN_PLAYER_NOT_DEATHBANNED.toString().replace("<player>", target.getName()));
			}
		} else {
			player.sendMessage(Lang.PREFIX.toString() + Lang.LIVES_COMMAND_ZERO_LIVES.toString());
		}
	}

	public void sendLives(Player player, Player target, int amount) {
		File file = new File(this.livesFolder, player.getUniqueId() + ".yml");
		YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
		int lives = configuration.getInt("lives");
		if(lives >= amount) {
			File targetFile = new File(this.livesFolder, target.getUniqueId() + ".yml");
			if (targetFile.exists()) {
				configuration.set("lives", Integer.valueOf(lives - amount));
				YamlConfiguration targetConfiguration = YamlConfiguration.loadConfiguration(targetFile);
				targetConfiguration.set("lives", Integer.valueOf(targetConfiguration.getInt("lives") + amount));
				player.sendMessage(Lang.PREFIX.toString() + Lang.LIVES_COMMAND_SUCCESSFULLY_SENT_LIVES.toString().replace("<amount>", String.valueOf(amount)).replace("<player>", target.getName()));
				try {
					configuration.save(file);
					targetConfiguration.save(targetFile);
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				player.sendMessage(Lang.PREFIX.toString() + Lang.LIVES_COMMAND_PLAYER_NOT_IN_DATABASE.toString().replace("<player>", target.getName()));
			}
		} else {
			player.sendMessage(Lang.PREFIX.toString() + Lang.LIVES_COMMAND_NOT_ENOUGH_LIVES.toString().replace("<amount>", String.valueOf(amount)));
		}
	}

	public void setLives(CommandSender sender, Player target, int amount) {
		File targetFile = new File(this.livesFolder, target.getUniqueId().toString() + ".yml");
		if(targetFile.exists()) {
			YamlConfiguration targetConfiguration = YamlConfiguration.loadConfiguration(targetFile);
			targetConfiguration.set("lives", Integer.valueOf(amount));
			sender.sendMessage(Lang.PREFIX.toString() + Lang.LIVES_COMMAND_LIVES_SET.toString().replace("<player>", target.getName()).replace("<amount>", String.valueOf(amount)));
			target.sendMessage(Lang.PREFIX.toString() + Lang.LIVES_COMMAND_LIVES_SET_RECEIVED.toString().replace("<amount>", String.valueOf(amount)).replace("<player>", sender.getName()));
			try {
				targetConfiguration.save(targetFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			sender.sendMessage(Lang.PREFIX.toString() + Lang.LIVES_COMMAND_PLAYER_NOT_IN_DATABASE.toString().replace("<player>", target.getName()));
		}
	}
	

	public void setLives(CommandSender sender, OfflinePlayer target, int amount) {
		File targetFile = new File(this.livesFolder, target.getUniqueId() + ".yml");
		if(targetFile.exists()) {
			YamlConfiguration targetConfiguration = YamlConfiguration.loadConfiguration(targetFile);
			targetConfiguration.set("lives", Integer.valueOf(amount));
			sender.sendMessage(Lang.PREFIX.toString() + Lang.LIVES_COMMAND_LIVES_SET.toString().replace("<player>", target.getName()).replace("<amount>", String.valueOf(amount)));
			try {
				targetConfiguration.save(targetFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			sender.sendMessage(Lang.PREFIX.toString() + Lang.LIVES_COMMAND_PLAYER_NOT_IN_DATABASE.toString().replace("<player>", target.getName()));
		}
	}

	public void addLives(CommandSender sender, Player target, int amount) {
		File targetFile = new File(this.livesFolder, target.getUniqueId().toString() + ".yml");
		if(targetFile.exists()) {
			YamlConfiguration targetConfiguration = YamlConfiguration.loadConfiguration(targetFile);
			targetConfiguration.set("lives", Integer.valueOf(targetConfiguration.getInt("lives") + amount));
			sender.sendMessage(Lang.PREFIX.toString() + Lang.LIVES_COMMAND_LIVES_ADDED.toString().replace("<amount>", String.valueOf(amount)).replace("<player>", target.getName()));
			target.sendMessage(Lang.PREFIX.toString() + Lang.LIVES_COMMAND_LIVES_ADD_RECEIVED.toString().replace("<amount>", String.valueOf(amount)).replace("<player>", sender.getName()));
			try {
				targetConfiguration.save(targetFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			sender.sendMessage(Lang.PREFIX.toString() + Lang.LIVES_COMMAND_PLAYER_NOT_IN_DATABASE.toString().replace("<player>", target.getName()));
		}
	}
	
	public void addLives(CommandSender sender, OfflinePlayer target, int amount) {
		File targetFile = new File(this.livesFolder, target.getUniqueId() + ".yml");
		if(targetFile.exists()) {
			YamlConfiguration targetConfiguration = YamlConfiguration.loadConfiguration(targetFile);
			targetConfiguration.set("lives", Integer.valueOf(targetConfiguration.getInt("lives") + amount));
			sender.sendMessage(Lang.PREFIX.toString() + Lang.LIVES_COMMAND_LIVES_ADDED.toString().replace("<amount>", String.valueOf(amount)).replace("<player>", target.getName()));
			try {
				targetConfiguration.save(targetFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			sender.sendMessage(Lang.PREFIX.toString() + Lang.LIVES_COMMAND_PLAYER_NOT_IN_DATABASE.toString().replace("<player>", target.getName()));
		}
	}

	public void sendUsage(CommandSender sender) {
		for(String message : Main.getInstance().getConfigHandler().getLivesUsageMessage()) {
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
		}
	}
}