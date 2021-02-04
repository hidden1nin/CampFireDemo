package com.hiddentech.campfiredemo;

import com.hiddentech.grid.items.CustomItem;
import com.hiddentech.grid.items.Edible;
import com.hiddentech.grid.items.crafts.Ingredient;
import com.hiddentech.grid.utilities.ItemUtility;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ScrambledEgg implements CustomItem, Edible, Ingredient {
    private ItemStack itemStack = ItemUtility.createItem(Material.HONEYCOMB, ChatColor.GRAY+"Scrambled Eggs","");
    @Override
    public ItemStack getItem() {
        return this.itemStack;
    }

    @Override
    public String getTag() {
        return "SCRAMBLED-EGG";
    }

    @Override
    public void eat(Player player) {

    }

    @Override
    public int getSaturation() {
        return 1;
    }

    @Override
    public int getFoodLevel() {
        return 4;
    }

    @Override
    public int eatTime() {
        return 0;
    }

    @Override
    public boolean eatWhenFull() {
        return false;
    }

    @Override
    public Sound getSound() {
        return Sound.ENTITY_GENERIC_EAT;
    }
}
