package me.oqwe.extrachannels.commands.sub;

import org.bukkit.command.CommandSender;

import me.oqwe.extrachannels.Channel;
import me.oqwe.extrachannels.util.ChannelHandler;
import me.oqwe.extrachannels.util.ChatUtil;

public class CreateCommand {

	public static void run(CommandSender sender, String[] args) {
		if (sender.hasPermission("extrachannels.create")) {

			// check for min 5 args and check if channel exists
			if (args.length >= 5) {

				if (args[1].equalsIgnoreCase("global")) {
					// tried to create global
					ChatUtil.msg(sender, "&eYou can't use the name &cglobal &efor a channel");
					return;
				}

				if (!args[1].toLowerCase().matches("[a-z]+")) {

					ChatUtil.msg(sender, "&cYou can only use letters in channel names");
					return;

				}

				Channel channel = ChannelHandler.getChannelByName(args[1]);
				if (channel == null) {

					ChannelHandler.createChannel(args[1], args[2], args[3], ChatUtil.getAllElementsAfterIndex(3, args));
					ChatUtil.msg(sender,
							"&eCreated channel " + args[1] + " with permission " + args[2] + ", displayname " + args[3]
									+ "&r&e and prefix " + ChatUtil.getAllElementsAfterIndex(3, args));
					return;

				} else {
					ChatUtil.channelExistsMsg(sender);
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
