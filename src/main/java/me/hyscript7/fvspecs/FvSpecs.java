package me.hyscript7.fvspecs;

import lombok.Getter;
import me.hyscript7.fvspecs.datastore.DatastoreManager;
import me.hyscript7.fvspecs.listeners.players.PlayerDataInitializer;
import me.hyscript7.fvspecs.listeners.players.PlayerStatListener;
import me.hyscript7.fvspecs.listeners.voidrealm.behaviour.AntiBuild;
import me.hyscript7.fvspecs.listeners.voidrealm.gatekeeper.EntryListener;
import me.hyscript7.fvspecs.listeners.voidrealm.gatekeeper.ExitListener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class FvSpecs extends JavaPlugin {
    private final Logger logger = this.getLogger();
    @Getter
    private DatastoreManager datastoreManager;

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.datastoreManager = new DatastoreManager(this);
        this.getServer().getPluginManager().registerEvents(new EntryListener(this, datastoreManager), this);
        this.getServer().getPluginManager().registerEvents(new ExitListener(this), this);
        this.getServer().getPluginManager().registerEvents(new AntiBuild(this), this);
        this.getServer().getPluginManager().registerEvents(new PlayerDataInitializer(this, datastoreManager), this);
        this.getServer().getPluginManager().registerEvents(new PlayerStatListener(this, datastoreManager), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
