package com.robinhood.game.view.interfaceObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Actor;

import com.robinhood.game.assetManagers.GameAssetManager;
import com.robinhood.game.model.Entity;
import com.robinhood.game.model.Model;

/**
 * Class with interface object showing the arrow
 * in shot animation.
 *
 * @author group 11
 * @version 1.0
 * @since 2020-04-25
 */
public class Arrow extends Actor {

    private final Model model;
    private Sprite sprite;
    private float startPosX, startPosY;
    private boolean isNewShot = true;
    private GameAssetManager assetManager;

    public Arrow(Model model, GameAssetManager assetManager) {
        this.model = model;
        this.assetManager = assetManager;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        Entity activeArrow = model.getActiveArrowEntity();
        if (activeArrow != null) {
            Body arrowBody =
                    activeArrow.components.box2dBody.body;
            if (isNewShot) {
                createSprite(activeArrow);
                isNewShot = false;
            }
            sprite.setPosition(
                    startPosX + arrowBody.getPosition().x *
                            Gdx.graphics.getWidth()/32,
                    startPosY + arrowBody.getPosition().y *
                            Gdx.graphics.getHeight()/24);
            sprite.draw(batch);
        } else {
            isNewShot = true;
        }
    }

    private void createSprite(Entity arrowEntity) {
        Body arrowBody = arrowEntity.components.box2dBody.body;
        Texture arrowTexture = assetManager.get(assetManager.arrow);
        sprite = new Sprite(arrowTexture);
        sprite.setSize(20, 20);
        sprite.rotate(40);
        if(arrowBody.getLinearVelocity().x < 0) {
            startPosX = Gdx.graphics.getWidth()/2.2f;
        } else {
            startPosX = Gdx.graphics.getWidth()/2.2f;
            sprite.rotate90(true);
            sprite.rotate90(true);
        }
        startPosY = Gdx.graphics.getHeight()/1.85f;
    }
}
