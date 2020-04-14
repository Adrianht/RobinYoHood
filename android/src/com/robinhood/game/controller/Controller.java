package com.robinhood.game.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.robinhood.game.RobinHood;

import com.robinhood.game.model.Model;
import com.robinhood.game.view.*;
import java.util.List;

public class Controller {

    private RobinHood game;
    private Model model = new Model();
    private FBConnector fbconnector = new FBConnector();

    public Controller(RobinHood game) {
        this.game = game;
    }

    // Method called from views to navigate through the application
    public void navigateTo(String destination) {
        switch(destination) {
            case "MENU":
                game.setView(new MenuView(this));
                break;
            case "SETTINGS":
                game.setView(new SettingsView(this, model));
                break;
            case "LOADING":
                game.setView(new LoadingView(this, model));
                break;
            case "LOBBY":
                game.setView(new RoomView(this));
                break;
            case "GAME":
                game.setView(new GameView(this, model));
                break;
            case "GAMEOVER":
                game.setView(new GameOverView(this,model));
                break;
            default:
                game.setView(new MenuView(this));
        }
    }

    // Method called from GameView to update fb and model
    public void move(Boolean left) {
        //fbConn.move(left);
        model.move(left);
    }

    // Method called from views to update fb and call model
    public void buyArrow(String type) {
        //fbConn.buyArrow(type);
        model.buyArrow(type);
    }

    // Method called from views to update fb and model
    public void drawBow(Vector2 vector2) {
        //fbConn.drawBow(vector2);
        boolean gameOver = model.drawBowEndGame(vector2);
        if (gameOver) {
            navigateTo("GAMEOVER");
        }
    }

    // Method to call model about sound settings change
    public void changeSound() {
        model.changeSound();
    }

    // Method to initiate Firebase-connector and find another player
    public void findPlayer(String username) {
        fbconnector.findPlayer(username);
    }

    // Method called to initiate game after Firebase has found opponent
    public void initiateGame() {
        model.initiateGame();
    }

    // Method to exit application, called from MenuView
    public void exitApplication() {
        Gdx.app.exit();
    }

    // Method call model about players hit point values
    public List<Integer> getHP(){
        return model.getHP();
    }

    // Method call model about players energy values
    public List<Integer> getEnergy(){
        return model.getEnergy();
    }

}