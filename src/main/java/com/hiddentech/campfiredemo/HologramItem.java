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

public class HologramItem implements CustomItem, Placeable,ShapedVanillaCraftable, Skinnable {
    private ItemStack itemStack = ItemUtility.createItem(Material.QUARTZ_SLAB, ChatColor.GRAY+"Hologram Projector","");
    private final List<ItemStack> skins = new ArrayList<ItemStack>(){{
        add(ItemUtility.createItem(Material.GLASS, ChatColor.GRAY+"Hologram Projector",""));
        add(ItemUtility.createItem(Material.BOOKSHELF, ChatColor.GRAY+"Hologram Projector",""));
    }};
    @Override
    public ItemStack getItem() {
        return this.itemStack;
    }

    @Override
    public String getTag() {
        return "HOLOGRAM";
    }

    @Override
    public void placed(BlockPlaceEvent blockPlaceEvent) {
        new Hologram(blockPlaceEvent.getBlock().getLocation());
    }

    @Override
    public List<ItemStack> getSkins() {
        return this.skins;
    }

    @Override
    public ArrayList<Material> getRecipe() {
        ArrayList<Material> items = new ArrayList<>();
        //
        items.add(null);
        items.add(Material.IRON_INGOT);
        items.add(null);
        //
        items.add(Material.IRON_INGOT);
        items.add(Material.OAK_SIGN);
        items.add(Material.IRON_INGOT);
        //
        items.add(null);
        items.add(Material.QUARTZ_SLAB);
        items.add(null);
        return items;
    }
}
