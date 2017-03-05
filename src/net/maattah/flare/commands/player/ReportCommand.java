package net.maattah.flare.commands.player;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.maattah.flare.utils.C;
import net.maattah.flare.utils.Lang;
import net.minecraft.util.org.apache.commons.lang3.StringUtils;

public class ReportCommand implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) { sender.sendMessage(Lang.PREFIX.toString() + Lang.PLAYER_ONLY.toString()); return true; }
		Player p = (Player) sender;
		if(args.length == 0) {
			sendUsage(p);
			return true;
		}
		
		else if(args.length == 1) {
			sendUsage(p);
			return true;
		} else {
			Player target = Bukkit.getPlayerExact(args[0]);
			String message = StringUtils.join(args, ' ');
			if(target != null) {
				reportPlayer(p, target, message);
			} else {
				p.sendMessage(Lang.PREFIX.toString() + Lang.INVALID_PLAYER.toString());
				return true;
			}
		}
		
		return true;
	}
	
	@SuppressWarnings("deprecation")
	public void reportPlayer(Player p, Player target, String reason) {
		p.sendMessage(C.GREEN + "All online staff have been alerted and have received your report.");
		for(Player staff : Bukkit.getOnlinePlayers()) {
			if(staff.hasPermission("utils.staff")) {
				staff.sendMessage(Lang.REPORT_STAFF_MESSAGE.toString()
					.replaceAll("%sender%", p.getName())
					.replaceAll("%target%", target.getName())
					.replaceAll("%reason%", reason.replace(target.getName() + " ", "")));
			}
		}
		return;
	}
	
	public void sendUsage(Player p) {
		p.sendMessage(Lang.PREFIX.toString() + Lang.REPORT_USAGE.toString());
		return;
	}
	
}
