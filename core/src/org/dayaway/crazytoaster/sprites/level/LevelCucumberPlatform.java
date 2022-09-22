package org.dayaway.crazytoaster.sprites.level;

import org.dayaway.crazytoaster.CrazyToaster;
import org.dayaway.crazytoaster.sprites.Floor;
import org.dayaway.crazytoaster.sprites.Toaster;
import org.dayaway.crazytoaster.states.PlayState;

public class LevelCucumberPlatform extends Level{
    private final PlayState playState;
    public LevelCucumberPlatform(int levelCount, PlayState playState) {

        //Инициализируем уровень тут
        super (new Toaster(new Floor(CrazyToaster.textures.floor_cucumber, 0, levelCount * LevelManager.LEVEL_GAP),
                150, levelCount == 0, CrazyToaster.textures.cucumberToasterPack));

        this.playState = playState;
    }

    @Override
    protected void dispose() {
        super.dispose();
        playState.getToast().setCOOKING_TIME(10000);
    }
}
