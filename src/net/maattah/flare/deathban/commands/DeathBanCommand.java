package net.maattah.flare.deathban.commands;

import java.io.File;
import java.io.IOException;

import org.apache.commons.lang.time.DurationFormatUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import net.maattah.flare.Main;
import net.maattah.flare.utils.InventoryUtils;
import net.maattah.flare.utils.Lang;

public class DeathBanCommand implements CommandExecutor, Listener {
	
	private File deathbanFolder = new File(Main.getInstance().getDataFolder(), "deathban");
	private File deathbansFolder = new File(this.deathbanFolder, "deathbans");
	private File inventoriesFolder = new File(this.deathbanFolder, "inventories");
	
	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd, String Commandlabel, String[] args) {
		if (cmd.getName().equalsIgnoreCase("deathban")) {
			if(sender instanceof Player) {
				Player player = (Player) sender;
				if (player.hasPermission(Main.getNode() + ".deathban.check") || player.hasPermission(Main.getNode() + ".deathban.revive") || player.hasPermission(Main.getNode() + ".deathban.rollback")) {
					if(args.length == 0) {
						sendUsage(player);
					} else if(args.length == 1) {
						if(args[0].equals("check")) {
							if(player.hasPermission(Main.getNode() + ".deathban.check")) {
								player.sendMessage(Lang.PREFIX.toString() + Lang.DEATHBAN_COMMAND_CHECK_USAGE.toString());
							} else {
								player.sendMessage(Lang.NO_PERMS.toString());
							}
						} else if(args[0].equals("revive")) {
							if(player.hasPermission(Main.getNode() + ".deathban.revive")) {
								player.sendMessage(Lang.PREFIX.toString() + Lang.DEATHBAN_COMMAND_REVIVE_USAGE.toString());
							} else {
								player.sendMessage(Lang.NO_PERMS.toString());
							}
						} else if(args[0].equals("rollback")) {
							if(player.hasPermission(Main.getNode() + ".deathban.rollback")) {
								player.sendMessage(Lang.PREFIX.toString() + Lang.DEATHBAN_COMMAND_INVENTORY_ROLLBACK_USAGE.toString());
							} else {
								player.sendMessage(Lang.NO_PERMS.toString());
							}
						} else {
							this.sendUsage(player);
						}
					} else if(args.length == 2) {
						if(args[0].equals("check")) {
							if(player.hasPermission(Main.getNode() + ".deathban.check")) {
								OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
								checkPlayer(player, target);
							} else {
								player.sendMessage(Lang.NO_PERMS.toString());
							}
						} else if(args[0].equals("revive")) {
							if(player.hasPermission(Main.getNode() + ".deathban.revive")) {
								OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
								revivePlayer(player, target);
							} else {
								player.sendMessage(Lang.NO_PERMS.toString());
							}
						} else if(args[0].equals("rollback")) { 
							if(player.hasPermission(Main.getNode() + ".deathban.rollback")) {
								Player target = Bukkit.getPlayer(args[1]);
								if(target != null) {
									checkPlayerInv(player, target);
								} else {
									player.sendMessage(Lang.INVALID_PLAYER.toString().replace("<player>", args[1]));
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
				} else {
					player.sendMessage(Lang.NO_PERMS.toString());
				}
			} else if(sender instanceof ConsoleCommandSender) {
				if(args.length == 0) {
					sendUsage(sender);
				} else if(args.length == 1) {
					if(args[0].equals("check")) {
						sender.sendMessage(Lang.PREFIX.toString() + Lang.DEATHBAN_COMMAND_CHECK_USAGE.toString());
					} else if(args[0].equals("revive")) {
						sender.sendMessage(Lang.PREFIX.toString() + Lang.DEATHBAN_COMMAND_REVIVE_USAGE.toString());
					} else {
						sender.sendMessage(Lang.PLAYER_ONLY.toString());
					}
				} else if(args.length == 2) {
					if(args[0].equals("revive")) {
						OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
						revivePlayer(sender, target);
					} else if(args[0].equals("check")) {
						OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
						checkPlayer(sender, target);
					} else {
						sender.sendMessage(Lang.PLAYER_ONLY.toString());
					}
				} else {
					this.sendUsage(sender);
				}
			}
		}
		return true;
	}
	
	public void checkPlayer(CommandSender sender, OfflinePlayer target) {
		File targetFile = new File(this.deathbansFolder, target.getUniqueId().toString() + ".yml");
		YamlConfiguration configuration = YamlConfiguration.loadConfiguration(targetFile);
		long banTime = configuration.getLong("ban_until");
		if (targetFile.exists()) {
			String duration = "";
			if((banTime - System.currentTimeMillis()) <= 0) {
				duration = String.valueOf("Unbanned");
			} else {
				duration = DurationFormatUtils.formatDurationWords(banTime - System.currentTimeMillis(), true, true);
			}
			for(String message : Main.getInstance().getConfigHandler().getDeathbanCheckMessage()) {
				sender.sendMessage(message.replace("<player>", target.getName()).replace("<duration>", 
				duration).replace("<reason>", configuration.getString("death_message")).replace("<location>", 
				configuration.getString("coords")));
			}
		} else {
			sender.sendMessage(Lang.PREFIX.toString() + Lang.DEATHBAN_PLAYER_NOT_DEATHBANNED.toString().replace("<player>", target.getName()));
		}
	}
	
	public void revivePlayer(CommandSender sender, OfflinePlayer target) {
		File targetFile = new File(this.deathbansFolder, target.getUniqueId().toString() + ".yml");
		if(targetFile.exists()) {
			targetFile.delete();
			sender.sendMessage(Lang.PREFIX.toString() + Lang.DEATHBAN_SUCCESSFULLY_REVIVED_PLAYER.toString().replace("<player>", target.getName()));
		} else {
			sender.sendMessage(Lang.PREFIX.toString() + Lang.DEATHBAN_PLAYER_NOT_DEATHBANNED.toString().replace("<player>", target.getName()));
		}
	}

	public void rollbackPlayerInv(Player player, Player target) {
		File targetFile = new File(this.inventoriesFolder, target.getUniqueId().toString() + ".yml");
		YamlConfiguration configuration = YamlConfiguration.loadConfiguration(targetFile);
		boolean used = configuration.getBoolean("used");
		if(targetFile.exists()) {
			if(!used) {
				try {
					ItemStack[] contents = InventoryUtils.itemStackArrayFromBase64(configuration.getString("inventory"));
					ItemStack[] armor = InventoryUtils.itemStackArrayFromBase64(configuration.getString("armor"));
					target.getInventory().setContents(contents);
					target.getInventory().setArmorContents(armor);
					configuration.set("used", Boolean.valueOf(true));
					configuration.save(targetFile);
				} catch(IOException e) {
					System.out.println(e.getMessage());
				}
				player.sendMessage(Lang.PREFIX.toString() + Lang.DEATHBAN_SUCCESSFULLY_ROLLBACKED_INVENTORY.toString().replace("<player>", target.getName()));
				target.sendMessage(Lang.PREFIX.toString() + Lang.DEATHBAN_SUCCESSFULLY_ROLLBACKED_INVENTORY_PLAYER.toString().replace("<player>", player.getName()));
			} else {
				player.sendMessage(Lang.PREFIX.toString() + Lang.DEATHBAN_INVENTORY_ALREADY_ROLLBACKED.toString().replace("<player>", target.getName()));
			}
		} else {
			player.sendMessage(Lang.PREFIX.toString() + Lang.DEATHBAN_PLAYER_HAS_NOT_DIED_YET.toString().replace("<player>", target.getName()));
		}
	}

	public void checkPlayerInv(Player player, Player target) {
		File targetFile = new File(this.inventoriesFolder, target.getUniqueId().toString() + ".yml");
		YamlConfiguration configuration = YamlConfiguration.loadConfiguration(targetFile);
		boolean used = configuration.getBoolean("used");
		Inventory inv = Bukkit.createInventory(null, 54, target.getName() + "'s Inventory");
		if(targetFile.exists()) {
			if(!used) {
				try {
					ItemStack[] contents = InventoryUtils.itemStackArrayFromBase64(configuration.getString("inventory"));
					ItemStack[] armor = InventoryUtils.itemStackArrayFromBase64(configuration.getString("armor"));

					inv.setContents(contents);

					inv.setItem(45, armor[0]);
					inv.setItem(46, armor[1]);
					inv.setItem(47, armor[2]);
					inv.setItem(48, armor[3]);

					inv.setItem(36, createGlass(ChatColor.RED + "Inventory Preview"));
					inv.setItem(37, createGlass(ChatColor.RED + "Inventory Preview"));
					inv.setItem(38, createGlass(ChatColor.RED + "Inventory Preview"));
					inv.setItem(39, createGlass(ChatColor.RED + "Inventory Preview"));
					inv.setItem(40, createGlass(ChatColor.RED + "Inventory Preview"));
					inv.setItem(41, createGlass(ChatColor.RED + "Inventory Preview"));
					inv.setItem(42, createGlass(ChatColor.RED + "Inventory Preview"));
					inv.setItem(43, createGlass(ChatColor.RED + "Inventory Preview"));
					inv.setItem(44, createGlass(ChatColor.RED + "Inventory Preview"));
					inv.setItem(49, createGlass(ChatColor.RED + "Inventory Preview"));

					inv.setItem(52, createWool(ChatColor.RED + "Close Preview", 14));
					inv.setItem(53, createWool(ChatColor.GREEN + "Rollback Inventory (" + target.getName() + ")", 5));

					player.openInventory(inv);
				} catch(IOException e) {
					System.out.println(e.getMessage());
				}
			} else {
				player.sendMessage(Lang.PREFIX.toString() + Lang.DEATHBAN_INVENTORY_ALREADY_ROLLBACKED.toString().replace("<player>", target.getName()));
			}
		} else {
			player.sendMessage(Lang.PREFIX.toString() + Lang.DEATHBAN_PLAYER_HAS_NOT_DIED_YET.toString().replace("<player>", target.getName()));
		}
	}

	public ItemStack createWool(String name, int value) {
		ItemStack item = new ItemStack(Material.WOOL, 1, (short) value);
		ItemMeta itemmeta = item.getItemMeta();
		itemmeta.setDisplayName(name);
		item.setItemMeta(itemmeta);
		return item;
	}

	public ItemStack createGlass(String name) {
		ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 7);
		ItemMeta itemmeta = item.getItemMeta();
		itemmeta.setDisplayName(name);
		item.setItemMeta(itemmeta);

		return item;
	}

	public void sendUsage(CommandSender sender) {
		for(String string : Main.getInstance().getConfigHandler().getDeathbanUsageMessage()) {
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', string));
		}
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		ItemStack item = event.getCurrentItem();
		Inventory inventory = event.getInventory();
		
		if(!inventory.getName().endsWith("'s Inventory")) return;
		if(event.getSlotType().equals(InventoryType.SlotType.OUTSIDE)) return;
		if(item.getType().equals(Material.AIR)) return;

		if(inventory.getName().endsWith("'s Inventory")) {
			event.setCancelled(true);
			if(item.getItemMeta().getDisplayName().contains("Close")) {
				new BukkitRunnable() {
					public void run() {
						player.closeInventory();
					}
				}.runTaskLater(Main.getInstance(), 1L);
			} else if(item.getItemMeta().getDisplayName().contains("Rollback")) {
				String[] name = event.getCurrentItem().getItemMeta().getDisplayName().split("\\(");
				Player target = Bukkit.getPlayer(name[1].replaceAll("\\)", ""));
				if(target != null)
					rollbackPlayerInv(player, target);
				else {
					player.sendMessage("§cPlayer " + name[1].replaceAll("\\)", "") + " is not online!");
				}
				new BukkitRunnable() {
					public void run() {
						player.closeInventory();
					}
				}.runTaskLater(Main.getInstance(), 1L);
			}
		}
	}
}