package com.hiddentech.campfiredemo;

import com.hiddentech.grid.GridPlugin;
import com.hiddentech.grid.events.PlayerObjectRangeEvent;
import com.hiddentech.grid.objects.Holos.IHologram;
import com.hiddentech.grid.objects.Inventory.Inventoried;
import com.hiddentech.grid.objects.Ranged;
import com.hiddentech.grid.objects.block.Interactable;
import com.hiddentech.grid.objects.block.PistonBreakable;
import com.hiddentech.grid.objects.ticking.Ticking;
import com.hiddentech.grid.utilities.Hologram;
import com.hiddentech.grid.utilities.ItemUtility;
import com.hiddentech.persistantance.ObjectData;
import com.hiddentech.persistantance.Persistence;
import com.hiddentech.persistantance.types.PersistentLocation;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.Arrays;

public class Beacon implements IHologram, Interactable, PistonBreakable, Ranged, Ticking, Inventoried, PersistentLocation {
    private Location location;
    private Block block;
    private Boolean loaded;
    private final ArrayList<Hologram> hologram= new ArrayList<>();
    //private BukkitTask spinner;
    private Inventory inventory;
    private  PotionEffect effect;
    private int id;
    public Beacon(ObjectData objectData){
        this.loaded = false;
        this.block = objectData.getLocation().getBlock();
        this.location = block.getLocation();
        this.id = objectData.getId();
        this.inventory = Bukkit.createInventory(this,9, ChatColor.GOLD+"Beacon");
        GridPlugin.getGridAPI().insertObject(this);
        Hologram holo = new Hologram(location.clone().add(.5,-0.3,.5),"");
        holo.setHelmet(new ItemStack(Material.LIGHT_GRAY_STAINED_GLASS));
        hologram.add(holo);
        load();
        loadInventory();
        this.effect = new PotionEffect(PotionEffectType.SPEED,100,0);
        GridPlugin.getGridHandler().getLoaded().put(this, (short) 2);
    }
    public Beacon(Location location){
        this.loaded = false;
        this.block = location.getBlock();
        this.location = block.getLocation();

        this.inventory = Bukkit.createInventory(this,9, ChatColor.GOLD+"Beacon");
        GridPlugin.getGridAPI().insertObject(this);
        Hologram holo = new Hologram(location.clone().add(.5,-0.3,.5),"");
        holo.setHelmet(new ItemStack(Material.LIGHT_GRAY_STAINED_GLASS));
        hologram.add(holo);
        load();
        loadInventory();
        this.effect = new PotionEffect(PotionEffectType.SPEED,100,0);
        GridPlugin.getGridHandler().getLoaded().put(this, (short) 2);
        this.id = Persistence.getApi().getNextID("Beacons");
        Persistence.getApi().save("Beacons",this);
    }

    private void loadInventory() {
        this.inventory.clear();
        ItemUtility.fillInventory(this.inventory,ItemUtility.background());

        ItemUtility.createItem(Material.SUGAR,this.inventory,1,ChatColor.GRAY+"Speed","");
        ItemUtility.createItem(Material.DIAMOND_PICKAXE,this.inventory,4,ChatColor.GRAY+"Haste","");
        ItemUtility.createItem(Material.DIAMOND_SWORD,this.inventory,7,ChatColor.GRAY+"Strength","");
    }


    @Override
    public Block getBlock() {
        return this.block;
    }

    @Override
    public String getName() {
        return "Mini Beacon";
    }

    @Override
    public Boolean isLoaded() {
        return this.loaded;
    }

    @Override
    public void load() {
    unload();
    //this.spinner = spin();
    this.loaded = true;
    }
/*
    private BukkitTask spin() {
        return new BukkitRunnable() {
            @Override
            public void run() {
                hologram.setRotation(hologram.getLocation().getYaw() + 8, 0);
        }}.runTaskTimer(CampFireDemo.getPlugin(), 2, 3);
    }*/

    @Override
    public void unload() {
        /*if (this.spinner != null) {
            this.spinner.cancel();
        }*/
        this.loaded = false;
    }


    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public void destroy() {
        unload();
        GridPlugin.getGridAPI().removeObject(this);
        Persistence.getApi().delete("Beacons",this);
    }

    @Override
    public Location getLocation() {
        return this.location;
    }

/*
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
*/
    @Override
    public void run(PlayerInteractEvent playerInteractEvent) {
        if(playerInteractEvent.getAction()!= Action.RIGHT_CLICK_BLOCK)return;
        playerInteractEvent.getPlayer().openInventory(this.inventory);
        GridPlugin.getInventoryHandler().getTickingInventories().remove(playerInteractEvent.getPlayer());
        GridPlugin.getInventoryHandler().getTickingInventories().put(playerInteractEvent.getPlayer(),(Inventoried)this);

        playerInteractEvent.setCancelled(true);

    }

    @Override
    public void run(BlockBreakEvent blockBreakEvent) {
        blockBreakEvent.setCancelled(false);
        blockBreakEvent.setDropItems(false);
        /*for(ItemStack itemStack:this.inventory.getContents()){
            if(itemStack==null) continue;
            blockBreakEvent.getBlock().getWorld().dropItemNaturally(blockBreakEvent.getBlock().getLocation(),itemStack);
        }
        */
        destroy();
        blockBreakEvent.getBlock().getWorld().dropItemNaturally(blockBreakEvent.getBlock().getLocation(),GridPlugin.getItemHandler().getCustomItemByTag("MINI-BEACON").getItem());
    }

    @Override
    public String getDrops() {
        return "Mini Beacon*1";
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
    public void run(InventoryClickEvent inventoryClickEvent) {
        inventoryClickEvent.setCancelled(true);
        if(inventoryClickEvent.getCurrentItem()==null)return;
        if(inventoryClickEvent.getCurrentItem().getType()==Material.SUGAR){
            this.effect = new PotionEffect(PotionEffectType.SPEED,100,0);
            inventoryClickEvent.getWhoClicked().sendMessage(ChatColor.GRAY+"Selected Speed");}
        if(inventoryClickEvent.getCurrentItem().getType()==Material.DIAMOND_PICKAXE){
            this.effect = new PotionEffect(PotionEffectType.FAST_DIGGING,100,0);
            inventoryClickEvent.getWhoClicked().sendMessage(ChatColor.GRAY+"Selected Haste");}
        if(inventoryClickEvent.getCurrentItem().getType()==Material.DIAMOND_SWORD){
            inventoryClickEvent.getWhoClicked().sendMessage(ChatColor.GRAY+"Selected Strength");
            this.effect = new PotionEffect(PotionEffectType.INCREASE_DAMAGE,100,0);}
    }

    @Override
    public ArrayList<Hologram> currentHolograms() {
        return this.hologram;
    }
}
