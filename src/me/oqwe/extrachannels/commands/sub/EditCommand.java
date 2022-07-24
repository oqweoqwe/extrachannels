package me.oqwe.extrachannels.commands.sub;

import org.bukkit.command.CommandSender;

import me.oqwe.extrachannels.Channel;
import me.oqwe.extrachannels.util.ChannelHandler;
import me.oqwe.extrachannels.util.ChatUtil;

public class EditCommand {

	public static void run(CommandSender sender, String[] args) {
		// if sender has permission to edit, check for min 4 args and switch over 3rd
		// arg to carry out corresponding action
		if (sender.hasPermission("extrachannels.edit")) {

			if (args.length >= 4) {

				Channel channel = ChannelHandler.getChannelByName(args[1]);
				if (channel != null) {

					switch (args[2]) {
					case "permission":
						if (channel.getName().equalsIgnoreCase("global")) {
							ChatUtil.msg(sender, "&cYou can not set a permission for " + channel.getDisplayName());
							return;
						}
						ChannelHandler.editChannelPermission(channel.getName(), args[3]);
						ChatUtil.msg(sender,
								"&eThe permission for " + channel.getDisplayName() + "&eis now " + args[3]);
						return;

					case "prefix":

						ChannelHandler.editChannelPrefix(channel.getName(), ChatUtil.getAllElementsAfterIndex(2, args));
						ChatUtil.msg(sender, "&eThe prefix for " + channel.getDisplayName() + "&eis now "
								+ ChatUtil.getAllElementsAfterIndex(2, args));
						return;

					case "displayname":

						ChannelHandler.editChannelDisplayName(channel.getName(),
								ChatUtil.getAllElementsAfterIndex(2, args));
						ChatUtil.msg(sender, "&eThe display name for " + channel.getDisplayName() + "&eis now "
								+ ChatUtil.getAllElementsAfterIndex(2, args));
						return;

					default:
						ChatUtil.wrongUse(sender);
						return;
					}

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
