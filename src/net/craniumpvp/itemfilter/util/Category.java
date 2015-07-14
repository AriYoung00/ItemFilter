package net.craniumpvp.itemfilter.util;

import java.util.Arrays;

import org.bukkit.inventory.ItemStack;

public enum Category {
	//I fucking hate VCS
	
	ARMOR, TOOLS, FOOD, POTIONS, BLOCKS, OTHER, NOT_CATEGORIZED;
	
	public static Category[] getTypes() {
		Category[] types = { ARMOR, TOOLS, FOOD, POTIONS, BLOCKS, OTHER };
		return types;
	}
	
	public static Category getItemCategory(ItemStack i) {
		if (Arrays.asList(ScreenUtil.getFood()).contains(i.getType())) 
			return FOOD;
		else if (Arrays.asList(ScreenUtil.getArmor()).contains(i.getType()))
			return ARMOR;
		else if (Arrays.asList(ScreenUtil.getPotions()).contains(i.getType()))
			return POTIONS;
		else if (Arrays.asList(ScreenUtil.getTools()).contains(i.getType()))
			return TOOLS;
		else if (Arrays.asList(ScreenUtil.getOther()).contains(i.getType()))
			return OTHER;
		else if (Arrays.asList(ScreenUtil.getBlocks()).contains(i.getType()))
			return BLOCKS;
		else
			return NOT_CATEGORIZED;
	}
}
