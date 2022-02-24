package com.projectgamma.ProjectGamma.api.game;

import java.util.Formattable;

/**
 * An interface representing a player of a game.
 */
public interface Player extends Formattable {

    /**
     * Returns a mention to the player, mostly useful when used in conjunction with
     * {@link GameChannel#sendMessage(String, Object...)}. This is also the return value of {@code Player#toString()}
     *
     * @return A mention to the player in the format required by the Discord API
     */
    String getMention();

    /**
     * Returns the username of the player in plaintext format and without the four digit discriminator.
     *
     * @return The username of the player
     */
    String getName();

    /**
     * Returns the unique ID that the Discord API associates to this player. This can be useful to map data to players
     * in your custom game state class.
     *
     * @return The unique ID of the player
     */
    long getId();
}
