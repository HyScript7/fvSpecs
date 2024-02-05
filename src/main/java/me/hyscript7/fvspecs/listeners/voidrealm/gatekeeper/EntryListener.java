package me.hyscript7.fvspecs.listeners.voidrealm.gatekeeper;

import me.hyscript7.fvspecs.datastore.DatastoreManager;
import me.hyscript7.fvspecs.datastore.PlayerStore;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.logging.Logger;

public class EntryListener implements Listener {
    private final Plugin plugin;
    private final Logger logger;
    private DatastoreManager datastoreManager;
    private World voidRealm;

    public EntryListener(Plugin p, DatastoreManager d) {
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
    public void onFallDamage(EntityDamageEvent e) {
        if (e.isCancelled()) {
            return;
        }
        if (!e.getEntityType().equals(EntityType.PLAYER)) {
            return;
        }
        if (!e.getCause().equals(EntityDamageEvent.DamageCause.FALL)) {
            return;
        }
        if (e.getEntity().getWorld().equals(voidRealm)) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent e) {
        if (e.isCancelled()) {
            return;
        }
        if (!isVoidTypeDamage(e)) {
            return;
        }
        if (!entityIsPlayer(e)) {
            return;
        }
        if (!worldQualifiesForTeleportationToVoidRealm(e.getEntity().getWorld())) {
            return;
        }
        Player player = (Player) e.getEntity();
        if (!playerQualifiesForTeleportation(player)) {
            return;
        }
        e.setCancelled(true); // Stop the event from propagating further
        if (isPrestigeRitual(player)) {
            // TODO: Modify player stats for the prestige ritual
            teleportPlayerToVoidRealm(player, false);
            return;
        }
        teleportPlayerToVoidRealm(player, true);
    }

    private boolean entityIsPlayer(EntityDamageEvent e) {
        return e.getEntityType().compareTo(EntityType.PLAYER) == 0;
    }

    private boolean isVoidTypeDamage(EntityDamageEvent e) {
        return e.getCause().compareTo(EntityDamageEvent.DamageCause.VOID) == 0;
    }

    private boolean worldQualifiesForTeleportationToVoidRealm(World w) {
        String worldName = w.getName();
        return worldName.equals("world") || worldName.equals("world_the_end");
    }

    private boolean isPrestigeRitual(Player p) {
        World w = p.getWorld();
        boolean worldIsTheEnd = w.getName().equals("world_the_end");
        PlayerStore playerStore = datastoreManager.getPlayerStore(p);
        boolean playerIsMaxLevel = playerStore.getLevel() == 50;
        return worldIsTheEnd && playerIsMaxLevel;
    }

    private double getPlayerMaxHealth(Player p) {
        double maxHealth;
        try {
            maxHealth = p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getDefaultValue();
        } catch (NullPointerException ignored) {
            maxHealth = 20.0;
        }
        return maxHealth;
    }

    private boolean playerQualifiesForTeleportation(Player p) {
        double maxHealth = getPlayerMaxHealth(p);
        return !(p.getHealth() < 0.6 * maxHealth);
    }

    private int roundCoordinate(int x) {
        return (x - (x % 16)) / 16;
    }

    private Location translateStandardLocationToVoidRealmLocation(Location l) {
        return new Location(voidRealm, roundCoordinate(l.getBlockX()), 320, roundCoordinate(l.getBlockZ()), l.getYaw(), l.getPitch());
    }

    private void healPlayer(Player p) {
        double maxHealth = getPlayerMaxHealth(p);
        p.setHealth(maxHealth);
    }

    private void teleportPlayerToVoidRealm(Player p, boolean spawnExitWell) {
        Location playerLocation = p.getLocation();
        Location targetLocation = translateStandardLocationToVoidRealmLocation(playerLocation);
        // Calculate exit well location (deltas are required due to structure block position)
        Location exitWellLocation = targetLocation.clone();
        exitWellLocation.setY(1);
        exitWellLocation.setX(exitWellLocation.getBlockX() - 5);
        exitWellLocation.setZ(exitWellLocation.getBlockZ() - 5);
        // Teleport the player
        p.teleport(targetLocation);
        healPlayer(p);
        p.addPotionEffect(PotionEffectType.DARKNESS.createEffect(120, 1)); // Give the player blindness while they fall
        if (spawnExitWell) {
            // Spawn the exit well after the chunks load
            new BukkitRunnable() {
                @Override
                public void run() {
                    spawnExitWellStructure(exitWellLocation);
                }
            }.runTaskLater(this.plugin, 21); // Delayed by 21 ticks (1.05 seconds) in hopes the world loads, since sometimes the chunks load late and we don't place the exit well
        }
    }

    private void spawnExitWellStructure(Location l) {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
                "execute in fvspecs:voidrealm run place template fvspecs:well_exit " +
                        l.getBlockX() + " " + l.getBlockY() + " " + l.getBlockZ() + " none none");
    }
}
