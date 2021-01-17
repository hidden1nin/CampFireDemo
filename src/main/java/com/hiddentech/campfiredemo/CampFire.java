package com.hiddentech.campfiredemo;

import com.hiddentech.grid.GridPlugin;
import com.hiddentech.grid.events.PlayerObjectRangeEvent;
import com.hiddentech.grid.objects.InventoryObject;
import com.hiddentech.grid.objects.RangeObject;
import com.hiddentech.grid.objects.block.DestroyBlockObject;
import com.hiddentech.grid.objects.block.InteractBlockObject;
import com.hiddentech.grid.objects.ticking.TickingObject;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class CampFire implements RangeObject, TickingObject, InteractBlockObject, InventoryObject, DestroyBlockObject {
    private boolean loaded;
    private Location location;
    private Inventory storage;
    public CampFire(Location location){
        this.location = location;
        this.loaded = false;
        this.storage = Bukkit.createInventory(this,9, ChatColor.GOLD+"Camp Fire");
        GridPlugin.getGridAPI().insertObject(this);
    }
    @Override
    public int getDistance() {
        return 5;
    }

    @Override
    public void run(PlayerObjectRangeEvent event) {
        event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION,100,0));
        event.getObject().getLocation().getWorld().spawnParticle(Particle.HEART,event.getObject().getLocation().clone().add(.5,1,.5),5);
    }

    @Override
    public Boolean isLoaded() {
        return this.loaded;
    }

    @Override
    public void load() {
    this.loaded = true;
    }

    @Override
    public void unload() {
        this.loaded = false;
    }

    @Override
    public void destroy() {
        GridPlugin.getGridAPI().removeObject(this);
        unload();
    }

    @Override
    public Location getLocation() {
        return this.location;
    }

    @Override
    public void tick() {

    }

    @Override
    public Block getBlock() {
        return this.location.getBlock();
    }

    @Override
    public void run(PlayerInteractEvent playerInteractEvent) {
        playerInteractEvent.getPlayer().openInventory(storage);
    }

    @Override
    public Inventory getInventory() {
        return this.storage;
    }

    @Override
    public void run(InventoryClickEvent inventoryClickEvent) {

    }

    @Override
    public void run(BlockBreakEvent blockBreakEvent) {
        blockBreakEvent.setCancelled(false);
        unload();
        GridPlugin.getGridAPI().removeObject(this);
        blockBreakEvent.setDropItems(false);
        for(ItemStack itemStack:this.storage.getContents()){
            if(itemStack==null) continue;
            blockBreakEvent.getBlock().getWorld().dropItemNaturally(blockBreakEvent.getBlock().getLocation(),itemStack);
        }
        blockBreakEvent.getBlock().getWorld().dropItemNaturally(blockBreakEvent.getBlock().getLocation(),new ItemStack(Material.CAMPFIRE, 1));

    }
}
