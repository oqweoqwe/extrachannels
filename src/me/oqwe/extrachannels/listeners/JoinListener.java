package me.oqwe.extrachannels.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import me.oqwe.extrachannels.Channel;
import me.oqwe.extrachannels.Main;
import me.oqwe.extrachannels.util.ChannelHandler;
import me.oqwe.extrachannels.util.PersistentDataHandler;

public class JoinListener implements Listener {

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		
		// check if they have persistent data key "channel", if not set it to "global"
		if (!PersistentDataHandler.hasPersistentDataKey(e.getPlayer(), "channel"))
			PersistentDataHandler.setChannel(e.getPlayer().getUniqueId(), "global");
		
		// check if the channel they are in (in persistent data) still exists
		if (ChannelHandler.getChannelByName(PersistentDataHandler.getChannelFromPersistentData(e.getPlayer())) == null) {
			PersistentDataHandler.setChannel(e.getPlayer().getUniqueId(), "global");
		}
		
		// loop through channels, and add player to members list in allowed channels
		for (Channel channel : Main.getChannelObjects()) {
			
			if (e.getPlayer().hasPermission(channel.getPermission())) 
				channel.addMember(e.getPlayer().getUniqueId());		
			
		}
		
	}
	
}
