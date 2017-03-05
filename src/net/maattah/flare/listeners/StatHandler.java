package net.maattah.flare.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import net.maattah.flare.utils.FlarePlayer;

public class StatHandler implements Listener {
	
	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		Player killed = e.getEntity().getPlayer();
		FlarePlayer killedd = new FlarePlayer(killed.getUniqueId());
		killedd.setDeaths(killedd.getDeaths() + 1);
		
		if(e.getEntity().getKiller() != null) {
			Player killer = e.getEntity().getKiller();
			FlarePlayer killerd = new FlarePlayer(killer.getUniqueId());
			killerd.setKills(killerd.getKills() + 1);
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onBreak(BlockBreakEvent e) {
		FlarePlayer player = new FlarePlayer(e.getPlayer().getUniqueId());
		if(!e.isCancelled()) {
		switch(Material.getMaterial(e.getBlock().getTypeId())) {
			case EMERALD_ORE:
				player.setEmeralds(player.getEmeralds() + 1);
				break;
			case DIAMOND_ORE:
				player.setDiamonds(player.getDiamonds() + 1);
				break;
			case GOLD_ORE:
				player.setGold(player.getGold() + 1);
				break;
			case LAPIS_ORE:
				player.setLapis(player.getLapis() + 1);
				break;
			case REDSTONE_ORE:
				player.setRedstone(player.getRedstone() + 1);
				break;
			case IRON_ORE:
				player.setIron(player.getIron() + 1);
				break;
			case COAL_ORE:
				player.setCoal(player.getCoal() + 1);
				break;
			default:
				break;	
			}	
		}
	}
}