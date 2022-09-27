package org.dayaway.crazytoaster.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Align;

import org.dayaway.crazytoaster.CrazyToaster;
import org.dayaway.crazytoaster.sprites.animation.Animation;
import org.dayaway.crazytoaster.sprites.level.LevelManager;

public class EndScreen {

    private final PlayState playState;
    private final Camera camera;
    private final GameStateManager gsm;

    private final BitmapFont bitmapFontScore;
    private final BitmapFont bitmapFontBestScore;
    private final BitmapFont bitmapFontContinue;

    private final ShapeRenderer shapeRenderer;

    private final GlyphLayout glyphLayout =  new GlyphLayout();

    private final Rectangle rectRestart = new Rectangle(0,0, CrazyToaster.textures.restartButton.getRegionWidth(),CrazyToaster.textures.restartButton.getRegionHeight());
    private final Rectangle rectRewardAd = new Rectangle(0,0,CrazyToaster.textures.rewardAdButton.getRegionWidth(),CrazyToaster.textures.rewardAdButton.getRegionHeight());
    private final Rectangle rectBackBut = new Rectangle(0,0,CrazyToaster.textures.backButton.getRegionWidth(),CrazyToaster.textures.backButton.getRegionHeight());
    private final Rectangle sound_button;
    private final Rectangle mosPos = new Rectangle(0,0,1,1);

    private Animation toasterAnim = new Animation(new TextureRegion(CrazyToaster.textures.exploded_toaster),
            4, 0.5f);


    public EndScreen(PlayState playState) {
        this.playState = playState;
        this.camera = playState.getCamera();
        this.gsm = playState.getGSM();

        bitmapFontScore = new BitmapFont(Gdx.files.internal("wet.fnt"));
        bitmapFontScore.getData().setScale(0.8f);
        bitmapFontBestScore =new BitmapFont(Gdx.files.internal("wet.fnt"));
        bitmapFontBestScore.getData().setScale(0.3f);
        bitmapFontContinue =new BitmapFont(Gdx.files.internal("wet.fnt"));
        bitmapFontContinue.getData().setScale(0.3f);

        this.shapeRenderer = new ShapeRenderer();
        sound_button = new Rectangle(0, 0,
                CrazyToaster.textures.sound_on.getRegionWidth(),CrazyToaster.textures.sound_on.getRegionHeight());
    }

    public void handleInput() {
        if (Gdx.input.justTouched()) {
            Vector3 tmpMosPos = camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
            mosPos.setPosition(tmpMosPos.x, tmpMosPos.y);

            if(rectRestart.overlaps(mosPos)) {
                if(gsm.isSoundOn()) {
                    CrazyToaster.textures.button_sound.play();
                }
                playState.RETURN_GAME();
            }
            else if(rectRewardAd.overlaps(mosPos) && !gsm.isCheckedReward()) {
                if(gsm.isSoundOn()) {
                    CrazyToaster.textures.button_sound.play();
                }
                    gsm.showRewardAd();
            }
            else if(rectBackBut.overlaps(mosPos)) {
                if(gsm.isSoundOn()) {
                    CrazyToaster.textures.button_sound.play();
                }

                if(playState.getLevelStatic() == null) {
                    gsm.resetCheckReward();
                    gsm.turnFirstScreen();
                    gsm.push(new StartState(gsm));
                }
                else {
                    gsm.resetCheckReward();
                    gsm.turnFirstScreen();
                    gsm.push(new LevelCollectionState(gsm));
                }


            }
            else if(sound_button.overlaps(mosPos)) {
                if(!gsm.isSoundOn()) {
                    CrazyToaster.textures.button_sound.play();
                }
                gsm.turnSound();
            }

        }
    }

    public void render(SpriteBatch batch) {
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(new Color(0, 0, 0, 0.75f));
        shapeRenderer.box(camera.position.x - camera.viewportWidth/2f, camera.position.y - camera.viewportHeight/2f, 0,
                camera.viewportWidth, camera.viewportHeight, 0);
        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);

        batch.begin();

        batch.draw(CrazyToaster.textures.floor_three, camera.position.x - CrazyToaster.textures.floor_three.getRegionWidth()/2f, camera.position.y + 96);

        batch.draw(toasterAnim.getFrame(), camera.position.x - toasterAnim.getFrame().getRegionWidth()/2f,
                camera.position.y + 96 + CrazyToaster.textures.floor_three.getRegionHeight() - 20);

