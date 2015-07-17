package io.flairhead.itemfilter.managers;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import io.flairhead.itemfilter.ItemFilter;
import io.flairhead.itemfilter.util.Category;
import io.flairhead.itemfilter.util.ScreenUtil;

public class EventMan implements Listener {
	//I fucking hate VCS
	
	@EventHandler
	public void InventoryClick(InventoryClickEvent i) {
		Player p = (Player) i.getWhoClicked();
		String title = i.getClickedInventory().getTitle();
		
		if (title.contains("ItemFilter") && i.getCurrentItem() != null) {
			i.setCancelled(true);
			String[] t = null;
			
			if (title.equals("ItemFilter")) {
				Material itemType = i.getCurrentItem().getType();
				//Logger.getGlobal().log(Level.INFO, itemType.toString());
			
				switch (itemType) {
				case DIAMOND_CHESTPLATE:
					ScreenUtil.displayArmorScreen(p);
					//Logger.getGlobal().log(Level.INFO, "DisplayArmorScreen called");
					break;
				case DIAMOND_SWORD:
					ScreenUtil.displayToolsScreen(p);
					System.out.println("case DisplayTooslScreen");
					break;
				case COOKED_BEEF:
					ScreenUtil.displayFoodScreen(p);
					break;
				case POTION:
					ScreenUtil.displayPotionsScreen(p);
					break;
				case GRASS:
					ScreenUtil.displayBlocksScreen(p);
					break;
				case BOOK:
					ScreenUtil.displayOtherScreen(p);
					break;
				case STAINED_CLAY:
					ItemFilter.PlayerMan.changeItemFilterEnabled(p);
					ScreenUtil.displayMainScreen(p);
				default:
					break;
				}
				
				return;
				
			} else {
				t = title.split(" ");
			}
				
			if (t[1].equals("Armor")) {
				ItemFilter.PlayerMan.changePlayerPref(p, Category.ARMOR, i.getCurrentItem().getType());
				ScreenUtil.displayArmorScreen(p);
			} else if (t[1].equals("Tools")) {
				ItemFilter.PlayerMan.changePlayerPref(p, Category.TOOLS, i.getCurrentItem().getType());
				ScreenUtil.displayToolsScreen(p);
			} else if (t[1].equals("Food")) {
				ItemFilter.PlayerMan.changePlayerPref(p, Category.FOOD, i.getCurrentItem().getType());
				ScreenUtil.displayFoodScreen(p);
			} else if (t[1].equals("Potions")) {
				ItemFilter.PlayerMan.changePlayerPref(p, Category.POTIONS, i.getCurrentItem().getType());
				ScreenUtil.displayPotionsScreen(p);
			} else if (t[1].equals("Blocks")) {
				ItemFilter.PlayerMan.changePlayerPref(p, Category.BLOCKS, i.getCurrentItem().getType());
				ScreenUtil.displayBlocksScreen(p);
			} else if (t[1].equals("Other")) {
				ItemFilter.PlayerMan.changePlayerPref(p, Category.OTHER, i.getCurrentItem().getType());
				ScreenUtil.displayOtherScreen(p);
			} else if (title.equals("ItemFilter") && i.getCurrentItem().getType() == Material.STAINED_CLAY) {
				ItemFilter.PlayerMan.changeItemFilterEnabled(p);
				ScreenUtil.displayMainScreen(p);
			}
	
		}
	}
	
	@EventHandler
	public void onItemPickup(PlayerPickupItemEvent e) {
		Player p = e.getPlayer();
		ItemStack item = e.getItem().getItemStack();
		Category c = Category.getItemCategory(item);
		
		if (c == Category.NOT_CATEGORIZED)
			return;
		
		/*
		System.out.println(item.getType().toString());
		System.out.println(c);
		System.out.println(ItemFilter.PlayerMan.getPlayerPrefs(c, p).get(item.getType()));
		System.out.println(ItemFilter.PlayerMan.getPlayerPrefs(c, p));
		*/
		
		
		if (ItemFilter.PlayerMan.getPlayerPrefs(c, p).containsKey(item.getType())) {
			if (!ItemFilter.PlayerMan.getPlayerPrefs(c, p).get(item.getType()) && ItemFilter.PlayerMan.getItemFilterEnabled(p))
				e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onLogin(PlayerLoginEvent e) {
		Player p = (Player) e.getPlayer();
		ItemFilter.PlayerMan.addPlayer(p);
		Logger.getGlobal().log(Level.INFO, "OnLogin called");
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		Player p = (Player) e.getPlayer();
		ItemFilter.PlayerMan.dropPlayer(p);
		Logger.getGlobal().log(Level.INFO, "OnQuit called");
	}
	
}
