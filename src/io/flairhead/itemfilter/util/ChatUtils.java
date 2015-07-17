package io.flairhead.itemfilter.util;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;

public class ChatUtils {
	String prefix;
	
	public void initChatUtils(FileConfiguration config) {
		this.prefix = config.getString("chat-message-prefix");
	}
	
	public void addChatMsg(Player target, String message) {
		String msg = ChatColor.translateAlternateColorCodes('&', prefix) + " " + message;
		target.sendMessage(msg);
	}
}
