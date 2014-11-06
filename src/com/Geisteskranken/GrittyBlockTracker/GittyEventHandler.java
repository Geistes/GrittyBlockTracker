package com.Geisteskranken.GrittyBlockTracker;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.granitemc.granite.api.block.Block;
import org.granitemc.granite.api.block.BlockType;
import org.granitemc.granite.api.entity.player.Player;
import org.granitemc.granite.api.event.On;
import org.granitemc.granite.api.event.block.EventBlockBreak;
import org.granitemc.granite.api.event.block.EventBlockPlace;
import org.granitemc.granite.api.utils.Location;

public class GittyEventHandler {
	
	public static final ExecutorService SQLQueue = Executors.newFixedThreadPool(2);
	
	// Called when a player breaks a block
		@On(event = EventBlockBreak.class)
		public void event(EventBlockBreak evt) {
				//final String BlockType = String.valueOf(blocktype);
				Block block = evt.getBlock();
				BlockType BlockType = block.getType();
				String Block = BlockType.getName();

				// Converts the dt object to X, Y, and Z variables
				//final int X = BBcoords.n();
				//final int Y = BBcoords.o();
				//final int Z = BBcoords.p();
				Location coords = block.getLocation();
				double X = coords.getX();
				double Y = coords.getY();
				double Z = coords.getZ();

				// Isolates the playername from the ahd object
				//final String Player = player.d_();
				Player player = evt.getPlayer();
				String Player = player.getName();
				player.sendMessage(Block);

				// Insert to DB
				SQLQueue.execute(new Runnable() {
					public void run() {
						GrittyBlockTrackerSQL.insertBlockBreak(Player, X, Y, Z,
								getTime(), Block);
					}
				});
		}

		// Called when a player places a block
		@On(event = EventBlockPlace.class)
		public void event(EventBlockPlace evt) {
				//final String BlockType = String.valueOf(blocktype);
				Block block = evt.getBlock();
				BlockType BlockType = block.getType();
				String Block = BlockType.getName();

				// Converts the dt object to X, Y, and Z variables
				//final int X = BBcoords.n();
				//final int Y = BBcoords.o();
				//final int Z = BBcoords.p();
				Location coords = block.getLocation();
				double X = coords.getX();
				double Y = coords.getY();
				double Z = coords.getZ();

				// Isolates the playername from the ahd object
				//final String Player = player.d_();
				Player player = evt.getPlayer();
				String Player = player.getName();
				player.sendMessage(Block);

				SQLQueue.execute(new Runnable() {
					public void run() {
						GrittyBlockTrackerSQL.insertBlockPlace(Player, X, Y, Z,
								getTime(), Block);
					}
				});

		}

		public String getTime() {
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date date = new Date();
			return dateFormat.format(date);
		}

}
