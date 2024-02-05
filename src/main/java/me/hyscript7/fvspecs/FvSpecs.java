package me.hyscript7.fvspecs;

import lombok.Getter;
import me.hyscript7.fvspecs.datastore.DatastoreManager;
import me.hyscript7.fvspecs.listeners.players.PlayerDataInitializer;
import me.hyscript7.fvspecs.listeners.players.PlayerStatListener;
import me.hyscript7.fvspecs.listeners.voidrealm.behaviour.AntiBuild;
import me.hyscript7.fvspecs.listeners.voidrealm.gatekeeper.EntryListener;
import me.hyscript7.fvspecs.listeners.voidrealm.gatekeeper.ExitListener;
import me.hyscript7.fvspecs.listeners.voidrealm.gatekeeper.ResurrectionListener;
import org.bukkit.event.Listener;
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
        registerListener(new EntryListener(this, datastoreManager));
        registerListener(new ExitListener(this, datastoreManager));
        registerListener(new AntiBuild(this));
        registerListener(new PlayerDataInitializer(this, datastoreManager));
        registerListener(new PlayerStatListener(this, datastoreManager));
        registerListener(new ResurrectionListener(this, datastoreManager));
    }

    private void registerListener(Listener l) {
        this.getServer().getPluginManager().registerEvents(l, this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
