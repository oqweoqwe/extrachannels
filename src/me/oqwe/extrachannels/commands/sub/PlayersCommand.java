package me.oqwe.extrachannels.commands.sub;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import me.oqwe.extrachannels.Channel;
import me.oqwe.extrachannels.util.ChannelHandler;
import me.oqwe.extrachannels.util.ChatUtil;

public class PlayersCommand {

	public static void run(CommandSender sender, String[] args) {
		if (sender.hasPermission("extrachannels.players")) {

			if (args.length >= 2) {

				if (args[1].equalsIgnoreCase("global")) {
					ChatUtil.msg(sender, "&eEveryone has acces to " + ChannelHandler.getChannelByName("global").getDisplayName());
					return;
				}

				Channel channel = ChannelHandler.getChannelByName(args[1]);
				if (channel != null) {

					ChatUtil.msg(sender,
							"&eThe online players with acces to " + channel.getDisplayName() + " are");
					channel.getMembers().forEach(member -> ChatUtil.msgNoPrefix(sender,
							"&7- &e" + Bukkit.getPlayer(member).getDisplayName()));
					return;

				}
				ChatUtil.noChannelMsg(sender);
				return;

			}
			ChatUtil.wrongUse(sender);
			return;
		}
		ChatUtil.permissionMsg(sender);
		return;
	}
	
}
