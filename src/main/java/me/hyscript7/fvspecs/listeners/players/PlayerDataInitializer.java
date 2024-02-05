package me.hyscript7.fvspecs.listeners.players;

import me.hyscript7.fvspecs.datastore.DatastoreManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;

import java.util.logging.Logger;

public class PlayerDataInitializer implements Listener {
    private final Plugin plugin;
    private final Logger logger;
    private DatastoreManager datastoreManager;

    public PlayerDataInitializer(Plugin p, DatastoreManager d) {
        this.plugin = p;
        this.datastoreManager = d;
        this.logger = p.getLogger();
    }
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        // We don't need the return, but will announce the player their stats in the future.
        datastoreManager.getPlayerStore(player);
    }
}
