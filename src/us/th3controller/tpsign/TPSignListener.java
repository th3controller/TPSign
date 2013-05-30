package us.th3controller.tpsign;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class TPSignListener implements Listener {
	
	TPSign plugin;
	
	public TPSignListener(TPSign plugin) {
		this.plugin = plugin;
	}
	@EventHandler
	public void onPlayerPlate(PlayerInteractEvent event) {
		if(event.getAction() == Action.PHYSICAL) {
			Block getCB = event.getClickedBlock();
			int x = getCB.getX();
			int y = getCB.getY();
			int z = getCB.getZ();
			Block getSignBelow = event.getPlayer().getWorld().getBlockAt(x, y - 2, z);
			if((getSignBelow.getType() == Material.SIGN_POST) || (getSignBelow.getType() == Material.WALL_SIGN)) {
				BlockState stateBlock = getSignBelow.getState();
				Sign sign = (Sign)stateBlock;
				if(sign.getLine(0).equalsIgnoreCase("[ tpsign ]")) {
					final Player player = event.getPlayer();
					//Get the details on the sign.
					String yYP = sign.getLine(3);
					String[] data = yYP.split(":");
					int xCord = Integer.parseInt(sign.getLine(1));
					int zCord = Integer.parseInt(sign.getLine(2));
					Integer yCord = Integer.parseInt(data[0]);
					Float yaw = Float.parseFloat(data[1]);
					Float pitch = Float.parseFloat(data[2]);
					//Define the block on the coordinates.
					final Location blockoncoords = new Location(player.getWorld(), xCord, yCord, zCord, yaw, pitch);
					//Load the chunk for better teleport.
					Bukkit.getWorld(player.getWorld().getName()).getBlockAt(blockoncoords).getChunk().load();
					//Teleport the player with a cause.
					plugin.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable() {
						@Override
						public void run() {
							player.teleport(blockoncoords);
						}
					}, 2L);
					player.sendMessage(ChatColor.GREEN+"Successfully teleported");
				}
			}
		}
	}
	@EventHandler
	public void PlayerSignChange(SignChangeEvent event) {
		if(event.getLine(0).equalsIgnoreCase("[ tpsign ]") || event.getLine(0).equalsIgnoreCase("[tpsign]")) {
			String name = event.getPlayer().getName();
			if(plugin.coords.containsKey(name+"-x")) {
				String x = Integer.toString(plugin.coords.get(name+"-x"));
				String y = Integer.toString(plugin.coords.get(name+"-y"));
				String z = Integer.toString(plugin.coords.get(name+"-z"));
				Integer yaw = plugin.coords.get(name+"-yaw");
				Integer pitch = plugin.coords.get(name+"-pitch");
				event.setLine(0, "[ TPSign ]");
				event.setLine(1, x);
				event.setLine(2, z);
				event.setLine(3, y+":"+yaw+":"+pitch);
			} else {
				event.getBlock().breakNaturally();
				event.getPlayer().sendMessage(ChatColor.RED+"You don't have any details registered for your sign!");
			}
		}
	}
}
