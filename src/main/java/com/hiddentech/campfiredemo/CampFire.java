package com.hiddentech.campfiredemo;

import com.hiddentech.grid.GridPlugin;
import com.hiddentech.grid.events.PlayerObjectRangeEvent;
import com.hiddentech.grid.objects.RangeObject;
import com.hiddentech.grid.objects.ticking.TickingObject;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class CampFire implements RangeObject, TickingObject {
    private boolean loaded;
    private Location location;
    public CampFire(Location location){
        this.location = location;
        this.loaded = false;
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
}
