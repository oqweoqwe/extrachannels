package me.oqwe.extrachannels;

import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import me.oqwe.extrachannels.commands.ExtraChannels;
import me.oqwe.extrachannels.commands.SelectChannel;
import me.oqwe.extrachannels.listeners.ChatListener;
import me.oqwe.extrachannels.listeners.JoinListener;
import me.oqwe.extrachannels.listeners.QuitListener;
import me.oqwe.extrachannels.util.ChannelFile;
import me.oqwe.extrachannels.util.ChannelHandler;
import me.oqwe.extrachannels.util.PersistentDataHandler;

public class Main extends JavaPlugin {
	
	// mute gui
		
	private static Main main;
	private static ChannelFile channelFile;
	private static Set<Channel> channelObjects;
	
	// data from config loaded into memory
	private static String prefix;
	private static String format;
	private static String globalFormat;
	private static String globalPrefix;
	private static String globalDisplayName;
	private static boolean disableGlobal;
	private static boolean formatGlobal;
	private static boolean globalCanBeMuted;
	
	public void onEnable() {
		
		// ensure that there is a data folder
		if (!getDataFolder().exists()) {
			getDataFolder().mkdirs();
		}
		
		saveDefaultConfig();
		
		// instances of cool shit
		main = this;
		channelFile = new ChannelFile(this);
		
		registerListeners();
		registerCommands();
		
		// load channels into memory
		channelObjects = ChannelHandler.loadChannels();
		
		// refresh member sets in channels
		ChannelHandler.refreshMembers();
		
		reload();
		
	}
	
	public void reload() {
		reloadConfig();
		prefix = getConfig().get("prefix").toString();
		format = getConfig().get("format").toString();
		disableGlobal = getConfig().getBoolean("disable-global");
		formatGlobal = getConfig().getBoolean("format-global");
		globalFormat = getConfig().getString("global-format");
		globalPrefix = getConfig().getString("global-prefix");
		globalDisplayName = getConfig().getString("global-display-name");
		globalCanBeMuted = getConfig().getBoolean("global-mute");
		
		ChannelHandler.refreshMembers();
		Bukkit.getOnlinePlayers().forEach(p -> PersistentDataHandler.updateMuted(p));
	}
	
	private void registerListeners() {
		
		getServer().getPluginManager().registerEvents(new JoinListener(), this);
		getServer().getPluginManager().registerEvents(new QuitListener(), this);
		getServer().getPluginManager().registerEvents(new ChatListener(), this);
		
	}
	
	private void registerCommands() {
		getCommand("channel").setExecutor(new SelectChannel());
		getCommand("extrachannels").setExecutor(new ExtraChannels());
	}
	
	public static Main getMain() {
		return main;
	}
	
	public static ChannelFile getChannelFile() {
		return channelFile;
	}
	
	public static Set<Channel> getChannelObjects() {
		return channelObjects;
	}
	
	public static String getPrefix() {
		return prefix;
	}

	public static String getFormat() {
		return format;
	}

	public static boolean isGlobalDisabled() {
		return disableGlobal;
	}

	public static String getGlobalFormat() {
		return globalFormat;
	}

	public static String getGlobalPrefix() {
		return globalPrefix;
	}

	public static boolean isGlobalFormatted() {
		return formatGlobal;
	}

	public static String getGlobalDisplayName() {
		return globalDisplayName;
	}
	
	public static boolean canGlobalBeMuted() {
		return globalCanBeMuted;
	}
	
}
