package org.dayaway.crazytoaster.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import org.dayaway.crazytoaster.CrazyToaster;
import org.dayaway.crazytoaster.levelApi.LevelCollection;
import org.dayaway.crazytoaster.levelApi.LevelStatic;
import org.dayaway.crazytoaster.sprites.Toaster;
import org.dayaway.crazytoaster.sprites.level.LevelManager;
import org.dayaway.crazytoaster.sprites.Toast;

public class PlayState extends State {

    private boolean CRASHED = false;
    private boolean EXPLODED = false;
    private boolean NEW_RECORD = false;

    private final LevelManager levelManager;
    private final LevelStatic levelStatic;
    private final Toast toast;
    private final BitmapFont bitmapFontScore;
    private int SCORE = 0;
    private boolean START = false;
    private boolean GAME_OVER = false;
    private boolean WINNER = false;
    private int BEST_SCORE;
    private final Preferences pref;

    private final GlyphLayout glyphLayout =  new GlyphLayout();

    private final FirstScreen firstScreen;
    private final EndScreen endScreen;
    private final WinStaticScreen winStaticScreen;

    private boolean blink;
    private long startBlinkTime;

    //Конструктор бесконечной игры
    public PlayState(GameStateManager gsm) {
        super(gsm);
        //Устанавливаем камеру вниз, что бы не возникали артифакты верхних платформ на FirstScreen
        if(gsm.isFirstScreen()) {
            camera.position.y = -CrazyToaster.HEIGHT / 1.5f + CrazyToaster.HEIGHT / 8f;
        }

        bitmapFontScore = new BitmapFont(Gdx.files.internal("wet.fnt"));
        bitmapFontScore.getData().setScale(0.8f);

        levelManager = new LevelManager(this);
        levelStatic = null;
        toast = new Toast(levelManager, this);

        pref = Gdx.app.getPreferences("crazy toaster preferences");
        BEST_SCORE = pref.getInteger("highscore");

        firstScreen = new FirstScreen(this);
        endScreen = new EndScreen(this);
        winStaticScreen = new WinStaticScreen(this);

        if(!gsm.isFirstScreen()) {
            START = true;
        }
    }

    public PlayState(GameStateManager gsm, LevelStatic levelStatic) {
        super(gsm);
        //Устанавливаем камеру вниз, что бы не возникали артифакты верхних платформ на FirstScreen
        if(gsm.isFirstScreen()) {
            camera.position.y = -CrazyToaster.HEIGHT / 1.5f + CrazyToaster.HEIGHT / 8f;
        }

        bitmapFontScore = new BitmapFont(Gdx.files.internal("wet.fnt"));
        bitmapFontScore.getData().setScale(0.8f);

        levelManager = new LevelManager(this, levelStatic);
        this.levelStatic = levelStatic;
        toast = new Toast(levelManager, this);

        pref = Gdx.app.getPreferences("crazy toaster preferences");
        BEST_SCORE = pref.getInteger("highscore");

        firstScreen = new FirstScreen(this);
        endScreen = new EndScreen(this);
        winStaticScreen = new WinStaticScreen(this);

        if(!gsm.isFirstScreen()) {
            START = true;
        }
    }



    @Override
    protected void handleInput() {

        if (toast.isCaptured()) {

            if (Gdx.input.justTouched()) {

                if (START) {
                    if(gsm.isSoundOn()) {
                        CrazyToaster.textures.throw_sound.play();
                    }
                    toast.release();
                }
                else {
                    if(gsm.isFirstScreen()) {
                        firstScreen.handleInput();
                    }
                    else {
                        toast.setTIME_SINCE_CAPTURE();
                    }
                }
            }
        }

        if(GAME_OVER) {
            endScreen.handleInput();
        }
        if(WINNER) {
            winStaticScreen.handleInput();
        }

    }

    @Override
    public void update(float dt) {

        if(START) {
            toast.update(dt);
        }
        if(!GAME_OVER && !WINNER) {
            levelManager.update(dt);
        }

        if(!gsm.isFirstScreen()) {
            //Если тост выше следующего тостера, камера останавливается
            if (toast.getPosition().y > levelManager.getFocusToaster().getPosition().y) {
                camera.position.y = levelManager.getFocusToaster().getPosition().y + LevelManager.LEVEL_GAP + 24;
            }
            //Если ниже то следим за тостом
            else {
                camera.position.y = toast.getPosition().y + LevelManager.LEVEL_GAP + 24;
            }
        }

        if(SCORE > BEST_SCORE && levelStatic == null) {
            pref.putInteger("highscore", SCORE);
            pref.flush();
            BEST_SCORE = SCORE;
            NEW_RECORD = true;
        }

        if(GAME_OVER) {
            START = false;

            if(gsm.getLoss() == 3) {
                gsm.showAd();
                gsm.restLoss();
            }

            if(gsm.getREWARD_FOR_AD()) {
                gsm.checkReward();
                toast.restartForReward();
                setGAME_OVER(false);
                gsm.offREWARD_FOR_AD();
                turnStart();
                CRASHED = false;
                EXPLODED = false;
                NEW_RECORD = false;
            }
        }
        handleInput();
    }

