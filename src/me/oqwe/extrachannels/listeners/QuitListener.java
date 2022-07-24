package me.oqwe.extrachannels.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import me.oqwe.extrachannels.Channel;
import me.oqwe.extrachannels.Main;

public class QuitListener implements Listener {

	@EventHandler
	public void onQuit(PlayerQuitEvent e) {

		// loop through channels, and add player to members list in allowed channels
		for (Channel channel : Main.getChannelObjects()) {

			if (channel.getMembers().contains(e.getPlayer().getUniqueId()))
				channel.removeMember(e.getPlayer().getUniqueId());

		}

	}

}
