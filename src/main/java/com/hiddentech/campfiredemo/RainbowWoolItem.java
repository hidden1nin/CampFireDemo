package com.hiddentech.campfiredemo;

import com.hiddentech.grid.items.Placeable;
import com.hiddentech.grid.items.crafts.Craftable;
import com.hiddentech.grid.items.crafts.ShapelessVanillaCraftable;
import com.hiddentech.grid.utilities.ItemUtility;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class RainbowWoolItem implements Placeable, ShapelessVanillaCraftable {
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
    @Override
    public ArrayList<Material> getRecipe() {
        ArrayList<Material> materials = new ArrayList<>();
        materials.add(Material.WHITE_WOOL);
        materials.add(Material.WHITE_DYE);
        materials.add(Material.CYAN_DYE);
        materials.add(Material.LIME_DYE);
        materials.add(Material.RED_DYE);
        return materials;
    }

    @Override
    public int getAmount() {
        return 8;
    }
}
