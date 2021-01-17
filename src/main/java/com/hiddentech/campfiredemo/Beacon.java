package com.hiddentech.campfiredemo;

import com.hiddentech.grid.GridPlugin;
import com.hiddentech.grid.events.PlayerObjectRangeEvent;
import com.hiddentech.grid.grid.GridAPI;
import com.hiddentech.grid.objects.InventoryObject;
import com.hiddentech.grid.objects.RangeObject;
import com.hiddentech.grid.objects.block.CustomBlock;
import com.hiddentech.grid.objects.block.DestroyBlockObject;
import com.hiddentech.grid.objects.block.InteractBlockObject;
import com.hiddentech.grid.objects.entity.EntityObject;
import com.hiddentech.grid.objects.entity.HologramObject;
import com.hiddentech.grid.objects.ticking.TickingObject;
import com.hiddentech.grid.utilities.Hologram;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;

public class Beacon implements HologramObject, InteractBlockObject, DestroyBlockObject, InventoryObject, RangeObject, TickingObject {
    private Location location;
    private Block block;
    private Entity hologram;
    private Boolean loaded;

    private BukkitTask spinner;
    private Inventory inventory;

    public Beacon(Block block){
        this.loaded = false;
        this.block = block;
        this.location = block.getLocation();

        this.inventory = Bukkit.createInventory(this,9, ChatColor.GOLD+"Beacon Demo");
        GridPlugin.getGridAPI().insertObject(this);
        loadHolograms();
        load();
        GridPlugin.getGridHandler().getLoaded().put(this, (short) 2);
    }
    @Override
    public Block getBlock() {
        return this.block;
    }

    @Override
    public Boolean isLoaded() {
        return this.loaded;
    }

    @Override
    public void load() {
    unload();
    this.spinner = spin();
    this.loaded = true;
    }

    private BukkitTask spin() {
        return new BukkitRunnable() {
            @Override
            public void run() {
                hologram.setRotation(hologram.getLocation().getYaw() + 8, 0);
        }}.runTaskTimer(CampFireDemo.getPlugin(), 2, 3);
    }

    @Override
    public void unload() {
        if (this.spinner != null) {
            this.spinner.cancel();
        }
        this.loaded = false;
    }

    @Override
    public void destroy() {
        unload();
        hologram.remove();

    }

    @Override
    public Location getLocation() {
        return this.location;
    }


    @Override
    public void loadHolograms() {
        this.hologram =Hologram.createHologram(location.clone().add(0.5,-.3,0.5),"",new ItemStack(Material.LAPIS_BLOCK));
        GridPlugin.getHologramHandler().load(this.hologram);
    }

    @Override
    public void unloadHolograms() {
        GridPlugin.getHologramHandler().unload(this.hologram);
        this.hologram.remove();
    }

    @Override
    public Entity currentHolograms() {
        return this.hologram;
    }

    @Override
    public void run(PlayerInteractEvent playerInteractEvent) {
        playerInteractEvent.getPlayer().openInventory(this.inventory);
    }

    @Override
    public void run(BlockBreakEvent blockBreakEvent) {
        blockBreakEvent.setCancelled(false);
        unload();
        unloadHolograms();
        GridPlugin.getGridAPI().removeObject(this);
        blockBreakEvent.setDropItems(false);
        blockBreakEvent.getBlock().getWorld().dropItemNaturally(blockBreakEvent.getBlock().getLocation(),new ItemStack(Material.BLUE_STAINED_GLASS, 1));
    }

    @Override
    public Inventory getInventory() {
        return this.inventory;
    }

    @Override
    public int getDistance() {
        return 8;
    }

    @Override
    public void run(PlayerObjectRangeEvent event) {
        event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SPEED,100,0));
        event.getObject().getLocation().getWorld().spawnParticle(Particle.VILLAGER_ANGRY,event.getObject().getLocation().clone().add(.5,1,.5),5);
    }

    @Override
    public void tick() {
        //do nothing im doing this because i want to have range event tick
    }
}
