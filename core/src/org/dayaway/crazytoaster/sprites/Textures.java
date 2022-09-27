package org.dayaway.crazytoaster.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import org.dayaway.crazytoaster.sprites.animation.AnimationPack;
import org.dayaway.crazytoaster.sprites.animation.BananaAnimationPack;
import org.dayaway.crazytoaster.sprites.animation.CucumberAnimationPack;
import org.dayaway.crazytoaster.sprites.animation.StandardAnimationPack;

public class Textures {

    private final TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("macken.pack.atlas"));

    public final TextureRegion toast = atlas.findRegion("toast");
    public final TextureRegion toast_rotate = atlas.findRegion("toast_rotate");
    public final TextureRegion floor_five = atlas.findRegion("five_floor");
    public final TextureRegion floor_three = atlas.findRegion("three_floor");
    public final TextureRegion floor_one = atlas.findRegion("one_floor");
    public final TextureRegion floor_banana = atlas.findRegion("banana_floor");
    public final TextureRegion floor_cucumber = atlas.findRegion("cucumber_floor");
    public final TextureRegion exploded_toaster = atlas.findRegion("exploded_toaster_animation");
    public final TextureRegion crashed_toaster = atlas.findRegion("crashed_toaster_animation");
    public final TextureRegion winner_toaster = atlas.findRegion("winner_toaster_animation");
    public final TextureRegion startButton = atlas.findRegion("button_start");
    public final TextureRegion restartButton = atlas.findRegion("button_restart");
    public final TextureRegion rewardAdButton = atlas.findRegion("button_ad");
    public final TextureRegion toast_fall = atlas.findRegion("toast_fall");
    public final TextureRegion title = atlas.findRegion("title");
    public final TextureRegion sound_on = atlas.findRegion("sound_on");
    public final TextureRegion sound_off = atlas.findRegion("sound_off");
    public final TextureRegion backButton = atlas.findRegion("button_back");
    public final TextureRegion endlessButton = atlas.findRegion("endless_game_button");
    public final TextureRegion levelCollectButton = atlas.findRegion("level_collection_button");
    public final TextureRegion levelEnding = atlas.findRegion("level_ending");
    public final TextureRegion levelClose = atlas.findRegion("level_close");
    public final TextureRegion levelImg = atlas.findRegion("level_img");
    public final TextureRegion levelActive = atlas.findRegion("level_open");

    public final AnimationPack standardToasterPack = new StandardAnimationPack(atlas);
    public final AnimationPack bananaToasterPack = new BananaAnimationPack(atlas);
    public final AnimationPack cucumberToasterPack = new CucumberAnimationPack(atlas);

    public final Texture background = new Texture("font.png");
    public final Texture timer = new Texture("timer.png");

    public final Sound button_sound = Gdx.audio.newSound(Gdx.files.internal("button_sound.wav"));
    public final Sound throw_sound = Gdx.audio.newSound(Gdx.files.internal("throw_sound.wav"));
    public final Sound grab_sound = Gdx.audio.newSound(Gdx.files.internal("grab_sound.wav"));
    public final Sound smash_sound = Gdx.audio.newSound(Gdx.files.internal("smash_sound.wav"));


    public void dispose() {
        atlas.dispose();
        background.dispose();
        timer.dispose();

        button_sound.dispose();
        throw_sound.dispose();
        grab_sound.dispose();
        smash_sound.dispose();
    }
}
