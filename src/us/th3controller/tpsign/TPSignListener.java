package us.th3controller.tpsign;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

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
					//Get the details on the sign.
					String yYP = sign.getLine(3);
					String[] data = yYP.split(":");
					int xCord = Integer.parseInt(sign.getLine(1));
					int zCord = Integer.parseInt(sign.getLine(2));
					Integer yCord = Integer.parseInt(data[0]);
					Float yaw = Float.parseFloat(data[1]);
					Float pitch = Float.parseFloat(data[2]);
					//Define the block on the coordinates.
					Location blockoncoords = new Location(event.getPlayer().getWorld(), xCord, yCord, zCord, yaw, pitch);
					//Load the chunk for better teleport.
					Bukkit.getWorld(event.getPlayer().getWorld().getName()).getBlockAt(blockoncoords).getChunk().load();
					//Teleport the player with a cause.
					event.getPlayer().teleport(blockoncoords, TeleportCause.PLUGIN);
					event.getPlayer().sendMessage(ChatColor.GREEN+"Successfully teleported");
				}
			}
		}
	}
	@EventHandler
	public void PlayerSignChange(SignChangeEvent event) {
		if(event.getLine(0).equalsIgnoreCase("[ tpsign ]") || event.getLine(0).equalsIgnoreCase("[tpsign]")) {
			if(plugin.coords.containsKey(event.getPlayer().getName()+"-x")) {
				String x = Integer.toString(plugin.coords.get(event.getPlayer().getName()+"-x"));
				String y = Integer.toString(plugin.coords.get(event.getPlayer().getName()+"-y"));
				String z = Integer.toString(plugin.coords.get(event.getPlayer().getName()+"-z"));
				Integer yaw = plugin.coords.get(event.getPlayer().getName()+"-yaw");
				Integer pitch = plugin.coords.get(event.getPlayer().getName()+"-pitch");
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
