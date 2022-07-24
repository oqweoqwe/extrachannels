package me.oqwe.extrachannels.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.oqwe.extrachannels.Channel;
import me.oqwe.extrachannels.util.ChannelHandler;
import me.oqwe.extrachannels.util.ChatUtil;
import me.oqwe.extrachannels.util.PersistentDataHandler;

public class SelectChannel implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (sender.hasPermission("extrachannels.select")) {

				if (args.length >= 1) {

					Channel channel = ChannelHandler.getChannelByName(args[0]);
					if (channel != null) {

						if (!PersistentDataHandler.getChannelFromPersistentData(p)
								.equalsIgnoreCase(channel.getName())) {

							if (sender.hasPermission(channel.getPermission()) || args[0].equalsIgnoreCase("global")) {

								PersistentDataHandler.setChannel(p.getUniqueId(), args[0]);
								ChatUtil.movedToChannel(sender, args[0]);
								return true;
							}
							ChatUtil.noAccesToChannel(sender);
							return true;

						}
						ChatUtil.alreadyInChannel(sender);
						return true;

					}
					ChatUtil.noChannelMsg(sender);
					return true;

				}
				ChatUtil.wrongUse(sender);
				return true;
			}
			ChatUtil.permissionMsg(sender);
			return true;
		}
		ChatUtil.playerCommand(sender);
		return true;
	}

}

/*
 * // changing to global if (args[0].equalsIgnoreCase("global")) {
 * 
 * // if global is enabled, else send no global msg if
 * (!Main.isGlobalDisabled()) {
 * 
 * // if already in global, else set to global if
 * (PersistentDataHandler.getChannelFromPersistentData(p).equalsIgnoreCase(
 * "global")) {
 * 
 * ChatUtil.alreadyInChannel(sender); return true;
 * 
 * }
 * 
 * // set to global PersistentDataHandler.setChannel(p.getUniqueId(), "global");
 * ChatUtil.msgNoPrefix(sender, "&eYou are now in " +
 * Main.getGlobalDisplayName()); return true;
 * 
 * } ChatUtil.noGlobal(sender); return true; }
 */
