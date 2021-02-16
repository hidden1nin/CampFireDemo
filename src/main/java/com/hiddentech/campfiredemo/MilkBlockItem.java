package com.hiddentech.campfiredemo;

import com.hiddentech.grid.items.Placeable;
import com.hiddentech.grid.items.crafts.ShapedVanillaCraftable;
import com.hiddentech.grid.utilities.ItemUtility;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;

public class MilkBlockItem implements Placeable, ShapedVanillaCraftable {
    private ItemStack itemStack = ItemUtility.createItem(Material.SNOW_BLOCK, ChatColor.GRAY+"Milk Block",ChatColor.GRAY+"Clears effects nearby");

    @Override
    public void placed(BlockPlaceEvent blockPlaceEvent) {
        new MilkBlock(blockPlaceEvent.getBlock());
    }

    @Override
    public ItemStack getItem() {
        return this.itemStack;
    }

    @Override
    public String getTag() {
        return "MILK-BLOCK";
    }

    @Override
    public ArrayList<Material> getRecipe() {
        ArrayList<Material> recipe = new ArrayList<>();
        recipe.add(Material.MILK_BUCKET);
        recipe.add(Material.MILK_BUCKET);
        recipe.add(Material.MILK_BUCKET);
        recipe.add(Material.MILK_BUCKET);
        recipe.add(Material.MILK_BUCKET);
        recipe.add(Material.MILK_BUCKET);
        recipe.add(Material.MILK_BUCKET);
        recipe.add(Material.MILK_BUCKET);
        recipe.add(Material.MILK_BUCKET);
        return recipe;
    }

    @Override
    public int getAmount() {
        return 4;
    }
}
