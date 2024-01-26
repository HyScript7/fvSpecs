package me.hyscript7.fvspecs.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class VoidRealmPortal implements Listener {
    private static final String WORLD_NAME_VOID_REALM = "world_fvspecs_voidrealm";
    private final Plugin plugin;

    public VoidRealmPortal(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent e) {
        if (e.isCancelled()) {
            return;
        }
        if (e.getEntity() instanceof Player p) {
            // TODO: Move this to void realm behaviour class
            // Scenario 1: Prevent fall damage in the Void Realm
            if (p.getLocation().getWorld().getName().equals(WORLD_NAME_VOID_REALM)) {
                if (0 == e.getCause().compareTo(EntityDamageEvent.DamageCause.FALL)) {
                    e.setCancelled(true);
                }
            }

            // Scenario 2: The player has jumped into the void
            // Do not teleport if the cause of damage is not the void
            if (0 != e.getCause().compareTo(EntityDamageEvent.DamageCause.VOID)) {
                return;
            }
            // Do not teleport if we are above Y = -64
            if (p.getLocation().getY() > -64) {
                return;
            }
            // Do not teleport if the player has less than 6 hearts
            if (p.getHealth() < 12) {
                return;
            }
            // Handle end void
            if (p.getLocation().getWorld().getName().equals("world_the_end")) {
                // TODO: Check level and perform prestige
                teleportPlayerToVoidRealm(p);
                e.setCancelled(true);
                return;
            }
            // Do not do anything if we are not in the overworld or end voids
            if (!p.getLocation().getWorld().getName().equals("world")) {
                return;
            }
            // Handle overworld void
            teleportPlayerToVoidRealm(p);
            e.setCancelled(true);
        }
    }

    private void teleportPlayerToVoidRealm(Player p) {
        Location playerLoc = p.getLocation();
        Location loc = new Location(Bukkit.getWorld(WORLD_NAME_VOID_REALM), (double) (playerLoc.getBlockX() - playerLoc.getBlockX() % 16) / 16, 300, (double) (playerLoc.getBlockZ() - playerLoc.getBlockZ() % 16) / 16, 0, 0);
        Location exitWellLoc = loc.clone();
        exitWellLoc.setY(1.0);
        p.teleport(loc);
        // Heal the player
        p.setHealth(p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getDefaultValue());
        // Summon the exit well structure
        new BukkitRunnable() {
            @Override
            public void run() {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "execute in fvspecs:voidrealm run place template fvspecs:well_exit " + (exitWellLoc.getBlockX()-5) + " " + 1 + " " + (exitWellLoc.getBlockZ()-5) + " none none");
            }
        }.runTaskLater(this.plugin, 1);
    }

    @EventHandler
    public void onPlayerUse(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (!p.getLocation().getWorld().getName().equals(WORLD_NAME_VOID_REALM)) {
            return;
        }
        Block block = e.getClickedBlock();
        if (block == null) {
            return;
        }
        if (block.getBlockData().getMaterial().equals(Material.SCULK_SHRIEKER)) {
            // The PlayerInteractEvent is sent separately for each arm (we have 2 arms), so we only want to handle one.
            if (e.getHand() != EquipmentSlot.HAND) {
                return;
            }
            // If an animation is already in progress, do not start it again, instead quit.
            if (p.hasPotionEffect(PotionEffectType.LEVITATION)) {
                return;
            }
            rescueLivingPlayer(p, block.getLocation());
        }
    }

    private void rescueLivingPlayer(Player p, Location shrineLocation) {
        // TODO: Check lives (whether the player is a living or a voided)
        Location loc = new Location(Bukkit.getWorld("world"), shrineLocation.getBlockX() * 16, 320, shrineLocation.getBlockZ() * 16);
        p.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 60, 10));
        new BukkitRunnable() {
            @Override
            public void run() {
                p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 200, 10));
                p.teleport(loc);
                p.setHealth(p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getDefaultValue());
            }
        }.runTaskLater(this.plugin, 59);
    }
}
