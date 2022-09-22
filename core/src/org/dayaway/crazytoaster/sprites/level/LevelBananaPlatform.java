package org.dayaway.crazytoaster.sprites.level;

import org.dayaway.crazytoaster.CrazyToaster;
import org.dayaway.crazytoaster.sprites.Floor;
import org.dayaway.crazytoaster.sprites.Toaster;

public class LevelBananaPlatform extends Level {

    public LevelBananaPlatform(int levelCount) {

        //Инициализируем уровень тут
        super (new Toaster(new Floor(CrazyToaster.textures.floor_banana, 0, levelCount * LevelManager.LEVEL_GAP),
                250, levelCount == 0, CrazyToaster.textures.bananaToasterPack));
    }
}
