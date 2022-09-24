package org.dayaway.crazytoaster.states;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import org.dayaway.crazytoaster.CrazyToaster;
import org.dayaway.crazytoaster.utill.ActionAd;

import java.util.Stack;

public class GameStateManager {

    private Camera camera = new OrthographicCamera();

    private Viewport viewport = new ExtendViewport(CrazyToaster.WIDTH,CrazyToaster.HEIGHT,camera);

    private ActionAd actionAd;

    private final Stack<State> states;

    private int loss = 1;

    private boolean REWARD_FOR_AD = false;

    private boolean checkedReward = false;

    private boolean firstScreen = true;

    private boolean isSoundOn = true;


    public GameStateManager(ActionAd actionAd) {
        states = new Stack<>();
        this.actionAd = actionAd;
    }

    public void push(State state) {
        states.push(state);
    }

    public void pop() {
        states.pop().dispose();
    }

    public void set(State state) {
        states.pop().dispose();
        states.push(state);
    }

    public void update(float dt) {
        states.peek().update(dt);
    }

    public void render(SpriteBatch batch) {
        states.peek().render(batch);
    }

    public void resize(int width, int height){
        states.peek().resize(width, height);
    }

    public void turnREWARD_FOR_AD() {
        this.REWARD_FOR_AD = true;
    }

    public boolean getREWARD_FOR_AD() {
        return this.REWARD_FOR_AD;
    }

    public void offREWARD_FOR_AD() {
        this.REWARD_FOR_AD = false;
    }

    public void incrementLoss() {
        loss += 1;
    }

    public int getLoss() {
        return loss;
    }

    public void restLoss() {
        loss = 0;
    }

    public void checkReward() {
        this.checkedReward = true;
    }

    public boolean isCheckedReward() {
        return checkedReward;
    }

    public void resetCheckReward() {
        this.checkedReward = false;
    }

    public boolean isFirstScreen() {
        return firstScreen;
    }

    public void turnFirstScreen() {
        firstScreen = !firstScreen;
    }

    public void turnSound() {
        this.isSoundOn = !isSoundOn;
    }

    public boolean isSoundOn() {
        return isSoundOn;
    }


    public void showAd() {
        actionAd.showAd();
    }

    public void showRewardAd() {
        actionAd.showRewardAd();
    }

    public Camera getCamera() {
        return this.camera;
    }

    public Viewport getViewport() {
        return this.viewport;
    }
}
