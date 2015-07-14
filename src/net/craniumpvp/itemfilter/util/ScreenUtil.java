package net.craniumpvp.itemfilter.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
//import java.util.logging.Level;
//import java.util.logging.Logger;

import net.craniumpvp.itemfilter.ItemFilter;

import org.apache.commons.lang.ArrayUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;

public class ScreenUtil {
	
	public static String arrayStr(ItemStack[] items) {
		String result = "{ ";
		for (ItemStack item : items) {
			if (item != null) {
				result += item.getType().toString() + " ";
			} else {
				result += "NULL ";
			}
		}
		return result + "}";
	}
	
	public static void displayMainScreen(Player p) {
		Inventory inv = Bukkit.createInventory(null, 9, "ItemFilter"); 
		String[] names = {"§r§2Armor", "§r§2Tools", "§r§2Food", "§r§2Potions", "§r§2Blocks", "§r§2Other"};
		String[] lores = {"§r§7Filter armor items.", "§r§7Filter tools/weapons.", "§r§7Filter food.", "§r§7Filter potions.", "§r§7Filter blocks.", 
				"§r§7Filter everything else."};
		
		ItemStack[] itemsTemp = {
				new ItemStack(Material.DIAMOND_CHESTPLATE), new ItemStack(Material.DIAMOND_SWORD), new ItemStack(Material.COOKED_BEEF), 
				new ItemStack(Material.POTION), new ItemStack(Material.GRASS), new ItemStack(Material.BOOK), new ItemStack(Material.AIR),
				new ItemStack(Material.AIR)};
		
		ArrayList<ItemStack> items = new ArrayList<>(Arrays.asList(itemsTemp));
		
		int i = 0;
		for (ItemStack item : items) {
			if (item.getType() != Material.AIR) {
				List<String> lore = new ArrayList<>();
				lore.add(lores[i]);
			
				ItemMeta meta = item.getItemMeta();
				meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES); //Hide damage value for swords
				meta.setLore(lore);
				meta.setDisplayName(names[i]);
				item.setItemMeta(meta);
			
				i++;
			}
		}
		
	    
	    ItemStack stained_clay;
	    ItemMeta meta;
	    
	    if (ItemFilter.PlayerMan.getItemFilterEnabled(p)) {
	    	stained_clay = new ItemStack(Material.STAINED_CLAY, 1, (short) 13);
	    	meta = stained_clay.getItemMeta();
	    	
	    	List<String> lore = new ArrayList<>();
	    	lore.add("§r§4Click to disable ItemFilter.");
	    	
	    	meta.setLore(lore);
	    	meta.setDisplayName("§r§2ItemFilter Enabled");
	    } else {
	    	stained_clay = new ItemStack(Material.STAINED_CLAY, 1, (short) 14);
	    	meta = stained_clay.getItemMeta();
	    	
	    	List<String> lore = new ArrayList<>();
	    	lore.add("§r§2Click to enabled ItemFilter.");
	    	
	    	meta.setLore(lore);
	    	meta.setDisplayName("§r§4ItemFilter Disabled");
	    }
	    
	    meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
	    stained_clay.setItemMeta(meta);
	    
	    items.add(stained_clay);
	    ItemStack[] itemsFinal = new ItemStack[items.size()];
	    
	    for (int k = 0; k < items.size(); k++)
	    	itemsFinal[k] = items.get(k);
	    
