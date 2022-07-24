package me.oqwe.extrachannels.commands.sub;

import org.bukkit.command.CommandSender;

import me.oqwe.extrachannels.util.ChatUtil;

public class ListCommand {

	public static void run(CommandSender sender, String[] args) {
		if (sender.hasPermission("extrachannels.list")) {
			ChatUtil.list(sender);
			return;
		}
		ChatUtil.permissionMsg(sender);
		return;
	}
	
}
