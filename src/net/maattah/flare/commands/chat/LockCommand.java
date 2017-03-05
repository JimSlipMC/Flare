package net.maattah.flare.commands.chat;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import net.maattah.flare.Main;
import net.maattah.flare.utils.C;
 
public class LockCommand implements CommandExecutor {
    public static boolean chatEnabled = true;
   
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("lock") || cmd.getName().equalsIgnoreCase("l")) {
            if(sender.hasPermission(Main.getNode() + ".chat.lock")) {
                if(chatEnabled) {
                	Bukkit.broadcastMessage(C.GREEN + "Chat was locked by " + sender.getName() + ".");
                    chatEnabled = false;
                } else {
                	Bukkit.broadcastMessage(C.GREEN + "Chat was unlocked by " + sender.getName() + ".");
                    chatEnabled = true;
                }
            } else {
            	sender.sendMessage(C.RED + "You do not have access to that command.");
            }
        }
        return false;
    }
 
}