	    inv.setContents(itemsFinal);
	    p.openInventory(inv);
	}
	
	
	public static void displayToolsScreen(Player p) {
		//Logger.getGlobal().log(Level.INFO, "Within method displayArmor");
		
		Inventory inv = Bukkit.createInventory(null, 4 * 9, "ItemFilter: Tools");
		
		String[] item_strs = {"WOOD_SPADE", "STONE_SPADE", "IRON_SPADE", "GOLD_SPADE", "DIAMOND_SPADE", "WOOD_PICKAXE", "STONE_PICKAXE", 
				"IRON_PICKAXE", "GOLD_PICKAXE", "DIAMOND_PICKAXE", "WOOD_AXE", "STONE_AXE", "IRON_AXE", "GOLD_AXE", "DIAMOND_AXE", "WOOD_HOE", 
				"STONE_HOE", "IRON_HOE", "GOLD_HOE", "DIAMOND_HOE", "WOOD_SWORD", "STONE_SWORD", "IRON_SWORD", "GOLD_SWORD", "DIAMOND_SWORD", "BOW", 
				"ARROW", "FISHING_ROD", "COMPASS", "SHEARS", "LEASH", "NAME_TAG"};
		
		ItemStack[] items = new ItemStack[item_strs.length];
		
		for (int i = 0; i < item_strs.length; i++) {
			items[i] = new ItemStack(Material.getMaterial(item_strs[i]));
		}
		
		List<String> lore = new ArrayList<String>();
		
		for (ItemStack item : items) {
			HashMap<Material, Boolean> prefs = ItemFilter.PlayerMan.getPlayerPrefs(Category.TOOLS, p);
			
			if (prefs.get(item.getType())) {
				ItemMeta meta = item.getItemMeta();
				meta.addEnchant(Enchantment.ARROW_DAMAGE, 1, false); //Add enchantment glow
				meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES); //Hide damage value for swords
				meta.addItemFlags(ItemFlag.HIDE_ENCHANTS); //Hide enchantment name so that only the glow remains
				lore.add("§2Item enabled");
				meta.setLore(lore);
				item.setItemMeta(meta);
			} else {
				ItemMeta meta = item.getItemMeta();
				lore.add("§4Item disabled");
				meta.setLore(lore);
				item.setItemMeta(meta);
			}
			
			lore.clear();
		}

		inv.setContents(items);
        p.openInventory(inv);
	}
	
	public static Material[] getTools() {
		String[] item_strs = {"WOOD_SPADE", "STONE_SPADE", "IRON_SPADE", "GOLD_SPADE", "DIAMOND_SPADE", "WOOD_PICKAXE", "STONE_PICKAXE", 
				"IRON_PICKAXE", "GOLD_PICKAXE", "DIAMOND_PICKAXE", "WOOD_AXE", "STONE_AXE", "IRON_AXE", "GOLD_AXE", "DIAMOND_AXE", "WOOD_HOE", 
				"STONE_HOE", "IRON_HOE", "GOLD_HOE", "DIAMOND_HOE", "WOOD_SWORD", "STONE_SWORD", "IRON_SWORD", "GOLD_SWORD", "DIAMOND_SWORD", "BOW", 
				"ARROW", "FISHING_ROD", "COMPASS", "SHEARS", "LEASH", "NAME_TAG"};
		
		Material[] materials = new Material[item_strs.length];
		
		for (int i = 0; i < item_strs.length; i++) {
			materials[i] = Material.getMaterial(item_strs[i]);
		}
		
		return materials;
	}
	
	public static void displayBlocksScreen(Player p) {
		Inventory inv = Bukkit.createInventory(null, 5 * 9, "ItemFilter: Blocks");

		String[] item_strs = {"STONE", "GRASS", "DIRT", "COBBLESTONE", "WOOD", "SAND", "GRAVEL", "DIAMOND_ORE", "GOLD_ORE", "IRON_ORE", "LAPIS_ORE", 
				"REDSTONE_ORE", "LOG", "SPONGE", "GLASS", "LAPIS_BLOCK", "DIAMOND_BLOCK", "GOLD_BLOCK", "IRON_BLOCK", "COAL_BLOCK", "REDSTONE_BLOCK", 
				"WOOL", "SAND", "STONE_SLAB2", "BRICK", "BOOKSHELF", "MOSSY_COBBLESTONE", "OBSIDIAN", "WOOD_STAIRS", "ICE", "CLAY", "STAINED_CLAY", 
				"PUMPKIN", "NETHERRACK", "SOUL_SAND", "GLOWSTONE", "JACK_O_LANTERN", "SMOOTH_BRICK", "PACKED_ICE", "HAY_BLOCK", "SANDSTONE"};
		
		ItemStack[] items = new ItemStack[item_strs.length];
		
		for (int i = 0; i < item_strs.length; i++) {
			items[i] = new ItemStack(Material.getMaterial(item_strs[i]));
		}
		
		List<String> lore = new ArrayList<String>();
		
		for (ItemStack item : items) {
			HashMap<Material, Boolean> prefs = ItemFilter.PlayerMan.getPlayerPrefs(Category.BLOCKS, p);
			//System.out.println(prefs.toString());
		
			if (prefs.get(item.getType())) {
				ItemMeta meta = item.getItemMeta();
				meta.addEnchant(Enchantment.ARROW_DAMAGE, 1, false); //Add enchantment glow
				meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES); //Hide damage value for swords
				meta.addItemFlags(ItemFlag.HIDE_ENCHANTS); //Hide enchantment name so that only the glow remains
				lore.add("§2Item enabled");
				meta.setLore(lore);
				item.setItemMeta(meta);
			} else {
				ItemMeta meta = item.getItemMeta();
				lore.add("§4Item disabled");
				meta.setLore(lore);
				item.setItemMeta(meta);
			}
			
			lore.clear();
		}

		inv.setContents(items);
		
		p.openInventory(inv);
	}
	
	public static Material[] getBlocks() {
		String[] item_strs = {"STONE", "GRASS", "DIRT", "COBBLESTONE", "WOOD", "SAND", "GRAVEL", "DIAMOND_ORE", "GOLD_ORE", "IRON_ORE", "LAPIS_ORE", 
				"REDSTONE_ORE", "LOG", "SPONGE", "GLASS", "LAPIS_BLOCK", "DIAMOND_BLOCK", "GOLD_BLOCK", "IRON_BLOCK", "COAL_BLOCK", "REDSTONE_BLOCK", 
				"WOOL", "SAND", "STONE_SLAB2", "BRICK", "BOOKSHELF", "MOSSY_COBBLESTONE", "OBSIDIAN", "WOOD_STAIRS", "ICE", "CLAY", "STAINED_CLAY", 
				"PUMPKIN", "NETHERRACK", "SOUL_SAND", "GLOWSTONE", "JACK_O_LANTERN", "SMOOTH_BRICK", "PACKED_ICE", "HAY_BLOCK", "SANDSTONE"};
		
		Material[] materials = new Material[item_strs.length];
		
		for (int i = 0; i < item_strs.length; i++) {
			materials[i] = Material.getMaterial(item_strs[i]);
		}
		
		return materials;
	}
	
	public static void displayFoodScreen(Player p) {
		Inventory inv = Bukkit.createInventory(null, 2 * 9, "ItemFilter: Food");
		
		String[] item_strs = {"APPLE", "GOLDEN_APPLE", "RAW_BEEF", "COOKED_BEEF", "PORK", "GRILLED_PORK", "RAW_CHICKEN", "COOKED_CHICKEN", "RAW_FISH", 
				"COOKED_FISH", "COOKIE", "MELON", "ROTTEN_FLESH", "CARROT_ITEM", "POTATO_ITEM", "BAKED_POTATO", "POISONOUS_POTATO", "PUMPKIN_PIE"};
		
		ItemStack[] items = new ItemStack[item_strs.length];
		
		for (int i = 0; i < item_strs.length; i++) {
			items[i] = new ItemStack(Material.getMaterial(item_strs[i]));
		}
		
		List<String> lore = new ArrayList<String>();
		
		for (ItemStack item : items) {
			HashMap<Material, Boolean> prefs = ItemFilter.PlayerMan.getPlayerPrefs(Category.FOOD, p);
			
			if (prefs.get(item.getType())) {
				ItemMeta meta = item.getItemMeta();
				meta.addEnchant(Enchantment.ARROW_DAMAGE, 1, false); //Add enchantment glow
				meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES); //Hide damage value for swords
				meta.addItemFlags(ItemFlag.HIDE_ENCHANTS); //Hide enchantment name so that only the glow remains
				lore.add("§2Item enabled");
				meta.setLore(lore);
				item.setItemMeta(meta);
			} else {
				ItemMeta meta = item.getItemMeta();
				lore.add("§4Item disabled");
				meta.setLore(lore);
				item.setItemMeta(meta);
			}
			
			lore.clear();
		}

		inv.setContents(items);
		p.openInventory(inv);
	}
	
	public static Material[] getFood() {
		String[] item_strs = {"APPLE", "GOLDEN_APPLE", "RAW_BEEF", "COOKED_BEEF", "PORK", "GRILLED_PORK", "RAW_CHICKEN", "COOKED_CHICKEN", "RAW_FISH", 
				"COOKED_FISH", "COOKIE", "MELON", "ROTTEN_FLESH", "CARROT_ITEM", "POTATO_ITEM", "BAKED_POTATO", "POISONOUS_POTATO", "PUMPKIN_PIE"};
		
		Material[] materials = new Material[item_strs.length];
		
		for (int i = 0; i < item_strs.length; i++) {
			materials[i] = Material.getMaterial(item_strs[i]);
		}
		
		return materials;
	}
	
	public static void displayPotionsScreen(Player p) {
		Inventory inv = Bukkit.createInventory(null, 27, "ItemFilter: Potions");
		
		ItemStack[] items = new ItemStack[25];
		ItemStack[] potions = new ItemStack[14];
		
		int i = 0;
		for (PotionType potionType : PotionType.values()) {
			//Logger.getGlobal().log(Level.INFO, potionType.toString());
			Potion pt = new Potion(potionType);
			//Logger.getGlobal().log(Level.INFO, pt.toString());
			//Logger.getGlobal().log(Level.INFO, pt.toItemStack(1).getType().toString());
			potions[i] = pt.toItemStack(1);
			i++;
		}
		
		//Logger.getGlobal().log(Level.INFO, arrayStr(potions));
		
		Material[] toAdd = {Material.GHAST_TEAR, Material.GLASS_BOTTLE, Material.POTION, Material.SPIDER_EYE, Material.FERMENTED_SPIDER_EYE, 
				Material.BLAZE_POWDER, Material.CAULDRON_ITEM, Material.MAGMA_CREAM, Material.SPECKLED_MELON, Material.BREWING_STAND};
		ItemStack[] toAddFinal = new ItemStack[toAdd.length];
		
		for (int r = 0; r < toAdd.length; r++)
			toAddFinal[r] = new ItemStack(toAdd[r]);
		
		//Logger.getGlobal().log(Level.INFO, arrayStr(toAddFinal));
		
		items = (ItemStack[]) ArrayUtils.addAll(potions, toAddFinal);
		
		//Logger.getGlobal().log(Level.INFO, arrayStr(items));
		
		List<String> lore = new ArrayList<String>();
		for (ItemStack item : items) {
			HashMap<Material, Boolean> prefs = ItemFilter.PlayerMan.getPlayerPrefs(Category.POTIONS, p);
			if (item != null) {
				if (prefs.get(item.getType())) {
					ItemMeta meta = item.getItemMeta();
					meta.addEnchant(Enchantment.ARROW_DAMAGE, 1, false); //Add enchantment glow
					meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES); //Hide damage value for swords
					meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
					meta.addItemFlags(ItemFlag.HIDE_ENCHANTS); //Hide enchantment name so that only the glow remains
					lore.add("§2Item enabled");
					meta.setLore(lore);
					item.setItemMeta(meta);
				} else {
					ItemMeta meta = item.getItemMeta();
					lore.add("§4Item disabled");
					meta.setLore(lore);
					item.setItemMeta(meta);
				}
			
				lore.clear();
			}
		}
		
		inv.setContents(items);
		p.openInventory(inv);
	}
	
	public static Material[] getPotions() {
		Material[] materials = {Material.POTION, Material.POTION, Material.POTION, Material.POTION, Material.POTION, Material.POTION, Material.POTION, 
				Material.POTION, Material.POTION, Material.POTION, Material.POTION, Material.POTION, Material.POTION, Material.POTION, 
				Material.GHAST_TEAR, Material.GLASS_BOTTLE, Material.POTION, Material.SPIDER_EYE, Material.FERMENTED_SPIDER_EYE, Material.BLAZE_POWDER, 
				Material.CAULDRON_ITEM, Material.MAGMA_CREAM, Material.SPECKLED_MELON, Material.BREWING_STAND};
		
		return materials;
	}
	
	public static void displayArmorScreen(Player p) {
		Inventory inv = Bukkit.createInventory(null, 3 * 9, "ItemFilter: Armor");
		
		String[] item_strs = {"LEATHER_HELMET", "LEATHER_CHESTPLATE", "LEATHER_LEGGINGS", "LEATHER_BOOTS", "IRON_HELMET", "IRON_CHESTPLATE", 
				"IRON_LEGGINGS", "IRON_BOOTS", "GOLD_HELMET", "GOLD_CHESTPLATE", "GOLD_LEGGINGS", "GOLD_BOOTS", "DIAMOND_HELMET", 
				"DIAMOND_CHESTPLATE", "DIAMOND_LEGGINGS", "DIAMOND_BOOTS", "IRON_BARDING", "GOLD_BARDING", "DIAMOND_BARDING"};
		ItemStack[] items = new ItemStack[item_strs.length];
		
		for (int i = 0; i < item_strs.length; i++) {
			//Logger.getGlobal().log(Level.INFO, item_strs[i]);
			items[i] = new ItemStack(Material.getMaterial(item_strs[i]));
			//Logger.getGlobal().log(Level.INFO, items[i].toString());
		}
		
		List<String> lore = new ArrayList<String>();
		for (ItemStack item : items) {
			HashMap<Material, Boolean> prefs = ItemFilter.PlayerMan.getPlayerPrefs(Category.ARMOR, p);
			
			if (prefs.get(item.getType())) {
				ItemMeta meta = item.getItemMeta();
				meta.addEnchant(Enchantment.ARROW_DAMAGE, 1, false); //Add enchantment glow
				meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES); //Hide damage value for swords
				meta.addItemFlags(ItemFlag.HIDE_ENCHANTS); //Hide enchantment name so that only the glow remains
				lore.add("§2Item enabled");
				meta.setLore(lore);
				item.setItemMeta(meta);
			} else {
				ItemMeta meta = item.getItemMeta();
				lore.add("§4Item disabled");
				meta.setLore(lore);
				item.setItemMeta(meta);
			}
			
			lore.clear();
		}
		
		inv.setContents(items);
		p.openInventory(inv);
	}
	
	public static Material[] getArmor() {
		String[] item_strs = {"LEATHER_HELMET", "LEATHER_CHESTPLATE", "LEATHER_LEGGINGS", "LEATHER_BOOTS", "IRON_HELMET", "IRON_CHESTPLATE", 
				"IRON_LEGGINGS", "IRON_BOOTS", "GOLD_HELMET", "GOLD_CHESTPLATE", "GOLD_LEGGINGS", "GOLD_BOOTS", "DIAMOND_HELMET", 
				"DIAMOND_CHESTPLATE", "DIAMOND_LEGGINGS", "DIAMOND_BOOTS", "IRON_BARDING", "GOLD_BARDING", "DIAMOND_BARDING"};
		
		Material[] materials = new Material[item_strs.length];
		
		for (int i = 0; i < item_strs.length; i++) {
			materials[i] = Material.getMaterial(item_strs[i]);
		}
		
		return materials;
	}
	
	public static void displayOtherScreen(Player p) {
		Inventory inv = Bukkit.createInventory(null, 9 * 5, "ItemFilter: Other");
		
		ItemStack[] items = {new ItemStack(Material.MONSTER_EGG),new ItemStack(Material.BEACON), new ItemStack(Material.BOOK), new ItemStack(Material.BOOK_AND_QUILL),
				new ItemStack(Material.ENCHANTED_BOOK), new ItemStack(Material.PAPER), new ItemStack(Material.SLIME_BALL), new ItemStack(Material.BONE),
				new ItemStack(Material.ENDER_PEARL), new ItemStack(Material.EYE_OF_ENDER), new ItemStack(Material.MAP), new ItemStack(Material.COAL),
				new ItemStack(Material.DIAMOND), new ItemStack(Material.EMERALD), new ItemStack(Material.IRON_INGOT),
				new ItemStack(Material.GOLD_INGOT), new ItemStack(Material.STICK), new ItemStack(Material.BOWL), new ItemStack(Material.STRING),
				new ItemStack(Material.FEATHER), new ItemStack(Material.SULPHUR), new ItemStack(Material.SEEDS), new ItemStack(Material.PUMPKIN_SEEDS),
				new ItemStack(Material.MELON_SEEDS), new ItemStack(Material.INK_SACK), new ItemStack(Material.WHEAT), new ItemStack(Material.FLINT),
				new ItemStack(Material.LEATHER),new ItemStack(Material.CLAY_BRICK), new ItemStack(Material.CLAY_BALL),
				new ItemStack(Material.SUGAR_CANE), new ItemStack(Material.EGG), new ItemStack(Material.GLOWSTONE_DUST), new ItemStack(Material.SUGAR),
				new ItemStack(Material.BLAZE_ROD), new ItemStack(Material.GOLD_INGOT), new ItemStack(Material.NETHER_STALK),
				new ItemStack(Material.NETHER_STAR), new ItemStack(Material.NETHER_BRICK_ITEM), new ItemStack(Material.QUARTZ),
				new ItemStack(Material.MUSHROOM_SOUP), new ItemStack(Material.MOB_SPAWNER)};
		
		List<String> lore = new ArrayList<String>();
		for (ItemStack item : items) {
			HashMap<Material, Boolean> prefs = ItemFilter.PlayerMan.getPlayerPrefs(Category.OTHER, p);
			
			//System.out.println(prefs);
			//System.out.println(item);
			//System.out.println(prefs.keySet().contains(item));
			//System.out.println(item.getType());
			if (prefs.get(item.getType()) == null)
				prefs.replace(item.getType(), false);
			
			if (prefs.get(item.getType())) {
				ItemMeta meta = item.getItemMeta();
				meta.addEnchant(Enchantment.ARROW_DAMAGE, 1, false); //Add enchantment glow
				meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES); //Hide damage value for swords
				meta.addItemFlags(ItemFlag.HIDE_ENCHANTS); //Hide enchantment name so that only the glow remains
				lore.add("§2Item enabled");
				meta.setLore(lore);
				item.setItemMeta(meta);
			} else {
				ItemMeta meta = item.getItemMeta();
				lore.add("§4Item disabled");
				meta.setLore(lore);
				item.setItemMeta(meta);
			}
			
			lore.clear();
		}
		
		inv.setContents(items);
		p.openInventory(inv);
	}
	
	public static Material[] getOther() {
		ItemStack[] items = {new ItemStack(Material.MONSTER_EGG), new ItemStack(Material.BEACON), new ItemStack(Material.BOOK), new ItemStack(Material.BOOK_AND_QUILL),
				new ItemStack(Material.ENCHANTED_BOOK), new ItemStack(Material.PAPER), new ItemStack(Material.SLIME_BALL), new ItemStack(Material.BONE),
				new ItemStack(Material.ENDER_PEARL), new ItemStack(Material.EYE_OF_ENDER), new ItemStack(Material.MAP), new ItemStack(Material.COAL),
				new ItemStack(Material.DIAMOND), new ItemStack(Material.EMERALD), new ItemStack(Material.IRON_INGOT),
				new ItemStack(Material.GOLD_INGOT), new ItemStack(Material.STICK), new ItemStack(Material.BOWL), new ItemStack(Material.STRING),
				new ItemStack(Material.FEATHER), new ItemStack(Material.SULPHUR), new ItemStack(Material.SEEDS), new ItemStack(Material.PUMPKIN_SEEDS),
				new ItemStack(Material.MELON_SEEDS), new ItemStack(Material.INK_SACK), new ItemStack(Material.WHEAT), new ItemStack(Material.FLINT),
				new ItemStack(Material.LEATHER),new ItemStack(Material.CLAY_BRICK), new ItemStack(Material.CLAY_BALL),
				new ItemStack(Material.SUGAR_CANE), new ItemStack(Material.EGG), new ItemStack(Material.GLOWSTONE_DUST), new ItemStack(Material.SUGAR),
				new ItemStack(Material.BLAZE_ROD), new ItemStack(Material.GOLD_NUGGET), new ItemStack(Material.NETHER_STALK),
				new ItemStack(Material.NETHER_STAR), new ItemStack(Material.NETHER_BRICK_ITEM), new ItemStack(Material.QUARTZ),
				new ItemStack(Material.MUSHROOM_SOUP), new ItemStack(Material.MOB_SPAWNER)};
		
		Material[] temp = new Material[items.length];
		for (int i = 1; i < items.length; i++)
			temp[i] = items[i].getType();
		
		temp[0] = Material.MONSTER_EGG;
		return temp;
	}
}
