package com.robinhood.game.view.loader;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.assets.loaders.SkinLoader.SkinParameter;

public class GameAssetManager {

    public final AssetManager manager = new AssetManager();

    // TODO: Add files specific files
    // Textures
    public final String firstPlayerImage = "";
    public final String secondPlayerImage = "";

    // Background Textures
    public final String menuBackground = "background-smaller.png";
    public final String gameBackground1 = "";
    public final String gameBackground2 = "";

    // Skins Parameters
    public final String buttonSkinParams = "skin/dark-hdpi/Holo-dark-hdpi.atlas";
    public final String textSkinParams = "skin/shade/uiskin.atlas";
    public final String headerSkinParams = "skin/craftacular/craftacular-ui.atlas";
    // Skins
    public final String buttonSkin = "skin/dark-hdpi/Holo-dark-hdpi.json";
    public final String textSkin = "skin/shade/uiskin.json";
    public final String headerSkin = "skin/craftacular/craftacular-ui.json";

    // Sounds
    //public final String moveSound = "";
    public final String drawSound = "sounds/arrow_draw.wav";
    public final String shootSound = "sounds/arrow_shoot.wav";
    public final String hitSound = "sounds/arrow_hit.wav";
    public final String upgradeSound = "sounds/arrow_upgrade.wav";

    // Music
    public final String music = "sounds/game_music.mp3";

    public void loadPlayerImages() {
        manager.load(firstPlayerImage, Texture.class);
        manager.load(secondPlayerImage, Texture.class);
    }

    public void loadBackgrounds() {
        manager.load(menuBackground, Texture.class);
        //manager.load(gameBackground1, Texture.class);
        //manager.load(gameBackground2, Texture.class);
    }

    public void loadSkins() {
        SkinParameter buttonParams = new SkinParameter(buttonSkinParams);
        SkinParameter textParams = new SkinParameter(textSkinParams);
        SkinParameter headerParams = new SkinParameter(headerSkinParams);
        manager.load(buttonSkin, Skin.class, buttonParams);
        manager.load(textSkin, Skin.class, textParams);
        manager.load(headerSkin, Skin.class, headerParams);
    }


    public void loadSounds() {
        //manager.load(moveSound, Sound.class);
        manager.load(drawSound, Sound.class);
        manager.load(shootSound, Sound.class);
        manager.load(hitSound, Sound.class);
        manager.load(upgradeSound, Sound.class);
    }

    public void loadMusic() {
        manager.load(music, Music.class);
    }
}