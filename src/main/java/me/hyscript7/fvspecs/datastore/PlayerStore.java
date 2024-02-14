package me.hyscript7.fvspecs.datastore;

import lombok.Getter;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

import java.util.Map;

public class PlayerStore {
    // Internals
    private final Plugin plugin;
    private final PersistentDataContainer dataContainer;
    private final int MAX_LIVES = 10;
    private final Map<String, Integer> defaultData = Map.of("level", 1, "prestige", 0, "exp", 0, "lives", MAX_LIVES, "bounty", 0, "kills", 0, "deaths", 0, "prip", 0);

    // Player stats
    @Getter
    private int level;
    @Getter
    private int prestige;
    @Getter
    private int exp;
    @Getter
    private int lives;
    @Getter
    private int bounty;
    @Getter
    private int kills;
    @Getter
    private int deaths;
    @Getter
    private int prestigeInProgress;

    public PlayerStore(Plugin pl, Player p) {
        this.plugin = pl;
        this.dataContainer = p.getPersistentDataContainer();
        // Setup container with default data if not present already
        for (Map.Entry<String, Integer> e :
                defaultData.entrySet()) {
            setDataKeyValue(e.getKey(), getDataKeyValue(e.getKey()));
        }
        // Load data from container
        level = getDataKeyValue("level");
        prestige = getDataKeyValue("prestige");
        exp = getDataKeyValue("exp");
        lives = getDataKeyValue("lives");
        bounty = getDataKeyValue("bounty");
        kills = getDataKeyValue("kills");
        deaths = getDataKeyValue("deaths");
        prestigeInProgress = getDataKeyValue("prip");
    }

    private int getDataKeyValue(String key) {
        return dataContainer.getOrDefault(
                new NamespacedKey(plugin, key),
                PersistentDataType.INTEGER,
                defaultData.get(key) // Default value in case NBT is null
        );
    }

    private void setDataKeyValue(String k, int v) {
        NamespacedKey key = new NamespacedKey(plugin, k);
        dataContainer.set(key, PersistentDataType.INTEGER, v);
    }

    public void setLevel(int level) {
        this.level = level;
        setDataKeyValue("level", this.level);
    }

    public void setPrestige(int prestige) {
        this.prestige = prestige;
        setDataKeyValue("prestige", this.prestige);
    }

    public void setExp(int exp) {
        this.exp = exp;
        setDataKeyValue("exp", this.exp);
    }

    public void setLives(int lives) {
        if (lives < 0) {
            lives = 0;
        }
        this.lives = lives;
        setDataKeyValue("lives", this.lives);
    }

    public void setBounty(int bounty) {
        if (bounty < 0) {
            bounty = 0;
        }
        this.bounty = bounty;
        setDataKeyValue("bounty", this.bounty);
    }

    public void setKills(int kills) {
        this.kills = kills;
        setDataKeyValue("kills", this.kills);
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
        setDataKeyValue("deaths", this.deaths);
    }

    public void setPrestigeInProgress(int prestigeInProgress) {
        this.prestigeInProgress = prestigeInProgress;
        setDataKeyValue("prip", this.prestigeInProgress);
    }
}
