package com.hiddentech.campfiredemo;

import com.hiddentech.grid.items.CustomItem;
import com.hiddentech.grid.items.Placeable;
import com.hiddentech.grid.items.Skinnable;
import com.hiddentech.grid.items.crafts.ShapedVanillaCraftable;
import com.hiddentech.grid.utilities.ItemUtility;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class BeaconItem implements CustomItem, Placeable, ShapedVanillaCraftable, Skinnable {

    private ItemStack itemStack = ItemUtility.createItem(Material.GLASS, ChatColor.BLUE+"Mini Beacon","");
    private final List<ItemStack> skins = new ArrayList<ItemStack>(){{
        add(ItemUtility.createItem(Material.WHITE_STAINED_GLASS, ChatColor.BLUE+"Mini Beacon",""));
        add(ItemUtility.createItem(Material.MAGENTA_STAINED_GLASS, ChatColor.BLUE+"Mini Beacon",""));
        add(ItemUtility.createItem(Material.ORANGE_STAINED_GLASS, ChatColor.BLUE+"Mini Beacon",""));
        add(ItemUtility.createItem(Material.LIGHT_BLUE_STAINED_GLASS, ChatColor.BLUE+"Mini Beacon",""));
        add(ItemUtility.createItem(Material.YELLOW_STAINED_GLASS, ChatColor.BLUE+"Mini Beacon",""));
        add(ItemUtility.createItem(Material.LIME_STAINED_GLASS, ChatColor.BLUE+"Mini Beacon",""));
        add(ItemUtility.createItem(Material.PINK_STAINED_GLASS, ChatColor.BLUE+"Mini Beacon",""));
        add(ItemUtility.createItem(Material.GRAY_STAINED_GLASS, ChatColor.BLUE+"Mini Beacon",""));
        add(ItemUtility.createItem(Material.LIGHT_GRAY_STAINED_GLASS, ChatColor.BLUE+"Mini Beacon",""));
        add(ItemUtility.createItem(Material.CYAN_STAINED_GLASS, ChatColor.BLUE+"Mini Beacon",""));
        add(ItemUtility.createItem(Material.PURPLE_STAINED_GLASS, ChatColor.BLUE+"Mini Beacon",""));
        add(ItemUtility.createItem(Material.BLUE_STAINED_GLASS, ChatColor.BLUE+"Mini Beacon",""));
        add(ItemUtility.createItem(Material.BROWN_STAINED_GLASS, ChatColor.BLUE+"Mini Beacon",""));
        add(ItemUtility.createItem(Material.GREEN_STAINED_GLASS, ChatColor.BLUE+"Mini Beacon",""));
        add(ItemUtility.createItem(Material.RED_STAINED_GLASS, ChatColor.BLUE+"Mini Beacon",""));
        add(ItemUtility.createItem(Material.BLACK_STAINED_GLASS, ChatColor.BLUE+"Mini Beacon",""));
    }};
    @Override
    public ItemStack getItem() {
        return this.itemStack;
    }

    @Override
    public String getTag() {
        return "MINI-BEACON";
    }

    @Override
    public void placed(BlockPlaceEvent event) {
        new Beacon(event.getBlock().getLocation());
    }

    @Override
    public ArrayList<Material> getRecipe() {
        ArrayList<Material> recipe = new ArrayList<>();
        recipe.add(Material.GLASS);
        recipe.add(Material.GLASS);
        recipe.add(Material.GLASS);

        recipe.add(Material.GLASS);
        recipe.add(Material.ENDER_EYE);
        recipe.add(Material.GLASS);
        recipe.add(Material.OBSIDIAN);
        recipe.add(Material.DIAMOND);
        recipe.add(Material.OBSIDIAN);
        return recipe;
    }

    @Override
    public List<ItemStack> getSkins() {
        return this.skins;
    }
}
