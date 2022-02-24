package com.projectgamma.ProjectGamma.api.game;

import java.util.List;
import java.util.Optional;

/**
 * A game is defined by implementing this interface. This object will be treated as a singleton and as such it should
 * not contain any game state. A type parameter is provided to define a game state class unique to each game instance,
 * that is created upon starting a game, destroyed on its end, and passed to every event handler.
 *
 * @param <S> The class representing the state of a particular game instance
 */
public interface Game<S> {

    /**
     * This method will be called every time a new game is started.
     *
     * @param players A non-empty list of players that will take part of the game
     * @param channel The channel associated to this game
     * @return A game state object if the game start is successful, or an empty object otherwise
     */
    Optional<S> onGameStart(List<Player> players, GameChannel channel);

    /**
     * This method will be called every time a player of the game sends a message in the game's channel.
     *
     * @implNote This method only filters messages by sender, not by content. Be aware that you are likely to receive
     * tons of messages irrelevant to your game.
     * @param player The player who sent the message
     * @param message The message sent by the player
     * @param state The current game state
     */
    void onPlayerMessage(Player player, String message, State<S> state);

    /**
     * This method will be called whenever a player trys to join an already started game.
     *
     * @param player The new player
     * @param state The current game state
     * @return A boolean indicating if the player is able to join the game
     */
    boolean onPlayerJoin(Player player, State<S> state);

    /**
     * This method will be called whenever a player decides to leave a not yet finished game.
     *
     * @implSpec You cannot decide if the player stays in the game or not
     * @param player The leaving player
     * @param state The current game state
     */
    void onPlayerLeave(Player player, State<S> state);

    /**
     * This method will be called when the game ends.
     *
     * @param timedOut Whether the game ended due to inactivity (true) or by an explicit call to {@link State#endGame()}
     * (false)
     * @param state The current game state (this will be destroyed when this method returns!)
     */
    void onGameEnd(boolean timedOut, State<S> state);

    /**
     * This interface is used to provide information about the game state to the event handlers of the {@link Game}
     * class.
     *
     * @param <S> Same as in {@link Game}
     */
    interface State<S> {

        /**
         * Returns the channel where the game is currently running.
         *
         * @implSpec The return value of this method could change across events, due to a game being moved to another
         * channel
         * @return The channel that is currently associated to the running game
         */
        GameChannel getChannel();

        /**
         * Returns the custom game state object associated to the running game.
         *
         * @return The custom game state object associated to the running game
         */
        S getState();

        /**
         * This method ends the game, calling {@link Game#onGameEnd(boolean, State)} beforehand.
         */
        void endGame();
    }
}
