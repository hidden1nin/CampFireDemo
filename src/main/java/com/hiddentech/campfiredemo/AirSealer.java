package com.hiddentech.campfiredemo;

import com.hiddentech.grid.GridPlugin;
import com.hiddentech.grid.objects.Display;
import com.hiddentech.grid.objects.Holos.SingleHologram;
import com.hiddentech.grid.objects.block.AirSealable;
import com.hiddentech.grid.objects.block.Destroyable;
import com.hiddentech.grid.objects.block.Interactable;
import com.hiddentech.grid.objects.ticking.Ticking;
import com.hiddentech.grid.space.Diagnostic;
import com.hiddentech.grid.space.SealDiagnostic;
import com.hiddentech.grid.utilities.Hologram;
import com.hiddentech.grid.utilities.ItemUtility;
import com.hiddentech.persistantance.ObjectData;
import com.hiddentech.persistantance.Persistence;
import com.hiddentech.persistantance.types.PersistentLocation;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class AirSealer implements SingleHologram, Ticking, Interactable, Display, Destroyable, PersistentLocation, AirSealable{
    private Location location;
    private Block block;
    private Boolean loaded;
    private int id;
    private SealDiagnostic sealed = new SealDiagnostic(Diagnostic.OFF,0,0);
    private BukkitTask spinner;
    private Entity hologram;
    private Inventory inventory;
    public AirSealer(ObjectData objectData){
        this.loaded = false;
        this.block = objectData.getLocation().getBlock();
        this.location = block.getLocation();
        this.id = objectData.getId();
        GridPlugin.getGridAPI().insertObject(this);
        this.inventory = Bukkit.createInventory(this,9, ChatColor.GOLD+"Air Sealer");
        loadHolograms();
        load();
        loadInventory();
        GridPlugin.getGridHandler().getLoaded().put(this, (short) 2);
    }
    public AirSealer(Location location){
        this.loaded = false;
        this.block = location.getBlock();
        this.location = block.getLocation();
        GridPlugin.getGridAPI().insertObject(this);
        this.inventory = Bukkit.createInventory(this,9, ChatColor.GOLD+"Air Sealer");
        loadHolograms();
        load();
        loadInventory();
        GridPlugin.getGridHandler().getLoaded().put(this, (short) 2);
        this.id = Persistence.getApi().getNextID("Air-Sealer");
        Persistence.getApi().save("Air-Sealer",this);
    }
    private void loadInventory() {
        this.inventory.clear();
        ItemUtility.fillInventory(this.inventory,ItemUtility.background());
        if(sealed.getDiagnostic().getOutput()) {
            ItemUtility.createItem(Material.GREEN_TERRACOTTA, this.inventory, 1, ChatColor.GREEN+""+sealed.getAvailableBlocks()+" Blocks Allowed", "");
            ItemUtility.createItem(Material.GREEN_TERRACOTTA, this.inventory, 4, sealed.getDiagnostic().getReason(), "");
            ItemUtility.createItem(Material.GREEN_TERRACOTTA, this.inventory, 7, ChatColor.GREEN+""+sealed.getTotalBlocks()+" Total Blocks", "");

        }else {
            ItemUtility.createItem(Material.RED_TERRACOTTA, this.inventory, 1, ChatColor.RED+""+sealed.getAvailableBlocks()+" Blocks Allowed", "");
            ItemUtility.createItem(Material.RED_TERRACOTTA, this.inventory, 4, sealed.getDiagnostic().getReason(), "");
            ItemUtility.createItem(Material.RED_TERRACOTTA, this.inventory, 7, ChatColor.RED+""+sealed.getTotalBlocks()+" Total Blocks", "");
        }
    }
    @Override
    public void run(BlockBreakEvent blockBreakEvent) {
        blockBreakEvent.setCancelled(false);
        blockBreakEvent.setDropItems(false);
        destroy();
        blockBreakEvent.getBlock().getWorld().dropItemNaturally(blockBreakEvent.getBlock().getLocation(),GridPlugin.getItemHandler().getCustomItemByTag("AIR-SEALER").getItem());

    }

    @Override
    public String getDrops() {
        return "Air Sealer*1";
    }

    @Override
    public Block getBlock() {
        return this.block;
    }

    @Override
    public String getName() {
        return "Air Sealer";
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
    public int getId() {
        return this.id;
    }

    @Override
    public void destroy() {
        unload();
        unloadHolograms();
        GridPlugin.getGridAPI().removeObject(this);
        Persistence.getApi().delete("Air-Sealer",this);
    }

    @Override
    public Location getLocation() {
        return this.location;
    }

    @Override
    public boolean enabled() {
        return this.block.isBlockPowered();
    }

    @Override
    public boolean sealed() {
        return this.sealed.getDiagnostic().getOutput();
    }

    @Override
    public int getBlocksSealed() {
        return 100;
    }

    @Override
    public void setSealed(SealDiagnostic b) {
        this.sealed = b;
        if(!b.getDiagnostic().getOutput()){
            ((ArmorStand)hologram).getEquipment().setHelmet(new ItemStack(Material.BLACK_STAINED_GLASS));
        }else {
            ((ArmorStand)hologram).getEquipment().setHelmet(new ItemStack(Material.WHITE_STAINED_GLASS));
        }
    }

    @Override
    public void loadHolograms() {
        this.hologram = Hologram.createHologram(location.clone().add(0.5,-.3,0.5),"",sealed.getDiagnostic().getOutput() ?new ItemStack(Material.WHITE_STAINED_GLASS):new ItemStack(Material.BLACK_STAINED_GLASS));
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

        if(playerInteractEvent.getAction()!= Action.RIGHT_CLICK_BLOCK)return;
        playerInteractEvent.setCancelled(true);
        loadInventory();
        playerInteractEvent.getPlayer().openInventory(inventory);
    }


    @Override
    public Inventory getInventory() {
        return this.inventory;
    }

    @Override
    public void tick() {
        //added ticking to fix it getting reloaded so much
    }
}
