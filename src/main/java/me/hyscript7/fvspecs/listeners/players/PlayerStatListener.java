package me.hyscript7.fvspecs.listeners.players;

import me.hyscript7.fvspecs.datastore.DatastoreManager;
import me.hyscript7.fvspecs.datastore.PlayerStore;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.Plugin;

import java.util.logging.Logger;

public class PlayerStatListener implements Listener {
    private final Plugin plugin;
    private final Logger logger;
    private final int MAX_LEVEL = 50;
    private final DatastoreManager datastoreManager;

    public PlayerStatListener(Plugin p, DatastoreManager d) {
        this.plugin = p;
        this.datastoreManager = d;
        this.logger = p.getLogger();
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        Player killer = e.getEntity().getKiller();
        if (killer == null) {
            return;
        }
        Player victim = e.getPlayer();
        // Get player data wrappers (stores)
        PlayerStore victimStore = datastoreManager.getPlayerStore(victim);
        PlayerStore killerStore = datastoreManager.getPlayerStore(killer);
        // Run update operations
        updatePlayerStats(killerStore, victimStore);
        // Check whether the hunter is eligible for any level ups.
        checkLevelUpdates(killerStore);
    }

    private void updatePlayerStats(PlayerStore killerStore, PlayerStore victimStore) {
        // Execute custom stat update operations
        killerStore.setKills(killerStore.getKills() + 1);
        killerStore.setExp(killerStore.getExp() + calculatePlayerExpDrop(victimStore.getLevel(), victimStore.getBounty(), killerStore.getLevel()));
        killerStore.setBounty(killerStore.getBounty() + calculateBounty(killerStore.getLevel(), victimStore.getLevel()));
        victimStore.setDeaths(victimStore.getDeaths() + 1);
        victimStore.setLives(victimStore.getLives() - 1);
        victimStore.setBounty(0);
    }

    /*
     * Executes level up checks for the provided player
     * @param playerStore: The store wrapper of the player to check level ups for
     */
    private void checkLevelUpdates(PlayerStore playerStore) {
        int expNext = calculateLevelExP(playerStore.getLevel() + 1);
        do {
            if (playerStore.getLevel() >= MAX_LEVEL) {
                return;
            }
            if (playerStore.getExp() >= expNext) {
                playerStore.setLevel(playerStore.getLevel() + 1);
                playerStore.setExp(playerStore.getExp() - expNext);
            }
            expNext = calculateLevelExP(playerStore.getLevel() + 1);
        } while (playerStore.getExp() >= expNext);
    }

    private int calculateLevelExP(int level) {
        // Magic formula for how much ExP is required for the specified level
        return (level - 1) * 150 * (level - 2) / 2 + 1;
    }

    private int calculateBounty(int killerLevel, int victimLevel) {
        if (victimLevel+5 < killerLevel) {
            return calculatePlayerLevelDelta(victimLevel, killerLevel) * 5;
        }
        return 5;
    }

    /*
     * Returns the difference in level of 2 players
     */
    private int calculatePlayerLevelDelta(int l1, int l2) {
        return Math.max(l1, l2) - Math.min(l1, l2);
    }

    private int calculatePlayerExpDrop(int victimLevel, int victimBounty, int killerLevel) {
        return (1 + killerLevel/10) * (victimLevel * 5 + victimBounty / 5);
    }
}
