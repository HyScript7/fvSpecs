/*
 * @author: SomeKristi
 */
package me.hyscript7.fvspecs.listeners.voidrealm.behaviour;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;
import java.util.logging.Logger;

public class AntiBuild implements Listener {
    private final Plugin plugin;
    private final Logger logger;
    private final String dimensionName = "world_fvspecs_voidrealm";
    private final Material[] whitelistedBlocks = {
            Material.BEETROOT,
            Material.BEETROOT_SEEDS,
            Material.BEETROOTS,
            Material.CACTUS,
            Material.CARROT,
            Material.CARROTS,
            Material.COCOA,
            Material.COCOA_BEANS,
            Material.CHORUS_FLOWER,
            Material.CHORUS_PLANT,
            Material.KELP,
            Material.KELP_PLANT,
            Material.MELON,
            Material.MELON_SEEDS,
            Material.MELON_STEM,
            Material.PUMPKIN,
            Material.PUMPKIN_SEEDS,
            Material.PUMPKIN_STEM,
            Material.NETHER_WART,
            Material.POTATO,
            Material.POTATOES,
            Material.SUGAR_CANE,
            Material.SWEET_BERRIES,
            Material.SWEET_BERRY_BUSH,
            Material.WHEAT,
            Material.WHEAT_SEEDS,
            Material.CARROT,
            Material.CARROTS,
            Material.POTATO,
            Material.POTATOES,
            Material.BAMBOO,
    };

    public AntiBuild(Plugin p) {
        this.plugin = p;
        this.logger = p.getLogger();
    }

    @EventHandler
    public void onBlockBreakInVoid(BlockBreakEvent event) {
        Block block = event.getBlock();
        if (block.getLocation().getWorld().getName().equals(dimensionName)) {
            if (!(materialIsWhitelisted(block) && playerIsVoidBound(event.getPlayer()))) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onBlockPlaceInVoid(BlockPlaceEvent event) {
        Block block = event.getBlock();
        if (block.getLocation().getWorld().getName().equals(dimensionName)) {
            if (!((materialIsWhitelisted(block) || block.getType().equals(Material.FARMLAND)) && playerIsVoidBound(event.getPlayer()))) {
                event.setCancelled(true);
            }
        }
    }

    private boolean materialIsWhitelisted(Block b) {
        return Arrays.asList(whitelistedBlocks).contains(b.getType());
    }

    private boolean playerIsVoidBound(Player player) {
        return true; // TODO: Do an actuall race check here, instead of assuming yes. (Default should be false)
    }
}
