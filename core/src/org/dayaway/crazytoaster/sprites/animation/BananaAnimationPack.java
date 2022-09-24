package org.dayaway.crazytoaster.sprites.animation;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class BananaAnimationPack implements AnimationPack {

    private final TextureRegion emptyToasterTextureRight;
    private final TextureRegion emptyToasterTextureLeft;
    private final TextureRegion fullToasterTextureRight;
    private final TextureRegion fullToasterTextureLeft;
    private final TextureRegion eatingToasterRight;
    private final TextureRegion eatingToasterLeft;

    public BananaAnimationPack(TextureAtlas atlas) {
        emptyToasterTextureRight = atlas.findRegion("empty_banana_toaster_animation_right");
        emptyToasterTextureLeft = atlas.findRegion("empty_banana_toaster_animation_left");
        fullToasterTextureRight = atlas.findRegion("full_banana_toaster_animation_right");
        fullToasterTextureLeft = atlas.findRegion("full_banana_toaster_animation_left");
        eatingToasterRight = atlas.findRegion("eating_banana_toaster_right");
        eatingToasterLeft = atlas.findRegion("eating_banana_toaster_left");
    }

    @Override
    public TextureRegion getEmptyToasterRight() {
        return emptyToasterTextureRight;
    }

    @Override
    public TextureRegion getEmptyToasterLeft() {
        return emptyToasterTextureLeft;
    }

    @Override
    public TextureRegion getFullToasterRight() {
        return fullToasterTextureRight;
    }

    @Override
    public TextureRegion getFullToasterLeft() {
        return fullToasterTextureLeft;
    }

    @Override
    public TextureRegion getEatingToasterRight() {
        return eatingToasterRight;
    }

    @Override
    public TextureRegion getEatingToasterLeft() {
        return eatingToasterLeft;
    }
}
