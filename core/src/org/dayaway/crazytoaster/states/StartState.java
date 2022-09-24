package org.dayaway.crazytoaster.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import org.dayaway.crazytoaster.CrazyToaster;

//СОЗДАН ТОЛЬКО ДЛЯ ТОГО ЧТОБЫ ПЕРВЫМ ДЕЛАТЬ НАСТРОИЛАСЬ КАМЕРА
public class StartState extends State{

    public StartState(GameStateManager gsm) {
        super(gsm);
    }

    @Override
    protected void handleInput() {

    }

    @Override
    public void update(float dt) {
        gsm.push(new StartPageState(gsm));
    }

    @Override
    public void render(SpriteBatch batch) {

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
