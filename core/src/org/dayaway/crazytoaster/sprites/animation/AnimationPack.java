package org.dayaway.crazytoaster.sprites.animation;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public interface AnimationPack {

    TextureRegion getEmptyToasterRight();
    TextureRegion getEmptyToasterLeft();
    TextureRegion getFullToasterRight();
    TextureRegion getFullToasterLeft();
    TextureRegion getEatingToasterRight();
    TextureRegion getEatingToasterLeft();
}
