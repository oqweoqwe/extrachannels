package me.oqwe.extrachannels.util;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.oqwe.extrachannels.Channel;
import me.oqwe.extrachannels.Main;

public class ChatUtil {

	public static void msg(CommandSender sender, String msg) {
		sender.sendMessage(cc(Main.getPrefix() + msg));
	}

	public static void msgNoPrefix(CommandSender sender, String msg) {
		sender.sendMessage(cc(msg));
	}

	public static void msg(Player player, String msg) {
		player.sendMessage(cc(Main.getPrefix() + msg));
	}

	public static void msgNoPrefix(Player player, String msg) {
		player.sendMessage(cc(msg));
	}

	private static String cc(String msg) {
		return ChatColor.translateAlternateColorCodes('&', msg);
	}

	public static void permissionMsg(CommandSender sender) {
		msgNoPrefix(sender, "&cYou do not have permission for this");
	}

	public static void noChannelMsg(CommandSender sender) {
		msgNoPrefix(sender, "&cThat channel doesn't seem to exist");
	}

	public static void channelExistsMsg(CommandSender sender) {
		msg(sender, "&cA channel with that name already exists");
	}

	public static void broadcastToChannel(String channelName, String msg, Player sender) {
		Channel channel = ChannelHandler.getChannelByName(channelName);
		if (channel != null) {

			for (UUID member : channel.getMembers()) {
				if (!PersistentDataHandler.isMuted(Bukkit.getPlayer(member), channel.getName()))
					Bukkit.getPlayer(member)
					.sendMessage(cc(Main.getFormat().replace("<channelprefix>", channel.getPrefix())
							.replace("<playername>", sender.getDisplayName()).replace("<message>", msg)));
			}

		}
	}

	public static void broadcastToGlobal(String msg, Player sender) {
		if (Main.canGlobalBeMuted()) {
			for (Player p : Bukkit.getOnlinePlayers()) {
				if (!PersistentDataHandler.isMuted(p, "global")) {
					p.sendMessage(cc(Main.getGlobalFormat().replace("<channelprefix>", ChannelHandler.getChannelByName("global").getPrefix())
							.replace("<playername>", sender.getDisplayName()).replace("<message>", msg)));
				}
			}
			return;
		}
		
		Bukkit.getOnlinePlayers()
				.forEach(p -> p.sendMessage(cc(Main.getGlobalFormat().replace("<channelprefix>", ChannelHandler.getChannelByName("global").getPrefix())
						.replace("<playername>", sender.getDisplayName()).replace("<message>", msg))));

	}

	public static void wrongUse(CommandSender sender) {
		msgNoPrefix(sender, "&cWrong usage, try /ec help");
	}

	public static void list(CommandSender sender) {

		msg(sender, "&eThe following channels exist");
		Main.getChannelFile().getConfig().getKeys(false)
				.forEach(key -> msgNoPrefix(sender, "&7- " + ChannelHandler.getChannelByName(key).getDisplayName()));

	}

	public static void help(CommandSender sender) {

		Main.getMain().getConfig().getStringList("help")
				.forEach(i -> msgNoPrefix(sender, i.replace("<prefix>", Main.getPrefix())));

	}

	public static String getAllElementsAfterIndex(int index, String[] args) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < args.length; i++) {
			if (i > index) {
				sb.append(args[i] + " ");
			}
		}
		return sb.toString();
	}

	public static void data(CommandSender sender, String channelName) {

		Channel channel = ChannelHandler.getChannelByName(channelName);
		if (channel != null) {

			msg(sender, "&eThe channel " + channel.getDisplayName() + " &ehas the following data associated with it");
			msgNoPrefix(sender, "&7- &6Permission: &e" + channel.getPermission());
			msgNoPrefix(sender, "&7- &6Display name: &e" + channel.getDisplayName());
			msgNoPrefix(sender, "&7- &6Prefix: &e" + channel.getPrefix());

		}

	}

	public static void noGlobal(CommandSender sender) {
		msg(sender, "&eThe channel " + ChannelHandler.getChannelByName("global").getDisplayName()
				+ " &eis disabled, try &6/ec channels &eto see your available channels");
	}

	public static void noGlobal(Player sender) {
		msg(sender, "&eThe channel " + ChannelHandler.getChannelByName("global").getDisplayName()
				+ " &eis disabled, try &6/ec channels &eto see your available channels");
	}

	public static void globalData(CommandSender sender) {

		msg(sender, "&eThe global channel has the following data associated with it ");
		msgNoPrefix(sender, "&7- &6Permission: &eEveryone has permission to use this channel");
		msgNoPrefix(sender, "&7- &6Display name: &e" + ChannelHandler.getChannelByName("global").getDisplayName());
		msgNoPrefix(sender, "&7- &6Prefix: &e" + ChannelHandler.getChannelByName("global").getPrefix());

	}

	public static void movedToGlobal(CommandSender sender) {

		msgNoPrefix(sender,
				"&eYou dont seem to be in a channel, so you have been moved to " + ChannelHandler.getChannelByName("global").getDisplayName());

	}

	public static void movedToGlobal(Player sender) {

		msgNoPrefix(sender,
				"&eYou dont seem to be in a channel, so you have been moved to " + ChannelHandler.getChannelByName("global").getDisplayName());

	}

	public static void alreadyInChannel(CommandSender sender) {
		msgNoPrefix(sender, "&eYou are already in that channel");
	}

	public static void movedToChannel(CommandSender sender, String channelName) {

		msgNoPrefix(sender, "&eYou are now in " + ChannelHandler.getChannelByName(channelName).getDisplayName());

	}

	public static void channelAlreadyMuted(CommandSender sender) {
		msgNoPrefix(sender, "&cYou have already muted that channel");
	}

	public static void channelMuted(CommandSender sender, String channelName) {
		msgNoPrefix(sender, "&eYou have muted " + ChannelHandler.getChannelByName(channelName).getDisplayName());
	}

	public static void playerCommand(CommandSender sender) {
		msg(sender, "&cThat command is only for players");
	}

	public static void notMuted(CommandSender sender) {
		msgNoPrefix(sender, "&eYou haven't muted that channel");
	}

	public static void unmuted(CommandSender sender, String channelName) {
		msgNoPrefix(sender, "&eYou have unmuted " + ChannelHandler.getChannelByName(channelName).getDisplayName());
	}

	public static void muteList(Player p) {
		PersistentDataHandler.updateMuted(p);
		if (PersistentDataHandler.playerHasMutedData(p)) {
			msgNoPrefix(p, "&eYou have the following channels muted");
			PersistentDataHandler.getMutedChannelsAsList(p).forEach(
					channel -> msgNoPrefix(p, "&7- " + ChannelHandler.getChannelByName(channel).getDisplayName()));
		} else
			msgNoPrefix(p, "&eYou do not have any channels muted");

	}
	
	public static void noGlobalMute(CommandSender sender) {
		msgNoPrefix(sender, "&eYou can not mute " + ChannelHandler.getChannelByName("global").getDisplayName());
	}
	
	public static void noAccesToChannel(CommandSender sender) {
		msgNoPrefix(sender, "&eYou do not have acces to this channel");
	}
	
	public static void noMsgInMutedChannel(Player p) {
		msgNoPrefix(p, "&eYou can't send a message in a channel that you have muted");
	}
}
