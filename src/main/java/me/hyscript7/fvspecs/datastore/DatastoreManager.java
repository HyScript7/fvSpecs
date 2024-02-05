package me.hyscript7.fvspecs.datastore;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

public class DatastoreManager {
    private final Plugin plugin;
    private final Logger pluginLogger;
    private Map<UUID, PlayerStore> players = new ConcurrentHashMap<>();
    public DatastoreManager(Plugin p) {
        this.plugin = p;
        this.pluginLogger = p.getLogger();
    }
    public PlayerStore getPlayerStore( Player p) {
        UUID playerUUID = p.getUniqueId();
        if (!players.containsKey(playerUUID)) {
            players.put(playerUUID, new PlayerStore(plugin, p));
        }
        return players.get(playerUUID);
    }
}
