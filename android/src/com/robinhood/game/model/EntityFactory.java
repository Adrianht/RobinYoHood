package com.robinhood.game.model;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

// simplified BodyFactory from box2d repo
public class EntityFactory {

    private final World world;

    EntityFactory (World world) {
        this.world = world;
    }

    public Entity createGround() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(0, -9);

        Entity entity = new Entity();
        entity.addComponent("box2d");
        entity.component.box2dBody.body = world.createBody(bodyDef);

        // Shape - long and narrow
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(500, 3);
        entity.component.box2dBody.body.createFixture(shape, 0.0f);
        shape.dispose();

        return entity;
    }

    public Entity createPlayer(String username, int posX, int i) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(posX, -3);

        Entity entity = new Entity();
        entity.addComponent("turn");
        if(i == 0) {
            entity.component.turn.turn = true;
        }
        entity.addComponent("playernr");
        entity.component.playernr.nr = i;
        entity.addComponent("energy");
        entity.addComponent("hp");
        entity.addComponent("name");
        entity.component.name.name = username;
        entity.addComponent("box2d");
        entity.component.box2dBody.body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(1, 3);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        entity.component.box2dBody.body.createFixture(shape, 0.0f);
        shape.dispose();

        return entity;
    }

    public Entity newArrow() {
        BodyDef arrowBodyDef = new BodyDef();
        arrowBodyDef.position.set(0, 0);
        arrowBodyDef.type = BodyDef.BodyType.DynamicBody;
        arrowBodyDef.fixedRotation = false;

        Entity entity = new Entity();
        entity.addComponent("arrowType");
        entity.addComponent("box2d");
        entity.component.box2dBody.body = world.createBody(arrowBodyDef);

        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(.5f,.2f);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = polygonShape;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.7f;
        fixtureDef.restitution = 0.3f;
        entity.component.box2dBody.body.createFixture(fixtureDef);
        polygonShape.dispose();

        return entity;
    }

}