package me.oqwe.extrachannels.commands.sub;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.oqwe.extrachannels.Channel;
import me.oqwe.extrachannels.Main;
import me.oqwe.extrachannels.util.ChatUtil;

public class ChannelsCommand {

	public static void run(CommandSender sender, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (sender.hasPermission("extrachannels.channels")) {

				ChatUtil.msgNoPrefix(sender, "&eYou currently have acces to the following channels");

				for (Channel channel : Main.getChannelObjects()) {

					if (channel.getMembers().contains(p.getUniqueId())) {
						ChatUtil.msgNoPrefix(sender, "&7- " + channel.getDisplayName());
					}

				}
				return;

			}
			ChatUtil.permissionMsg(sender);
			return;
		}
		ChatUtil.playerCommand(sender);
		return;
	}

}
