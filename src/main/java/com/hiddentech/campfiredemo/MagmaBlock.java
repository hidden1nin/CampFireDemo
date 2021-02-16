package com.hiddentech.campfiredemo;

import com.hiddentech.grid.GridPlugin;
import com.hiddentech.grid.enums.DetectType;
import com.hiddentech.grid.events.PlayerDetectBlockEvent;
import com.hiddentech.grid.objects.Loadable;
import com.hiddentech.grid.objects.block.Destroyable;
import com.hiddentech.grid.objects.block.Detectable;
import com.hiddentech.grid.objects.block.ExplosionResistant;
import com.hiddentech.grid.objects.ticking.Ticking;
import com.hiddentech.persistantance.ObjectData;
import com.hiddentech.persistantance.Persistence;
import com.hiddentech.persistantance.types.PersistentLocation;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class MagmaBlock implements Destroyable, PersistentLocation, Detectable, Ticking, ExplosionResistant {
    private Location location;
    private int id;
    private boolean loaded;
    public MagmaBlock(ObjectData objectData){
        this.location = objectData.getLocation();
        this.loaded = false;
        GridPlugin.getGridAPI().insertObject(this);
        load();
        GridPlugin.getGridHandler().getLoaded().put(this, (short) 2);
        this.id = objectData.getId();
    }
    public MagmaBlock(Location location){
        this.location = location;
        this.loaded = false;
        GridPlugin.getGridAPI().insertObject(this);
        load();
        GridPlugin.getGridHandler().getLoaded().put(this, (short) 2);
        this.id = Persistence.getApi().getNextID("MagmaDemo");
        Persistence.getApi().save("MagmaDemo",this);
    }
    @Override
    public void run(BlockBreakEvent blockBreakEvent) {
        destroy();
    }

    @Override
    public String getDrops() {
        return "Flamin' Hot Wool*1";
    }

    @Override
    public Block getBlock() {
        return this.location.getBlock();
    }

    @Override
    public String getName() {
        return "Flamin' Hot Wool";
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
        this.loaded=false;
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public void destroy() {
        unload();
        GridPlugin.getGridAPI().removeObject(this);
        Persistence.getApi().delete("MagmaDemo",this);
    }

    @Override
    public Location getLocation() {
        return this.location;
    }

    @Override
    public void run(PlayerDetectBlockEvent playerDetectBlockEvent) {
        if(playerDetectBlockEvent.getDetectType()== DetectType.WALK){
            playerDetectBlockEvent.getPlayer().setFireTicks(100);
        }
    }

}
