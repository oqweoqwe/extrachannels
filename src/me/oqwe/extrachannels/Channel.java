package me.oqwe.extrachannels;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.bukkit.ChatColor;

public class Channel {

	private String name;
	private String permission;
	private String displayName;
	private String prefix;
	
	private Set<UUID> members;
	
	public Channel(String name, String permission, String displayName, String prefix) {
		
		this.name = name;
		this.permission = permission;
		this.displayName = displayName;
		this.prefix = prefix +"&r";
		
		this.members = new HashSet<UUID>();
		
		Main.getChannelFile().createChannelInFile(name, permission, displayName, prefix);
		
	}
	
	public Set<UUID> getMembers() {
		return members;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public void addMember(UUID uuid) {
		members.add(uuid);
	}
	
	public void removeMember(UUID uuid) {
		members.remove(uuid);
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getPermission() {
		return this.permission;
	}
	
	public String getDisplayName() {
		return this.displayName;
	}
	
	public String getPrefix() {
		return this.prefix;
	}
	
	public String cc(String msg) {
		return ChatColor.translateAlternateColorCodes('&', msg);
	}
	
}
