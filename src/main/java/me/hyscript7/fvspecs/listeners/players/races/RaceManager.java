package me.hyscript7.fvspecs.listeners.players.races;

import me.hyscript7.fvspecs.datastore.DatastoreManager;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

public class RaceManager implements Listener {
    private final Plugin plugin;
    private final Logger logger;
    private DatastoreManager datastoreManager;
    private Race[] races;
    private Map<Integer, Race> raceLookupMap = new ConcurrentHashMap<>();

    public RaceManager(Plugin p, DatastoreManager d) {
        this.plugin = p;
        this.datastoreManager = d;
        this.logger = p.getLogger();
        races = new Race[]{new Human(p, d)};
        for (Race r : races) {
            this.plugin.getServer().getPluginManager().registerEvents((Listener) r, this.plugin);
            logger.info("Registered race " + r.getName());
            raceLookupMap.put(r.getId(), r);
        }
    }
    public int getRandomRaceId() {
        int i = (int) (Math.random() * races.length);
        return races[i].getId();
    }

    public Race getRace(int id) {
        return raceLookupMap.get(id);
    }
}
