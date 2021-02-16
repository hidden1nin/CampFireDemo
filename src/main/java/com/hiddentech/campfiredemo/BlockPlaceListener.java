package com.hiddentech.campfiredemo;

import com.hiddentech.grid.GridPlugin;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlaceListener implements Listener {
    @EventHandler
    public void PlaceBlock(BlockPlaceEvent event){
        if(event.isCancelled())return;
        if(event.getBlock().getType()== Material.CAMPFIRE){
            new CampFire(event.getBlock().getLocation());
        }
        if(event.getBlock().getType()== Material.SPONGE&&event.getPlayer().isOp()){
            event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), GridPlugin.getItemHandler().getCustomItemByTag("MINI-BEACON").getItem());
            event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), GridPlugin.getItemHandler().getCustomItemByTag("APPLE-JUICE").getItem());
            event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), GridPlugin.getItemHandler().getCustomItemByTag("SCRAMBLED-EGG").getItem());
            event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), GridPlugin.getItemHandler().getCustomItemByTag("HEARTY-BREAKFAST").getItem());
            event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), GridPlugin.getItemHandler().getCustomItemByTag("RAINBOW-WOOL").getItem());
            event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), GridPlugin.getItemHandler().getCustomItemByTag("AIR-SEALER").getItem());
        }
        if(event.getBlock().getType()== Material.ORANGE_WOOL){
            new MagmaBlock(event.getBlock().getLocation());
        }
    }
}
