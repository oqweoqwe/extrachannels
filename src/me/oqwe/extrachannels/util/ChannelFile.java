package me.oqwe.extrachannels.util;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import me.oqwe.extrachannels.Main;

public class ChannelFile {

	protected Main main;
	private File file;
	private FileConfiguration config;

	public ChannelFile(Main main) {

		this.main = main;
		
		this.file = new File(main.getDataFolder(), "channels.yml");
		if (!file.exists()) {
			try {
				file.createNewFile();
				
				// set file configuration as default and save
				config = getDefaultConfig();				
				save();
				config = YamlConfiguration.loadConfiguration(file);
				
			} catch (IOException e) {
				e.printStackTrace();
							}
		}
		this.config = YamlConfiguration.loadConfiguration(file);
		
	}
	
	public void save() {
		try {
			config.save(file);
		} catch	(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void resetFile() {
		this.config = getDefaultConfig();
		save();
		config = YamlConfiguration.loadConfiguration(file);
	}
	
	private FileConfiguration getDefaultConfig() {
		FileConfiguration config = new YamlConfiguration();
		
		config.set("staff.permission", "extrachannels.channel.staff");
		config.set("staff.displayname", "&c&lSTAFF&r ");
		config.set("staff.prefix", "&7&l<&c&lSTAFF&7&l>&r ");
		
		config.set("donor.permission", "extrachannels.channel.donor");
		config.set("donor.displayname", "&a&lDONOR&r ");
		config.set("donor.prefix", "&7&l<&a&lDONOR&7&l>&r ");
		
		return config;
	}
	
	public void editPermissionInFile(String channelName, String permission) {
		try {
			config.set(channelName+".permission", permission);
			save();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Something went wrong when editing permission in " + channelName);
		}
	}
	
	public void editDisplayNameInFile(String channelName, String displayName) {
		try {
			config.set(channelName+".displayname", displayName);
			save();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Something went wrong when editing displayname in " + channelName);
		}
	}
	
	public void editPrefixInFile(String channelName, String prefix) {
		try {
			config.set(channelName+".prefix", prefix);
			save();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Something went wrong when editing prefix in " + channelName);
		}
	}
	
	public void createChannelInFile(String channelName, String permission, String displayName, String prefix) {
		try {
			config.set(channelName+".permission", permission);
			config.set(channelName+".displayname", displayName);
			config.set(channelName+".prefix", prefix);
			save();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Something went wrong when creating " + channelName);
		}
	}
	
	public void deleteChannelInFile(String channelName) {
		try {
			config.set(channelName, null);
			save();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Something went wrong when deleting " + channelName);
		}
		
	}
	
	public File getFile() {
		return this.file;
	}
	
	public FileConfiguration getConfig() {
		return config;
	}

}
 