package me.hyscript7.fvspecs.listeners.players.races;

import lombok.Getter;
import me.hyscript7.fvspecs.datastore.DatastoreManager;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import java.util.logging.Logger;

public class Human implements Race, Listener {
    @Getter
    private final int ID = 0;
    @Getter
    private final String NAME = "Human";
    private final Plugin plugin;
    private final Logger logger;
    private DatastoreManager datastoreManager;

    public Human(Plugin p, DatastoreManager d) {
        this.plugin = p;
        this.datastoreManager = d;
        this.logger = p.getLogger();
    }

}
