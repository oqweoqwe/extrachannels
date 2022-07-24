package me.oqwe.extrachannels.commands.sub;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.oqwe.extrachannels.util.ChatUtil;

public class MutelistCommand {

	public static void run(CommandSender sender, String[] args) {
		if (sender instanceof Player) {

			if (sender.hasPermission("extrachannels.mute")) {

				ChatUtil.muteList((Player) sender);
				return;

			}
			ChatUtil.permissionMsg(sender);
			return;

		}
		ChatUtil.playerCommand(sender);
		return;
	}

}
