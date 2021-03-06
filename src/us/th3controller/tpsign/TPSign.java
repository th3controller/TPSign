package us.th3controller.tpsign;

import java.io.File;
import java.util.HashMap;
import java.util.logging.Logger;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class TPSign extends JavaPlugin {
	
	public HashMap<String, Integer> coords = new HashMap<String, Integer>();
	public HashMap<String, String> world = new HashMap<String, String>();
	Logger log = Logger.getLogger("Minecraft");
	
	PluginDescriptionFile pdfile;
	
	@Override
	public void onDisable() {
		coords.clear();
		log.info("[TPSign] Successfully terminated the plugin!");
	}
	@Override
	public void onEnable() {
		File file = new File("plugins/TPSign", "config.yml");
		if(!file.exists()) {
			this.saveResource("config.yml", true);
		}
		getServer().getPluginManager().registerEvents(new TPSignListener(this), this);
		pdfile = getDescription();
		getCommand("tpsign").setExecutor(new CmdTPSign(this));
		log.info("[TPSign] Successfully initiated the plugin!");
		log.info("[TPSign] Running version "+pdfile.getVersion());
	}
}
