package com.hiddentech.campfiredemo;

import com.hiddentech.grid.GridPlugin;
import com.hiddentech.persistantance.Persistence;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.InvocationTargetException;

public final class CampFireDemo extends JavaPlugin {
    private static CampFireDemo plugin;

    public static CampFireDemo getPlugin() {
        return plugin;
    }


    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;
        Bukkit.getServer().getPluginManager().registerEvents(new BlockPlaceListener(), this);
        Persistence.getApi().register("Campfires");
        Persistence.getApi().register("Beacons");
        Persistence.getApi().register("HoloDemo");
        Persistence.getApi().register("MagmaDemo");
        Persistence.getApi().register("Air-Sealer");
        Persistence.getApi().register("Rainbow-Wool");
        Persistence.getApi().load("Beacons",Beacon.class);
        Persistence.getApi().load("Campfires",CampFire.class);
        Persistence.getApi().load("HoloDemo",Hologram.class);
        Persistence.getApi().load("MagmaDemo",MagmaBlock.class);
        Persistence.getApi().load("Air-Sealer",AirSealer.class);
        Persistence.getApi().load("Rainbow-Wool",RainbowWool.class);
        GridPlugin.itemHandler.registerCustomItem(new BeaconItem());
        GridPlugin.itemHandler.registerCustomItem(new AppleJuice());
        GridPlugin.itemHandler.registerCustomItem(new AirSealerItem());
        GridPlugin.itemHandler.registerCustomItem(new RainbowWoolItem());
        ScrambledEgg  egg=new ScrambledEgg();
        GridPlugin.itemHandler.registerCustomItem(egg);
        HeartyBreakfast heartyBreakfast = new HeartyBreakfast();
        GridPlugin.itemHandler.registerCustomItem(heartyBreakfast);

        NamespacedKey key = new NamespacedKey(this, "key");
        ShapedRecipe  recipe= new ShapedRecipe(key,heartyBreakfast.getItem());
        recipe.shape("   "," c ","   ");

        recipe.setIngredient('c', new RecipeChoice.ExactChoice(egg.getItem()));
        Bukkit.addRecipe(recipe);
        //Persistence.getApi().load("Beacons",Beacon.class);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
