package net.maattah.flare.commands.player;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.maattah.flare.Main;
import net.maattah.flare.utils.FlarePlayer;
import net.maattah.flare.utils.Lang;
import net.maattah.flare.utils.StringUtils;

public class PvpCommand implements CommandExecutor {
	
	private Main plugin;
	
	public PvpCommand(Main plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("pvp")) {
			if(sender instanceof Player) {
				Player player = (Player) sender;
				if(args.length == 0) {
					this.sendCommandUsage(player);
				} else if(args.length == 1) {
					FlarePlayer playerData = new FlarePlayer(player.getUniqueId());
					if(args[0].equals("enable")) {
						if(playerData.getPvPTime() > 0) {
							playerData.setPvPTime(0);
							player.sendMessage(Lang.PREFIX.toString() + Lang.PVPTIMER_TIMER_DISABLED.toString());
						} else {
							player.sendMessage(Lang.PREFIX.toString() + Lang.PVPTIMER_TIMER_NOT_ACTIVE.toString());
						}
						
					} else if(args[0].equals("set")) {
						this.sendCommandUsage(player);
							
					} else if(args[0].equals("time")) {
						if(playerData.getPvPTime() > 0) {
							String time = StringUtils.formatSecondsToMinutes(playerData.getPvPTime());
							player.sendMessage(Lang.PREFIX.toString() + Lang.PVPTIMER_TIMER_STATUS.toString().replace("<time>", time));
						} else {
							player.sendMessage(Lang.PREFIX.toString() + Lang.PVPTIMER_TIMER_NOT_ACTIVE.toString());
						}
					} else {
						this.sendCommandUsage(player);
					}
				}
				
				else if(args.length == 2) {
					if(args[0].equalsIgnoreCase("set") && player.hasPermission(Main.getNode() + ".pvp.set")) {
						Player target = Bukkit.getPlayerExact(args[1]);
						 if(target != null){
							 FlarePlayer playerData = new FlarePlayer(target.getUniqueId());
								playerData.setPvPTime(plugin.getConfigFile().getInt("pvptimer.duration"));
								target.sendMessage(Lang.PREFIX.toString() + Lang.PVPTIMER_TIMER_ENABLED.toString());
								player.sendMessage(Lang.PREFIX.toString() +  Lang.PVPTIMER_TIMER_ENABLED_OTHER.toString().replace("<player>", target.getName().toString() + "§7's"));
								return true;
						 } else {
							 player.sendMessage(Lang.PREFIX.toString() + Lang.PVPTIMER_PLAYER_INVAILD);
						 }
					} else {
						this.sendCommandUsage(player);
					}
				} else {
					this.sendCommandUsage(player);
				}
			} else {
				sender.sendMessage(Lang.PLAYER_ONLY.toString());
			}
		}
		return true;
	}
	
	public void sendCommandUsage(Player player) {
		player.sendMessage(Lang.PREFIX.toString() + Lang.PVPTIMER_FORMAT_ENABLE.toString());
		player.sendMessage(Lang.PREFIX.toString() + Lang.PVPTIMER_FORMAT_TIME.toString());
		player.sendMessage(Lang.PREFIX.toString() + Lang.PVPTIMER_FORMAT_SET.toString());
	}
}
