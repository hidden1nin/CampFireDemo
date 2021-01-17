package com.hiddentech.campfiredemo;

import com.hiddentech.grid.GridPlugin;
import com.hiddentech.grid.events.PlayerObjectRangeEvent;
import com.hiddentech.grid.objects.Holos.SingleHologram;
import com.hiddentech.grid.objects.Inventoried;
import com.hiddentech.grid.objects.Ranged;
import com.hiddentech.grid.objects.block.Destroyable;
import com.hiddentech.grid.objects.block.Interactable;
import com.hiddentech.grid.objects.ticking.Ticking;
import com.hiddentech.grid.utilities.Hologram;
import com.hiddentech.grid.utilities.ItemUtility;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class Beacon implements SingleHologram, Interactable, Destroyable, Ranged, Ticking, Inventoried {
    private Location location;
    private Block block;
    private Entity hologram;
    private Boolean loaded;

    private BukkitTask spinner;
    private Inventory inventory;
    private PotionEffect effect;
    public Beacon(Block block){
        this.loaded = false;
        this.block = block;
        this.location = block.getLocation();

        this.inventory = Bukkit.createInventory(this,9, ChatColor.GOLD+"Beacon Demo");
        GridPlugin.getGridAPI().insertObject(this);
        loadHolograms();
        load();
        loadInventory();
        this.effect = new PotionEffect(PotionEffectType.SPEED,100,0);
        GridPlugin.getGridHandler().getLoaded().put(this, (short) 2);
    }

    private void loadInventory() {
        this.inventory.clear();
        ItemUtility.fillInventory(this.inventory,ItemUtility.background());

        ItemUtility.createItem(Material.SUGAR,this.inventory,1,ChatColor.GRAY+"Speed","");
        ItemUtility.createItem(Material.DIAMOND_CHESTPLATE,this.inventory,4,ChatColor.GRAY+"Resistance","");
        ItemUtility.createItem(Material.DIAMOND_SWORD,this.inventory,7,ChatColor.GRAY+"Strength","");
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
        //TODO fix bug where players arent put into hashmap properly
        playerInteractEvent.getPlayer().openInventory(this.inventory);
        GridPlugin.getInventoryHandler().getTickingInventories().remove(playerInteractEvent.getPlayer());
        GridPlugin.getInventoryHandler().getTickingInventories().put(playerInteractEvent.getPlayer(),(Inventoried)this);


    }

    @Override
    public void run(BlockBreakEvent blockBreakEvent) {
        blockBreakEvent.setCancelled(false);
        unload();
        unloadHolograms();
        GridPlugin.getGridAPI().removeObject(this);
        blockBreakEvent.setDropItems(false);
        /*for(ItemStack itemStack:this.inventory.getContents()){
            if(itemStack==null) continue;
            blockBreakEvent.getBlock().getWorld().dropItemNaturally(blockBreakEvent.getBlock().getLocation(),itemStack);
        }
        */
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
        event.getPlayer().addPotionEffect(this.effect);
        event.getObject().getLocation().getWorld().spawnParticle(Particle.VILLAGER_ANGRY,event.getObject().getLocation().clone().add(.5,1,.5),5);
    }

    @Override
    public void tick() {
        //
    }

    @Override
    public void run(InventoryClickEvent inventoryClickEvent) {
        inventoryClickEvent.setCancelled(true);
        if(inventoryClickEvent.getCurrentItem()==null)return;
        if(inventoryClickEvent.getCurrentItem().getType()==Material.SUGAR){
            this.effect = new PotionEffect(PotionEffectType.SPEED,100,0);
            inventoryClickEvent.getWhoClicked().sendMessage(ChatColor.GRAY+"Selected Speed");}
        if(inventoryClickEvent.getCurrentItem().getType()==Material.DIAMOND_CHESTPLATE){
            this.effect = new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE,100,0);
            inventoryClickEvent.getWhoClicked().sendMessage(ChatColor.GRAY+"Selected Resistance");}
        if(inventoryClickEvent.getCurrentItem().getType()==Material.DIAMOND_SWORD){
            inventoryClickEvent.getWhoClicked().sendMessage(ChatColor.GRAY+"Selected Strength");
            this.effect = new PotionEffect(PotionEffectType.INCREASE_DAMAGE,100,0);}
    }

}
