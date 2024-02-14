package me.hyscript7.fvspecs.datastore;

import lombok.Getter;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;

public class PlayerStore {
    // Internals
    private final Plugin plugin;
    private final PersistentDataContainer dataContainer;
    private static final int MAX_LIVES = 10;
    private final Map<String, Integer> defaultData = getDefaultPlayerData();

    public static Map<String, Integer> getDefaultPlayerData() {
        Map<String, Integer> defaultData = new HashMap<>();
        defaultData.put("level", 1);
        defaultData.put("prestige", 0);
        defaultData.put("exp", 0);
        defaultData.put("lives", MAX_LIVES);
        defaultData.put("bounty", 0);
        defaultData.put("kills", 0);
        defaultData.put("deaths", 0);
        defaultData.put("prip", 0);
        defaultData.put("race", -1);
        return defaultData;
    }

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
    private int race;
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
        race = getDataKeyValue("race");
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

    public void setRace(int race) {
        this.race = race;
        setDataKeyValue("race", this.race);
    }

    public void setPrestigeInProgress(int prestigeInProgress) {
        this.prestigeInProgress = prestigeInProgress;
        setDataKeyValue("prip", this.prestigeInProgress);
    }
}
