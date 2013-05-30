package us.th3controller.tpsign;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdTPSign implements CommandExecutor {
	
	TPSign plugin;
	
	public CmdTPSign(TPSign plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) {
			if(args.length == 1) {
				if(args[0].equalsIgnoreCase("create")) {
					Player player = (Player) sender;
					if(player.hasPermission("tpsign.create")) {
						Location loc = player.getLocation();
						String name = player.getName();
						plugin.coords.put(name+"-x", loc.getBlockX());
						plugin.coords.put(name+"-y", loc.getBlockY());
						plugin.coords.put(name+"-z", loc.getBlockZ());
						plugin.coords.put(name+"-yaw", (int) Math.floor(loc.getYaw()));
						plugin.coords.put(name+"-pitch", (int) Math.floor(loc.getPitch()));
						player.sendMessage(ChatColor.GREEN+"Successfully registered coordinates!");
						player.sendMessage(ChatColor.GREEN+"Place a sign and write on the first line "+ChatColor.RED+"[ tpsign ]");
					} else {
						player.sendMessage(ChatColor.RED+"Insufficient permissions to execute this command!");
					}
				} else {
					sender.sendMessage(ChatColor.RED+"Invalid argument!");
				}
			} else {
				sender.sendMessage(ChatColor.GREEN+"---== TPSign ==---");
				sender.sendMessage(ChatColor.GREEN+" - /tpsign create");
			}
		} else {
			sender.sendMessage("Console execution for this command is not allowed!");
		}
		return true;
	}
}
