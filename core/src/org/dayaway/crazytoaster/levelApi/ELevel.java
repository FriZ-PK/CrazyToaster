package org.dayaway.crazytoaster.levelApi;

//ЭНАМ платформ
public enum ELevel {
    FIVE(1),
    THREE(1),
    ONE(1),
    BANANA(1),
    CUCUMBER(1);

    private final int chance;

    ELevel(int chance) {
        this.chance = chance;
    }

    public int getChance() {
        return chance;
    }
}
