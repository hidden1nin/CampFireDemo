package com.hiddentech.campfiredemo;

import com.hiddentech.grid.items.CustomItem;
import com.hiddentech.grid.items.Placeable;
import com.hiddentech.grid.utilities.ItemUtility;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

public class RainbowWoolItem implements Placeable {
    private ItemStack itemStack = ItemUtility.createItem(Material.WHITE_STAINED_GLASS, ChatColor.LIGHT_PURPLE+"Rainbow Wool","");
    @Override
    public void placed(BlockPlaceEvent event) {
        event.getBlock().setType(Material.WHITE_WOOL);
        new RainbowWool(event.getBlock().getLocation());
    }

    @Override
    public ItemStack getItem() {
        return this.itemStack;
    }

    @Override
    public String getTag() {
        return "RAINBOW-WOOL";
    }
}
