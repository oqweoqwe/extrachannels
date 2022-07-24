package me.oqwe.extrachannels.commands.sub;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.oqwe.extrachannels.Main;
import me.oqwe.extrachannels.util.ChannelHandler;
import me.oqwe.extrachannels.util.ChatUtil;
import me.oqwe.extrachannels.util.PersistentDataHandler;

public class MuteCommand {

	public static void run(CommandSender sender, String[] args) {
		if (sender instanceof Player) {

			if (sender.hasPermission("extrachannels.mute")) {

				if (args.length >= 2) {

					if (ChannelHandler.getChannelByName(args[1]) != null) {

						if (!Main.canGlobalBeMuted() && args[1].equalsIgnoreCase("global")) {
							ChatUtil.noGlobalMute(sender);
							return;
						}

						if (PersistentDataHandler.muteChannel((Player) sender, args[1])) {
							ChatUtil.channelMuted(sender, args[1]);
							return;
						}
						ChatUtil.channelAlreadyMuted(sender);
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
		ChatUtil.playerCommand(sender);
		return;
	}

}
