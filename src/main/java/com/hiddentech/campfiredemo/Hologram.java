package com.hiddentech.campfiredemo;

import com.hiddentech.grid.GridPlugin;
import com.hiddentech.grid.objects.Chattable;
import com.hiddentech.grid.objects.Holos.SingleHologram;
import com.hiddentech.grid.objects.block.Destroyable;
import com.hiddentech.grid.objects.block.Interactable;
import com.hiddentech.grid.utilities.StringUtils;
import com.hiddentech.persistantance.ObjectData;
import com.hiddentech.persistantance.Persistence;
import com.hiddentech.persistantance.types.PersistentLocation;
import com.hiddentech.persistantance.types.PersistentString;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

public class Hologram implements SingleHologram,Interactable, Destroyable, Chattable, PersistentLocation, PersistentString {
    private Boolean loaded;
    private final Location location;
    private final Block block;
    private Entity holo;
    private String text;
    private int id;

    public Hologram(ObjectData objectData){

        this.loaded = false;
        this.location = objectData.getLocation();
        this.block = location.getBlock();
        GridPlugin.getGridAPI().insertObject(this);
        this.text = StringUtils.colorCode(objectData.getString());

        loadHolograms();
        load();
        GridPlugin.getGridHandler().getLoaded().put(this, (short) 2);
        this.id = objectData.getId();
    }
    public Hologram(Location location){
        this.loaded = false;
        this.location = location;
        this.block = location.getBlock();
        GridPlugin.getGridAPI().insertObject(this);
        this.text = "New Hologram";
        loadHolograms();
        load();
        GridPlugin.getGridHandler().getLoaded().put(this, (short) 2);
        this.id = Persistence.getApi().getNextID("HoloDemo");
        Persistence.getApi().save("HoloDemo",this);
    }
    @Override
    public void run(PlayerInteractEvent playerInteractEvent) {
        if(playerInteractEvent.getAction()!= Action.RIGHT_CLICK_BLOCK)return;
        GridPlugin.getChatHandler().getSpeakMap().put(playerInteractEvent.getPlayer().getUniqueId(),this);
        playerInteractEvent.getPlayer().sendMessage(ChatColor.GRAY+"Type hologram text in chat!");

        playerInteractEvent.setCancelled(true);
    }
    @Override
    public void run(AsyncPlayerChatEvent asyncPlayerChatEvent) {
        this.holo.setCustomName(StringUtils.colorCode(asyncPlayerChatEvent.getMessage()));
        this.holo.setCustomNameVisible(true);
        this.text = asyncPlayerChatEvent.getMessage();
        asyncPlayerChatEvent.getPlayer().sendMessage("Text set \""+asyncPlayerChatEvent.getMessage()+"\"");
        Persistence.getApi().save("HoloDemo",this);
        asyncPlayerChatEvent.setCancelled(true);
    }

    @Override
    public void run(BlockBreakEvent blockBreakEvent) {
        destroy();
    }
    @Override
    public Block getBlock() {
        return this.block;
    }

    @Override
    public String getName() {
        return "Hologram Projector";
    }
    @Override
    public String getDrops() {
        return "Hologram Projector*1";
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
        unload();
        unloadHolograms();
        GridPlugin.getGridAPI().removeObject(this);
        Persistence.getApi().delete("HoloDemo",this);
    }

    @Override
    public Location getLocation() {
        return this.location;
    }


    @Override
    public Entity currentHolograms() {
        return this.holo;
    }

    @Override
    public void loadHolograms() {
        this.holo = com.hiddentech.grid.utilities.Hologram.createHologram(location.clone().add(0.5,1,0.5),StringUtils.colorCode(this.text));
    }

    @Override
    public void unloadHolograms() {
        GridPlugin.getHologramHandler().unload(this.holo);
        this.holo.remove();
    }

    @Override
    public String getString() {
        return this.text;
    }
}
