package com.robinhood.game.model;


import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Systems {

    // system class handling rendering entities
    public static class UserInput {
        public void moveLeft(List<Entity> entities) {
            for(Entity entity: entities) {
                if(entity.component.turn != null) {
                    if (entity.component.turn.turn & entity.component.energy.value > 0) {
                        entity.component.pos.x -= 20;
                        entity.component.energy.value -= 2;
                        System.out.println("Player, whose turn it is moved left, energy points reduced to: " +
                                entity.component.energy.value);
                    }
                }
            }
        }
        public void moveRight(List<Entity> entities) {
            for(Entity entity: entities) {
                if (entity.component.turn != null) {
                    if (entity.component.turn.turn & entity.component.energy.value > 0) {
                        entity.component.pos.x += 20;
                        entity.component.energy.value -= 2;
                        System.out.println("Player, whose turn it is moved right, energy points reduced to: " +
                                entity.component.energy.value);
                    }
                }
            }
        }
        // FIXME: possibly move elsewhere
        public void changeTurn(List<Entity> entities, int nrOfPlayers) {

            // FIXME: try to find more elegant solution(s)

            int prevPlayerNr = 0;

            // remove current players turn
            for(Entity entity: entities) {
                if(entity.component.turn != null) {
                    if (entity.component.turn.turn) {
                        prevPlayerNr = entity.component.playernr.nr;
                        entity.component.turn.turn = false;
                    }
                }
            }

            // give turn to next player and add 10 energy points
            for(Entity entity: entities) {
                if(entity.component.playernr != null) {
                    if (entity.component.playernr.nr == (prevPlayerNr + 1) % nrOfPlayers) {
                        entity.component.turn.turn = true;
                        if (entity.component.energy.value < 90) {
                            entity.component.energy.value += 10;
                        } else {
                            entity.component.energy.value = 100;
                        }
                    }
                }
            }

            // reset arrow attributes to "Normal" arrow
            for(Entity entity: entities) {
                if(entity.component.arrowtype != null) {
                    entity.component.arrowtype.type = "Normal";
                    entity.component.arrowtype.damage = 10;
                }
            }
        }

        /* Checks if the player has enough energy to buy that arrow type, if yes,
        updates the arrow type*/
        public void buyArrow(List<Entity> entities, String type) {
            Entity activePlayer = null;
            Entity arrowEntity = null;
            for(Entity entity: entities) {
                if (entity.component.turn != null && entity.component.turn.turn) {
                    activePlayer = entity;
                } else if (entity.component.arrowtype != null) {
                    arrowEntity = entity;
                }
            }

            // Check if player can afford current buy,
            //  then update arrowtype and player energy
            if(activePlayer != null && arrowEntity != null) {
                int currentEnergy = activePlayer.component.energy.value;

                // TODO: remove sysout
                System.out.println("Arrowtype before buying: "+ arrowEntity.component.arrowtype.type);

                // Level 2 arrow - cost: 20, damage: 20
                if(type.equals("Level2") && currentEnergy >= 20) {
                    arrowEntity.component.arrowtype.type = "Level2";
                    arrowEntity.component.arrowtype.damage = 20;
                    activePlayer.component.energy.value -= 20;
                    // TODO: remove sysout
                    System.out.println("Arrow2 bought and has damage: " +
                            arrowEntity.component.arrowtype.damage);
                }
                // Level 3 arrow - cost: 50, damage: 50
                else if(type.equals("Level3") && currentEnergy >= 50) {
                    arrowEntity.component.arrowtype.type = "Level3";
                    arrowEntity.component.arrowtype.damage = 50;
                    activePlayer.component.energy.value -= 50;
                    // TODO: remove sysout
                    System.out.println("Arrow3 bought and has damage: " +
                            arrowEntity.component.arrowtype.damage);
                }
                // Level 4 arrow - cost: 70, damage: 70
                else if(type.equals("Level4") && currentEnergy >= 70) {
                    arrowEntity.component.arrowtype.type = "Level4";
                    arrowEntity.component.arrowtype.damage = 70;
                    activePlayer.component.energy.value -= 70;
                    // TODO: remove sysout
                    System.out.println("Arrow4 bought and has damage: " +
                            arrowEntity.component.arrowtype.damage);
                }
            }
        }
    }


    public static class playerInfo {

        // Finds and returns HP values of all players
        public static List<Integer> getHP(List<Entity> entities, int nrOfPlayers){
            List<Integer> points = Arrays.asList(new Integer[nrOfPlayers]);
            for(Entity entity: entities){
                if(entity.component.hp != null){
                    points.set(entity.component.playernr.nr, entity.component.hp.value);
                }
            }
            return points;
        }

        public static List<Integer> getEnergyPoints(List<Entity> entities, int nrOfPlayers){
            List<Integer> points = Arrays.asList(new Integer[nrOfPlayers]);
            for (Entity entity: entities){
                if(entity.component.energy != null){
                    points.set(entity.component.playernr.nr, entity.component.energy.value);
                }
            }
            return points;
        }
    }


    // system class handling rendering entities
    public static class Render {
        public List<Actor> getActors(List<Entity> entities) {
            List<Actor> actors = new ArrayList<>();

            for(Entity entity: entities) {
                if (entity.component.actor != null) {
                    actors.add(entity.component.actor);
                }
            }

            return actors;
        }

        /*
           TODO:
           add system method update() to change correct rendering actor(s)

        public void update(EntityManager manager) {
            int required_components = Component.POS | Component.RENDER;
            for (int i = 0; i < manager.size; i++) {
                if ((manager.flag[i] & required_components) == required_components) {
                    System.out.println(String.format("%s: (%f x, %f y)", manager.rendering[i].name, manager.pos[i].y, manager.pos[i].y));
                }
            }
        }
        */
    }


    // system class handling animations
    // FIXME: currently suited for two players
    public static class Animation {
        public boolean arrowAnimationShotIsVital(List<Entity> entities, Vector2 vector2) {

            // FIXME: refac this test of whos turn it is and arrow direction
            float direction = -400;
            for(Entity playerTurn: entities) {
                if(playerTurn.component.turn != null
                        && playerTurn.component.turn.turn
                        && playerTurn.component.playernr.nr == 0) {
                    direction = 400;
                    break;
                }
            }

            for(Entity entity: entities) {
                // finds arrow object
                if (entity.component.arrowtype != null) {

                    //TODO-Lars: add correct flying pattern and direction
                    entity.component.pos.x += direction;
                    entity.component.pos.y += 0;

                    // finds opponent object
                    for(Entity entity2: entities) {
                        if(entity2.component.playernr != null && !entity2.component.turn.turn) {
                            if (this.isHit(entity, entity2)) {
                                entity2.component.hp.value -= entity.component.arrowtype.damage;
                                System.out.println("Player " + entity2.component.playernr.nr
                                        + " was hit, HP now: " + entity2.component.hp.value);
                                return entity2.component.hp.value < 1;
                            }
                        }
                    }
                }
            }
            return false;
        }

        private boolean isHit(Entity arrow, Entity opponent) {

            Float arrowLeftmost = arrow.component.pos.x;
            Float arrowRightmost = arrow.component.pos.x + arrow.component.actor.sprite.getWidth();
            Float arrowLowest = arrow.component.pos.y;
            Float arrowHighest = arrow.component.pos.y + arrow.component.actor.sprite.getHeight();

            Float opponentLeftmost = opponent.component.pos.x;
            Float opponentRightmost = opponent.component.pos.x + opponent.component.actor.sprite.getWidth();
            Float opponentLowest = opponent.component.pos.y;
            Float opponentHighest = opponent.component.pos.y + arrow.component.actor.sprite.getHeight();

            // check if any of arrow sprite corners are inside opponent sprite
            if (((arrowLeftmost <= opponentRightmost && arrowLeftmost >= opponentLeftmost)
                    || (arrowRightmost <= opponentRightmost && arrowRightmost >= opponentLeftmost))
                && ((arrowLowest <= opponentHighest && arrowLowest >= opponentLowest)
                    || (arrowHighest <= opponentHighest && arrowHighest >= opponentLowest))) {
                return true;
            }

            return false;
        }
    }
}