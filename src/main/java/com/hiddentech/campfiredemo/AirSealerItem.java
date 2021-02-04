package com.hiddentech.campfiredemo;

import com.hiddentech.grid.items.CustomItem;
import com.hiddentech.grid.items.Placeable;
import com.hiddentech.grid.utilities.ItemUtility;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class AirSealerItem implements Placeable {
    private ItemStack itemStack = ItemUtility.createItem(ItemUtility.getCustomSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTYwZjUwZDdlNDc2ZjZjODQ4NGIzYjk3MzEzODZhZWIwNzA5OTc0MmNkNjg1MjliNzA1ZGQ4ZjdkYTY3ODg5YyJ9fX0="),ChatColor.GRAY+"Air Sealer",ChatColor.GRAY+"Power to activate!\n"+ChatColor.AQUA+"Seals 100 blocks");
    @Override
    public void placed(BlockPlaceEvent event) {
        // do it a tick later so we don't make an error cause they place a tile entity!
        new BukkitRunnable(){
            @Override
            public void run() {
                event.getBlockPlaced().setType(Material.BLACK_STAINED_GLASS);
            }
        }.runTaskLater(CampFireDemo.getPlugin(),1);
        new AirSealer(event.getBlock().getLocation());
    }

    @Override
    public ItemStack getItem() {
        return this.itemStack;
    }

    @Override
    public String getTag() {
        return "AIR-SEALER";
    }
}
