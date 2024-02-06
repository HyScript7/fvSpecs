package me.hyscript7.fvspecs.listeners.voidrealm.gatekeeper;

import me.hyscript7.fvspecs.datastore.DatastoreManager;
import me.hyscript7.fvspecs.datastore.PlayerStore;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Objects;
import java.util.logging.Logger;

public class ResurrectionListener implements Listener {
    private final Plugin plugin;
    private final Logger logger;
    private DatastoreManager datastoreManager;
    private final String[] animationCommands = new String[]{"particle shriek 10 ~ ~1 ~ 0 1 0 0 100 force", "particle flash 10 ~ ~1 0 0 0 0 5 force"};
    private World voidRealm;
    public ResurrectionListener(Plugin p, DatastoreManager d) {
        this.plugin = p;
        this.datastoreManager = d;
        this.logger = p.getLogger();
        new BukkitRunnable() {
            @Override
            public void run() {
                setVoidRealm(Bukkit.getWorld("world_fvspecs_voidrealm"));
            }
        }.runTaskLater(this.plugin, 1); // One tick after world loads
    }
    private void setVoidRealm(World w) {
        voidRealm = w;
    }

    @EventHandler
    public void onItemPickup(EntityPickupItemEvent e) {
        if (!(e.getEntity() instanceof Player player)) {
            return;
        }
        if (!e.getEntity().getWorld().equals(voidRealm)) {
            return;
        }
        if (!e.getItem().getItemStack().getType().equals(Material.NETHER_STAR)) {
            return;
        }
        PlayerStore playerStore = datastoreManager.getPlayerStore(player);
        if (playerStore.getLives() > 0) {
            return;
        }
        e.getItem().remove();
        playerStore.setLives(10);
        Location animationLocation = player.getLocation();
        // TODO: Get player's spawnpoint instead
        Location respawnLocation = Objects.requireNonNull(Bukkit.getWorld("world")).getSpawnLocation();
        playAnimation(animationLocation);
        player.teleport(respawnLocation);
    }

    private void playAnimation(Location l) {
        for (String command : animationCommands) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "execute in fvspecs:voidrealm positioned " + l.getBlockX() + " " + l.getBlockY() + " " + l.getBlockZ() + " run " + command);
        }
    }
}
