package me.hyscript7.fvspecs;

import me.hyscript7.fvspecs.datastore.DatastoreManager;
import me.hyscript7.fvspecs.listeners.VoidRealmPortal;
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
        this.getServer().getPluginManager().registerEvents(new VoidRealmPortal(this), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        logger.info("Closing database file through datastore manager");
        datastoreManager.getFileManager().close();
    }
}
