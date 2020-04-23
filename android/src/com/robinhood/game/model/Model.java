package com.robinhood.game.model;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;
import java.util.List;

/**
 * Class representing Model in Model-View-Controller.
 * Holds all data and runs the game loop.
 *
 * @author group 11
 * @version 1.0
 * @since 2020-04-25
 */
public class Model {

    // ECS-related fields
    private List<Entity> entities;
    private Systems systems;

    // Box2D-related fields
    private World world;
    private EntityFactory entityFactory;

    // Other data fields
    private String myUsername = "Username";
    private boolean gameInitialized = false;
    private int[] hitPointValues;
    private int myEnergyPoints;
    private boolean isMyTurn;
    private Body[] collidingBodies;
    private String userInput, gameWinner;
    private Entity[] playerEntities;
    private Entity activeArrowEntity;

    // Initiates a game
    public void initiateGame(List<String> usernames) {
        // Initiate Box2D-related objects
        world = new World(new Vector2(0,-10f), true);
        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                collidingBodies = new Body[2];
                collidingBodies[0] = contact.getFixtureA().getBody();
                collidingBodies[1] = contact.getFixtureB().getBody();
            }

            @Override
            public void endContact(Contact contact) {
            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {
            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {
            }
        });
        entityFactory = new EntityFactory(world);

        // Initiate game entities
        entities = new ArrayList<>();
        entities.add(entityFactory.createGround());

        createNewArrowEntity();

        int playerSpace = 24 / (usernames.size()-1);
        setIsMyTurn(myUsername.equals(usernames.get(0)));
        this.playerEntities = new Entity[usernames.size()];
        for (int i = 0; i < usernames.size(); i++) {
            Entity player = entityFactory.createPlayer(
                    usernames.get(i),
                    (playerSpace*i - 12),
                    i
            );
            entities.add(player);
            this.playerEntities[i] = player;
        }

        // Initiate game systems
        systems = new Systems(this);
        systems.GameInfoSystem(entities);

        this.gameInitialized = true;
    }

    // Method called on every game action
    public void gameLoop() {
        systems.UserInputSystem(entities);
        systems.AnimationSystem(entities);
        systems.GameInfoSystem(entities);
        systems.action = "";
        collidingBodies = null;
    }

    public void createNewArrowEntity() {
        Entity arrowEntity = new Entity();
        arrowEntity.addComponent("arrowType");
        entities.add(arrowEntity);
    }

    public void createArrowBody(Entity arrowEntity, float startPosX) {
        entityFactory.newArrowBody(arrowEntity, startPosX, userInput);
    }

    // Method called on change in Firebase Real-time db
    public void notifyChangeInFirebase(String userInput) {
        this.userInput = userInput;
        gameLoop();
    }

    // Method returns if game is initialized
    public void resetModelData() {
        entities.clear();
        gameWinner = null;
        gameInitialized = false;
    }

    // Field getters
    public World getWorld() {
        return world;
    }
    public Body[] getCollidingBodies() {
        return collidingBodies;
    }
    public Entity[] getPlayerEntities() {
        return playerEntities;
    }
    public String getMyUsername() {
        return myUsername;
    }
    public String getGameWinner() {
        return gameWinner;
    }
    public String getUserInput() {
        return userInput;
    }
    public int[] getHitPointValues(){
        return hitPointValues;
    }
    public int getMyEnergyPoints(){
        return myEnergyPoints;
    }
    public boolean isMyTurn(){
        return isMyTurn;
    }
    public boolean isGameInitialized() {
        return gameInitialized;
    }

    // Field setters
    public void setMyUsername(String username) {
        this.myUsername = username;
    }
    public void setGameWinner(String gameWinner) {
        this.gameWinner = gameWinner;
    }
    public void setHitPointValues(int[] hitPointValues) {
        this.hitPointValues = hitPointValues;
    }
    public void setMyEnergyPoints(int myEnergyPoints) {
        this.myEnergyPoints = myEnergyPoints;
    }
    public void setIsMyTurn(boolean isMyTurn) {
        this.isMyTurn = isMyTurn;
    }

    public void setActiveArrowEntity(Entity activeArrowEntity) {
        this.activeArrowEntity = activeArrowEntity;
    }

    public Entity getActiveArrowEntity() {
        return activeArrowEntity;
    }
}
