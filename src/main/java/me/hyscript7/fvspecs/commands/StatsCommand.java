package me.hyscript7.fvspecs.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;

import me.hyscript7.fvspecs.ExperienceFlyweight;
import me.hyscript7.fvspecs.datastore.DatastoreManager;
import me.hyscript7.fvspecs.datastore.PlayerStore;
import me.hyscript7.fvspecs.listeners.players.races.RaceManager;

public class StatsCommand implements CommandExecutor {
    private DatastoreManager datastoreManager;
    private RaceManager raceManager;

    public StatsCommand(DatastoreManager datastoreManager, RaceManager raceManager) {
        this.datastoreManager = datastoreManager;
        this.raceManager = raceManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command!");
            return true;
        }
        Player player = (Player) sender;
        PlayerStore playerStore = datastoreManager.getPlayerStore(player);

        String message = String.format(
                "Level: %d\n" +
                        "Prestige: %d\n" +
                        "Race: %s\n" +
                        "Kills: %d\n" +
                        "Deaths: %d\n" +
                        "Exp: %d/%d\n" +
                        "Lives: %d/%d\n" +
                        "Bounty: %d",
                playerStore.getLevel(),
                playerStore.getPrestige(),
                raceManager.getRace(playerStore.getRace()).getName(),
                playerStore.getKills(),
                playerStore.getDeaths(),
                playerStore.getExp(),
                ExperienceFlyweight.calculateLevelExP(player.getLevel() + 1),
                playerStore.getLives(),
                PlayerStore.MAX_LIVES,
                playerStore.getBounty());

        sender.sendMessage(message);

        return true;
    }
}
