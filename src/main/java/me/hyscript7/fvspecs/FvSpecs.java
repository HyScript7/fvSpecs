package me.hyscript7.fvspecs;

import me.hyscript7.fvspecs.datastore.DatastoreManager;
import me.hyscript7.fvspecs.listeners.voidrealm.behaviour.AntiBuild;
import me.hyscript7.fvspecs.listeners.voidrealm.gatekeeper.EntryListener;
import me.hyscript7.fvspecs.listeners.voidrealm.gatekeeper.ExitListener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class FvSpecs extends JavaPlugin {
    private Logger logger = this.getLogger();
    private DatastoreManager datastoreManager;

    @Override
    public void onEnable() {
        // Plugin startup logic
        logger.info("Loading datastore manager");
        this.datastoreManager = new DatastoreManager(this);
        logger.info("Registering listeners");
        this.getServer().getPluginManager().registerEvents(new EntryListener(this), this);
        this.getServer().getPluginManager().registerEvents(new ExitListener(this), this);
        this.getServer().getPluginManager().registerEvents(new AntiBuild(this), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        logger.info("Closing database file through datastore manager");
        datastoreManager.getFileManager().close();
    }
}
