package com.Geisteskranken.GrittyBlockTracker;

import org.apache.logging.log4j.Logger;
import org.granitemc.granite.api.Granite;
import org.granitemc.granite.api.plugin.Plugin;
import org.granitemc.granite.api.plugin.OnEnable;
import org.granitemc.granite.api.plugin.PluginContainer;

@Plugin(name = "GrittyBlockTracker", id = "grittyblocktracker", version = "0.1")
public class GrittyBlockTracker extends Thread {
	public static String host;
	public static String port;
	public static String database;
	public static String dbuser;
	public static String dbpass;
	public static boolean Track;
	public static Logger logger;
	public PluginContainer plugin;

	public GrittyBlockTracker() {
		plugin = Granite.getPluginContainer(this);
        //plugin.registerCommandHandler(new GrittyBlockTrackerCommand());
        plugin.registerEventHandler(new GittyEventHandler());
        logger = Granite.getLogger();
	}
	
	@OnEnable
	public void OnEnable(PluginContainer p){
		this.setName("GrittyBlockTracker");
		if (GrittyBlockTrackerConfig.readConfig()) {
			logger.info("Checking SQL DB...");
			if (GrittyBlockTrackerSQL.checkDB()) {
				logger.info("Checking SQL Table...");
				if (GrittyBlockTrackerSQL.checkTable()) {
					logger.info("Everything appears OK");
					logger.info("Enabled!");
					Track = true;
				} else {
					Track = false;
					plugin.disable();
				}
			} else {
				Track = false;
				plugin.disable();
			}
		} else {
			Track = false;
			plugin.disable();
		}
	}
}
