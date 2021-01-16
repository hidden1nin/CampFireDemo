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
    }
}
