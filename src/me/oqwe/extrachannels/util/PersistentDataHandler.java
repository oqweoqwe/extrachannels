package me.oqwe.extrachannels.util;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;

import me.oqwe.extrachannels.Main;

public class PersistentDataHandler {

	private static NamespacedKey CHANNELKEY = new NamespacedKey(Main.getMain(), "channel");
	private static NamespacedKey MUTEDKEY = new NamespacedKey(Main.getMain(), "muted");

	public static void setChannel(UUID uuid, String channelName) {

		Player p = Bukkit.getServer().getPlayer(uuid);
		if (p.isOnline())
			p.getPersistentDataContainer().set(CHANNELKEY, PersistentDataType.STRING, channelName.toLowerCase());

	}

	public static void set(Player p, String key, String value) {
		if (p.isOnline())
			p.getPersistentDataContainer().set(new NamespacedKey(Main.getMain(), key), PersistentDataType.STRING,
					value);
	}

	public static String get(Player p, String key) {
		if (p.isOnline())
			return p.getPersistentDataContainer().get(new NamespacedKey(Main.getMain(), key),
					PersistentDataType.STRING);
		return null;
	}

	public static void setToGlobal(UUID uuid) {

		Player p = Bukkit.getServer().getPlayer(uuid);
		if (p.isOnline())
			p.getPersistentDataContainer().set(CHANNELKEY, PersistentDataType.STRING, "global");

	}

	public static boolean hasPersistentDataKey(Player p, String key) {
		if (p.getPersistentDataContainer().has(new NamespacedKey(Main.getMain(), key), PersistentDataType.STRING))
			return true;
		return false;
	}

	public static String getChannelFromPersistentData(Player p) {
		return p.getPersistentDataContainer().get(CHANNELKEY, PersistentDataType.STRING);
	}

	public static boolean playerHasMutedData(Player p) {
		return p.getPersistentDataContainer().has(MUTEDKEY, PersistentDataType.STRING);
	}

	public static String getMutedChannelsAsString(Player p) {
		return p.getPersistentDataContainer().get(MUTEDKEY, PersistentDataType.STRING);
	}

	public static List<String> getMutedChannelsAsList(Player p) {
		return new LinkedList<>(Arrays.asList(getMutedChannelsAsString(p).split(",")));
	}

	public static int getMutedAmount(Player p) {
		if (playerHasMutedData(p))
			return getMutedChannelsAsList(p).size();
		return 0;
	}
	
	public static boolean isMuted(Player p, String channelName) {
		if (!playerHasMutedData(p))
			return false;
		return getMutedChannelsAsList(p).contains(channelName);
	}

	// returns true if succes, false if already muted
	public static boolean muteChannel(Player p, String channelName) {

		// assume channel exists, is checked in command executor

		if (playerHasMutedData(p)) {

			// check if channel is already muted
			if (!getMutedChannelsAsList(p).contains(channelName))
				set(p, "muted", getMutedChannelsAsString(p) + "," + channelName);
			else
				return false;

		} else
			set(p, "muted", channelName);
		return true;

	}

	// returns true if success, false if channel isn't muted in the first place
	public static boolean unmuteChannel(Player p, String channelName) {

		// assume channel exists, is checked in command executor

		if (playerHasMutedData(p)) {

			if (getMutedChannelsAsList(p).contains(channelName)) {

				if (getMutedAmount(p) == 1) {
					// only 1 channel muted, delete persistent data key entirely
					p.getPersistentDataContainer().remove(MUTEDKEY);
				} else {

					// multiple muted channels

					// get muted channels and remove the one to be removed
					List<String> newlist = getMutedChannelsAsList(p);
					newlist.removeIf(channel -> channel.equalsIgnoreCase(channelName));

					// create a string version of the new list to be stored in persistent data
					set(p, "muted", getDataStringFromList(newlist));

				}

			} else
				return false;

		} else
			return false;

		return true;
	}

	public static void updateMuted(Player p) {
		if (playerHasMutedData(p)) {
			List<String> newlist = getMutedChannelsAsList(p);
			if (newlist.contains("global") && !Main.canGlobalBeMuted())
				newlist.remove("global");
			newlist.removeIf(channel -> ChannelHandler.getChannelByName(channel) == null);
			if (newlist.size() > 0)
				set(p, "muted", getDataStringFromList(newlist));
			else 
				p.getPersistentDataContainer().remove(MUTEDKEY);
		}
	}

	public static String getDataStringFromList(List<String> list) {
		StringBuilder sb = new StringBuilder();
		list.forEach(channel -> sb.append(channel + ","));
		return sb.toString().substring(0, sb.toString().length() - 1);
	}

}
