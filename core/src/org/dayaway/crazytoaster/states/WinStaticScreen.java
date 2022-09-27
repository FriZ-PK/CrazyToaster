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
import org.dayaway.crazytoaster.levelApi.LevelCollection;
import org.dayaway.crazytoaster.sprites.animation.Animation;

public class WinStaticScreen {

    private final PlayState playState;
    private final Camera camera;
    private final GameStateManager gsm;

    private final BitmapFont bitmapFontScore;
    private final BitmapFont bitmapFontBestScore;
    private final BitmapFont text;

    private final ShapeRenderer shapeRenderer;

    private final GlyphLayout glyphLayout =  new GlyphLayout();

    private final Rectangle rectNextButton = new Rectangle(0,0, CrazyToaster.textures.endlessButton.getRegionWidth(),CrazyToaster.textures.endlessButton.getRegionHeight());
    private final Rectangle rectBackButton = new Rectangle(0,0,CrazyToaster.textures.backButton.getRegionWidth(),CrazyToaster.textures.backButton.getRegionHeight());
    private final Rectangle sound_button;
    private final Rectangle mosPos = new Rectangle(0,0,1,1);

    private Animation toasterAnim = new Animation(new TextureRegion(CrazyToaster.textures.winner_toaster),
            8, 0.5f);

    public WinStaticScreen(PlayState playState) {
        this.playState = playState;
        this.camera = playState.getCamera();
        this.gsm = playState.getGSM();

        bitmapFontScore = new BitmapFont(Gdx.files.internal("wet.fnt"));
        bitmapFontScore.getData().setScale(0.8f);
        bitmapFontBestScore =new BitmapFont(Gdx.files.internal("wet.fnt"));
        bitmapFontBestScore.getData().setScale(0.3f);
        text = new BitmapFont(Gdx.files.internal("wet.fnt"));
        text.getData().setScale(0.3f);

        this.shapeRenderer = new ShapeRenderer();
        sound_button = new Rectangle(0, 0,
                CrazyToaster.textures.sound_on.getRegionWidth(),CrazyToaster.textures.sound_on.getRegionHeight());
    }

    public void handleInput() {
        if (Gdx.input.justTouched()) {
            Vector3 tmpMosPos = camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
            mosPos.setPosition(tmpMosPos.x, tmpMosPos.y);

            if(rectBackButton.overlaps(mosPos)) {
                if(gsm.isSoundOn()) {
                    CrazyToaster.textures.button_sound.play();
                }
                gsm.turnFirstScreen();
                gsm.resetCheckReward();
                playState.getGSM().push(new LevelCollectionState(playState.getGSM()));
            }
            if(rectNextButton.overlaps(mosPos)) {
                if(gsm.isSoundOn()) {
                    CrazyToaster.textures.button_sound.play();
                }
                gsm.turnFirstScreen();
                gsm.resetCheckReward();
                //Следующий уровень
                gsm.push(new PlayState(gsm, LevelCollection.getInstance().get().get(
                        playState.getLevelStatic().getId() + 1)));
            }
            if(sound_button.overlaps(mosPos)) {
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

        glyphLayout.setText(bitmapFontScore, String.valueOf(playState.getSCORE()));

        //Рисует WINNER
        bitmapFontScore.draw(batch, "WINNER", 0,camera.unproject(new Vector3(0, 0, 0)).y - 32,
                CrazyToaster.WIDTH, Align.center,true);

        sound_button.setPosition(0, camera.unproject(new Vector3(0, 0, 0)).y - CrazyToaster.textures.sound_on.getRegionHeight());
        batch.draw(gsm.isSoundOn()?CrazyToaster.textures.sound_on:CrazyToaster.textures.sound_off,
                sound_button.x,sound_button.y);


        rectNextButton.setPosition(camera.position.x - CrazyToaster.textures.restartButton.getRegionWidth()/2f,
                camera.position.y - 80);
        rectBackButton.setPosition(camera.position.x - CrazyToaster.textures.rewardAdButton.getRegionWidth()/2f,
                camera.position.y - 180);

        batch.draw(CrazyToaster.textures.endlessButton, rectNextButton.x, rectNextButton.y);
        glyphLayout.setText(text, "NEXT");
        text.draw(batch, "NEXT",
                rectNextButton.x + CrazyToaster.textures.endlessButton.getRegionWidth()/2f - glyphLayout.width/2f,
                rectNextButton.y + glyphLayout.height * 3f);

        batch.draw(CrazyToaster.textures.backButton, rectBackButton.x, rectBackButton.y);

        batch.end();

        toasterAnim.update(Gdx.graphics.getDeltaTime());
    }

    public void dispose() {
        shapeRenderer.dispose();
        bitmapFontScore.dispose();
        bitmapFontBestScore.dispose();
    }
}
