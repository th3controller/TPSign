package us.th3controller.tpsign;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
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
					final int zCord = Integer.parseInt(sign.getLine(2));
					final String yYP = sign.getLine(3);
					String[] data = yYP.split(":");
					final Integer yCord = Integer.parseInt(data[0]);
					final Float yaw = Float.parseFloat(data[1]);
					final Float pitch = Float.parseFloat(data[2]);
					plugin.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable(){
						@Override
						public void run(){
							event.getPlayer().teleport(new Location(event.getPlayer().getWorld(), xCord, yCord, zCord, yaw, pitch));
						}
					}, 5L);
					event.getPlayer().sendMessage(ChatColor.GREEN+"Successfully teleported");
				}
			}
		}
	}
	@EventHandler
	public void PlayerSignChange(SignChangeEvent event) {
		if(event.getLine(0).equals("[ tpsign ]")) {
			if(plugin.coords.containsKey(event.getPlayer().getName()+"-x")) {
				String x = Integer.toString(plugin.coords.get(event.getPlayer().getName()+"-x"));
				String y = Integer.toString(plugin.coords.get(event.getPlayer().getName()+"-y"));
				String z = Integer.toString(plugin.coords.get(event.getPlayer().getName()+"-z"));
				Integer yaw = plugin.coords.get(event.getPlayer().getName()+"-yaw");
				Integer pitch = plugin.coords.get(event.getPlayer().getName()+"-pitch");
				event.setLine(1, x);
				event.setLine(2, z);
				event.setLine(3, y+":"+yaw+":"+pitch);
			} else {
				event.getPlayer().sendMessage(ChatColor.RED+"You don't have any details registered for your sign!");
			}
		}
	}
}
