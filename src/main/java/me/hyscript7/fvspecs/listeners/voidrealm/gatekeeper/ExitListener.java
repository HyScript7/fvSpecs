package me.hyscript7.fvspecs.listeners.voidrealm.gatekeeper;

import me.hyscript7.fvspecs.datastore.DatastoreManager;
import me.hyscript7.fvspecs.datastore.PlayerStore;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Objects;
import java.util.logging.Logger;

public class ExitListener implements Listener {
    private final Plugin plugin;
    private final Logger logger;
    private DatastoreManager datastoreManager;
    private final int MAX_LIVES = 10;
    private World voidRealm;
    private World overworld;

    public ExitListener(Plugin p, DatastoreManager d) {
        this.plugin = p;
        this.datastoreManager = d;
        this.logger = p.getLogger();
        new BukkitRunnable() {
            @Override
            public void run() {
                setVoidRealm(Bukkit.getWorld("world_fvspecs_voidrealm"));
                setOverworld(Bukkit.getWorld("world"));
            }
        }.runTaskLater(this.plugin, 1); // Since the plugin is loaded BEFORE the world, we need to wait 'till after world loads to get the worlds
    }

    private void setVoidRealm(World w) {
        voidRealm = w;
    }

    private void setOverworld(World w) {
        overworld = w;
    }

    @EventHandler
    public void onPlayerUse(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (!isVoidRealmWorld(p.getWorld())) {
            return;
        }
        Block block = e.getClickedBlock();
        if (block == null) {
            return;
        }
        if (!isExitShrineBlock(block)) {
            return;
        }
        // The PlayerInteractEvent is sent separately for each arm (we have 2 arms)
        // In this case, we only need the interact event is self, not the items in the hands, so we only need it once, which is why we ignore the other hand.
        if (e.getHand() != EquipmentSlot.HAND) {
            return;
        }
        PlayerStore playerStore = datastoreManager.getPlayerStore(p);
        if (playerStore.getPrestigeInProgress() == 1) {
            playerStore.setPrestigeInProgress(0);
            playerStore.setLives(MAX_LIVES);
            sendPlayerToSpawnpoint(p);
            return;
        }
        if (playerStore.getLives() <= 0) {
            return;
        }
        sendPlayerToOverworld(p, block.getLocation());
    }

    private boolean isVoidRealmWorld(World w) {
        return w.getName().compareTo("world_fvspecs_voidrealm") == 0;
    }

    private boolean isExitShrineBlock(Block b) {
        return b.getBlockData().getMaterial().equals(Material.SCULK_SHRIEKER);
    }

    private Location translateLayerOneLocationToStandardLocation(Location l, Location p) {
        return new Location(overworld, l.getBlockX()*16, 320, l.getBlockZ()*16, p.getYaw(), p.getPitch());
    }

    private void healPlayer(Player p) {
        double maxHealth;
        try {
            maxHealth = p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getDefaultValue();
        } catch (NullPointerException ignored) {
            maxHealth = 20.0;
        }
        p.setHealth(maxHealth);
    }

    private void sendPlayerToSpawnpoint(Player p) {
        /*
        Location spawnpoint = p.getRespawnLocation();
        if (spawnpoint == null) {
            sendPlayerToOverworld(p, p.getLocation()); // fallback to default shrine behaviour
            return;
        }
         */
        Location spawnpoint = overworld.getSpawnLocation();
        healPlayer(p);
        p.teleport(spawnpoint);
    }

    private void sendPlayerToOverworld(Player p, Location l) {
        Location targetLocation = translateLayerOneLocationToStandardLocation(l, p.getLocation());
        int ANIMATION_LENGTH = 80;
        playAnimation(p, l, ANIMATION_LENGTH);
        new BukkitRunnable() {
            @Override
            public void run() {
                p.teleport(targetLocation);
                healPlayer(p);
                p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 200, 10));
            }
        }.runTaskLater(this.plugin, ANIMATION_LENGTH-1);
        // The teleport there is done a little bit before the animation ends, so that the player doesn't fall as the levitation effect expires before they are teleported.
    }

    private void playAnimation(Player p, Location l, int animationLength) {
        Location shrineLocation = l.clone();
        shrineLocation.setY(shrineLocation.getBlockY()+1);
        p.teleport(shrineLocation);
        p.addPotionEffect(PotionEffectType.LEVITATION.createEffect(animationLength, 20));
        p.addPotionEffect(PotionEffectType.BLINDNESS.createEffect(animationLength, 20));
        p.addPotionEffect(PotionEffectType.GLOWING.createEffect(200+animationLength, 20)); // This glowing effect will persist for the entire duration of the animation AND the duration of the resistance after teleportation.
    }
}
