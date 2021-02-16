package com.hiddentech.campfiredemo;

import com.hiddentech.grid.items.CustomItem;
import com.hiddentech.grid.items.Edible;
import com.hiddentech.grid.items.crafts.ShapedCustomCraft;
import com.hiddentech.grid.utilities.ItemUtility;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class HeartyBreakfast implements CustomItem, Edible, ShapedCustomCraft {
    private ItemStack itemStack = ItemUtility.createItem(ItemUtility.getCustomSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzhhOTcxZGY1MWU5Y2E5YTViOTJlNDVkM2ZhNTQ3ZGQzMTkxODc3OTI5YTQzN2Q1MDE1Mzg5MmU1ODVhIn19fQ=="),ChatColor.GRAY+"Hearty Breakfast","");
    @Override
    public ItemStack getItem() {
        return this.itemStack;
    }

    @Override
    public String getTag() {
        return "HEARTY-BREAKFAST";
    }

    @Override
    public void eat(Player player) {

    }

    @Override
    public int getSaturation() {
        return 5;
    }

    @Override
    public int getFoodLevel() {
        return 10;
    }

    @Override
    public int eatTime() {
        return 0;
    }

    @Override
    public boolean eatWhenFull() {
        return true;
    }

    @Override
    public Sound getSound() {
        return Sound.ENTITY_LLAMA_EAT;
    }

    @Override
    public ArrayList<String> getRecipe() {
        ArrayList<String> items = new ArrayList<>();
        //
        items.add(null);
        items.add("APPLE-JUICE");
        items.add(null);
        //
        items.add(null);
        items.add("SCRAMBLED-EGG");
        items.add(null);
        //
        items.add(null);
        items.add("BOWL");
        items.add(null);
        return items;
    }
}
