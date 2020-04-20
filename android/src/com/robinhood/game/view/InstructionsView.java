package com.robinhood.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;

import com.robinhood.game.controller.Controller;

/**
 * Subclass in Template method pattern creating the UI to get game instructions.
 *
 * @author group 11
 * @version 1.0
 * @since 2020-04-25
 */
public class InstructionsView extends View {

    public InstructionsView(Controller controller) {
        super(controller);

        Skin skinInstruction = new Skin(Gdx.files.internal(
                "skin/shade/uiskin.json"));

        Skin skinButton = new Skin(Gdx.files.internal(
                "skin/dark-hdpi/Holo-dark-hdpi.json"));
        //Label instructionsHeading = new Label("GAME INSTRUCTIONS", skinHeading);
        Label instructions1 = new Label(
                " - You win the game when the other player’s HitPoints runs out. \n" +
                        "- It remains your turn until you shoot the arrow.\n" +
                        "- Hitting the opponent results in the opponent losing points\n"
                , skinInstruction);
        Label instructions2 = new Label(
                " - Every move costs 2 energy points\n" +
                        "- Upgrade your weapon if wanted before making the shot\n" +
                        "- Drag your finger and aim towards the opponent\n" +
                        "- When aimed, let go of the screen to shoot the opponent\n"
                , skinInstruction);
        Label instructions3 = new Label(
                " - Both players start with 100 HitPoints and 20 EnergyPoints. \n" +
                        "- EnergyPoints can be used to purchase upgraded arrows with increased damage\n" +
                        "- The cost of the upgraded arrows are indicated in the game \n" +
                        "- For every turn, the players gains 10 EnergyPoints \n"
                , skinInstruction);
        TextButton menu = new TextButton("Menu", skinButton);

        table.bottom();
        table.row().pad(20f, 0, 30f, 0);
        table.row().pad(0, 0, 30f, 0);

        table.add(instructions1).fillX().uniform().width(300f).height(100f);
        table.row().pad(30f, 0, 30f, 0);
        instructions1.setAlignment(Align.center);

        table.add(instructions2).fillX().uniform().width(300f).height(100f);
        table.row().pad(30f, 0, 50f, 0);
        instructions2.setAlignment(Align.center);

        table.add(instructions3).fillX().uniform().width(300f).height(100f);
        table.row().pad(30f, 0, 80f, 0);
        instructions3.setAlignment(Align.center); // Align center

        table.add(menu).fillX().uniform().width(300f).height(100f);
        table.row().pad(10f, 0, 60f, 0);

        instructions1.setFontScale(2.5f);
        instructions2.setFontScale(2.5f);
        instructions3.setFontScale(2.5f);

        menu.addListener(generateNavigateListener("MENU"));
    }
}