    @Override
    public void render(SpriteBatch batch) {
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        Gdx.gl.glClearColor(247, 215, 116, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //Рисуем фон оключаем смешивание, это увеличивает производительность
        batch.begin();
        batch.disableBlending();
        batch.draw(CrazyToaster.textures.background, 0, camera.position.y - LevelManager.LEVEL_GAP * 2);
        batch.enableBlending();

        levelManager.render(batch);
        toast.render(batch);

        if(START) {

            //Рисует очки
            if(!blink) {
                bitmapFontScore.getData().setScale(0.8f);
            }
            else {
                bitmapFontScore.getData().setScale(1f);

                if((System.currentTimeMillis() - startBlinkTime)/1000.0 > 0.15) {
                    bitmapFontScore.getData().setScale(0.8f);
                    unBlink();
                }
            }

            glyphLayout.setText(bitmapFontScore, String.valueOf(SCORE));
            bitmapFontScore.draw(batch, String.valueOf(SCORE), camera.position.x - glyphLayout.width / 2f,
                    camera.position.y + glyphLayout.height*1.5f + camera.viewportHeight/2f - 112);
        }

        if(gsm.isFirstScreen()) {
            firstScreen.render(batch);
        }
        batch.end();

        if (GAME_OVER) {
            endScreen.render(batch);
        }

        if(WINNER){
            winStaticScreen.render(batch);
        }
    }

    @Override
    public void dispose() {
        levelManager.dispose();
        toast.dispose();
        bitmapFontScore.dispose();
        firstScreen.dispose();
        endScreen.dispose();
        winStaticScreen.dispose();
    }

    @Override
    public void resize(int width, int height) {
    }

    public void incrementScore(){
        SCORE++;
    }

    //Update этого класса все равно продолжается после этого метода
    public void RETURN_GAME() {
        if(levelStatic != null) {
            gsm.set(new PlayState(gsm, levelStatic));
            gsm.incrementLoss();
            gsm.resetCheckReward();
        }
        else {
            gsm.set(new PlayState(gsm));
            gsm.incrementLoss();
            gsm.resetCheckReward();
        }
    }

    public boolean isGAME_OVER() {
        return this.GAME_OVER;
    }

    public void setGAME_OVER(boolean GAME_OVER) {
        this.GAME_OVER = GAME_OVER;

        if(GAME_OVER) {
            endScreen.setEndToasterTexture();
        }
    }

    public Toast getToast() {
        return toast;
    }

    public Camera getCamera() {
        return camera;
    }

    public boolean isSTART() {
        return START;
    }

    public GameStateManager getGSM() {
        return gsm;
    }

    public LevelManager getLevelManager() {
        return levelManager;
    }

    public void turnStart() {
        this.START = true;
        toast.setTIME_SINCE_CAPTURE();
    }

    public int getSCORE() {
        return SCORE;
    }

    public int getBEST_SCORE() {
        return BEST_SCORE;
    }

    public void blink() {
        this.blink = true;
        startBlinkTime = System.currentTimeMillis();
    }

    private void unBlink() {
        this.blink = false;
        startBlinkTime = 0;
    }

    public void turnCRASHED() {
        this.CRASHED = true;
    }

    public void turnEXPLODED() {
        this.EXPLODED = true;
    }

    public boolean isCRASHED() {
        return CRASHED;
    }

    public boolean isEXPLODED() {
        return EXPLODED;
    }

    public boolean isNEW_RECORD() {
        return NEW_RECORD;
    }

    public LevelStatic getLevelStatic() {
        return levelStatic;
    }

    public void win() {
        this.START = false;
        this.WINNER = true;
        this.levelStatic.ending();
        //Открываем следующий уровень
        if(levelStatic.getId() + 1 < LevelCollection.getInstance().get().size()) {
            LevelCollection.getInstance().get().get(levelStatic.getId() + 1).open();
        }
    }
}