package me.hyscript7.fvspecs;

public class ExperienceFlyweight {
    public static int calculateLevelExP(int level) {
        // Magic formula for how much ExP is required for the specified level
        return (level - 1) * 150 * (level - 2) / 2 + 1;
    }
}
