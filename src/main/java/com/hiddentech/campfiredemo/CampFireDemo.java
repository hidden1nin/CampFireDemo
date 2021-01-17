package com.hiddentech.campfiredemo;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

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
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
