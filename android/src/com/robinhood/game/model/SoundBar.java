package com.robinhood.game.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;


public final class SoundBar extends Actor {
    /*

    Singleton class

    Example class demonstrating an interface object which
    holds game data. Objects containing game data should
    be placed in the model-package.

    */

    private static final SoundBar soundBar = new SoundBar();
    private Boolean soundActive = true;

    private Sprite sprite;

    public SoundBar() {
        sprite = new Sprite(new Texture("soundon.png"));
        setPosition(200, 200);
    }

    // TODO: add description
    public Boolean getSound() {
        return soundActive;
    }

    // TODO: add description
    public void changeSound() {
        if(soundActive) {
            soundActive = false;
            sprite = new Sprite(new Texture("soundoff.png"));
        } else {
            soundActive = true;
            sprite = new Sprite(new Texture("soundon.png"));
        }
    }

    public static SoundBar getSoundBar() {
        return soundBar;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.setPosition(this.getX(), this.getY());
        sprite.draw(batch);
    }

}