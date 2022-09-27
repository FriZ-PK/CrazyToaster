package org.dayaway.crazytoaster.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Align;

import org.dayaway.crazytoaster.CrazyToaster;
import org.dayaway.crazytoaster.sprites.level.LevelManager;

public class FirstScreen {

    private final Camera camera;
    private final PlayState playState;
    private final LevelManager levelManager;
    private final GameStateManager gsm;

    private final Vector3 titlePos;
    private final Vector3 floorPos;
    private final Vector3 toasterPos;

    private final Vector3 velocityFloor;
    private final Vector3 velocityTitle;
    private final Vector3 velocityCamera;

    private final Rectangle sound_button;
    private final Rectangle start_button;
    private final Rectangle back_button;

    private final BitmapFont levelInd;
    private final BitmapFont text;
    private final GlyphLayout glyphLayout;

    private final Rectangle mosPos = new Rectangle(0,0,1,1);

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
        sound_button = new Rectangle(0, 0,
                CrazyToaster.textures.sound_on.getRegionWidth(),CrazyToaster.textures.sound_on.getRegionHeight());

        start_button = new Rectangle(camera.position.x - CrazyToaster.textures.startButton.getRegionWidth() / 2f,
                floorPos.y - CrazyToaster.textures.startButton.getRegionHeight() * 2f,
                CrazyToaster.textures.startButton.getRegionWidth(),
                CrazyToaster.textures.startButton.getRegionHeight());

        back_button = new Rectangle(camera.position.x - CrazyToaster.textures.startButton.getRegionWidth() / 2f,
                floorPos.y - CrazyToaster.textures.startButton.getRegionHeight() * 3.4f,
                CrazyToaster.textures.startButton.getRegionWidth(),
                CrazyToaster.textures.startButton.getRegionHeight());

        text = new BitmapFont(Gdx.files.internal("wet.fnt"));
        text.getData().setScale(0.3f);
        glyphLayout = new GlyphLayout();
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
        else if(mosPos.overlaps(start_button)) {
            if (gsm.isSoundOn()) {
                CrazyToaster.textures.button_sound.play();
            }
            active();
        }

        if(mosPos.overlaps(back_button)) {
            if (gsm.isSoundOn()) {
                CrazyToaster.textures.button_sound.play();
            }
            gsm.push(new LevelCollectionState(gsm));
        }


    }

    public void render(SpriteBatch batch) {
        if(!activated) {
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

            //Если проходят уровень нужно рисовать кнопку назад и старт
            if(playState.getLevelStatic() != null) {
                start_button.setPosition(camera.position.x - CrazyToaster.textures.startButton.getRegionWidth()/2f,
                        floorPos.y - CrazyToaster.textures.startButton.getRegionHeight() * 2f);
                batch.draw(CrazyToaster.textures.startButton, start_button.x, start_button.y);

                back_button.setPosition(camera.position.x - CrazyToaster.textures.backButton.getRegionWidth()/2f,
                        floorPos.y - CrazyToaster.textures.backButton.getRegionHeight() * 3.4f);
                batch.draw(CrazyToaster.textures.backButton, back_button.x, back_button.y);
            }
            //Если в бесконечной игре
            else {
                back_button.setPosition(camera.position.x - CrazyToaster.textures.levelCollectButton.getRegionWidth() - 20,
                        floorPos.y - CrazyToaster.textures.levelCollectButton.getRegionHeight() * 2.5f);
                batch.draw(CrazyToaster.textures.levelCollectButton, back_button.x, back_button.y);

                glyphLayout.setText(text, "LEVELS");
                text.draw(batch, "LEVELS",
                        back_button.x + CrazyToaster.textures.levelCollectButton.getRegionWidth()/2f - glyphLayout.width/2f,
                        back_button.y + glyphLayout.height * 3f);

                start_button.setPosition(camera.position.x + 20,
                        floorPos.y - CrazyToaster.textures.endlessButton.getRegionHeight() * 2.5f);
                batch.draw(CrazyToaster.textures.endlessButton, start_button.x, start_button.y);

                glyphLayout.setText(text, "SURVIVE");
                text.draw(batch, "SURVIVE",
                        start_button.x + CrazyToaster.textures.levelCollectButton.getRegionWidth()/2f - glyphLayout.width/2f,
                        start_button.y + glyphLayout.height * 3f);
            }
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
    }

    public void dispose() {
        levelInd.dispose();
        text.dispose();
    }

    public void active() {
        this.activated = true;
    }
}
