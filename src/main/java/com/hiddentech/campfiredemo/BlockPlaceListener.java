package com.hiddentech.campfiredemo;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlaceListener implements Listener {
    @EventHandler
    public void PlaceBlock(BlockPlaceEvent event){
        if(event.getBlock().getType()== Material.CAMPFIRE){
            new CampFire(event.getBlock().getLocation());
        }
        if(event.getBlock().getType()== Material.BLUE_STAINED_GLASS){

            event.getBlock().setType(Material.GLASS);
            new Beacon(event.getBlock());
        }
    }
}
