package org.dayaway.crazytoaster.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import org.dayaway.crazytoaster.CrazyToaster;
import org.dayaway.crazytoaster.levelApi.LevelCollection;
import org.dayaway.crazytoaster.sprites.Floor;
import org.dayaway.crazytoaster.sprites.Toaster;
import org.dayaway.crazytoaster.sprites.level.LevelManager;

public class StartPageState extends State {

    private final GameStateManager gsm;

    private final Toaster toaster;

    private final Vector3 titlePos;

    private final Rectangle sound_button;
    private final Rectangle levels_button;
    private final Rectangle mosPos = new Rectangle(0,0,1,1);

    public StartPageState(GameStateManager gsm) {
        super(gsm);
        this.gsm = gsm;
        titlePos = new Vector3();
        this.camera = gsm.getCamera();

        this.toaster = new Toaster(new Floor(CrazyToaster.textures.floor_five, 0,-CrazyToaster.HEIGHT/1.5f),
                150, true, CrazyToaster.textures.standardToasterPack);

        sound_button = new Rectangle(0, 0,
                CrazyToaster.textures.sound_on.getRegionWidth(),CrazyToaster.textures.sound_on.getRegionHeight());

        levels_button = new Rectangle(0,0,
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
                //gsm.push(new PlayState(gsm));
            }
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
        camera.position.y = toaster.getFloor().getPosition().y + CrazyToaster.HEIGHT/8f;

        titlePos.y = toaster.getFloor().getPosition().y + LevelManager.LEVEL_GAP;

        sound_button.setPosition(0, camera.unproject(new Vector3(0, 0, 0)).y - CrazyToaster.textures.sound_on.getRegionHeight());
        levels_button.setPosition(camera.position.x - CrazyToaster.textures.startButton.getRegionWidth()/2f,
                toaster.getFloor().getPosition().y - CrazyToaster.textures.startButton.getRegionHeight() * 2f);

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
        batch.draw(CrazyToaster.textures.startButton, camera.position.x - CrazyToaster.textures.startButton.getRegionWidth()/2f,
                toaster.getFloor().getPosition().y - CrazyToaster.textures.startButton.getRegionHeight() * 2f);
        batch.draw(CrazyToaster.textures.startButton, camera.position.x - CrazyToaster.textures.startButton.getRegionWidth()/2f,
                toaster.getFloor().getPosition().y - CrazyToaster.textures.startButton.getRegionHeight() * 3.5f);


        toaster.render(batch);
        toaster.getFloor().render(batch);

        batch.end();
    }

    @Override
    public void dispose() {

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width,height);
        //Устанавливает камеру по центру экрана
        camera.position.set(camera.viewportWidth/2, camera.viewportHeight/2,0);


        //Соотношение сторон для нахождения левой и правой границы
        float ratioForWidth = (float) width / height;
        float viewportWidth = CrazyToaster.HEIGHT * ratioForWidth;

        float ratioForHeight = (float) height / width;
        float viewportHeight = CrazyToaster.WIDTH * ratioForHeight;

        //Тут мы получаем границы камеры слева и справа
        CrazyToaster.LEFT = (CrazyToaster.WIDTH - viewportWidth) / 2f;
        CrazyToaster.RIGHT = CrazyToaster.LEFT + viewportWidth;
        CrazyToaster.WIDTH_SCREEN = CrazyToaster.RIGHT - CrazyToaster.LEFT;

        //Тут мы получаем границы камеры сверху и снизу
        CrazyToaster.BOTTOM = (CrazyToaster.HEIGHT - viewportHeight) / 2f;
        CrazyToaster.TOP = CrazyToaster.BOTTOM + viewportHeight;
        CrazyToaster.HEIGHT_SCREEN = CrazyToaster.TOP - CrazyToaster.BOTTOM;
    }
}
