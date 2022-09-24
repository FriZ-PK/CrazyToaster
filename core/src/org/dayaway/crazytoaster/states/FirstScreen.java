package org.dayaway.crazytoaster.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Align;

import org.dayaway.crazytoaster.sprites.animation.Animation;
import org.dayaway.crazytoaster.CrazyToaster;
import org.dayaway.crazytoaster.sprites.level.LevelManager;

public class FirstScreen {

    private final Camera camera;
    private final PlayState playState;
    private final LevelManager levelManager;
    private final GameStateManager gsm;

    private final BitmapFont levelInd;

    private final Vector3 titlePos;
    private final Vector3 floorPos;
    private final Vector3 toasterPos;

    private final Vector3 velocityFloor;
    private final Vector3 velocityTitle;
    private final Vector3 velocityCamera;

    private final Rectangle sound_button;
    private final Rectangle mosPos = new Rectangle(0,0,1,1);

    private final Animation toasterAnim;

    private boolean activated = false;
    private boolean stage2 = false;

    private final int SPEED = 800;


    public FirstScreen(PlayState playState) {
        this.levelManager = playState.getLevelManager();
        this.playState = playState;
        this.gsm = playState.getGSM();

        levelInd = new BitmapFont(Gdx.files.internal("wet.fnt"));
        levelInd.getData().setScale(0.8f);


        titlePos = new Vector3();
        floorPos = new Vector3();
        toasterPos = new Vector3();
        velocityFloor = new Vector3(0,0,0);
        velocityTitle = new Vector3(0,0,0);
        velocityCamera = new Vector3(0,0,0);
        this.camera = playState.getCamera();

        floorPos.y = -CrazyToaster.HEIGHT/1.5f;
        toasterAnim = new Animation(new TextureRegion(CrazyToaster.textures.standardToasterPack.getFullToasterLeft()), 8, 0.5f);
        sound_button = new Rectangle(0, 0,
                CrazyToaster.textures.sound_on.getRegionWidth(),CrazyToaster.textures.sound_on.getRegionHeight());
    }

    public void handleInput() {
        Vector3 tmpMosPos = camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
        mosPos.setPosition(tmpMosPos.x, tmpMosPos.y);

        if(sound_button.overlaps(mosPos)) {
            if(!gsm.isSoundOn()) {
                CrazyToaster.textures.button_sound.play();
            }
            gsm.turnSound();
        }
        else {
            if (gsm.isSoundOn()) {
                CrazyToaster.textures.button_sound.play();
            }
            active();
        }
    }

    public void render(SpriteBatch batch) {
        if(!activated) {
            camera.position.y = floorPos.y + CrazyToaster.HEIGHT/8f;
            titlePos.y = floorPos.y + LevelManager.LEVEL_GAP;
            floorPos.x = camera.position.x - CrazyToaster.textures.floor_five.getRegionWidth()/2f;

            sound_button.setPosition(0, camera.unproject(new Vector3(0, 0, 0)).y - CrazyToaster.textures.sound_on.getRegionHeight());
            batch.draw(gsm.isSoundOn()?CrazyToaster.textures.sound_on:CrazyToaster.textures.sound_off,
                    sound_button.x,sound_button.y);
        }
        if(!stage2) {
            batch.draw(CrazyToaster.textures.floor_five, floorPos.x, floorPos.y);
            toasterPos.y = floorPos.y + CrazyToaster.textures.floor_five.getRegionHeight() - 20;
            batch.draw(levelManager.getFocusToaster().getToaster(), levelManager.getFocusToaster().getPosition().x, toasterPos.y);

            if(playState.getLevelStatic() != null) {
                levelInd.draw(batch, "LEVEL " + (playState.getLevelStatic().getId() + 1),
                        0, titlePos.y + 64,
                        CrazyToaster.WIDTH, Align.center,true);
            }
            else {
                batch.draw(CrazyToaster.textures.title, camera.position.x - CrazyToaster.textures.title.getRegionWidth()/2f,
                        titlePos.y);
            }

            batch.draw(CrazyToaster.textures.startButton, camera.position.x - CrazyToaster.textures.startButton.getRegionWidth()/2f,
                    floorPos.y - CrazyToaster.textures.startButton.getRegionHeight() * 2f);
        }
        else {
            batch.draw(CrazyToaster.textures.floor_five, floorPos.x, floorPos.y);
            toasterPos.y = floorPos.y + CrazyToaster.textures.floor_five.getRegionHeight() - 20;
            batch.draw(levelManager.getFocusToaster().getToaster(), levelManager.getFocusToaster().getPosition().x, toasterPos.y);
        }



        if(activated) {

            if (!stage2) {
                if (floorPos.y > -400 - LevelManager.LEVEL_GAP - 82) {
                    velocityFloor.add(0, -SPEED, 0);
                    velocityFloor.scl(Gdx.graphics.getDeltaTime());
                    floorPos.add(0, velocityFloor.y, 0);

                    velocityTitle.add(0, SPEED, 0);
                    velocityTitle.scl(Gdx.graphics.getDeltaTime());
                    titlePos.add(0, velocityTitle.y, 0);
                } else {
                    stage2 = true;
                    camera.position.y = toasterPos.y + LevelManager.LEVEL_GAP + 24;
                }
            }
            else {
                if(floorPos.y < levelManager.getThisLevel().getFloor().getPosition().y) {
                    velocityFloor.add(0, 1500, 0);
                    velocityFloor.scl(Gdx.graphics.getDeltaTime());
                    floorPos.add(0, velocityFloor.y, 0);
                }
                else {
                    floorPos.y = levelManager.getThisLevel().getFloor().getPosition().y;
                }

                if(camera.position.y < levelManager.getFocusToaster().getPosition().y + LevelManager.LEVEL_GAP + 24) {
                    velocityCamera.add(0, 1500, 0);
                    velocityCamera.scl(Gdx.graphics.getDeltaTime());
                    camera.position.y += velocityCamera.y;
                } else {
                    levelManager.shake();
                    gsm.turnFirstScreen();
                }
            }

        }

        toasterAnim.update(Gdx.graphics.getDeltaTime());
    }

    public void dispose() {
        levelInd.dispose();
    }

    public void active() {
        this.activated = true;
    }
}
