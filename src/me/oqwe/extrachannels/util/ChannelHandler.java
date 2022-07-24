package me.oqwe.extrachannels.util;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import me.oqwe.extrachannels.Channel;
import me.oqwe.extrachannels.Main;

public class ChannelHandler {

	// returns null if there are duplicate channel names
	public static Set<Channel> loadChannels() {
		Set<Channel> channelObjects = new HashSet<Channel>();
		YamlConfiguration config = YamlConfiguration.loadConfiguration(Main.getChannelFile().getFile());

		for (String channel : config.getKeys(false)) {
			channelObjects.add(new Channel(channel, config.get(channel + ".permission").toString(),
					config.get(channel + ".displayname").toString(), config.get(channel + ".prefix").toString()));
		}
		if (!config.getKeys(false).contains("global"))
			channelObjects.add(new Channel("global", "", "&aGLOBAL", "&aGLOBAL &7>"));
		return channelObjects;
	}

	public static void createChannel(String channelName, String permission, String displayName, String prefix) {

		Main.getChannelObjects().add(new Channel(channelName, permission, displayName, prefix));
		refreshMembers();

	}

	public static void deleteChannel(String channelName) {

		Main.getChannelFile().deleteChannelInFile(channelName);
		Main.getChannelObjects().removeIf(channel -> (channel.getName().equalsIgnoreCase(channelName)));
		refreshMembers();

	}

	public static void editChannelPermission(String channelName, String permission) {

		Channel channel = getChannelByName(channelName);
		if (channel == null)
			return;
		
		channel.setPermission(permission);
		Main.getChannelFile().editPermissionInFile(channel.getName(), permission);
		refreshMembers();
		
	}

	public static void editChannelDisplayName(String channelName, String displayName) {

		Channel channel = getChannelByName(channelName);
		if (channel == null)
			return;
		
		channel.setDisplayName(displayName);
		Main.getChannelFile().editDisplayNameInFile(channel.getName(), displayName);
		refreshMembers();
		
	}
	
	public static void editChannelPrefix(String channelName, String prefix) {

		Channel channel = getChannelByName(channelName);
		if (channel == null)
			return;
		
		channel.setPrefix(prefix+"&r");
		Main.getChannelFile().editPrefixInFile(channel.getName(), prefix+"&r");
		refreshMembers();
		
	}

	public static void reloadChannelsFromFile() {

		Main.getChannelObjects().clear();
		for (Channel channel : loadChannels()) {
			Main.getChannelObjects().add(channel);
		}
		refreshMembers();

	}

	// returns null if no channel found
	public static Channel getChannelByName(String channelName) {
		for (Channel channel : Main.getChannelObjects()) {
			if (channelName.equalsIgnoreCase(channel.getName()))
				return channel;
		}
		return null;
	}
	
	public static void refreshMembers() {
		for (Channel channel : Main.getChannelObjects()) {
			for (Player p : Bukkit.getServer().getOnlinePlayers()) {
				if (channel.getMembers().contains(p.getUniqueId()) && !p.hasPermission(channel.getPermission())) {
					channel.removeMember(p.getUniqueId());
				}
				if (p.hasPermission(channel.getPermission()))
					channel.addMember(p.getUniqueId());
			}
		}
	}

}
