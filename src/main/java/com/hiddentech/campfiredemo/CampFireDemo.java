package com.hiddentech.campfiredemo;

import com.hiddentech.grid.GridAddon;
import com.hiddentech.grid.GridPlugin;
import com.hiddentech.grid.items.category.Category;
import com.hiddentech.grid.utilities.ItemUtility;
import com.hiddentech.persistantance.Persistence;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;
import java.lang.reflect.InvocationTargetException;

public final class CampFireDemo extends JavaPlugin implements GridAddon {
    private static CampFireDemo plugin;

    public static CampFireDemo getPlugin() {
        return plugin;
    }


    @Override
    public void onEnable() {
        // Plugin startup logic
        //register our grid addon
        register(this);
        plugin = this;
        Bukkit.getServer().getPluginManager().registerEvents(new BlockPlaceListener(), this);
        Persistence.getApi().register("Campfires");
        Persistence.getApi().register("Beacons");
        Persistence.getApi().register("HoloDemo");
        Persistence.getApi().register("MagmaDemo");
        Persistence.getApi().register("Air-Sealer");
        Persistence.getApi().register("Rainbow-Wool");
        Persistence.getApi().register("Milk-Block");
        Persistence.getApi().load("Beacons",Beacon.class);
        Persistence.getApi().load("Campfires",CampFire.class);
        Persistence.getApi().load("HoloDemo",Hologram.class);
        Persistence.getApi().load("MagmaDemo",MagmaBlock.class);
        Persistence.getApi().load("Air-Sealer",AirSealer.class);
        Persistence.getApi().load("Rainbow-Wool",RainbowWool.class);
        Persistence.getApi().load("Milk-Block",MilkBlock.class);
        Plugin plugin;
        NamespacedKey key = new NamespacedKey(this, "Basics");
        Category category = new Category(key, ItemUtility.createItem(Material.SPONGE, ChatColor.GREEN+"Demo Items",""));
        category.register(this);
        GridPlugin.itemHandler.registerCustomItem(category.add(new BeaconItem()));
        GridPlugin.itemHandler.registerCustomItem(category.add(new AppleJuice()));
        GridPlugin.itemHandler.registerCustomItem(category.add(new AirSealerItem()));
        GridPlugin.itemHandler.registerCustomItem(category.add(new RainbowWoolItem()));
        GridPlugin.itemHandler.registerCustomItem(category.add(new MilkBlockItem()));
        GridPlugin.itemHandler.registerCustomItem(category.add(new ScrambledEgg()));
        GridPlugin.itemHandler.registerCustomItem(category.add(new HeartyBreakfast()));
        GridPlugin.itemHandler.registerCustomItem(category.add(new HologramItem()));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @Nonnull
    @Override
    public JavaPlugin getJavaPlugin() {
        return this;
    }
}
