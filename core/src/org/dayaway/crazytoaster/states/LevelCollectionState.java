package org.dayaway.crazytoaster.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Align;

import org.dayaway.crazytoaster.CrazyToaster;
import org.dayaway.crazytoaster.levelApi.LevelCollection;
import org.dayaway.crazytoaster.levelApi.LevelStatic;
import org.dayaway.crazytoaster.sprites.level.LevelManager;

import java.util.ArrayList;
import java.util.List;

public class LevelCollectionState extends State{

    private static final float BORDER = 28;
    private static final float WIDTH = 64;

    private final GameStateManager gsm;

    private final List<LevelStatic> levelList;

    private final List<Rectangle> rectLev;

    private final Rectangle back_button;

    private final Rectangle sound_button;

    private final Rectangle mosPos = new Rectangle(0,0,1,1);

    private final BitmapFont text;
    private final GlyphLayout glyphLayout;

    public LevelCollectionState(GameStateManager gsm) {
        super(gsm);
        this.gsm = gsm;
        this.levelList = LevelCollection.getInstance().get();

        rectLev = new ArrayList<>();

        //Тут мы создаем лист скелетов
        for (int i = 0; i < levelList.size(); i++) {
            rectLev.add(new Rectangle(0,0,64,64));
        }

        setLevelPositions();

        back_button = new Rectangle(camera.position.x - CrazyToaster.textures.backButton.getRegionWidth()/2f,
                rectLev.get(rectLev.size()-1).y - 150,
                CrazyToaster.textures.backButton.getRegionWidth(),
                CrazyToaster.textures.backButton.getRegionHeight());

        sound_button = new Rectangle(0, camera.position.y + camera.viewportHeight/2f - CrazyToaster.textures.sound_on.getRegionHeight(),
                CrazyToaster.textures.sound_on.getRegionWidth(),CrazyToaster.textures.sound_on.getRegionHeight());

        text = new BitmapFont(Gdx.files.internal("wet.fnt"));
        text.getData().setScale(0.3f);
        glyphLayout = new GlyphLayout();
    }

    @Override
    protected void handleInput() {
        Vector3 tmpMosPos = camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
        mosPos.setPosition(tmpMosPos.x, tmpMosPos.y);

        if(Gdx.input.justTouched()) {
            //Если нажали на кнопку НАЗАД
            if(mosPos.overlaps(back_button)) {
                if (gsm.isSoundOn()) {
                    CrazyToaster.textures.button_sound.play();
                }
                gsm.push(new PlayState(gsm));
            }
            //Если нажали на кнопку ЗВУКА
            else if(sound_button.overlaps(mosPos)) {
                if(!gsm.isSoundOn()) {
                    CrazyToaster.textures.button_sound.play();
                }
                gsm.turnSound();
            }
            //Если на что то другое
            else {
                for (int i = 0; i < rectLev.size(); i++) {

                    if (rectLev.get(i).overlaps(mosPos)) {
                        if (levelList.get(i).isOpen()) {
                            if (gsm.isSoundOn()) {
                                CrazyToaster.textures.button_sound.play();
                            }
                            //Тут мы получаем лист уровней который выбрали
                            //Нужно запушить PlayState и в аргумент передать этот уровень
                            gsm.push(new PlayState(gsm, levelList.get(i))); //Достали уровень под индексом i
                        }
                    }
                }
            }
        }
    }

    @Override
    public void update(float dt) {
        handleInput();

    }

    @Override
    public void render(SpriteBatch batch) {
        camera.update();
        batch.setProjectionMatrix(camera.combined);


        Gdx.gl.glClearColor(247, 215, 116, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.disableBlending();
        batch.draw(CrazyToaster.textures.background, 0, camera.position.y - LevelManager.LEVEL_GAP * 2);
        batch.enableBlending();

        batch.draw(CrazyToaster.textures.backButton, back_button.x, back_button.y);

        batch.draw(gsm.isSoundOn()?CrazyToaster.textures.sound_on:CrazyToaster.textures.sound_off,
                sound_button.x,sound_button.y);

        drawLevelPositions(batch);

        batch.end();
    }

    @Override
    public void dispose() {
        text.dispose();
    }

    @Override
    public void resize(int width, int height) {

    }

    public void setLevelPositions() {
        int layer = 1;

        for (int i = 0; i < rectLev.size(); i++) {

            if(layer == 1) {
                rectLev.get(i).setPosition(BORDER + ((BORDER * 2) * i) + (i * WIDTH), camera.position.y + 150);
                if(i == 3) {
                    layer = 2;
                }
            }
            else if(layer == 2) {
                int p = (i + layer) - rectLev.size()/layer;
                rectLev.get(i).setPosition(BORDER + ((BORDER * 2) * p) + (p * WIDTH), camera.position.y);
                if(i == 7) {
                    layer = 3;
                }
            }
            else if(layer == 3) {
                int p = (i - layer) - rectLev.size()/layer - 1;
                rectLev.get(i).setPosition(BORDER + ((BORDER * 2) * p) + (p * WIDTH), camera.position.y - 150);
                if(i == 7) {
                    layer = 4;
                }
            }
        }
    }

    public void drawLevelPositions(SpriteBatch batch) {

        for (int i = 0; i < rectLev.size(); i++) {

            //Если уровень открыт
            if(levelList.get(i).isOpen()) {

                //Если уровень пройден
                if(levelList.get(i).isEnding()) {
                    batch.draw(CrazyToaster.textures.levelImg, rectLev.get(i).x, rectLev.get(i).y);

                    batch.draw(CrazyToaster.textures.levelEnding, rectLev.get(i).x
                            + (CrazyToaster.textures.levelImg.getRegionWidth() - CrazyToaster.textures.levelEnding.getRegionWidth()),
                            rectLev.get(i).y);
                }
                //Если открыт но не пройден(АКТИВНЫЙ)
                else {
                    batch.draw(CrazyToaster.textures.levelActive, rectLev.get(i).x, rectLev.get(i).y);
                }

                //Рисуем номер уровня
                glyphLayout.setText(text, String.valueOf(levelList.get(i).getId() + 1));
                text.draw(batch, String.valueOf(levelList.get(i).getId() + 1),
                        rectLev.get(i).x + 32 - glyphLayout.width/2f,
                        rectLev.get(i).y + glyphLayout.height * 3f);
            }
            //Если уровень не открыт
            else {
                batch.draw(CrazyToaster.textures.levelImg, rectLev.get(i).x, rectLev.get(i).y);
                batch.draw(CrazyToaster.textures.levelClose, rectLev.get(i).x, rectLev.get(i).y);
            }

        }
    }
}
