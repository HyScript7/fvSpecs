package me.hyscript7.fvspecs;

import java.util.logging.Logger;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import lombok.Getter;
import me.hyscript7.fvspecs.commands.StatsCommand;
import me.hyscript7.fvspecs.datastore.DatastoreManager;
import me.hyscript7.fvspecs.listeners.players.PlayerDataInitializer;
import me.hyscript7.fvspecs.listeners.players.PlayerStatListener;
import me.hyscript7.fvspecs.listeners.voidrealm.behaviour.AntiBuild;
import me.hyscript7.fvspecs.listeners.voidrealm.gatekeeper.EntryListener;
import me.hyscript7.fvspecs.listeners.voidrealm.gatekeeper.ExitListener;
import me.hyscript7.fvspecs.listeners.voidrealm.gatekeeper.ResurrectionListener;

public final class FvSpecs extends JavaPlugin {
    private final Logger logger = this.getLogger();
    @Getter
    private DatastoreManager datastoreManager;
    private PlayerDataInitializer playerDataInitializer;

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.datastoreManager = new DatastoreManager(this);
        this.playerDataInitializer = new PlayerDataInitializer(this, datastoreManager);
        registerListener(new EntryListener(this, datastoreManager));
        registerListener(new ExitListener(this, datastoreManager));
        registerListener(new AntiBuild(this));
        registerListener(playerDataInitializer);
        registerListener(new PlayerStatListener(this, datastoreManager));
        registerListener(new ResurrectionListener(this, datastoreManager));
        this.getCommand("stats").setExecutor(new StatsCommand(datastoreManager, this.playerDataInitializer.raceManager)); // ew wtf
    }

    private void registerListener(Listener l) {
        this.getServer().getPluginManager().registerEvents(l, this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
