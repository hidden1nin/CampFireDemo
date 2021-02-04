package com.hiddentech.campfiredemo;

import com.hiddentech.grid.items.CustomItem;
import com.hiddentech.grid.items.Placeable;
import com.hiddentech.grid.utilities.ItemUtility;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

public class BeaconItem implements CustomItem, Placeable {
    private ItemStack itemStack = ItemUtility.createItem(Material.BLUE_STAINED_GLASS, ChatColor.BLUE+"Mini Beacon","");

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
        event.getBlock().setType(Material.GLASS);
        new Beacon(event.getBlock().getLocation());
    }
}
