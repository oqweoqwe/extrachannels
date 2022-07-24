package me.oqwe.extrachannels.listeners;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.persistence.PersistentDataType;

import me.oqwe.extrachannels.Channel;
import me.oqwe.extrachannels.Main;
import me.oqwe.extrachannels.util.ChannelHandler;
import me.oqwe.extrachannels.util.ChatUtil;
import me.oqwe.extrachannels.util.PersistentDataHandler;

public class ChatListener implements Listener {

	@EventHandler
	public void chatEvent(AsyncPlayerChatEvent e) {

		// if in global

		if (e.getPlayer().getPersistentDataContainer()
				.get(new NamespacedKey(Main.getMain(), "channel"), PersistentDataType.STRING)
				.equalsIgnoreCase("global")) {
			// if global disabled in config
			if (Main.isGlobalDisabled()) {
				e.setCancelled(true);
				ChatUtil.noGlobal(e.getPlayer());
				return;
			}

			// handle global formatting
			if (Main.isGlobalFormatted()) {
				e.setCancelled(true);
				if (!PersistentDataHandler.isMuted(e.getPlayer(), PersistentDataHandler.getChannelFromPersistentData(e.getPlayer())))
						ChatUtil.broadcastToGlobal(e.getMessage(), e.getPlayer());
				else ChatUtil.noMsgInMutedChannel(e.getPlayer());
				return;
			}

			// if global can be muted, manually send not formatted message
			if (Main.canGlobalBeMuted()) {
				e.setCancelled(true);
				for (Player p : Bukkit.getOnlinePlayers()) {
					if (!PersistentDataHandler.isMuted(p, "global"))
						ChatUtil.msgNoPrefix(p, "<" + e.getPlayer().getName() + "> " + e.getMessage());
				}
				return;
			}

			return;
		}

		Channel channel = ChannelHandler.getChannelByName(e.getPlayer().getPersistentDataContainer()
				.get(new NamespacedKey(Main.getMain(), "channel"), PersistentDataType.STRING));
		if (channel == null) {
			ChatUtil.movedToGlobal(e.getPlayer());
			e.getPlayer().getPersistentDataContainer().set(new NamespacedKey(Main.getMain(), "channel"),
					PersistentDataType.STRING, "global");
			e.setCancelled(true);
			return;
		}
		ChatUtil.broadcastToChannel(channel.getName(), e.getMessage(), e.getPlayer());
		e.setCancelled(true);

	}

}
