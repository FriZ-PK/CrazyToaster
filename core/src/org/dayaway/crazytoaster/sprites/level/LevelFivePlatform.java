package org.dayaway.crazytoaster.sprites.level;

import org.dayaway.crazytoaster.CrazyToaster;
import org.dayaway.crazytoaster.sprites.Floor;
import org.dayaway.crazytoaster.sprites.Toaster;

public class LevelFivePlatform extends Level {

    public LevelFivePlatform(int levelCount) {

        //Инициализируем уровень тут
        super (new Toaster(new Floor(CrazyToaster.textures.floor_five, 0,levelCount * LevelManager.LEVEL_GAP),
                150, levelCount == 0, CrazyToaster.textures.standardToasterPack));
    }

    public LevelFivePlatform(int levelCount, Toaster toaster) {
        //Инициализируем уровень тут
        super (toaster.setFirstPlatformOnEndlessGame());
    }
}
