package com.hiddentech.campfiredemo;

import com.hiddentech.grid.GridPlugin;
import com.hiddentech.grid.events.PlayerObjectRangeEvent;
import com.hiddentech.grid.objects.Loadable;
import com.hiddentech.grid.objects.Ranged;
import com.hiddentech.grid.objects.block.Destroyable;
import com.hiddentech.grid.objects.ticking.Ticking;
import com.hiddentech.persistantance.ObjectData;
import com.hiddentech.persistantance.Persistence;
import com.hiddentech.persistantance.types.PersistentLocation;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.potion.PotionEffect;

public class MilkBlock implements Destroyable, Ranged, Ticking,PersistentLocation {
    private Location location;
    private Block block;
    private Boolean loaded;
    private int id;
    public MilkBlock(ObjectData objectData){
        this.loaded = false;
        this.block = objectData.getLocation().getBlock();
        this.location = block.getLocation();
        this.id = objectData.getId();
        GridPlugin.getGridHandler().getLoaded().put(this, (short) 2);
    }

    public MilkBlock(Block block) {
        this.block = block;
        this.location = block.getLocation();
        this.loaded = false;
        GridPlugin.getGridAPI().insertObject(this);
        GridPlugin.getGridHandler().getLoaded().put(this, (short) 2);
        this.id = Persistence.getApi().getNextID("Milk-Block");
        Persistence.getApi().save("Milk-Block",this);
    }

    @Override
    public Location getLocation() {
        return this.location;
    }

    @Override
    public int getId() {
        return this.id;
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
        unload();
        GridPlugin.getGridAPI().removeObject(this);
        Persistence.getApi().delete("Milk-Block",this);
    }

    @Override
    public void run(BlockBreakEvent blockBreakEvent) {
        destroy();
    }

    @Override
    public String getDrops() {
        return "";
    }

    @Override
    public Block getBlock() {
        return this.block;
    }

    @Override
    public String getName() {
        return "Milk Block";
    }

    @Override
    public int getDistance() {
        return 3;
    }

    @Override
    public void run(PlayerObjectRangeEvent playerObjectRangeEvent) {
        for (PotionEffect effect : playerObjectRangeEvent.getPlayer().getActivePotionEffects())
            playerObjectRangeEvent.getPlayer().removePotionEffect(effect.getType());
    }
}
