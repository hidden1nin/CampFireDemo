package com.hiddentech.campfiredemo;

import com.hiddentech.grid.GridPlugin;
import com.hiddentech.grid.events.PlayerObjectRangeEvent;
import com.hiddentech.grid.objects.Ranged;
import com.hiddentech.grid.objects.block.Destroyable;
import com.hiddentech.grid.objects.ticking.Ticking;
import com.hiddentech.persistantance.ObjectData;
import com.hiddentech.persistantance.Persistence;
import com.hiddentech.persistantance.types.PersistentLocation;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;


public class CampFire implements Ranged, Ticking, Destroyable, PersistentLocation {
    private boolean loaded;
    private Location location;
    private int id;
    public CampFire(ObjectData objectData){
        this.location = objectData.getLocation();
        this.loaded = false;
        GridPlugin.getGridAPI().insertObject(this);
        this.id = objectData.getId();
    }
    public CampFire(Location location){
        this.location = location;
        this.loaded = false;
        GridPlugin.getGridAPI().insertObject(this);
        this.id = Persistence.getApi().getNextID("Campfires");
        Persistence.getApi().save("Campfires",this);
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
    public int getId() {
        return this.id;
    }

    @Override
    public void destroy() {
        GridPlugin.getGridAPI().removeObject(this);
        unload();
        Persistence.getApi().delete("Campfires",this);
    }

    @Override
    public Location getLocation() {
        return this.location;
    }


    @Override
    public Block getBlock() {
        return this.location.getBlock();
    }

    @Override
    public String getName() {
        return "Healing Camp Fire";
    }

    @Override
    public void run(BlockBreakEvent blockBreakEvent) {
        blockBreakEvent.setCancelled(false);
        destroy();
        blockBreakEvent.setDropItems(false);
        blockBreakEvent.getBlock().getWorld().dropItemNaturally(blockBreakEvent.getBlock().getLocation(),new ItemStack(Material.CAMPFIRE, 1));

    }

    @Override
    public String getDrops() {
        return "Camp Fire*1";
    }

}
