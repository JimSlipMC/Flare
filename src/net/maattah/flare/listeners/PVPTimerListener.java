package net.maattah.flare.listeners;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import net.maattah.flare.Main;
import net.maattah.flare.utils.FlarePlayer;
import net.maattah.flare.utils.Handler;
import net.maattah.flare.utils.Lang;
import net.maattah.flare.utils.WorldGuard;

public class PVPTimerListener extends Handler implements Listener {
	
private ArrayList<UUID> notified;
	
	public PVPTimerListener(Main plugin) {
		super(plugin);
		this.notified = new ArrayList<UUID>();
	}

	public void enable() {
		new BukkitRunnable() {
			@SuppressWarnings("deprecation")
			public void run() {
				for(Player player : Bukkit.getOnlinePlayers()) {
					if(!player.isDead()) {
						boolean freeze = false;
						FlarePlayer playerData = new FlarePlayer(player.getUniqueId());
						if(!WorldGuard.isPvPEnabled(player)) {
							freeze = true;
						}
						if(playerData.getPvPTime() > 0) {
							if(!freeze) {
								if(notified.contains(player.getUniqueId())) {
									notified.remove(player.getUniqueId());
									player.sendMessage(Lang.PREFIX.toString() + Lang.PVPTIMER_UNFROZEN_MESSAGE.toString());
								}
								playerData.setPvPTime(playerData.getPvPTime() - 1);
							} else if(!notified.contains(player.getUniqueId())) {
								notified.add(player.getUniqueId());
								player.sendMessage(Lang.PREFIX.toString() + Lang.PVPTIMER_FROZEN_MESSAGE.toString());
							}
						}
					}
				}
			}
		}.runTaskTimer(Main.getInstance(), 20L, 20L);
		
		this.getInstance().getServer().getPluginManager().registerEvents(this, this.getInstance());
	}
	
	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent event) {
		Player player = event.getPlayer();
		
		FlarePlayer playerData = new FlarePlayer(player.getUniqueId());
		playerData.setPvPTime(this.getInstance().getConfig().getInt("pvptimer.duration"));
	}

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
		Player player = event.getEntity();
		
		FlarePlayer playerData = new FlarePlayer(player.getUniqueId());
		playerData.setPvPTime(this.getInstance().getConfig().getInt("pvptimer.duration"));
	}

	@EventHandler
	public void onFoodLevelChange(FoodLevelChangeEvent event) {
		HumanEntity entity = event.getEntity();
		if (entity instanceof Player) {
			Player player = (Player) entity;
			FlarePlayer playerData = new FlarePlayer(player.getUniqueId());
			if (playerData.getPvPTime() > 0) {
				event.setFoodLevel(20);
				player.setSaturation(20.0F);
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
    	if (event.getEntity() instanceof Player) {
    		if (event.getDamager() instanceof Player) {
    			Player victim = (Player)event.getEntity();
    			Player damager = (Player)event.getDamager();
    			
    			FlarePlayer victimData = new FlarePlayer(victim.getUniqueId());
    			FlarePlayer damagerData = new FlarePlayer(damager.getUniqueId());
    			
    			if(victimData.getPvPTime() > 0 && damagerData.getPvPTime() > 0) {
    				event.setCancelled(true);
    				damager.sendMessage(Lang.PREFIX.toString() + Lang.PVPTIMER_PVPDENY_ATTACKER.toString().replace("<name>", victim.getName()));
    			} else if(victimData.getPvPTime() > 0) {
    				event.setCancelled(true);
    				damager.sendMessage(Lang.PREFIX.toString() + Lang.PVPTIMER_PVPDENY_ATTACKER.toString().replace("<name>", victim.getName()));
    			} else if(damagerData.getPvPTime() > 0) {
    				event.setCancelled(true);
    				damager.sendMessage(Lang.PREFIX.toString() + Lang.PVPTIMER_PVPDENY_VICTIM.toString());
    			}
    		}
			else if (event.getDamager() instanceof Projectile) {
				Projectile projectile = (Projectile) event.getDamager();
				if (projectile.getShooter() instanceof Player) {
					Player shooter = (Player) projectile.getShooter();
					if (shooter != event.getEntity()) {
						Player player = (Player) event.getEntity();

						FlarePlayer playerData = new FlarePlayer(player.getUniqueId());
						FlarePlayer taggerData = new FlarePlayer(shooter.getUniqueId());
		    			
		    			if(playerData.getPvPTime() > 0 && taggerData.getPvPTime() > 0) {
		    				event.setCancelled(true);
		    				shooter.sendMessage(Lang.PREFIX.toString() + Lang.PVPTIMER_PVPDENY_ATTACKER.toString().replace("<name>", player.getName()));
		    			} else if(playerData.getPvPTime() > 0) {
		    				event.setCancelled(true);
		    				shooter.sendMessage(Lang.PREFIX.toString() + Lang.PVPTIMER_PVPDENY_VICTIM.toString());
		    			} else if(taggerData.getPvPTime() > 0) {
		    				event.setCancelled(true);
		    				shooter.sendMessage(Lang.PREFIX.toString() + Lang.PVPTIMER_PVPDENY_ATTACKER.toString().replace("<name>", player.getName()));
		    			}
					}
				}
			}
    	}
    }
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		Action action = event.getAction();
		ItemStack item = event.getItem();
		
		if(item == null) return;
		
		FlarePlayer playerData = new FlarePlayer(player.getUniqueId());
		
		if(playerData.getPvPTime() > 0) {
			if(action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK)) {
				for(Material material : this.getInstance().getConfigHandler().getPvPTimerDisabledItems()) {
					if(item.getType().equals(material)) {
						if(!player.isOp()) {
							event.setCancelled(true);
							player.sendMessage(Lang.PREFIX.toString() + Lang.PVPTIMER_ITEM_DENY_MESSAGE.toString().replace("<item>", item.getType().name().replace("_", " ")));
						}
					}
				}
			}
		}
	}
	
	@EventHandler
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
    	Player player = event.getPlayer();
    	FlarePlayer playerData = new FlarePlayer(player.getUniqueId());
    	if(playerData.getPvPTime() > 0) {
    		for(String command : this.getInstance().getConfigHandler().getPvpTimerDisabledCommands()) {
    			if(event.getMessage().startsWith("/" + command)) {
    				event.setCancelled(true);
    				player.sendMessage(Lang.PREFIX.toString() + Lang.PVPTIMER_COMMAND_DENY_MESSAGE.toString());
    			}
    		}
    	}
    }
	
}
