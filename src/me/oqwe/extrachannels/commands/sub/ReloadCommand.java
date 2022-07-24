package me.oqwe.extrachannels.commands.sub;

import org.bukkit.command.CommandSender;

import me.oqwe.extrachannels.Main;
import me.oqwe.extrachannels.util.ChatUtil;

public class ReloadCommand {

	public static void run(CommandSender sender, String[] args) {
		if (sender.hasPermission("extrachannels.reload")) {
			Main.getMain().reload();
			ChatUtil.msg(sender, "&eReloaded config");
			return;
		}
		ChatUtil.permissionMsg(sender);
		return;
	}
	
}
