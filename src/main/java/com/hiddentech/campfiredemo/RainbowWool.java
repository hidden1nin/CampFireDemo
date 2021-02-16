package com.hiddentech.campfiredemo;

import com.hiddentech.grid.GridPlugin;
import com.hiddentech.grid.objects.block.PistonBreakable;
import com.hiddentech.grid.objects.ticking.Tickable;
import com.hiddentech.persistantance.ObjectData;
import com.hiddentech.persistantance.Persistence;
import com.hiddentech.persistantance.types.PersistentLocation;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockBreakEvent;

public class RainbowWool implements PistonBreakable, Tickable, PersistentLocation {
    private Location location;
    private Block block;
    private Boolean loaded;
    private int id;
    private int material = 0;
    public RainbowWool(ObjectData objectData){
        this.loaded = false;
        this.block = objectData.getLocation().getBlock();
        this.location = block.getLocation();
        this.id = objectData.getId();
        GridPlugin.getGridAPI().insertObject(this);
        load();
        GridPlugin.getGridHandler().getLoaded().put(this, (short) 2);
    }
    public RainbowWool(Location location){
        this.loaded = false;
        this.block = location.getBlock();
        this.location = block.getLocation();
        GridPlugin.getGridAPI().insertObject(this);
        load();
        GridPlugin.getGridHandler().getLoaded().put(this, (short) 2);
        this.id = Persistence.getApi().getNextID("Rainbow-Wool");
        Persistence.getApi().save("Rainbow-Wool",this);
    }
    @Override
    public void run(BlockBreakEvent blockBreakEvent) {
        blockBreakEvent.setCancelled(false);
        blockBreakEvent.setDropItems(false);
        destroy();
        blockBreakEvent.getBlock().getWorld().dropItemNaturally(blockBreakEvent.getBlock().getLocation(),GridPlugin.getItemHandler().getCustomItemByTag("RAINBOW-WOOL").getItem());

    }

    @Override
    public String getDrops() {
        return "Rainbow Wool*1";
    }

    @Override
    public Block getBlock() {
        return this.block;
    }

    @Override
    public String getName() {
        return "Rainbow Wool";
    }

    @Override
    public void tick() {
        if(this.material<8) this.material++;else this.material=0;
        Material m=Material.WHITE_WOOL;
        switch(material){
            case 0:
                m = Material.RED_WOOL;
                break;
            case 1:
                m = Material.ORANGE_WOOL;
                break;
            case 2:
                m = Material.YELLOW_WOOL;
                break;
            case 3:
                m = Material.LIME_WOOL;
                break;
            case 4:
                m = Material.CYAN_WOOL;
                break;
            case 5:
                m = Material.LIGHT_BLUE_WOOL;
                break;
            case 6:
                m = Material.BLUE_WOOL;
                break;
            case 7:
                m = Material.PURPLE_WOOL;
                break;
            case 8:
                m = Material.PINK_WOOL;
                break;
        }
        this.block.setType(m);
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
        GridPlugin.getGridAPI().removeObject(this);
        Persistence.getApi().delete("Rainbow-Wool",this);
    }

    @Override
    public Location getLocation() {
        return this.location;
    }

}
