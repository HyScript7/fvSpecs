package me.hyscript7.fvspecs.listeners.players.races;

public interface Race {
    final int ID = 0;
    final String NAME = "Human";
    default int getId() {
        return ID;
    }
    default String getName() {
        return NAME;
    }
}
