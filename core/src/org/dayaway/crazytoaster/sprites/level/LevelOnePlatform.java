package org.dayaway.crazytoaster.sprites.level;

import org.dayaway.crazytoaster.CrazyToaster;
import org.dayaway.crazytoaster.sprites.Floor;
import org.dayaway.crazytoaster.sprites.Toaster;

public class LevelOnePlatform extends Level {

    public LevelOnePlatform(int levelCount) {

        //Инициализируем уровень тут
        super (new Toaster(new Floor(CrazyToaster.textures.floor_one, 0, levelCount * LevelManager.LEVEL_GAP, true),
                0, levelCount == 0, CrazyToaster.textures.standardToasterPack));
    }
}
