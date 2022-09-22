package org.dayaway.crazytoaster.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import org.dayaway.crazytoaster.sprites.animation.Animation;
import org.dayaway.crazytoaster.CrazyToaster;
import org.dayaway.crazytoaster.sprites.level.LevelManager;

public class FirstScreen {

    private final Camera camera;
    private final LevelManager levelManager;
    private final GameStateManager gsm;

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
        this.gsm = playState.getGSM();

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
                CrazyToaster.textures.sound_on.getWidth(),CrazyToaster.textures.sound_on.getHeight());
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
            floorPos.x = camera.position.x - CrazyToaster.textures.floor_five.getWidth()/2f;

            sound_button.setPosition(0, camera.unproject(new Vector3(0, 0, 0)).y - CrazyToaster.textures.sound_on.getHeight());
            batch.draw(gsm.isSoundOn()?CrazyToaster.textures.sound_on:CrazyToaster.textures.sound_off,
                    sound_button.x,sound_button.y);
        }
        if(!stage2) {
            batch.draw(CrazyToaster.textures.floor_five, floorPos.x, floorPos.y);
            toasterPos.y = floorPos.y + CrazyToaster.textures.floor_five.getHeight() - 20;
            batch.draw(levelManager.getFocusToaster().getToaster(), levelManager.getFocusToaster().getPosition().x, toasterPos.y);

            batch.draw(CrazyToaster.textures.title, camera.position.x - CrazyToaster.textures.title.getWidth()/2f,
                    titlePos.y);
            batch.draw(CrazyToaster.textures.startButton, camera.position.x - CrazyToaster.textures.startButton.getWidth()/2f,
                    floorPos.y - CrazyToaster.textures.startButton.getHeight() * 2f);
        }
        else {
            batch.draw(CrazyToaster.textures.floor_five, floorPos.x, floorPos.y);
            toasterPos.y = floorPos.y + CrazyToaster.textures.floor_five.getHeight() - 20;
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

    public void active() {
        this.activated = true;
    }
}
