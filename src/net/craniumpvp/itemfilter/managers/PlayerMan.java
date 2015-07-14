package net.craniumpvp.itemfilter.managers;

import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import net.craniumpvp.itemfilter.util.Category;
import net.craniumpvp.itemfilter.util.ScreenUtil;

public class PlayerMan {
	private HashMap<Player, HashMap<Category, HashMap<Material, Boolean>>> players = new HashMap<>();
	private HashMap<Player, Boolean> itemFilterEnabled = new HashMap<>();
	
	public void addPlayer(Player player) {
		//Query DB for player name, prefs
		HashMap<Category, HashMap<Material, Boolean>> playerPrefs = new HashMap<>(); //Temp for testing; will eventually be result returned by DbInterface
		
		for (Category type : Category.getTypes()) {
			playerPrefs.put(type, new HashMap<Material, Boolean>());
		}
		
		
		HashMap<Material, Boolean> subCat = playerPrefs.get(Category.ARMOR);
			
		for (Material mat : ScreenUtil.getArmor())
			subCat.put(mat, false);
		
		subCat = playerPrefs.get(Category.FOOD);
			
		for (Material mat : ScreenUtil.getFood())
			subCat.put(mat, false);
		
		subCat = playerPrefs.get(Category.POTIONS);
			
		for (Material mat : ScreenUtil.getPotions())
			subCat.put(mat, false);
		
		subCat = playerPrefs.get(Category.TOOLS);
			
		for (Material mat : ScreenUtil.getTools())
			subCat.put(mat, false);
		
		subCat = playerPrefs.get(Category.BLOCKS);
			
		for (Material mat : ScreenUtil.getBlocks())
			subCat.put(mat, false);
			
		subCat = playerPrefs.get(Category.OTHER);
		
		for (Material mat : ScreenUtil.getOther())
			subCat.put(mat, false);
		
		//System.out.println(subCat);
		//subCat.put(Material.MONSTER_EGG, false);
		
		players.put(player, playerPrefs);
		System.out.println("Added player " + player.getDisplayName());
		itemFilterEnabled.put(player, false);
	}
	
	
	public void dropPlayer(Player player) {
		players.remove(player);
	}
	
	public HashMap<Material, Boolean> getPlayerPrefs(Category category, Player player) {
		if (!players.containsKey(player))
			this.addPlayer(player);
		return players.get(player).get(category);
	}
	
	public void changePlayerPref(Player player, Category category, Material material) {
		boolean current = players.get(player).get(category).get(material);
		players.get(player).get(category).put(material, !current);
	}
	
	public boolean getItemFilterEnabled(Player p) {
		return itemFilterEnabled.get(p);
	}
	
	public void changeItemFilterEnabled(Player p) {
		itemFilterEnabled.put(p, !itemFilterEnabled.get(p));
	}
}
