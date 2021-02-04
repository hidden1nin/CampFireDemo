package com.hiddentech.campfiredemo;

import com.hiddentech.grid.items.CustomItem;
import com.hiddentech.grid.items.Edible;
import com.hiddentech.grid.items.crafts.Ingredient;
import com.hiddentech.grid.items.crafts.ShapelessVanillaCraftable;
import com.hiddentech.grid.utilities.ItemUtility;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.awt.*;
import java.util.ArrayList;

public class AppleJuice implements CustomItem, Edible, ShapelessVanillaCraftable, Ingredient {
    private ItemStack itemStack = ItemUtility.colorPotion(ItemUtility.createItem(Material.POTION, ChatColor.RED +"Apple Juice",""), Color.fromRGB(255,163,149));
    @Override
    public void eat(Player player) {
        //nothing atm
        player.playSound(player.getLocation(),Sound.ENTITY_WANDERING_TRADER_DRINK_POTION,1,.5f);
    }

    @Override
    public int getSaturation() {
        return 2;
    }

    @Override
    public int getFoodLevel() {
        return 4;
    }

    @Override
    public int eatTime() {
        return 2;
    }

    @Override
    public boolean eatWhenFull() {
        return false;
    }

    @Override
    public Sound getSound() {
        return Sound.ENTITY_WITCH_DRINK;
    }

    @Override
    public ItemStack getItem() {
        return this.itemStack;
    }

    @Override
    public String getTag() {
        return "APPLE-JUICE";
    }

    @Override
    public ArrayList<Material> getRecipe() {
        ArrayList<Material> materials = new ArrayList<>();
        materials.add(Material.GLASS_BOTTLE);
        materials.add(Material.APPLE);
        return materials;
    }
}
