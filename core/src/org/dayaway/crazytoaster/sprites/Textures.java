package org.dayaway.crazytoaster.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

import org.dayaway.crazytoaster.sprites.animation.AnimationPack;
import org.dayaway.crazytoaster.sprites.animation.BananaAnimationPack;
import org.dayaway.crazytoaster.sprites.animation.CucumberAnimationPack;
import org.dayaway.crazytoaster.sprites.animation.StandardAnimationPack;

public class Textures {

    public final Texture toast = new Texture("toast.png");
    public final Texture toast_rotate = new Texture("toast_rotate.png");
    public final Texture background = new Texture("font.png");
    public final Texture floor_five = new Texture("five_floor.png");
    public final Texture floor_three = new Texture("three_floor.png");
    public final Texture floor_one = new Texture("one_floor.png");
    public final Texture floor_banana = new Texture("banana_floor.png");
    public final Texture floor_cucumber = new Texture("cucumber_floor.png");
    public final AnimationPack standardToasterPack = new StandardAnimationPack();
    public final AnimationPack bananaToasterPack = new BananaAnimationPack();
    public final AnimationPack cucumberToasterPack = new CucumberAnimationPack();
    public final Texture exploded_toaster = new Texture("exploded_toaster_animation.png");
    public final Texture crashed_toaster = new Texture("crashed_toaster_animation.png");
    public final Texture winner_toaster = new Texture("winner_toaster_animation.png");
    public final Texture startButton = new Texture("button_start.png");
    public final Texture restartButton = new Texture("button_restart.png");
    public final Texture rewardAdButton = new Texture("button_ad.png");
    public final Texture timer = new Texture("timer.png");
    public final Texture toast_fall = new Texture("toast_fall.png");
    public final Texture title = new Texture("title.png");
    public final Texture sound_on = new Texture("sound_on.png");
    public final Texture sound_off = new Texture("sound_off.png");


    public final Sound button_sound = Gdx.audio.newSound(Gdx.files.internal("button_sound.wav"));
    public final Sound throw_sound = Gdx.audio.newSound(Gdx.files.internal("throw_sound.wav"));
    public final Sound grab_sound = Gdx.audio.newSound(Gdx.files.internal("grab_sound.wav"));
    public final Sound smash_sound = Gdx.audio.newSound(Gdx.files.internal("smash_sound.wav"));


    public void dispose() {
        toast.dispose();
        toast_rotate.dispose();
        background.dispose();
        floor_five.dispose();
        floor_three.dispose();
        floor_one.dispose();
        floor_banana.dispose();
        floor_cucumber.dispose();
        standardToasterPack.dispose();
        bananaToasterPack.dispose();
        cucumberToasterPack.dispose();
        startButton.dispose();
        restartButton.dispose();
        rewardAdButton.dispose();
        timer.dispose();
        toast_fall.dispose();
        title.dispose();
        sound_on.dispose();
        sound_off.dispose();

        button_sound.dispose();
        throw_sound.dispose();
        grab_sound.dispose();
        smash_sound.dispose();
    }
}
