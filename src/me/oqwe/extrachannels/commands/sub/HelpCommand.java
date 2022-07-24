package me.oqwe.extrachannels.commands.sub;

import org.bukkit.command.CommandSender;

import me.oqwe.extrachannels.util.ChatUtil;

public class HelpCommand {

	public static void run(CommandSender sender, String[] args) {
		if (sender.hasPermission("extrachannels.help")) {
			ChatUtil.help(sender);
			return;
		}
		ChatUtil.permissionMsg(sender);
		return;
	}
	
}
