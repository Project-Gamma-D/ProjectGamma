package com.projectgamma.ProjectGamma.game;

import com.projectgamma.ProjectGamma.api.game.Game;
import com.projectgamma.ProjectGamma.api.game.GameChannel;
import com.projectgamma.ProjectGamma.api.game.Player;

import java.util.List;
import java.util.Optional;

/**
 * Instances of this object represent an instance of an ongoing game.
 *
 * @param <G> The game object type
 * @param <S> The game state object type
 */
public class GameInstance<G extends Game<S>, S> {

    private final G game;
    private final Game.State<S> state;

    private GameInstance(G game, GameChannel channel, S gameState) {
        this.game = game;
        this.state = new State(channel, gameState);
    }

    /**
     * Attempts to start a game by dispatching the {@link Game#onGameStart(List, GameChannel)} event.
     *
     * @param game The game object
     * @param players The players participating in the game
     * @param channel The channel associated to the game instance
     * @param <G> The game object type
     * @param <S> The game state object type
     * @return A game instance if the game started successfully, an empty object otherwise
     */
    public static <G extends Game<S>, S> Optional<GameInstance<G, S>> start(G game, List<Player> players, GameChannel channel) {
        return game.onGameStart(players, channel)
                .map(s -> new GameInstance<>(game, channel, s));
    }

    /**
     * Dispatches the {@link Game#onPlayerMessage(Player, String, Game.State)} event on this game instance.
     *
     * @param player The player who sent the message
     * @param message The message contents
     */
    public void sendMessage(Player player, String message) {
        this.game.onPlayerMessage(player, message, this.state);
    }

    /**
     * Dispatches the {@link Game#onPlayerJoin(Player, Game.State)} event on this game instance.
     *
     * @param player The player attempting to join the game
     * @return Whether the player was able to join the game
     */
    public boolean tryJoinPlayer(Player player) {
        return this.game.onPlayerJoin(player, this.state);
    }

    /**
     * Dispatches the {@link Game#onPlayerLeave(Player, Game.State)} event on this game instance.
     *
     * @param player The player leaving the game
     */
    public void removePlayer(Player player) {
        this.game.onPlayerLeave(player, this.state);
    }

    /**
     * Dispatches the {@link Game#onGameEnd(boolean, Game.State)} event on this game instance.<br/><br/>
     * Note: No other events should be dispatched after calling this method.
     *
     * @param timedOut Whether the game is ending due to inactivity
     */
    public void end(boolean timedOut) {
        this.game.onGameEnd(timedOut, this.state);
    }

    /**
     * A {@link GameInstance} dependant implementation of {@link Game.State}.
     */
    class State implements Game.State<S> {

        private final GameChannel channel;
        private final S state;

        private State(GameChannel channel, S state) {
            this.channel = channel;
            this.state = state;
        }

        @Override
        public GameChannel getChannel() {
            return channel;
        }

        @Override
        public S getState() {
            return state;
        }

        @Override
        public void endGame() {
            end(false);
        }
    }
}
