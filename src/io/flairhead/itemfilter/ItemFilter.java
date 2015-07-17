package io.flairhead.itemfilter;

import java.util.Collection;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import io.flairhead.itemfilter.managers.*;
import io.flairhead.itemfilter.util.*;


public class ItemFilter extends JavaPlugin {
	public static ChatUtils Chat;
	public static PlayerMan PlayerMan;
	public static EventMan EventMan;
	
	@Override
	public void onEnable() {
		this.saveDefaultConfig();
		
		ItemFilter.Chat = new ChatUtils();
		ItemFilter.PlayerMan = new PlayerMan();
		ItemFilter.EventMan = new EventMan();
		
		Chat.initChatUtils(this.getConfig());
		this.getServer().getPluginManager().registerEvents(EventMan, this);
		Collection<Player> players = (Collection<Player>) this.getServer().getOnlinePlayers();
		for (Player p : players) {
			this.PlayerMan.addPlayer(p);
		}
	}
	
	@Override
	public void onDisable() {
		
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("itemfilter") && sender instanceof Player) {
			Player p = (Player) sender;
			ScreenUtil.displayMainScreen(p);
			return true;
		} 
		return false; 
	}
}
