package me.hyscript7.fvspecs.listeners.players.races;

import me.hyscript7.fvspecs.datastore.PlayerStore;

public interface Race {
    final int ID = 0;
    final String NAME = "Human";
    default int getId() {
        return ID;
    }
    default String getName() {
        return NAME;
    }
    default boolean playerRaceMatches(PlayerStore playerStore) {
        return playerStore.getRace() == getId();
    }
}
