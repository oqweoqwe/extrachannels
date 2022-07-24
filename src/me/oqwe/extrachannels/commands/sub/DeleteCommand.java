package me.oqwe.extrachannels.commands.sub;

import org.bukkit.command.CommandSender;

import me.oqwe.extrachannels.Channel;
import me.oqwe.extrachannels.util.ChannelHandler;
import me.oqwe.extrachannels.util.ChatUtil;

public class DeleteCommand {

	public static void run(CommandSender sender, String[] args) {
		if (sender.hasPermission("extrachannels.delete")) {

			// check for correct args and channel existance
			if (args.length >= 2) {

				if (args[1].equalsIgnoreCase("global")) {
					ChatUtil.msg(sender,
							"&eYou can't delete the global chat as it represents the standard minecraft chat");
					return;
				}

				Channel channel = ChannelHandler.getChannelByName(args[1]);
				if (channel != null) {

					ChatUtil.msg(sender, "&eDeleted " + channel.getDisplayName());
					ChannelHandler.deleteChannel(channel.getName());
					return;

				} else {
					ChatUtil.noChannelMsg(sender);
					return;
				}

			}
			ChatUtil.wrongUse(sender);
			return;

		} else {
			ChatUtil.permissionMsg(sender);
			return;
		}
	}

}
