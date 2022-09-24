package org.dayaway.crazytoaster.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import org.dayaway.crazytoaster.CrazyToaster;
import org.dayaway.crazytoaster.sprites.Floor;
import org.dayaway.crazytoaster.sprites.Toaster;
import org.dayaway.crazytoaster.sprites.level.LevelManager;

public class StartPageState extends State {

    private final GameStateManager gsm;

    private final Toaster toaster;

    private final Vector3 titlePos;

    private final Rectangle sound_button;
    private final Rectangle levels_button;
    private final Rectangle endless_game_button;
    private final Rectangle mosPos = new Rectangle(0,0,1,1);

    public StartPageState(GameStateManager gsm) {
        super(gsm);
        this.gsm = gsm;

        this.toaster = new Toaster(new Floor(CrazyToaster.textures.floor_five, 0,-CrazyToaster.HEIGHT/1.5f),
                150, true, CrazyToaster.textures.standardToasterPack);

        camera.position.y = toaster.getFloor().getPosition().y + CrazyToaster.HEIGHT/8f;

        titlePos = new Vector3(0,toaster.getFloor().getPosition().y+ LevelManager.LEVEL_GAP,0);

        sound_button = new Rectangle(0, camera.position.y + camera.viewportHeight/2f - CrazyToaster.textures.sound_on.getRegionHeight(),
                CrazyToaster.textures.sound_on.getRegionWidth(),CrazyToaster.textures.sound_on.getRegionHeight());

        levels_button = new Rectangle(camera.position.x - CrazyToaster.textures.startButton.getRegionWidth() - 20,
                toaster.getFloor().getPosition().y - CrazyToaster.textures.startButton.getRegionHeight() * 2.5f,
                CrazyToaster.textures.startButton.getRegionWidth(),
                CrazyToaster.textures.startButton.getRegionHeight());

        endless_game_button = new Rectangle(camera.position.x + 20,
                toaster.getFloor().getPosition().y - CrazyToaster.textures.startButton.getRegionHeight() * 2.5f,
                CrazyToaster.textures.startButton.getRegionWidth(),
                CrazyToaster.textures.startButton.getRegionHeight());
    }

    public void handleInput() {
        if(Gdx.input.justTouched()) {

            Vector3 tmpMosPos = camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
            mosPos.setPosition(tmpMosPos.x, tmpMosPos.y);

            if (sound_button.overlaps(mosPos)) {
                if (!gsm.isSoundOn()) {
                    CrazyToaster.textures.button_sound.play();
                }
                gsm.turnSound();
            }
            else if(levels_button.overlaps(mosPos)) {
                if (gsm.isSoundOn()) {
                    CrazyToaster.textures.button_sound.play();
                }
                gsm.push(new LevelCollectionState(gsm));
            }
            else if(endless_game_button.overlaps(mosPos)) {
                if (gsm.isSoundOn()) {
                    CrazyToaster.textures.button_sound.play();
                }
                gsm.push(new PlayState(gsm, toaster));
            }
        }
    }

    @Override
    public void update(float dt) {
        handleInput();

        toaster.update(Gdx.graphics.getDeltaTime());
        toaster.getFloor().update(Gdx.graphics.getDeltaTime());
    }

    public void render(SpriteBatch batch) {
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        Gdx.gl.glClearColor(247, 215, 116, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        batch.draw(CrazyToaster.textures.background, 0, camera.position.y - LevelManager.LEVEL_GAP * 2);

        batch.draw(gsm.isSoundOn()?CrazyToaster.textures.sound_on:CrazyToaster.textures.sound_off,
                sound_button.x,sound_button.y);

        batch.draw(CrazyToaster.textures.title, camera.position.x - CrazyToaster.textures.title.getRegionWidth()/2f,
                titlePos.y);
        batch.draw(CrazyToaster.textures.startButton, levels_button.x, levels_button.y);
        batch.draw(CrazyToaster.textures.startButton, endless_game_button.x, endless_game_button.y);

        toaster.render(batch);
        toaster.getFloor().render(batch);

        batch.end();
    }

    @Override
    public void dispose() {

    }

    @Override
    public void resize(int width, int height) {
    }
}
