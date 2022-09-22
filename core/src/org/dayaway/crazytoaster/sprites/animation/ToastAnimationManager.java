package org.dayaway.crazytoaster.sprites.animation;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import org.dayaway.crazytoaster.CrazyToaster;
import org.dayaway.crazytoaster.sprites.Toast;

public class ToastAnimationManager {

    private Animation animation;
    private final Toast toast;

    public ToastAnimationManager(Toast toast) {
        this.toast = toast;
        setAnimation();
    }

    public TextureRegion getFrame() {
        return animation.getFrame();
    }

    public void update(float dt) {
        if(toast.isLost()) {
            if(animation.getCurrentFrameCount() == 3) {
                animation.refresh(2);
            }
        }
        animation.update(dt);
    }

    public void setAnimation() {
        if(!toast.isLost()) {
            animation = new Animation(new TextureRegion(CrazyToaster.textures.toast_rotate), 4,0.2f);
        }
        else {
            animation = new Animation(new TextureRegion(CrazyToaster.textures.toast_fall), 4,0.01f);
        }

    }
}
