package org.dayaway.crazytoaster.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

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

    private final Rectangle mosPos = new Rectangle(0,0,1,1);

    public LevelCollectionState(GameStateManager gsm) {
        super(gsm);
        this.gsm = gsm;
        this.levelList = LevelCollection.getInstance().get();

        rectLev = new ArrayList<>();

        //Тут мы создаем лист скелетов
        for (int i = 0; i < levelList.size(); i++) {
            rectLev.add(new Rectangle(0,0,64,64));
        }

        //Устанавливает камеру по центру экрана
        camera.position.set(camera.viewportWidth/2, camera.viewportHeight/2,0);

        setLevelPositions();
    }

    @Override
    protected void handleInput() {
        Vector3 tmpMosPos = camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
        mosPos.setPosition(tmpMosPos.x, tmpMosPos.y);

        if(Gdx.input.justTouched()) {
            for (int i = 0; i < rectLev.size(); i++) {
                if(rectLev.get(i).overlaps(mosPos)) {

                    if(levelList.get(i).isOpen()) {
                        //Тут мы получаем лист уровней который выбрали
                        //Нужно запушить PlayStaticState в аргумент передать этот лист
                        gsm.push(new PlayState(gsm, levelList.get(i))); //Достали уровень под индексом i
                    }
                }
            }
        }
    }

    @Override
    public void update(float dt) {
        camera.position.set(camera.viewportWidth/2, camera.viewportHeight/2,0);

        handleInput();
    }

    @Override
    public void render(SpriteBatch batch) {
        camera.update();
        batch.setProjectionMatrix(camera.combined);


        Gdx.gl.glClearColor(247, 215, 116, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        batch.draw(CrazyToaster.textures.background, 0, camera.position.y - LevelManager.LEVEL_GAP * 2);

        drawLevelPositions(batch);

        batch.end();
    }

    @Override
    public void dispose() {

    }

    @Override
    public void resize(int width, int height) {

    }

    public void setLevelPositions() {
        int layer = 1;

        for (int i = 0; i < rectLev.size(); i++) {

            if(layer == 1) {
                rectLev.get(i).setPosition(BORDER + ((BORDER * 2) * i) + (i * WIDTH), camera.position.y + 100);
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
                rectLev.get(i).setPosition(BORDER + ((BORDER * 2) * p) + (p * WIDTH), camera.position.y - 100);
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

                //Если уровень не пройден
                if(!levelList.get(i).isEnding()) {
                    batch.draw(CrazyToaster.textures.toast, rectLev.get(i).x, rectLev.get(i).y);
                }
                //Если уровень пройден
                else {
                    batch.draw(CrazyToaster.textures.toast, rectLev.get(i).x, rectLev.get(i).y);
                }
            }
            //Если уровень не открыт
            else {
                batch.draw(CrazyToaster.textures.toaster, rectLev.get(i).x, rectLev.get(i).y);
            }
        }
    }
}
