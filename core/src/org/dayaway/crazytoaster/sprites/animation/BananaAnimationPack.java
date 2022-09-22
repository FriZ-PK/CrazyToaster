package org.dayaway.crazytoaster.sprites.animation;

import com.badlogic.gdx.graphics.Texture;

public class BananaAnimationPack implements AnimationPack {

    private final Texture emptyToasterTextureRight = new Texture("empty_banana_toaster_animation_right.png");
    private final Texture emptyToasterTextureLeft = new Texture("empty_banana_toaster_animation_left.png");
    private final Texture fullToasterTextureRight = new Texture("full_banana_toaster_animation_right.png");
    private final Texture fullToasterTextureLeft = new Texture("full_banana_toaster_animation_left.png");
    private final Texture eatingToasterRight = new Texture("eating_banana_toaster_right.png");
    private final Texture eatingToasterLeft = new Texture("eating_banana_toaster_left.png");

    @Override
    public Texture getEmptyToasterRight() {
        return emptyToasterTextureRight;
    }

    @Override
    public Texture getEmptyToasterLeft() {
        return emptyToasterTextureLeft;
    }

    @Override
    public Texture getFullToasterRight() {
        return fullToasterTextureRight;
    }

    @Override
    public Texture getFullToasterLeft() {
        return fullToasterTextureLeft;
    }

    @Override
    public Texture getEatingToasterRight() {
        return eatingToasterRight;
    }

    @Override
    public Texture getEatingToasterLeft() {
        return eatingToasterLeft;
    }

    @Override
    public void dispose() {
        emptyToasterTextureRight.dispose();
        emptyToasterTextureLeft.dispose();
        fullToasterTextureRight.dispose();
        fullToasterTextureLeft.dispose();
        eatingToasterRight.dispose();
        eatingToasterLeft.dispose();
    }
}
