package org.dayaway.crazytoaster.sprites.animation;

import com.badlogic.gdx.graphics.Texture;

public interface AnimationPack {

    Texture getEmptyToasterRight();
    Texture getEmptyToasterLeft();
    Texture getFullToasterRight();
    Texture getFullToasterLeft();
    Texture getEatingToasterRight();
    Texture getEatingToasterLeft();

    void dispose();
}
