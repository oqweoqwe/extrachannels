package me.oqwe.extrachannels.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import me.oqwe.extrachannels.commands.sub.ChannelsCommand;
import me.oqwe.extrachannels.commands.sub.CreateCommand;
import me.oqwe.extrachannels.commands.sub.DataCommand;
import me.oqwe.extrachannels.commands.sub.DeleteCommand;
import me.oqwe.extrachannels.commands.sub.EditCommand;
import me.oqwe.extrachannels.commands.sub.HelpCommand;
import me.oqwe.extrachannels.commands.sub.ListCommand;
import me.oqwe.extrachannels.commands.sub.MuteCommand;
import me.oqwe.extrachannels.commands.sub.MutelistCommand;
import me.oqwe.extrachannels.commands.sub.PlayersCommand;
import me.oqwe.extrachannels.commands.sub.ReloadCommand;
import me.oqwe.extrachannels.commands.sub.UnmuteCommand;
import me.oqwe.extrachannels.util.ChatUtil;

public class ExtraChannels implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		// if there are args, otherwise return false
		if (args.length >= 1) {

			// switch over first arg
			switch (args[0].toLowerCase()) {
			case "edit":

				EditCommand.run(sender, args);
				return true;

			case "create":

				CreateCommand.run(sender, args);
				return true;

			case "delete":

				DeleteCommand.run(sender, args);
				return true;

			case "help":
				
				HelpCommand.run(sender, args);
				return true;

			case "list":

				ListCommand.run(sender, args);
				return true;

			case "reload":

				ReloadCommand.run(sender, args);
				return true;
				
			case "data":

				DataCommand.run(sender, args);
				return true;

			case "players":

				PlayersCommand.run(sender, args);
				return true;
			
			case "channels":

				ChannelsCommand.run(sender, args);
				return true;

			case "mute":

				MuteCommand.run(sender, args);
				return true;

			case "unmute":
				
				UnmuteCommand.run(sender, args);
				return true;
			
			case "mutelist":
				
				MutelistCommand.run(sender, args);
				return true;				
				
			default:
				ChatUtil.wrongUse(sender);
				return true;
			}

		}
		ChatUtil.wrongUse(sender);
		return true;
	}

}
