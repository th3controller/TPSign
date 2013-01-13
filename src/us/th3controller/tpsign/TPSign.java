package us.th3controller.tpsign;

import java.util.ArrayList;
import java.util.logging.Logger;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class TPSign extends JavaPlugin {
	
	Logger log = Logger.getLogger("Minecraft");
	PluginDescriptionFile pdfile;
	
	public ArrayList<Double> xcord = new ArrayList<Double>();
	
	public void onEnable() {
		getServer().getPluginManager().registerEvents(new TPSignListener(this), this);
		pdfile = getDescription();
		log.info("[TPSign] Successfully initiated the plugin!");
		log.info("[TPSign] Running version"+pdfile.getVersion());
	}
	public void onDisable() {
		log.info("[TPSign] Successfully terminated the plugin!");
	}
}
