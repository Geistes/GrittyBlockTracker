package com.Geisteskranken.GrittyBlockTracker;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class GrittyBlockTrackerConfig {

	private static File f;
	static Properties prop = new Properties();
	static OutputStream output = null;

	public static boolean readConfig() {

    	f = new File("plugins/GrittyBlockTracker/GrittyBlockTracker.conf");
        Properties prop = new Properties();
        InputStream input = null;

            if (f.exists() && !f.isDirectory()) {
            	try{
            input = new FileInputStream(f);

            prop.load(input);

            GrittyBlockTracker.host = prop.getProperty("host");
            GrittyBlockTracker.port = prop.getProperty("port");
            GrittyBlockTracker.database = prop.getProperty("database");
            GrittyBlockTracker.dbuser = prop.getProperty("dbuser");
            GrittyBlockTracker.dbpass = prop.getProperty("dbpass");
        } catch (IOException ex) {
            GrittyBlockTracker.logger.warn("Disabled! Configuration error.", ex);
        }
        try {
            input.close();
        } catch (IOException e) {
            GrittyBlockTracker.logger.warn("Disabled! Configuration error.", e);
            return false;
        }
        
        GrittyBlockTracker.logger.info("Config: OK");
        return true;
    } else {
        createConfig();
		return false;
    }

        }

	public static void createConfig() {
		try {

			f.getParentFile().mkdirs();
			f.createNewFile();
			output = new FileOutputStream(f);

			prop.setProperty("host", "localhost");
			prop.setProperty("port", "3306");
			prop.setProperty("database", "blocktracker");
			prop.setProperty("dbuser", "username");
			prop.setProperty("dbpass", "pasword");

			prop.store(output, null);

		} catch (IOException io) {
			io.printStackTrace();
		} finally {
			if (output != null) {
				try {
					output.close();
					GrittyBlockTracker.logger
							.warn("Configuration file created. Please edit and restart server");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
	}

}
