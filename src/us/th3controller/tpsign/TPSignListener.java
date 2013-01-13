package us.th3controller.tpsign;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class TPSignListener implements Listener {
	
	TPSign plugin;
	
	public TPSignListener(TPSign plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlayerPlate(final PlayerInteractEvent event) {
		if(event.getAction() == Action.PHYSICAL) {
			int x = event.getClickedBlock().getX();
			int y = event.getClickedBlock().getY();
			int z = event.getClickedBlock().getZ();
			if((event.getPlayer().getWorld().getBlockAt(x, y - 2, z).getType() == Material.SIGN_POST)) {
				BlockState stateBlock = event.getPlayer().getWorld().getBlockAt(x, y - 2, z).getState();
				Sign sign = (Sign)stateBlock;
				if(sign.getLine(0).equalsIgnoreCase("[ tpsign ]")) {
					final int xCord = Integer.parseInt(sign.getLine(1));
					final int yCord = Integer.parseInt(sign.getLine(2));
					final int zCord = Integer.parseInt(sign.getLine(3));
					this.plugin.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable(){
						public void run(){
							event.getPlayer().teleport(new Location(event.getPlayer().getWorld(), xCord, yCord, zCord));
						}
					}, 5L);
					event.getPlayer().sendMessage(ChatColor.GREEN+"Successfully teleported");
				}
			}
		}
	}
}
