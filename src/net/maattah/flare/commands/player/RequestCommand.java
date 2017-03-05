package net.maattah.flare.commands.player;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.maattah.flare.utils.C;
import net.maattah.flare.utils.Lang;
import net.minecraft.util.org.apache.commons.lang3.StringUtils;

public class RequestCommand implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) { sender.sendMessage(Lang.PREFIX.toString() + Lang.PLAYER_ONLY.toString()); return true; }
		Player p = (Player) sender;
		if(args.length == 0) {
			sendUsage(p);
			return true;
		} else {
			String message = StringUtils.join(args, ' ');
			request(p, message);
			return true;
		}
	}
	
	@SuppressWarnings("deprecation")
	public void request(Player p, String reason) {
		p.sendMessage(C.GREEN + "All online staff have been alerted and have received your message.");
		for(Player staff : Bukkit.getOnlinePlayers()) {
			if(staff.hasPermission("utils.staff")) {
				staff.sendMessage(Lang.REQUEST_STAFF_MESSAGE.toString()
						.replaceAll("%sender%", p.getName())
						.replaceAll("%reason%", reason));
			}
		}
		return;
	}
	
	public void sendUsage(Player p) {
		p.sendMessage(Lang.PREFIX.toString() + Lang.REQUEST_USAGE.toString());
		return;
	}
}