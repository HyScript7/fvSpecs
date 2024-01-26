package me.hyscript7.fvspecs;

import me.hyscript7.fvspecs.listeners.VoidRealmPortal;
import org.bukkit.plugin.java.JavaPlugin;

public final class FvSpecs extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.getServer().getPluginManager().registerEvents(new VoidRealmPortal(this), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
