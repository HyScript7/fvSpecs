/*
 * @author: SomeKristi
 */
package me.hyscript7.fvspecs.listeners.voidrealm.behaviour;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;
import java.util.logging.Logger;

public class AntiBuild implements Listener {
    private final Plugin plugin;
    private final Logger logger;
    private final String dimensionName = "world_fvspecs_voidrealm";
    private final String[] allowedBlocks = {
            "BEETROOTS",
            "CACTUS",
            "CARROTS",
            "COCOA",
            "CHORUS_FLOWER",
            "KELP",
            "MELON_STEM",
            "NETHER_WART",
            "POTATOES",
            "PUMPKIN_STEM",
            "SUGAR_CANE",
            "SWEET_BERRY_BUSH",
            "TORCHFLOWER",
            "TORCHFLOWER_CROP",
            "WHEAT",
            "MELON",
            "PUMPKIN"
    };

    public AntiBuild(Plugin p) {
        this.plugin = p;
        this.logger = p.getLogger();
    }

    @EventHandler
    public void onBlockBreakInVoid(BlockBreakEvent event) {
        Block block = event.getBlock();

        // check if the event is happening in the right dimension
        if (block.getLocation().getWorld().getName().equals(dimensionName)) {

            // check if the effected block is an exception and player is a void bound
            if (!Arrays.asList(allowedBlocks).contains((block.getType().toString())) && playerIsNotVoidBoundRace(event.getPlayer())) {

                // cancel if non exception
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onBlockPlaceInVoid(BlockPlaceEvent event) {
        Block block = event.getBlock();

        // check if the event is happening in the right dimension
        if (block.getLocation().getWorld().getName().equals(dimensionName)) {

            // check if the effected block is an exception
            if (!Arrays.asList(allowedBlocks).contains((block.getType().toString())) && playerIsNotVoidBoundRace(event.getPlayer())) {

                // cancel if non exception
                event.setCancelled(true);
            }
        }
    }

    private boolean playerIsNotVoidBoundRace(Player player) {
        return true;
    }
}
