package me.oqwe.extrachannels.commands.sub;

import org.bukkit.command.CommandSender;

import me.oqwe.extrachannels.Channel;
import me.oqwe.extrachannels.util.ChannelHandler;
import me.oqwe.extrachannels.util.ChatUtil;

public class DataCommand {

	public static void run(CommandSender sender, String[] args) {
		if (sender.hasPermission("extrachannels.data")) {

			if (args.length >= 2) {

				if (args[1].equalsIgnoreCase("global")) {
					ChatUtil.globalData(sender);
					return;
				}

				Channel channel = ChannelHandler.getChannelByName(args[1]);
				if (channel != null) {

					ChatUtil.data(sender, channel.getName());
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