        //Рисует очки в бесконечной игре
        if(playState.getLevelStatic() == null) {
            glyphLayout.setText(bitmapFontScore, String.valueOf(playState.getSCORE()));
            bitmapFontScore.draw(batch, String.valueOf(playState.getSCORE()), camera.position.x - glyphLayout.width / 2f,
                    camera.unproject(new Vector3(0, 0, 0)).y - 32);
        }
        //Рисует информацию в режиме прохождении уровней
        else {
            glyphLayout.setText(bitmapFontScore, "OOPS!");
            bitmapFontScore.draw(batch, "OOPS!", camera.position.x - glyphLayout.width / 2f,
                    camera.unproject(new Vector3(0, 0, 0)).y - 32);
            bitmapFontBestScore.getData().setScale(0.3f);
            bitmapFontBestScore.draw(batch, "COMPLETE: " + playState.getSCORE() + "/" + (playState.getLevelStatic().get().size()-1)
                    , 0, camera.unproject(new Vector3(0,0,0)).y - LevelManager.LEVEL_GAP/3f - 32,
                    CrazyToaster.WIDTH, Align.center,true);
        }

        sound_button.setPosition(0, camera.unproject(new Vector3(0, 0, 0)).y - CrazyToaster.textures.sound_on.getRegionHeight());
        batch.draw(gsm.isSoundOn()?CrazyToaster.textures.sound_on:CrazyToaster.textures.sound_off,
                sound_button.x,sound_button.y);

        //Если еще не смотрели рекламу за вторую попытку рисует три кнопки
        if(!gsm.isCheckedReward()) {

            rectRestart.setPosition(camera.position.x - CrazyToaster.textures.restartButton.getRegionWidth()/2f,
                    camera.position.y - 80);
            rectRewardAd.setPosition(camera.position.x - CrazyToaster.textures.rewardAdButton.getRegionWidth()/2f,
                    camera.position.y - 200);
            rectBackBut.setPosition(camera.position.x - CrazyToaster.textures.backButton.getRegionWidth()/2f,
                    camera.position.y - 310);

            batch.draw(CrazyToaster.textures.restartButton, rectRestart.x, rectRestart.y);
            bitmapFontContinue.draw(batch, "CONTINUE",
                    0, rectRewardAd.y + 110,
                    CrazyToaster.WIDTH, Align.center,true);
            batch.draw(CrazyToaster.textures.rewardAdButton, rectRewardAd.x, rectRewardAd.y);
            batch.draw(CrazyToaster.textures.backButton, rectBackBut.x, rectBackBut.y);
        }
        //Если уже посмотрели рекламу за вторую попытку рисует только рестарт и назад
        else {
            rectRestart.setPosition(camera.position.x - CrazyToaster.textures.restartButton.getRegionWidth()/2f,
                    camera.position.y - 100);
            rectBackBut.setPosition(camera.position.x - CrazyToaster.textures.backButton.getRegionWidth()/2f,
                    camera.position.y - 200);

            batch.draw(CrazyToaster.textures.restartButton, rectRestart.x,
                    rectRestart.y);
            batch.draw(CrazyToaster.textures.backButton, rectBackBut.x,
                    rectBackBut.y);
        }

        //Рисует рекорд если бесконечная игра
        if(playState.getLevelStatic() == null) {
            if (!playState.isNEW_RECORD()) {
                bitmapFontBestScore.getData().setScale(0.3f);
                bitmapFontBestScore.draw(batch, "BEST: " + playState.getBEST_SCORE(), 0, camera.unproject(new Vector3(0, 0, 0)).y - LevelManager.LEVEL_GAP / 3f - 32, CrazyToaster.WIDTH, Align.center, true);
            } else {
                bitmapFontBestScore.getData().setScale(0.6f);
                bitmapFontBestScore.draw(batch, "NEW BEST!", 0, camera.unproject(new Vector3(0, 0, 0)).y - LevelManager.LEVEL_GAP / 3f - 32, CrazyToaster.WIDTH, Align.center, true);
            }
        }
        batch.end();

        toasterAnim.update(Gdx.graphics.getDeltaTime());
    }

    public void dispose() {
        bitmapFontScore.dispose();
        bitmapFontBestScore.dispose();
        bitmapFontContinue.dispose();
        shapeRenderer.dispose();
    }

    public void setEndToasterTexture() {
        if(playState.isNEW_RECORD()) {
            toasterAnim = new Animation(new TextureRegion(CrazyToaster.textures.winner_toaster),
                    8, 0.5f);
        } else {
            if(playState.isCRASHED()) {
                toasterAnim = new Animation(new TextureRegion(CrazyToaster.textures.crashed_toaster),
                        4, 0.5f);
            }
            else {
                toasterAnim = new Animation(new TextureRegion(CrazyToaster.textures.exploded_toaster),
                        4, 0.5f);
            }
        }
    }
}
